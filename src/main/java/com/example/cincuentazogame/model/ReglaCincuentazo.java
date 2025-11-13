package com.example.cincuentazogame.model;

public class ReglaCincuentazo {
    private ReglaCincuentazo() {}

    public static boolean puedeJugar(int sumaActual, Carta carta) {
        int valor= carta.obtenerValor(sumaActual);
        return sumaActual + valor <= 50;
    }

    public static int calcularNuevaSuma(int sumaActual, Carta carta) {
        return sumaActual + carta.obtenerValor(sumaActual);
    }
}
