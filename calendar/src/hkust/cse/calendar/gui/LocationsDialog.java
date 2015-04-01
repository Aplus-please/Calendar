package hkust.cse.calendar.gui;

import java.awt.*;

import javax.swing.*;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;

import javax.swing.DefaultListModel;

import hkust.cse.calendar.unit.Location;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class LocationsDialog extends JFrame{
	private static final long serialVersionUID = 1L;
	private ApptStorageControllerImpl _controller;
	private DefaultListModel<Location> listModel;
	private JList<Location> list;
	private JTextField locNameText;
	private JLabel lblAddLocation;
	private JTextField txtAddlocation;
	private JButton btnAddLocation;
	public LocationsDialog(){
		setTitle("Location Dialog");
		JPanel contentPane = new JPanel();
		lblAddLocation = new JLabel("Add Location:");
		txtAddlocation = new JTextField(20);
		btnAddLocation = new JButton("Add");
		contentPane.add(lblAddLocation);
		contentPane.add(txtAddlocation);
		contentPane.add(btnAddLocation);
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}
	public LocationsDialog(ApptStorageControllerImpl controller){
		_controller = controller;
		this.setLayout (new BorderLayout());
		
		this.setSize(300,200);
		
		listModel = new DefaultListModel<Location>();
		list = new JList<Location>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		pack();
		
		this.setVisible(true);
	}
	
	
	

}
