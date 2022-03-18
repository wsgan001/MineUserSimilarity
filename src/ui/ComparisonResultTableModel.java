package ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ComparisonResultTableModel extends AbstractTableModel {
	private String[] columnNames;
	private Object[][] data;
	
	public ComparisonResultTableModel(List<String> userNames, double[][] similarityMatrix) {
		columnNames = new String[userNames.size() + 1];
		columnNames[0] = "Users";
		for(int i = 0; i < userNames.size(); i++) {
			columnNames[i + 1] = userNames.get(i);
		}
		
		data = new Object[similarityMatrix.length][similarityMatrix[0].length + 1];
		for(int i = 0; i < similarityMatrix.length; i++) {
			data[i][0] = /*Double.parseDouble(*/userNames.get(i)/*)*/;
			for(int j = 0; j < similarityMatrix[i].length; j++) {
				data[i][j + 1] = similarityMatrix[i][j];
			}
		}
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data[row][col];
	}

	public Class<? extends Object> getColumnClass(int c) {		
        return Number.class;
    }
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}
}
