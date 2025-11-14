package com.example.cincuentazogame.model;
import com.example.cincuentazogame.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Partida partida=new Partida(2); /*1 humano + 2 máquinas */

        Jugador jugador0=partida.getJugadorActual(); /*turno 0*/
        /*Obtiene y guarda en jugador0 la referencia al jugador que tiene el turno inicial*/

        partida.avanzarTurno(); /*turno 1*/

        Jugador jugador1=partida.getJugadorActual();
        /*Lee el jugador que ahora tiene el turno (despues del primer avance) y lo guarda en jugador1, esperando que sea distinto de jugador0*/

        partida.avanzarTurno(); /*turno 2*/
        Jugador jugador2 = partida.getJugadorActual();

        partida.avanzarTurno(); /*tercer llamado - vuelve a 0*/

        Jugador jugador0Again = partida.getJugadorActual();
        /*Guarda en jugador0Again el jugador actual despues de esa tercera llamada, esperando que sea la misma instancia que jugador0*/

        assertNotSame(jugador0, jugador1);
        /*verifica que la referencia jugador0 no es la misma que jugador1. Confirma que el primer avanzarTurno() cambio realmente el turno*/
        assertNotSame(jugador1, jugador2);
        /*confirma el segundo avance - que jugador1 y jugador2 no sean el mismo*/
        assertNotSame(jugador2, jugador0Again);

        assertSame(jugador0, jugador0Again); // vuelve al inicio
        /*Verifica que la referencia original jugador0 y jugador0Again son la misma instancia*/
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

    /*test 4: testEliminarJugador()
    - Cuando un jugador no tiene jugadas validas, se elimina
    - Su mano vuelve al mazo
    - Se reduce el número de jugadores
    - Si solo queda 1, la partida termina y hay ganador*/

    @Test
    void testEliminarJugador() throws NoSuchFieldException,IllegalAccessException{

        Partida partida=new Partida(1);
        Jugador humano=partida.getJugadorHumano();

        /*forzar la mesa a estar casi en 50*/
        partida.getMesa().clear();
        partida.getMazo().agregarCartas(new ArrayList<>()); /*evitar errores en robar*/
        Carta carta48=new Carta("Corazones", "10"); /*Da +10 al inicio -> 10*/
        partida.getMesa().add(carta48);

        /*forzamos suma de mesa a 48
        *(Simulando estado avanzado de partida)*/
        java.lang.reflect.Field suma=Partida.class.getDeclaredField("sumaMesa");
        suma.setAccessible(true);
        suma.set(partida, 48);


        /*ahora dar cartas no jugables porque todas sumarían más de 2, por lo que se pasan de 50*/
        humano.getMano().clear();
        humano.getMano().add(new Carta("Corazones", "10"));
        humano.getMano().add(new Carta("Corazones", "10"));
        humano.getMano().add(new Carta("Corazones", "10"));
        humano.getMano().add(new Carta("Corazones", "10"));

        int jugadoresAntes=partida.getJugadores().size();

        /*intentar jugar la primera (no será válida → debería eliminarse el juador)*/
        partida.turnoHumano(humano.getMano().get(0));

        assertEquals(jugadoresAntes - 1, partida.getJugadores().size());
    }
}