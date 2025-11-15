package com.example.cincuentazogame.controller;

import com.example.cincuentazogame.model.Jugador;
import com.example.cincuentazogame.model.Partida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;

public class MainController{
    @FXML private Button btnUnBot;
    @FXML private Button btnDosBots;
    @FXML private Button btnTresBots;

    private int numeroBots=2; /* valor por defecto si no selecciona*/
    @FXML
    private void seleccionarUnBot(){
        numeroBots=1;
        System.out.println("Seleccionado 1 bot");
        btnUnBot.setStyle("-fx-background-color: #5C0C7D;");
        btnDosBots.setStyle("");
        btnTresBots.setStyle("");

    }

    @FXML
    private void seleccionarDosBots(){
        numeroBots=2;
        System.out.println("Seleccionado 2 bots");
        btnDosBots.setStyle("-fx-background-color: #5C0C7D;");
        btnUnBot.setStyle("");
        btnTresBots.setStyle("");
    }

    @FXML
    private void seleccionarTresBots(){
        numeroBots=3;
        System.out.println("Seleccionado 3 bots");
        btnTresBots.setStyle("-fx-background-color: #5C0C7D;");
        btnUnBot.setStyle("");
        btnDosBots.setStyle("");
    }

    /*esta función se encarga por medio de un onAction de mostrar la segunda interfaz del juego,
     esta segunda interfaz es el tablero donde se jugara con el número de
     bots que el usuario quiera jugar*/
    @FXML
    private void onJugarPoker(ActionEvent e){
        try{
            /*carga FXML del tablero*/
            URL url=getClass().getResource("/com/example/cincuentazogame/view/tablero-view.fxml");

            FXMLLoader loader=new FXMLLoader(url);
            Parent root=loader.load();
            /*inicia la partida con el numero de bots elegido*/

            TableroController controller=loader.getController();
            controller.iniciarPartida(numeroBots); /* numero de bots que se elijan*/
            controller.mostrarBots(numeroBots);




            Stage stage=(Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cincuentazo - Tablero");
            stage.show();

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
    /* ------------------------------------------------------------------------------------------------------------*/

    /*prueba rapido solo para ensayar que las clases carta, mazo, jugador,
     jugador humano, jugador maquina, y partida, funcionan*/

    private Partida partida;

    @FXML
    public void initialize(){

        }




}
