package application.photos.model;

import java.io.*;
import java.util.*;

public class Photo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private File imgFile;
    private String caption;
    private ArrayList<String> albums;
    private ArrayList<Tag> tags;
    private Calendar date;

    public Photo(File imgSrc) {
        this.imgFile = imgSrc;
        tags = new ArrayList<>();
        albums = new ArrayList<String>();
    }

    public void setDate(){
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND, 0);
    }

    public void editCaption(String caption) {
        this.caption = caption;
    }

    public Calendar getDate(){return date;}

    public void addTag(Tag t) {
        tags.add(t);
    }

    public void addedtoAlbum(String a){albums.add(a);}

    public ArrayList<String> getAlbums() {return albums;}

    public void removeTag() {}

    public File getSrc() {
        return imgFile;
    }

    public String getphotoName() {
        return null;
    }

    public ArrayList<Tag> getTags(){return tags;}

    public Tag getTagByName(String name){
        for(Tag t : tags){
            if(t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }

    public boolean searchTagName(String name){
        for(Tag t : tags){
            if(t.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public String getCaption() {
        return caption;
    }

    public String printTags() {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < tags.size(); i++) {
            Tag t = tags.get(i);
            if (i < tags.size() - 1){
                sBuilder.append(t.getName()).append(": ").append(t.getValue()).append(", ");
            } else {
                sBuilder.append(t.getName()).append(": ").append(t.getValue());
            }
        }
        return sBuilder.toString();
    }
}
