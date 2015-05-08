package hkust.cse.calendar.gui;

import hkust.cse.calendar.apptstorage.ApptStorageNullImpl;
import hkust.cse.calendar.unit.Appt;
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
import java.sql.Timestamp;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class InviteDialog extends JFrame implements ActionListener
{	
	private User Iuser;
	private Appt Iappt;
	private String Iline;
	private JButton button;
	private JButton closeButton;
		public InviteDialog(User user,Appt appt,String name,Timestamp start,Timestamp end,String line)		// Create a dialog to log in	{
		{
		setTitle("Invitation");
		
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
		namePanel.add(new JLabel(name+" has invited you to join the event from "+start.toString()+" to "+end.toString()));
		top.add(namePanel);
		
		contentPane.add("North", top);
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		button = new JButton("Yes");
		button.addActionListener(this);
		butPanel.add(button);
		closeButton = new JButton("No");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);
		contentPane.add("South", butPanel);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		Iuser=user;
		Iappt=appt;
		Iline=line;
		
	}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == button)
			{
				ApptStorageNullImpl a=new ApptStorageNullImpl(Iuser);
				a.RemoveApptfromtext(Iappt);
			       try {
			          	FileWriter fstream=new FileWriter("appt_list.txt",true);
			          	BufferedWriter out=new BufferedWriter(fstream);
			    		String[] splited = Iline.split("\\|");
			    		out.write(splited[0]+"|"+splited[1]+"|"+splited[2]+"|"+splited[3]+"|"+splited[4]+"|"+splited[5]+"|"+splited[6]+"|"+splited[7]+"|"+splited[8]+"|");
			    		for (int j=9;j<splited.length;j=j+2){
			    			if (splited[j].equals(Iuser.ID()))
			    					splited[j+1]="Attend";
			    		}
			    		for (int j=9;j<splited.length;j++){
			    			out.write(splited[j]+"|");
			    		}
			              out.write("\n");
			              out.close();
			              System.out.println("saved");
			    
			      } catch (IOException e1) {
			          // TODO Auto-generated catch block
			              System.out.println("cannot open appt_list.txt");
			              e1.printStackTrace();
			      }
				dispose();
			}
			else if(e.getSource() == closeButton)
			{
				ApptStorageNullImpl a=new ApptStorageNullImpl(Iuser);
				a.RemoveApptfromtext(Iappt);
			       try {
			          	FileWriter fstream=new FileWriter("appt_list.txt",true);
			          	BufferedWriter out=new BufferedWriter(fstream);
			    		String[] splited = Iline.split("\\|");
			    		out.write(splited[0]+"|"+splited[1]+"|"+splited[2]+"|"+splited[3]+"|"+splited[4]+"|"+splited[5]+"|"+splited[6]+"|"+splited[7]+"|"+splited[8]+"|");
			    		for (int j=9;j<splited.length;j=j+2){
			    			if (splited[j].equals(Iuser.ID()))
			    					splited[j+1]="Reject";
			    		}
			    		for (int j=9;j<splited.length;j++){
			    			out.write(splited[j]+"|");
			    		}
			              out.write("\n");
			              out.close();
			              System.out.println("saved");
			    
			      } catch (IOException e1) {
			          // TODO Auto-generated catch block
			              System.out.println("cannot open appt_list.txt");
			              e1.printStackTrace();
			      }
				dispose();
			}
		}
}
