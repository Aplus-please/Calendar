package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemoveLocationDialog extends JFrame implements ActionListener {
	
	private CalGrid calgrid;

	private String inviteUser;
	private Appt inviteAppt;
	private Location location;

	private String inviteMessage;
	private JButton attendBut;
	
	RemoveLocationDialog(Appt appt, Location l, CalGrid c){
		inviteAppt = appt;
		location = l;
		calgrid = c;
		
		setTitle("Confirmation");
		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		

		JPanel messagePanel = new JPanel();
		inviteMessage = "Location "+location.getName()+" has been removed";
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
			location.status--;
			if(location.status == 0){
				calgrid.controller.mApptStorage.mLocations.remove(location.getName());
				calgrid.controller.ManageAppt(inviteAppt, calgrid.controller.REMOVE);
				calgrid.repaint();
				
			}
			else
				calgrid.controller.mApptStorage.mLocations.put(location.getName(), location);
			calgrid.controller.mApptStorage.saveLocationToTxt();
			dispose();
		}
	}
}
