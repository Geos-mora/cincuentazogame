package com.example.cincuentazogame.model;

//clase que hereda de jugador
public class JugadorMaquina extends Jugador{
    //constrictor de la clase jugadorMaquina, inicializa un jugador maquina con nombre y mano vacia
    public JugadorMaquina(String nombre){
        super(nombre); //llama al constructor de la clase jugador
    }

    //sobrescribe el metodo heredado
    @Override
    public Carta seleccionarCarta(int sumaActualMesa){
        for(Carta carta:mano){ //inicia bucle que recorre toda la mano
            int nuevaSuma=sumaActualMesa+carta.getPuntaje();//para cada carta calcula una nuevaSuma para ver si la carta puede ser lanzada
            if (nuevaSuma<=50){
                return carta;//juega la primera carta válida
            }
        }
        return null; //no puede jugar
    }
}