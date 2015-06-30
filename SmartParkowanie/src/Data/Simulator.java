package Data;
import Model.Vehicle;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TreeMap;
import javax.swing.JLabel;


public class Simulator {

//******************************************************************************************************************************************//
//	ATRYBUTY																																//
//******************************************************************************************************************************************//
	
	private TreeMap<Integer, Vehicle> vehicles;
        private JLabel clockLabel;
        
        public Calendar calendar = Calendar.getInstance();
        private SimpleDateFormat format = new SimpleDateFormat("hh:mm");
	
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
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
	}
        
        public Simulator(JLabel clockLabel)
        {
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            this.clockLabel = clockLabel;
        }

	
//******************************************************************************************************************************************//
//	FUNKCJE U�YTKOWE																														//
//******************************************************************************************************************************************//
	
	public void doIteration(){
		pushTime();
//		System.out.println("Godzina: " + calendar.get(Calendar.MINUTE) + ":" + minute);
                clockLabel.setText(format.format(calendar.getTime()));
                clockLabel.repaint();
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda wykorzystywana przy symulacji- manipulacja wirtualnym czasem. Inkrementacja minut.
	 */
	private boolean incrementMinute(){
                
		if(calendar.get(Calendar.MINUTE) < 59){

                        calendar.add(Calendar.MINUTE, 1);
			return false;
		}else{
			calendar.set(Calendar.MINUTE, 0);
			return true;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda wykorzystywana przy symulacji- manipulacja wirtualnym czasem. Inkrementacja godzin.
	 */
	private void incrementHour(){
		if(calendar.get(Calendar.MINUTE) < 23){
			calendar.add(Calendar.MINUTE, 1);
		}else{
			calendar.set(Calendar.MINUTE, 0);
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
		calendar.set(Calendar.HOUR, val);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda ustawiaj�ca minute wirtualnego czasu symulacji.
	 * @param val
	 */
	public void setMinute(int val){
		calendar.set(Calendar.MINUTE, val);
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
		return calendar.get(Calendar.HOUR);
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Metoda zwraca aktualn� minut� wirtuualnego czasu symulacji.
	 * @return
	 */
	public int getMinute(){
		return calendar.get(Calendar.MINUTE);
	}
	
//******************************************************************************************************************************************//
//	TESTOWANIE																																//
//******************************************************************************************************************************************//
//	public static void main(String[] args){
//		
//		DataGenerator dataGenerator = new DataGenerator();
//		Simulator simulator = new Simulator(dataGenerator.generateVehicles(10, new String[]{"aaa","bbb","ccc"}));
//		for(int i = 0; i < 3600; i++){
//			simulator.doIteration();
//		}
//	}
}
