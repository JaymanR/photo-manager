package application.photos.controllers;

import application.photos.Photos;
import application.photos.model.Photo;
import application.photos.model.Tag;
import application.photos.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages the tags and the preset tags for the images
 */
public class TagManagerController {
    /**
     * Choice box that holds the preset Tag names
     */
    @FXML
    private ChoiceBox<String> tagChoiceBox;
    /**
     * Textfield for the value of a tag for an image
     */
    @FXML
    private TextField presetTagValue;
    /**
     * Textfield to input a name of the new tag
     */
    @FXML
    private TextField newTagName;
    /**
     * Textfield to input the value of the new tag
     */
    @FXML
    private TextField newTagValue;
    /**
     * Check box to show if a tag is single value
     */
    @FXML
    private CheckBox newTagSingleValue;

    /**
     * ArrayList of tag names for the preset choice box
     */
    private ArrayList<String> tagNames;

    private Stage stage;
    private User user;
    private Photo img;
    /**
     * Observable list for tags
     */
    ObservableList<String> tagObservableList;
    ImageGalleryController imageGalleryController;

    public void start(Stage stage, User user, Photo img, ImageGalleryController imageGalleryController) {
        this.user = user;
        this.img = img;
        this.stage = stage;
        this.imageGalleryController = imageGalleryController;
        tagNames = new ArrayList<>();

        initializePresetChoiceBox();
    }

    /**
     * Adds tag to image based on the name and value selected by user
     * @throws IOException
     */
    public void addTagPreset() throws IOException {
        String value = presetTagValue.getText();

        if (value.isEmpty()) {
            warningNoInput();
            return;
        }

        Tag newTag = new Tag(tagChoiceBox.getSelectionModel().getSelectedItem(), value, user.getSingleList().contains(tagChoiceBox.getSelectionModel().getSelectedItem()));

        if(img.searchTagName(tagChoiceBox.getSelectionModel().getSelectedItem()) && user.getSingleList().contains(tagChoiceBox.getSelectionModel().getSelectedItem())){
            warningSingleValueAlert();
            return;
        }else if(img.getTags().contains(newTag)){
            duplicateTagAlert();
            return;
        }else{
            img.addTag(newTag);
            user.writeUser();
            imageGalleryController.showImage();
        }

        initializePresetChoiceBox();
        presetTagValue.clear();

    }

    /**
     * Adds tag to image based on name and value inputted by user
     * @throws IOException
     */
    public void addNewTag() throws IOException {
        if (newTagName.getText().isEmpty() || newTagValue.getText().isEmpty()) {
            warningNoInput();
            return;
        }

        if(tagNames.contains(newTagName.getText())) {
            presetExists();
        }else {

            Tag tag = new Tag(newTagName.getText(), newTagValue.getText(), newTagSingleValue.isSelected());
            img.addTag(tag);

            if (newTagSingleValue.isSelected()) {
                user.addSingleTag(newTagName.getText());
            } else {
                user.addMultiTag(newTagName.getText());
            }

        }

        user.writeUser();
        imageGalleryController.showImage();

        initializePresetChoiceBox();
        newTagName.clear();
        newTagValue.clear();
        newTagSingleValue.setSelected(false);
    }

    /**
     * Closes the current stage
     */
    public void cancel() {
        stage.close();
    }

    /**
     * Warns user that there is no input for tag name or value
     */
    public void warningNoInput() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Please input all required fields.");
        alert.showAndWait();
    }

    /**
     * Warns user if an existing tag already exists
     */
    public void duplicateTagAlert() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Duplicate Tag");
        alert.setHeaderText("Tag already exists");
        alert.showAndWait();
    }

    /**
     * Warns user that the tag is single value and there can only be one per image
     */
    public void warningSingleValueAlert() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Single Value Only");
        alert.setHeaderText("This tag already has a value.");
        alert.showAndWait();
    }

    /**
     * Warns user that a preset for the inputted name already exists
     */
    public void presetExists(){
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Preset Exists");
        alert.setHeaderText("A preset with this tag name already exists!");
        alert.showAndWait();
    }

    /**
     * Warns user that the tag does not exist
     */
    public void DoesntExist(){
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Tag Doesn't Exists");
        alert.setHeaderText("The Tag Does Not Exist!");
        alert.showAndWait();
    }

    /**
     * Initializes the preset Choice box
     */
    public void initializePresetChoiceBox(){

        tagNames.clear();

        tagNames.addAll(user.getSingleList());
        tagNames.addAll(user.getMultiList());

        tagObservableList = FXCollections.observableArrayList();
        tagObservableList.addAll(tagNames);
        tagChoiceBox.setItems(tagObservableList);
    }

    /**
     * Deletes a tag assigned to an image, based on the value input
     * @throws IOException
     */
    public void deleteTag() throws IOException {
        String value = presetTagValue.getText();
        String name = tagChoiceBox.getSelectionModel().getSelectedItem();

        Tag temp = new Tag(name, value, false);

        if(img.getTags().contains(temp)){
            img.removeTag(temp);
        }else{
            DoesntExist();
        }

        user.writeUser();
        imageGalleryController.showImage();
        presetTagValue.clear();
        tagChoiceBox.setValue(null);

    }
}
