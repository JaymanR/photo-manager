package application.photos12.model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String albumName;
    private ArrayList<Photo> photos;
    private Integer numPhotos;

    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<>();
        numPhotos = 0;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void renameAlbum(String albumName) {
        this.albumName = albumName;
    }

    public void addImage(Photo img) {
        photos.add(img);
        numPhotos++;
    }

    public ArrayList<Photo> getImages(){return photos;}
    public void removeImage(Photo img) {
        photos.remove(img);
        numPhotos--;
    }

    public Integer getNumPhotos() {
        return numPhotos;
    }

}
