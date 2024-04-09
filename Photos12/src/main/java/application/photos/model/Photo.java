package application.photos.model;

import java.io.*;
import java.util.*;

/**
 * this class represents a Photo. It contains the image File, its caption, the tags it has and the albums it is part of.
 * @author Jayman Rana
 */
public class Photo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The image file.
     */
    private final File imgFile;

    /**
     * The caption of the image.
     */
    private String caption;

    /**
     * The albums the Photo is part of.
     */
    private final ArrayList<String> albums;

    /**
     * All the tags that are added to the Photo.
     */
    private final ArrayList<Tag> tags;

    /**
     * The date the Photo was created.
     */
    private Calendar date;

    /**
     * constructor for Photo.
     * @param imgSrc File instance of the image.
     */
    public Photo(File imgSrc) {
        this.imgFile = imgSrc;
        tags = new ArrayList<>();
        albums = new ArrayList<String>();
    }

    /**
     * Sets the date of this Photo instance.
     */
    public void setDate(){
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Edits the caption of this Photo.
     * @param caption String value to change the caption to.
     */
    public void editCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Getter for the date of the Photo.
     * @return Calendar instance.
     */
    public Calendar getDate(){return date;}

    /**
     * Adds the tag to the Tag ArrayList of this instance.
     * @param t Tag instance to add to the Photo.
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     *Adds the album name to the albums String ArrayList.
     * @param a Album name in String form.
     */
    public void addedtoAlbum(String a){albums.add(a);}

    /**
     * Getter for album String Arraylist
     * @return ArrayList<String> of album name.
     */
    public ArrayList<String> getAlbums() {return albums;}

    /**
     * Removes the Tag from the Photo.
     * @param tag Tag instance to be removed.
     */
    public void removeTag(Tag tag) {tags.remove(tag);}

    /**
     * @return File instance of the Photo.
     */
    public File getSrc() {
        return imgFile;
    }

    /**
     *
     * @return ArrayList of Tag instances.
     */
    public ArrayList<Tag> getTags(){return tags;}

    /**
     * Searches a tag based on its name property.
     * @param name String value to search for
     * @return true if a Tag with the same name is found, otherwise returns false.
     */
    public boolean searchTagName(String name){
        for(Tag t : tags){
            if(t.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return caption of the Photo in String form.
     */
    public String getCaption() {
        return caption;
    }

    /**
     *Builds a String based on all the Tags the Photo contains and appends all its values together.
     * @return A String with all the Tag name : value seperated by commas.
     */
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

    /**
     * Searches the TagList of this Photo instance based on its properties.
     * @param tag Tag instance that contains the properties to search for.
     * @return true if a Tag contains the same properties, false if not found.
     */
    public boolean containsPropertiesOf(Tag tag) {
        for (Tag t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }
        return false;
    }
}
