package com.example.cincuentazogame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;

public class MainController {

    /*esta función se encarga por medio de un onAction de mostrar la segunda interfaz del juego,
     esta segunda interfaz es el tablero donde se jugara con el número de
     bots que el usuario quiera jugar*/
    @FXML
    private void onJugarPoker(ActionEvent e) throws Exception {
        URL url = getClass().getResource("/com/example/cincuentazogame/view/tablero-view.fxml");

        FXMLLoader loader = new FXMLLoader(url);
        Scene tableroScene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(tableroScene);
        stage.setTitle("Cincuentazo - Tablero");
        stage.show();
    }
    /// ------------------------------------------------------------------------------------------------------------



}
