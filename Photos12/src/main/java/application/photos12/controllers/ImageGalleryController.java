package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.Album;
import application.photos12.model.Photo;
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
import java.util.Objects;

public class ImageGalleryController {
    private static final double THUMBNAIL_SIZE = 100;

    private Album currentAlbum;

    @FXML
    private FlowPane flowPane;
    public void start(Album a) {
        currentAlbum = a;
        if (a.getNumPhotos() > 0) {
            for (Photo p : a.getImages()) {
                addImageThumbnail(p);
            }
        }
    }

    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Photos.window);
        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
            Photo photo = new Photo(selectedFile);
            currentAlbum.addImage(photo);
            addImageThumbnail(photo);
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
            if (e.getClickCount() == 2) {
                Object source = e.getSource();
                if (source instanceof VBox sourceBox) {
                    int x = 1;
                }
            }
        });
        flowPane.getChildren().add(photoImageView);
    }

    public void back(){
        // goes back to previous scene
    }

}
