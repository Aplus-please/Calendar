package hkust.cse.calendar.apptstorage;//

import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public abstract class ApptStorage {

	public HashMap<String,Location> mLocations;
	public HashMap<Integer,Appt> mAppts;		//a hashmap to save every thing to it, write to memory by the memory based storage implementation	
	public User defaultUser;	//a user object, now is single user mode without login
	public int mAssignedApptID;	//a global appointment ID for each appointment record
    public int usableid;
    
	public ApptStorage() { // default constructor
		mLocations = new HashMap<String, Location>();
		loadLocationFromTxt();
		mAppts = new HashMap<Integer, Appt>();
		loadApptFromTxt();
		// retrieve mappts from txt here
		
		// retrieve llist from txt here
	}

	private void loadLocationFromTxt() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("location_list.txt"));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				String[] splited = tmp.split("\\|");
				Location tmpLocation = new Location(splited[0], Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
				mLocations.put(tmpLocation.getName(), tmpLocation);		
				System.out.println(tmpLocation.getName() + "and" + tmpLocation);
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

	public void saveLocationToTxt(){
		try {
			File inputFile = new File("location_list.txt");
			File tempFile = new File("tempFile.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			for(Object key : mLocations.keySet()){
				writer.write(mLocations.get(key).getName() + "|");
				writer.write(mLocations.get(key).getCapacity() + "|");
				writer.write(mLocations.get(key).getStatus() + "|");
				writer.write("\n");
			}
			writer.close();
			inputFile.delete();
			tempFile.renameTo(inputFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("cannot open");
			e1.printStackTrace();
		}
	}
	
	private void loadApptFromTxt() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("appt_list.txt"));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				String[] splited = tmp.split("\\|");
				Appt tmpAppt = new Appt();
				tmpAppt.setID(Integer.parseInt(splited[0]));
				tmpAppt.setusername(splited[1]);
				tmpAppt.setstarttime(Timestamp.valueOf(splited[2]));
				tmpAppt.setendtime(Timestamp.valueOf(splited[3]));
				tmpAppt.setTitle(splited[4]);
				tmpAppt.setInfo(splited[5]);
				tmpAppt.setJoinID(Integer.parseInt(splited[6]));
				TimeSpan tempTimeSpan= new TimeSpan(tmpAppt.getstarttime(),tmpAppt.getendtime()); 
				tmpAppt.setTimeSpan(tempTimeSpan);
				
				Location tempLocation= mLocations.get(splited[7]);
				
				tmpAppt.setLocation(tempLocation);
//				tempLocation.getName();
				if (!splited[9].equals("EmptyWaitingList")) {
					String[] splitedWaiting = splited[9].split(",");
					tmpAppt.setWaitingList(splitedWaiting);
				}else{
					String[] nullString = null;
					tmpAppt.setWaitingList(nullString);
				}
				if (!splited[10].equals("EmptyAttendList")) {
					String[] splitedAttend = splited[10].split(",");
					tmpAppt.setAttendList(splitedAttend);
				}else{
					String[] nullString = null;
					tmpAppt.setAttendList(nullString);
				}
				if (!splited[11].equals("EmptyRejectList")) {
					String[] splitedReject = splited[11].split(",");
					tmpAppt.setRejectList(splitedReject);
				}else{
					String[] nullString = null;
					tmpAppt.setRejectList(nullString);
				}
				if (!splited[12].equals("true")) {
					tmpAppt.setprivate(false);
				}else{
					tmpAppt.setprivate(true);
				}
				mAppts.put(tmpAppt.getID(), tmpAppt);
				
			}
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public abstract void SaveAppt(Appt appt);	//abstract method to save an appointment record
    
	public abstract Appt[] RetrieveAppts(TimeSpan d);	//abstract method to retrieve an appointment record by a given timespan

	public abstract Appt[] RetrieveAppts(User entity, TimeSpan time);	//overloading abstract method to retrieve an appointment record by a given user object and timespan
	
	public abstract Appt RetrieveAppts(int joinApptID);					// overload method to retrieve appointment with the given joint appointment id

	public abstract void UpdateAppt(Appt appt);	//abstract method to update an appointment record

	public abstract void RemoveAppt(Appt appt);	//abstract method to remove an appointment record
	
	public abstract User getDefaultUser();		//abstract method to return the current user object
	
	public abstract void LoadApptFromXml();		//abstract method to load appointment from xml reocrd into hash map
	
	/*
	 * Add other methods if necessary
	 */
	public abstract int getusableid();
	
	public abstract void testing();
	
	public abstract void testing2();
	
	public abstract void saveApptToTxt();
    
	public abstract void setlocationlist(String location);
	
	public abstract String getusername();
	
	public abstract Boolean timecrash(String username,TimeSpan time);

	public abstract Appt[] RetrieveInviteAppts(User user);
	
	public abstract boolean locationCrash(Location location, TimeSpan time);
	
	public abstract Appt[] RetrieveAppts(Location location);

	public Appt[] RetrieveAppts(User mCurrUser) {
		LinkedList<Appt> tmpApptLL = new LinkedList<Appt>();
		defaultUser = mCurrUser;
		for(Object key : mAppts.keySet()){
			if(mAppts.get(key).getusername().equals(defaultUser.ID()))
				tmpApptLL.add(mAppts.get(key));
		}
		if(tmpApptLL.isEmpty()){
			return null;
		}
		Appt[] apptArray = new Appt[tmpApptLL.size()];
		apptArray = tmpApptLL.toArray(new Appt[tmpApptLL.size()]);
		return apptArray;
	}

	public Appt[] RetrieveRemovedAppts(User user) {
		LinkedList<Appt> tmpApptLL = new LinkedList<Appt>();
		defaultUser = user;
		for(Object key : mAppts.keySet()){
			if((mAppts.get(key).getusername().equals(defaultUser.ID()))||
				(mAppts.get(key).getWaitingList().contains(defaultUser))||
					(mAppts.get(key).getAttendList().contains(defaultUser))||
						(mAppts.get(key).getRejectList().contains(defaultUser)))
				tmpApptLL.add(mAppts.get(key));
		}
		if(tmpApptLL.isEmpty()){
			return null;
		}
		Appt[] apptArray = new Appt[tmpApptLL.size()];
		apptArray = tmpApptLL.toArray(new Appt[tmpApptLL.size()]);
		return apptArray;
	}
}
