package application.photos.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class Represents a Tag that can be added to a Photo. Contains the name and value of the Tag.
 * @author Jayman Rana, Dev Patel
 */
public class Tag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the tag.
     */
    private final String name;

    /**
     * Value of the tag.
     */
    private String value;

    private final boolean singleValue;

    /**
     * Creates a Tag instance.
     * @param name name of the tag.
     * @param value value of the tag.
     * @param singleValue boolean
     */
    public Tag(String name, String value, boolean singleValue) {
        this.singleValue = singleValue;
        this.name = name;
        this.value = value;
    }

    /**
     * Compares an object to check if it is a Tag with the same value.
     * @param o Object to test.
     * @return true if the object is a Tag and has the same properties, false otherwise.
     */
    public boolean equals(Object o) {
        if (!(o instanceof Tag other)) {return false;}
        return name.equals(other.getName()) && value.equals(other.getValue());
    }

    /**
     * Compares the Tag with the provided tag to test if the properties are the same.
     * @param t Tag to test with.
     * @return true if the properties match, false otherwise.
     */
    public boolean equals(Tag t) {
        if (t == null) {return false;}
        return name.equals(t.getName()) && value.equals(t.getValue());
    }

    /**
     *
     * @return the name of the Tag.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the value of the tag.
     */
    public String getValue() {
        return value;
    }

}
