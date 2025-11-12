package com.example.cincuentazogame.model;

public class Carta {
    private final String palo;
    private final String valor;
    private final int puntaje;
    private final String imagenFile; // ej: "2C.png"

    public Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;
        this.puntaje = calcularPuntaje(valor);
        this.imagenFile = buildImageFile(valor, palo); // no guardes la ruta completa
    }

    private int calcularPuntaje(String v) {
        return switch (v) {
            case "A" -> 1;
            case "J", "Q", "K" -> -10;
            case "9" -> 0;
            case "10" -> 10;
            default -> Integer.parseInt(v);
        };
    }

    private String buildImageFile(String valor, String palo) {
        String suit = switch (palo) {
            case "Corazones" -> "H";
            case "Diamantes" -> "D";
            case "Tréboles"  -> "C";
            case "Picas"     -> "S";
            default -> throw new IllegalArgumentException("Palo inválido: " + palo);
        };
        return valor + suit + ".png";
    }

    public String getPalo()      { return palo; }
    public String getValor()     { return valor; }
    public int getPuntaje()      { return puntaje; }
    public String getImagenFile(){ return imagenFile; } // <- nombre de archivo

    @Override
    public String toString() { return valor + " de " + palo + " (" + puntaje + ")"; }
}
