package hkust.cse.calendar.gui;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.userstorage.UserStorage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class AdminDialog extends JFrame implements ActionListener{
	
	private UserStorage us;
	CalGrid parent;
	
	private JList<User> list;
	private JButton inspecBut;
	private JButton deleteBut;
	private DefaultListModel listModel;
	
	AdminDialog(User currentUser, CalGrid c){
		
		setTitle("Account Management");
		
		Container contentPane;
		contentPane = getContentPane();
		
		us = new UserStorage("user_list.txt");
		parent = c;
		
		listModel = new DefaultListModel();
		for(int i = 0; i < us.getNumOfUsers(); i++){
			if(us.getUser(i).ID().equalsIgnoreCase(currentUser.ID())){
				
			}
			listModel.addElement(us.getUser(i));
		}
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel listPanel = new JPanel();
		listPanel.add(new JLabel("Please choose a user from below:"));
//        listPanel.add(listScroller);
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		listPanel.add(list);
		top.add(listPanel);
		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		inspecBut = new JButton("Inspect");
		inspecBut.addActionListener(this);
		butPanel.add(inspecBut);

		deleteBut = new JButton("Delete");
		deleteBut.addActionListener(this);
		butPanel.add(deleteBut);

		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	 

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == inspecBut){
			int selectIndex = list.getSelectedIndex();
			User u = list.getSelectedValue();
			if(u == null){
				JOptionPane.showMessageDialog(this,
						"Please select a user.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				InspectDialog c = new InspectDialog(u);
				listModel.set(selectIndex, c.getUser());
//				c.dispose();
			}
			
		}
		if(e.getSource() == deleteBut){
			int deleteIndex = list.getSelectedIndex();
			if(deleteIndex == -1){
				JOptionPane.showMessageDialog(this,
						"Please select a user.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				Appt[] userarray = parent.controller.mApptStorage.RetrieveRemovedAppts(us.user[deleteIndex]);
				if(userarray != null){
					us.user[deleteIndex].status = userarray.length;
					us.update();
				}
				else{
				us.delete(deleteIndex);
				listModel.remove(deleteIndex);
			}
		}
	}
}}

