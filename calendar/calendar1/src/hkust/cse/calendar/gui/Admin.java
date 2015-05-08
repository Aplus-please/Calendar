package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;


public class Admin extends JFrame implements ActionListener
{//Custom button text
	private JButton button1;
	private JButton button2;
	private ApptStorageControllerImpl cl;
	public Admin(ApptStorageControllerImpl controller)		// Create a dialog to log in
	{
		
		setTitle("Admin");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		button1 = new JButton("Locations");
		button1.addActionListener(this);
		butPanel.add(button1);
		
		button2 = new JButton("Users");
		button2.addActionListener(this);
		butPanel.add(button2);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		cl=controller;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button1)
		{
			LocationsDialog a = new LocationsDialog(cl);
		}
		else if(e.getSource() == button2)
		{
			UserDialog b = new UserDialog();
		}
	}

}
