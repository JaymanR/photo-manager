package application.photos12.model;

import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;

public class Photo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private File imgFile;
    private String caption;
    private ArrayList<Album> albums;
    private ArrayList<Tag> tags;
    private Calendar date;

    public Photo(File imgSrc) {
        this.imgFile = imgSrc;
        tags = new ArrayList<>();
    }

    public void setDate(){

//        int year = Integer.parseInt(dateString.substring(6, 10));
//        int month = Integer.parseInt(dateString.substring(0, 1));
//        int day = Integer.parseInt(dateString.substring(3, 4));
//        date.set(year,month,day);
        this.date = Calendar.getInstance();


    }

    public Calendar getDate(){return date;}

    public void addTag(Tag t) {
        tags.add(t);
    }

    public void removeTag() {

    }

    public File getSrc() {
        return imgFile;
    }

    public String getphotoName() {
        return null;
    }

    public ArrayList<Tag> getTags(){return tags;}
}
