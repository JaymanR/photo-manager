package application.photos.controllers;

import application.photos.Photos;
import application.photos.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private Label warning;

    public void login() throws IOException {
        String name = username.getText().toLowerCase();

        if(name.isBlank()) {
            warning.setText("Please Enter a Username!");
        }else{
            if (name.equals("admin")) {
                admin();
                warning.setText("");
            } else {
                User user = null;
                for (User u : Photos.getUsers()) {
                    if (u.getUserName().equals(name)) {
                        user(name, u);
                        user = u;
                        warning.setText("");
                        break;
                    }
                }
                if (user == null) {
                    warning.setText("User does not exist!");
                }
            }
        }
        username.clear();
    }

    public void admin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos/view/admin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        AdminController adminController = loader.getController();
        adminController.start(Photos.getStage());

        Photos.changeScene(scene);
    }

    public void user(String name, User user) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/application/photos/view/album.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            AlbumController albumController = loader.getController();
            albumController.start(Photos.getStage(), user);

            Photos.getStage().setTitle("Albums");
            Photos.changeScene(scene);
            Photos.window.setResizable(true);
    }
}
