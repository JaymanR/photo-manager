package application.photos.controllers;

import application.photos.Photos;
import application.photos.model.Album;
import application.photos.model.Photo;
import application.photos.model.Tag;
import application.photos.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static application.photos.controllers.AlbumController.checkIfDuplicate;

public class ImageGalleryController {
    private static final double THUMBNAIL_SIZE = 100;

    @FXML
    private FlowPane flowPane;
    @FXML
    private VBox addPhotoBox;
    @FXML
    private VBox albumTools;
    @FXML
    private VBox displayArea;
    @FXML
    private Button createAlbumBtn;
    @FXML
    private ImageView displayImageView;
    @FXML
    private Button slideshow;
    @FXML
    private ChoiceBox<String> copyToCB;
    @FXML
    private ChoiceBox<String> moveToCB;
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

    private VBox currentThumbnailContainer;
    private ImageView prevThumbnail;
    private Photo selectedPhoto;

    public void start(User user, Album a, Scene prevScene, ObservableList<Album> albumObservableList, boolean bool) throws FileNotFoundException {
        currentAlbum = a;
        albumsScene = prevScene;
        this.user = user;
        this.albumObservableList = albumObservableList;
        if (!bool) {
            initializeImages();
            ObservableList<String> albumStrObsList = FXCollections.observableArrayList();
            albumStrObsList.addAll(user.getAlbumsAsString());
            copyToCB.setItems(albumStrObsList);
            moveToCB.setItems(albumStrObsList);
            createAlbumBtn.setVisible(false);
            createAlbumBtn.setManaged(false);
            searchView(true);
        } else {
            initializeImages();
            searchView(false);
        }
    }

    public void searchView(boolean bool) {
        addPhotoBox.setVisible(bool);
        addPhotoBox.setManaged(bool);
        albumTools.setVisible(bool);
        albumTools.setManaged(bool);
        slideshow.setVisible(bool);
        if (!bool){
            flowPane.setStyle("-fx-background-color:  #e4e9ed");
        } else {
            flowPane.setStyle("-fx-background-color:  #ffff");
        }
    }

    public void initializeImages() throws FileNotFoundException {
        if (currentAlbum.getNumPhotos() > 0) {
            for (Photo p : currentAlbum.getImages()) {
                addImageThumbnail(p);
            }
        }
        hideDisplayArea();
    }

    public void createAlbum() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(Photos.window);
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter AlbumName");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() && !user.searchAlb(result.get().toLowerCase())){
            if(result.get().isBlank()){
                Alert alert = new Alert((Alert.AlertType.INFORMATION));
                alert.initOwner(Photos.window);
                alert.setTitle("No Album Name");
                alert.setHeaderText("Album name not provided.");
                alert.setContentText("Please enter a name for the Album.");
                alert.showAndWait();
            }
            currentAlbum.renameAlbum(result.get().toLowerCase());
        }else {
            checkIfDuplicate(result, user);
        }

        user.addAlbum(currentAlbum);
        albumObservableList.add(currentAlbum);
        user.writeUser();
        flowPane.getChildren().clear();
        start(user, currentAlbum, albumsScene, albumObservableList, false);
    }



    public void addImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.gif", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(Photos.window);
        if (selectedFile != null) {
            Photo photo = user.searchAllPictures(selectedFile);

            if (currentAlbum.searchImage(selectedFile)) {
                duplicatePhotoAlert();
                return;
            }

            if (photo != null) {
                currentAlbum.addImage(photo);
                photo.addedtoAlbum(currentAlbum.getAlbumName());
            } else {
                photo = new Photo(selectedFile);
                user.addImageToUser(photo);
                currentAlbum.addImage(photo);
                photo.addedtoAlbum(currentAlbum.getAlbumName());
            }
            user.writeUser();
            addImageThumbnail(photo);

            albumObservableList.set(albumObservableList.indexOf(currentAlbum), currentAlbum);
        }
    }

    public void copyPhoto() throws IOException {
        String selection = copyToCB.getSelectionModel().getSelectedItem();
        Album targetAlbum = user.getAlbum(selection);
        if (targetAlbum.searchImage(selectedPhoto.getSrc())) {
            duplicatePhotoAlert();
        } else {
            targetAlbum.addImage(selectedPhoto);
            albumObservableList.set(albumObservableList.indexOf(targetAlbum), targetAlbum);
            user.writeUser();
        }
    }

    public void movePhoto() throws IOException {
        String selection = moveToCB.getSelectionModel().getSelectedItem();
        Album targetAlbum = user.getAlbum(selection);
        if (targetAlbum.searchImage(selectedPhoto.getSrc())) {
            duplicatePhotoAlert();
        } else {
            targetAlbum.addImage(selectedPhoto);
            albumObservableList.set(albumObservableList.indexOf(targetAlbum), targetAlbum);
            currentAlbum.removeImage(selectedPhoto);
            albumObservableList.set(albumObservableList.indexOf(currentAlbum), currentAlbum);
            flowPane.getChildren().clear();
            initializeImages();
            user.writeUser();
        }
    }

    public void addImageThumbnail(Photo p) throws FileNotFoundException {
        Image image = new Image((p.getSrc().toURI().toString()));
        ImageView photoImageView = new ImageView();

        photoImageView.setImage(image);
        photoImageView.setFitHeight(THUMBNAIL_SIZE);
        photoImageView.setFitWidth(THUMBNAIL_SIZE);
        photoImageView.setPreserveRatio(false);
        //photoImageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 1);");

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

                Label fileSrc = (Label) vBoxSource.getChildren().getLast();
                String photoPath = fileSrc.getText();
                selectedPhoto = currentAlbum.getPhotoByPath(photoPath);
                currentThumbnailContainer = vBoxSource;
                try {
                    showImage();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
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

    public void deletePhoto() throws IOException {
        currentAlbum.removeImage(selectedPhoto);
        albumObservableList.set(albumObservableList.indexOf(currentAlbum), currentAlbum);
        selectedPhoto.getAlbums().remove(currentAlbum.getAlbumName());
        
        for (Album a : user.getAlbums()) {
            for (Photo p : a.getImages()) {
                if (p.getSrc().toString().equalsIgnoreCase(selectedPhoto.toString())) {
                    return;
                }
            }
        }

        if(selectedPhoto.getAlbums().isEmpty()){
            user.getPictures().remove(selectedPhoto);
        }

        flowPane.getChildren().clear();
        initializeImages();
        user.writeUser();
    }

    public String giveCaptionDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(Photos.window);
        dialog.setTitle("Image Caption");
        dialog.setHeaderText("Please input caption for this image");
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public void editCaption() throws IOException {
        String caption = giveCaptionDialog();
       if (caption != null) {
            selectedPhoto.editCaption(caption);
            Label label = (Label) currentThumbnailContainer.getChildren().get(1);
            label.setText(caption);
            showImage();
            user.writeUser();
        }
    }

    public void manageTags() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos/view/tag-manager.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initOwner(Photos.window);
        stage.setScene(scene);
        stage.setResizable(false);

        TagManagerController tagManagerController = loader.getController();
        tagManagerController.start(stage, user, selectedPhoto, this);
        stage.show();
    }

    public void slideshow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/application/photos/view/slideshow.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initOwner(Photos.window);
        stage.setScene(scene);
        stage.setFullScreen(true);

        SlideshowController slideshowController = fxmlLoader.getController();
        slideshowController.start(stage, currentAlbum);
        stage.show();
    }

    public void hideDisplayArea() {
        if (prevThumbnail != null) {
            prevThumbnail.setOpacity(1.0);
        }
        displayArea.setVisible(false);
        displayArea.setManaged(false);
        selectedPhoto = null;
    }

    public void showImage() throws FileNotFoundException {

        if (selectedPhoto != null) {
            date.setText(selectedPhoto.getDate().getTime().toString());
            caption.setText(selectedPhoto.getCaption());
            tags.setText(selectedPhoto.printTags());
            displayImageView.setImage(new Image(new FileInputStream(selectedPhoto.getSrc())));
        }


        displayImageView.setPreserveRatio(true);

        displayArea.setVisible(true);
        displayArea.setManaged(true);
    }

}
