package Model;
import Data.DayEvent;
import java.util.ArrayList;


public class Vehicle {

//******************************************************************************************************************************************//
//	ATRYBUTY																																//
//******************************************************************************************************************************************//
	
	private int id;					// unikalny numer pojazdu
	private String position;		// aktualna pozycja pojazdu- ulica na kt�rej si� znajduje(kraw�d� grafu)
	private String destination;		// pozycja do kt�rej zmierza pojazd- wierzcho�ek grafu(parking).
	boolean isParked;				// zmienna okre�laj�ca czy pojazd jest w trasie czy mo�e parkuje
	int waitTime;					// zmienna oznaczaj�ca czas parkowania/postoju
	private String user;                //użytkownik pojazdu

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
        
	private ArrayList<DayEvent> dayPlan;
	int dayPlanIndex;

//******************************************************************************************************************************************//
//	KONSTRUKTOR																																//
//******************************************************************************************************************************************//
	
	public Vehicle(String user, int identifier, String pos){
		
		id = identifier;
		isParked = false;
		position = pos;
		destination = null;
		waitTime = 0;
		this.user = user;
		dayPlan = new ArrayList<DayEvent>();
		dayPlanIndex = 0;
	}
	
//******************************************************************************************************************************************//
//	FUNKCJE U�YTKOWE																														//
//******************************************************************************************************************************************//
	
	public String toString(){
		return "Vehicle: " + id + "\nposition: " + position + "\ndestination: " + destination + "\nisParked: " + isParked + "\nwait Time: " + waitTime + "\n"; 
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	private void incrementDayPlanIndex(){
		if(dayPlanIndex < dayPlan.size()){
			dayPlanIndex++;
		}else{
			dayPlanIndex = 0;
		}
	}
	
//******************************************************************************************************************************************//
//	SETTERY																																	//
//******************************************************************************************************************************************//
	
	public void setIsParked(boolean val){
		isParked = val;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public void setPosition(String pos){
		position = pos;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public void setWaitTime(int wait){
		waitTime = wait;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public void setDestination(String dest){
		destination = dest;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public void setDayPlan(ArrayList<DayEvent> plan){
		dayPlan = plan;
	}

//******************************************************************************************************************************************//
//	SETTERY																																	//
//******************************************************************************************************************************************//

	public boolean getIsParked(){
		return isParked;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public int getID(){
		return id;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public int getwaitTime(){
		return waitTime;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public String getPosition(){
		return position;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public String getDestination(){
		return destination;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public ArrayList<DayEvent> getDayPlan(){
		return dayPlan;
	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	public DayEvent getNextDayEvent(){
		DayEvent event = dayPlan.get(dayPlanIndex);
		incrementDayPlanIndex();
		return event;
	}
}
