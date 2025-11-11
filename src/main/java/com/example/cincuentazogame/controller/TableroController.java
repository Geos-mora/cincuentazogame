package com.example.cincuentazogame.controller;

import com.example.cincuentazogame.model.Carta;
import com.example.cincuentazogame.model.Jugador;
import com.example.cincuentazogame.model.Partida;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TableroController {

    private Partida partida;

    /* ====== Elementos del tablero (asigna los fx:id en tablero-view.fxml) ======*/
    @FXML private Label lblSumaMesa;
    @FXML private Label lblTurno;
    @FXML private HBox contenedorCartasJugador;
    @FXML private HBox contenedorMesa;
    @FXML private Button btnJugarCarta;

    /* ==========================================================================*/


    public void mostrarCartasJugador(List<Carta> mano){
        contenedorCartasJugador.getChildren().clear();
        for (Carta carta:mano){
            String ruta="com/example/cincuentazogame/view/recursos/cartas"+ carta.getImagen();
        }

    }


    /* Este método lo llama el MainController al crear la escena*/
    public void iniciarPartida(int numBots) {
        partida = new Partida(numBots);
        actualizarVista();
        iniciarTurnos();
    }

    /* Actualiza lo que se ve en la ventana*/
    private void actualizarVista() {
        lblSumaMesa.setText("Suma de la mesa: " + partida.getSumaMesa());
        lblTurno.setText("Turno de: " + partida.getJugadorActual().getNombre());

        /* TODO: Mostrar cartas reales del jugador en contenedorCartasJugador*/
        /* TODO: Mostrar cartas de la mesa en contenedorMesa*/
    }

    /* Ciclo de turnos automáticos (humano + bots)*/
    private void iniciarTurnos() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (partida.isPartidaTerminada()) {
                        timer.cancel();
                        mostrarPantallaFinal();
                        return;
                    }

                    Jugador actual = partida.getJugadorActual();
                    if (actual.esMaquina()) {
                        partida.turnoMaquina();
                        actualizarVista();
                    }
                });
            }
        }, 1000, 2000); /* ejecuta cada 2 segundos*/
    }

    /* Evento del botón "Jugar carta" (para el jugador humano)*/
    @FXML
    private void onJugarCarta() {
        try {
            partida.turnoHumano();
            actualizarVista();
        } catch (Exception e) {
            System.out.println("Error al jugar carta: " + e.getMessage());
        }
    }

    /* Cuando termina la partida*/
    private void mostrarPantallaFinal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cincuentazogame/view/endgame-view.fxml"));
            Parent root = loader.load();

            EndGameController controller = loader.getController();
            controller.mostrarResultado(partida.getGanador());

            Stage stage = (Stage) lblSumaMesa.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
