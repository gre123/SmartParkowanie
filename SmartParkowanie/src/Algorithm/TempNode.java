/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * @author Tomek
 */
public class TempNode {

    int id;
    double szerokosc;
    double dlugosc;

    public double getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(double szerokosc) {
        this.szerokosc = szerokosc;
    }

    public double getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(double dlugosc) {
        this.dlugosc = dlugosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    TempNode(int a, double x, double y) {
        id = a;
        dlugosc = y;
        szerokosc = x;
    }

    int minus(TempNode b) {
        double PI = 3.1415;
        double wynik=(sqrt(pow(cos(PI * szerokosc / 180) * (b.dlugosc - dlugosc), 2) + pow((b.szerokosc - szerokosc), 2)) * PI * 12765.274 / 360) * 1000;
        return (int)wynik;
    }

    static TempNode find_node(ArrayList<TempNode> node_list, int a) {
        int d = node_list.size();

        int ip = 0;
        int ik = d - 1;
        int isr;
        while (ip <= ik) {

            isr = (ip + ik) >> 1;// szybkie dzielenie na dwa
            if (node_list.get(isr).getId() == a) {
                // cout<<"1";
                return node_list.get(isr);
            } else if (a < node_list.get(isr).getId()) {
                ik = isr - 1;
            } else {
                ip = isr + 1;
            }
        }
        return new TempNode(0, 0, 0);
    }

}
