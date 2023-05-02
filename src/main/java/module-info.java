module com.example.software1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.software to javafx.fxml;
    exports com.example.software;
    exports controller;
    opens controller to javafx.fxml;
    exports model;

}