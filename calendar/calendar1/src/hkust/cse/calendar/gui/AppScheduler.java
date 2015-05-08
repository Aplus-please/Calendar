package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class AppScheduler extends JDialog implements ActionListener,
		ComponentListener {

	
	private JLabel yearL;
	private JTextField yearF;
	private JLabel monthL;
	private JTextField monthF;
	private JLabel dayL;
	private JTextField dayF;
	private JLabel sTimeHL;
	private JTextField sTimeH;
	private JLabel sTimeML;
	private JTextField sTimeM;
	private JLabel eTimeHL;
	private JTextField eTimeH;
	private JLabel eTimeML;
	private JTextField eTimeM;
	private JLabel group;

	private DefaultListModel<String> userlist;
	private DefaultListModel<String> attenduserlist;
	private DefaultListModel model;
	private JTextField titleField;

	private JButton saveBut;
	private JButton CancelBut;
	private JButton inviteBut;
	private JButton rejectBut;
	private JButton AddBut;
	private JButton RemoveBut;
	private JButton TimeBut;
	private JList<String> listuser;
	private JList<String> listattend;
	
	private Appt NewAppt;
	private Appt[] appta;
	private CalGrid parent;
	private boolean isNew = true;
	private boolean isChanged = true;
	private boolean isJoint = false;

	private JTextArea detailArea;

	private JSplitPane pDes;
	JPanel detailPanel;
	JPanel groupPanel;

//	private JTextField attendField;
//	private JTextField rejectField;
//	private JTextField waitingField;
	private int selectedApptId = -1;
	private JComboBox locField;
	private JComboBox userField;
	private JComboBox Frequency;
	private JComboBox privacy;
	private JTextField NotiField;
	private JCheckBox jbox;
	private String pptID[];
	private TimeSpan jointtime;
	
	

	private void commonConstructor(String title, CalGrid cal) {
		parent = cal;
		this.setAlwaysOnTop(true);
		setTitle(title);
		setModal(false);

		Container contentPane;
		contentPane = getContentPane();
		
		JPanel pDate = new JPanel();
		Border dateBorder = new TitledBorder(null, "DATE");
		pDate.setBorder(dateBorder);

		yearL = new JLabel("YEAR: ");
		pDate.add(yearL);
		yearF = new JTextField(6);
		pDate.add(yearF);
		monthL = new JLabel("MONTH: ");
		pDate.add(monthL);
		monthF = new JTextField(4);
		pDate.add(monthF);
		dayL = new JLabel("DAY: ");
		pDate.add(dayL);
		dayF = new JTextField(4);
		pDate.add(dayF);

		JPanel psTime = new JPanel();
		Border stimeBorder = new TitledBorder(null, "START TIME");
		psTime.setBorder(stimeBorder);
		sTimeHL = new JLabel("Hour");
		psTime.add(sTimeHL);
		sTimeH = new JTextField(4);
		psTime.add(sTimeH);
		sTimeML = new JLabel("Minute");
		psTime.add(sTimeML);
		sTimeM = new JTextField(4);
		psTime.add(sTimeM);

		JPanel peTime = new JPanel();
		Border etimeBorder = new TitledBorder(null, "END TIME");
		peTime.setBorder(etimeBorder);
		eTimeHL = new JLabel("Hour");
		peTime.add(eTimeHL);
		eTimeH = new JTextField(4);
		peTime.add(eTimeH);
		eTimeML = new JLabel("Minute");
		peTime.add(eTimeML);
		eTimeM = new JTextField(4);
		peTime.add(eTimeM);

		JPanel pTime = new JPanel();
		pTime.setLayout(new BorderLayout());
		pTime.add("West", psTime);
		pTime.add("East", peTime);

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setBorder(new BevelBorder(BevelBorder.RAISED));
		top.add(pDate, BorderLayout.NORTH);
		top.add(pTime, BorderLayout.CENTER);

		contentPane.add("North", top);

		JPanel titleAndTextPanel = new JPanel();
		JLabel titleL = new JLabel("TITLE");
		titleField = new JTextField(15);
		titleAndTextPanel.add(titleL);
		titleAndTextPanel.add(titleField);
		
		Location[] locations = cal.controller.getLocationList();
		String[] LocText=null;
		if(locations==null){
			locations =new Location[0];
			LocText = new String[0];
		}
		int j = locations.length;
		LocText = new String[j];
		for(int i=0;i<j;i++){
	
			LocText[i]=locations[i].getName();
		}
		
		JLabel locationL = new JLabel("LOCATION");
		locField = new JComboBox(LocText);
		locField.addActionListener(this);
		locField.setPreferredSize(new Dimension(130,30));
		titleAndTextPanel.add(locationL);
		titleAndTextPanel.add(locField);
		
		String[] Freq ={"one-time","daily","weekly","monthly"};
		JLabel FreqL = new JLabel("Frequency");
		Frequency = new JComboBox(Freq);
		titleAndTextPanel.add(FreqL);
		titleAndTextPanel.add(Frequency);
		
		String[] pri ={"private","public"};
		JLabel priL = new JLabel("privacy");
		privacy = new JComboBox(pri);
		titleAndTextPanel.add(priL);
		titleAndTextPanel.add(privacy);
		
		//Notification button, in AppScheduler
		JLabel NotiL = new JLabel("Notification");
		NotiField = new JTextField(2);
		JLabel NotiMin = new JLabel("Minute(s) before");
		NotiField.setEnabled(false);
        final JCheckBox cbox = new JCheckBox("", false);
        ItemListener itemListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent ie)
            {
            	NotiField.setEnabled(ie.getStateChange() == ItemEvent.SELECTED);
            }
        };
        cbox.addItemListener(itemListener);
		titleAndTextPanel.add(NotiL);
		titleAndTextPanel.add(NotiField);
		titleAndTextPanel.add(NotiMin);
        titleAndTextPanel.add (cbox);
		
        
    
    /*  groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());
		Border groupBorder = new TitledBorder(null, "Gourp Event");
		groupPanel.setBorder(groupBorder);
		group = new JLabel("Joint?");
		groupPanel.add(group);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBorder(new BevelBorder(BevelBorder.RAISED));
		bottom.add(groupPanel, BorderLayout.NORTH);
		

		contentPane.add("East", bottom);*/
		
		detailPanel = new JPanel();
		detailPanel.setLayout(new BorderLayout());
		Border detailBorder = new TitledBorder(null, "Appointment Description");
		detailPanel.setBorder(detailBorder);
		detailArea = new JTextArea(20, 30);

		detailArea.setEditable(true);
		JScrollPane detailScroll = new JScrollPane(detailArea);
		detailPanel.add(detailScroll);

		pDes = new JSplitPane(JSplitPane.VERTICAL_SPLIT, titleAndTextPanel,
				detailPanel);

		top.add(pDes, BorderLayout.SOUTH);

		if (NewAppt != null) {
			detailArea.setText(NewAppt.getInfo());

		}
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));

//		inviteBut = new JButton("Invite");
//		inviteBut.addActionListener(this);
//		panel2.add(inviteBut);
		String[] UserText=new String[100];
		int i=0;
		try{
			
			String line;
	    	BufferedReader fr=new BufferedReader(new FileReader("user_list.txt"));
	    	while((line=fr.readLine())!=null){
	    		String[] splited = line.split("\\|");
	    		if(!(splited[0].toString().equals(cal.getCurrUser().toString()))){
	    		UserText[i]=splited[0];
	    		i++;}
	    	}
	    	fr.close();
		}
		catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		jbox = new JCheckBox("", false);
		 ItemListener itemListener1 = new ItemListener()
	        {
	            public void itemStateChanged(ItemEvent ie)
	            {
	            	AddBut.setEnabled(ie.getStateChange() == ItemEvent.SELECTED);
	            	
	            }
	        };
	    jbox.addItemListener(itemListener1);
		group = new JLabel("Joint?");
		JLabel inviteL = new JLabel("User");
		JLabel ptL = new JLabel("Participants:");
		userField = new JComboBox(UserText);
		AddBut = new JButton("Add");
		RemoveBut = new JButton("Remove");
		TimeBut = new JButton("Ava-time");
		
		//Jist participants
		
		userlist = new DefaultListModel<String>();
		AddBut.setEnabled(false);
		TimeBut.setEnabled(false);
		listuser = new JList<String>(userlist);
		listuser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listuser.setSelectedIndex(0);
		listuser.toString();
		listuser.setPreferredSize(new Dimension(130,30));
		JScrollPane pane = new JScrollPane(listuser);
		//if()
		JLabel att = new JLabel("Attendants:");
		attenduserlist = new DefaultListModel<String>();
		listattend = new JList<String>(attenduserlist);
		listattend.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listattend.setSelectedIndex(0);
		listattend.toString();
		listattend.setPreferredSize(new Dimension(130,30));
		JScrollPane pane1 = new JScrollPane(listattend);
		
		
		TimeBut.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  String check[]=new String[(pptID.length+1)];
		    	  for(int length=0;length<pptID.length;length++){
		    	  check[0]=pptID[0];}
		    	  
		    	  check[pptID.length]=getCurrentUser();
		    	  TimeslotDialog tsd=new TimeslotDialog(check);
		    	
		      }
		      });

		 System.out.println(jointtime);
	    
		AddBut.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  for(int i=0;i<userlist.size();i++){
				    	if(userField.getSelectedItem().toString().equals(userlist.elementAt(i))){
				    		return;
				    	}}
		    	  userlist.addElement(userField.getSelectedItem().toString());
		    	  pptID= new String[userlist.getSize()];
		    	  userlist.copyInto(pptID);
		    	  TimeBut.setEnabled(true);
		    	  }
		      });
		RemoveBut.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  int i = listuser.getSelectedIndex();
			        userlist.removeElementAt(i);
			        pptID= new String[userlist.getSize()];
			    	userlist.copyInto(pptID);
		      }
		      });
		panel2.add(TimeBut);
		panel2.add(att);
		panel2.add(pane1);
		panel2.add(ptL);
		panel2.add(pane);
		panel2.add(inviteL);
		panel2.add(userField);
		panel2.add(AddBut);
		panel2.add(RemoveBut);
		panel2.add(group);
		panel2.add(jbox);
		
		
		saveBut = new JButton("Save");
		saveBut.addActionListener(this);
		panel2.add(saveBut);

		rejectBut = new JButton("Reject");
		rejectBut.addActionListener(this);
		panel2.add(rejectBut);
		rejectBut.show(false);

		CancelBut = new JButton("Cancel");
		CancelBut.addActionListener(this);
		panel2.add(CancelBut);

		contentPane.add("South", panel2);
		NewAppt = new Appt();

		if (this.getTitle().equals("Join Appointment Content Change") || this.getTitle().equals("Join Appointment Invitation")){
			inviteBut.show(false);
			rejectBut.show(true);
			CancelBut.setText("Consider Later");
			saveBut.setText("Accept");
		}
		if (this.getTitle().equals("Someone has responded to your Joint Appointment invitation") ){
			inviteBut.show(false);
			rejectBut.show(false);
			CancelBut.show(false);
			saveBut.setText("confirmed");
		}
		if (this.getTitle().equals("Join Appointment Invitation") || this.getTitle().equals("Someone has responded to your Joint Appointment invitation") || this.getTitle().equals("Join Appointment Content Change")){
			allDisableEdit();
		}
		pack();

	}
	
	AppScheduler(String title, CalGrid cal, int selectedApptId) {
		this.selectedApptId = selectedApptId;
		commonConstructor(title, cal);
	}

	AppScheduler(String title, CalGrid cal) {
		commonConstructor(title, cal);
	}
	
	public void actionPerformed(ActionEvent e) {

		// distinguish which button is clicked and continue with require function
		if (e.getSource() == CancelBut) {

			setVisible(false);
			dispose();
		} else if (e.getSource() == saveBut) {
			try {
				saveButtonResponse();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == rejectBut){
			if (JOptionPane.showConfirmDialog(this, "Reject this joint appointment?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0){
				NewAppt.addReject(getCurrentUser());
				NewAppt.getAttendList().remove(getCurrentUser());
				NewAppt.getWaitingList().remove(getCurrentUser());
				this.setVisible(false);
				dispose();
			}
		}
		parent.getAppList().clear();
		parent.getAppList().setTodayAppt(parent.GetTodayAppt());
		parent.repaint();
	}

	private JPanel createPartOperaPane() {
		JPanel POperaPane = new JPanel();
		JPanel browsePane = new JPanel();
		JPanel controPane = new JPanel();

		POperaPane.setLayout(new BorderLayout());
		TitledBorder titledBorder1 = new TitledBorder(BorderFactory
				.createEtchedBorder(Color.white, new Color(178, 178, 178)),
				"Add Participant:");
		browsePane.setBorder(titledBorder1);

		POperaPane.add(controPane, BorderLayout.SOUTH);
		POperaPane.add(browsePane, BorderLayout.CENTER);
		POperaPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		return POperaPane;

	}

	private int getValidNoti(){
		int notimin=Utility.getNumber(NotiField.getText());
		if (notimin==-1)
			return -1;
		if (notimin<0){
			JOptionPane.showMessageDialog(this, "Please input proper time",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		if (notimin>(Utility.getNumber(sTimeH.getText())*60+Utility.getNumber(sTimeM.getText()))){
			JOptionPane.showMessageDialog(this, "The notification should be within the day",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		return notimin;
	}
	
	private int[] getValidDate() {

		int[] date = new int[3];
		date[0] = Utility.getNumber(yearF.getText());
		date[1] = Utility.getNumber(monthF.getText());
		if (date[0] < 1980 || date[0] > 2100) {
			JOptionPane.showMessageDialog(this, "Please input proper year",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (date[1] <= 0 || date[1] > 12) {
			JOptionPane.showMessageDialog(this, "Please input proper month",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		date[2] = Utility.getNumber(dayF.getText());
		int monthDay = CalGrid.monthDays[date[1] - 1];
		if (date[1] == 2) {
			GregorianCalendar c = new GregorianCalendar();
			if (c.isLeapYear(date[0]))
				monthDay = 29;
		}
		if (date[2] <= 0 || date[2] > monthDay) {
			JOptionPane.showMessageDialog(this,
			"Please input proper month day", "Input Error",
			JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return date;
	}

	private int getTime(JTextField h, JTextField min) {

		int hour = Utility.getNumber(h.getText());
		if (hour == -1)
			return -1;
		int minute = Utility.getNumber(min.getText());
		if (minute == -1)
			return -1;

		return (hour * 60 + minute);

	}

	private int[] getValidTimeInterval() {

		int[] result = new int[2];
		result[0] = getTime(sTimeH, sTimeM);
		result[1] = getTime(eTimeH, eTimeM);
		if ((result[0] % 15) != 0 || (result[1] % 15) != 0) {
			JOptionPane.showMessageDialog(this,
					"Minute Must be 0, 15, 30, or 45 !", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if (!sTimeM.getText().equals("0") && !sTimeM.getText().equals("15") && !sTimeM.getText().equals("30") && !sTimeM.getText().equals("45") 
			|| !eTimeM.getText().equals("0") && !eTimeM.getText().equals("15") && !eTimeM.getText().equals("30") && !eTimeM.getText().equals("45")){
			JOptionPane.showMessageDialog(this,
					"Minute Must be 0, 15, 30, or 45 !", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if (result[1] == -1 || result[0] == -1) {
			JOptionPane.showMessageDialog(this, "Please check time",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (result[1] <= result[0]) {
			JOptionPane.showMessageDialog(this,
					"End time should be bigger than \nstart time",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if ((result[0] < AppList.OFFSET * 60)
				|| (result[1] > (AppList.OFFSET * 60 + AppList.ROWNUM * 2 * 15))) {
			JOptionPane.showMessageDialog(this, "Out of Appointment Range !",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return result;
	}

	private boolean checkValidLocation(TimeSpan ts,String l){
		//////////////////
	    try {
	    	BufferedReader fr=new BufferedReader(new FileReader("appt_list.txt"));
	    	String line;
    	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    	    Date parsedTimeStamp;
    	    Timestamp start;
    	    Timestamp end;
    	    
	    	while((line=fr.readLine())!=null){
	    		String[] splited = line.split("\\|");
		    	    parsedTimeStamp = dateFormat.parse(splited[1]);
		    	    start = new Timestamp(parsedTimeStamp.getTime());
		    	    parsedTimeStamp = dateFormat.parse(splited[2]);
		    	    end = new Timestamp(parsedTimeStamp.getTime());
		    	    TimeSpan temptimespan=new TimeSpan(start,end);
		    	    
		    	    
					if(ts.Overlap(temptimespan)){
						if (l.equals(splited[7])){
					    	fr.close();

							JOptionPane.showMessageDialog(this, "Duplicated location !",
									"Error", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
	    		}
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
	    return true;
		//////////////////
	}
	
	private void saveButtonResponse() throws ParseException {
		// Fix Me!
			// Save the appointment to the hard disk
		int count=0;
		 String starttime="";
	     String endtime="";
		 try{
		        File inputFile = new File("TimeSpan.txt");
		        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		        String currentLine;
		        while((currentLine=reader.readLine())!=null){
		        	count++;
		        }
		        System.out.println(count);
		        reader.close();
		       
		        }catch (FileNotFoundException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 try{
		        File inputFile = new File("TimeSpan.txt");
		        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		        String Line;
		       
		        Line=reader.readLine();
		        String[] splited = Line.split("\\|");
		        starttime=splited[0];
		       int cc=2;
		       while(cc<count){   
		        	Line=reader.readLine();
		        	cc++;
		        
		        }
		       String Line1;
		       Line=reader.readLine();
		       System.out.println(Line);
		       String[] splited1 = Line.split("\\|");
		       endtime=splited1[1];
		       
		        reader.close();
		       
		        }catch (FileNotFoundException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		int[] Appt_Date = getValidDate();
		int[] Appt_Time = getValidTimeInterval();
		int noti = getValidNoti();
		TimeSpan tssave=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    Date parsedTimeStamp;
	    Timestamp start;
	    Timestamp end;
		
		if(Frequency.getSelectedItem().toString()=="one-time"){
			if(starttime.equals("")){
		Timestamp Sstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
		Timestamp Estamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);	
		tssave=new TimeSpan(Sstamp,Estamp);}
			else{
				 parsedTimeStamp = dateFormat.parse(starttime);
				 start = new Timestamp(parsedTimeStamp.getTime());
		    	 parsedTimeStamp = dateFormat.parse(endtime);
		    	 end = new Timestamp(parsedTimeStamp.getTime());
		    	 tssave=new TimeSpan(start,end);
			}
		
		String n=(String)locField.getSelectedItem();
		if(!checkValidLocation(tssave,n))
			return;
		
		NewAppt.setTimeSpan(tssave);
		NewAppt.setTitle(titleField.getText());
		NewAppt.setInfo(detailArea.getText());
		NewAppt.setLoc(n);
		
		NewAppt.setNoti(noti);
		if(privacy.getSelectedItem().toString()=="public"){
			NewAppt.setprivate(true);
		}
		if(jbox.isSelected()==true){
			NewAppt.setJoint(true);
			if(pptID!=null){
				for(int i=0;i<pptID.length;i++)
				NewAppt.addWaiting(pptID[i]);
				System.out.println(NewAppt.getWaitingList().size());
			}
		for(int i=0;i<attenduserlist.getSize();i++){
			NewAppt.addAttendant(attenduserlist.elementAt(i));
		}
		}
		
		if(this.selectedApptId!=-1)
			NewAppt.setID(this.selectedApptId);
		
		parent.controller.ManageAppt(NewAppt,3);}
		else if(Frequency.getSelectedItem().toString()=="daily"){
			int o=0;
			int n=Appt_Date[1];
			int monthDay = CalGrid.monthDays[n - 1];
			for(int d=Appt_Date[2];d<=monthDay;d++){
				o++;
				appta=new Appt[o];
				for(int i=o-1;i<appta.length;i++){
					appta[i]= new Appt();
				}
			System.out.println(Appt_Date[0]);  
			Appt_Date[2]=d;
			Timestamp dSstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
			Timestamp dEstamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
			TimeSpan dtssave=new TimeSpan(dSstamp,dEstamp);
			
			appta[o-1].setTimeSpan(dtssave);
			appta[o-1].setTitle(titleField.getText());
			appta[o-1].setInfo(detailArea.getText());
			appta[o-1].setNoti(noti);
			String r=(String)locField.getSelectedItem();
			appta[o-1].setLoc(r);
			if(this.selectedApptId!=-1)
				appta[o-1].setID(this.selectedApptId);
		
			parent.controller.ManageAppt(appta[o-1],3);
			}
			if(privacy.getSelectedItem().toString()=="public"){
				appta[o-1].setprivate(true);
			}
			if(jbox.isSelected()==true){
				appta[o-1].setJoint(true);
				if(pptID!=null){
					for(int i=0;i<pptID.length;i++)
						appta[o-1].addWaiting(pptID[i]);
				}
			}
			for(int m=Appt_Date[1]+1;m<=12;m++){
				int monthDay1 = CalGrid.monthDays[m - 1];
				for(int d=1;d<=monthDay1;d++){
					o++;
					appta=new Appt[o];
					for(int i=o-1;i<appta.length;i++){
						appta[o-1]= new Appt();
					}
				System.out.println(Appt_Date[0]);  
				Appt_Date[1]=m;
				Appt_Date[2]=d;
				Timestamp dSstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
				Timestamp dEstamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
				TimeSpan dtssave=new TimeSpan(dSstamp,dEstamp);
				
				appta[o-1].setTimeSpan(dtssave);
				appta[o-1].setTitle(titleField.getText());
				appta[o-1].setInfo(detailArea.getText());
				appta[o-1].setNoti(noti);
				String r=(String)locField.getSelectedItem();
				appta[o-1].setLoc(r);
				if(this.selectedApptId!=-1)
					appta[o-1].setID(this.selectedApptId);
			
				parent.controller.ManageAppt(appta[o-1],3);
				}
				if(privacy.getSelectedItem().toString()=="public"){
					appta[o-1].setprivate(true);
				}
				if(jbox.isSelected()==true){
					appta[o-1].setJoint(true);
					if(pptID!=null){
						for(int i=0;i<pptID.length;i++)
							appta[o-1].addWaiting(pptID[i]);
					}
				}
			}
			
			
			for(int y=Appt_Date[0]+1;y<=2100;y++){
				for(int m=1;m<=12;m++){
					int monthDay2 = CalGrid.monthDays[m - 1];
					for(int d=1;d<=monthDay2;d++){
						o++;
						appta=new Appt[o];
						for(int i=o-1;i<appta.length;i++){
							appta[o-1]= new Appt();
						}
					System.out.println(Appt_Date[0]);
					Appt_Date[0]=y;    
					Appt_Date[1]=m;
					Appt_Date[2]=d;
					Timestamp dSstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
					Timestamp dEstamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
					TimeSpan dtssave=new TimeSpan(dSstamp,dEstamp);
					
					appta[o-1].setTimeSpan(dtssave);
					appta[o-1].setTitle(titleField.getText());
					appta[o-1].setInfo(detailArea.getText());
					appta[o-1].setNoti(noti);
					String r=(String)locField.getSelectedItem();
					appta[o-1].setLoc(r);
					if(this.selectedApptId!=-1)
						appta[o-1].setID(this.selectedApptId);
				
					parent.controller.ManageAppt(appta[o-1],3);
				}
					if(privacy.getSelectedItem().toString()=="public"){
						appta[o-1].setprivate(true);
					}
					if(jbox.isSelected()==true){
						appta[o-1].setJoint(true);
						if(pptID!=null){
							for(int i=0;i<pptID.length;i++)
								appta[o-1].addWaiting(pptID[i]);
						}
					}
			}
		}
		}
		
		
			else if(Frequency.getSelectedItem().toString()=="weekly"){
				/*int f=Appt_Date[1];
				int monthDay2 = CalGrid.monthDays[f - 1];
				int u=Appt_Date[2];
				int l=Appt_Date[0];
				Timestamp Sstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
				Timestamp Estamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
				TimeSpan tssave=new TimeSpan(Sstamp,Estamp);
				do{
					monthDay2 = CalGrid.monthDays[f - 1];
					if (f == 2) {
						GregorianCalendar c = new GregorianCalendar();
						if (c.isLeapYear(l)){
							monthDay2 = 29;}}
				NewAppt= new Appt();
				NewAppt.setTimeSpan(tssave);
				NewAppt.setTitle(titleField.getText());
				NewAppt.setInfo(detailArea.getText());
				String n=(String)locField.getSelectedItem();
				NewAppt.setLoc(n);
				NewAppt.setNoti(noti);*/
				int f=Appt_Date[1];
				int monthDay2 = CalGrid.monthDays[f - 1];
				int u=Appt_Date[2];
				int l=Appt_Date[0];
				List<Appt> rApptsal = new ArrayList<Appt>();
				int h=0;
				do{
					h++;
					System.out.println(h);
					appta=new Appt[h];
					for(int j=h-1;j<appta.length;j++){
						appta[j]= new Appt();
					}
					monthDay2 = CalGrid.monthDays[f - 1];
					if (f == 2) {
						GregorianCalendar c = new GregorianCalendar();
						if (c.isLeapYear(l)){
							monthDay2 = 29;}}
				
						
						
						Appt_Date[0]=l;
						Appt_Date[1]=f; 
						Appt_Date[2]=u;    
						Timestamp dSstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
						Timestamp dEstamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
						TimeSpan dtssave=new TimeSpan(dSstamp,dEstamp);
						
						appta[h-1].setTimeSpan(dtssave);
						appta[h-1].setTitle(titleField.getText());
						appta[h-1].setInfo(detailArea.getText());
						appta[h-1].setNoti(noti);
						String r=(String)locField.getSelectedItem();
						appta[h-1].setLoc(r);
						if(this.selectedApptId!=-1)
							appta[h-1].setID(this.selectedApptId);
						if(privacy.getSelectedItem().toString()=="public"){
							appta[h-1].setprivate(true);
						}
						if(jbox.isSelected()==true){
							appta[h-1].setJoint(true);
							if(pptID!=null){
								for(int i=0;i<pptID.length;i++)
									appta[h-1].addWaiting(pptID[i]);
							}
						}
						parent.controller.ManageAppt(appta[h-1],3);
						
						u=u+7;
						//System.out.println(u);
						if(u>monthDay2){
							u=u-monthDay2;
							if((f+1)<=12){
							f=f+1;	}
							//System.out.print("xyz");
						else{
							f=1;
							l=l+1;
							//System.out.print("ABC");
						}
						}

						}while(l<=2100); 
						//System.out.print("ABC");
		/*				int f=Appt_Date[1];
						int monthDay2 = CalGrid.monthDays[f - 1];
						int u=Appt_Date[2];
						int l=Appt_Date[0];
					
						do{
							monthDay2 = CalGrid.monthDays[f - 1];
							if (f == 2) {
								GregorianCalendar c = new GregorianCalendar();
								if (c.isLeapYear(l)){
									monthDay2 = 29;}}
							appta=new Appt[f+u+l+7];
							for(int i=0;i<appta.length;i++){
								appta[i]= new Appt();
							}
							Appt_Date[0]=l;
							Appt_Date[1]=f; 
							Appt_Date[2]=u;    
							Timestamp dSstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
							Timestamp dEstamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
							TimeSpan dtssave=new TimeSpan(dSstamp,dEstamp);
							//System.out.println(l);
							appta[u+f+l].setTimeSpan(dtssave);
							appta[u+f+l].setTitle(titleField.getText());
							appta[u+f+l].setInfo(detailArea.getText());
							appta[u+f+1].setNoti(noti);
							String r=(String)locField.getSelectedItem();
							appta[u+f+l].setLoc(r);
							if(this.selectedApptId!=-1)
								appta[u+f+l].setID(this.selectedApptId);
						
							parent.controller.ManageAppt(appta[u+f+l],3);
							
							u=u+7;
							//System.out.println(u);
							if(u>monthDay2){
								u=u-monthDay2;
								if((f+1)<=12){
								f=f+1;	}
								//System.out.println(f);
								
							
								/*if (f == 2) {
									GregorianCalendar c = new GregorianCalendar();
									if (c.isLeapYear(l)){
										monthDay2 = 29;}
									}*/
	/*						else{
									f=1;
									l=l+1;
								}
							}

			}while(l<=2100); 
						*/

			}
				else if(Frequency.getSelectedItem().toString()=="monthly"){
					for(int y=Appt_Date[0];y<=2100;y++){
						for(int m=1;m<=12;m++){
							
								appta=new Appt[m+y+1];
								for(int i=0;i<appta.length;i++){
									appta[i]= new Appt();
								}
							System.out.println(Appt_Date[0]);
							Appt_Date[0]=y;    
							Appt_Date[1]=m;
							
							Timestamp dSstamp=CreateTimeStamp(Appt_Date,Appt_Time[0]);
							Timestamp dEstamp=CreateTimeStamp(Appt_Date,Appt_Time[1]);
							TimeSpan dtssave=new TimeSpan(dSstamp,dEstamp);
							
							appta[m+y].setTimeSpan(dtssave);
							appta[m+y].setTitle(titleField.getText());
							appta[m+y].setInfo(detailArea.getText());
							appta[m+y].setNoti(noti);
							String r=(String)locField.getSelectedItem();
							appta[m+y].setLoc(r);
							if(this.selectedApptId!=-1)
								appta[m+y].setID(this.selectedApptId);
							if(privacy.getSelectedItem().toString()=="public"){
								appta[m+y].setprivate(true);
							}
							if(jbox.isSelected()==true){
								appta[m+y].setJoint(true);
								if(pptID!=null){
									for(int i=0;i<pptID.length;i++)
										appta[m+y].addWaiting(pptID[i]);
								}
							}
							parent.controller.ManageAppt(appta[m+y],3);
						}
					}
					}
				
				else{}			
		
		File inputFile = new File("TimeSpan.txt");
		inputFile.delete();
		dispose();
		
			
		}
	
	public void deleteapp(Appt appt){
		System.out.println("appschedel");
		parent.controller.ManageAppt(appt, 1);
		parent.getAppList().clear();
		parent.getAppList().setTodayAppt(parent.GetTodayAppt());
		parent.repaint();
	}



	private Timestamp CreateTimeStamp(int[] date, int time) {
		Timestamp stamp = new Timestamp(0);
		stamp.setYear(date[0]);
		stamp.setMonth(date[1] - 1);
		stamp.setDate(date[2]);
		stamp.setHours(time / 60);
		stamp.setMinutes(time % 60);
		return stamp;
	}

	public void updateSetApp(Appt appt) {
		// Fix Me!
		  titleField.setText(appt.getTitle());
		  detailArea.setText(appt.getInfo());
		  sTimeH.setText(String.valueOf(appt.TimeSpan().StartTime().getHours()));
		  sTimeM.setText(String.valueOf(appt.TimeSpan().StartTime().getMinutes()));
		  eTimeH.setText(String.valueOf(appt.TimeSpan().EndTime().getHours()));
		  eTimeM.setText(String.valueOf(appt.TimeSpan().EndTime().getMinutes()));
		  yearF.setText(String.valueOf(appt.TimeSpan().StartTime().getYear()+1900));
		  monthF.setText(String.valueOf(appt.TimeSpan().StartTime().getMonth()+1));
		  dayF.setText(String.valueOf(appt.TimeSpan().StartTime().getDate()));
		  jbox.setSelected(appt.isJoint());
		  System.out.println("abc");
		  System.out.println(appt.getWaitingList().size());
		  if(!(appt.getWaitingList().isEmpty())){
				  for(int i=0;i<appt.getWaitingList().size();i++){
					  userlist.addElement(appt.getWaitingList().get(i).toString());
					  System.out.println("abc");
				  }
		  }
		  if(!(appt.getAttendList().isEmpty())){
			  for(int i=0;i<appt.getAttendList().size();i++){
				  attenduserlist.addElement(appt.getAttendList().get(i).toString());
				  System.out.println("abc");
			  }
	  }	  
	}

	public void componentHidden(ComponentEvent e) {

	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {

		Dimension dm = pDes.getSize();
		double width = dm.width * 0.93;
		double height = dm.getHeight() * 0.6;
		detailPanel.setSize((int) width, (int) height);

	}

	public void componentShown(ComponentEvent e) {

	}
	
	public String getCurrentUser()		// get the id of the current user
	{
		return this.parent.mCurrUser.ID();
	}
	
	
	
	private void allDisableEdit(){
		yearF.setEditable(false);
		monthF.setEditable(false);
		dayF.setEditable(false);
		sTimeH.setEditable(false);
		sTimeM.setEditable(false);
		eTimeH.setEditable(false);
		eTimeM.setEditable(false);
		titleField.setEditable(false);
		detailArea.setEditable(false);
	}
}
