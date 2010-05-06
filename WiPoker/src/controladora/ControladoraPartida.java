/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladora;

import domini.Aposta;
import domini.Baralla;
import domini.Bot;
import domini.Carta;
import domini.Fase;
import domini.Jugador;
import domini.Ma;
import domini.Partida;
import domini.Ronda;
import domini.Taula;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author wida45787385
 */
public class ControladoraPartida {

    private Taula taula;
    private Baralla baralla;
    private Partida partida;
    private ArrayList<Jugador> jugadors;

    public ControladoraPartida(byte maxJugadors) {
        baralla = new Baralla();
        crearBaralla();
        taula = new Taula(maxJugadors, baralla);
        partida = new Partida(Calendar.getInstance());
        taula.setPartidaActual(partida);
        jugadors = partida.getJugadors();
    }

    public void crearBaralla() {
        ArrayList<Carta> cartes = new ArrayList<Carta>();
        for (byte i = 1; i <= 13; i++) {
            cartes.add(new Carta((byte) 0, i));
        }
        for (byte i = 1; i <= 13; i++) {
            cartes.add(new Carta((byte) 1, i));
        }
        for (byte i = 1; i <= 13; i++) {
            cartes.add(new Carta((byte) 2, i));
        }
        for (byte i = 1; i <= 13; i++) {
            cartes.add(new Carta((byte) 3, i));
        }
        baralla.setCartes(cartes);
    }

    public boolean taulaIsFull() {
        boolean completa = taula.getPlaces() - taula.getCadiresOcupades() == 0;
        return completa;
    }

    public void afegirJugador(Jugador nouJugador) {
        if (!taulaIsFull()) {
            jugadors.add(nouJugador);
            taula.setCadiresOcupades((byte) (taula.getCadiresOcupades() + 1));
        } else {
            //implementar metodo no quedanPlazas
        }
    }

    public void iniciarRonda() {
        Ronda novaRonda = new Ronda(0);
        novaRonda.setPartida(partida);
        partida.getRondes().add(novaRonda);
        barallar();
        for (int i = 0; i < 4; i++) {
            gestionarFase(novaRonda);
        }
        novaRonda.setJugadorGuanyadorRonda(determinarGuanyador());
        novaRonda.getJugadorGuanyadorRonda().setFitxesActuals(novaRonda.getPot());
        Fase.setNumFase((byte) 0);
        determinarJugadorsEliminats();
        novaRonda.getFases().clear();
        partida.getRondes().clear();
    }

    public void gestionarFase(Ronda ronda) {
        //Clase fase te dos static: array string nom fases i byte amb el numero de fase
        //Passem al constructor l'string de l'index de la fase
        Fase novaFase = new Fase(Fase.getNomFases()[Fase.getNumFase()]);
        ronda.getFases().add(novaFase);
        novaFase.setRonda(ronda);
        if (Fase.getNumFase() == 1) {
            repartirCartesPrivades();
            determinarCombinacioPreFlop();
            for (Jugador j : jugadors) {
                apostar(j, 0, ronda);
            }
        } else if (Fase.getNumFase() == 2) {
            cremarCartes();
            aixecarCartes((byte) 3);
            determinarCombinacio();
            for (Jugador j : jugadors) {
                apostar(j, 0, ronda);
            }
        } else if (Fase.getNumFase() == 3) {
            cremarCartes();
            aixecarCartes((byte) 1);
            determinarCombinacio();
            for (Jugador j : jugadors) {
                apostar(j, 0, ronda);
            }
        } else if (Fase.getNumFase() == 4) {
            cremarCartes();
            aixecarCartes((byte) 1);
            determinarCombinacio();
            for (Jugador j : jugadors) {
                apostar(j, 0, ronda);
            }
        }

        //Al finalitzar la fase afegir potFase al pot de la ronda
    }

    public void repartirCartesPrivades() {
        for (int i = 0; i <= 1; i++) {
            for (Jugador j : jugadors) {
                j.getMaActual().getCartes().add(baralla.getCartes().get(baralla.getCartesActuals()));
                baralla.setCartesActuals((byte) (baralla.getCartesActuals() - 1));
            }
        }
    }

    public void aixecarCartes(byte numCartes) {
        for (int i = 0; i < numCartes; i++) {
            for (Jugador j : jugadors) {
                j.getMaActual().getCartes().add(baralla.getCartes().get(baralla.getCartesActuals()));
                baralla.setCartesActuals((byte) (baralla.getCartesActuals() - 1));
            }
        }
    }

    public void cremarCartes() {
        baralla.getCartes().remove(baralla.getCartes().size());
    }

    public void barallar() {
        Collections.shuffle(baralla.getCartes());
    }

    public void apostar(Jugador jugador, int quantitat, Ronda ronda) {
        //modificar cuando este hecha gui
        int fase = ronda.getFases().size();
        if (quantitat > ronda.getFases().get(fase).getApostes().get(ronda.getFases().size()).getQuantitat()) {
            jugador.setAposta(new Aposta(jugador, quantitat));
            jugador.setFitxesActuals(jugador.getFitxesActuals() - quantitat);
            ronda.setPot(ronda.getPot() + quantitat);
        }
    }

    private void determinarCombinacioPreFlop() {
        for (Jugador j : jugadors) {
            ArrayList<Carta> cartes = j.getMaActual().getCartes();
            byte combinacio = (byte) (cartes.get(0).equals(cartes.get(1)) ? 1 : 0);
            j.getMaActual().setCombinacio(combinacio);
        }
    }

    private void determinarCombinacio() {
        for (Jugador j : jugadors) {
            ArrayList<Carta> cartes = j.getMaActual().getCartes();
            if (esEscalaReial(cartes)) {
                j.getMaActual().setCombinacio((byte) 10);
            } else if (esEscala(cartes) && sonMateixColor(cartes)) {
                j.getMaActual().setCombinacio((byte) 9);
            } else if (esPoker(cartes)) {
                j.getMaActual().setCombinacio((byte) 8);
            } else if (esParella(cartes) && esTrio(cartes)) {
                j.getMaActual().setCombinacio((byte) 7);
            } else if (sonMateixColor(cartes)) {
                j.getMaActual().setCombinacio((byte) 6);
            } else if (esEscala(cartes)) {
                j.getMaActual().setCombinacio((byte) 5);
            } else if (esTrio(cartes)) {
                j.getMaActual().setCombinacio((byte) 4);
            } else if (esDobleParella(cartes)) {
                j.getMaActual().setCombinacio((byte) 3);
            } else if (esParella(cartes)) {
                j.getMaActual().setCombinacio((byte) 2);
            } else {
                j.getMaActual().setCombinacio((byte) 1);
            }
        }
    }

    private boolean sonMateixColor(ArrayList<Carta> cartes) {
        boolean mateixColor = true;
        byte color = cartes.get(0).getPal();
        for (Carta c : cartes) {
            if (color != c.getPal()) {
                mateixColor = false;
                break;
            }
        }
        return mateixColor;
    }

    private boolean esEscala(ArrayList<Carta> cartes) {
        boolean esEscala = true;
        for (int i = 0; i < cartes.size() - 1; i++) {
            if (!(cartes.get(i).getValor() < cartes.get(i + 1).getValor())) {
                esEscala = false;
                break;
            }
        }
        return esEscala;
    }

    private boolean esEscalaReial(ArrayList<Carta> cartes) {
        boolean esEscala = true;
        for (int i = 10; i <= 13; i++) {
            if (!(cartes.get(i).getValor() < cartes.get(i + 1).getValor())) {
                esEscala = false;
                break;
            }
        }
        esEscala = esEscala && (cartes.get(cartes.size() - 1).getValor() == 13);
        return esEscala;
    }

    private boolean esPoker(Jugador jugador) {
        boolean esPoker = true;
        ArrayList<Carta> cartes = jugador.getMaActual().getCartes();
        int condicioNoEsPoker = 0;
        int diferents = 0;
        if (cartes.size() == 5) {
            condicioNoEsPoker = 2;
        } else if (cartes.size() == 6) {
            condicioNoEsPoker = 3;
        } else if (cartes.size() == 7) {
            condicioNoEsPoker = 4;
        }
        int valor = 0;
        int iguals = 0;
        for (int i = 0; i < cartes.size(); i++) {
            for (int j = i + 1; j < cartes.size() - 1; j++) {
                if (cartes.get(i).equals(cartes.get(j)) && valor == 0) {
                    valor = cartes.get(i).getValor();
                    iguals++;
                } else {
                    diferents++;
                }
            }
            if (diferents == condicioNoEsPoker) {
                esPoker = false;
                break;
            }
        }


        return esPoker;
    }

    private boolean esTrio(Jugador jugador) {
        boolean esTrio = true;
        ArrayList<Carta> cartes = jugador.getMaActual().getCartes();
        int condicioNoEsTrio = 0;
        int diferents = 0;
        if (cartes.size() == 5) {
            condicioNoEsTrio = 3;
        } else if (cartes.size() == 6) {
            condicioNoEsTrio = 4;
        } else if (cartes.size() == 7) {
            condicioNoEsTrio = 5;
        }

        int iguals = 0;
        int valor = 0;
        for (int i = 0; i <
                cartes.size(); i++) {
            boolean cond = valor == 0;
            for (int j = i + 1; j <
                    cartes.size() - 1; i++) {
                if (cartes.get(i).equals(cartes.get(j)) && cond) {
                    iguals++;
                    valor =
                            cartes.get(i).getValor();
                } else if (cartes.get(i).equals(cartes.get(j)) && !cond && valor == cartes.get(i).getValor()) {
                    iguals++;
                } else if (!cartes.get(i).equals(cartes.get(j))) {
                    diferents++;
                    if (diferents == condicioNoEsTrio) {
                        esTrio = false;
                        break;
                    }
                }
            }
        }
        if (iguals == 3) {
            esTrio = true;
            jugador.getMaActual().setCombinacio((byte) 4);
            jugador.getMaActual().setValorMesAlt((byte) valor);
        }

        return esTrio;
    }

    private boolean esDobleParella(Jugador jugador) {
        ArrayList<Carta> cartes = jugador.getMaActual().getCartes();
        int valorParella1 = -1;
        int valorParella2 = -1;
        int valorParella3 = -1;
        int numParelles = 0;
        for (int i = 0; i <
                cartes.size(); i++) {
            for (int j = i + 1; j <
                    cartes.size() - 1; j++) {
                if (cartes.get(i).equals(cartes.get(j))) {
                    if (valorParella1 == -1) {
                        valorParella1 = cartes.get(i).getValor();
                        numParelles++;

                    } else if (valorParella2 == -1 && valorParella1 != -1) {
                        valorParella2 = cartes.get(i).getValor();
                        numParelles++;

                    } else if (valorParella3 == -1 && valorParella2 != -1) {
                        valorParella3 = cartes.get(i).getValor();
                        numParelles++;

                    }


                }
            }
        }
        boolean dobleParella = numParelles >= 2 ? true : false;
        if (dobleParella) {
            if ((valorParella1 > valorParella2) && valorParella1 > valorParella3) {
                jugador.getMaActual().setCombinacio((byte) 3);
                jugador.getMaActual().setValorMesAlt((byte) valorParella1);
            } else if (valorParella2 > valorParella1 && valorParella2 > valorParella3) {
                jugador.getMaActual().setCombinacio((byte) 3);
                jugador.getMaActual().setValorMesAlt((byte) valorParella2);
            } else if (valorParella3 > valorParella1 && valorParella3 > valorParella2) {
                jugador.getMaActual().setCombinacio((byte) 3);
                jugador.getMaActual().setValorMesAlt((byte) valorParella3);
            }

        }
        return dobleParella;
    }

    private boolean esParella(Jugador jugador) {
        boolean esParella = true;
        int condicioNoEsParella = 0;
        int diferents = 0;
        ArrayList<Carta> cartes = jugador.getMaActual().getCartes();
        int valor = -1;
        if (cartes.size() == 5) {
            condicioNoEsParella = 4;
        } else if (cartes.size() == 6) {
            condicioNoEsParella = 5;
        } else if (cartes.size() == 7) {
            condicioNoEsParella = 6;
        }

        for (int i = 0; i <
                cartes.size(); i++) {
            for (int j = i + 1; j <
                    cartes.size(); j++) {
                if (cartes.get(i).equals(cartes.get(j)) && cartes.get(i).getValor() > valor) {
                    valor = cartes.get(i).getValor();
                } else if (!cartes.get(i).equals(cartes.get(j))) {
                    diferents++;
                    if (diferents == condicioNoEsParella) {
                        esParella = false;
                        break;

                    }


                }
            }
        }
        if (esParella) {
            jugador.getMaActual().setCombinacio((byte) 2);
            jugador.getMaActual().setValorMesAlt((byte) valor);
        }

        return esParella;
    }

    public byte cartaMesAlta(ArrayList<Carta> cartes) {
        byte num = 0;
        for (int i = 1; i <=
                2; i++) {
            if (cartes.get(i).getValor() > num) {
                num = cartes.get(i).getValor();
            }

        }
        return num;
    }

    public Jugador desempat() {
        Jugador jugadorAux = jugadors.get(0);
        byte puntuacioMesAlta = cartaMesAlta(jugadorAux.getMaActual().getCartes());
        for (Jugador jugador : jugadors) {
            byte puntuacio = cartaMesAlta(jugador.getMaActual().getCartes());
            if (puntuacio > puntuacioMesAlta) {
                puntuacioMesAlta = puntuacio;
                jugadorAux =
                        jugador;
            }

        }
        return jugadorAux;
    }

    private Jugador determinarGuanyador() {
        ArrayList<Jugador> jugadorsOrdenats = jugadors;
        Comparator c = new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Jugador j1 = (Jugador) o1;
                Jugador j2 = (Jugador) o2;
                return j1.getMaActual().getCombinacio() - j2.getMaActual().getCombinacio();
            }
        };
        Collections.sort(jugadorsOrdenats, c);
        //arreglar para retorno misma combinacion
        return jugadorsOrdenats.get(0);
    }

    private void determinarJugadorsEliminats() {
        for (Jugador j : jugadors) {
            if (j.getFitxesActuals() <= 0) {
                jugadors.remove(j);
            }

        }
    }

    public double calcularAposta(Bot jugador) {
        double aposta = 0.0;
        double valorMa = jugador.getMaActual().getCombinacio();
        double valorCarta = jugador.getMaActual().getValorMesAlt();
        Ronda ultimaRonda = partida.getRondes().get(partida.getRondes().size());
        Fase ultimafase = ultimaRonda.getFases().get(ultimaRonda.getFases().size());
        double apostaMinima = ultimafase.getApostaMinima();
        if (valorMa == 10) {
            //escala reial
            aposta += jugador.getFitxesActuals();
        } else if (valorMa == 9) {
            //escala color
            aposta += jugador.getFitxesActuals();
        } else if (valorMa == 8) {
            //poker
            aposta += jugador.getFitxesActuals();
        } else if (valorMa == 7) {
            //full
            aposta += jugador.getFitxesActuals();
        } else if (valorMa == 6) {
            //color
            aposta += jugador.getFitxesActuals();
        } else if (valorMa == 5) {
            //escala
            aposta += jugador.getFitxesActuals();
        } else if (valorMa == 4) {
            //trio
            if (valorCarta == 13) {
                //trio asos
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 12) {
                //trio K
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 11) {
                //trio Q
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 10) {
                //trio J
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 9) {
                //trio 10
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 8) {
                //trio 9
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 7) {
                //trio 8
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 6) {
                //trio 7
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 5) {
                //trio 6
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 4) {
                //trio 5
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 3) {
                //trio 4
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 2) {
                //trio 3
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 1) {
                //trio 2
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            }

        } else if (valorMa == 3) {
            //doble parella
            if (valorCarta == 13) {
                //Parella asos
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 12) {
                //parella K
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 11) {
                //Parella Q
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 10) {
                //Parella J
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 9) {
                //Parella 10
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 2;
            } else if (valorCarta == 8) {
                //Parella 9
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 3;
            } else if (valorCarta == 7) {
                //Parella 8
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 4;
            } else if (valorCarta == 6) {
                //Parella 7
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 5;
            } else if (valorCarta == 5) {
                //Parella 6
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 5;
            } else if (valorCarta == 4) {
                //Parella 5
                aposta += apostaMinima;
            } else if (valorCarta == 3) {
                //Parella 4
                aposta += apostaMinima;
            } else if (valorCarta == 2) {
                //Parella 3
                aposta += apostaMinima;
            } else if (valorCarta == 1) {
                //Parella 2
                aposta += apostaMinima;
            }

        } else if (valorMa == 2) {
            //parella
            if (valorCarta == 13) {
                //Parella asos
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 12) {
                //parella K
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 11) {
                //Parella Q
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 10) {
                //Parella J
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals();
            } else if (valorCarta == 9) {
                //Parella 10
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 2;
            } else if (valorCarta == 8) {
                //Parella 9
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 3;
            } else if (valorCarta == 7) {
                //Parella 8
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 4;
            } else if (valorCarta == 6) {
                //Parella 7
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 5;
            } else if (valorCarta == 5) {
                //Parella 6
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 5;
            } else if (valorCarta == 4) {
                //Parella 5
                aposta += apostaMinima;
            } else if (valorCarta == 3) {
                //Parella 4
                aposta += apostaMinima;
            } else if (valorCarta == 2) {
                //Parella 3
                aposta += apostaMinima;
            } else if (valorCarta == 1) {
                //Parella 2
                aposta += apostaMinima;
            }

        } else if (valorMa == 1) {
            if (valorCarta == 13) {
                //as
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 5;
            } else if (valorCarta == 12) {
                //K
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 6;
            } else if (valorCarta == 11) {
                //Q
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 7;
            } else if (valorCarta == 10) {
                //J
                aposta += apostaMinima;
                aposta +=
                        jugador.getFitxesActuals() / 8;
            } else if (valorCarta == 9) {
                //10
                aposta += apostaMinima;

            } else if (valorCarta == 8) {
                //9
                //No fa res
            } else if (valorCarta == 7) {
                //8
                //No fa res
            } else if (valorCarta == 6) {
                //7
                //No fa res
            } else if (valorCarta == 5) {
                //6
                //No fa res
            } else if (valorCarta == 4) {
                //5
                //No fa res
            } else if (valorCarta == 3) {
                //4
                //No fa res
            } else if (valorCarta == 2) {
                //3
                //No fa res
            } else if (valorCarta == 1) {
                //2
                //No fa res
            }

        }

        return aposta;
    }
}
