package AdministrationGui;

import Algorithm.CordNode;
import Algorithm.ParkFinder;
import Factory.CarMarkerFactory;
import Factory.ParkMarkerFactory;
import Model.CarMarker;
import Model.LabeledMarker;
import Model.LinePathMarker;
import Model.PathMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import processing.core.PVector;

public class AdminMap extends PApplet {

    UnfoldingMap map;
    CarMarkerFactory carMarkerFactory = new CarMarkerFactory();
    ParkMarkerFactory parkMarkerFactory = new ParkMarkerFactory();
    Location AGHLocation;
    MarkerManager markerManager;
    ParkFinder parkFinder;
    ArrayList<PathMarker> path;
    ArrayList<LinePathMarker> pathLines;
    ArrayList<LabeledMarker> carparks;
    float step = 0.0004f;
    ArrayList<CordNode> parkings;
    ArrayList<CarMarker> allCars = new ArrayList<>();
    
    //============
    CarMarker ourCarMarker;
    //============
    
    @Override
    public void setup() {
        size(1250, 700);
        map = new UnfoldingMap(this, new OpenStreetMap.OpenStreetMapProvider());
        markerManager = map.getDefaultMarkerManager();
        AGHLocation = new Location(50.0660f, 19.9190f);
        allCars = carMarkerFactory.generateMarkers();
        ourCarMarker = carMarkerFactory.generateMarker();
        MapUtils.createDefaultEventDispatcher(this, map);
        
        markerManager.addMarker(ourCarMarker);
        markerManager.addMarkers(allCars);
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
        ourCarMarker.getLocation().add(new PVector(step, 0, 0));
        cleanPath();
        parkFinder.findClosestParking(ourCarMarker.getLocation().getLat(), ourCarMarker.getLocation().getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public void down() {
        ourCarMarker.getLocation().add(new PVector(-step, 0, 0));
        cleanPath();
        parkFinder.findClosestParking(ourCarMarker.getLocation().getLat(), ourCarMarker.getLocation().getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public void left() {
        ourCarMarker.getLocation().add(new PVector(0, -step, 0));
        cleanPath();
        parkFinder.findClosestParking(ourCarMarker.getLocation().getLat(), ourCarMarker.getLocation().getLon(), parkings);
        drawPathToNearestParking(parkFinder.getPath(), parkFinder.getParkingId());
        this.repaint();
    }

    public void right() {
        ourCarMarker.getLocation().add(new PVector(0, step, 0));
        cleanPath();
        parkFinder.findClosestParking(ourCarMarker.getLocation().getLat(), ourCarMarker.getLocation().getLon(), parkings);
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
        carparks = parkMarkerFactory.generateMarkers();
        
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

}
