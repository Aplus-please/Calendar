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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class SignUpDialog extends JFrame implements ActionListener {

	private UserStorage us;

	private JTextField username;
	private JPasswordField password;
	private JButton signUpButton;
	private JButton closeButton;
	private JRadioButton adminBut = new JRadioButton("Admin", false);
	private JRadioButton normalBut = new JRadioButton("Normal User", false);
	private JTextField email;
	private JTextField fullName;
	private JPanel panel;

	private boolean correctUserName = true;
	private boolean correctUserType = true;
	private boolean corrrctPassword = true;

	public SignUpDialog(UserStorage us1) // Create a dialog to log in {
	{
		us = us1;
		
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
		namePanel.add(new JLabel("Username: "));
		username = new JTextField(15);
		namePanel.add(username);
		top.add(namePanel);

		JPanel pwPanel = new JPanel();
		pwPanel.add(new JLabel("Password: "));
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);

		JPanel typePanel = new JPanel();
		typePanel.add(new JLabel("User Type: "));
		typePanel.add(adminBut);
		typePanel.add(normalBut);

		ButtonGroup group = new ButtonGroup();
		group.add(adminBut);
		group.add(normalBut);
		top.add(typePanel);
		
		JPanel emailPanel = new JPanel();
		emailPanel.add(new JLabel("Email: "));
		email = new JTextField(15);
		emailPanel.add(email);
		top.add(emailPanel);
		
		JPanel fullnamePanel = new JPanel();
		fullnamePanel.add(new JLabel("Full Name:"));
		fullName = new JTextField(15);
		fullnamePanel.add(fullName);
		top.add(fullnamePanel);

		contentPane.add("North", top);

		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		signUpButton = new JButton("Sign Up");
		signUpButton.addActionListener(this);

		panel = new JPanel();
		butPanel.add(panel);
		butPanel.add(signUpButton);

		closeButton = new JButton("Cancel");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);

		contentPane.add("South", butPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signUpButton) {
			if (username.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please input a username.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				correctUserName = false;
			} else
				correctUserName = true;

			if (us.IDExist(username.getText())) {
				JOptionPane.showMessageDialog(this,
						"This username is already exist. Try another username.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				correctUserName = false;
			}

			if (password.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Please input a password.",
						"Input Error", JOptionPane.ERROR_MESSAGE);
				corrrctPassword = false;
			} else
				corrrctPassword = true;
			if (!adminBut.isSelected() && !normalBut.isSelected()) {
				JOptionPane.showMessageDialog(this,
						"Please select a user type.", "Input Error",
						JOptionPane.ERROR_MESSAGE);
				correctUserType = false;
			} else
				correctUserType = true;
			
			if(correctUserName && corrrctPassword && correctUserType){
				User u = new User(username.getText(),password.getText(),adminBut.isSelected(),email.getText(), fullName.getText(),-1);
				us.add(u);
				dispose();
			}

		}
		if (e.getSource() == closeButton) {
			dispose();
		}
	}
}
