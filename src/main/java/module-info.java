module com.example.carrentaldesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.carrentaldesktop to javafx.fxml;
    exports com.example.carrentaldesktop;
}