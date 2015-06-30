package Data;
import Model.Vehicle;
import java.util.TreeMap;


public class Simulator {

//******************************************************************************************************************************************//
//	ATRYBUTY																																//
//******************************************************************************************************************************************//
	
	private TreeMap<Integer, Vehicle> vehicles;
	private int hour;
	private int minute;
	
//******************************************************************************************************************************************//
//	KONSTRUKTOR																																//
//******************************************************************************************************************************************//
	
	/**
	 * Konstruktor przyjmuje wygenerowan� list� pojazd�w bior�cych udzia� w symulacji 
	 * Godzina symulacji zostaje ustawiona na 00:00
	 * @param vehicleList
	 */
	public Simulator(TreeMap<Integer, Vehicle> vehicleList){
		vehicles = vehicleList;
		hour = 0;
		minute= 0;
	}

	
//******************************************************************************************************************************************//
//	FUNKCJE U�YTKOWE																														//
//******************************************************************************************************************************************//
	
	public void doIteration(){
		pushTime();
		System.out.println("Godzina: " + hour + ":" + minute);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda wykorzystywana przy symulacji- manipulacja wirtualnym czasem. Inkrementacja minut.
	 */
	private boolean incrementMinute(){
		if(minute < 59){
			++minute;
			return false;
		}else{
			minute = 0;
			return true;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda wykorzystywana przy symulacji- manipulacja wirtualnym czasem. Inkrementacja godzin.
	 */
	private void incrementHour(){
		if(hour < 23){
			++hour;
		}else{
			hour = 0;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda realizuj�ca symulacj� przep�ywu czasu.
	 */
	private void pushTime(){
		if(incrementMinute()){
			incrementHour();
		}
	}

//******************************************************************************************************************************************//
//	SETTERY																																	//
//******************************************************************************************************************************************//
	
	/**
	 * Metoda ustawiaj�ca godziene wirtualnego czasu symulacji.
	 * @param val
	 */
	public void setHour(int val){
		hour =val;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda ustawiaj�ca minute wirtualnego czasu symulacji.
	 * @param val
	 */
	public void setMinute(int val){
		minute = val;
	}
	
//******************************************************************************************************************************************//
//	GETTERY																																	//
//******************************************************************************************************************************************//
	
	/**
	 * Metoda zwraca list� pojazd�w.
	 * @return
	 */
	public TreeMap<Integer, Vehicle> getVehicles(){
		return vehicles;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda zwraca aktualn� godzin� wirtualnego czasu symulacji.
	 * @return
	 */
	public int getHour(){
		return hour;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda zwraca aktualn� minut� wirtuualnego czasu symulacji.
	 * @return
	 */
	public int getMinute(){
		return minute;
	}
	
//******************************************************************************************************************************************//
//	TESTOWANIE																																//
//******************************************************************************************************************************************//
	public static void main(String[] args){
		
		DataGenerator dataGenerator = new DataGenerator();
		Simulator simulator = new Simulator(dataGenerator.generateVehicles(10, new String[]{"aaa","bbb","ccc"}));
		for(int i = 0; i < 3600; i++){
			simulator.doIteration();
		}
	}
}
