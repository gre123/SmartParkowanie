package Factory;

import java.util.ArrayList;

public interface AbstractMarkerFactory<T> {
    public T generateMarker();
    public ArrayList<T> generateMarkers();
}
