package com.example.cincuentazogame.controller;

import com.example.cincuentazogame.model.Carta;
import com.example.cincuentazogame.model.Jugador;
import com.example.cincuentazogame.model.Partida;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class TableroController {
    private static final String BASE_IMG="/com/example/cincuentazogame/view/recursos/cartas/";

    private Partida partida;
    private Thread hiloCronometro;
    private volatile boolean cronometroActivo = false;
    private int segundosTranscurridos = 0;

    /* ====== Elementos del tablero (asigna los fx:id en tablero-view.fxml) ======*/
    @FXML private Label lblSumaMesa;
    @FXML private Label lblTurno;
    @FXML private HBox contenedorCartasJugador;
    @FXML private HBox contenedorMesa;
    @FXML private ImageView imgBot1;
    @FXML private ImageView imgBot2;
    @FXML private ImageView imgBot3;
    @FXML private Label lblTimer;


    /* =============================== HILO DEL TIMER ===========================================*/
    private void iniciarCronometro() {
        cronometroActivo = true;
        segundosTranscurridos = 0;

        if (lblTimer !=null) {
            lblTimer.setText("Tiempo: 00:00");/*poner un texto inicial*/
        }

        /* Creamos el hilo del cronómetro dentro del Runnable va el código que el hilo va a ejecutar en paralelo*/
        hiloCronometro = new Thread(() -> {
            /*este bucle que se ejecuta mientras el cronometroActivo sea true y que  exista una partida y no haya terminado*/
            while (cronometroActivo && partida != null && !partida.isPartidaTerminada()) {
                try {
                    /* pausa el hilo 1000 milesegundos =  1 segundo*/
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                /*aqui aumentamos el contador un segundo */
                segundosTranscurridos++;
                /*  usamos Platform.runLater(), que agenda la actualización en
                    el hilo de la UI */
                Platform.runLater(() -> {
                    if (lblTimer != null) {
                        lblTimer.setText("Tiempo: " + formatearTiempo(segundosTranscurridos));
                    }
                });
            }
        });

        hiloCronometro.setDaemon(true); /* no se bloquea el cierre de la app*/
        hiloCronometro.start();
    }

    /* esta funcion me sirve para convertir  los segundos totales a formato mm:ss*/
    private String formatearTiempo(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }
    private void detenerCronometro() {
        cronometroActivo = false;
        if (hiloCronometro != null && hiloCronometro.isAlive()) {
            hiloCronometro.interrupt();
        }
    }
//====================================================================================

    public void mostrarBots(int numBots){
        imgBot1.setVisible(false);
        imgBot2.setVisible(false);
        imgBot3.setVisible(false);

        String ruta = BASE_IMG + "back.png";
        Image back = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta)));

        if (numBots>=1) {
            imgBot1.setImage(back);
            imgBot1.setVisible(true);
        }
        if (numBots>=2) {
            imgBot2.setImage(back);
            imgBot2.setVisible(true);
        }
        if (numBots>=3) {
            imgBot3.setImage(back);
            imgBot3.setVisible(true);
        }
    }


    public void mostrarCartasJugador(List<Carta> mano){
        contenedorCartasJugador.getChildren().clear();

        for (Carta carta : mano) {
            String ruta = BASE_IMG + carta.getImagenFile();

            ImageView vista =new ImageView(
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta)))
            );
            vista.setFitWidth(100);
            vista.setPreserveRatio(true);

            //----------estilos de hover------
            vista.setOnMouseEntered(e -> {
                vista.setScaleX(1.2);
                vista.setScaleY(1.2);
                vista.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0.5, 0, 0);");

            });
            vista.setOnMouseExited(e -> {
                vista.setScaleX(1.0);
                vista.setScaleY(1.0);
                vista.setStyle("");
            });
            //---------------------------------


            /* Handler dek click para jugar la carta*/
            vista.setOnMouseClicked(event -> {
                if (partida == null || partida.isPartidaTerminada()) return;

                boolean ok=partida.turnoHumano(carta);
                if (!ok) {
                    System.out.println("No se pudo jugar la carta seleccionada.");
                    return;
                }
                actualizarVista();
            });
            contenedorCartasJugador.getChildren().add(vista);
        }
    }


    private void mostrarCartaMesa(List<Carta> mesa) {
        contenedorMesa.getChildren().clear();

        if (mesa.isEmpty()) return;

        Carta ultima = mesa.get(mesa.size() - 1); // última jugada
        String ruta = BASE_IMG + ultima.getImagenFile();

        ImageView vista = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta)))
        );
        vista.setFitWidth(100);
        vista.setPreserveRatio(true);

        vista.setOnMouseEntered(e -> {
            vista.setScaleX(1.2);
            vista.setScaleY(1.2);
            vista.setStyle("-fx-effect: dropshadow(gaussian, black, 20, 0.5, 0, 0);");

        });
        vista.setOnMouseExited(e -> {
            vista.setScaleX(1.0);
            vista.setScaleY(1.0);
            vista.setStyle("");
        });


        contenedorMesa.getChildren().add(vista);
    }




    /* Este metodo lo llama el maincontroller al crear la escena */
    public void iniciarPartida(int numBots){
        partida=new Partida(numBots);
        actualizarVista();
        iniciarCronometro();
        iniciarTurnos();
    }

    /* Actualiza lo que se ve en la ventana*/
    private void actualizarVista() {
        lblSumaMesa.setText("Suma de la mesa: " + partida.getSumaMesa());
        lblTurno.setText("Turno de: " + partida.getJugadorActual().getNombre());

        // Mano del jugador humano
        mostrarCartasJugador(partida.getJugadorHumano().getMano());

        // Carta en la mesa
        mostrarCartaMesa(partida.getCartasMesa());
    }



    /*ciclo de turnos automaticos (humano y bot)*/
    private void iniciarTurnos() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (partida.isPartidaTerminada()) {
                        timer.cancel();
                        detenerCronometro();
                        mostrarPantallaFinal();
                        return;
                    }

                    Jugador actual=partida.getJugadorActual();
                    if (actual.esMaquina()) {
                        partida.turnoMaquina();
                        actualizarVista();
                    }
                });
            }
        }, 1000, 2000); /* ejecuta cada 2 segundos*/
    }

    /* Evento del boton jugar carta para el jugador */

    /* cuando termina la partida */
    private void mostrarPantallaFinal() {
        detenerCronometro();
        mostrarAlertFin();
    }


    /*alert box para el final del juego
    * (sea que se pierda o gane)*/
    private void mostrarAlertFin(){
        /*mensaje del alertbox*/
        String mensaje="Juego terminado, gano: " + partida.getGanador().getNombre() + "\n\n deseas volver al menu principal?";
        javafx.scene.control.Alert alert=new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("fin de la partida");
        alert.setHeaderText("resultado:");
        alert.setContentText(mensaje);

        /*botones del alert box*/
        ButtonType btnMenu=new ButtonType("Volver al menu");
        ButtonType btnSalir=new ButtonType("Salir del juego");
        alert.getButtonTypes().setAll(btnMenu, btnSalir);
        alert.showAndWait().ifPresent(respuesta->{
            if (respuesta==btnMenu){
                volverAlMenuPrincipal();

            } else{
                Platform.exit();
            }
        });
    }

    /*
    * Metodo que carga FXML del menu principal:*/
    private void volverAlMenuPrincipal(){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/cincuentazogame/view/main-view.fxml"));
            Parent root =loader.load();
            Stage stage=(Stage) lblSumaMesa.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cincuentazo-menu principal");
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
