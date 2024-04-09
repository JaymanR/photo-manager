package application.photos.util;

import java.util.*;
import application.photos.model.Photo;
import application.photos.model.Tag;
import application.photos.model.User;

/**
 * This class is a utility Class that provides a search feature based on Tags.
 *
 * @author Dev Patel
 */
public class SearchByTag {

    /**
     * Searches All user photo for the Name : Value pair and returns all photos that contain said pair.
     * @param searchTag Tag to search for
     * @param user User instance
     * @return ArrayList of Photo
     */
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

    /**
     * Searches all user photo for disjunction/conjunction of Two tags
     * @param tag1 Tag instance
     * @param tag2 Tag instance
     * @param bool Conjunction ( True ) or Disjunction ( False)
     * @param user User instance
     * @return ArrayList of Photo
     */
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
