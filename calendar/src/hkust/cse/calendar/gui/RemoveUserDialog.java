package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.userstorage.UserStorage;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemoveUserDialog extends JFrame implements ActionListener {
	
	private CalGrid calgrid;

	private String inviteUser;
	private Appt inviteAppt;
	private User user;
//	private int index;

	private String inviteMessage;
	private JButton attendBut;
	
	private UserStorage us = new UserStorage("user_list.txt");
	
	RemoveUserDialog(Appt appt, User u, CalGrid c){
		inviteAppt = appt;
		user = u;
		calgrid = c;
//		index = i;
		
		setAlwaysOnTop(true);
		
		setTitle("Confirmation");
		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		

		JPanel messagePanel = new JPanel();
		inviteMessage = "User " + user.ID() + " has been removed ";
		messagePanel.add(new JLabel(inviteMessage));
		top.add(messagePanel);
		
		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		attendBut = new JButton("Accept");
		attendBut.addActionListener(this);
		butPanel.add(attendBut);

		contentPane.add("South", butPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == attendBut) {
			user.status--;
			if(user.status == 0){
				Appt[] temparray = calgrid.controller.mApptStorage.RetrieveAppts(user);
				for (int i=0;  i< temparray.length;i++){
				calgrid.controller.ManageAppt(temparray[i], calgrid.controller.REMOVE);}
				us.delete(us.UserIndex(user));
			}
			else
				us.update(user);
			dispose();
		}
	}
}
