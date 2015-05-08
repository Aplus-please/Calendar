package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.gui.CalCellRenderer;
import hkust.cse.calendar.unit.Appt;
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.Position;

import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.userstorage.UserStorage;

public class AppScheduler extends JDialog implements ActionListener,
		ComponentListener {

	private	JComboBox invitelistF;
	private JLabel invitelistL;
	
	private	JComboBox locationF;
	private JLabel locationL;
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

	private DefaultListModel model;
	private JTextField titleField;

	private JButton saveBut;
	private JButton CancelBut;
	private JButton inviteBut;
	private JButton rejectBut;
	
	private LinkedList<User> userLL;
	private LinkedList<Location> locationLL;
	private JList<String> userList;
	private JList<String> attentList;
	private JList<String> rejectList;
	private JList<String> waitingList;
	
	private Appt NewAppt;
	private CalGrid parent;
	private UserStorage us;
	
	private boolean isNew = true;
	private boolean isChanged = true;
	private boolean isJoint = false;

	private JTextArea detailArea;

	private boolean priornot;
	boolean validInput = true;
	
	private JCheckBox publicbox;
	private JLabel publiclabel;
	
	private JSplitPane pDes;
	JPanel detailPanel;
	private JComboBox Frequency;
	private JTextField NotiField;
	private JCheckBox jbox;
//	private JTextField attendField;
//	private JTextField rejectField;
//	private JTextField waitingField;
	private int selectedApptId = -1;
	

	private void commonConstructor(String title, CalGrid cal) {
		us = new UserStorage("user_list.txt");
		
		parent = cal;
//		this.setAlwaysOnTop(true);
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

		JPanel pTime = new JPanel();	// Container of START TIME and END TIME
		pTime.setLayout(new BorderLayout());
		pTime.add("West", psTime);
		pTime.add("East", peTime);

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.setBorder(new BevelBorder(BevelBorder.RAISED));
		top.add(pDate, BorderLayout.NORTH);
		top.add(pTime, BorderLayout.CENTER);

		contentPane.add("North", top);

		JPanel titleAndTextPanel = new JPanel(); // Container for TITLE
		JLabel titleL = new JLabel("TITLE");
		titleField = new JTextField(15);
		titleAndTextPanel.add(titleL);
		titleAndTextPanel.add(titleField);

		String[] Freq ={"Once","Daily","Weekly","Monthly"};
		JLabel FreqL = new JLabel("Frequency");
		Frequency = new JComboBox(Freq);
		titleAndTextPanel.add(FreqL);
		titleAndTextPanel.add(Frequency);
		
		locationLL = loadFromTxt("location_list.txt");
		String [] locationArray = new String[locationLL.size()];
		for(int i = 0; i < locationArray.length; i++){
			locationArray[i] = locationLL.get(i).getName();
		}
		
		locationL = new JLabel("Location: ");
		locationF = new JComboBox(locationArray);
		locationF.setPreferredSize(new Dimension(130,30));
		titleAndTextPanel.add(locationL);
		titleAndTextPanel.add(locationF);
		
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
        
        //testing show that is not the problem of class
        //cal.controller.test();
		
		detailPanel = new JPanel();	// Container for Appointment Description
		detailPanel.setLayout(new BorderLayout());
		Border detailBorder = new TitledBorder(null, "Appointment Description");
		detailPanel.setBorder(detailBorder);
		detailArea = new JTextArea(20, 30); //(Row, Col)

		detailArea.setEditable(true); // Can type or not
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
	//new add
		model=new DefaultListModel();
		userList=new JList<String> (model);
//		userList.setBorder(dateBorder);
		userList.setPreferredSize(new Dimension(200,120));
		panel2.add(userList);
//new add		
//String [] userArray=getuserarray();
//		userLL = loaduserFromTxt("user_list.txt");
//		String [] userArray = new String[userLL.size()];
//		for(int i = 0,a=0; i < userArray.length; i++){
//			if()
//			{continue;}
//			userArray[a] = userLL.get(i).ID();
//		    a++;
//		}

		invitelistL = new JLabel("User list: ");
		invitelistF = new JComboBox(us.getAllUsername());
		invitelistF.removeItem(parent.controller.getusername());
		invitelistF.setPreferredSize(new Dimension(130,30));
		panel2.add(invitelistL);
		panel2.add(invitelistF);
		
		inviteBut = new JButton("Invite");
		inviteBut.addActionListener(this);
		panel2.add(inviteBut);
		
		JLabel publiclabel = new JLabel("Public");
		publicbox = new JCheckBox("", false);
        ItemListener publicListener = new ItemListener()
        {
            public void itemStateChanged(ItemEvent ie)
            {
            	priornot = (ie.getStateChange() == ItemEvent.SELECTED);
            }
        };
        publicbox.addItemListener(publicListener);
        panel2.add (publiclabel);
		panel2.add (publicbox);
        
		saveBut = new JButton("Save"); // Save Button
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

		// distinguish which button is clicked and continue with require
		// function
		if (e.getSource() == inviteBut) {
			inviteButtonResponse();
			return;
		} else if (e.getSource() == CancelBut) {
			setVisible(false);
			dispose();
		} else if (e.getSource() == saveBut) {
			saveButtonResponse();
			if (validInput){
				setVisible(false);
			}
		} else if (e.getSource() == rejectBut) {
			if (JOptionPane.showConfirmDialog(this,
					"Reject this joint appointment?", "Confirmation",
					JOptionPane.YES_NO_OPTION) == 0) {
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
		if (validInput){
			dispose();
		}
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

	private int[] getValidDate() {

		int[] date = new int[3]; // An array store YMD 
		date[0] = Utility.getNumber(yearF.getText());
		date[1] = Utility.getNumber(monthF.getText());
		if (date[0] < 1980 || date[0] > 2100) {
			JOptionPane.showMessageDialog(this, "Please input proper year.",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (date[1] <= 0 || date[1] > 12) {
			JOptionPane.showMessageDialog(this, "Please input proper month.",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		date[2] = Utility.getNumber(dayF.getText());	// Get the input date
		int monthDay = CalGrid.monthDays[date[1] - 1];
		if (date[1] == 2) {	// If it is Feb
			GregorianCalendar c = new GregorianCalendar();
			if (c.isLeapYear(date[0]))
				monthDay = 29;
		}
		if (date[2] <= 0 || date[2] > monthDay) {
			JOptionPane.showMessageDialog(this,
			"Please input proper month day.", "Input Error",
			JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return date;
	}

	private int getTime(JTextField h, JTextField min) { // Help function for getValidTimeInterval()

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
	
	private boolean isPastEvent(Appt appt){
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp current = new java.sql.Timestamp(now.getTime());
		return appt.TimeSpan().StartTime().before(current);
	}

	private void saveButtonResponse() {
		// Fix Me!
		// Save the appointment to the hard disk
		// 1.Create timestan(for Hashmap key)
		
		String yearStr = yearF.getText();
		int yearInt = Integer.parseInt(yearStr);
		String monthStr = monthF.getText();
		int monthInt = Integer.parseInt(monthStr);
		String dayStr = dayF.getText();
		int dayInt = Integer.parseInt(dayStr);

		int[] date = new int[] { yearInt, monthInt, dayInt };
		Timestamp timestampS = CreateTimeStamp(date, getValidTimeInterval()[0]);
		Timestamp timestampE = CreateTimeStamp(date, getValidTimeInterval()[1]);

		Appt event = new Appt();
		event.setusername(parent.controller.getusername());
		event.setID(parent.controller.getusableid());
		event.setstarttime(timestampS);
		event.setendtime(timestampE);
		TimeSpan etimespan = new TimeSpan(timestampS, timestampE);
		event.setTimeSpan(etimespan);
		if (isPastEvent(event))
			System.out.println("Past Event could not be saved");

		event.setprivate(priornot);
		event.setTitle(titleField.getText());
		LinkedList<String> waiting = new LinkedList<String>();
		for (int i = 0; i < model.getSize(); i++) {
			waiting.add((String) model.getElementAt(i));
		}
		Location tempLoc = locationLL.get(locationF.getSelectedIndex());
		if (parent.controller.mApptStorage.locationCrash(tempLoc, etimespan)) {
			JOptionPane.showMessageDialog(this, "Location has been used.",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			validInput = false;
		} else {
			event.setLocation(tempLoc);
		}
		event.setWaitingList(waiting);
		if (validInput){
			if (selectedApptId == -1)
				parent.controller.ManageAppt(event, parent.controller.NEW);
			else
				parent.controller.ManageAppt(event, parent.controller.MODIFY);
		}

	}
	
	private boolean validInput(Appt appt){
		if(appt.getLocation() == null)
			return false;
		return true;
	}
	
//	new add
	private void inviteButtonResponse() {
		// model=new DefaultListModel();
		String yearStr = yearF.getText();
		int yearInt = Integer.parseInt(yearStr);
		String monthStr = monthF.getText();
		int monthInt = Integer.parseInt(monthStr);
		String dayStr = dayF.getText();
		int dayInt = Integer.parseInt(dayStr);
		int[] date = new int[] { yearInt, monthInt, dayInt };
		Timestamp timestampS = CreateTimeStamp(date, getValidTimeInterval()[0]);
		Timestamp timestampE = CreateTimeStamp(date, getValidTimeInterval()[1]);
		TimeSpan etimespan = new TimeSpan(timestampS, timestampE);
		User selectedUser = us.getUser((String) invitelistF.getSelectedItem());
		Appt[] tmpUserArray = parent.controller.RetrieveAppts(selectedUser, etimespan);
		if(tmpUserArray != null){
			JOptionPane.showMessageDialog(this, "Warning:Your friend have another appointment on that day!",
					"Input Error", JOptionPane.ERROR_MESSAGE);
			return;
//			System.out.println("Warning:Your friend have another appointment on that day!");
		}
		model.addElement(invitelistF.getSelectedItem());
		waitingList = new JList<String>(model);
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
		yearF.setText(String
				.valueOf(appt.TimeSpan().StartTime().getYear() + 1900));
		monthF.setText(String
				.valueOf(appt.TimeSpan().StartTime().getMonth() + 1));
		dayF.setText(String.valueOf(appt.TimeSpan().StartTime().getDate()));
		if(!appt.getWaitingList().isEmpty()){
			for(int i = 0; i < appt.getWaitingList().size(); i++){
				model.addElement(appt.getWaitingList().get(i));
			}
			waitingList = new JList<String>(model);
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
	
	public LinkedList<Location> loadFromTxt(String fileName) {
		locationLL = new LinkedList<Location>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				String[] splited = tmp.split("\\|");
				locationLL.add(new Location(splited[0], Integer
						.parseInt(splited[1]), Integer.parseInt(splited[2])));
			}
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return locationLL;
	}
}
