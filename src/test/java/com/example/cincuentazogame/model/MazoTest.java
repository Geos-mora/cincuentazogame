package com.example.cincuentazogame.model;

import com.example.cincuentazogame.model.excepciones.MazoVacioException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazoTest {
    /*Test 1: testMazoSeCreaCon52Cartas()
     * Verifica que al crear un mazo nuevo se generen exactamente 52 cartas:
     * 4 palos × 13 valores. Además, el mazo debe inicializarse mezclado*/
    @Test
    void testMazoSeCreaCon52Cartas() {
        Mazo mazo=new Mazo();
        assertEquals(52, mazo.cantidadCartas());
    }

    /*test 2: testBarajarCambiaElOrden()
     * Comprueba que el metodo barajar() modifica el orden de las cartas*/

    @Test
    void testBarajarCambiaElOrden() {
        Mazo mazo=new Mazo();

        /*Obtener copia del orden inicial*/
        List<Carta> ordenAntes=new ArrayList<>(mazo.cartas);
        mazo.barajar();
        List<Carta> ordenDespues=new ArrayList<>(mazo.cartas);
        assertNotEquals(ordenAntes, ordenDespues);
    }

    /*test 3: testTomarCartaReduceCantidad()
    *Verifica que tomarCarta() retorna una carta válida y además reduce el número de cartas del mazo en 1*/

    @Test
    void testTomarCartaReduceCantidad() throws Exception, MazoVacioException {
        Mazo mazo=new Mazo();

        int antes=mazo.cantidadCartas();
        Carta carta=mazo.tomarCarta();

        assertNotNull(carta);
        assertEquals(antes - 1, mazo.cantidadCartas());
    }

    /*test 4:
    *Comprueba que el metodo tomarCarta() lanza correctamente la excepción MazoVacioException cuando el mazo esta vacío*/
    @Test
    void testTomarCartaMazoVacioLanzaExcepcion() {
        Mazo mazo=new Mazo();

        /*Vaciar el mazo*/
        for (int i=0; i<52; i++) {
            assertDoesNotThrow(()->mazo.tomarCarta());
        }

        /*debe lanzar la excepción*/
        assertThrows(MazoVacioException.class,()->mazo.tomarCarta());
    }
}