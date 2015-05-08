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

public class LocationsDialog extends JFrame implements ActionListener {
	private LinkedList<Location> locationLL;

	private JTextField locationF;
	private JTextField capacityF;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JButton saveBut;
	private JButton deleteBut;
	private JButton inspectBut;
	private JButton changeBut;
	private CalGrid parent;

	LocationsDialog(String title, CalGrid cal) {
		parent = cal;
		this.setAlwaysOnTop(true);
		setTitle(title);
		Container contentPane;
		contentPane = getContentPane();

		loadFromTxt("location_list.txt");
		
		listModel = new DefaultListModel<String>();
		for(int i = 0; i < locationLL.size(); i++){
			listModel.addElement(locationLL.get(i).getName());
		}
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel listPanel = new JPanel();
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		listPanel.add(list);
		top.add(listPanel);
		
		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Please input a new location."));
		top.add(messPanel);

		JPanel locationPanel = new JPanel();
		
		locationPanel.add(new JLabel("Location:"));
		locationF = new JTextField(15);
		locationPanel.add(locationF);
		top.add(locationPanel);
		
		locationPanel.add(new JLabel("Capacity:"));
		capacityF = new JTextField(15);
		locationPanel.add(capacityF);
		top.add(locationPanel);

		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		saveBut = new JButton("save");
		saveBut.addActionListener(this);
		butPanel.add(saveBut);
		
		inspectBut = new JButton("inspect");
		inspectBut.addActionListener(this);
		butPanel.add(inspectBut);
		
		changeBut = new JButton("change");
		changeBut.addActionListener(this);
		butPanel.add(changeBut);

		deleteBut = new JButton("delete");
		deleteBut.addActionListener(this);
		butPanel.add(deleteBut);

		contentPane.add("South", butPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == saveBut) {
			String location;
			int c;
			Location newLocation;
			if(locationF.getText().isEmpty() || capacityF.getText().isEmpty()){
				JOptionPane.showMessageDialog(this,
						"The input is empty.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			location = locationF.getText();
			c = Integer.parseInt(capacityF.getText());
			newLocation = new Location(location, c, -1);
			
			
			if(listModel.contains(location)){
				JOptionPane.showMessageDialog(this,
				"The location is already exist.",
				"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				locationLL.add(newLocation);
				listModel.addElement(location);
				SaveToTxt(locationLL);
				pack();
			}
		}
		if (e.getSource() == deleteBut) {
			int deleteIndex = list.getSelectedIndex();
			if(deleteIndex == -1){
				JOptionPane.showMessageDialog(this,
						"Please select a location.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				Appt[] location = parent.controller.mApptStorage.RetrieveAppts(locationLL.get(deleteIndex));
				if(location != null){
					locationLL.get(deleteIndex).status = location.length;
					SaveToTxt(locationLL);
				}
				else{
				locationLL.remove(deleteIndex);
				listModel.remove(deleteIndex);
				SaveToTxt(locationLL);
			}
		}

		}
		if (e.getSource() == inspectBut) {
			int inspectIndex = list.getSelectedIndex();
			if(inspectIndex == -1){
				JOptionPane.showMessageDialog(this,
						"Please select a location.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				Location inspectLocation = locationLL.get(inspectIndex);
				locationF.setText(inspectLocation.getName());
				capacityF.setText(Integer.toString(inspectLocation.getCapacity()));
			}

		}
		
		if (e.getSource() == changeBut) {
			int changeIndex = list.getSelectedIndex();
			Location changeLocation;
			if (changeIndex == -1) {
				JOptionPane.showMessageDialog(this,
						"Please select a location.", "Input Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// locationF.setText(inspectLocation.getName());
				// capacityF.setText(Integer.toString(inspectLocation.getCapacity()));
				// SaveToTxt(locationLL);
				changeLocation = locationLL.get(changeIndex);
				if ((locationF.getText().isEmpty())
						&& (capacityF.getText().isEmpty()))
					return;
				else if (locationF.getText().isEmpty())
					locationLL.get(changeIndex).setCapacity(
							Integer.parseInt(capacityF.getText()));
				else if (capacityF.getText().isEmpty())
					locationLL.get(changeIndex).setName(locationF.getText());
				else {
					locationLL.get(changeIndex).setCapacity(
							Integer.parseInt(capacityF.getText()));
					if (listModel.contains(locationF.getText())){
						JOptionPane.showMessageDialog(this,
								"The location is already exist.",
								"Input Error", JOptionPane.ERROR_MESSAGE);
					return;
					
					}
					else
						locationLL.get(changeIndex)
								.setName(locationF.getText());
				}
				listModel.setElementAt(locationF.getText(), changeIndex);
				SaveToTxt(locationLL);
			}

		}
	}

	private void SaveToTxt(LinkedList<Location> locationLL) {
		// TODO Auto-generated method stub
		try {
			File inputFile = new File("location_list.txt");
			File tempFile = new File("tempFile.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			for(int i = 0; i < locationLL.size(); i++){
				writer.write(locationLL.get(i).getName() + "|");
				writer.write(locationLL.get(i).getCapacity() + "|");
				writer.write(locationLL.get(i).getStatus() + "|");
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

	public void loadFromTxt(String fileName) {
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
	}
}
