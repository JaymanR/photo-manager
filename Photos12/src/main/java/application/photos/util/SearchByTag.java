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

    public static ArrayList<Photo> searchByCombination(Tag tag1, Tag tag2, boolean bool, User user) {
        ArrayList<Photo> hitList = new ArrayList<>();
        //if true = OR combination
        if (bool) {
            //or search
            for (Photo p : user.getPictures()) {
                    if (p.containsPropertiesOf(tag1) || p.containsPropertiesOf(tag2)) {
                        if (!hitList.contains(p)) {
                            hitList.add(p);
                        }
                    }
            }

        } else {
            //AND
            for (Photo p : user.getPictures()) {
                    if (p.containsPropertiesOf(tag1) && p.containsPropertiesOf(tag2)) {
                        if (!hitList.contains(p)) {
                            hitList.add(p);
                        }
                    }
            }
        }
        return hitList;
    }
}
