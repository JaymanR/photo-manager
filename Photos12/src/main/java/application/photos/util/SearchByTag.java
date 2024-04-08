package application.photos.util;

import java.util.*;
import application.photos.model.Photo;
import application.photos.model.Tag;
import application.photos.model.User;

public class SearchByTag {

    private ArrayList<Photo> temp;

    public static ArrayList<Photo> singleTagSearch(Tag searchTag, User user) {
        ArrayList<Photo> hitList = new ArrayList<>();
        for (Photo p : user.getPictures()) {
            for (Tag t : p.getTags()) {
                if (t.equals(searchTag)) {
                    if (!hitList.contains(p)) {
                        hitList.add(p);
                    }
                }
            }
        }
        return hitList;
    }

}
