package hkust.cse.calendar.gui;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class CalCellRenderer extends DefaultTableCellRenderer

{

	private int r;

	private int c;
	
	public static int row=-1,col=-1,state;
	
	public boolean Appt_exist;

	public CalCellRenderer(Object value, int r, int c) {
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		if(r==row&&c==col)
		{
			if(state==1)setBackground(Color.green);
			else if(state==2)setBackground(Color.yellow);
			else setBackground(Color.white);
		}
		if(Appt_exist){setBackground(Color.pink);}
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
