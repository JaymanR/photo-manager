package application.photos12.controllers;

import application.photos12.Photos;
import application.photos12.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.lang.module.ModuleDescriptor;
import java.util.*;

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
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(Photos.window);
        dialog.setTitle("Create User");
        dialog.setHeaderText("Enter Username");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !obsList.contains(result.get().toLowerCase())) {
            User user = new User(result.get().toLowerCase());
            Photos.addUser(user);
            obsList.add(user.getUserName());
            user.writeUser();
        }
    }


    public void deleteUser() throws IOException {
        String userName = listView.getSelectionModel().getSelectedItem();
        obsList.remove(userName);
        Photos.removeUser(userName);
        User.clearUser(userName);
    }

    public void start(Stage stage){
        List<User> users = Photos.getUsers();
        List<String> userNames = new ArrayList<>();
        for (User u : users) {
            userNames.add(u.getUserName());
        }
        obsList = FXCollections.observableList(userNames);
        listView.setItems(obsList);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                deleteUser.setDisable(false);
            }
        });
    }
}
