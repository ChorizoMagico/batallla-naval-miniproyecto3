module com.example.batallanaval {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.batallanaval to javafx.fxml;
    opens com.example.batallanaval.controller to javafx.fxml;
    exports com.example.batallanaval;
    opens com.example.batallanaval.model to javafx.fxml;
    opens exceptions to javafx.fxml;
}