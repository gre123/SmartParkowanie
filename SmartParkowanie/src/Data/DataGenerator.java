package smartparkowanie.simulator;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * Klasa przeznaczona do generowania losowych danych wykorzystywanych przy symulacji inteligentnego parkingu.
 * @author Mateusz
 *
 */
public class DataGenerator {
	
	public static final int STREET_LOAD_LOWER_BOUND = 30;
	public static final int STREET_LOAD_UPPER_BOUND = 200;
	
	public static final int PARKING_LOAD_LOWER_BOUND = 5;
	public static final int PARKING_LOAD_UPPER_BOUND = 300;
	
	public static final int EVENT_DURATION_LOWER_BOUND = 3;
	public static final int EVENT_DURATION_UPPER_BOUND = 300;
	

//******************************************************************************************************************************************//
//	ATRYBUTY																																//
//******************************************************************************************************************************************//
	
	
	
	
//******************************************************************************************************************************************//
//	KONSTRUKTOR																																//
//******************************************************************************************************************************************//

	public DataGenerator(){
		
	}
	
//******************************************************************************************************************************************//
//	FUNKCJE U¯YTKOWE																														//
//******************************************************************************************************************************************//
	
	/**
	 * Metoda generuje wektor pojemnoœci ulic.
	 * zakres definiowany poprzez sta³e STREET_LOAD_LOWER_BOUND i STREET_LOAD_UPPER_BOUND.
	 * @param numberOfEdges  Iloœæ ulic- iloœæ krawedzi w grafie.
	 * @return
	 */
	public int[] generateStreetCapacity(int numberOfEdges){
		
		int[] streetCapacityTable = new int[numberOfEdges];
		Random random = new Random();
		for(int i = 0; i < numberOfEdges; i++){
			streetCapacityTable[i] = (random.nextInt(STREET_LOAD_UPPER_BOUND - STREET_LOAD_LOWER_BOUND) + STREET_LOAD_LOWER_BOUND);
		}
		
		return streetCapacityTable;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda generuje wektor pojemnoœci parkingów.
	 * zakres definiowany poprzez sta³e PARKING_LOAD_LOWER_BOUND i PARKING_LOAD_UPPER_BOUND.
	 * @param numberOfEdges  Iloœæ ulic- iloœæ krawedzi w grafie.
	 * @return
	 */
	public int[] generateCarParksCapacity(int numberOfCarParks){
		
		int[] parkingCapacityTable = new int[numberOfCarParks];
		Random random = new Random();
		for(int i = 0; i < numberOfCarParks; i++){
			parkingCapacityTable[i] = (random.nextInt(PARKING_LOAD_UPPER_BOUND - PARKING_LOAD_LOWER_BOUND) + PARKING_LOAD_LOWER_BOUND); 
		}
		
		return parkingCapacityTable;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	

	/**
	 * Metoda zwraca listê wygenerowanych pojazdów
	 * @param numberOfVehicles - iloœæ pojazdów do wygenerowania.
	 * @return
	 */
	public TreeMap<Integer, Vehicle> generateVehicles(int numberOfVehicles, String[] positions){
		
		Vehicle vehicle;
		TreeMap<Integer, Vehicle> vehicleList = new TreeMap<Integer, Vehicle>();
		Random random = new Random();
		for(int i = 0; i < numberOfVehicles; i++){
			
			vehicle = new Vehicle(i,positions[random.nextInt(positions.length)]);
			vehicleList.put(vehicle.getID(), vehicle);
		}
		
		return vehicleList;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda generuj¹ca plan dnia dla danego pojazdu w witualnym modelu symulacji
	 * @param vehicle			pojazd dla którego generowany bêdzie plan
	 * @param numberofEvents	ilosc wydarzen które maj¹ siê znaleŸæ na liœcie danego poajzdu.
	 */
	private void generateVehicleDayPlan(Vehicle vehicle, int numberOfEvents, String[] places){
		
		Random random = new Random();
		ArrayList<DayEvent> eventList = new ArrayList<DayEvent>();
		DayEvent event;
		int duration;
		
		for(int i = 0; i < numberOfEvents; i++){
			duration = (random.nextInt(EVENT_DURATION_UPPER_BOUND - EVENT_DURATION_LOWER_BOUND) + EVENT_DURATION_LOWER_BOUND); 
			event = new DayEvent(places[random.nextInt(places.length)], duration);
			eventList.add(event);
		}
		
		vehicle.setDayPlan(eventList);
	}

	
//******************************************************************************************************************************************//
//	TESTOWANIE																																//
//******************************************************************************************************************************************//
	
	public static void printTable(int[] table, String info){
		System.out.println(info);
		
		for(int i = 0; i < table.length; i++){
			System.out.print(table[i] + " ");
		}
		System.out.println();
	}
	
	
	public static void main(String[] args){
		
		DataGenerator dataGenerator = new DataGenerator();
		printTable(dataGenerator.generateStreetCapacity(10), "Obci¹zenie ulic:");
		printTable(dataGenerator.generateCarParksCapacity(10), "Obci¹zenie ulic:");
		System.out.println(dataGenerator.generateVehicles(10, new String[]{"aaa","bbb","ccc"}));
	}
}
