package AdministrationGui;

import Algorithm.CordNode;
import Algorithm.ParkFinder;
import Model.CarMarker;
import Model.LabeledMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
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


public class AdminMap extends PApplet{
    
    UnfoldingMap map;
    Location AGHLocation;
    Location carLocation;
    MarkerManager markerManager;
    SimplePointMarker carMarker;
    ParkFinder parkFinder;
    
    @Override
    public void setup() {
        size(1250, 700);
        map = new UnfoldingMap(this);
        markerManager = map.getDefaultMarkerManager();
        carLocation = new Location(50.0660f, 19.9190f);
        AGHLocation = new Location(50.0660f, 19.9190f);
        MapUtils.createDefaultEventDispatcher(this, map);
        carMarker = new CarMarker(carLocation, "Adam Mickiewicz", 111111,1);
        markerManager.addMarker(carMarker);
        addCarparks();
        
        
        map.zoomAndPanTo(AGHLocation, 17);
        map.setPanningRestriction(AGHLocation, 0);
        map.setZoomRange(17, 17);
        
        parkFinder=new ParkFinder();
        try {
            parkFinder.readMap();
        } catch (IOException ex) {
            Logger.getLogger(AdminMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
 
    public void up()
    {
        carLocation.add(new PVector(0.0001f,0,0));
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon());
        this.repaint();
    }
    
    public void down()
    {
        carLocation.add(new PVector(-0.0001f,0,0));
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon());
        this.repaint();
    }
     
    public void left()
    {
        carLocation.add(new PVector(0,-0.0001f,0));
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon());
        this.repaint();
    }
      
      
    public void right() 
    {
        carLocation.add(new PVector(0,0.0001f,0));
        parkFinder.findClosestParking(carLocation.getLat(), carLocation.getLon());
        this.repaint();
    }
       
    public void draw() {
        map.draw();
    }
    
    private void drawPathToNearestParking(ArrayList<CordNode> path){
        
        ArrayList<LabeledMarker> dotPath = new ArrayList<>();
        for(int i=0;i>path.size();i++){
         
       dotPath.add(new LabeledMarker(new Location(path.get(i).getSzerokosc(), path.get(i).getDlugosc()), "",1));
       
       for(LabeledMarker park: dotPath)
       {
           park.setColor(Color.CYAN.hashCode());
           park.setStrokeColor(Color.CYAN.hashCode());
           park.setRadius(12);
           park.setSelected(true);
       }
       markerManager.addMarkers(dotPath);
      
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
         switch(e.getKeyCode())
        {
             case KeyEvent.VK_UP:
             {
                 up();
                 break;
             }
             case KeyEvent.VK_DOWN:
             {
                 down();
                 break;
             }
             case KeyEvent.VK_LEFT:
             {
                 left();
                 break;
             }
             case KeyEvent.VK_RIGHT:
             {
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
   public void addCarparks()
   {
       ArrayList<LabeledMarker> carparks = new ArrayList<LabeledMarker>();
       
       carparks.add(new LabeledMarker(new Location(50.06682f, 19.91584f), "parking 1 - miejsce na info:\n\nIlość wolnych miejsc : 30"
               + "\nStatus : Dostępny",4));
       carparks.add(new LabeledMarker(new Location(50.06645f, 19.91505f), "parking 1 - miejsce na info",1));
       carparks.add(new LabeledMarker(new Location(50.06756f, 19.91384f), "parking 3 - miejsce na info",1));
       carparks.add(new LabeledMarker(new Location(50.06642f, 19.91253f), "parking 4 - miejsce na info",1));
       carparks.add(new LabeledMarker(new Location(50.06626f, 19.91243f), "parking 5 - miejsce na info",1));
       carparks.add(new LabeledMarker(new Location(50.06687f, 19.91960f), "parking 1 - miejsce na info:\n\nIlość wolnych miejsc : 30"
               + "\nStatus : Dostępny",4));
       carparks.add(new LabeledMarker(new Location(50.06576f, 19.91268f), "parking 7 - miejsce na info",1));
       
       for(LabeledMarker park: carparks)
       {
           park.setColor(Color.BLUE.hashCode());
           park.setStrokeColor(Color.BLUE.hashCode());
           park.setRadius(15);
           park.setSelected(true);
       }
       markerManager.addMarkers(carparks);
   }

   
   static public void main(String args[]) {
   PApplet.main(new String[] { "AdministrationGui.AdminMap" });
   }
}
