package hkust.cse.calendar.gui;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InspectDialog extends JFrame implements ActionListener{
	private UserStorage us;
	private User user;
	
	private JTextField usernameF;
	private JTextField passwordF;
	private JTextField emailF;
	private JTextField fullNameF;
	private JButton saveButton;
	
	public InspectDialog(User u){
		
		us = new UserStorage("user_list.txt");
		user = us.user[us.UserIndex(u)];
		
		Container contentPane;
		contentPane = getContentPane();

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.add(new JLabel("Username:"));
		usernameF = new JTextField(u.ID(),15);
		usernamePanel.add(usernameF);
		top.add(usernamePanel);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.add(new JLabel("Password:"));
		passwordF = new JTextField(u.Password(),15);
		passwordPanel.add(passwordF);
		top.add(passwordPanel);
		
		JPanel emailPanel = new JPanel();
		emailPanel.add(new JLabel("Email: "));
		emailF = new JTextField(u.Email(),15);
		emailPanel.add(emailF);
		top.add(emailPanel);
		
		JPanel fullnamePanel = new JPanel();
		fullnamePanel.add(new JLabel("Full Name:"));
		fullNameF = new JTextField(u.FullName(),15);
		fullnamePanel.add(fullNameF);
		top.add(fullnamePanel);
		
		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		saveButton = new JButton("OK");
		saveButton.addActionListener(this);
		butPanel.add(saveButton);
		
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == saveButton){
			user.ID(usernameF.getText());
			user.Password(passwordF.getText());
			user.Email(emailF.getText());
			user.FullName(fullNameF.getText());
			us.update();
			dispose();
		}
	}

	public User getUser() {
		// TODO Auto-generated method stub
		return user;
	}
}
