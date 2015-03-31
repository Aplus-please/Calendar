package hkust.cse.calendar.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
	
	public LocationsDialog(ApptStorageControllerImpl controller){
		_controller = controller;
		this.setLayout (new BorderLayout());
		this.setLocationByPlatform(true);
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
	}
	
	
	

}
