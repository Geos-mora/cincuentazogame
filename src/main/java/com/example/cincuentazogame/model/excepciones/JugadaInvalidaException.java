package com.example.cincuentazogame.model.excepciones;

/*
  Excepción  que indica que una carta no se puede
  jugar porque viola las reglas del juego por ejemplo,
  hace que la suma de la mesa supere 50.*/
public class JugadaInvalidaException extends Exception {

    public JugadaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
