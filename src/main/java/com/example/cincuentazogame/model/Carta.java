package com.example.cincuentazogame.model;

/*definicion de la clase*/
public class Carta{
    /*atributos*/
    private String palo;
    private String valor;
    private int puntaje;
    private String imagen;

    /*contructor*/
    public Carta(String palo,String valor){
        this.palo=palo;
        this.valor=valor;
        this.puntaje=calcularPuntaje(valor);

        /*asignar carta con la imagen - FALTA LOGICA AQUI para asignar imagen y mostrar*/
        this.imagen="/com/example/cincuentazogame/view/images/"+valor+"_de_"+palo.toLowerCase()+".png";
}
    private int calcularPuntaje(String valor){
        switch (valor) {
            case "A":
                return 1;
            case "J":
            case "Q":
            case "K":
                return -10;
            case "9":
                return 0;
            case "10":
                return 10;
            default:
                return Integer.parseInt(valor);
        }
    }
    /*getters - metodos publucos para obtener el valor privado de los atributos*/
    public String getPalo(){return palo;}
    public String getValor(){return valor;}
    public int getPuntaje(){return puntaje;}
    public String getImagen(){return imagen;}

    @Override
    /*punto validador para validar funcinamiento de la carta en consola*/
    public String toString(){
        return valor+" de "+palo+" (" +puntaje+ ")";
    }
}

