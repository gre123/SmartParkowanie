package Algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tomek
 */
public class ParkFinder {

    private Dijkstra dijkstra;
    private ArrayList<Node> nodes;
    private ArrayList<CordNode> cords;
    private ArrayList<Way> ways;
    private ArrayList<CordNode> path;
    private Parser parser;
    private int parkingId;

    public ParkFinder() {
        nodes = new ArrayList<>();
        cords = new ArrayList<>();
        ways = new ArrayList<>();
        parser = new Parser();
        parser.setCords(cords);
        parser.setWays(ways);
        parser.setNodes(nodes);
    }

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public ArrayList<CordNode> getCords() {
        return cords;
    }

    public void setCords(ArrayList<CordNode> cords) {
        this.cords = cords;
    }

    public ArrayList<CordNode> getPath() {
        return path;
    }

    public void setPath(ArrayList<CordNode> path) {
        this.path = path;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public Dijkstra getDijkstra() {
        return dijkstra;
    }

    public void setDijkstra(Dijkstra dijkstra) {
        this.dijkstra = dijkstra;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Way> getWays() {
        return ways;
    }

    public void setWays(ArrayList<Way> ways) {
        this.ways = ways;
    }

    public ArrayList<CordNode> getNodeList() {
        return cords;
    }

    public void setNodeList(ArrayList<CordNode> nodeList) {
        this.cords = nodeList;
    }

    private int findNodeByCord(double lat, double lon) {
        int result = -1;

        CordNode baseNode = new CordNode(1, lat, lon);
        int d = cords.size();
        double min_odl = cords.get(0).minus(baseNode);
        int min_id = cords.get(0).getId();

        for (int i = 1; i < d; i++) {
            double temp_odl = cords.get(i).minus(baseNode);
            if (temp_odl == 0) {
                return cords.get(i).getId();
            }
            if (min_odl > temp_odl) {
                min_id = i;
                min_odl = temp_odl;
            }
        }
        return cords.get(min_id).getId();
    }

    int findNodeById(int id) {
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

    public void findClosestParking(double lat, double lot, ArrayList<CordNode> parkings) {
        path = new ArrayList<>();
        int start = findNodeByCord(lat, lot);
        dijkstra = new Dijkstra();
        nodes.clear();

        try {
            readDistancesFromFile();
        } catch (IOException ex) {
            Logger.getLogger(ParkFinder.class.getName()).log(Level.SEVERE, null, ex);
        }

        dijkstra.find(nodes, start);
        int minDistance = 200000000;
        int closestParkingId = -1;
        int parkingIndex=-1;
        for (int i = 0; i < parkings.size(); i++) {
            int id = findNodeByCord(parkings.get(i).getSzerokosc(), parkings.get(i).getDlugosc());
            id = findNodeById(id);
            if (nodes.get(id).getDroga() < minDistance) {
                minDistance = nodes.get(id).getDroga();
                closestParkingId = id;
                parkingIndex=i;
            }
        }
        if (closestParkingId != -1) {
            path = dijkstra.show_droga(nodes, cords, ways, closestParkingId);
            parkingId=parkingIndex;
        } else {
            path = null;
        }
        
    }

    public void readMap() throws IOException {
        parser.parseOSMUgly();
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

}
