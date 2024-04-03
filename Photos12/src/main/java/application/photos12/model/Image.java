package application.photos12.model;

import java.io.*;
import java.util.*;

public class Image implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private File imgSrc;
    private ArrayList<Album> albums;
    private ArrayList<String> tags;
    private Calendar date;

    public Image(File imgSrc) {
        this.imgSrc = imgSrc;
        tags = new ArrayList<>();
    }

    public void addTag(String tagName) {
        tags.add(tagName);
    }
}
