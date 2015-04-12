package Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Tomek
 */
public class Dijkstra {

    void find(ArrayList<Node> lista, int aw, int bw) {
        //priority_queue<Queue,vector<Queue>,less<Queue> > kol_pri;
        LinkedList<Queue> kol_pri = new LinkedList<Queue>();

        lista.get(aw).setDroga(0);
        Queue q = new Queue();
        q.setId(lista.get(aw).getId());
        q.setWart(0);
        kol_pri.push(null);//push(Queue{lista[aw].id,0});

        Node temp = lista.get(aw);
        Node pseek = lista.get(aw);
        int bleble = 0;

        while (kol_pri.size() != 0) {
            if ((bleble++) % 1000 == 0) {
                System.out.println((int) ((double) (bleble)) / lista.size() * 100 + "% ");
            }
            // kolejka.pop_back();//sortowanie musi byc malejace

            while (pseek.getNext() != null) {
                int indeks = find_lista2(lista, pseek.getNext().getId());

                if (lista.get(indeks).getDroga() > temp.getDroga() + pseek.next.getDl()) {
                    lista.get(indeks).setDroga(temp.getDroga() + pseek.next.getDl());

                    update_kol(kol_pri, pseek.next.id, lista.get(indeks).getDroga());//moze zmienic indeks

                    lista.get(indeks).setPoprzedni(temp.getId());
                }
                pseek = pseek.getNext();
            }

            if (lista.get(bw).getDroga() < kol_pri.getLast().getWart()) {
                break;
            }//przyspieszacz!!!

            temp = pseek = lista.get(find_lista2(lista, kol_pri.getLast().getId()));
            kol_pri.pop();//usuniecie pierwszego
        }

    }

    int find_lista2(ArrayList<Node> lista, int id) {
        int d = lista.size();

        int ip = 0;
        int ik = d - 1;
        int isr;
        while (ip <= ik) {
            isr = (ip + ik) >> 1;// szybkie dzielenie na dwa
            if (lista.get(isr).getId() == id) {
                return isr;
            } else if (id < lista.get(isr).getId()) {
                ik = isr - 1;
            } else {
                ip = isr + 1;
            }
        }
        return 0;
    }

    void update_kol(LinkedList<Queue> kol_pri, int id, int droga) {
        LinkedList<Queue> temp_v = new LinkedList<>();

        while (kol_pri.size() != 0 && kol_pri.getLast().id != id) {
            temp_v.add(kol_pri.getLast());
            kol_pri.pop();
        }
        if (kol_pri.size() == 0) {
            Queue q = new Queue();
            q.setId(id);
            q.setWart(droga);
            kol_pri.push(q);
        } else {
            Queue temp_elk = kol_pri.getLast();
            kol_pri.pop();
            temp_elk.wart = droga;
            kol_pri.push(temp_elk);
        }

        while (temp_v.size() != 0) {
            kol_pri.push(temp_v.getLast());
            temp_v.removeLast();
        }
    }
}
