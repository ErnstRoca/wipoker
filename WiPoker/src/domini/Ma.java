package domini;

import java.util.ArrayList;

/**
 *
 * @author ernest
 */
public class Ma {

    private ArrayList<Carta> Cartes = new ArrayList<Carta>();
    private Fase fase;
    private byte combinacio;
    private byte valorMesAlt = 0;
    private byte valorDesempat = 0;

    public Ma(ArrayList<Carta> Cartes) {
        this.Cartes = Cartes;
    }

    public ArrayList<Carta> getCartes() {
        return Cartes;
    }

    public void setCartes(ArrayList<Carta> Cartes) {
        this.Cartes = Cartes;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }

    public byte getCombinacio() {
        return combinacio;
    }

    public void setCombinacio(byte combinacio) {
        this.combinacio = combinacio;
    }

    public byte getValorMesAlt() {
        return valorMesAlt;
    }

    public void setValorMesAlt(byte valorMesAlt) {
        this.valorMesAlt = valorMesAlt;
    }

    public byte getValorDesempat() {
        return valorDesempat;
    }

    public void setValorDesempat(byte valorDesempat) {
        this.valorDesempat = valorDesempat;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ma other = (Ma) obj;
        if (this.Cartes != other.Cartes && (this.Cartes == null || !this.Cartes.equals(other.Cartes))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.Cartes != null ? this.Cartes.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String cad = "Mà formada per : \n";
        for (int i = 0; i < Cartes.size(); i++) {
            cad += Cartes.get(i);
        }
        return cad;
    }
}
