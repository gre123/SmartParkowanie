package Algorithm;

/**
 * @author Tomek
 */
public class Node {

    int id;
    int dl;//dlougosc drogi do
    int droga;

    int poprzedni;
    Node next;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDl() {
        return dl;
    }

    public void setDl(int dl) {
        this.dl = dl;
    }

    public int getDroga() {
        return droga;
    }

    public void setDroga(int droga) {
        this.droga = droga;
    }

    public int getPoprzedni() {
        return poprzedni;
    }

    public void setPoprzedni(int poprzedni) {
        this.poprzedni = poprzedni;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    
    
    Node(int d, int id2) {
        dl = d;
        next = null;
        id = id2;
        droga = 200000000;
        poprzedni = 0;
    }

    Node() {
        next = null;
        droga = 200000000;
        poprzedni = 0;
    }
}
