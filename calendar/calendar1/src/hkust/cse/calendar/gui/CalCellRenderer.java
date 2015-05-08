package hkust.cse.calendar.gui;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class CalCellRenderer extends DefaultTableCellRenderer
{
	private int r;
	private int c;
	public boolean have_Appt;
	
	public CalCellRenderer(Object value, boolean have_Appt) {
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		
		if(have_Appt){setBackground(Color.yellow);}
		else {setBackground(Color.white);}
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}
	public int row() {
		return r;
	}
	public int col() {
		return c;
	}
}
