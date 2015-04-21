package Model;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import java.awt.Color;
import processing.core.PGraphics;

/**
 * @author Tomek
 */
public class PathMarker extends SimplePointMarker {

    protected String name;
    protected int size=10;
    protected SimplePointMarker nextNode;

    public PathMarker(Location location) {
        this.location = location;
    }

    /**
     * Displays this marker's name in a box.
     */
    @Override
    public void draw(PGraphics pg, float x, float y) {
        pg.pushStyle();
        pg.pushMatrix();
        pg.strokeWeight(strokeWeight);
        if (selected) {
            pg.fill(Color.GREEN.hashCode());
            pg.stroke(Color.GREEN.hashCode());
        } else {
            pg.fill(Color.GREEN.hashCode());
            pg.stroke(Color.GREEN.hashCode()+1);
        }
        
        pg.ellipse(x, y, size, size);

        pg.popMatrix();
        pg.popStyle();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SimplePointMarker getNextNode() {
        return nextNode;
    }

    public void setNextNode(SimplePointMarker nextNode) {
        this.nextNode = nextNode;
    }

}
