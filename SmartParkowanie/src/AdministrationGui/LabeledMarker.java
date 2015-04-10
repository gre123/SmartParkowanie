
package AdministrationGui;

import processing.core.PFont;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import java.awt.Color;

/**
 * Źródło: https://github.com/tillnagel/unfolding/blob/master/examples/de/fhpotsdam/unfolding/examples/marker/labelmarker/LabeledMarker.java
 * A point marker which can show a label containing the marker's name.
 */
public class LabeledMarker extends SimplePointMarker {

	protected String name;
	protected int space = 6;
	private float fontSize = 12;

	public LabeledMarker(Location location, String name) {
		this(location, name, null, 0);
	}

	public LabeledMarker(Location location, String name, PFont font, float size) {
		this.location = location;
		this.name = name;

		if (font != null) {
			this.fontSize = font.getSize();
		}
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
			pg.fill(Color.BLUE.hashCode());
			pg.stroke(Color.RED.hashCode());
		} else {
			pg.fill(Color.BLUE.hashCode());
			pg.stroke(Color.BLUE.hashCode());
		}
		pg.ellipse(x, y, 15, 15);

		// label
		if (selected && name != null) {
			pg.fill(Color.BLUE.hashCode());
			pg.stroke(Color.RED.hashCode());
			pg.rect(x + strokeWeight / 2, y - fontSize + strokeWeight / 2 - space, pg.textWidth(name) + space * 1.5f,
					fontSize + space);
			pg.fill(255, 255, 255);
			pg.text(name, Math.round(x + space * 0.75f + strokeWeight / 2),
					Math.round(y + strokeWeight / 2 - space * 0.75f));
		}
		pg.popMatrix();
		pg.popStyle();
	}

	public String getName() {
		return name;
	}

}