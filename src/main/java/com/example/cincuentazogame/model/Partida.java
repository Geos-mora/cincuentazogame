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
        if (partidaTerminada) return;
        /*  si no es el turno del humano simplemnete retorna*/
        Jugador j = getJugadorActual();
        if (j.esMaquina()) return;

        /* para saber siu el jugador humano iiene alguna carta jugable, si no la tiene, será elinado*/
        Carta c = elegirCartaJugable(j);
        if (c ==null) {
            eliminarJugadorActual();
            return;
        }

        /* si tiene una carta jugable jugará esa carta*/
        j.getMano().remove(c);
        boolean ok= jugarCarta(j, c);
        // aquí luego meteremos "robarCarta(j)" cuando implementemos el robo
        if (ok) {
            avanzarTurno();
        }
    }


    public void turnoMaquina() {
        if (partidaTerminada) return;
        /*  si no es el turno del bot simplemnete retorna*/
        Jugador j = getJugadorActual();
        if (!j.esMaquina()) return;

        /* para saber si el bot tiene alguna carta jugable, si no la tiene, será elinado*/
        Carta c = elegirCartaJugable(j);
        if (c == null) {
            eliminarJugadorActual();
            return;
        }

        /* aqui es lo mismo, si tiene una carta jugable jugará esa carta*/
        j.getMano().remove(c);
        boolean ok = jugarCarta(j, c);
        //  irá "robarCarta(j)"
        if (ok) {
            avanzarTurno();
        }
    }


    private boolean jugarCarta(Jugador j, Carta c) {
        /* Aqui si la carta hace pasar de 50, NO se juega vuelve y se recalcula para hacer una jugada válida*/
        if (!ReglaCincuentazo.puedeJugar(sumaMesa, c)) {
            System.out.println("Jugada inválida de " + j.getNombre() + " con " + c);
            return false;
        }

        mesa.add(c);
        sumaMesa = ReglaCincuentazo.calcularNuevaSuma(sumaMesa, c);

        System.out.println(j.getNombre() + " jugó: " + c +
                " (suma=" + sumaMesa + ")");
        return true;
    }

    /* esta funcion me permite jugar la  primera carta que no pasa de 50, si no hay cartas jugables, inmediatamente retornará null
      ya que no  tendra una jugada valida */

    private Carta elegirCartaJugable(Jugador j) {
        for (Carta c : j.getMano()) {
            if (ReglaCincuentazo.puedeJugar(sumaMesa, c)) {
                return c;
             }
       }
        return null;
    }

    //=============================ELIMINAR JUGADOR=============================

    /*esta funcion me érmiter eliminar un jugador cuando ya se queda sin jugada validad asi hasta quedar un solo jugador quien sera
     el ganador de la partida */

    private void eliminarJugadorActual() {
        Jugador j = getJugadorActual();
        j.eliminarJugador();

        /* Las cartas van al mazo y se barajan  */
        if (!j.getMano().isEmpty()) {
            mazo.agregarCartas(new ArrayList<>(j.getMano()));
            j.getMano().clear();
        }
        System.out.println("Jugador eliminado: " + j.getNombre());

        /* codigo para eliminar de la lista de jugadores*/
        jugadores.remove(indiceJugadorActual);

        // Ajustar índice de turno
        if (jugadores.isEmpty()) {
            partidaTerminada= true;
            ganador= null;
            return;
        }

        if (indiceJugadorActual>= jugadores.size()) {
            indiceJugadorActual= 0;
        }

        /*ya si solo queda un solo jugador, sera el ganador*/
        if (jugadores.size() == 1) {
            partidaTerminada = true;
            ganador = jugadores.get(0);
            System.out.println("Ganador: " + ganador.getNombre());
        }
    }




    private void avanzarTurno() {
        if (partidaTerminada) return;

        indiceJugadorActual=(indiceJugadorActual +1) % jugadores.size();

        if (condicionFin()) {
            partidaTerminada =true;
            ganador= jugadores.get(0);
            System.out.println("Ganador: "+ ganador.getNombre());
        }
    }


    private boolean condicionFin() {
        return jugadores.size() == 1;
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
