package application.photos.model;

import java.io.*;
import java.util.*;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final File rootDir = new File("src/main/resources/application/photos/dat/users");

    private final String name;
    private ArrayList<Album> albums;
    private ArrayList<Photo> pictures;
    private ArrayList<String> singleList;
    private ArrayList<String> multiList;
    private int loginCount;

    public User (String name) {
        this.name = name;
        albums = new ArrayList<>();
        pictures = new ArrayList<>();
        singleList = new ArrayList<>();
        multiList = new ArrayList<>();
        loginCount = 0;
    }

    public String getUserName() {
        return name;
    }

    private void createUserDirectory(String name) {
        File dir = new File(rootDir.getPath() + File.separator + name);
    }

    public void writeUser() throws IOException {
        createUserDirectory(name);
        File storeDir = new File(rootDir.getPath() + File.separator + name);
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + name + ".dat")
        );
        oos.writeObject(this);
    }

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

    public boolean searchAlb(String albName) {
        if (albums == null) return false;
        for (Album a : albums) {
            if (a.getAlbumName().equals(albName)) {
                return true;
            }
        }
        return false;
    }

    public void mkAlb(String albName) {
        Album album = new Album(albName);
        albums.add(album);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void delAlb(Album album) {
        albums.remove(album);
    }

    public ArrayList<Photo> getPictures(){return pictures;}

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public ArrayList<String> getAlbumsAsString() {
        ArrayList<String> aList = new ArrayList<>();
        for (Album a : albums) {
            aList.add(a.getAlbumName());
        }
        return aList;
    }

    public Album getAlbum(String name) {
        for (Album a : albums) {
            if (a.getAlbumName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public Photo searchAllPictures(File src) {
        for (Photo p : pictures) {
            if (p.getSrc().equals(src)) {
                return p;
            }
        }
        return null;
    }

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

