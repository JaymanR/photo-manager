package application.photos.util;

import java.util.*;
import application.photos.model.Photo;

public class SearchByDate {

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