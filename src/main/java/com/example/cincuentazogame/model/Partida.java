package com.example.cincuentazogame.model;

import com.example.cincuentazogame.model.excepciones.MazoVacioException;
import java.util.ArrayList;
import java.util.List;

public class Partida {

    /* Estado principal*/
    private final Mazo mazo;
    private final List<Jugador> jugadores;
    private final List<Carta> mesa;
    private int sumaMesa;

    /* Flujo de juego*/
    private int indiceJugadorActual=0;
    private boolean partidaTerminada=false;
    private Jugador ganador;

    /* --- Construcción ---*/
    public Partida(int cantidadJugadoresMaquina) {
        this.mazo=new Mazo();
        this.jugadores=new ArrayList<>();
        this.mesa=new ArrayList<>();

        jugadores.add(new JugadorHumano("Jugador"));
        for (int i=1; i <=cantidadJugadoresMaquina; i++) {
            jugadores.add(new JugadorMaquina("Máquina "+i));
        }

        prepararPartida();
    }

    /* --- Setup inicial ---*/
    private void prepararPartida() {
        /* Repartir 4 cartas a cada jugador*/
        for (Jugador jugador : jugadores) {
            for (int i=0; i <4; i++) {
                try {
                    jugador.agregarCarta(mazo.tomarCarta());
                } catch (MazoVacioException e) {
                    System.out.println("No hay suficientes cartas para repartir.");
                    return;
                }
            }
        }

        /* Carta inicial a la mesa*/
        try {
            Carta cartaInicial=mazo.tomarCarta();
            mesa.add(cartaInicial);
            sumaMesa=cartaInicial.obtenerValor(0);
            System.out.println("Carta inicial: " + cartaInicial);
            System.out.println("Suma inicial de la mesa: " + sumaMesa);

        } catch (MazoVacioException e) {
            System.out.println("Mazo vacío al iniciar la mesa.");
        }
    }

    /* --- Turnos ---*/
    public void turnoHumano() {
        Jugador j=getJugadorActual();
        if (j.esMaquina() ||j.getMano().isEmpty()) return;
        /* Temporal: juega la primera carta. Luego se integra selección por UI.*/
        Carta c=j.getMano().remove(0);
        jugarCarta(j, c);
        avanzarTurno();
    }

    public void turnoMaquina() {
        Jugador j=getJugadorActual();
        if (!j.esMaquina() ||j.getMano().isEmpty()) return;
        /* Temporal: juega la primera carta. Luego integra estrategia.*/
        Carta c=j.getMano().remove(0);
        jugarCarta(j, c);
        avanzarTurno();
    }

    private void jugarCarta(Jugador j, Carta c) {
        mesa.add(c);
        int valorCarta=c.obtenerValor(sumaMesa);
        sumaMesa += valorCarta;

        System.out.println(j.getNombre() + " jugó: " + c +
                " => " + (valorCarta >= 0 ? "+" : "") + valorCarta +
                " (suma=" + sumaMesa + ")");
    }


    private void avanzarTurno() {
        indiceJugadorActual=(indiceJugadorActual + 1) % jugadores.size();
        if (condicionFin()) {
            partidaTerminada=true;
            ganador=getJugadorActual();
        }
    }

    private boolean condicionFin() {
        /* Placeholder: finaliza cuando hay 20 cartas en la mesa.*/
        return mesa.size() >= 20;
    }

    /* --- Getters y utilidades para la vista ---*/
    public Jugador getJugadorActual() { return jugadores.get(indiceJugadorActual); }

    public Jugador getJugadorHumano() { return jugadores.get(0); }

    public List<Jugador> getJugadores() { return jugadores; }

    public List<Carta> getCartasMesa() { return mesa; }

    public List<Carta> getMesa() { return mesa; } /* si tienes código que ya usa este nombre*/

    public int getSumaMesa() { return sumaMesa; }


    public Mazo getMazo() { return mazo; }

    public boolean isPartidaTerminada() { return partidaTerminada; }

    public Jugador getGanador() { return ganador; }
}
