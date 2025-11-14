package com.example.cincuentazogame.model.excepciones;

/*
  Excepción  que se lanza cuando se intenta
  jugar una carta que no está en la mano del jugador.
 */
public class CartaNoEncontradaException extends RuntimeException {

    public CartaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
