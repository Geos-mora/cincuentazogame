package com.example.cincuentazogame.model;

import java.util.List;

public class EstrategiaBasica implements EstrategiaSeleccionCarta {

    @Override
    public Carta seleccionarCarta(List<Carta> mano, int sumaMesa) {
        // escoger la PRIMERA carta que se pueda jugar sin pasar de 50
        for (Carta c : mano) {
            if (ReglaCincuentazo.puedeJugar(sumaMesa, c)) {
                return c;
            }
        }
        return null;
    }
}
