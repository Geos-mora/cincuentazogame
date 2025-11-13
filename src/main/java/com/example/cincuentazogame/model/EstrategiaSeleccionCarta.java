package com.example.cincuentazogame.model;

import java.util.List;

public interface EstrategiaSeleccionCarta {
    Carta seleccionarCarta(List<Carta> mano, int sumaMesa);
}
