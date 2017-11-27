package autoestudio;
public class Nodo implements Comparable<Nodo> {
    char id;
    int  distancia   = Integer.MAX_VALUE;
    Nodo siguiente = null;
    Nodo(char x, int d, Nodo s) { id=x; distancia=d; siguiente=s; }
    Nodo(char x) { this(x, 0, null); }
    public int compareTo(Nodo tmp) { return this.distancia-tmp.distancia; }
    public boolean equals(Object o) {
        Nodo tmp = (Nodo) o;
        if(tmp.id==this.id) return true;
        return false;
    }
}