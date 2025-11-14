package com.example.cincuentazogame.model;

/*implementacion de diseño creacional - factory method para crear los jugadores humano y bots separado del uso de los objetos craeados*/

public class JugadorFactory{
    public static Jugador crearJugador(String tipo, String nombre){
        /*Método publico y estatico crearJugador que recibe dos Strings:
        - tipo: indica si el jugador es "humano" o "maquina"
        - nombre: nombre que se le dará al jugador creado
        Es estático para poder llamarlo sin instanciar JugadorFactory*/

        switch (tipo.toLowerCase()){
            /*convierte el texto a minúsculas y selecciona el caso apropiado*/
            case "humano":
                return new JugadorHumano(nombre);
                /*Si el tipo (en minúsculas) es "humano" entonces crea y devuelve una nueva instancia de JugadorHumano pasando el nombre*/

            case "maquina":
            case "bot":
                return new JugadorMaquina(nombre);
                /* Si el tipo es "maquina" o "bot", entonces crea y devuelve una instancia de JugadorMaquina con el nombre*/

            default:
                throw new IllegalArgumentException("Tipo de jugador no válido: " + tipo);
                /*Si ninguna de las opciones anteriores coincide entocnes lanza una IllegalArgumentException con un mensaje que incluye el tipo recibido*/
        }
    }
}
