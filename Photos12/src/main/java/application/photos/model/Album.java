package application.photos.model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String albumName;
    private ArrayList<Photo> photos;
    private Integer numPhotos;
    private String lowestD;
    private String highestD;

    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<>();
        numPhotos = 0;
    }

    public void getLowestDate() {
        Calendar lowest = photos.getFirst().getDate();
        for(Photo p: photos){
            if(p.getDate().before(lowest)){
                lowest = p.getDate();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        lowestD = formatter.format(lowest.getTime());
    }

    public void getHighestDate(){
        Calendar highest = photos.getFirst().getDate();
        for(Photo p: photos){
            if(p.getDate().after(highest)){
                highest = p.getDate();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        highestD = formatter.format(highest.getTime());
    }
    public String getAlbumName() {
        return albumName;
    }

    public void renameAlbum(String albumName) {
        this.albumName = albumName;
    }

    public void addImage(Photo img) {
        img.setDate();
        photos.add(img);
        numPhotos++;
    }

    public Photo getPhotoByPath(String path) {
        for (Photo p : photos) {
            if (p.getSrc().toString().equals(path)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Photo> getImages(){return photos;}
    public void removeImage(Photo img) {
        photos.remove(img);
        numPhotos--;
    }

    public Integer getNumPhotos() {
        return numPhotos;
    }

    public boolean searchImage(File src) {
        for (Photo p : photos) {
            if (p.getSrc().equals(src)) {
                return true;
            }
        }
        return false;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
        numPhotos = photos.size();
    }

    public String getLowestD() {
        return lowestD;
    }

    public String getHighestD() {
        return highestD;
    }
}
