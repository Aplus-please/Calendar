package hkust.cse.calendar.gui;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class CalCellRenderer extends DefaultTableCellRenderer

{

	private int r;

	private int c;
	
	public boolean Appt_exist;

	public CalCellRenderer(Object value) {
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		if(Appt_exist){setBackground(Color.pink);}
		setBackground(Color.white);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}
	
	public void ChangeCellColor(int r, int c){
		setBackground(Color.pink);
	}
	
	public void setAppt_exist(boolean a){
		Appt_exist = a;
	}

	public int row() {
		return r;
	}

	public int col() {
		return c;
	}

}
