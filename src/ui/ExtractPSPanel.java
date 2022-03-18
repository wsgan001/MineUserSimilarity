package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ExtractPSPanel extends JPanel {
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Main.AllowExit allowExit;

	/**
	 * Create the panel.
	 */
	public ExtractPSPanel(final JList<String> list, final Dataset aDataset, final JComboBox<String> SPParaComboBox, Main.AllowExit anObject) {
		allowExit = anObject;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Parameters for extracting frequent pattern sets", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{90, 0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[] {25, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblSupportThreshold = new JLabel("Support threshold:");
		GridBagConstraints gbc_lblSupportThreshold = new GridBagConstraints();
		gbc_lblSupportThreshold.anchor = GridBagConstraints.WEST;
		gbc_lblSupportThreshold.insets = new Insets(0, 0, 0, 5);
		gbc_lblSupportThreshold.gridx = 0;
		gbc_lblSupportThreshold.gridy = 0;
		panel_2.add(lblSupportThreshold, gbc_lblSupportThreshold);
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("<html>\r\nIt specifies the minimum percentage of trajectories that support a specific pattern \r\n<br/>\r\nfor it to be a frequent pattern. \r\n<br/>\r\nIt should be between 0 and 1. A typical value is 0.1.\r\n<html/>");
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 0, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 0;
		panel_2.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblTimeTolerance = new JLabel("Time tolerance:");
		GridBagConstraints gbc_lblTimeTolerance = new GridBagConstraints();
		gbc_lblTimeTolerance.insets = new Insets(0, 0, 0, 5);
		gbc_lblTimeTolerance.anchor = GridBagConstraints.EAST;
		gbc_lblTimeTolerance.gridx = 2;
		gbc_lblTimeTolerance.gridy = 0;
		panel_2.add(lblTimeTolerance, gbc_lblTimeTolerance);
		
		textField_5 = new JTextField();
		textField_5.setToolTipText("<html>\r\nIt is the maximum allowed time length that a time length between two RoIs in a \r\n<br/>\r\npattern can differ from a time length between two points in a trajectory in order to \r\n<br/>\r\nlet the trajectory contain the pattern. A typical value is 7200.\r\n<html/>");
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 0, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 0;
		panel_2.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblS = new JLabel("s");
		GridBagConstraints gbc_lblS = new GridBagConstraints();
		gbc_lblS.gridx = 4;
		gbc_lblS.gridy = 0;
		panel_2.add(lblS, gbc_lblS);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] {0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[] {25, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblSideCell = new JLabel("Side length of cell:");
		GridBagConstraints gbc_lblSideCell = new GridBagConstraints();
		gbc_lblSideCell.insets = new Insets(0, 2, 0, 5);
		gbc_lblSideCell.anchor = GridBagConstraints.EAST;
		gbc_lblSideCell.gridx = 0;
		gbc_lblSideCell.gridy = 0;
		panel_3.add(lblSideCell, gbc_lblSideCell);
		
		textField_6 = new JTextField();
		textField_6.setToolTipText("<html>\r\nIt refers to the span of latitudes or longitudes a cell covers. \r\n<br/>\r\na cell is a terminology used in course of trajectory pattern mining.\r\n<br/>\r\nA typical value is 0.02.\r\n<html/>");
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 0;
		panel_3.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);
		
		JLabel lblRoiFiles = new JLabel("RoI files:");
		GridBagConstraints gbc_lblRoiFiles = new GridBagConstraints();
		gbc_lblRoiFiles.insets = new Insets(0, 31, 0, 5);
		gbc_lblRoiFiles.anchor = GridBagConstraints.EAST;
		gbc_lblRoiFiles.gridx = 2;
		gbc_lblRoiFiles.gridy = 0;
		panel_3.add(lblRoiFiles, gbc_lblRoiFiles);
		
		final WideJComboBox<String> comboBox = new WideJComboBox<String>();		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 0;
		panel_3.add(comboBox, gbc_comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Type of days taken into account", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1);
		
		final JRadioButton rdbtnAllDays = new JRadioButton("All days");
		buttonGroup.add(rdbtnAllDays);
		panel_1.add(rdbtnAllDays);
		
		final JRadioButton rdbtnWeekdays = new JRadioButton("Weekdays");
		buttonGroup.add(rdbtnWeekdays);
		panel_1.add(rdbtnWeekdays);
		
		final JRadioButton rdbtnWeekends = new JRadioButton("Weekends");
		buttonGroup.add(rdbtnWeekends);
		panel_1.add(rdbtnWeekends);
		
		JPanel panel_4 = new JPanel();
		add(panel_4, BorderLayout.SOUTH);
		
		final JButton btnStart = new JButton("Start");		
		panel_4.add(btnStart);
		
		JButton btnClear = new JButton("Clear");		
		panel_4.add(btnClear);
		
		JPanel panel_5 = new JPanel();
		add(panel_5, BorderLayout.CENTER);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(7, 7, 7, 7);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_5.add(scrollPane, gbc_scrollPane);
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		ToolTipManager.sharedInstance().setDismissDelay(15000);
		comboBox.setPrototypeDisplayValue("MMMMMMMMMMMM");
		
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				List<String> selectedList = list.getSelectedValuesList();
				if(selectedList.size() == 0) {					
					return;
				}
				
				ArrayList<String> usableRoIFiles = new ArrayList<String>();
				if(aDataset.getType().equals("GPS")) {
					String paraSetting = (String)SPParaComboBox.getSelectedItem();
					HashSet<String> selectedUsersSet = new HashSet<String>(selectedList);
					String[] RoIFiles = new File(aDataset.getOutputPath() + "/RoIs/" + paraSetting).list();				
					for(String aFile : RoIFiles) {
						String[] fields = aFile.split("-");
						String[] users = fields[0].split("_");
						HashSet<String> usersAnRoI = new HashSet<String>();
						for(String aUser : users) {
							usersAnRoI.add(aUser);
						}

						if(usersAnRoI.containsAll(selectedUsersSet)) {
							usableRoIFiles.add(aFile);
						}					
					}					
				} else {
					HashSet<String> selectedUsersSet = new HashSet<String>(selectedList);
					String[] RoIFiles = new File(aDataset.getOutputPath() + "/RoIs").list();				
					for(String aFile : RoIFiles) {
						String[] fields = aFile.split("-");
						String[] users = fields[0].split("_");
						HashSet<String> usersAnRoI = new HashSet<String>();
						for(String aUser : users) {
							usersAnRoI.add(aUser);
						}

						if(usersAnRoI.containsAll(selectedUsersSet)) {
							usableRoIFiles.add(aFile);
						}					
					}
				}
				/*HashSet<String> selectedUsersSet = new HashSet<String>(selectedList);
				String[] RoIFiles = new File(aDataset.getOutputPath() + File.separator + "RoIs").list();
				ArrayList<String> usableRoIFiles = new ArrayList<String>();
				for(String aFile : RoIFiles) {
					String[] fields = aFile.split("-");
					String[] users = fields[0].split("_");
					HashSet<String> usersAnRoI = new HashSet<String>();
					for(String aUser : users) {
						usersAnRoI.add(aUser);
					}
					
					if(usersAnRoI.containsAll(selectedUsersSet)) {
						usableRoIFiles.add(aFile);
					}					
				}*/
				
				comboBox.removeAllItems();
				for(String aUsableRoIFile : usableRoIFiles) {
					comboBox.addItem(aUsableRoIFile);
				}
				comboBox.setWide(true);
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> selectedList = list.getSelectedValuesList();
				if(selectedList.size() == 0) {
					JOptionPane.showMessageDialog(null, "You haven't chosen any user yet.", "Choose users", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String selectedRoIFile = (String)comboBox.getSelectedItem();
				if(selectedRoIFile == null) {
					JOptionPane.showMessageDialog(null, "You haven't chosen an RoI file yet.", "Choose an RoI file", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				HashSet<String> selectedUsersSet = new HashSet<String>(selectedList);
				String[] fields = selectedRoIFile.split("-");
				String[] users = fields[0].split("_");
				HashSet<String> usersAnRoI = new HashSet<String>();
				for(String aUser : users) {
					usersAnRoI.add(aUser);
				}
				if(!usersAnRoI.containsAll(selectedUsersSet)) {
					JOptionPane.showMessageDialog(null, "The RoI file you chose is not usable for the users you chose.", "Choose another RoI file", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				try {
					double supportThreshold = Double.parseDouble(textField_4.getText().trim());
					double timeTolerance = Double.parseDouble(textField_5.getText().trim());
					double side = Double.parseDouble(textField_6.getText().trim());
					
					if(supportThreshold < 0.0 || supportThreshold > 1.0) {
						JOptionPane.showMessageDialog(null, "The support threshold you entered is not between 0 and 1.", "Input error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(timeTolerance < 0.0) {
						JOptionPane.showMessageDialog(null, "The time tolerance you entered is not non-negative.", "Input error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(side <= 0.0) {
						JOptionPane.showMessageDialog(null, "The time tolerance you entered is not positive.", "Input error", JOptionPane.ERROR_MESSAGE);
						return;
					}		
					
					String paraSetting = null;
					if(aDataset.getType().equals("GPS")) {
						paraSetting = (String)SPParaComboBox.getSelectedItem();
					}
					boolean isSelectedAllDays = rdbtnAllDays.isSelected();
					boolean isSelectedWeekdays = rdbtnWeekdays.isSelected();
					boolean isSelectedWeekends = rdbtnWeekends.isSelected();
					if(isSelectedAllDays == false && isSelectedWeekdays == false && isSelectedWeekends == false) {
						JOptionPane.showMessageDialog(null, "Please first choose a type of days.", "Choose a type of days", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String paraPS;
					if(isSelectedAllDays == true) {
						paraPS = supportThreshold + "_" + timeTolerance + "_" + side + "_AllDays";
					} else if(isSelectedWeekends == true) {
						paraPS = supportThreshold + "_" + timeTolerance + "_" + side + "_Weekends";
					} else {
						paraPS = supportThreshold + "_" + timeTolerance + "_" + side + "_Weekdays";
					}
					File pathnameComparablePS = null;
					if(aDataset.getType().equals("GPS")) {						
						pathnameComparablePS = new File(aDataset.getOutputPath() + "/PatternSets/" + paraSetting + "/" + selectedRoIFile + "/" + paraPS);
					} else {
						pathnameComparablePS = new File(aDataset.getOutputPath() + "/PatternSets/" + selectedRoIFile + "/" + paraPS);
					}
					ArrayList<String> filteredList = new ArrayList<String>(selectedList);
					if(pathnameComparablePS.exists()) {
						String[] filenamesPS = pathnameComparablePS.list();
						for(int i = 0; i < filenamesPS.length; i++) {
							filenamesPS[i] = filenamesPS[i].substring(0, 3);
						}
						Arrays.sort(filenamesPS);
						for(String anSelectedUser : selectedList) {
							if(Arrays.binarySearch(filenamesPS, anSelectedUser) >= 0) {
								filteredList.remove(anSelectedUser);
							}
						}
					}
					if(filteredList.size() == 0) {
						JOptionPane.showMessageDialog(null, "All the users you selected have already been extracted from.\nNo more work needs to be done.", "Duplicate operation", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					btnStart.setEnabled(false);
					allowExit.ifAllowExit = false;
					PSExtractor worker = null;
					if(isSelectedAllDays == true) {						
						worker = new PSExtractor(aDataset, filteredList, textArea, btnStart, supportThreshold, timeTolerance, side, selectedRoIFile, (byte)-1, paraSetting, pathnameComparablePS);						
					}
					if(isSelectedWeekdays == true) {
						worker = new PSExtractor(aDataset, filteredList, textArea, btnStart, supportThreshold, timeTolerance, side, selectedRoIFile, (byte)1, paraSetting, pathnameComparablePS);
					}
					if(isSelectedWeekends == true) {
						worker = new PSExtractor(aDataset, filteredList, textArea, btnStart, supportThreshold, timeTolerance, side, selectedRoIFile, (byte)0, paraSetting, pathnameComparablePS);
					}
					Thread t = new Thread(worker);
		    		t.setPriority(Thread.MAX_PRIORITY);
		    		t.start();
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "The values you entered are not all numbers.", "Number format error", JOptionPane.ERROR_MESSAGE);
					return;
				}			
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
	}

	private class PSExtractor implements Runnable {
		private Dataset dataset;
		private List<String> list;
		private JTextArea textArea;
		private JButton btnStart;
		private double supportThreshold;
		private double timeTolerance;
		private double side;
		private String selectedRoIFile;
		private byte typeDay;
		private String paraSetting;
		private File PSFilePath;
		
		public PSExtractor(Dataset aDataset, List<String> aList, JTextArea aTextArea, JButton aButton, double aSupportThreshold, double aTimeTolerance, double aSide, String aSelectedRoIFile, byte aTypeDay, String aParaSetting, File aDir) {
			dataset = aDataset;
			list = aList;
			textArea = aTextArea;			
			btnStart = aButton;
			supportThreshold = aSupportThreshold;
			timeTolerance = aTimeTolerance;
			side = aSide;
			selectedRoIFile = aSelectedRoIFile;
			typeDay = aTypeDay;
			paraSetting = aParaSetting;
			PSFilePath = aDir;
		}
		
		public void run() {
			double minLatitude = 0.0;
			double maxLatitude = 0.0;
			double minLongitude = 0.0;
			double maxLongitude = 0.0;
			String statRoIFilePath;
			if(dataset.getType().equals("GPS")) {
				statRoIFilePath = dataset.getOutputPath() + "/Stats/RoIs/" + paraSetting + "/" + selectedRoIFile;
			} else {
				statRoIFilePath = dataset.getOutputPath() + "/Stats/RoIs/" + selectedRoIFile;
			}
			try {
				BufferedReader bf = new BufferedReader(new FileReader(statRoIFilePath));
				String aLine;
				int i = 0;
				while((aLine = bf.readLine()) != null && i < 4) {
					if(i == 0) {
						minLatitude = Double.parseDouble(aLine.trim());						
					} else if(i == 1) {
						minLongitude = Double.parseDouble(aLine.trim());
					} else if(i == 2) {
						maxLatitude = Double.parseDouble(aLine.trim());
					} else if(i == 3) {
						maxLongitude = Double.parseDouble(aLine.trim());
					}
					i++;
				}			
				bf.close();
			} catch(Exception e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "An error occurred when reading coordinates of min and max points of the RoI file.", "Error", JOptionPane.ERROR_MESSAGE);
						btnStart.setEnabled(true);
						return;
					}
				});
			}
			
			if(!PSFilePath.exists()) {
				PSFilePath.mkdirs();
			}
			for(String aUser : list) {
				try {
					ProcessBuilder pb;
					if(dataset.getType().equals("GPS")) {
						if(typeDay == -1) {
							String[] cmd = {"./TPMForIndividual.exe", dataset.getOutputPath() + "/StayPoints/" + paraSetting + "/" + aUser + ".txt", "" + supportThreshold, "" + timeTolerance, "-static", dataset.getOutputPath() + "/RoIs/" + paraSetting + "/" + selectedRoIFile, "-no_interpolate", "-side", "" + side, "-minlatitude", "" + minLatitude, "-maxlatitude", "" + maxLatitude, "-minlongitude", "" + minLongitude, "-maxlongitude", "" + maxLongitude, "-outputpath", PSFilePath.getAbsolutePath()};
							pb = new ProcessBuilder(cmd);
						} else if(typeDay == 0) {
							String[] cmd = {"./TPMForIndividual.exe", dataset.getOutputPath() + "/StayPoints/" + paraSetting + "/" + aUser + ".txt", "" + supportThreshold, "" + timeTolerance, "-static", dataset.getOutputPath() + "/RoIs/" + paraSetting + "/" + selectedRoIFile, "-no_interpolate", "-side", "" + side, "-minlatitude", "" + minLatitude, "-maxlatitude", "" + maxLatitude, "-minlongitude", "" + minLongitude, "-maxlongitude", "" + maxLongitude, "-outputpath", PSFilePath.getAbsolutePath(), "-weekdays", "" + 0};
							pb = new ProcessBuilder(cmd);
						} else {
							String[] cmd = {"./TPMForIndividual.exe", dataset.getOutputPath() + "/StayPoints/" + paraSetting + "/" + aUser + ".txt", "" + supportThreshold, "" + timeTolerance, "-static", dataset.getOutputPath() + "/RoIs/" + paraSetting + "/" + selectedRoIFile, "-no_interpolate", "-side", "" + side, "-minlatitude", "" + minLatitude, "-maxlatitude", "" + maxLatitude, "-minlongitude", "" + minLongitude, "-maxlongitude", "" + maxLongitude, "-outputpath", PSFilePath.getAbsolutePath(), "-weekdays", "" + 1};
							pb = new ProcessBuilder(cmd);
						}
					} else {
						if(typeDay == -1) {
							String[] cmd = {"./TPMForIndividual.exe", dataset.getInputPath() + "/" + aUser + ".txt", "" + supportThreshold, "" + timeTolerance, "-static", dataset.getOutputPath() + "/RoIs/" + selectedRoIFile, "-no_interpolate", "-side", "" + side, "-minlatitude", "" + 15.12148/*minLatitude*/, "-maxlatitude", "" + 38.066629/*maxLatitude*/, "-minlongitude", "" + 126.423577/*minLongitude*/, "-maxlongitude", "" + 145.724293/*maxLongitude*/, "-outputpath", PSFilePath.getAbsolutePath()};
							pb = new ProcessBuilder(cmd);
						} else if(typeDay == 0) {
							String[] cmd = {"./TPMForIndividual.exe", dataset.getInputPath() + "/" + aUser + ".txt", "" + supportThreshold, "" + timeTolerance, "-static", dataset.getOutputPath() + "/RoIs/" + selectedRoIFile, "-no_interpolate", "-side", "" + side, "-minlatitude", "" + minLatitude, "-maxlatitude", "" + maxLatitude, "-minlongitude", "" + minLongitude, "-maxlongitude", "" + maxLongitude, "-outputpath", PSFilePath.getAbsolutePath(), "-weekdays", "" + 0};
							pb = new ProcessBuilder(cmd);
						} else {
							String[] cmd = {"./TPMForIndividual.exe", dataset.getInputPath() + "/" + aUser + ".txt", "" + supportThreshold, "" + timeTolerance, "-static", dataset.getOutputPath() + "/RoIs/" + selectedRoIFile, "-no_interpolate", "-side", "" + side, "-minlatitude", "" + 15.12148/*minLatitude*/, "-maxlatitude", "" + 38.066629/*maxLatitude*/, "-minlongitude", "" + 126.423577/*minLongitude*/, "-maxlongitude", "" + 145.724293/*maxLongitude*/, "-outputpath", PSFilePath.getAbsolutePath(), "-weekdays", "" + 1};
							pb = new ProcessBuilder(cmd);
						}
					}
					
					Process p = pb.start();
					BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
					BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					String line;
					while ((line = bri.readLine()) != null) {						
						textArea.append(line + "\n");
					}
					bri.close();
					while ((line = bre.readLine()) != null) {
						textArea.append(line + "\n");
					}
					bre.close();
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							textArea.append("****************************************************************\n");
						}
					});
					
				} catch(Exception e) {
					e.printStackTrace();
				}					
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					btnStart.setEnabled(true);
					allowExit.ifAllowExit = true;
				}
			});
		}
	}
}
