
package AdministrationGui;


import Model.CarMarker;
import Model.LabeledMarker;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;


public class AdminMap extends PApplet{
    
    UnfoldingMap map;
    Location AGHLocation;
    Location carLocation;
    MarkerManager markerManager;
    SimplePointMarker carMarker;
    
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
    }
 
    public void up()
    {
        carLocation.add(new PVector(0.0001f,0,0));
        this.repaint();
    }
    
    public void down()
    {
        carLocation.add(new PVector(-0.0001f,0,0));
        this.repaint();
    }
     
    public void left()
    {
        carLocation.add(new PVector(0,-0.0001f,0));
        this.repaint();
    }
      
      
    public void right()
    {
        carLocation.add(new PVector(0,0.0001f,0));
        this.repaint();
    }
       
    public void draw() {
        map.draw();
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
    
   public void addCarparks()
   {
       ArrayList<LabeledMarker> carparks = new ArrayList<LabeledMarker>();
       PFont font = null; 
       
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
