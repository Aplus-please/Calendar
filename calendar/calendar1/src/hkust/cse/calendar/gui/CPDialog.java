package hkust.cse.calendar.gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class CPDialog extends JFrame implements ActionListener
{
	private JTextField password1;
	private JPasswordField password;
	private JButton button;
	private JButton closeButton;
	private String name;
		public CPDialog(String username)		// Create a dialog to log in	{
		{
		setTitle("Change Password");
		
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
		namePanel.add(new JLabel("Password:"));
		password1 = new JPasswordField(15);
		namePanel.add(password1);
		top.add(namePanel);
		
		JPanel pwPanel = new JPanel();
		pwPanel.add(new JLabel("Password (Retype):  "));
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		button = new JButton("OK");
		button.addActionListener(this);
		butPanel.add(button);
		
		closeButton = new JButton("Cancel");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		name=username;
	}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == button)
			{
				// Do Not Allow same ID
				if(!password1.getText().equals(password.getText()))
					{
	    			JOptionPane.showMessageDialog(this, "Wrong password!",
	    					"Input Error", JOptionPane.ERROR_MESSAGE);
					return;
					}
					
					String delete=name;
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
			        }
			        catch (IOException e1) {
			        // TODO Auto-generated catch block
			            System.out.println("cannot open");
			            e1.printStackTrace();
			        }
			    
			    if(password1.getText().equals("")||password.getText().equals(""))return;
		        try {
		        	FileWriter fstream=new FileWriter("user_list.txt",true);
		            
		        	BufferedWriter out=new BufferedWriter(fstream);
		     
		            out.write(name+"|");
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
