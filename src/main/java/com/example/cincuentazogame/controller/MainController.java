package com.example.cincuentazogame.controller;

import com.example.cincuentazogame.model.Jugador;
import com.example.cincuentazogame.model.Partida;
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
    private Partida partida;

    @FXML
    public void initialize() {
        // 🔹 Prueba rápida: crear partida con 2 jugadores máquina
        partida = new Partida(2); // 1 humano + 2 máquinas

        System.out.println("=== Inicio de la partida ===");
        System.out.println("Carta inicial en la mesa: " + partida.getMesa().get(0));
        System.out.println("Suma inicial de la mesa: " + partida.getSumaMesa());

        System.out.println("\nJugadores y sus manos iniciales:");
        for (Jugador j : partida.getJugadores()) {
            System.out.println(j.getNombre() + " → " + j.getMano());
        }
    }


}
