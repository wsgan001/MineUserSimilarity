package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class StatTableModel extends AbstractTableModel {
	private String[] columnNames = {"User", "Number of days", "Total number", "Min number in a day", "Max number in a day", "Average number"};
	private Object[][] data;
	
	public StatTableModel(List<String> selectedList, Dataset selectedDataset, boolean sourceData, String selectedParaSetting) {		
		if(sourceData || (selectedDataset.getType().equals("SP"))) {
			data = new Object[selectedList.size()][columnNames.length];
			String inputPath;
			if(sourceData) {
				inputPath = selectedDataset.getOutputPath() + "/Stats/SourceDataAndStayPoints/SourceData.txt";		
			} else {
				inputPath = selectedDataset.getOutputPath() + "/Stats/StayPoints.txt";
			}
			try {
				String[] selectedUsers = selectedList.toArray(new String[0]);
				Arrays.sort(selectedUsers);
				int foundUsers = 0;
				
				BufferedReader br = new BufferedReader(new FileReader(inputPath));
				String aLine;
				while(foundUsers < selectedUsers.length && (aLine = br.readLine()) != null) {
					String[] fields = aLine.split(" ");
					if(Arrays.binarySearch(selectedUsers, fields[0]) < 0) {
						continue;
					} else {
						for(int i = 0; i < 6; i++) {
							if(i != 5) {
								data[foundUsers][i] = fields[i];
							} else {								
								data[foundUsers][i] = String.format("%1$.1f", Double.parseDouble(fields[i]));
							}
						}
						foundUsers++;
					}
				}
				br.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} else {
			String inputPath = selectedDataset.getOutputPath() + "/Stats/SourceDataAndStayPoints/" + selectedParaSetting;
			ArrayList<String[]> initialData = new ArrayList<String[]>();
			try {
				String[] selectedUsers = selectedList.toArray(new String[0]);
				Arrays.sort(selectedUsers);
				int foundUsers = 0;
				
				BufferedReader br = new BufferedReader(new FileReader(inputPath));
				String aLine;
				while(foundUsers < selectedUsers.length && (aLine = br.readLine()) != null) {
					String[] fields = aLine.split(" ");
					if(Arrays.binarySearch(selectedUsers, fields[0]) < 0) {
						continue;
					} else {
						initialData.add(fields);
						foundUsers++;
					}
				}
				
				data = new Object[initialData.size()][columnNames.length];
				for(int i = 0; i < initialData.size(); i++) {
					String[] aRow = initialData.get(i);
					/*data[i] = Arrays.copyOf(aRow, aRow.length);*/
					for(int j = 0; j < 6; j++) {
						if(j != 5) {
							data[i][j] = aRow[j];
						} else {								
							data[i][j] = String.format("%1$.1f", Double.parseDouble(aRow[j]));
						}
					}
				}
				br.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}/*
		data = new Object[selectedList.size()][columnNames.length];
		
		for(int i = 0; i < selectedList.size(); i++) {
			String userID = selectedList.get(i);
			try {
				BufferedReader br = new BufferedReader(new FileReader(inputPath + File.separator + userID + ".txt"));
				String aLine = null;
				if(gps) {
					for(int j = 0; j < 6; j++) {
						aLine = br.readLine();
						data[i][j] = aLine;
					}
				} else {
					for(int j = 0; j < 2; j++) {
						aLine = br.readLine();
						data[i][j] = aLine;
					}
					for(int j = 0; j < 4; j++) {
						aLine = br.readLine();
					}
					for(int j = 0; j < 4; j++) {
						aLine = br.readLine();
						data[i][j + 2] = aLine;
					}
				}
				br.close();
			} catch(FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Cannot find the stat file of the user " + userID + ".", "File not found", JOptionPane.ERROR_MESSAGE);
				return;
			} catch(IOException e) {
				JOptionPane.showMessageDialog(null, "An error occurred when reading the stat file of the user " + userID + ".", "File input error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}*/
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data[row][col];
	}

	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}	
	
	public Class<? extends Object> getColumnClass(int c) {		
        return Number.class;
    }
}
