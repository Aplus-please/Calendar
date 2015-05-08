package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.User;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class UserDialog extends JFrame{
	private JList<String> list;
	private JPopupMenu pop;
	private int selectedindex;
	private String selecteduser;
	public UserDialog(){
		try{
		BufferedReader fr=new BufferedReader(new FileReader("user_list.txt"));
		String line;
		final DefaultListModel<String> listModel = new DefaultListModel<String>();
    	while((line=fr.readLine())!=null){
    		String[] splited = line.split("\\|");
    		listModel.addElement(splited[0]);
    	}
    	fr.close();
		list = new JList<String>(listModel);

		add(new JScrollPane(list));

        this.setTitle("JList Example");       
        this.setSize(200,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        Font f1 = new Font("Helvetica", Font.ITALIC, 11);
		pop = new JPopupMenu();
		pop.setFont(f1);
        
		JMenuItem mi;
		mi = (JMenuItem) pop.add(new JMenuItem("Inspect"));

		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User( selecteduser, "");
				ApptStorageNullImpl nameduser=new ApptStorageNullImpl(user);
				nameduser.ReadAppt();	    		
				ApptStorageControllerImpl controller=new ApptStorageControllerImpl(nameduser);
				
					try{
						DefaultListModel<String> listModel= new DefaultListModel<String>();;
						DefaultListModel<Location> Locat= new  DefaultListModel<Location>();;
						Location[] loc;
			    	BufferedReader fr1=new BufferedReader(new FileReader("location_list.txt"));
			    	String line1;
			    	while((line1=fr1.readLine())!=null){
				    	  Location x =new Location(line1);
				    	for(int i1=0;i1<Locat.size();i1++){
				    	if(x.getName().equals(listModel.elementAt(i1))){
				    		fr1.close();
				    		return;
				    	}
				    }
				    	Locat.addElement(x);
				        listModel.addElement(x.getName());
				        loc= new Location[Locat.getSize()];
				        Locat.copyInto(loc);
				        controller.setLocationList(loc);
			    	}
			    	fr1.close();
			    }catch (FileNotFoundException e1) {
			        // TODO Auto-generated catch block
			        e1.printStackTrace();
			    } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					CalGrid grid = new CalGrid(controller);
			}
		});

		mi = (JMenuItem) pop.add(new JMenuItem("Remove"));

		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.remove(selectedindex);
				
				String delete=selecteduser;
		        try{
		        File inputFile = new File("user_list.txt");
		        File tempFile = new File("myTempFile.txt");
		        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		        String currentLine;
		        while((currentLine = reader.readLine()) != null) {
		            // trim newline when comparing with lineToRemove
		    		String[] splited = currentLine.split("\\|");
		            if(splited[0].equals(delete)) continue;
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
		
		list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				pressResponse(e);
			}});
		

        
		}catch (FileNotFoundException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
		private void pressResponse(MouseEvent e) {
			selectedindex=list.getSelectedIndex();
			selecteduser=list.getSelectedValue();
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
				pop.show(e.getComponent(), e.getX(), e.getY());
		}
}
