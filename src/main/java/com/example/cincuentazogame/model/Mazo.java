package com.example.cincuentazogame.model;

import com.example.cincuentazogame.model.excepciones.MazoVacioException;
import java.util.*;

public class Mazo {
        /*lista privada de cartas*/
        private List<Carta> cartas;
        /*arreglo estatico de tipo string para definir los palos*/
        private static final String[] PALOS = {"Corazones", "Diamantes", "Tréboles", "Picas"};
        /*arreglo estatico de tipo string para definir los valores*/
        private static final String[] VALORES = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        /*constructor del mazo*/
        public Mazo() {
            /*inicializa la lista de cartas vacia*/
            cartas = new ArrayList<>();
            /*doble for que recorre todos los palos y valores oara crear una carta con un palo y valor*/
            for (String palo : PALOS) {
                for (String valor : VALORES) {
                    /*se añade la carta a la lista de cartas*/
                    cartas.add(new Carta(palo, valor));
                }
            }
            /*mwzcla el mazo despues de crearlo*/
            barajar();
        }
        /*metodo para barajar las cartas dek mazo*/
        public void barajar() {
            Collections.shuffle(cartas);
        }

        /*metodo para tomar una carta y genera la execpcion de si el mazo está vacio*/
        public Carta tomarCarta()throws MazoVacioException{
            /*comprobacion de si el mazo está vacio*/
            if(cartas.isEmpty()){
                /*FALTA LOGICA DE LA EXCEPCION MazoVacioException*/
                throw new MazoVacioException("El mazo está vacío.");
            }
            return cartas.remove(0);
        }

        /*metodo que toma una lista de cartas nuevas, las añade al final del mazo y mezcla nuevamnete el mazo*/
        /*algo asi como llenar el mazo nuevamente con las cartas que ya hayan sido usadas*/
        public void agregarCartas(List<Carta>nuevas) {
            cartas.addAll(nuevas);
            barajar();
        }

        /*devuelve el numero actual de cartas del mazo usando*/
        public int cantidadCartas() {
            return cartas.size();
        }

        /*retorna True si el mazo está vacio*/
        public boolean estaVacio() {
            return cartas.isEmpty();
        }
}


