package application.photos.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String value;
    private ArrayList<Photo> photos;
    private boolean singleValue;

    public Tag(String name, String value, boolean singleValue) {
        this.singleValue = singleValue;
        this.name = name;
        this.value = value;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Tag other)) {return false;}
        return name.equals(other.getName()) && value.equals(other.getValue());
    }

    public boolean equals(Tag t) {
        if (t == null) {return false;}
        return name.equals(t.getName()) && value.equals(t.getValue());
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public boolean isSingleValue() {
        return singleValue;
    }
}
