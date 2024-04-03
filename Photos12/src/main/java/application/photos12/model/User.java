package application.photos12.model;

import java.io.*;
import java.util.*;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final File rootDir = new File("src/main/resources/application/photos12/dat/users");

    private final String name;

    public User (String name) {
        this.name = name;
    }

    public String getUserName() {
        return name;
    }

    public void createUserDirectory(String name) {
        File dir = new File(rootDir.getPath() + File.separator + name);
        if (dir.mkdir()) {
            System.out.println("Successfully created user directory for" + name);
        }
    }

    public void writeUser() throws IOException {
        createUserDirectory(name);
        File storeDir = new File(rootDir.getPath() + File.separator + name);
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + name + ".dat")
        );
        oos.writeObject(this);
        System.out.println("successfully stored user data");
    }

    public static ArrayList<User> readUsers() throws IOException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        String[] dirList = rootDir.list();

        if (dirList == null) {
            System.out.println("No user data found");
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
        System.out.println("successfully extracted user data");
        return users;
    }

    public static void clearUser(String dirName) {
        File userDir = new File(rootDir.getPath() + File.separator + dirName);
        File[] fileList = userDir.listFiles();

        if (fileList != null) {
            for (File f : userDir.listFiles()) {
                if (f.delete()) {
                    System.out.println("Successfully deleted user file");
                }
            }
        }

        if (userDir.delete()) {
            System.out.println("successfully deleted: " + dirName);
        }
    }
}
