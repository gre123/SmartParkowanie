package Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Tomek
 */
public class Dijkstra {

    void find(ArrayList<Node> lista, int aw, int bw) {
        //priority_queue<Queue,vector<Queue>,less<Queue> > kol_pri;
        LinkedList<Queue> kol_pri = new LinkedList<>();
        int ind = find_lista2(lista, aw);
        lista.get(ind).setDroga(0);
        Queue q = new Queue();
        q.setId(lista.get(ind).getId());
        q.setWart(0);
        kol_pri.add(q);//push(Queue{lista[aw].id,0});

        Node temp = lista.get(ind);
        Node pseek = lista.get(ind);
        int postep = 0;

        while (kol_pri.size() != 0) {
            kol_pri.removeLast();
            postep++;
            if ((postep) % 1000 == 0) {
                System.out.println((int)( ((double) (postep)) / lista.size() * 100) + "% ");
            }

            while (pseek.getNext() != null) {
                int indeks = find_lista2(lista, pseek.getNext().getId());

                if (lista.get(indeks).getDroga() > temp.getDroga() + pseek.getNext().getDl()) {
                    lista.get(indeks).setDroga(temp.getDroga() + pseek.getNext().getDl());

                    update_kol(kol_pri, pseek.getNext().getId(), lista.get(indeks).getDroga());//moze zmienic indeks
                    lista.get(indeks).setPoprzedni(temp.getId());
                }
                pseek = pseek.getNext();
            }
            int ind2 = find_lista2(lista, bw);
            //  if (lista.get(ind2).getDroga() < kol_pri.getLast().getWart()) {
            //      break;
            //}//przyspieszacz!!!
            if (kol_pri.size() > 0) {
                temp = pseek = lista.get(find_lista2(lista, kol_pri.getLast().getId()));
            } else {
                break;
            }
        }

    }

     ArrayList<TempNode> show_droga(ArrayList<Node> lista, ArrayList<TempNode> node_list, ArrayList<TempWay> way_list, int bw) {
        int i = find_lista2(lista, bw);
        ArrayList<Integer> droga = new ArrayList<>();
        ArrayList<TempNode> path=new ArrayList<>();
        while (lista.get(i).getPoprzedni() != -1) {
            droga.add(lista.get(find_lista(lista, lista.get(i).getPoprzedni())).getId());//System.out.println(lista.get(i).poprzedni<<endl;
            i = find_lista(lista, lista.get(i).getPoprzedni());
        }

        int d = droga.size();
        String prev_s = "";
        for (i = d - 1; i >= 0; i--) {
            TempNode tn = TempNode.find_node(node_list, droga.get(i));
            path.add(tn);
            System.out.println(tn.getSzerokosc() + " - " + tn.getDlugosc());
        }
        return path;
    }

    int find_lista(ArrayList<Node> lista, int id) {
        int d = lista.size();
        int i;
        for (i = 0; i < d; i++) {
            if (lista.get(i).getId() == id) {
                return i;
            }
        }
        return 0;

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
        boolean found = false;
        int droga2 = 0;
        while (kol_pri.size() != 0 && kol_pri.getLast().getWart() < droga) {
            if (kol_pri.getLast().getId() == id) {
                found = true;
                droga2 = kol_pri.getLast().getWart();
            }
            temp_v.add(kol_pri.getLast());
            kol_pri.removeLast();
        }
        if (!found) {

            Queue q = new Queue();
            q.setId(id);
            q.setWart(droga);
            kol_pri.add(q);

        } else {
            while (kol_pri.size() != 0) {
                if (kol_pri.getLast().getId() == id) {
                    kol_pri.removeLast();
                    break;
                }
            }
        }

        while (temp_v.size() != 0) {
            kol_pri.add(temp_v.getLast());
            temp_v.removeLast();
        }
    }
}
