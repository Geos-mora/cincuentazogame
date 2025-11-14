package com.example.cincuentazogame.model;
import com.example.cincuentazogame.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PartidaTest {

    /*TEST 1:
   - verifica que cada jugador recibe 4 cartas
   - verifica que la mesa comienza con 1 carta inicial
   */
    @Test
    void testRepartirCartasIniciales(){

        Partida partida=new Partida(2);
        /*se crea un nuevo objeto partida
        el parametro 2 valida para 1 humano+2 máquinas=3 jugadores*/

        /*cada jugador debe tener 4 cartas*/
        for (Jugador j:partida.getJugadores()){
            assertEquals(4, j.getMano().size());
        }
        /*partida.getJugadores() obtiene la lista de jugadores creados dentro de la partida
         * for (Jugador j : ...) recorre uno por uno cada jugador de la partida
         * assertEquals(4, j.getMano().size()); comprueba que cada jugador tiene exactamente 4 cartas en su mano, si algun jugador tiene mas o menos de 4 cartas, el test falla*/

        /*la mesa debe tener 1 carta inicial*/
        assertEquals(1, partida.getMesa().size());
        /*partida.getMesa() obtiene la lista de cartas que están en la mesa
        * .size() cuenta cuántas cartas hay
        * assertEquals(1, ...) verifica que la mesa tiene exactamente 1 carta inicial*/
    }


    /*TEST 2
    * - verifica que el turno pasa del jugador 0 → 1 → 2 y asi
    * - si está en el último, vuelve al primero*/
    @Test
    void testAvanzarTurno(){
        Partida partida=new Partida(2); /*se crea la partida, y nuevamente se pasa parametro 2 para validar 1 jugador humano y dos maquina */

        Jugador primero=partida.getJugadorActual();
        /*guarda en la variable "primero" el jugador actual al inicio de la partida, que sería el jugador 0, normalmente el humano
        * se guarda para comrpobar que despues el turno cambia*/

        partida.turnoMaquina();
        /*si el primer jugador es humano este turno no avanza porque los humanos juegan con acciones de interfaz, no automáticamente*/
        partida.turnoMaquina();
        /*llama de nuevo al turno de maquina, si el turno no cambió antes, el jugador actual sigue siendo humano
        * El test asume que despues de dos llamadas el turno sí habrá avanzado*/

        Jugador segundo=partida.getJugadorActual();
        /*guarda en la variable "segundo" el jugador que quedó como actual después de las 2 llamadas a turnoMaquina()*/

        assertNotSame(primero, segundo);
        /*comprueba que el jugador actual NO es el mismo que había al inicio, valida que el turno sí avanzó*/

    }



    /*TEST 3:
    * - verifica que el humano juega una carta válida
    * - verifica que la carta se elimina de la mano
    * - verifica que se actualiza la suma de la mesa
    * - verifica que se roba una carta después de jugar*/
    @Test
    void testTurnoHumanoCartaValida() {
        Partida partida=new Partida(1); /* crea la partida con parametro 1, es decir, 1 humano + 1 máquina*/

        Jugador humano=partida.getJugadorHumano();
        /*obtiene el jugador humano directamente desde la partida, se guarda en la variable humano*/

        Carta cartaJugable=humano.getMano().get(0);
        /*selecciona la primera carta de la mano del jugador humano, la carta que intentará jugar*/

        /* se garantiza que haya una carta que sea jugable*/
        while (!ReglaCincuentazo.puedeJugar(partida.getSumaMesa(), cartaJugable)) {
            cartaJugable=humano.getMano().get(0);
            humano.getMano().remove(0); /*se quita carta invalida para buscar una válida*/
            humano.getMano().add(new Carta("Diamantes", "1")); /*carta siempre jugable*/
        }
        /*while (!ReglaCincuentazo.puedeJugar(...)) mientras la carta actual no pueda jugarse, repite el proceso
        * cartaJugable=humano.getMano().get(0); obtiene de nuevo la primera carta de la mano (por si cambió al eliminar la anterior).
        * humano.getMano().remove(0); elimina esa carta inválida de la mano
        * humano.getMano().add(new Carta("ORO", 1)); agrega una carta que siempre será jugable por su valor 1 que casi siempre es valido */

        int tamañoAntes=humano.getMano().size();
        /*guarda cuántas cartas tiene el humano antes de jugar para comprobar
        - se eliminó una carta al jugar
        - se robó una carta después
        entonces el tamaño final debe ser el mismo*/

        boolean resultado=partida.turnoHumano(cartaJugable);
        /*Ejecuta el turno del jugador humano, pasa como parámetro la carta seleccionada
        * dentro verifica si la carta es valida, la agrega a la mesa, actualiza la suma, elimina carta de la mano, toma carta del mazo
        * retorna true si se pudo jugar la carta*/

        assertTrue(resultado); /*comprueba que el turno fue válido, es decir, la carta se pudo jugar*/
        assertEquals(tamañoAntes, humano.getMano().size());
        /*verifica que el tamaño de la mano es igual al de antes de jugar para confirmar que se eliminó la carta jugada y
        que después se robó una nueva carta. Por lo que la suma de cantidad de cartas debe ser igual*/

    }



}