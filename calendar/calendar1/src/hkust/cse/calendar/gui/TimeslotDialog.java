package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeMac;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class TimeslotDialog extends JFrame 
{
	private DefaultListModel<String> listModel;
	
	private JList<String> timeslot;
	private DefaultListModel<String> timelist;
	private JList<String> seltimeslot;
	private DefaultListModel<String> seltimelist;
	private JButton AddBut;
	private JButton RemoveBut;
	private TimeSpan all[];
	private String allstring[];
	private String allststring[];
	private String alledstring[];
	private String selstring[];
	private TimeMac tm = new TimeMac();
	
	
		public TimeslotDialog(String[] part){
			//timeslot
			
			setTitle("Timeslot");
			this.setLayout(new BorderLayout());
			this.setLocationByPlatform(true);
			this.setSize(300,200);
			
			
			Calendar calendar = Calendar.getInstance();
			java.util.Date currenttime = calendar.getTime();
			int Avayear=0;
			int Avamonth=0;
			int Avadate=0;
			
			if(tm.isEnable()){
			Avayear=tm.getYear();
			Avamonth=tm.getMonth();
			Avadate=tm.getDate();
			}
			else{
				Avayear= currenttime.getYear();
				Avamonth= currenttime.getMonth();
				Avadate= currenttime.getDate();
			}
			all=new TimeSpan[40];
			int index=0;
			
				for(int begin=8;begin<18;begin++){
					for(int beginmin=0;beginmin<60;beginmin+=15){
					if(beginmin==45){
						Timestamp st1 = new Timestamp(Avayear,Avamonth,Avadate,begin,beginmin,0,0);
						Timestamp et1 = new Timestamp(Avayear,Avamonth,Avadate,begin+1,0,0,0);
						TimeSpan ava=new TimeSpan(st1,et1);
						all[index]=ava;
						index++;
					}
					
					else{
					Timestamp st = new Timestamp(Avayear,Avamonth,Avadate,begin,beginmin,0,0);
					Timestamp et = new Timestamp(Avayear,Avamonth,Avadate,begin,beginmin+15,0,0);
					TimeSpan ava=new TimeSpan(st,et);
					all[index]=ava;
					index++;}
					}
					}
			
			JLabel timeava = new JLabel("Ava-time");
			allststring=new String[40];
			for(int size=0;size<all.length;size++){
				allststring[size]=all[size].StartTime().toString();
			}
			alledstring=new String[40];
			for(int size=0;size<all.length;size++){
				alledstring[size]=all[size].EndTime().toString();
			}
			allstring=new String[40];
			for(int size=0;size<all.length;size++){
				allstring[size]=all[size].StartTime().toString()+"|"+all[size].EndTime().toString();
			}
			//System.out.println(allstring[0]);
			
			timelist=new DefaultListModel<String>();
			timeslot = new JList<String>(timelist);
			timeslot.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			timeslot.setSelectedIndex(0);
			timeslot.toString();
			JScrollPane pane = new JScrollPane(timeslot);
			if(allstring.length!=0){
				for(int i=0;i<allstring.length;i++){
					 timelist.addElement(allstring[i]);
				}
			}
			AddBut = new JButton("Add");
			RemoveBut = new JButton("Remove");
			
			//checking timeslot
			if(part.length!=0){
			try{
	    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    		Date parsedTimeStamp;
	      	    Timestamp start;
	      	    Timestamp end;
	  			String line;
	  			int st=0;
	  			int ed=0;
	  			int count=0;
	  	    	BufferedReader fr=new BufferedReader(new FileReader("appt_list.txt"));
	  	    	while((line=fr.readLine())!=null){
	  	    		String[] splited = line.split("\\|");
	  	    		for(int i=0;i<part.length;i++){
	  	    			if(splited[6].equals(part[i])){
	  	    				//System.out.println(splited[6]);
	  	    				//System.out.println(part[i]);
	  	    				parsedTimeStamp = dateFormat.parse(splited[1]);
	  	    				start = new Timestamp(parsedTimeStamp.getTime());
	  	    				parsedTimeStamp = dateFormat.parse(splited[2]);
	  	    				end = new Timestamp(parsedTimeStamp.getTime());
	  	    				TimeSpan temptimespan=new TimeSpan(start,end);
			    	    
	  	    				String startstring=temptimespan.StartTime().toString();
	  	    				String endstring=temptimespan.EndTime().toString();
			    	   
	  	    				
			    	    for(int stindex=0;stindex<allststring.length;stindex++){
					    	if(allststring[stindex].equals(startstring)){
					    			st=stindex;
					    			count++;
					    		}
			    	    }	
			    	    for(int edindex=0;edindex<alledstring.length;edindex++){
					    	if(alledstring[edindex].equals(endstring)){
					    			ed=edindex;
					    		}
					    	
			    	    }	
			    	    
			    	    	if(count!=0){
			    	    	timelist.removeRange(st, ed);
			    	    	allstring=new String[timelist.size()];
			    	    	timelist.copyInto(allstring);
			    	    	
			    	    	allststring=new String[allstring.length];
			    			for(int size=0;size<allstring.length;size++){
			    				line=allstring[size];
			    				String[] splited1 = line.split("\\|");
			    				allststring[size]=splited1[0];
			    			}
			    			alledstring=new String[allstring.length];
			    			for(int size=0;size<allstring.length;size++){
			    				line=allstring[size];
			    				String[] splited1 = line.split("\\|");
			    				alledstring[size]=splited1[1];
			    			}}
			    	    	
			    			
	  	    				}
	  	    			}
	  	    		}
			    	    fr.close();
	  	    	
	  		
	  	    }catch (FileNotFoundException e1) {
	          // TODO Auto-generated catch block
	          e1.printStackTrace();
	  		} catch (IOException e1) {
	  		// TODO Auto-generated catch block
	  		e1.printStackTrace();
	    	  } catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			//check end
				
			seltimelist=new DefaultListModel<String>();
			seltimeslot = new JList<String>(seltimelist);
			seltimeslot.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			seltimeslot.setSelectedIndex(0);
			seltimeslot.toString();
			JScrollPane selpane = new JScrollPane(seltimeslot);
			
			AddBut.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			    	  for(int i=0;i<seltimelist.size();i++){
			    	  if(seltimelist.getElementAt(i).equals(timeslot.getSelectedValue())){
			    		  return;
			    	  }}
			    	  seltimelist.addElement(timeslot.getSelectedValue());
			    	  
			    	  try {
				        	FileWriter fstream=new FileWriter("TimeSpan.txt",true);
				            
				        	BufferedWriter out=new BufferedWriter(fstream);
				            out.write(timeslot.getSelectedValue()+"\n");
				            out.close();
				            System.out.println("saved");
				  
				    } catch (IOException e1) {
				        // TODO Auto-generated catch block
				            System.out.println("cannot open");
				            e1.printStackTrace();}
				      
			      } 
			      
			      
			      });
			 RemoveBut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int i = seltimeslot.getSelectedIndex();
					String delete;
			        delete=seltimelist.elementAt(i);
					seltimelist.removeElementAt(i);;
					 try{
					        File inputFile = new File("TimeSpan.txt");
					        File tempFile = new File("tempTimeSpan.txt");
					        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
					        String currentLine;
					        while((currentLine = reader.readLine()) != null) {
					            // trim newline when comparing with lineToRemove
					            if(currentLine.equals(delete)) continue;
					            writer.write(currentLine+"\n");
					        }
					        writer.close(); 
					        reader.close(); 
					        inputFile.delete();
					        tempFile.renameTo(inputFile);

					        }catch (IOException e1) {
					        // TODO Auto-generated catch block
					            System.out.println("cannot open");
					            e1.printStackTrace();
					    }
				}
				});
			add(pane,BorderLayout.NORTH);
			add(AddBut, BorderLayout.EAST);
		    add(RemoveBut, BorderLayout.WEST);
		    add(selpane, BorderLayout.SOUTH);
			
			pack();
			setVisible(true);
			
	}
		
	
}
