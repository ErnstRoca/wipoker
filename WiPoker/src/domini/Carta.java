package domini;

/**
 *
 * @author ernest
 */
public class Carta implements Comparable {

    private final byte pal;
    private final byte valor;

    public Carta(byte pal, byte valor) {
        this.pal = pal;
        this.valor = valor;
    }

    public byte getPal() {
        return pal;
    }

    public byte getValor() {
        return valor;
    }

    //Una Carta serà diferent quan el seu pal i el seu valor siguin diferents.
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (this.pal != other.pal) {
            return false;
        }
        if (this.valor != other.valor) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.pal;
        hash = 17 * hash + this.valor;
        return hash;
    }

    @Override
    public String toString() {
        return "Carta amb valor " + valor + " i pal " + pal + "\n";
    }

    public int compareTo(Object o) {
        Carta c = (Carta) o;
        return this.valor - c.getValor();
    }
}
