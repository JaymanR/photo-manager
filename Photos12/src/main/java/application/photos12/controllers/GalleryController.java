package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.Album;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryController {

    private ArrayList<Album> albums;

    public void start(Stage stage){

    }

    public void createAlbum() {

    }

    public void deleteAlbum() {

    }

    public void close() throws IOException {
        Photos.changeScene(Photos.login);
    }

    public ArrayList<Album> getAlbums() {
       return albums;
    }

    public Album getAlbum(String albumName) {
        Album album = null;
        for (Album a : albums) {
            if (a.getAlbumName().equals(albumName)) {
                album = a;
            }
        }
        return album;
    }
}
