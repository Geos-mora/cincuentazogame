package com.example.cincuentazogame.model;
import java.util.ArrayList;
import java.util.List;

/*se define la clase*/
/*administra el mazo, los jugadores, las cartas jugadas y la suma de puntos en la mesa*/
public class Partida{

    /*atributos*/
    private Jugador ganador;

    private Mazo mazo;/*almacena el objeto Mazo, donde estan todas las cartas disponibles para repartir o comer*/
    private List<Jugador> jugadores;/*lista que contiene tanto al jugador humano como a las máquinas*/
    private List<Carta> mesa; /*lista con las cartas jugadas durante la partida, en el orden en que se jugaron*/
    private int sumaMesa;/*numero entero que lleva la suma actual de los puntajes de las cartas sobre la mes*/

    /*constructor que recibe el número de jugadores máquina que participarán*/
    /*si se pasa 2, habrá 1 jugador humano + 2 máquinas=3 jugadores en total*/
    public Partida(int cantidadJugadoresMaquina){
        this.mazo=new Mazo();/*crea un nuevo mazo barajado automáticamente*/
        /*inicializa las listas vacías donde se almacenarán los jugadores y las cartas jugadas*/
        this.jugadores=new ArrayList<>();
        this.mesa=new ArrayList<>();

        /*crea jugador humano y lo agrega a la lista de jugadores*/
        jugadores.add(new JugadorHumano("Jugador"));

        /*crear jugadores máquina especificada en el parámetro cantidadJugadoresMaquina y lo agrega a la lista de juagdores*/
        /*FALTA CONECTAR CON LOS BOTONES DE LA PANTALLA DE INICIO*/
        for (int i=1; i<=cantidadJugadoresMaquina; i++){
            jugadores.add(new JugadorMaquina("Máquina "+i));
        }

        /*llama a un metodo privado que configura el inicio del juego: reparte las cartas y coloca la carta inicial en la mesa*/
        prepararPartida();
    }

    /*metodo para preparar la partida*/
    private void prepararPartida(){
        /*1.Repartir 4 cartas a cada jugador*/
        for(Jugador jugador:jugadores){ /*recorre cada jugador (maquina y humano)*/
            for(int i=0; i<4; i++){ /*realiza un segundo bucle para darle 4 cartas al jugador*/
                try{
                    jugador.agregarCarta(mazo.tomarCarta());/*quita y devuelve la primera carta del mazo y la agrega al mazo*/
                }catch (Exception e){
                    System.out.println("No hay suficientes cartas para repartir.");
                } catch (Throwable e){
                    throw new RuntimeException(e);/*si el mazo queda sin cartas lanza una excecpcion*/
                }
            }
        }

        /*2.saca una carta adicional del mazo para colocarla como la carta inicial en la mesa*/
        try{
            Carta cartaInicial=mazo.tomarCarta();/*toma la carta del amzo*/
            mesa.add(cartaInicial);/*añade la carta a lista "mesa"*/
            sumaMesa=cartaInicial.getPuntaje();/*calcula el puntaje y lo guarda en sumaMesa*/

            /*print para pruebas de funcionamiento en consola*/
            System.out.println("Carta inicial: " +cartaInicial);
            System.out.println("Suma inicial de la mesa: " +sumaMesa);

        }catch(Exception e){
            e.printStackTrace();
        } catch (Throwable e){
            throw new RuntimeException(e);
        }
    }

    /*devuelve la lista de jugadores para que otras clases puedan accederlos*/
    public List<Jugador>getJugadores(){
        return jugadores;
    }
    /*devuelve la lista de carts jugadas*/
    public List<Carta>getMesa(){
        return mesa;
    }
    /*devuleve la suma total actual de la mesa*/
    public int getSumaMesa(){
        return sumaMesa;
    }
    /*permite actualizar la suma de la mesa agregando el puntaje de una carta jugada*/
    public void actualizarSumaMesa(int cambio){
        sumaMesa+=cambio;
    }
    /*devuelve el pbjeto "mazo" para acceder desde afuera, lo que permite de robar/comer cartas*/
    public Mazo getMazo(){

        return mazo;
    }

    /*
    * METODOS QUE TENEMOS QUE LLENAR
    * */

    private int indiceJugadorActual=0;
    private boolean partidaTerminada=false;


    /*Devuelve el jugador actual*/
    public Jugador getJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    /*Lógica simple para el turno del jugador humano*/
    public void turnoHumano(){
        Jugador jugador=getJugadorActual();
        if (jugador.esMaquina()) return; /*evita error si no es su turno*/
        if (jugador.getMano().isEmpty()) return;

        Carta carta=jugador.getMano().remove(0);
        mesa.add(carta);
        actualizarSumaMesa(carta.getPuntaje());

        System.out.println(jugador.getNombre() + " jugó: " + carta);

        avanzarTurno();
    }

    /*Lógica simple para el turno de la máquina*/
    public void turnoMaquina() {
        Jugador jugador=getJugadorActual();
        if (!jugador.esMaquina()) return;
        if (jugador.getMano().isEmpty()) return;

        Carta carta=jugador.getMano().remove(0);
        mesa.add(carta);
        actualizarSumaMesa(carta.getPuntaje());

        System.out.println(jugador.getNombre() + " jugó: " + carta);

        avanzarTurno();
    }

    /*Pasa el turno al siguiente jugador*/
    private void avanzarTurno() {
        indiceJugadorActual=(indiceJugadorActual + 1) % jugadores.size();
        if (mesa.size() >= 20) { /*condición provisional de fin*/
            partidaTerminada=true;
            ganador=getJugadorActual(); /*provisional*/
        }
    }

    /*Indica si la partida terminó*/
    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    /*Devuelve el ganador*/

    public Jugador getGanador() {
        return ganador;
    }


}

