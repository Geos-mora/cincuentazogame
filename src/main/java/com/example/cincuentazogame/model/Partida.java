package com.example.cincuentazogame.model;
import com.example.cincuentazogame.model.excepciones.MazoVacioException;
import com.example.cincuentazogame.model.excepciones.JugadaInvalidaException;
import com.example.cincuentazogame.model.excepciones.CartaNoEncontradaException;

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
    private final List<String> historialEliminados=new ArrayList<>();
    /* --- Construcción ---*/

    public Partida(int cantidadJugadoresMaquina) {
        this.mazo=new Mazo();
        this.jugadores=new ArrayList<>();
        this.mesa=new ArrayList<>();

        /*
        jugadores.add(new JugadorHumano("Jugador"));
        for (int i=1; i <=cantidadJugadoresMaquina; i++) {
            jugadores.add(new JugadorMaquina("Máquina "+i));
       }*/

        /*para usaer el patron de creacion factory method*/
        jugadores.add(JugadorFactory.crearJugador("humano", "Jugador"));
        /*llama al metodo crearJugador() de la clase JugadorFactory, le pasa dos parametros "humano" y "jugador" y revisa el tipo
        * con el .add agrega el jugador creado a la lista de jugadores */

        for (int i=1; i<=cantidadJugadoresMaquina; i++){
            /*ciclo que se repita tantas veces como bots haya elegido el jugador crear*/
            jugadores.add(JugadorFactory.crearJugador("maquina", "Bot " +i));
            /*Llama al Factory para que cree un jugador de tipo "maquina" y le asigna nombres individuales automáticamente
            * con el .add agrega al juagador maquina a la lista de jugadores*/
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
            }}

        /* Carta inicial a la mesa*/
        try {
            Carta cartaInicial=mazo.tomarCarta();
            mesa.add(cartaInicial);
            sumaMesa=cartaInicial.obtenerValor(0);
            System.out.println("Carta inicial: "+cartaInicial);
            System.out.println("Suma inicial de la mesa: "+sumaMesa);
        } catch (MazoVacioException e) {
            System.out.println("Mazo vacío al iniciar la mesa.");
        }
    }

    /* --- Turnos ---*/

    /* Turno del humano seleccionando una carta específica con el click*/
    public boolean turnoHumano(Carta c) {
        if (partidaTerminada) return false;

        Jugador j=getJugadorActual();
        if (j.esMaquina()) return false;
        if (c == null) return false;

        /* Si la carta no está en la mano se lanza la excepcion*/
        if (!j.getMano().contains(c)) {
            throw new CartaNoEncontradaException(
                    "La carta seleccionada no está en la mano del jugador."
            );
        }

        /* Verificar si el jugador almenos tiene una carta que sea válida*/
        boolean tieneJugadaValida=false;
        for (Carta carta : j.getMano()) {
            if (ReglaCincuentazo.puedeJugar(sumaMesa, carta)) {
                tieneJugadaValida=true;
                break;
            }
        }
        /*aqui eliminamos eel jugador si no tiene jugadas validas */
        if (!tieneJugadaValida) {
            System.out.println("Jugador "+j.getNombre()+" no tiene jugadas válidas. Eliminado.");
            eliminarJugadorActual();
            return false;
        }

        /*Intentar jugar la carta seleccionada*/
        try {
            j.getMano().remove(c);
            jugarCarta(j, c);
            robarCarta(j);
            avanzarTurno();
            return true;

        } catch (JugadaInvalidaException e) {
            System.out.println("[JUGADA INVÁLIDA] "+e.getMessage());
            j.agregarCarta(c);     /* devolvemos la carta a la mano*/
            return false;
        }
    }



    public void turnoMaquina() {
        if (partidaTerminada) return;

        Jugador j=getJugadorActual();
        if (!j.esMaquina()) return;

        /*  el bot aqui selecciona con estrategia*/
        Carta c=j.seleccionarCarta(sumaMesa);

        if (c==null) {
            System.out.println("Máquina sin jugadas válidas. Eliminada: " + j.getNombre());
            eliminarJugadorActual();
            return;
        }

        try {
            j.getMano().remove(c);
            jugarCarta(j, c);
            robarCarta(j);
            avanzarTurno();

        } catch (JugadaInvalidaException e) {
            System.out.println("[ERROR ESTRATEGIA] Máquina intentó jugada inválida: " + e.getMessage());
        }
    }



    private void jugarCarta(Jugador j, Carta c) throws JugadaInvalidaException {

        /* Si la carta hace que la suma pase de 50, lanzamos la excepción CHECKED*/
        if (!ReglaCincuentazo.puedeJugar(sumaMesa, c)) {
            throw new JugadaInvalidaException(
                    "La carta "+c+" haría que la suma supere 50. Suma actual: "+ sumaMesa
            );
        }


        mesa.add(c);
        sumaMesa=ReglaCincuentazo.calcularNuevaSuma(sumaMesa, c);

        System.out.println(j.getNombre()+" jugó: "+c +
                " -> nueva suma="+sumaMesa);
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
        Jugador j=getJugadorActual();
        j.eliminarJugador();

        /* guardaremos aqui  el historial de eliminados para luego mostrar en la interfaz*/
        historialEliminados.add(j.getNombre());

        /* Las cartas van al mazo y se barajan  */
        if (!j.getMano().isEmpty()) {
            mazo.agregarCartas(new ArrayList<>(j.getMano()));
            j.getMano().clear();
        }
        System.out.println("Jugador eliminado: "+j.getNombre());

        /* codigo para eliminar de la lista de jugadores*/
        jugadores.remove(indiceJugadorActual);

        /*Ajustar índice de turno*/
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
            partidaTerminada=true;
            ganador=jugadores.get(0);
            System.out.println("Ganador: "+ganador.getNombre());
        }
    }
    public boolean existeJugadorConNombre(String nombre) {
        for (Jugador j : jugadores) {
            if (j.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }



    /*=====================================REPONER MAZO===================*/
    private void reponerMazoDesdeMesa() {
       /*  Si la mesa tiene 0 o 1 carta, no hay suficientes para reponer*/
        if (mesa.size() <= 1) {
            return;
        }
        /* Cartas para devolver al mazo: todas menos la última*/
        List<Carta> paraMazo=new ArrayList<>(mesa.subList(0,mesa.size()- 1));

        /*Dejamos solo la última carta en la mesa*/
        Carta ultima= mesa.get(mesa.size() - 1);
        mesa.clear();
        mesa.add(ultima);
        /*Enviamos cartas al mazo y barajar */
        mazo.agregarCartas(paraMazo);
        System.out.println("Mazo repuesto desde la mesa. Cartas añadidas: "+paraMazo.size());
    }

    /*==========================ROBAR CARTA =================================*/

    private void robarCarta(Jugador j) {
        /*Si el mazo está vacío, intentamos reponerlo desde la mesa*/
        if (mazo.estaVacio()) {
            reponerMazoDesdeMesa();
        }

        /*SI no hay cartas ni en mazo ni en mesa para reponer*/
        if (mazo.estaVacio()) {
            System.out.println("No hay cartas disponibles para robar.");
            return;
        }

        try {
            Carta nueva=mazo.tomarCarta();
            j.agregarCarta(nueva);
            System.out.println(j.getNombre()+" robó carta: "+nueva);
        } catch (MazoVacioException e) {
            System.out.println("Error: mazo vacío al intentar robar.");
        }
    }





    public void avanzarTurno() {
        if (partidaTerminada) return;

        indiceJugadorActual=(indiceJugadorActual +1) % jugadores.size();

        if (condicionFin()) {
            partidaTerminada =true;
            ganador= jugadores.get(0);
            System.out.println("Ganador: "+ ganador.getNombre());
        }
    }


    private boolean condicionFin() {return jugadores.size() == 1;}


    /* --- Getters y utilidades para la vista ---*/
    public Jugador getJugadorActual() { return jugadores.get(indiceJugadorActual);}

    public Jugador getJugadorHumano() { return jugadores.get(0);}

    public List<Jugador> getJugadores() { return jugadores;}
    public List<String> getHistorialEliminados() {return historialEliminados;}

    public List<Carta> getCartasMesa() { return mesa;}

    public List<Carta> getMesa() { return mesa;} /* si tienes código que ya usa este nombre*/

    public int getSumaMesa() { return sumaMesa;}


    public Mazo getMazo() { return mazo;}

    public boolean isPartidaTerminada() { return partidaTerminada;}

    public Jugador getGanador() { return ganador;}
}
