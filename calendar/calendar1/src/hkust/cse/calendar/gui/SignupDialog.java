package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupDialog extends JFrame implements ActionListener
{
	private JTextField userName;
	private JPasswordField password;
	private JButton button;
	private JButton closeButton;
		public SignupDialog()		// Create a dialog to log in	{
		{
		setTitle("Sign Up");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		
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
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		button = new JButton("Sign Up");
		button.addActionListener(this);
		butPanel.add(button);
		
		closeButton = new JButton("Cancel");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
	}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == button)
			{
				// Do Not Allow same ID
			    try {
			    	BufferedReader fr=new BufferedReader(new FileReader("user_list.txt"));
			    	String line;
		    	    int i=0;

			    	while((line=fr.readLine())!=null){
			    		String[] splited = line.split("\\|");

			    			if(userName.getText().equals(splited[0])){{
			    				fr.close();
			    				return;
			    			}
			    			}
			    		}
			    	fr.close();
			    	}	catch (IOException e1) {
			        // TODO Auto-generated catch block
			            System.out.println("cannot open");
			            e1.printStackTrace();
			    }
				// Do not Allow null ID
			    if(userName.getText().equals("")||password.getText().equals(""))return;
		        try {
		        	FileWriter fstream=new FileWriter("user_list.txt",true);
		            
		        	BufferedWriter out=new BufferedWriter(fstream);
		            out.write(userName.getText()+"|");
		            out.write(password.getText()+"|"+"\n");
		            out.close();
		            System.out.println("saved");
		  
		    } catch (IOException e1) {
		        // TODO Auto-generated catch block
		            System.out.println("cannot open");
		            e1.printStackTrace();
		    }
				setVisible( false );
			}
			else if(e.getSource() == closeButton)
			{
				dispose();
			}
		}
}
