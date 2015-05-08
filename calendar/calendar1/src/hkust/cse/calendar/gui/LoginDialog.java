package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginDialog extends JFrame implements ActionListener
{
	private JTextField userName;
	private JPasswordField password;
	private JButton button;
	private JButton closeButton;
	private JButton signupButton;
	private void login(){
	    try {
	    	BufferedReader fr=new BufferedReader(new FileReader("user_list.txt"));
	    	String line;
    	    int i=0;

	    	while((line=fr.readLine())!=null){
	    		String[] splited = line.split("\\|");

	    			if(userName.getText().equals(splited[0])&&password.getText().equals(splited[1])){

	    				User user = new User( userName.getText(), password.getText());
	    				//for no name user to read data from file
	    				
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
	    				
	    				
	    				//
	    				if(!userName.getText().equals("admin")){
	    					CalGrid grid = new CalGrid(controller);
	    				}
	    				else{
	    					Admin a = new Admin(controller);
	    				}
	    				
    				
	    				setVisible( false );
	    			}
	    		}
	    	fr.close();
	    	}
	    catch (IOException e1) {
	        // TODO Auto-generated catch block
	            System.out.println("cannot open");
	            e1.printStackTrace();
	    }
	}
	
	public LoginDialog()		// Create a dialog to log in
	{
		
		setTitle("Log in");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Please input your user name and password to log in."));
		top.add(messPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("User Name:"));
		userName = new JTextField(15);
		namePanel.add(userName);
		top.add(namePanel);
		
		JPanel pwPanel = new JPanel();
		pwPanel.add(new JLabel("Password:  "));
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);
		
		JPanel signupPanel = new JPanel();
		signupPanel.add(new JLabel("If you don't have an account, please sign up:"));
		signupButton = new JButton("Sign up now");
		signupButton.addActionListener(this);
		signupPanel.add(signupButton);
		top.add(signupPanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		button = new JButton("Login");
		button.addActionListener(this);
		butPanel.add(button);
		
		closeButton = new JButton("Close program");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
	}
	

	

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button)
		{
			// When the button is clicked, check the user name and password, and try to log the user in
			login();
		}
		else if(e.getSource() == signupButton)
		{
			// Create a new account
			SignupDialog a=new SignupDialog();
		}
		else if(e.getSource() == closeButton)
		{
			int n = JOptionPane.showConfirmDialog(null, "Exit Program ?",
					"Confirm", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
				System.exit(0);			
		}
	}
	
	// This method checks whether a string is a valid user name or password, as they can contains only letters and numbers
	public static boolean ValidString(String s)
	{
		char[] sChar = s.toCharArray();
		for(int i = 0; i < sChar.length; i++)
		{
			int sInt = (int)sChar[i];
			if(sInt < 48 || sInt > 122)
				return false;
			if(sInt > 57 && sInt < 65)
				return false;
			if(sInt > 90 && sInt < 97)
				return false;
		}
		return true;
	}
}
