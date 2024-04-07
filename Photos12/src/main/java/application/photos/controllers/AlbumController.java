package application.photos.controllers;

import application.photos.Photos;
import application.photos.util.*;
import application.photos.model.Album;
import application.photos.model.Photo;
import application.photos.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AlbumController {
    @FXML
    private BorderPane borderPane;
    private TableView<Album> table;
    private User user;
    private ObservableList<Album> albumObsList;

    @FXML
    private TextField firstdate;
    @FXML
    private TextField lastdate;
    @FXML
    private Label warning;

    public void start(Stage stage, User user){
        this.user = user;
        initializeAlbums();
    }

    private void initializeAlbums() {
        albumObsList = FXCollections.observableArrayList();
        albumObsList.addAll(user.getAlbums());

        TableColumn<Album, String> nameColumn = new TableColumn<>("Album");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));

        TableColumn<Album, Integer> photoCountColumn = new TableColumn<>("Photo Count");
        photoCountColumn.setCellValueFactory(new PropertyValueFactory<>("numPhotos"));

        table = new TableView<>();
        table.setItems(albumObsList);
        table.getColumns().addAll(nameColumn, photoCountColumn);

        borderPane.setCenter(table);
    }

    public void openAlbum() throws IOException {
        Album album = table.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos/view/image-gallery.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        ImageGalleryController imageGalleryController = loader.getController();
        imageGalleryController.start(user, album, Photos.getStage().getScene(), albumObsList, false);
        Photos.getStage().setTitle(album.getAlbumName());
        Photos.getStage().setScene(scene);
    }

    public void createAlbum() throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(Photos.window);
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter AlbumName");
        Optional<String> result = dialog.showAndWait();


        if (result.isPresent() && !user.searchAlb(result.get().toLowerCase())) {
            if (result.get().isBlank()) {
                Alert alert = new Alert((Alert.AlertType.INFORMATION));
                alert.initOwner(Photos.window);
                alert.setTitle("No Album Name");
                alert.setHeaderText("Album name not provided.");
                alert.setContentText("Please enter a name for the Album.");
                alert.showAndWait();
                return;
            }
            user.mkAlb(result.get().toLowerCase());
            albumObsList.add(user.getAlbum(result.get().toLowerCase()));
            initializeAlbums();
        } else if (result.isPresent() && user.searchAlb(result.get().toLowerCase())) {
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.initOwner(Photos.window);
            alert.setTitle("Duplicate Album");
            alert.setHeaderText("Album " + result.get() + " already exists." );
            alert.setContentText("Please enter another name.");
            alert.showAndWait();
        }
        user.writeUser();
    }

    public void logout() {
        Photos.getStage().setTitle("Photos");
        Photos.changeScene(Photos.login);
    }

    public void delete() throws IOException{
        Album albName = table.getSelectionModel().getSelectedItem();
        user.delAlb(albName);
        albumObsList.remove(albName);
        user.writeUser();
    }

    public void rename() throws IOException{
        Album albName = table.getSelectionModel().getSelectedItem();

        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(Photos.window);
        dialog.setTitle("Rename Album");
        dialog.setHeaderText("Enter New AlbumName");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() && !user.searchAlb(result.get().toLowerCase())){
            if(result.get().isBlank()){
                Alert alert = new Alert((Alert.AlertType.INFORMATION));
                alert.initOwner(Photos.window);
                alert.setTitle("No Album Name");
                alert.setHeaderText("Album name not provided.");
                alert.setContentText("Please enter a name for the Album.");
                alert.showAndWait();
                return;
            }
            albName.renameAlbum(result.get().toLowerCase());
            initializeAlbums();
        }else {
            checkIfDuplicate(result, user);
        }
        user.writeUser();
    }

    protected static void checkIfDuplicate(Optional<String> result, User user) {
        if(result.isPresent() && user.searchAlb(result.get().toLowerCase())){
            Alert alert = new Alert((Alert.AlertType.INFORMATION));
            alert.initOwner(Photos.window);
            alert.setTitle("Album Name Already Exists");
            alert.setHeaderText("Album Name is used already.");
            alert.setContentText("Please enter a new name for Album.");
            alert.showAndWait();
        }
    }

    public void openSearch(ArrayList<Photo> list) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/application/photos/view/image-gallery.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Album temp = new Album("temp");
        temp.setPhotos(list);

        ImageGalleryController imageGalleryController = loader.getController();
        imageGalleryController.start(user, temp, Photos.getStage().getScene(), albumObsList, true);
        Photos.getStage().setTitle("Search Results");
        Photos.getStage().setScene(scene);
    }

    public void searchbydate() throws IOException{

        boolean format = true;
        String first = firstdate.getText();
        String last = lastdate.getText();

        Calendar fcal = Calendar.getInstance();
        Calendar lcal = Calendar.getInstance();

        fcal.set(Calendar.MILLISECOND, 0);
        lcal.set(Calendar.MILLISECOND, 0);

        if(!first.isBlank() && !last.isBlank()){warning.setText("");}

        if(first.isBlank()){
            format = false;
            warning.setText("Enter First Date");
        }else if(last.isBlank()){
            format = false;
            warning.setText("Enter Last Date");
        }else if(first.length() != 10){
            format = false;
            warning.setText("Please enter a Valid First Date");
        }else if(last.length() != 10){
            format = false;
            warning.setText("Please enter a Valid Last Date");
        }


        try {
            SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            date.setLenient(false);
            date.parse(first);
        } catch (ParseException | IllegalArgumentException e) {
            format = false;
            warning.setText("First Date is Invalid");
        }

        if(format) {
            try {
                SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                date.setLenient(false);
                date.parse(last);
            } catch (ParseException | IllegalArgumentException e) {
                format = false;
                warning.setText("Last Date is Invalid");
            }
        }




        if(format){
            int fmonth = Integer.parseInt(first.substring(0,2));
            int fday = Integer.parseInt(first.substring(3,5));
            int fyear = Integer.parseInt(first.substring(6,10));

            int lmonth = Integer.parseInt(last.substring(0,2));
            int lday = Integer.parseInt(last.substring(3,5));
            int lyear = Integer.parseInt(last.substring(6,10));

            fcal.set(fyear,fmonth-1,fday);
            lcal.set(lyear,lmonth-1,lday);

            try {
                long epoch = new SimpleDateFormat("MM/dd/yyyy").parse(first).getTime();
                fcal.setTimeInMillis(epoch);
            }catch (ParseException e) {}

            try {
                long epoch = new SimpleDateFormat("MM/dd/yyyy").parse(last).getTime();
                lcal.setTimeInMillis(epoch + 86400000);
            }catch (ParseException e) {}

            ArrayList<Photo> test = SearchByDate.searchdate(user.getPictures(),fcal,lcal);
            openSearch(test);
        }

    }
}
