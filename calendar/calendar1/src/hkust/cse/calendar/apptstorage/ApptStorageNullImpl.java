package hkust.cse.calendar.apptstorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hkust.cse.calendar.gui.InviteDialog;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

public class ApptStorageNullImpl extends ApptStorage {

	private User defaultUser = null;
	
	public ApptStorageNullImpl( User user )
	{
		defaultUser = user;
	}
	
	public HashMap<Integer,Appt> mAppts=new HashMap<Integer,Appt>();
	int count=0;
	
	
	public void RemoveApptfromtext(Appt appt){
		//delete the text

		String delete;
        delete=Integer.toString(appt.getID());
        try{
        File inputFile = new File("appt_list.txt");
        File tempFile = new File("myTempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
    		String[] splited = trimmedLine.split("\\|");
    		System.out.println(delete);
    		System.out.println(splited[0]);
            if(splited[0].equals(delete)&&defaultUser.ID().equals(splited[6])) continue;
            writer.write(currentLine+"\n");
        }
        writer.close(); 
        reader.close(); 
        inputFile.delete();
        tempFile.renameTo(inputFile);

        }catch (IOException e) {
        // TODO Auto-generated catch block
            System.out.println("cannot open");
            e.printStackTrace();
    }
        //
	}
	public void Invite(Appt appt,String name,Timestamp start, Timestamp end,String line){
		InviteDialog a=new InviteDialog(defaultUser,appt,name,start,end,line);
	}
	public void ReadAppt(){
	    try {
	        Appt[] temp=new Appt[40000];
	        Integer tempid;
	    	BufferedReader fr=new BufferedReader(new FileReader("appt_list.txt"));
	    	String line;
    	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    	    Date parsedTimeStamp;
    	    Timestamp start;
    	    Timestamp end;
    	    int i=0;
    	    int max=0;
    	    boolean invited=false;
    	    
	    	while((line=fr.readLine())!=null){
	    		String[] splited = line.split("\\|");

	    		
	    		if(splited[6].equals(defaultUser.ID())||splited[6].equals("all")||splited[8].equals("isjoint")){
		    		tempid=Integer.parseInt(splited[0]);
		    		temp[i]=new Appt();
		    	    parsedTimeStamp = dateFormat.parse(splited[1]);
		    	    start = new Timestamp(parsedTimeStamp.getTime());
		    	    parsedTimeStamp = dateFormat.parse(splited[2]);
		    	    end = new Timestamp(parsedTimeStamp.getTime());
		    	    TimeSpan temptimespan=new TimeSpan(start,end);
		    	 	
		    	    temp[i].setTimeSpan(temptimespan);
		    		temp[i].setTitle(splited[3]);
		    		temp[i].setInfo(splited[4]);
		    		temp[i].setNoti(Integer.parseInt(splited[5]));
		    		temp[i].setID(tempid);
		    		temp[i].setLoc(splited[7]);
		    		temp[i].setJoint(false);

		    		
		    		if(splited[8].equals("isjoint")){
		    			temp[i].setJoint(true);
		    			int j = 9;
		    			int WaitingandReject=0;
		    			while(j<splited.length){
		    				if(splited[j].equals(defaultUser.ID())){
		    					invited=true;
		    					if(splited[j+1].equals("Waiting")){
		    		    			Invite(temp[i],splited[6],start,end,line);
		    					}

		    				}
	    					if(splited[j+1].equals("Reject"))
	    						WaitingandReject++;
	    					if(splited[j+1].equals("Waiting"))
	    						WaitingandReject++;
		    				if(splited[j+1].equals("Attend"))
		    					temp[i].addAttendant(splited[j]);
		    				j=j+2;

		    			}
		    			if(WaitingandReject!=0){
		    				i++;
				    		if (tempid>max)
				    			max=tempid;
		    				continue;
		    			}
		    		}
		    		if(invited||splited[6].equals(defaultUser.ID()))
		    		mAppts.put(tempid, temp[i]);
		    		i++;
		    		if (tempid>max)
		    			max=tempid;
	    		}
	    	}
	    	count=max;
	    	fr.close();
	    }catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void SaveAppt(Appt appt) {
		 // TODO Auto-generated method stub
		
		
		
		for(Integer key:mAppts.keySet()){
			if(mAppts.get(key).getID()==appt.getID()){
				continue;
			}
			if(appt.TimeSpan().Overlap(mAppts.get(key).TimeSpan())){
				return;
			}
		}

	
		if (appt.getID()!=0){
			RemoveApptfromtext(appt);
		}
		if (appt.getID()==0){
			appt.setID(count+1);
		}
		else
			count--;
		/////////////////////////////////////////////////
	       try {
	          	FileWriter fstream=new FileWriter("appt_list.txt",true);
	              
	          	BufferedWriter out=new BufferedWriter(fstream);
	              out.write(Integer.toString(appt.getID())+"|");
	              TimeSpan ts=appt.TimeSpan();
	              out.write(ts.StartTime().toString()+"|");
	              out.write(ts.EndTime().toString()+"|");
	              out.write(appt.getTitle()+"|");
	              out.write(appt.getInfo()+"|");
	              out.write(appt.getNoti()+"|");
	              if(appt.isPrivate()==false){
	           	   out.write(defaultUser.ID()+"|");}
	              else{
	           	   out.write("all"+"|");
	              }
	              out.write(appt.getALoc()+"|");
	              if(appt.isJoint()){
	            	  out.write("isjoint|");
	            	 
	            		  for(int i=0;i<appt.getWaitingList().size();i++){
	            			  String a=appt.getWaitingList().get(i);
	            		  
	            			  out.write(a+"|");
	            		 // out.write(appt.getWaitingList().pop()+"|");
	            			  out.write("Waiting"+"|");
	            		  }
	            		  for(int i=0;i<appt.getAttendList().size();i++){
	            			  String a=appt.getAttendList().get(i);
	            		  
	            			  out.write(a+"|");
	            		 // out.write(appt.getWaitingList().pop()+"|");
	            			  out.write("Attend"+"|");
	            		  }
	            	  
	              }
	              else{
	            	  out.write("notjoint|");
	              }
	              out.write("\n");
	              out.close();
	              System.out.println("saved");
	    
	      } catch (IOException e) {
	          // TODO Auto-generated catch block
	              System.out.println("cannot open");
	              e.printStackTrace();
	      }
		//////////////////////////////////////////////////
	    if(!appt.isJoint()||appt.getWaitingList().size()==0)
		mAppts.put(appt.getID(),appt);
		//System.out.println(appt.getID());
		count++;
		

	}

	
	
	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		// TODO Auto-generated method stub
		List<Appt> rApptsal = new ArrayList<Appt>();
		if(mAppts.size()!=0){
			int j=0;
			for(Appt i : mAppts.values()){
				if(d.Overlap(i.TimeSpan())){
					j++;
					rApptsal.add(i);
				}
			}
			Appt[] Appts=new Appt[j];
			Appts=rApptsal.toArray(Appts);
			return Appts;
		}
		return null;
	}

	@Override
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		// TODO Auto-generated method stub
		return RetrieveAppts(time);
	}

	@Override
	public Appt RetrieveAppts(int joinApptID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateAppt(Appt appt) {
		// TODO Auto-generated method stub
	}

	
	
	@Override
	public void RemoveAppt(Appt appt) {
		// TODO Auto-generated method stub
		System.out.println("appstoragenulldel");
		RemoveApptfromtext(appt);
        System.out.println(appt.getID());
		mAppts.remove(appt.getID());
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
	
	Location[] _locations;
	
	@Override
	public Location[] getLocationList() {
		// TODO Auto-generated method stub
		return _locations;
	}
	
	@Override
	public void setLocationList(Location[] locations) {
		_locations = locations;
	}
}
