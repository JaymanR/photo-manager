package application.photos12.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

}
