package application.photos.util;

import java.util.*;
import application.photos.model.Photo;
import application.photos.model.Tag;
import application.photos.model.User;

public class SearchByTag {

    private ArrayList<Photo> temp;
    public ArrayList<Photo> searchtag(User user, ArrayList<Tag> tags){

        for(Photo p: user.getPictures()){
            for(Tag t: tags){
                if(p.getTags().contains(t)){
                    temp.add(p);
                }
            }
        }

        return temp;
    }
}
