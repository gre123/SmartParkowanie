package Model;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.utils.MapPosition;
import java.awt.Color;
import java.util.List;
import processing.core.PGraphics;

/**
 * @author Tomek
 */
public class LinePathMarker extends SimpleLinesMarker {

    
    @Override
    public void draw(PGraphics pg, List<MapPosition> list) {
        pg.pushStyle();
        pg.pushMatrix();

        pg.fill(Color.RED.hashCode());
        pg.stroke(Color.ORANGE.hashCode());
        pg.strokeWeight(10);
        pg.line(list.get(0).x, list.get(0).y, list.get(1).x, list.get(1).y);

        pg.popMatrix();
        pg.popStyle();
    }
   public LinePathMarker(Location a,Location b){
       super(a,b);
   } 
}
