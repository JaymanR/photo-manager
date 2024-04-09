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

/**
 * Manages the login view and the event fired
 */
public class LoginController {
    /**
     * Button used to log in
     */
    @FXML
    private Button login;
    /**
     * Textfield where you enter a username
     */
    @FXML
    private TextField username;
    /**
     * Label for the warning text
     */
    @FXML
    private Label warning;

    /**
     * Logs into user based on the name inputted
     * @throws IOException
     */
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

    /**
     * Loads the admin view from the fxml file and displays it
     * @throws IOException
     */
    public void admin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos/view/admin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        AdminController adminController = loader.getController();
        adminController.start(Photos.getStage());

        Photos.changeScene(scene);
    }

    /**
     * Loads
     * @param name Name of the user
     * @param user User instance of the user
     * @throws IOException
     */
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
