package application.photos12.util;

import java.util.*;
import application.photos12.model.Photo;

public class SearchByDate {

    public ArrayList<Photo> searchdate(ArrayList<Photo> pics, Calendar date1, Calendar date2){

        ArrayList<Photo> temp = new ArrayList<Photo>();

        for(Photo p: pics){
            if((p.getDate().after(date1) || p.getDate().equals(date1)) && (p.getDate().before(date2) || p.getDate().equals(date2))){
                temp.add(p);
            }
        }
        return temp;
    }
}