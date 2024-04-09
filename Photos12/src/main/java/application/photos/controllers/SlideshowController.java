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

/**
 * Manages the slideshow view and the events fired
 *
 * @author Jayman Rana, Dev Patel
 */
public class SlideshowController {
    /**
     * BorderPane that holds everything
     */
    @FXML
    private BorderPane borderPane;
    /**
     * Vbox that holds
     */
    @FXML
    private VBox vBox;
    /**
     * Button that moves the slideshow right
     */
    @FXML
    private Button rightBtn;
    /**
     * Button that moves the slideshow left
     */
    @FXML
    private Button leftBtn;

    private Stage stage;
    private ImageView imgView;

    /**
     * Album that is currently open
     */
    private Album currentAlbum;
    private int size;
    private int count;

    /**
     * Array of Photos
     */
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

    /**
     * Displays the immage to the right
     * @throws FileNotFoundException
     */
    public void right() throws FileNotFoundException {
        rightBtn.setDisable(count + 1 == size-1);
        leftBtn.setDisable(false);
        imgView.setImage(new Image(new FileInputStream(photos[++count].getSrc())));
    }

    /**
     * Displays the image to the left
     * @throws FileNotFoundException
     */
    public void left() throws FileNotFoundException {
        leftBtn.setDisable(count - 1 == 0);
        rightBtn.setDisable(false);
        imgView.setImage(new Image(new FileInputStream(photos[--count].getSrc())));
    }

    /**
     * Closes the Slideshow
     */
    public void close() {
        stage.close();
    }

}
