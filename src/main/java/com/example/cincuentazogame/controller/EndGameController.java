package com.example.cincuentazogame.controller;

import javafx.animation.FadeTransition;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EndGameController{

    @FXML
    private Label scoreLabel;

    @FXML
    private Button retryButton;

    @FXML
    private Button menuButton;

    @FXML
    private ImageView backgroundImage;

    private int finalScore;

    public void initialize(){
        FadeTransition fade = new FadeTransition(Duration.seconds(1.2), scoreLabel.getScene().getRoot());
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public void setFinalScore(int score){
        this.finalScore = score;
        scoreLabel.setText(String.valueOf(score));
    }

    @FXML
    private void onRetry(){
        //Logica para reiniciar el juego
        Stage stage = (Stage) retryButton.getScene().getWindow();
        stage.close();
        // MainApp.loadGameScene();
    }

    @FXML
    private void onMenu(){
        // Lógica para volver al menú principal
        Stage stage=(Stage) menuButton.getScene().getWindow();
        stage.close();
        // MainApp.loadMenuScene();
    }
}
