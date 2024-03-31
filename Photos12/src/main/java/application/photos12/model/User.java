package application.photos12.model;

import java.io.*;
import java.util.*;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final File storeDir = new File("src/main/resources/application/photos12/dat/users");

    private String name;

    public User (String name) {
        this.name = name;
    }

    public String getUserName() {
        return name;
    }

    public void writeUser() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + name + ".dat")
        );
        oos.writeObject(this);
        System.out.println("successfully stored user data");
    }

    public static ArrayList<User> readUsers() throws IOException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        File[] filesList = storeDir.listFiles();
        if (filesList == null) {
            System.out.println("No user data found");
            return null;
        }
        for (File f : filesList) {
            System.out.println(storeDir + File.separator + f.getName());
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(storeDir + File.separator + f.getName())
            );

            User user = (User) ois.readObject();
            users.add(user);
        }
        System.out.println("successfully extracted user data");
        return users;
    }
}
