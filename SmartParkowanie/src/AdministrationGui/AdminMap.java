package AdministrationGui;

import Algorithm.CordNode;
import Algorithm.ParkFinder;
import Model.CarMarker;
import Model.LabeledMarker;
import Model.LinePathMarker;
import Model.PathMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class AdminMap extends PApplet {

    UnfoldingMap map;
    Location AGHLocation;
    Location carLocation;
    MarkerManager markerManager;
    SimplePointMarker carMarker;
    ParkFinder parkFinder;
    ArrayList<PathMarker> path;
    ArrayList<LinePathMarker> pathLines;
    ArrayList<LabeledMarker> carparks;
    float step = 0.0004f;
    ArrayList<CordNode> parkings;

    @Override
    public void setup() {
        size(1250, 700);
        map = new UnfoldingMap(this, new OpenStreetMap.OpenStreetMapProvider());
        markerManager = map.getDefaultMarkerManager();
        carLocation = new Location(50.0660f, 19.9190f);
        AGHLocation = new Location(50.0660f, 19.9190f);
        MapUtils.createDefaultEventDispatcher(this, map);
        carMarker = new CarMarker(carLocation, "Adam Mickiewicz", 111111, 1);
        markerManager.addMarker(carMarker);
        parkFinder = new ParkFinder();
        parkings = new ArrayList<>();
        path = new ArrayList<>();
        pathLines = new ArrayList<>();
        carparks = new ArrayList<>();
        addCarparks();

        map.zoomAndPanTo(AGHLocation, 17);
        map.setPanningRestriction(AGHLocation, 0);
        map.setZoomRange(17, 17);

        try {
            parkFinder.readMap();
        } catch (IOException ex) {
            Logger.getLogger(AdminMap.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void up() {
        carLocation.add(new PVector(step, 0, 0));
        cleanPath();
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public void down() {
        carLocation.add(new PVector(-step, 0, 0));
        cleanPath();
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public void left() {
        carLocation.add(new PVector(0, -step, 0));
        cleanPath();
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public void right() {
        carLocation.add(new PVector(0, step, 0));
        cleanPath();
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public synchronized void draw() {
        synchronized (this) {
            map.draw();
        }
    }

    private void cleanPath() {
        synchronized (this) {
            for (int i = 0; i < path.size(); i++) {
                markerManager.removeMarker(path.get(i));
            }
            for (int i = 0; i < pathLines.size(); i++) {
                markerManager.removeMarker(pathLines.get(i));
            }
        }
        synchronized (this) {
            path.clear();
            pathLines.clear();
        }
    }

    private void drawPathToNearestParking(ArrayList<CordNode> cordPath, int parkingId) {
        for (int i = 0; i < cordPath.size(); i++) {
            synchronized (this) {
                path.add(new PathMarker(new Location(cordPath.get(i).getSzerokosc(), cordPath.get(i).getDlugosc())));

                path.get(path.size() - 1).setColor(Color.GREEN.hashCode());
                path.get(path.size() - 1).setSize(15);
                path.get(path.size() - 1).setSelected(false);
            }
        }
        for (int i = 0; i < path.size() - 1; i++) {
            path.get(i).setNextNode(path.get(i + 1));
        }
        if (parkingId >= 0) {
            path.get(path.size() - 1).setNextNode(carparks.get(parkingId));
        }
        for (PathMarker mark : path) {
            if (mark.getNextNode() != null) {

                LinePathMarker connectionMarker = new LinePathMarker(mark.getLocation(), mark.getNextNode().getLocation());
                synchronized (this) {
                    pathLines.add(connectionMarker);
                }
                connectionMarker.setColor(Color.ORANGE.hashCode());

            }
        }
        synchronized (this) {
            markerManager.addMarkers(pathLines);
            markerManager.addMarkers(path);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP: {
                up();
                break;
            }
            case KeyEvent.VK_DOWN: {
                down();
                break;
            }
            case KeyEvent.VK_LEFT: {
                left();
                break;
            }
            case KeyEvent.VK_RIGHT: {
                right();
                break;
            }

        }

    }

    @Override
    public void mouseMoved() {
        Marker hitMarker = map.getFirstHitMarker(mouseX, mouseY);
        if (hitMarker != null) {
            hitMarker.setSelected(true);
        } else {
            for (Marker marker : map.getMarkers()) {
                marker.setSelected(false);
            }
        }
    }

    /**
     * Ręczne dodawanie parkingów
     */
    public void addCarparks() {

        carparks.add(new LabeledMarker(new Location(50.06682f, 19.91584f), "parking 1 - miejsce na info:\n\nIlość wolnych miejsc : 30"
                + "\nStatus : Dostępny", 4));
        carparks.add(new LabeledMarker(new Location(50.06645f, 19.91505f), "parking 1 - miejsce na info", 1));
        carparks.add(new LabeledMarker(new Location(50.06756f, 19.91384f), "parking 3 - miejsce na info", 1));
        carparks.add(new LabeledMarker(new Location(50.06642f, 19.91253f), "parking 4 - miejsce na info", 1));
        carparks.add(new LabeledMarker(new Location(50.06626f, 19.91243f), "parking 5 - miejsce na info", 1));
        carparks.add(new LabeledMarker(new Location(50.06687f, 19.91960f), "parking 1 - miejsce na info:\n\nIlość wolnych miejsc : 30"
                + "\nStatus : Dostępny", 4));
        carparks.add(new LabeledMarker(new Location(50.06576f, 19.91268f), "parking 7 - miejsce na info", 1));

        for (LabeledMarker park : carparks) {
            CordNode newCordNode = new CordNode(1, park.getLocation().getLat(), park.getLocation().getLon());
            parkings.add(newCordNode);
            park.setColor(Color.BLUE.hashCode());
            park.setStrokeColor(Color.BLUE.hashCode());
            park.setRadius(20);
            park.setSelected(true);
        }
        markerManager.addMarkers(carparks);
    }

    static public void main(String args[]) {
        PApplet.main(new String[]{"AdministrationGui.AdminMap"});
    }
}
