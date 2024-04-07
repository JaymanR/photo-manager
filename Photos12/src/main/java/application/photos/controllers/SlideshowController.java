package application.photos.controllers;

import application.photos.model.Album;
import application.photos.model.Photo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SlideshowController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox vBox;
    @FXML
    private Button rightBtn;
    @FXML
    private Button leftBtn;

    private Stage stage;
    private ImageView imgView;

    private Album currentAlbum;
    private int size;
    private int count;

    private Photo[] photos;

    public void start(Stage primaryStage, Album a) throws FileNotFoundException {
        stage = primaryStage;
        currentAlbum = a;
        photos = currentAlbum.getImages().toArray(Photo[]::new);
        size = currentAlbum.getImages().size();
        count = 0;

        imgView = new ImageView();
        leftBtn.setDisable(true);

        imgView.setImage(new Image(new FileInputStream(photos[count].getSrc())));
        vBox.getChildren().add(imgView);
    }
    public void right() throws FileNotFoundException {
        rightBtn.setDisable(count + 1 == size-1);
        leftBtn.setDisable(false);
        imgView.setImage(new Image(new FileInputStream(photos[++count].getSrc())));
    }

    public void left() throws FileNotFoundException {
        leftBtn.setDisable(count - 1 == 0);
        rightBtn.setDisable(false);
        imgView.setImage(new Image(new FileInputStream(photos[--count].getSrc())));
    }

    public void close() {
        stage.close();
    }

}
