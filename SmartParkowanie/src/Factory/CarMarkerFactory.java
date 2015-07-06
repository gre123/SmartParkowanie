
package Factory;

import Data.DataGenerator;
import Model.Marker.CarMarker;
import Model.Vehicle;
import de.fhpotsdam.unfolding.geo.Location;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class CarMarkerFactory implements AbstractMarkerFactory<CarMarker>{

    DataGenerator dataGenerator= new DataGenerator();
    
    @Override
    public CarMarker generateMarker() {
        return new CarMarker(new Vehicle("Adam Mickiewicz",2,"ulicaJakastam"), new Location(50.0660f, 19.9190f)); //TODOFIXME
    }

    @Override
    public ArrayList<CarMarker> generateMarkers() {
        Random random = new Random();
        ArrayList<CarMarker> cars = new ArrayList<>();
        TreeMap<Integer, Vehicle> carMap = dataGenerator.generateVehicles(10, new String[]{"aaa","bbb","ccc"});
        for(int i =0;i<carMap.size();i++)
        {
            Vehicle actualCar = carMap.get(i);
            cars.add(new CarMarker(actualCar, new Location(50.06665f, 19.91515f + random.nextFloat()/1000))); //TODO FIXME
        }
        return cars;
    }
    
}
