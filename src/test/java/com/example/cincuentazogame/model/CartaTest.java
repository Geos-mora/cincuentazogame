package com.example.cincuentazogame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaTest {

    /*test 1: testValoresNumericos28()
     * Verifica que las cartas con valor numérico entre 2 y 8 devuelven correctamente su mismo valor al calcular el puntaje
     * la suma actual en la mesa debe ser el numero como tal, ya que la carta entre esos rangos no tienen reglas especiales*/

    @Test
    void testValoresNumericos28() {
        Carta c=new Carta("Corazones", "5");
        int valor=c.obtenerValor(30);
        assertEquals(5, valor);
    }

    /*test 2: testAsVale10
    * valida que as valga 10 cuando la suma de la mesa lo permite*/

    @Test
    void testAsVale10() {
        Carta c=new Carta("Picas", "A");
        int valor=c.obtenerValor(35); /*35 + 10 = 45 <= 50*/
        assertEquals(10, valor);
    }


    /*test 3: testAsVale1()
    * valida que as valga 1 cuando la suma de la mesa lo permita*/

    @Test
    void testAsVale1() {
        Carta c = new Carta("Picas", "A");
        int valor = c.obtenerValor(45); /*45 + 10 = 55 > 50*/
        assertEquals(1, valor);
    }

    /*test 4: testNueveVale0()
    *valida que el 9 valga 0*/
    @Test
    void testNueveVale0() {
        Carta c=new Carta("Diamantes", "9");
        assertEquals(0, c.obtenerValor(20));
    }

    /*test 5: testDiezVale10()
    * valida que 10 valga 10*/
    @Test
    void testDiezVale10() {
        Carta c=new Carta("Tréboles", "10");
        assertEquals(10, c.obtenerValor(20));
    }

    /*test 6:  testFigurasValenMenos10()
    * valida que j,q y k valen -10*/
    @Test
    void testFigurasValenMenos10() {
        assertEquals(-10, new Carta("Corazones", "J").obtenerValor(20));
        assertEquals(-10, new Carta("Corazones", "Q").obtenerValor(20));
        assertEquals(-10, new Carta("Corazones", "K").obtenerValor(20));

        /*
        - Crea una instancia de Carta con palo "Corazones" y figura J, Q o K
        - Llama al método obtenerValor(20) sobre esa carta.
        - El 20 es un parámetro que representa la suma actual de la mesa para calcular el valor de la carta.
        - assertEquals(expected, actual) compara el valor esperado (primer argumento, -10)
        con el valor real devuelto por obtenerValor.
        - Si coinciden los valores el assert pasa, si no, falla y el test termina con error*/
    }

    /*test 7:  testImagenFile()
    * verifica que la imagen de la carta sea la correcta*/
    @Test
    void testImagenFile() {
        Carta c=new Carta("Corazones", "7");
        assertEquals("7H.png", c.getImagenFile());
    }


}