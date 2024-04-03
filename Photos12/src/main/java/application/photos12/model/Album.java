package application.photos12.model;

import java.io.Serial;
import java.io.Serializable;

public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String albumName;

    public Album(String albumName, User owner) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void rename(String albumName) {
        this.albumName = albumName;
    }
}
