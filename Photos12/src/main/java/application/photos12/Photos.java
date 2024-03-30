package application.photos12;

import application.photos12.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.*;

import java.io.IOException;

public class Photos extends Application implements Serializable {

    private static Stage window;
    public static Scene login;

    private ArrayList<User> users;
    public static final String storeDir = "/application/photos12/dat";
    public static final String storeFile = "photos.dat";

    public Photos () {
        users = new ArrayList<User>();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
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


}
