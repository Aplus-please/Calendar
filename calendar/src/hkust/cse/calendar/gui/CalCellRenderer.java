package hkust.cse.calendar.gui;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

class CalCellRenderer extends DefaultTableCellRenderer

{

	private int r;

	private int c;
	
	public boolean hasAppt;

	public CalCellRenderer(Object value, boolean hasAppt) {
		if (value != null) {
			setForeground(Color.red);
		} else
			setForeground(Color.black);
		if(hasAppt){
			setBackground(Color.pink);
		} else{
			setBackground(Color.white);
		}	
		setHorizontalAlignment(SwingConstants.RIGHT);
		setVerticalAlignment(SwingConstants.TOP);
	}

//	public CalCellRenderer(int value) {
//		if (value == 1) {
//			setBackground(Color.green);
//		} else
//			setBackground(Color.white);
//	}
//	
//	public void ChangeCellColor(int r, int c){
//		setBackground(Color.pink);
//	}
	
	public void setHasAppt(boolean a){
		hasAppt = a;
	}

	public int row() {
		return r;
	}

	public int col() {
		return c;
	}

}
