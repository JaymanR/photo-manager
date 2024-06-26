package application.photos.controllers;

import application.photos.Photos;
import application.photos.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

/**
 * Manages the admin view and the events fired in admin view
 *
 * @author Jayman Rana and Dev Patel
 */
public class AdminController {
    /**
     * Button to delete a User
     */
    @FXML
    private Button deleteUser;
    /**
     * List of users
     */
    @FXML
    private ListView<String> listView;
    /**
     * Observable List of users
     */
    private ObservableList<String> obsList;

    /**
     * Logs the user out
     * @throws IOException throw exception
     */
    public void logout() throws IOException {
        Photos.changeScene(Photos.login);
    }

    /**
     * Creates a new user
     * @throws IOException throws exception
     */
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

    /**
     * Deletes a selected User
     * @throws IOException throws Exception
     */
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
