package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.Album;
import application.photos12.model.Photo;
import application.photos12.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageGalleryController {
    private static final double THUMBNAIL_SIZE = 100;

    @FXML
    private FlowPane flowPane;
    private Album currentAlbum;
    private Scene albumsScene;
    private User user;
    private ObservableList<Album> albumObservableList;

    public void start(User user, Album a, Scene prevScene, ObservableList<Album> albumObservableList) {
        currentAlbum = a;
        albumsScene = prevScene;
        this.user = user;
        this.albumObservableList = albumObservableList;
        if (a.getNumPhotos() > 0) {
            for (Photo p : a.getImages()) {
                addImageThumbnail(p);
            }
        }

    }

    public void addImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Photos.window);
        if (selectedFile != null) {
            Photo photo = new Photo(selectedFile);
            currentAlbum.addImage(photo);
            user.writeUser();
            addImageThumbnail(photo);

            albumObservableList.set(albumObservableList.indexOf(currentAlbum), currentAlbum);

        }
    }

    public void addImageThumbnail(Photo p) {
        Image image = new Image(p.getSrc().toURI().toString());
        ImageView photoImageView = new ImageView();
        photoImageView.setImage(image);
        photoImageView.setFitHeight(THUMBNAIL_SIZE);
        photoImageView.setFitWidth(THUMBNAIL_SIZE);
        photoImageView.setPreserveRatio(true);

        Label photoName = new Label(p.getphotoName());

        VBox vBox = new VBox();
        vBox.getChildren().addAll(photoImageView, photoName);
        vBox.setStyle("-fx-padding: 25px;");
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

    public void back(){
        Photos.getStage().setTitle("Albums");
        Photos.getStage().setScene(albumsScene);
    }

    public void remove(){

    }

}
