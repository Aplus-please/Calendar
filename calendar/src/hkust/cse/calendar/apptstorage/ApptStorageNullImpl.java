package hkust.cse.calendar.apptstorage;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class ApptStorageNullImpl extends ApptStorage {

	private User defaultUser = null;

	public ApptStorageNullImpl(User user) {
		defaultUser = user;
	}
	
	@Override
	public void SaveAppt(Appt appt) {
		int locationCapacity = appt.getLocation().getCapacity();
		for (Object key : mAppts.keySet()) {
			if (defaultUser.ID().equals(mAppts.get(key).getusername())) {
				if (appt.TimeSpan().Overlap(mAppts.get(key).TimeSpan())) {
					JOptionPane.showMessageDialog(null, "Time crash with another appointment.",
							"Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
//		for (Object key : mAppts.keySet()) {
//				if (appt.getLocation().getName()
//		.equals(mAppts.get(key).getLocation().getName())) {
//				if (appt.TimeSpan().Overlap(mAppts.get(key).TimeSpan())) {
//					if (locationCapacity <= 1) {
//						JOptionPane.showMessageDialog(null, "Over capacity.",
//								"Input Error", JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//					locationCapacity--;
//				}
//
//			}
//		}
		// appt.setID(getusableid());
		mAppts.put(appt.getID(), appt);
		mAppts.remove(0);
		saveApptToTxt();
	}

	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		LinkedList<Appt> tmpApptLL = new LinkedList<Appt>();
		for (Object key : mAppts.keySet()){
			Appt tmpAppt = mAppts.get(key);
			if(tmpAppt.TimeSpan().Overlap(d)){
				tmpApptLL.add(tmpAppt);
			}
		}
		if(tmpApptLL.isEmpty()){
			return null;
		}
		Appt[] apptArray = new Appt[tmpApptLL.size()];
		apptArray = tmpApptLL.toArray(new Appt[tmpApptLL.size()]);
		return apptArray;
	}

	@Override
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		LinkedList<Appt> tmpApptLL = new LinkedList<Appt>();
		defaultUser = entity;
		for (Object key : mAppts.keySet()){
			Appt tmpAppt = mAppts.get(key);
			if((tmpAppt.getusername().equals(defaultUser.ID()) && tmpAppt.TimeSpan().Overlap(time))||
					((tmpAppt.isPrivate())==true) && tmpAppt.TimeSpan().Overlap(time)){
				tmpApptLL.add(tmpAppt);
			}
		}
		if(tmpApptLL.isEmpty()){
			return null;
		}
		Appt[] apptArray = new Appt[tmpApptLL.size()];
		apptArray = tmpApptLL.toArray(new Appt[tmpApptLL.size()]);
		return apptArray;
	}
	
	@Override
	public Appt[] RetrieveAppts(Location location) {
		LinkedList<Appt> tmpApptLL = new LinkedList<Appt>();
		for (Object key : mAppts.keySet()){
			Appt tmpAppt = mAppts.get(key);
			if(tmpAppt.getLocation().getName().equals(location.getName())){
				tmpApptLL.add(tmpAppt);
			}
		}
		if(tmpApptLL.isEmpty()){
			return null;
		}
		Appt[] apptArray = new Appt[tmpApptLL.size()];
		apptArray = tmpApptLL.toArray(new Appt[tmpApptLL.size()]);
		return apptArray;
	}

	@Override
	public Appt RetrieveAppts(int joinApptID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateAppt(Appt appt) {
		// TODO Auto-generated method stub
		mAppts.put(appt.getID(),appt);
		saveApptToTxt();
	}

	@Override
	public void RemoveAppt(Appt appt) {
		// TODO Auto-generated method stub
       mAppts.remove(appt.getID());
       saveApptToTxt();
	}

	@Override
	public User getDefaultUser() {
		// TODO Auto-generated method stub
		return defaultUser;
	}

	@Override
	public void LoadApptFromXml() {
		// TODO Auto-generated method stub

	}
	
    // create other function that you need
	@Override
	public int getusableid() {
		if (mAppts.isEmpty()) {
			usableid = 1;
			return usableid;
		}
		usableid = -1;
		for (Object key : mAppts.keySet()) {
			if (mAppts.get(key).getID() >= usableid) {
				usableid = mAppts.get(key).getID();
			}
		}
		usableid=usableid+1;
		return usableid;
	}
	
	@Override
	public void testing() {
		System.out.println(defaultUser.ID());
		System.out.println("Print all appt start!!");
		for (Object key : mAppts.keySet()) {
			System.out.println(key + " : " + mAppts.get(key).getTitle() + ":"
					+ mAppts.get(key).getusername()
					+ mAppts.get(key).getWaitingList());
		}
		System.out.println("Print all appt end!!");
	}
	
	@Override
	public void testing2() {
//		System.out.println("Print all location start!!");
//		for (Object key : Llist.keySet()) {
//			System.out.println(key + " : " + Llist.get(key));
//		}
//		System.out.println("Print all location end!!");
	}
	
	@Override
	public void setlocationlist(String location)
	{
//		Llist.put(location,location);
//	    System.out.println("Save Location successfully");
//	    testing2();
	}
	//new add
	@Override
	public Boolean timecrash (String username,TimeSpan time)
	{	for (Object key : mAppts.keySet()) {
		if (username.equals(mAppts.get(key).getusername())) {
			if (time.Overlap(mAppts.get(key).TimeSpan())) {
				return true;
			}
		}
	}
	return false;
	}
	
	public boolean locationCrash(Location location, TimeSpan time){
		Appt[] tempApptArray = RetrieveAppts(time);
		if(tempApptArray == null){
			return false;
		}
		for(int i = 0; i < tempApptArray.length; i++){
			if(tempApptArray[i].getLocation().getName().equals(location.getName())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String getusername()
	{
		return defaultUser.ID();
	}
	
	public Appt[] RetrieveInviteAppts(User user){
		LinkedList<Appt> tmpApptLL = new LinkedList<Appt>();
		defaultUser = user;
		for(Object key : mAppts.keySet()){
			if(mAppts.get(key).getWaitingList().contains(user.ID()))
				tmpApptLL.add(mAppts.get(key));
		}
		if(tmpApptLL.isEmpty()){
			return null;
		}
		Appt[] apptArray = new Appt[tmpApptLL.size()];
		apptArray = tmpApptLL.toArray(new Appt[tmpApptLL.size()]);
		return apptArray;
		
	}
	@Override
	public void saveApptToTxt() {
		try {
			File inputFile = new File("appt_list.txt");
			File tempFile = new File("tempFile.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			for (Object key : mAppts.keySet()){
				writer.write(mAppts.get(key).getID()+"|");
				writer.write(mAppts.get(key).getusername()+"|");
				writer.write(mAppts.get(key).getstarttime()+"|");
				writer.write(mAppts.get(key).getendtime()+"|");
				writer.write(mAppts.get(key).getTitle()+"|");
				writer.write(mAppts.get(key).getInfo()+"|");
				writer.write(mAppts.get(key).getJoinID()+"|");
				writer.write(mAppts.get(key).getLocation().getName()+"|");
				writer.write(mAppts.get(key).getLocation().getCapacity()+"|");
				LinkedList<String> tmpWaiting = mAppts.get(key).getWaitingList();
				if(!tmpWaiting.isEmpty()){
					for(int i = 0; i < tmpWaiting.size(); i++){
						writer.write(tmpWaiting.get(i) + ",");
					}
					writer.write("|");
				}else{
					writer.write("EmptyWaitingList" + "|");
				}
				LinkedList<String> tmpAttend = mAppts.get(key).getAttendList();
				if(!tmpAttend.isEmpty()){
					for(int i = 0; i < tmpAttend.size(); i++){
						writer.write(tmpAttend.get(i) + ",");
					}
					writer.write("|");
				}else{
					writer.write("EmptyAttendList" + "|");
				}
				LinkedList<String> tmpReject = mAppts.get(key).getRejectList();
				if(!tmpReject.isEmpty()){
					for(int i = 0; i < tmpReject.size(); i++){
						writer.write(tmpReject.get(i) + ",");
					}
					writer.write("|");
				}else{
					writer.write("EmptyRejectList" + "|");
				}
				writer.write(mAppts.get(key).isPrivate()+"|");
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
}

