package com.example.cincuentazogame.model;

public class Carta {
    private final String palo;
    private final String valor;
    private final int puntaje;
    private final String imagenFile; // ej: "2C.png"

    public Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;
        this.puntaje = 0;
        this.imagenFile = buildImageFile(valor, palo);
    }

    public int obtenerValor(int sumaActualMesa) {
        if ("A".equals(valor)) {
            return (sumaActualMesa + 10 <= 50) ? 10 : 1;
        }
        if ("9".equals(valor))  return 0;
        if ("10".equals(valor)) return 10;
        if ("J".equals(valor) || "Q".equals(valor) || "K".equals(valor)) return -10;
        return Integer.parseInt(valor); // 2–8
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
