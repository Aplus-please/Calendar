package hkust.cse.calendar.unit;

public class Location {
	private String name;
	private int capacity;	//if it is not a group facility, capacity = -1
	public int status;		// if status = -1, not delete
	
	public Location(String newName){
		name = newName;
	}
	public Location(String newName, int c, int s){
		name = newName;
		capacity = c;
		status = s;
	}
	
	public Location() {
		name = "";
		capacity = 10;
		status = -1;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setCapacity(int c){
		capacity = c;
	}
	
	public int getCapacity(){
		return capacity;
	}
	
	public void setName(int c){
		capacity = c;
	}
	
	public int getStatus(){
		return status;
	}
}
