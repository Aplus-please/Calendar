package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
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
import java.io.File;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class LocationsDialog extends JFrame implements ActionListener{
	private JTextField locationF;
	private JButton saveBut;
	private CalGrid parent;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == saveBut) {
			saveButtonResponse();
			setVisible(false);

		}
	}
	private void saveButtonResponse() {
		// Fix Me!
		// Save the appointment to the hard disk
		// 1.Create timestan(for Hashmap key)
    String location=locationF.getText();
	parent.controller.setlocation(location);
	}
	LocationsDialog(String title, CalGrid cal) {
		commonConstructor(title, cal);
	}
	private void commonConstructor(String title, CalGrid cal) {
	parent=cal;
	this.setAlwaysOnTop(true);
	setTitle(title);
	Container contentPane;
	contentPane = getContentPane();
	
	JPanel top = new JPanel();
	top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
	
	JPanel messPanel = new JPanel();
	messPanel.add(new JLabel("Please input a new location."));
	top.add(messPanel);
	
	JPanel locationPanel = new JPanel();
	locationPanel.add(new JLabel("Location:"));
	locationF = new JTextField(15);
	locationPanel.add(locationF);
	top.add(locationPanel);
	

	
	contentPane.add("North", top);
	
	JPanel butPanel = new JPanel();
	butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

	saveBut = new JButton("save");
	saveBut.addActionListener(this);
	butPanel.add(saveBut);
	
	contentPane.add("South", butPanel);
	
	pack();
	setLocationRelativeTo(null);
	setVisible(true);	
	}
//	private JLabel locationL;
//	private JTextField locationF;
//	private JButton saveBut;
//	private ApptStorageControllerImpl controller;
//	public LocationsDialog(CalGrid a){
////	CalGrid parent=a;
////	setTitle("Location Dialog");
////	JPanel contentPane=new JPanel();
////	locationL=new JLabel("Add Location:");
////	locationF=new JTextField(20);
////	locationL.setText("New location");
////	saveBut=new JButton("Add");
////	saveBut.addActionListener(l);
////    contentPane.add(locationL);
////    contentPane.add(locationF);
//    contentPane.add(saveBut);
//    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//    pack();
//    setLocationRelativeTo(null);
//    setVisible(true);
//    
//    saveBut.addActionListener(new ActionListener(){
//    	public void actionPerformed(ActionEvent e)
//    	{String location=locationL.getText();
//    	parent.controller.setlocation(location);
//    	setVisible(false);
//    		 }
//    });

}
