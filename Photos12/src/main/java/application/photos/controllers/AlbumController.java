package application.photos.controllers;

import application.photos.Photos;
import application.photos.model.Tag;
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
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Manages the Ablums list and the events fired in within the album view
 *
 * @author Jayman Rana and Dev Patel
 */
public class AlbumController {
    /**
     * Border Pane that hold the TableView
     */
    @FXML
    private BorderPane borderPane;
    /**
     * Tableview that holds all the albums
     */
    private TableView<Album> table;
    /**
     * The user who is logged in
     */
    private User user;
    /**
     * Observable List of albums
     */
    private ObservableList<Album> albumObsList;
    /**
     * Textfield for the first date for searching
     */
    @FXML
    private TextField firstdate;
    /**
     * Textfield for the second date for searching
     */
    @FXML
    private TextField lastdate;
    /**
     * Label for warnings
     */
    @FXML
    private Label warning;

    /**
     * Vbox that holds the advanced components
     */
    @FXML
    private VBox advancedVbox;
    /**
     * Textfield for the Tag name for searching
     */
    @FXML
    private TextField tagNameField1;
    /**
     * Textfield for the Tag value for searching
     */
    @FXML
    private TextField tagValueField1;
    /**
     * Holds the conjunction and disjuntion for Tag searching
     */
    @FXML
    private ChoiceBox<String> choiceBonx;
    /**
     * Check box for advanced
     */
    @FXML
    private CheckBox checkBonx;
    /**
     * Textfield for the second Tag name for searching
     */
    @FXML
    private TextField tagNameField2;
    /**
     * Textfield for the second Tag value for searching
     */
    @FXML
    private TextField tagValueField2;
    /**
     * Search button to initiate the searching methods
     */
    @FXML
    private Button searchBtn;
    /**
     * Boolean for the advanced section
     */
    private boolean advanced;

    public void start(Stage stage, User user){
        if (user.getUserName().equals("stock") && !user.searchAlb("stock") && user.getLoginCount() == 0){
            File stockDir = new File("src/../data");
            Album a = new Album("stock");
            user.addAlbum(a);
            if (stockDir.listFiles() != null) {
                for (File f : stockDir.listFiles()) {
                  a.addImage(new Photo(f.getAbsoluteFile()));
                }
            }
        }
        this.user = user;
        initializeAlbums();
        advanced = false;
        advancedVbox.setVisible(false);
        advancedVbox.setManaged(false);
        ObservableList<String> choices = FXCollections.observableArrayList();
        choices.add("OR");
        choices.add("And");
        choiceBonx.setItems(choices);
        user.incrementLoginCount();
    }

    /**
     * Initializes the Albums to the updated version
     */
    private void initializeAlbums() {
        for (Album a : user.getAlbums()) {
            a.getHighestDate();
            a.getLowestDate();
        }
        albumObsList = FXCollections.observableArrayList();
        albumObsList.addAll(user.getAlbums());



        TableColumn<Album, String> nameColumn = new TableColumn<>("Album");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));

        TableColumn<Album, Integer> photoCountColumn = new TableColumn<>("Photo Count");
        photoCountColumn.setCellValueFactory(new PropertyValueFactory<>("numPhotos"));

        TableColumn<Album, String> date1Column = new TableColumn<>("Earliest Date");
        date1Column.setCellValueFactory(new PropertyValueFactory<>("lowestD"));

        TableColumn<Album, String> date2Column = new TableColumn<>("Latest Date");
        date2Column.setCellValueFactory(new PropertyValueFactory<>("highestD"));

        table = new TableView<>();
        table.setItems(albumObsList);
        table.getColumns().addAll(nameColumn, photoCountColumn, date1Column, date2Column);

        borderPane.setCenter(table);
        table.setPrefHeight(TableView.USE_COMPUTED_SIZE);
        table.setPrefWidth(TableView.USE_COMPUTED_SIZE);
    }

    /**
     * Opens the selected album
     * @throws IOException
     */
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

    /**
     * Creates a new Ablum
     * @throws IOException
     */
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

    /**
     * Logs the user out
     */
    public void logout() {
        Photos.getStage().setTitle("Photos");
        Photos.changeScene(Photos.login);
    }

    /**
     * Deletes the selected album
     * @throws IOException
     */
    public void delete() throws IOException{
        Album albName = table.getSelectionModel().getSelectedItem();
        user.delAlb(albName);
        albumObsList.remove(albName);

        for(Photo p: albName.getImages()){
            p.getAlbums().remove(albName.getAlbumName());
        }

        if(user.getAlbums().isEmpty()){
            user.getPictures().removeAll(user.getPictures());
        }

        user.writeUser();
    }

    /**
     * Renames the selected album
     * @throws IOException
     */
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

    /**
     * Checks for duplicate albums in the list
     * @param result List of ablum names for a specific user
     * @param user Owner of the albums
     */
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

    /**
     * Opens the searched photos in a separate stage
     * @param list List of users that get searched
     * @throws IOException
     */
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

    /**
     * Begins searching based on date inputs
     * @throws IOException
     */

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

    /**
     * Shows or hides the Advanced view
     */
    public void initializeAdvancedView() {
        searchBtn.setVisible(advanced);
        //searchBtn.setManaged(advanced);
        advancedVbox.setVisible(!advanced);
        advancedVbox.setManaged(!advanced);
        advanced = !advanced;
    }

    /**
     * Alert for invalid input
     */
    public void invalidInputAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Photos.window);
        alert.setTitle("Invalid Input");
        alert.setContentText("Please give valid input");
        alert.showAndWait();
    }

    /**
     * Overloaded method that takes an input and outputs the error message based on the input
     * @param content Input that is integrated into the error message
     */
    public void invalidInputAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Photos.window);
        alert.setTitle("Invalid Input");
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Begins searching based on Tag inputs
     * @throws IOException
     */
    public void searchByTag() throws IOException {
        if (tagNameField1.getText().isBlank() || tagValueField1.getText().isBlank()) {
            invalidInputAlert();
            return;
        }

        String tagName = tagNameField1.getText();
        String tagValue = tagValueField1.getText();

        Tag searchTag = new Tag(tagName, tagValue, false);

        ArrayList<Photo> tagHitPhotoList = SearchByTag.singleTagSearch(searchTag, user);

        openSearchIfNotNull(tagHitPhotoList);
    }

    /**
     * Begins searching based on the advanced tag inputs
     * @throws IOException
     */
    public void advancedTagSearch() throws IOException {
        initializeAdvancedView();

        if (tagNameField1.getText().isBlank()
                || tagValueField1.getText().isBlank()
                || tagNameField2.getText().isBlank()
                || tagValueField2.getText().isBlank()) {
            invalidInputAlert();
            return;
        }

        if (choiceBonx.getValue() == null) {
            invalidInputAlert("No Combination AND/OR selected");
            return;
        }
        String tag1Name = tagNameField1.getText();
        String tag1Value = tagValueField1.getText();

        String tag2Name = tagNameField2.getText();
        String tag2Value = tagValueField2.getText();


        Tag tag1 = new Tag(tag1Name, tag1Value, false);
        Tag tag2 = new Tag(tag2Name, tag2Value, false);

        boolean bool = choiceBonx.getValue().equals("OR");

        //OR is true AND is false

        ArrayList<Photo> tagPhotoHitList= SearchByTag.searchByCombination(tag1, tag2, bool, user);

        openSearchIfNotNull(tagPhotoHitList);
    }

    /**
     * opens seperate stage and shows the photos that were searched
     * @param tagHitList ArrayList of Photos that get searched
     * @throws IOException throws exception
     */
    private void openSearchIfNotNull(ArrayList<Photo> tagHitList) throws IOException {
        if (tagHitList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(Photos.window);
            alert.setHeaderText("No Photo found");
            alert.setContentText("No Photo found with the Search Criteria");
            alert.showAndWait();
            return;
        }
        openSearch(tagHitList);
        checkBonx.setSelected(false);
    }
}
