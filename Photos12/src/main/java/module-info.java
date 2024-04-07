module application.photos {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.photos to javafx.fxml;
    exports application.photos;
    exports application.photos.controllers;
    exports  application.photos.model;
    opens application.photos.controllers to javafx.fxml;
}