
package Model;

public class CarPark {
    
        private int id;
        private String name;
        private int capacity;
	private String administrator;
        
    public CarPark(int id, String name, int capacity, String administrator)
    {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.administrator = administrator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
        
        
        
}
