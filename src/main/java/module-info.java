module com.example.dron {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dron to javafx.fxml;
    exports com.example.dron;
}