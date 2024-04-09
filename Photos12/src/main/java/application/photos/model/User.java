package application.photos.model;

import java.io.*;
import java.util.*;

/**
 * This class represents the User. It stores the albums the user creates and all the pictures the user has added.
 * The User class implements Serializable to serialize the user object for persistence.
 *
 * @author Jayman Rana, Dev Patel
 */
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Directory where the user data is stored
     */
    private static final File rootDir = new File("src/main/resources/application/photos/dat/users");

    /**
     * Name of the user.
     */
    private final String name;

    /**
     * All the albums the user created
     */
    private final ArrayList<Album> albums;

    /**
     * All photos user has added.
     */
    private final ArrayList<Photo> pictures;
    private final ArrayList<String> singleList;
    private final ArrayList<String> multiList;
    private int loginCount;

    /**
     * constructs the User.
     * @param name name of the user.
     */
    public User (String name) {
        this.name = name;
        albums = new ArrayList<>();
        pictures = new ArrayList<>();
        singleList = new ArrayList<>();
        multiList = new ArrayList<>();
        loginCount = 0;
    }

    /**
     *
     * @return name of the user.
     */
    public String getUserName() {
        return name;
    }

    /**
     * Helper method that creates a directory for the user to store data.
     * @param name name of the user.
     */
    private void createUserDirectory(String name) {
        File dir = new File(rootDir.getPath() + File.separator + name);
        dir.mkdir();
    }

    /**
     * Stores this instance fo the User into a .dat file inside the user directory.
     * @throws IOException
     */
    public void writeUser() throws IOException {
        createUserDirectory(name);
        File storeDir = new File(rootDir.getPath() + File.separator + name);
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + name + ".dat")
        );
        oos.writeObject(this);
    }

    /**
     * Reads all user data in the data storage directory and deserializes it.
     * @return Arraylist of users.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ArrayList<User> readUsers() throws IOException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        String[] dirList = rootDir.list();

        if (dirList == null) {
            return null;
        }
        for (String d : dirList) {
            File storeDir = new File(rootDir.getPath() + File.separator + d);
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(storeDir + File.separator + d + ".dat")
            );

            User user = (User) ois.readObject();
            users.add(user);
        }
        return users;
    }

    /**
     * Deletes the data of a user.
     * @param dirName name of the user directory.
     */
    public static void clearUser(String dirName) {
        File userDir = new File(rootDir.getPath() + File.separator + dirName);
        File[] fileList = userDir.listFiles();

        if (fileList != null) {
            for (File f : userDir.listFiles()) {
                f.delete();
            }
        }
        userDir.delete();
    }

    /**
     * Searches an Album based on its name.
     * @param albName Name of the album.
     * @return true if album is found, false otherwise.
     */
    public boolean searchAlb(String albName) {
        if (albums == null) return false;
        for (Album a : albums) {
            if (a.getAlbumName().equals(albName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates an Album.
     * @param albName Name of the album to be created.
     */
    public void mkAlb(String albName) {
        Album album = new Album(albName);
        albums.add(album);
    }

    /**
     * adds the provided album to the album list.
     * @param album Album instance.
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * Deletes the specified Album.
     * @param album Album instance.
     */
    public void delAlb(Album album) {
        albums.remove(album);
    }

    /**
     *
     * @return Arraylist of photos.
     */
    public ArrayList<Photo> getPictures(){return pictures;}

    /**
     *
     * @return Arraylist of albums.
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     *
     * @return Arraylist of Album Names.
     */
    public ArrayList<String> getAlbumsAsString() {
        ArrayList<String> aList = new ArrayList<>();
        for (Album a : albums) {
            aList.add(a.getAlbumName());
        }
        return aList;
    }

    /**
     * Gets the specified album based on name
     * @param name name of the album
     * @return Album instance
     */
    public Album getAlbum(String name) {
        for (Album a : albums) {
            if (a.getAlbumName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Searches all user picture for the specified file
     * @param src File instance of the Photo
     * @return The Photo if found, null if not found.
     */
    public Photo searchAllPictures(File src) {
        for (Photo p : pictures) {
            if (p.getSrc().equals(src)) {
                return p;
            }
        }
        return null;
    }

    /**
     * adds the specified photo to universal photo list.
     * @param p Photo instance
     */
    public void addImageToUser(Photo p) {
        pictures.add(p);
    }

    public ArrayList<String> getSingleList() {
        return singleList;
    }

    public ArrayList<String> getMultiList() {return multiList;}

    public void addSingleTag(String tag){if(!singleList.contains(tag)){singleList.add(tag);}}

    public void addMultiTag(String tag){if(!multiList.contains(tag)){multiList.add(tag);}}

    public int getLoginCount() {
        return loginCount;
    }

    public void incrementLoginCount() {
        loginCount++;
    }
}

