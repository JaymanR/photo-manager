package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.Album;
import application.photos12.model.Photo;
import application.photos12.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.Optional;

public class ImageGalleryController {
    private static final double THUMBNAIL_SIZE = 100;

    @FXML
    private FlowPane flowPane;
    @FXML
    private VBox displayArea;
    @FXML
    private ImageView displayImageView;
    @FXML
    private Label caption;
    @FXML
    private Label date;
    @FXML
    private Label tags;

    private Album currentAlbum;
    private Scene albumsScene;
    private User user;
    private ObservableList<Album> albumObservableList;

    private ImageView currentImageView;
    private ImageView prevThumbnail;

    public void start(User user, Album a, Scene prevScene, ObservableList<Album> albumObservableList) throws FileNotFoundException {
        currentAlbum = a;
        albumsScene = prevScene;
        this.user = user;
        this.albumObservableList = albumObservableList;
        initializeImages();
    }

    public void initializeImages() throws FileNotFoundException {
        if (currentAlbum.getNumPhotos() > 0) {
            for (Photo p : currentAlbum.getImages()) {
                addImageThumbnail(p);
            }
        }
        hideDisplayArea();
    }

    public void addImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Photos.window);
        if (selectedFile != null) {
            Photo photo = user.searchAllPictures(selectedFile);

            if (currentAlbum.searchImage(selectedFile)) {
                duplicatePhotoAlert();
                return;
            }

            if (photo != null) {
                currentAlbum.addImage(photo);
            } else {
                photo = new Photo(selectedFile);
                user.addImageToUser(photo);
                currentAlbum.addImage(photo);
            }
            user.writeUser();
            addImageThumbnail(photo);

            albumObservableList.set(albumObservableList.indexOf(currentAlbum), currentAlbum);
        }
    }

    public void giveCaptionDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(Photos.window);
        dialog.setTitle("Image Caption");
        dialog.setHeaderText("Please input caption for this image");
        Optional<String> result = dialog.showAndWait();

    }

    public void addImageThumbnail(Photo p) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(p.getSrc()));
        ImageView photoImageView = new ImageView();

        photoImageView.setImage(image);
        photoImageView.setFitHeight(THUMBNAIL_SIZE);
        photoImageView.setFitWidth(THUMBNAIL_SIZE);
        photoImageView.setPreserveRatio(false);
        photoImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 1);");

        Label caption = new Label(p.getCaption());
        caption.setAlignment(Pos.CENTER);
        caption.setWrapText(true);

        Label filePath = new Label(p.getSrc().toString());
        filePath.setVisible(false);
        filePath.setManaged(false);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(photoImageView, caption, filePath);
        vBox.setPrefHeight(THUMBNAIL_SIZE);
        vBox.setMaxWidth(THUMBNAIL_SIZE);
        vBox.setAlignment(Pos.CENTER);
        vBox.setId(p.getphotoName());
        vBox.setOnMouseClicked(e -> {
            e.consume();
            Object oSource = e.getSource();

            if (oSource instanceof VBox vBoxSource) {
                if (prevThumbnail != null) {
                    prevThumbnail.setOpacity(1.0);
                }
                ImageView imgV = (ImageView) vBoxSource.getChildren().getFirst();
                prevThumbnail = imgV;
                imgV.setOpacity(0.1);
                Image img = imgV.getImage();

                Label fileSrc = (Label) vBoxSource.getChildren().getLast();
                showImage(img, fileSrc.getText());
            }
        });

        flowPane.getChildren().add(vBox);
    }

    public void duplicatePhotoAlert() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Duplicate Image");
        alert.setHeaderText("Image already exists in this album.");
        alert.showAndWait();
    }

    public void back(){
        Photos.getStage().setTitle("Albums");
        Photos.getStage().setScene(albumsScene);
    }

    public void remove(){

    }

    public void hideDisplayArea() {
        if (prevThumbnail != null) {
            prevThumbnail.setOpacity(1.0);
        }
        displayArea.setVisible(false);
        displayArea.setManaged(false);
    }

    public void showImage(Image img, String path) {
        Photo p = currentAlbum.getPhotoByPath(path);

        if (p != null) {
            date.setText(p.getDate().getTime().toString());
            caption.setText(p.getCaption());
            tags.setText(p.printTags());
        }

        displayImageView.setImage(img);
        displayImageView.setPreserveRatio(true);

        displayArea.setVisible(true);
        displayArea.setManaged(true);
    }

}
