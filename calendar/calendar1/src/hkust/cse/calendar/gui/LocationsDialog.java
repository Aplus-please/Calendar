package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Location;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LocationsDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ApptStorageControllerImpl _controller;
	
	private DefaultListModel<String> listModel;
	private DefaultListModel<Location> Locat;
	private JList<String> list;
	private JTextField locNameText;
	private JButton AddBut;
	private JButton RemoveBut;
	private Location[] loc;
	
	public LocationsDialog (ApptStorageControllerImpl controller)
	{
		_controller=controller;
		
		setTitle("Location");
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setSize(300,200);
		locNameText = new JTextField(20);
		
		
		
		listModel = new DefaultListModel<String>();
		Locat = new  DefaultListModel<Location>();
		
		if(_controller.getLocationList()!= null){
			loc=_controller.getLocationList();
			
			for(int i=0;i<loc.length;i++){
				Locat.addElement(loc[i]);
				listModel.addElement(loc[i].getName());
			}
		}
		
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.toString();
		JScrollPane pane = new JScrollPane(list);
		AddBut = new JButton("Add location");
		RemoveBut = new JButton("Remove location");
		AddBut.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  Location x =new Location(locNameText.getText());
		    	for(int i=0;i<Locat.size();i++){
		    	if(x.getName().equals(listModel.elementAt(i))){
		    		return;
		    	}
		    }
		    	Locat.addElement(x);
		        listModel.addElement(x.getName());
		        loc= new Location[Locat.getSize()];
		        Locat.copyInto(loc);
		        _controller.setLocationList(loc);
		        
			    if(x.equals(""))return;
		        try {
		        	FileWriter fstream=new FileWriter("location_list.txt",true);
		            
		        	BufferedWriter out=new BufferedWriter(fstream);
		            out.write(x.getName()+"\n");
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
		    	int i = list.getSelectedIndex();
				String delete;
		        delete=listModel.elementAt(i);
		        listModel.removeElementAt(i);
		        Locat.removeElementAt(i);
		        loc= new Location[Locat.getSize()];
		        Locat.copyInto(loc);
		        _controller.setLocationList(loc);
		        

		        try{
		        File inputFile = new File("location_list.txt");
		        File tempFile = new File("myTempFile.txt");
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

		
		add(locNameText, BorderLayout.CENTER);
		add(pane, BorderLayout.NORTH);
		add(AddBut, BorderLayout.WEST);
	    add(RemoveBut, BorderLayout.EAST);

		
		
		pack();
		setVisible(true);
		
		
		
	}
}
