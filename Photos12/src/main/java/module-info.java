module application.photos12 {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.photos12 to javafx.fxml;
    exports application.photos12;
    exports application.photos12.controllers;
    opens application.photos12.controllers to javafx.fxml;
}