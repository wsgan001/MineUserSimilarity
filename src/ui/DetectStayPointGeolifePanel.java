package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import detectStayPoints.FormatConverterGeolife;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
public class DetectStayPointGeolifePanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JList<String> list;
	private Dataset selectedDataset;
	private Main.AllowExit allowExit;

	/**
	 * Create the panel.
	 */
	public DetectStayPointGeolifePanel(JList<String> aList, Dataset aDataset, final JButton btnRoI, Main.AllowExit anObject) {
		list = aList;
		selectedDataset = aDataset;
		allowExit = anObject;
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Parameters for detecting stay points of Geolife", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{93, 86, 0, 0};
		gbl_panel_1.rowHeights = new int[] {30, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblDistanceThreshold = new JLabel("Distance threshold:");
		GridBagConstraints gbc_lblDistanceThreshold = new GridBagConstraints();
		gbc_lblDistanceThreshold.anchor = GridBagConstraints.WEST;
		gbc_lblDistanceThreshold.insets = new Insets(0, 54, 0, 8);
		gbc_lblDistanceThreshold.gridx = 0;
		gbc_lblDistanceThreshold.gridy = 0;
		panel_1.add(lblDistanceThreshold, gbc_lblDistanceThreshold);
		
		textField = new JTextField();
		textField.setToolTipText("<html>\r\nIt is the maximum distance between the first point and the last point of a \r\n<br/>\r\ncontinuous set of GPS points for them to be a stay point. Typical values are \r\n<br/>\r\nintegers like 100, 150 and 200.\r\n</html>");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblM = new JLabel("m");
		GridBagConstraints gbc_lblM = new GridBagConstraints();
		gbc_lblM.gridx = 2;
		gbc_lblM.gridy = 0;
		panel_1.add(lblM, gbc_lblM);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{74, 86, 0, 0};
		gbl_panel_2.rowHeights = new int[] {23, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblTimeThreshold = new JLabel("Time threshold:");
		lblTimeThreshold.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblTimeThreshold = new GridBagConstraints();
		gbc_lblTimeThreshold.anchor = GridBagConstraints.WEST;
		gbc_lblTimeThreshold.insets = new Insets(0, 73, 0, 8);
		gbc_lblTimeThreshold.gridx = 0;
		gbc_lblTimeThreshold.gridy = 0;
		panel_2.add(lblTimeThreshold, gbc_lblTimeThreshold);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("<html>\r\nIt is the minimum time length that a continuous set of GPS points lasts to be a stay\r\n<br/>\r\n point. A typical value is 30.\r\n<html/>");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_2.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblS = new JLabel("s");
		GridBagConstraints gbc_lblS = new GridBagConstraints();
		gbc_lblS.gridx = 2;
		gbc_lblS.gridy = 0;
		panel_2.add(lblS, gbc_lblS);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel.add(panel_3);
		
		JLabel lblStayPointMerging = new JLabel("Stay point merging threshold: ");
		panel_3.add(lblStayPointMerging);
		
		textField_2 = new JTextField();
		textField_2.setToolTipText("<html>\r\nIt is the maximum distance for two preliminary stay points to be merged into one.\r\n<br/>\r\nA common practice is to make it take the same value as the distance threshold.\r\n<html/>");
		panel_3.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblM_1 = new JLabel("m");
		panel_3.add(lblM_1);
		
		JPanel panel_4 = new JPanel();
		add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0};
		gbl_panel_4.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(7, 7, 7, 7);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_4.add(scrollPane, gbc_scrollPane);
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JPanel panel_5 = new JPanel();
		add(panel_5, BorderLayout.SOUTH);
		
		final JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> selectedList = list.getSelectedValuesList();
				if(selectedList.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please first choose some users.", "Choose users", JOptionPane.INFORMATION_MESSAGE);
					return;
				}				
				
				try {
					double distanceThreshold = Double.parseDouble(textField.getText().trim());
					double timeThreshold = Double.parseDouble(textField_1.getText().trim());
					double mergingThreshold = Double.parseDouble(textField_2.getText().trim());
					
					if(distanceThreshold <= 0.0 || timeThreshold <= 0.0 || mergingThreshold <= 0.0) {
						JOptionPane.showMessageDialog(null, "The thresholds must be positive numbers.", "Number format error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					ArrayList<String> filteredList = new ArrayList<String>();
					File SPfolder = new File(selectedDataset.getOutputPath() + "/StayPoints");
					if(SPfolder.exists()) {
						String[] paraSettings = SPfolder.list();
						Arrays.sort(paraSettings);
						int index = Arrays.binarySearch(paraSettings, distanceThreshold + "_" + timeThreshold + "_" + mergingThreshold);
						if(index >= 0) {
							File sameParaSettingFolder = new File(SPfolder + "/" + paraSettings[index]);
							String[] usersHavingDetectedSP = sameParaSettingFolder.list();
							for(int i = 0; i < usersHavingDetectedSP.length; i++) {
								usersHavingDetectedSP[i] = usersHavingDetectedSP[i].substring(0, 3);
							}							
							
							Arrays.sort(usersHavingDetectedSP);
							for(String aSelectedUser : selectedList) {								
								if(Arrays.binarySearch(usersHavingDetectedSP, aSelectedUser) >= 0) {
									/*int ans = JOptionPane.showConfirmDialog(null, "The stay points of the user " + aSelectedUser + " you selected were already detected using the same parameters.\nDo you want to do it again?", "Confirmation", JOptionPane.YES_NO_OPTION);
									if(ans == JOptionPane.YES_OPTION) {
										filteredList.add(aSelectedUser);
									}*/
								} else {
									filteredList.add(aSelectedUser);
								}
							}
						} else {
							filteredList.addAll(selectedList);
						}
						/*String[] usersHavingDetectedSP = SPfolder.list();
						for(int i = 0; i < usersHavingDetectedSP.length; i++) {
							usersHavingDetectedSP[i] = usersHavingDetectedSP[i].substring(0, 3);
						}
						Arrays.sort(usersHavingDetectedSP, null);
						for(String aSelectedUser : selectedList) {
							if(Arrays.binarySearch(usersHavingDetectedSP, aSelectedUser, null) >= 0) {
								int ans = JOptionPane.showConfirmDialog(null, "The stay points of the user " + aSelectedUser + " you selected were already detected.\nDo you want to do it again?", "Confirmation", JOptionPane.YES_NO_OPTION);
								if(ans == JOptionPane.NO_OPTION) {
									return;
								}
							}
						}*/
					} else {
						filteredList.addAll(selectedList);
					}
					
					if(filteredList.size() != 0) {
						btnStart.setEnabled(false);
						allowExit.ifAllowExit = false;
						StayPointDetecterGeolife spd = new StayPointDetecterGeolife(selectedDataset, filteredList, distanceThreshold, timeThreshold, mergingThreshold, textArea, btnStart, btnRoI);
						Thread t = new Thread(spd);
						t.setPriority(Thread.MAX_PRIORITY);
						t.start();
		    		} else {
		    			JOptionPane.showMessageDialog(DetectStayPointGeolifePanel.this, "All the users you chose has already been detected using these parameters.\nThere is no need to do it again.", "Duplicate detection", JOptionPane.INFORMATION_MESSAGE);
						return;
		    		}
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "The thresholds must be positive numbers.", "Number format error", JOptionPane.ERROR_MESSAGE);
					return;
				}				
			}
		});
		panel_5.add(btnStart);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		panel_5.add(btnClear);	
		
		ToolTipManager.sharedInstance().setDismissDelay(15000);
	}
	
	private class StayPointDetecterGeolife implements Runnable {
		private Dataset dataset;
		private List<String> list;
		private double distanceThreshold;
		private double timeThreshold;
		private double mergingThreshold;
		private JTextArea textArea;
		private JButton btnStart;
		private JButton btnRoI;
		
		public StayPointDetecterGeolife(Dataset aDataset, List<String> aList, double aDistanceThreshold, double aTimeThreshold, double aMergingThreshold, JTextArea aTextArea, JButton aButton, JButton anotherButton) {
			dataset = aDataset;
			list = aList;
			distanceThreshold = aDistanceThreshold;
			timeThreshold = aTimeThreshold;
			mergingThreshold = aMergingThreshold;
			textArea = aTextArea;
			btnStart = aButton;
			btnRoI = anotherButton;
		}
		
		public void run() {
			FormatConverterGeolife fcGeo = new FormatConverterGeolife();
			fcGeo.processFilesOfAllUsers(dataset.getInputPath(), dataset.getOutputPath(), list, distanceThreshold, timeThreshold, mergingThreshold, textArea);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					btnStart.setEnabled(true);
					btnRoI.setEnabled(true);
					allowExit.ifAllowExit = true;
				}
			});			
		}
	}	
}
