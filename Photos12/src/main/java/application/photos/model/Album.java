package application.photos.model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class represents an Album. It stores images, and statistics assigned to the Album and provides methods to retrieve them.
 *
 * @author Jayman Rana and Dev Patel
 */
public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the album.
     */
    private String albumName;

    /**
     * All photos in the album.
     */
    private ArrayList<Photo> photos;

    /**
     * The number of photos in the album.
     */
    private Integer numPhotos;

    /**
     * earliest photo in the album.
     */
    private String lowestD;

    /**
     * latest photo in the album.
     */
    private String highestD;

    /**
     * contructor creating the album.
     * @param albumName name of the album.
     */
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<>();
        numPhotos = 0;
        lowestD = "N/A";
        highestD = "N/A";
    }

    /**
     * sets the lowestD field of this instance.
     */
    public void getLowestDate() {
        if (lowestD.equals("N/A")) {return;}
        Calendar lowest = photos.getFirst().getDate();
        for(Photo p: photos){
            if(p.getDate().before(lowest)){
                lowest = p.getDate();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        lowestD = formatter.format(lowest.getTime());
    }

    /**
     * sets the highestD field of this instance.
     */
    public void getHighestDate(){
        if (highestD.equals("N/A")) {return;}
        Calendar highest = photos.getFirst().getDate();
        for(Photo p: photos){
            if(p.getDate().after(highest)){
                highest = p.getDate();
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        highestD = formatter.format(highest.getTime());
    }

    /**
     * getter for albumName.
     * @return name of the album.
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * renames the album.
     * @param albumName the name to rename the album to.
     */
    public void renameAlbum(String albumName) {
        this.albumName = albumName;
    }

    /**
     * adds the image
     * @param img image to be added.
     */
    public void addImage(Photo img) {
        img.setDate();
        photos.add(img);
        numPhotos++;
    }

    /**
     * searches the photos ArrayList by pathName and return the Photo instance, returns null if not found.
     * @param path pathname to search.
     * @return a photo instance or null if not found.
     */
    public Photo getPhotoByPath(String path) {
        for (Photo p : photos) {
            if (p.getSrc().toString().equals(path)) {
                return p;
            }
        }
        return null;
    }

    /**
     * getter method for photos field.
     * @return an ArrayList of photos of the instance.
     */
    public ArrayList<Photo> getImages(){return photos;}
    public void removeImage(Photo img) {
        photos.remove(img);
        numPhotos--;
    }

    /**
     *Get the number of photos in this instance.
     * @return an Integer.
     */
    public Integer getNumPhotos() {
        return numPhotos;
    }

    /**
     * Checks if a Photo with the same file is found.
     * @param src 
     * @return
     */
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
