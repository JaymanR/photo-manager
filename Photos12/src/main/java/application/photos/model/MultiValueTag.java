package application.photos.model;

import java.util.ArrayList;

public class MultiValueTag extends Tag{
    private ArrayList<String> values;

    public MultiValueTag(String name, ArrayList<String> values) {
        super(name);
        this.values = values;
    }
}
