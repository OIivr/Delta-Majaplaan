module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.Delta to javafx.fxml;
    exports com.example.Delta;
}