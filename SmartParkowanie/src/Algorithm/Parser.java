package Algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Tomek
 */
public class Parser {

    ArrayList<Node> nodes;
    ArrayList<CordNode> cords;
    ArrayList<Way> ways;
    boolean highway;

    Parser() {
        highway = false;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<CordNode> getCords() {
        return cords;
    }

    public void setCords(ArrayList<CordNode> cords) {
        this.cords = cords;
    }

    public ArrayList<Way> getWays() {
        return ways;
    }

    public void setWays(ArrayList<Way> ways) {
        this.ways = ways;
    }

    public boolean isHighway() {
        return highway;
    }

    public void setHighway(boolean highway) {
        this.highway = highway;
    }

    private void readCordNodes(String linijka) {
        int temp_id = 0;
        double temp_dlugosc = 0;
        double temp_szerokosc = 0;
        boolean right = false;

        int d = linijka.length();
        for (int i = 0; i < d; i++) {
            if (Character.isLetter(linijka.charAt(i))) {
                String temp_String = getWord(linijka, i);
                i = i + temp_String.length();
                if (temp_String.equals("node id")) {
                    i = i + 2;
                    temp_id = getInteger(linijka, i);
                    i = i + Integer.toString(temp_id).length();
                    right = true;
                } else if (temp_String.equals("lat")) {
                    i = i + 2;
                    String tempStr = getDouble(linijka, i);
                    temp_szerokosc = Double.parseDouble(tempStr);
                    i = i + tempStr.length();
                } else if (temp_String.equals("lon")) {
                    i = i + 2;
                    String tempStr = getDouble(linijka, i);
                    temp_dlugosc = Double.parseDouble(tempStr);
                    i = i + tempStr.length();
                }

            }
        }
        if (right) {// jesli nowy werzocholek i prawidlowy dodaj do listy wierzcholkow
            cords.add(new CordNode(temp_id, temp_szerokosc, temp_dlugosc));
        }
    }

    private void readWays(String linijka) {
        int temp_id;
        int temp_ref;
        int d = linijka.length();
        int licznik = 0;

        for (int i = 0; i < d; i++) {
            if (Character.isLetter(linijka.charAt(i))) {
                String temp_String = getWord(linijka, i);
                i = i + temp_String.length();
                if (temp_String.equals("way id")) {
                    i = i + 2;
                    temp_id = getInteger(linijka, i);
                    i = i + Integer.toString(temp_id).length();
                    ways.add(new Way(temp_id));
                } else if (temp_String.equals("nd ref")) {
                    i = i + 2;
                    temp_ref = getInteger(linijka, i);
                    i = i + Integer.toString(temp_ref).length();
                    ways.get(ways.size() - 1).getSciezka().add(temp_ref);
                } else if (temp_String.equals("tag k")) {
                    i += 2;
                    String temp_String2 = getWord(linijka, i);
                    i = i + temp_String2.length();
                    if (temp_String2.equals("highway")) {
                        highway = true;
                        licznik++;
                    } else if (temp_String2.equals("name")) {
                        i += 5;
                        String nazwa = getWord(linijka, i);
                        i = i + nazwa.length();
                        ways.get(ways.size() - 1).setNazwa(nazwa);
                    } else if (temp_String2.equals("oneway")) {
                        i += 5;
                        String tempString4 = getWord(linijka, i);
                        i = i + tempString4.length();
                        if (tempString4.equals("yes")) {
                            ways.get(ways.size() - 1).setOneway(true);
                        }
                    }
                }
                if (temp_String.equals("way") && linijka.charAt(i) == '>') {
                    if (!ways.isEmpty() && highway == false) {
                        ways.remove(ways.size() - 1);
                    }
                    highway = false;
                }

            }

        }
    }

    private String getWord(String s, int i) {
        String wynik = "";
        while (Character.isLetter(s.charAt(i)) || s.charAt(i) == ' ') {
            wynik += s.charAt(i);
            i++;
        }
        return wynik;
    }

    private int getInteger(String s, int i) {
        int wynik = 0;
        while (Character.isDigit(s.charAt(i))) {
            wynik = wynik * 10 + s.charAt(i) - '0';
            i++;
        }
        return wynik;
    }

    private String getDouble(String line, int i) {
        String result = "";
        while (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.' || line.charAt(i) == '.') {
            result += line.charAt(i);
            i++;
        }
        return result;
    }

    private void saveDistances() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("distances.txt");

        int d = ways.size();
        for (int i = 0; i < d; i++) {
            writer.print(make_map(ways.get(i)));
        }

        writer.close();
    }

    private String make_map(Way way) {
        int d = way.sciezka.size() - 1;
        String temp_String = "";

        if (way.isOneway() == true) {
            CordNode prev_node = findCordNodeById(way.sciezka.get(0));
            CordNode next_node = prev_node;
            String temp_droga = "";
            for (int i = 0; i < d; i++) {
                if (next_node.id == 0 || prev_node.id == 0) {
                    return "";
                }

                next_node = findCordNodeById(way.sciezka.get(i + 1));

                temp_droga = Integer.toString(prev_node.minus(next_node)) + ",";
                prev_node = next_node;

                temp_String += "(" + way.sciezka.get(i).toString() + ",";
                temp_String += temp_droga;
                temp_String += way.sciezka.get(i + 1).toString() + ")\n";
            }
        } else {
            CordNode prev_node = findCordNodeById(way.sciezka.get(0));
            CordNode next_node = prev_node;
            String temp_droga = "";

            for (int i = 0; i < d; i++) {
                if (next_node.id == 0 || prev_node.id == 0) {
                    return "";
                }

                next_node = findCordNodeById(way.sciezka.get(i + 1));

                temp_droga = Integer.toString(prev_node.minus(next_node)) + ",";
                prev_node = next_node;
                temp_String += way.sciezka.get(i).toString() + ",";
                temp_String += temp_droga;
                temp_String += (way.sciezka.get(i + 1)).toString() + "\n";
                ///
                temp_String += (way.sciezka.get(i + 1)).toString() + ",";
                temp_String += temp_droga;
                temp_String += (way.sciezka.get(i)).toString() + "\n";

            }
        }
        return temp_String;
    }

    public void parseOSMUgly() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("krakow.osm"));

        String line = br.readLine();
        boolean way = false;// zeby czytal droge
        int i;
        while (line != null) {
            i = 3;

            if (!way && line.charAt(2) == '<' && getWord(line, i).equals("way id")) {
                way = true;
            }
            if (!way) {
                readCordNodes(line);
            } else {
                readWays(line);
            }
            line = br.readLine();
        }

        br.close();

        saveDistances();
        sortDistancesInFile();
        readDistancesFromFile();
        cleanNodeList();
    }

    private void cleanNodeList() {
        for (int i = cords.size() - 1; i >= 0; i--) {
            if (findNodeById(cords.get(i).getId()) == -1) {
                cords.remove(i);
            }
        }
    }

    private void readDistancesFromFile() throws IOException {//funkcjia haszyjaca sie przyda
        BufferedReader br = new BufferedReader(new FileReader("distances.txt"));
        StringBuilder sb = new StringBuilder();

        String s;
        String line = br.readLine();
        while (line != null) {

            int id1, id2, dl;
            int i = 1;

            String[] split = line.split(",");
            if (split.length != 3) {
                line = br.readLine();
                continue;
            }
            id1 = Integer.parseInt(split[0]);
            dl = Integer.parseInt(split[1]);
            id2 = Integer.parseInt(split[2]);

            int indeks = IsAllreadyAdded(nodes, id1);

            if (indeks != 0) {
            } else {
                nodes.add(new Node());
                indeks = nodes.size() - 1;
            }
            nodes.get(indeks).setId(id1);
            Node ntemp;
            ntemp = new Node(dl, id2);
            Node pseek = nodes.get(indeks);
            while (pseek.getNext() != null) {
                pseek = pseek.getNext();
            }
            pseek.setNext(ntemp);
            line = br.readLine();
        }
        br.close();
        Collections.sort(nodes, new ComparatorID());//TODO może nie potrzebne
    }

    private int IsAllreadyAdded(ArrayList<Node> lista, int id) {
        int d = lista.size();
        if (d == 0) {
            return 0;
        }
        if (lista.get(d - 1).getId() == id) {
            return d - 1;
        }
        return 0;
    }

    void sortDistancesInFile() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("mapa_nowa.txt"));
        String line = br.readLine();
        LinkedList<ToSort> listToSort = new LinkedList<>();

        while (line != null) {
            ToSort toSort = new ToSort();
            toSort.setId(getInteger(line, 1));
            toSort.setLinijka(line);
            listToSort.add(toSort);
            line = br.readLine();
        }
        br.close();
        Collections.sort(listToSort, new ComparatorFile());
        PrintWriter writer = new PrintWriter("mapa_nowa.txt", "UTF-8");

        while (listToSort.size() != 0) {
            writer.println(listToSort.getFirst().getLinijka());
            listToSort.removeFirst();
        }
        writer.close();
    }

    private int findNodeById(int id) {
        int start = 0;
        int end = nodes.size() - 1;
        int pivot;
        while (start <= end) {
            pivot = (start + end) >> 1;// szybkie dzielenie na dwa
            if (nodes.get(pivot).getId() == id) {
                return pivot;
            } else if (id < nodes.get(pivot).getId()) {
                end = pivot - 1;
            } else {
                start = pivot + 1;
            }
        }
        return -1;
    }

    private CordNode findCordNodeById(int a) {
        int ip = 0;
        int ik = cords.size() - 1;
        int isr;
        while (ip <= ik) {

            isr = (ip + ik) >> 1;// szybkie dzielenie na dwa
            if (cords.get(isr).getId() == a) {
                return cords.get(isr);
            } else if (a < cords.get(isr).getId()) {
                ik = isr - 1;
            } else {
                ip = isr + 1;
            }
        }
        return new CordNode(0, 0, 0);
    }
}
