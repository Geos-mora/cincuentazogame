package com.example.cincuentazogame.model;

//clase jugadorHumano que hereda de Jugador
public class JugadorHumano extends Jugador{
    //constructor que crea un jugadorHumano con su nombre y mano vacia
    public JugadorHumano(String nombre){
        super(nombre); //ejecuta el constructor de la clase padre
    }
    //sobrescribe el metodo eliminarCarta
    @Override
    public void eliminarCarta(Carta carta){
        super.eliminarCarta(carta);
    }

    @Override
    public Carta seleccionarCarta(int sumaActualMesa){
        //La selección real se manejará por interfaz (FXML) entonces toca unir con evento del click
        return null;
    }
}
