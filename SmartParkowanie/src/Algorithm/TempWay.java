package Algorithm;

import java.util.ArrayList;

/**
 * @author Tomek
 */
public class TempWay {

    int wid;
    String nazwa;
    ArrayList<Integer> sciezka;

    boolean oneway;

    TempWay(int a) {
        wid = a;
        oneway = false;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public ArrayList<Integer> getSciezka() {
        return sciezka;
    }

    public void setSciezka(ArrayList<Integer> sciezka) {
        this.sciezka = sciezka;
    }

    public boolean isOneway() {
        return oneway;
    }

    public void setOneway(boolean oneway) {
        this.oneway = oneway;
    }

    void print() {
        int d = sciezka.size();
        System.out.println("wid " + wid + " ul. " + nazwa + "\n");
        for (int i = 0; i < d; i++) {
            System.out.println(sciezka.get(i) + " -");
        }
    }
}
