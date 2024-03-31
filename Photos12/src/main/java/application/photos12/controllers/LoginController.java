package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button login;

    @FXML
    private TextField username;

    public void login(ActionEvent e) throws IOException {
        String name = username.getText();
        if (name.equals("admin")) {
            admin();
        }
        username.clear();
    }

    public void admin() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos12/view/admin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        AdminController adminController = loader.getController();
        adminController.start(Photos.getStage());

        Photos.changeScene(scene);
    }

    public void User(String name) throws IOException{

    }
}
