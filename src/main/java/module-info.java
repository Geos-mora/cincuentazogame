module com.example.cincuentazogame {
    requires javafx.controls;
    requires javafx.fxml;

    // 👇 abrir ambos paquetes para FXML
    opens com.example.cincuentazogame.controller to javafx.fxml;
    opens com.example.cincuentazogame.view to javafx.fxml;

    // 👇 opcionales, pero recomendados
    exports com.example.cincuentazogame;
    exports com.example.cincuentazogame.controller;
    exports com.example.cincuentazogame.view;
}
