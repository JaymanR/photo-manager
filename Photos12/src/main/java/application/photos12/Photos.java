package application.photos12;

import application.photos12.model.User;
import application.photos12.model.Photo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class Photos extends Application implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static Stage window;
    public static Scene login;

    private static final String storeDir = "src/main/resources/application/photos12/dat/users";

    private static List<User> users;

    public static void addUser(User u) {
        users.add(u);
    }

    public static void removeUser(String uName) {
        users.removeIf(u -> u.getUserName().equals(uName));
    }

    public static List<User> getUsers() {
        return users;
    }

    public static Stage getStage() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
        users = User.readUsers();
        startUp(primaryStage);
    }
    public static void changeScene(Scene scene) {
        window.setScene(scene);
        window.show();
    }

    public void startUp(Stage primaryStage) throws IOException{
        window = primaryStage;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos12/view/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        login = scene;

        window.setScene(scene);
        window.setTitle("Photos");
        window.setResizable(false);
        window.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}
