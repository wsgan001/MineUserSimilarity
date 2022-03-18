package ui;

import generateRoIs.StayPointClustering;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashSet;
import java.util.List;

@SuppressWarnings("serial")
public class GenerateRoIPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private Main.AllowExit allowExit;

	/**
	 * Create the panel.
	 */
	public GenerateRoIPanel(final JList<String> list, final Dataset aDataset, final JButton btnPS, final JComboBox<String> comboBox, Main.AllowExit anObject) {
		allowExit = anObject;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Parameters for generating RoIs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{101, 86, 11, 0};
		gbl_panel_1.rowHeights = new int[] {25, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblDeletionPercentage = new JLabel("Deletion percentage:");
		GridBagConstraints gbc_lblDeletionPercentage = new GridBagConstraints();
		gbc_lblDeletionPercentage.anchor = GridBagConstraints.WEST;
		gbc_lblDeletionPercentage.insets = new Insets(0, 0, 0, 5);
		gbc_lblDeletionPercentage.gridx = 0;
		gbc_lblDeletionPercentage.gridy = 0;
		panel_1.add(lblDeletionPercentage, gbc_lblDeletionPercentage);
		
		textField = new JTextField();
		textField.setToolTipText("<html>\r\nIt specifies the percentage of stay points to constitute noise and be removed.\r\n<br/>\r\nA typical value is 20 or 30.\r\n<html/>");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("%");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridx = 2;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{88, 86, 0};
		gbl_panel_2.rowHeights = new int[] {25, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblLowerBoundOf = new JLabel("Lower bound of K:");
		GridBagConstraints gbc_lblLowerBoundOf = new GridBagConstraints();
		gbc_lblLowerBoundOf.anchor = GridBagConstraints.WEST;
		gbc_lblLowerBoundOf.insets = new Insets(0, 13, 0, 5);
		gbc_lblLowerBoundOf.gridx = 0;
		gbc_lblLowerBoundOf.gridy = 0;
		panel_2.add(lblLowerBoundOf, gbc_lblLowerBoundOf);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("<html>\r\nK is a parameter used when removing outliers. Typical bounds are 5 and 35 \r\n<br/>\r\nwhen the number of stay points to be clustered is less than 1000, 10 and 50 when\r\n<br/>\r\nthe number is between 1000 and 3000, 20 and 100 when the number is more than\r\n<br/>\r\n3000. But these are not necessarily applicable. Different sets of stay points may\r\n<br/>\r\nrequire different bounds of K.\r\n<html/>\r\n\r\n");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_2.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{88, 86, 0};
		gbl_panel_3.rowHeights = new int[] {25, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblUpperBoundOf = new JLabel("Upper bound of K:");
		GridBagConstraints gbc_lblUpperBoundOf = new GridBagConstraints();
		gbc_lblUpperBoundOf.anchor = GridBagConstraints.WEST;
		gbc_lblUpperBoundOf.insets = new Insets(0, 13, 0, 5);
		gbc_lblUpperBoundOf.gridx = 0;
		gbc_lblUpperBoundOf.gridy = 0;
		panel_3.add(lblUpperBoundOf, gbc_lblUpperBoundOf);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("<html>\r\nK is a parameter used when removing outliers. Typical bounds are 5 and 35 \r\n<br/>\r\nwhen the number of stay points to be clustered is less than 1000, 10 and 50 when\r\n<br/>\r\nthe number is between 1000 and 3000, 20 and 100 when the number is more than\r\n<br/>\r\n3000. But these are not necessarily applicable. Different sets of stay points may\r\n<br/>\r\nrequire different bounds of K.\r\n<html/>");
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.anchor = GridBagConstraints.WEST;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel_3.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
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
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> selectedList = list.getSelectedValuesList();
				if(selectedList.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please first choose some users.", "Choose users", JOptionPane.INFORMATION_MESSAGE);
					return;
				}				
				
				/*HashSet<String> selectedUsers = new HashSet<String>(selectedList);
				String[] RoIFiles = new File(aDataset.getOutputPath() + File.separator + "RoIs").list();
				for(String aFile : RoIFiles) {
					String[] fields = aFile.split("%");
					String[] users = fields[0].split("_");
					HashSet<String> usersAnRoIFile = new HashSet<String>();
					for(String aUser : users) {
						usersAnRoIFile.add(aUser);
					}
					if(usersAnRoIFile.equals(selectedUsers)) {
						int ans = JOptionPane.showConfirmDialog(null, "You have generated the RoI file of these selected users.\nDo you want to do it again?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.YES_OPTION) {
							break;
						} else {
							return;
						}
					}
					if(usersAnRoIFile.containsAll(selectedUsers)) {
						int ans = JOptionPane.showConfirmDialog(null, "You have generated an RoI file whose users include these selected users.\nDo you want to continue generating their RoI file?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.YES_OPTION) {
							break;
						} else {
							return;
						}
					}
				}*/
				
				try {
					int deletionPercentage = Integer.parseInt(textField.getText().trim());
					String lowerBound = textField_1.getText().trim();
					int intLowerBound = Integer.parseInt(lowerBound);
					String upperBound = textField_2.getText().trim();
					int intUpperBound = Integer.parseInt(upperBound);
					
					if(deletionPercentage <= 0 || intLowerBound <= 0 || intUpperBound <= 0) {
						JOptionPane.showMessageDialog(null, "The three parameters must be positive integers.", "Number format error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(intUpperBound <= intLowerBound) {
						JOptionPane.showMessageDialog(null, "The upper bound of K you entered is not greater than the lower bound.", "Input error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String selectedPara = null;
					int popupMessageDecision = 0;
					if(aDataset.getType().equals("GPS")) {						
						selectedPara = (String)comboBox.getSelectedItem();
						File RoIFolder = new File(aDataset.getOutputPath() + "/RoIs");
						if(RoIFolder.exists()) {
							File theParaRoISubfoler = new File(RoIFolder + "/" + selectedPara);
							if(theParaRoISubfoler.exists()) {
								HashSet<String> selectedUsers = new HashSet<String>(selectedList);
								String[] RoIFiles = theParaRoISubfoler.list();
								for(String aFile : RoIFiles) {
									String[] fields = aFile.split("-");
									String[] users = fields[0].split("_");
									HashSet<String> usersAnRoIFile = new HashSet<String>();
									for(String aUser : users) {
										usersAnRoIFile.add(aUser);
									}

									if(usersAnRoIFile.equals(selectedUsers)) {
										if(fields[1].equals(deletionPercentage + "_" + lowerBound + "_" + upperBound + ".txt")) {
											/*JOptionPane.showMessageDialog(null, "You have generated the RoI file of these selected users using these parameters.\nThere is no need to do it again.", "Duplicate operation", JOptionPane.INFORMATION_MESSAGE);
										return;*/
											popupMessageDecision = 1;
										} else {
											if(popupMessageDecision != 1) {
												popupMessageDecision = 2;
											}
											/*int ans = JOptionPane.showConfirmDialog(null, "You have generated the RoI file of these selected users using a different parameter setting.\nDo you want to do it using the current parameter setting?", "Confirmation", JOptionPane.YES_NO_OPTION);
										if(ans == JOptionPane.NO_OPTION) {
											return;
										} else {
											break;
										}*/
										}
									}

									if(usersAnRoIFile.containsAll(selectedUsers)) {
										if(popupMessageDecision != 1 && popupMessageDecision != 2) {
											popupMessageDecision = 3;
										}
										/*int ans = JOptionPane.showConfirmDialog(null, "You have generated an RoI file whose users include these selected users.\nDo you want to continue generating their RoI file?", "Confirmation", JOptionPane.YES_NO_OPTION);
									if(ans == JOptionPane.YES_OPTION) {
										break;
									} else {
										return;
									}*/
									}
								}
								/*StringBuilder sb = new StringBuilder();
							for(String aUser : selectedList) {
								sb.append(aUser + "_");			
							}
							String selectedUserList = sb.toString();
							sb.append("-" + deletionPercentage + "_" + lowerBound + "_" + upperBound + ".txt");

							File theFile = new File(theParaRoISubfoler + "/" + sb.toString());
							if(theFile.exists()) {
								JOptionPane.showMessageDialog(null, "You have generated the RoI file of these selected users using these parameters.\nThere is no need to do it again.", "Duplicate operation", JOptionPane.INFORMATION_MESSAGE);
								return;
							}							

							HashSet<String> userLists = new HashSet<String>();
							String[] filenamesInThePara = theParaRoISubfoler.list();
							for(String aFilename : filenamesInThePara) {
								String[] fields = aFilename.split("-");
								userLists.add(fields[0]);
							}
							if(userLists.contains(selectedUserList)) {
								int ans = JOptionPane.showConfirmDialog(null, "You have generated the RoI file of these selected users using a different parameter setting.\nDo you want to do it using the current parameter setting?", "Confirmation", JOptionPane.YES_NO_OPTION);
								if(ans == JOptionPane.NO_OPTION) {
									return;
								}
							}*/
							}					
						}						
					} else {
						HashSet<String> selectedUsers = new HashSet<String>(selectedList);
						File RoIFolder = new File(aDataset.getOutputPath() + File.separator + "RoIs");
						if(RoIFolder.exists()) {
							String[] RoIFiles = RoIFolder.list();
							for(String aFile : RoIFiles) {
								String[] fields = aFile.split("-");
								String[] users = fields[0].split("_");
								HashSet<String> usersAnRoIFile = new HashSet<String>();
								for(String aUser : users) {
									usersAnRoIFile.add(aUser);
								}
								
								if(usersAnRoIFile.equals(selectedUsers)) {
									if(fields[1].equals(deletionPercentage + "_" + lowerBound + "_" + upperBound + ".txt")) {
										popupMessageDecision = 1;
									} else {
										if(popupMessageDecision != 1) {
											popupMessageDecision = 2;
										}
									}
								}

								if(usersAnRoIFile.containsAll(selectedUsers)) {
									if(popupMessageDecision != 1 && popupMessageDecision != 2) {
										popupMessageDecision = 3;
									}
								}
							}
						}
					}
					
					if(popupMessageDecision == 1) {
						int ans = JOptionPane.showConfirmDialog(GenerateRoIPanel.this, "You have generated the RoI file of these selected users using these parameters.\nDo you want to do it again?", "Duplicate operation", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.NO_OPTION) {
							return;
						}
					}
					if(popupMessageDecision == 2) {
						int ans = JOptionPane.showConfirmDialog(GenerateRoIPanel.this, "You have generated the RoI file of these selected users using a different parameter setting.\nDo you want to do it using the current parameter setting?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.NO_OPTION) {
							return;
						}
					}
					if(popupMessageDecision == 3) {
						int ans = JOptionPane.showConfirmDialog(GenerateRoIPanel.this, "You have generated an RoI file whose users include these selected users.\nDo you want to continue generating their RoI file?", "Confirmation", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.NO_OPTION) {
							return;
						}
					}
					
					btnStart.setEnabled(false);
					allowExit.ifAllowExit = false;
					RoIGenerator worker = new RoIGenerator(aDataset, selectedList, deletionPercentage, lowerBound, upperBound, textArea, btnStart, btnPS, selectedPara);
					Thread t = new Thread(worker);
		    		t.setPriority(Thread.MAX_PRIORITY);
		    		t.start();
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "The three parameters must be positive integers.", "Number format error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}			
		});
	}
	
	private class RoIGenerator implements Runnable {
		private Dataset dataset;
		private List<String> list;
		private int deletionPercentage;
		private String lowerBound;
		private String upperBound;
		private JTextArea textArea;
		private JButton btnStart;
		private JButton btnPS;
		private String selectedPara;
		
		public RoIGenerator(Dataset aDataset, List<String> aList, int aDeletionPercentage, String aLowerBound, String anUpperBound, JTextArea aTextArea, JButton aButton, JButton anotherButton, String aPara) {
			dataset = aDataset;
			list = aList;
			deletionPercentage = aDeletionPercentage;
			lowerBound = aLowerBound;
			upperBound = anUpperBound;
			textArea = aTextArea;	
			btnStart = aButton;
			btnPS = anotherButton;
			selectedPara = aPara;
		}
		
		public void run() {
			StayPointClustering spc = new StayPointClustering(deletionPercentage, lowerBound, upperBound);
			/*try {*/
				spc.transFormat(dataset, list, textArea, selectedPara);
			/*} catch (Exception e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(GenerateRoIPanel.this, "The bounds of K you provided are not appropriate.\nTry again using different bounds.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				});
			}*/
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					btnStart.setEnabled(true);
					btnPS.setEnabled(true);
					allowExit.ifAllowExit = true;
				}
			});
		}
	}
}
