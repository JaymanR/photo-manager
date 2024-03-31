package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController {
    @FXML
    private Button logout;
    @FXML
    private Button createUser;
    @FXML
    private Button deleteUser;
    @FXML
    private ListView<String> listView;

    private ObservableList<String> obsList;

    public void logout() throws IOException{
        Photos.changeScene(Photos.login);
    }

    //Note for later : if user already exists give a warning
    public void createUser() throws IOException{
        TextInputDialog dialog = new TextInputDialog("Username");
        dialog.initOwner(Photos.window);
        dialog.setTitle("Create User");
        dialog.setHeaderText("Enter Username");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            if(!obsList.contains(result.get())){
                User user = new User(result.get());
                Photos.createUser(user);
                obsList.add(user.getUserName());
                user.writeUser();
            }
        }

        //fix this so it only adds user if the "ok" button is pressed.
        //Otherwise nothing happens
        System.out.println(Photos.getUsers());
    }

    public void deleteUser() throws IOException{

    }

    public void start(Stage stage){
        List<User> users = Photos.getUsers();
        List<String> userNames = new ArrayList<>();
        for (User u : users) {
            userNames.add(u.getUserName());
        }
        obsList = FXCollections.observableList(userNames);
        listView.setItems(obsList);
    }
}
