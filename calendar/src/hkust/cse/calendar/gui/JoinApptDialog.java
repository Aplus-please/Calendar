package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class JoinApptDialog extends JFrame implements ActionListener {
	private ApptStorageControllerImpl con;
	
	private String inviteUser;
	private Appt inviteAppt;

	private String inviteMessage;
	private JButton attendBut;
	private JButton rejectBut;

	JoinApptDialog(String u, Appt a, ApptStorageControllerImpl c) {
		this.setAlwaysOnTop(true);
		con = c;
		inviteUser = u;
		inviteAppt = a;
		
		setTitle("Invitation");
		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		

		JPanel messagePanel = new JPanel();
		inviteMessage = inviteUser + " has invited you to join the event from " + inviteAppt.TimeSpan().StartTime() + " to " + inviteAppt.TimeSpan().EndTime() + ".";
		messagePanel.add(new JLabel(inviteMessage));
		top.add(messagePanel);
		
		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		attendBut = new JButton("Attend");
		attendBut.addActionListener(this);
		butPanel.add(attendBut);

		rejectBut = new JButton("Reject");
		rejectBut.addActionListener(this);
		butPanel.add(rejectBut);

		contentPane.add("South", butPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == attendBut) {
			Appt appt = new Appt();
			appt.setID(con.getusableid());
			appt.setTimeSpan(inviteAppt.TimeSpan());
			appt.setstarttime(inviteAppt.getstarttime());
			appt.setendtime(inviteAppt.getendtime());
			appt.setTitle(inviteAppt.getTitle());
			appt.setInfo(inviteAppt.getInfo());
			appt.setJoint(true);
			appt.setJoinID(inviteAppt.getID());
			appt.setusername(con.getusername());
			appt.setLocation(inviteAppt.getLocation());
			con.ManageAppt(appt, con.NEW);
			inviteAppt.addAttendant(con.getDefaultUser().ID());
			inviteAppt.removeWaitingList(con.getDefaultUser().ID());
			con.ManageAppt(inviteAppt, con.MODIFY);
		}
		if (e.getSource() == rejectBut) {
			inviteAppt.addReject(con.getDefaultUser().ID());
			inviteAppt.removeWaitingList(con.getDefaultUser().ID());
			con.ManageAppt(inviteAppt, con.MODIFY);
		}
		dispose();
	}
}
