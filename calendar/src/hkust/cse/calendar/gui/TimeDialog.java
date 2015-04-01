package hkust.cse.calendar.gui;

import hkust.cse.calendar.unit.TimeMachine;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class TimeDialog extends JFrame {

	private JTextField locNameText;

	private JButton AddBut;
	private JButton RemoveBut;
	private JLabel yearL;
	private JTextField yearF;
	private JLabel monthL;
	private JTextField monthF;
	private JLabel dayL;
	private JTextField dayF;
	private JLabel sTimeHL;
	private JTextField sTimeH;
	private JLabel sTimeML;
	private JTextField sTimeM;
	private JLabel eTimeHL;
	private JTextField eTimeH;
	private JLabel sTimeSL;
	private JTextField sTimeS;
	private TimeMachine tm = new TimeMachine();
	private Date d = new Date(System.currentTimeMillis());
	private Calendar cl = Calendar.getInstance();

	public TimeDialog() {

		setTitle("Time Machine");
		getContentPane().setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setSize(300, 200);

		JPanel pDate = new JPanel();
		Border dateBorder = new TitledBorder(null, "DATE");
		pDate.setBorder(dateBorder);

		yearL = new JLabel("YEAR: ");
		pDate.add(yearL);
		yearF = new JTextField(6);
		pDate.add(yearF);
		monthL = new JLabel("MONTH: ");
		pDate.add(monthL);
		monthF = new JTextField(4);
		pDate.add(monthF);
		dayL = new JLabel("DAY: ");
		pDate.add(dayL);
		dayF = new JTextField(4);
		pDate.add(dayF);

		JPanel psTime = new JPanel();
		Border stimeBorder = new TitledBorder(null, "TIME");
		psTime.setBorder(stimeBorder);
		sTimeHL = new JLabel("Hour");
		psTime.add(sTimeHL);
		sTimeH = new JTextField(4);
		psTime.add(sTimeH);
		sTimeML = new JLabel("Minute");
		psTime.add(sTimeML);
		sTimeM = new JTextField(4);
		psTime.add(sTimeM);
		sTimeSL = new JLabel("Second");
		psTime.add(sTimeSL);
		sTimeS = new JTextField(4);
		psTime.add(sTimeS);

		AddBut = new JButton("Change Time");
		AddBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("in time machine");
				// System.out.println(yearText.getText());
				// System.out.println(Integer.parseInt(yearText.getText()));

				// System.out.println(Integer.getInteger(yearText.getText()));
				tm.setTimeMachine(Integer.parseInt(yearF.getText()),
						Integer.parseInt(monthF.getText()),
						Integer.parseInt(dayF.getText()),
						Integer.parseInt(sTimeH.getText()),
						Integer.parseInt(sTimeM.getText()),
						Integer.parseInt(sTimeS.getText()));
				if (tm.getCount() == 0) {
					tm.turnOn();
				}
				/*
				 * cl.set(Calendar.YEAR,Integer.parseInt(yearF.getText()));
				 * cl.set(Calendar.MONTH,Integer.parseInt(yearF.getText()));
				 * cl.set(Calendar.DATE,Integer.parseInt(yearF.getText()));
				 * cl.set(Calendar.HOUR,Integer.parseInt(yearF.getText()));
				 * cl.set(Calendar.MINUTE,Integer.parseInt(yearF.getText()));
				 * cl.set(Calendar.SECOND,Integer.parseInt(yearF.getText()));
				 */
				setVisible(false);
				dispose();
			}

		});
		AddBut.setEnabled(true);
		RemoveBut = new JButton("Cancel");
		RemoveBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();

			}

		});
		RemoveBut.setEnabled(true);

		getContentPane().add(psTime, BorderLayout.CENTER);
		getContentPane().add(pDate, BorderLayout.PAGE_START);
		getContentPane().add(AddBut, BorderLayout.SOUTH);
		getContentPane().add(RemoveBut, BorderLayout.EAST);

		System.out.println(tm.getYear());
		System.out.println(tm.getMonth());
		System.out.println(tm.getDate());
		System.out.println(tm.getHourOfDay());
		System.out.println(tm.getMinute());
		System.out.println(tm.getSecond());
		System.out.println(d.getYear());
		System.out.println(d.getMonth());
		System.out.println(d.getDate());
		System.out.println(d.getHours());
		System.out.println(d.getMinutes());
		System.out.println(d.getSeconds());
		// set(Calendar.MARCH,Calendar.SEPTEMBER);

		pack();
		setVisible(true);

	}
}
