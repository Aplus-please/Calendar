package hkust.cse.calendar.unit;

public class Location {
	private String name;
	
	public Location(String newName){
		name = newName;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
}
