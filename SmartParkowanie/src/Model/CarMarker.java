
package Model;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import java.awt.Color;
import java.util.Vector;

/**
 * Źródło: https://github.com/tillnagel/unfolding/blob/master/examples/de/fhpotsdam/unfolding/examples/marker/labelmarker/LabeledMarker.java
 * A point marker which can show a label containing the marker's name.
 */
public class CarMarker extends SimplePointMarker {

        private float fontSize = 12;
	private int carId=0;
        private String userName;
        private int space = 6;
        private int lines = 1;
	

	public CarMarker(Location location, String userName, int carId, int lines) {
		this.location = location;
		this.userName = userName;
                this.carId = carId;
                this.lines = lines;
                this.setSelected(true);
	}

	/**
	 * Displays this marker's name in a box.
	 */
	public void draw(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.pushMatrix();
		if (selected) {
			pg.translate(0, 0, 1);
		}
		pg.strokeWeight(strokeWeight);
		if (selected) {
			pg.fill(Color.RED.hashCode());
			pg.stroke(Color.RED.hashCode());
		} else {
			pg.fill(Color.RED.hashCode());
			pg.stroke(Color.RED.hashCode());
		}
		pg.ellipse(x, y, 5, 5);

		// label
		if (selected && carId != 0) {
			pg.fill(Color.RED.hashCode());
			pg.stroke(Color.RED.hashCode());
			pg.rect(x + strokeWeight / 2, y - fontSize + strokeWeight / 2 - space, pg.textWidth(userName) + space * 1.5f,
				fontSize + 2*space*lines);
			pg.fill(255, 255, 255);
			pg.text(userName, Math.round(x + space * 0.75f + strokeWeight / 2),
					Math.round(y + strokeWeight / 2 - space * 0.75f));
		}
		pg.popMatrix();
		pg.popStyle();
	}

	public String gerUser() {
		return userName;
	}
        
        public void moveCar(float x, float y)
        {
           location.add(x,y,0f);
           this.setLocation(location);
        }
}