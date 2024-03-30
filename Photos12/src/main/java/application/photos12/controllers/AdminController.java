package application.photos12.controllers;

import application.photos12.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AdminController {
    @FXML
    private Button logout;
    @FXML
    private Button createUser;
    @FXML
    private Button deleteUser;

    public void logout() throws IOException{
        Photos.changeScene(Photos.login);
    }

    public void createUser() throws IOException{

    }

    public void deleteUser() throws IOException{

    }
}
