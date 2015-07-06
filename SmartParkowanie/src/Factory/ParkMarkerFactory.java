
package Factory;

import Data.DataGenerator;
import Model.CarPark;
import Model.Marker.LabeledMarker;
import de.fhpotsdam.unfolding.geo.Location;
import java.util.ArrayList;

public class ParkMarkerFactory implements AbstractMarkerFactory<LabeledMarker>{

    DataGenerator dataGenerator = new DataGenerator();
    
    @Override
    public LabeledMarker generateMarker() {
       return new LabeledMarker(new Location(50.06645f, 19.91505f),new CarPark(1, "parking przypadkowy",100,"Zenek"), 1);
    }

    @Override
    public ArrayList<LabeledMarker> generateMarkers() {
        int[] capacities = dataGenerator.generateCarParksCapacity(7);
        ArrayList<LabeledMarker> carParks = new ArrayList<>();
        carParks.add(new LabeledMarker(new Location(50.06682f, 19.91584f), new CarPark(1, "parking 1",capacities[0],"Zenek"), 4));
        carParks.add(new LabeledMarker(new Location(50.06645f, 19.91505f), new CarPark(1, "parking 2",capacities[1],"Zenek"), 4));
        carParks.add(new LabeledMarker(new Location(50.06756f, 19.91384f), new CarPark(1, "parking 3",capacities[2],"Zenek"), 4));
        carParks.add(new LabeledMarker(new Location(50.06642f, 19.91253f), new CarPark(1, "parking 4",capacities[3],"Zenek"), 4));
        carParks.add(new LabeledMarker(new Location(50.06626f, 19.91243f), new CarPark(1, "parking 5",capacities[4],"Zenek"), 4));
        carParks.add(new LabeledMarker(new Location(50.06687f, 19.91960f), new CarPark(1, "parking 6",capacities[5],"Zenek"), 4));
        carParks.add(new LabeledMarker(new Location(50.06576f, 19.91268f), new CarPark(1, "parking 7",capacities[6],"Zenek"), 4));
        return carParks;
    }
    
}
