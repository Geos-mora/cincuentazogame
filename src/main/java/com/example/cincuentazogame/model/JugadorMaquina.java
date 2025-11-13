package com.example.cincuentazogame.model;

public class JugadorMaquina extends Jugador {

    private EstrategiaSeleccionCarta estrategia;

    public JugadorMaquina(String nombre) {
        super(nombre);
        this.estrategia=new EstrategiaBasica();
    }

    public void setEstrategia(EstrategiaSeleccionCarta estrategia) {
        this.estrategia = estrategia;
    }

    @Override
    public Carta seleccionarCarta(int sumaActualMesa) {
        return estrategia.seleccionarCarta(mano, sumaActualMesa);
    }
}
