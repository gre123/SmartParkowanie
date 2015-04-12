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

    void print() {
        int d = sciezka.size();
        System.out.println("wid " + wid + " ul. " + nazwa + "\n");
        for (int i = 0; i < d; i++) {
            System.out.println(sciezka.get(i) + " -");
        }
    }
}
