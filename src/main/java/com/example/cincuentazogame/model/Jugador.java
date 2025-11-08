package com.example.cincuentazogame.model;

import java.util.ArrayList;
import java.util.List;

//es una clase abstracta que sirve como plantilla para los otros tipos de jugadores
public abstract  class Jugador{
    //atributos
    protected String nombre; //nombre del jugador
    protected List<Carta> mano; //lisra de cartas del jugador
    protected boolean eliminado; //indica si el jugador esta eliminado o no

    //constructor
    public Jugador(String nombre){
        this.nombre=nombre;
        this.mano=new ArrayList<>(); //crea un arraylist vacio
        this.eliminado=false;//fija el estado eliminado en falso
    }

    //añade una carta a la nmano del jugador
    public void agregarCarta(Carta carta){
        mano.add(carta);
    }

    //elimina una carta de la mano del jugador si se la pasa como argumento
    public void eliminarCarta(Carta carta){
        mano.remove(carta);
    }

    //getter que devuelve la lista de cartas "mano"
    public List<Carta>getMano(){
        return mano;
    }

    //getter que devuelve el  nombre del jugadpr
    public String getNombre(){
        return nombre;
    }

    //metodo que indica si el jugador fue eliminado
    public boolean estaEliminado(){
        return eliminado;
    }

    //metodo que marca al jugador como eliminado
    public void eliminarJugador() {
        this.eliminado = true;
    }

    //metodo abstracto para seleccionar una carta
    public abstract Carta seleccionarCarta(int sumaActualMesa);

    /* Este metodo verifica si el jugador actual es una instancia de la clase JugadorMaquina
       si lo es, devuelve true (indica que es un jugador controlado por la máquina) Si no, devuelve false (indica que es un jugador humano
       Se usa para diferenciar entre jugadores humanos y automáticos durante la partida.*/
    public boolean esMaquina() {
        return this instanceof JugadorMaquina;
    }
}


