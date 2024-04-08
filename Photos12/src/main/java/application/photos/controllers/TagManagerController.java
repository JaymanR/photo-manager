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

public class TagManagerController {
    @FXML
    private ChoiceBox<String> tagChoiceBox;
    @FXML
    private TextField presetTagValue;
    @FXML
    private TextField newTagName;
    @FXML
    private TextField newTagValue;
    @FXML
    private CheckBox newTagSingleValue;

    private ArrayList<String> tagNames;

    private Stage stage;
    private User user;
    private Photo img;

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

    public void cancel() {
        stage.close();
    }

    public void warningNoInput() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Please input all required fields.");
        alert.showAndWait();
    }
    public void duplicateTagAlert() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Duplicate Tag");
        alert.setHeaderText("Tag already exists");
        alert.showAndWait();
    }

    public void warningSingleValueAlert() {
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Single Value Only");
        alert.setHeaderText("This tag already has a value.");
        alert.showAndWait();
    }

    public void presetExists(){
        Alert alert = new Alert((Alert.AlertType.INFORMATION));
        alert.initOwner(Photos.window);
        alert.setTitle("Preset Exists");
        alert.setHeaderText("A preset with this tag name already exists!");
        alert.showAndWait();
    }

    public void initializePresetChoiceBox(){

        tagNames.clear();

        tagNames.addAll(user.getSingleList());
        tagNames.addAll(user.getMultiList());

        tagObservableList = FXCollections.observableArrayList();
        tagObservableList.addAll(tagNames);
        tagChoiceBox.setItems(tagObservableList);
    }
}
