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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.*;

public class ImageGalleryController {
    private static final double THUMBNAIL_SIZE = 100;

    @FXML
    private FlowPane flowPane;
    @FXML
    private VBox displayArea;
    private Album currentAlbum;
    private Scene albumsScene;
    private User user;
    private ObservableList<Album> albumObservableList;

    public void start(User user, Album a, Scene prevScene, ObservableList<Album> albumObservableList) throws FileNotFoundException {
        currentAlbum = a;
        albumsScene = prevScene;
        this.user = user;
        this.albumObservableList = albumObservableList;
        if (a.getNumPhotos() > 0) {
            for (Photo p : a.getImages()) {
                addImageThumbnail(p);
                System.out.println(p.getDate());
            }
        }
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

    public void addImageThumbnail(Photo p) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(p.getSrc()));
        ImageView photoImageView = new ImageView();

        photoImageView.setImage(image);
        photoImageView.setFitHeight(THUMBNAIL_SIZE);
        photoImageView.setFitWidth(THUMBNAIL_SIZE);
        photoImageView.setPreserveRatio(false);
        photoImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 1);");

        Label photoName = new Label(p.getphotoName());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(photoImageView, photoName);
        vBox.setPrefHeight(THUMBNAIL_SIZE + photoName.prefHeight(-1));
        vBox.setMaxWidth(THUMBNAIL_SIZE);
        vBox.setStyle("-fx-padding: 5px; -fx-border-color: 1px; -fx-border-width: 1px; -fx-border-radius: 5px;");
        vBox.setAlignment(Pos.CENTER);
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        vBox.setId(p.getphotoName());

        vBox.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Object source = e.getSource();
                if (source instanceof VBox sourceBox) {
                    int x = 1;
                }
            }
        });
        flowPane.getChildren().add(photoImageView);
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
        displayArea.setVisible(false);
        displayArea.setManaged(false);
    }

    public void showImage() {

        displayArea.setVisible(true);
        displayArea.setManaged(true);
    }

}
