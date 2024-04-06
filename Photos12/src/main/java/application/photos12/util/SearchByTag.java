package application.photos12.util;

import java.util.*;
import application.photos12.model.Photo;
import application.photos12.model.Tag;
import application.photos12.model.User;

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
