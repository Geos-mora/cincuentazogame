module com.example.cincuentazogame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cincuentazogame to javafx.fxml;
    exports com.example.cincuentazogame;
}