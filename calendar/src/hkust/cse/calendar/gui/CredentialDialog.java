package hkust.cse.calendar.gui;

import hkust.cse.calendar.unit.User;
import hkust.cse.calendar.userstorage.UserStorage;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CredentialDialog extends JFrame implements ActionListener {
	private JTextField usernameF;
	private JPasswordField oldPasswordF;
	private JPasswordField newPasswordF;
	private JTextField emailF;
	private JButton saveButton;
	private JButton closeButton;
	private User user;
	private UserStorage us;

	public CredentialDialog(User newUser) // Create a dialog to log in {
	{
		if(newUser == null)
			System.out.println("the newUser is null object.");
		us = new UserStorage("user_list.txt");
		user = newUser;
		
		setTitle("Change Information");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		Container contentPane;
		contentPane = getContentPane();

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		JPanel oldPasswordPanel = new JPanel();
		oldPasswordPanel.add(new JLabel("Old Password:"));
		oldPasswordF = new JPasswordField(15);
		oldPasswordPanel.add(oldPasswordF);
		top.add(oldPasswordPanel);

		JPanel newPasswordPanel = new JPanel();
		newPasswordPanel.add(new JLabel("New Password: "));
		newPasswordF = new JPasswordField(15);
		newPasswordPanel.add(newPasswordF);
		top.add(newPasswordPanel);

		JPanel emailPanel = new JPanel();
		emailPanel.add(new JLabel("Email: "));
		emailF = new JTextField(user.Email(),15);
		emailPanel.add(emailF);
		top.add(emailPanel);
		
		JPanel usernamePanel = new JPanel();
		usernamePanel.add(new JLabel("Full Name:"));
		usernameF = new JTextField(user.FullName(),15);
		usernamePanel.add(usernameF);
		top.add(usernamePanel);

		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		saveButton = new JButton("OK");
		saveButton.addActionListener(this);
		butPanel.add(saveButton);

		closeButton = new JButton("Cancel");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);

		contentPane.add("South", butPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == saveButton){
				if(user == null)
					System.out.println("the user is null object.");
				if(oldPasswordF.getText().equals(user.Password())){
					
					if(!newPasswordF.getText().equals(""))
						user.Password(newPasswordF.getText());
					user.Email(emailF.getText());
					user.FullName(usernameF.getText());
					us.update(user);
					dispose();
				}else{
					JOptionPane.showMessageDialog(this,
							"Inccorect password, please try again.",
							"Input Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			if(e.getSource() == closeButton)
				dispose();
		}
}
