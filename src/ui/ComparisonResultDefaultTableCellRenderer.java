package ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ComparisonResultDefaultTableCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(column > 0) {
			double similarityValue = (double)value;
			if(similarityValue > 0.5) {
				setForeground(Color.WHITE);
			} else {
				setForeground(Color.BLACK);
			}
			
			setBackground(new Color((int)(255 - similarityValue * 255), (int)(255 - similarityValue * 255), (int)(255 - similarityValue * 255)));
			setValue(value);
			
		} else {
			setForeground(Color.BLACK);
			setBackground(Color.WHITE);
			setValue(value);
		}
		return this;
	}
	
	public void setValue(Object value) {
		if(value.toString().contains(".")) {
			setText((value == null) ? "" : String.format("%1$.2f", (double)value));
		} else {
			setText((value == null) ? "" : value.toString());
		}
	}
}
