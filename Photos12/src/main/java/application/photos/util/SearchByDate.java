package application.photos.util;

import java.util.*;
import application.photos.model.Photo;

/**
 * This class is a utility Class that provides a search feature based on dates.
 *
 * @author Dev Patel
 */
public class SearchByDate {
    /**
     * Searches
     * @param pics
     * @param date1
     * @param date2
     * @return
     */
    public static ArrayList<Photo> searchdate(ArrayList<Photo> pics, Calendar date1, Calendar date2){

        ArrayList<Photo> temp = new ArrayList<>();

        for(Photo p: pics){
            if((p.getDate().getTime().after(date1.getTime()) || p.getDate().getTime().equals(date1.getTime())) && (p.getDate().getTime().before(date2.getTime()) || p.getDate().getTime().equals(date2.getTime()))){
                temp.add(p);
            }
        }
        return temp;
    }
}