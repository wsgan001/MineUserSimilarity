package ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.apache.commons.io.FileUtils;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class VisualizePanel extends JPanel {
	private byte listType;

	/**
	 * Create the panel.
	 */
	public VisualizePanel(final Dataset dataset) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		JButton btnStart = new JButton("Start");		
		panel.add(btnStart);
		
		/*JButton btnClear = new JButton("Clear");		
		panel.add(btnClear);*/
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblGpsUserList = new JLabel("GPS user list:");
		panel_3.add(lblGpsUserList);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		final JList<String> list = new JList<String>();		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		/*JPanel panel_4 = new JPanel();
		panel_4.setVisible(false);
		panel_2.add(panel_4, BorderLayout.NORTH);*/
		
		/*JLabel lblParametersForDetecting = new JLabel("Parameters for detecting stay points:");
		panel_4.add(lblParametersForDetecting);
		
		final JComboBox<String> comboBox = new JComboBox<String>();		
		panel_4.add(comboBox);*/
		
		JSplitPane splitPane_1 = new JSplitPane();
		panel_2.add(splitPane_1, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		splitPane_1.setLeftComponent(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);
		
		final JLabel lblStayPointUser = new JLabel("Stay point file list:");
		panel_6.add(lblStayPointUser);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
		final JList<String> list_1 = new JList<String>();
		if (dataset.getType().equals("GPS")) {
			list_1.setToolTipText("<html>\r\nEach item is the parameter settings used when forming the stay point file it represents. \r\n<br/>\r\nIts format is \"distance threshold_time threshold_stay point merging threshold\".\r\n</html>");
		}
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(list_1);
		
		JPanel panel_7 = new JPanel();
		splitPane_1.setRightComponent(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8, BorderLayout.NORTH);
		
		JLabel lblRoiFileList = new JLabel("RoI file list:");
		panel_8.add(lblRoiFileList);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_7.add(scrollPane_2, BorderLayout.CENTER);
		
		final JList<String> list_2 = new JList<String>();
		list_2.setToolTipText("<html>\r\nThe first part after splitting each item with \"-\" is a list of all users separated by \"_\".\r\n<br/>\r\nThe second part is the parameter settings used when detecting stay points involved in forming the RoI file.\r\n<br/>\r\nThe format of the second part  is \"distance threshold_time threshold_stay point merging threshold\".\r\n</html>");
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(list_2);
		
		if (dataset.getType().equals("GPS")) {
			String[] users = new File(dataset.getInputPath()).list();
			list.setListData(users);
			
			/*comboBox.addItem("none");
			String[] SPParas = new File(dataset.getOutputPath() + "/StayPoints").list();
			for (String anSPPara : SPParas) {
				comboBox.addItem(anSPPara);
			}*/
		} else {
			panel_1.setVisible(false);
			/*panel_4.setVisible(false);*/
			
			String[] users = new File(dataset.getInputPath()).list();
			for(int i = 0; i < users.length; i++) {
				users[i] = users[i].substring(0, 3);
			}
			list_1.setListData(users);
			
			String[] RoIFiles = new File(dataset.getOutputPath() + "/RoIs").list();
			for (int i = 0; i < RoIFiles.length; i++) {
				RoIFiles[i] = RoIFiles[i].substring(0, RoIFiles[i].length() - 4);
			}
			list_2.setListData(RoIFiles);
		}
		
		JPopupMenu popup = new JPopupMenu();
		JMenuItem mntmViewFilePopup = new JMenuItem("View file");
		popup.add(mntmViewFilePopup);
		JMenuItem mntmVisualizePopup = new JMenuItem("Visualize");
		popup.add(mntmVisualizePopup);
		
		ListMouseListener gpsListML = new ListMouseListener(list, (byte)0, mntmViewFilePopup, popup);
		
		JPanel panel_9 = new JPanel();
		panel_1.add(panel_9, BorderLayout.SOUTH);
		
		JButton btnClearSelection = new JButton("Clear selection");		
		panel_9.add(btnClearSelection);
		ListMouseListener spListML = new ListMouseListener(list_1, (byte)1, mntmViewFilePopup, popup);
		
		JPanel panel_10 = new JPanel();
		panel_5.add(panel_10, BorderLayout.SOUTH);
		
		JButton btnClearSelection_1 = new JButton("Clear selection");		
		panel_10.add(btnClearSelection_1);
		ListMouseListener roiListML = new ListMouseListener(list_2, (byte)2, mntmViewFilePopup, popup);
		
		JPanel panel_11 = new JPanel();
		panel_7.add(panel_11, BorderLayout.SOUTH);
		
		JButton btnClearSelection_2 = new JButton("Clear selection");		
		panel_11.add(btnClearSelection_2);
		
		final JCheckBox chckbxOneUser = new JCheckBox("One user");
		panel_11.add(chckbxOneUser);
		list.addMouseListener(gpsListML);
		list_1.addMouseListener(spListML);
		list_2.addMouseListener(roiListML);
		
		splitPane.setDividerLocation(120);
		splitPane_1.setDividerLocation(120);
		
		ToolTipManager.sharedInstance().setDismissDelay(15000);
		
		btnClearSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.clearSelection();
				
				/*comboBox.removeAllItems();
				comboBox.addItem("none");
				String[] SPParas = new File(dataset.getOutputPath() + "/StayPoints").list();
				for (String anSPPara : SPParas) {
					comboBox.addItem(anSPPara);
				}*/
			}
		});
		
		btnClearSelection_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list_1.clearSelection();
			}
		});
		
		btnClearSelection_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list_2.clearSelection();
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				/*comboBox.removeAllItems();
				comboBox.addItem("none");*/
				String user = list.getSelectedValue();
				if (user != null) {
					ArrayList<String> spParas = new ArrayList<String>();
					File spDir = new File(dataset.getOutputPath() + "\\StayPoints");
					if (spDir.exists()) {
					File[] SPDirs = spDir.listFiles();
					for (File anSPDir : SPDirs) {
						File supposedSPFile = new File(anSPDir + "\\" + user + ".txt");
						try {
							if (FileUtils.directoryContains(anSPDir, supposedSPFile)) {
								lblStayPointUser.setText("SP file list of user " + user);
								spParas.add(anSPDir.getName());
								/*comboBox.addItem(anSPDir.getName());*/
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					list_1.setListData(spParas.toArray(new String[0]));
					}
				} /*else {
					list_1.setListData(new String[0]);
					list_2.setListData(new String[0]);
				}*/
			}
		});
		
		list_1.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String user = list/*_1*/.getSelectedValue();
				if (user != null) {
					/*if (list.getSelectedValue() == null) {*/
						String spPara = list_1.getSelectedValue();
						if (spPara != null) {
							ArrayList<String> usableRoIFiles = new ArrayList<String>();
							File selectedParaRoIDir = new File(dataset.getOutputPath() + "/RoIs/" + spPara);
							if(selectedParaRoIDir.exists()) {
								String[] RoIFiles = selectedParaRoIDir.list();
								for (String anRoIFile : RoIFiles) {
									String[] fields = anRoIFile.split("-");
									String[] users = fields[0].split("_");
									HashSet<String> usersAnRoI = new HashSet<String>();
									for(String aUser : users) {
										usersAnRoI.add(aUser);
									}

									if (usersAnRoI.contains(user)) {
										usableRoIFiles.add(anRoIFile.substring(0, anRoIFile.length() - 4));
									}
								}
								list_2.setListData(usableRoIFiles.toArray(new String[0]));
							} else {
								list_2.setListData(new String[0]);
							}
						} else {
							list_2.setListData(new String[0]);
						} 
					/*}*/
				} else {
					String spUser = list_1.getSelectedValue();
					if (spUser != null) {
						ArrayList<String> usableRoIFiles = new ArrayList<String>();
						File selectedParaRoIDir = new File(dataset.getOutputPath() + "/RoIs");
						if(selectedParaRoIDir.exists()) {
							String[] RoIFiles = selectedParaRoIDir.list();
							for (String anRoIFile : RoIFiles) {
								String[] fields = anRoIFile.split("-");
								String[] users = fields[0].split("_");
								HashSet<String> usersAnRoI = new HashSet<String>();
								for(String aUser : users) {
									usersAnRoI.add(aUser);
								}

								if (usersAnRoI.contains(spUser)) {
									usableRoIFiles.add(anRoIFile.substring(0, anRoIFile.length() - 4));
								}
							}
							list_2.setListData(usableRoIFiles.toArray(new String[0]));
						} else {
							list_2.setListData(new String[0]);
						}
					} else {
						list_2.setListData(new String[0]);
					}
				}
			}
		});
		
		mntmViewFilePopup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(listType == 1) {
					if(dataset.getType().equals("GPS")) {
						/*String selectedPara = (String)comboBox.getSelectedItem();*/
						String filePath = dataset.getOutputPath() + "/StayPoints/" + list_1.getSelectedValue() + "/" + list.getSelectedValue() + ".txt";
						String[] cmd = {"write.exe", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while opening the stay point file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String filePath = dataset.getInputPath() + "/" + list_1.getSelectedValue() + ".txt";
						String[] cmd = {"write.exe", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while opening the stay point file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					if(dataset.getType().equals("GPS")) {
						/*String selectedPara = (String)comboBox.getSelectedItem();*/
						String filePath = dataset.getOutputPath() + "/RoIs/" + list_1.getSelectedValue() + "/" + list_2.getSelectedValue() + ".txt";
						String[] cmd = {"write.exe", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while opening the RoI file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String filePath = dataset.getOutputPath() + "/RoIs/" + list_2.getSelectedValue() + ".txt";
						String[] cmd = {"write.exe", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while opening the RoI file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}				
			}			
		});
		
		mntmVisualizePopup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listType == 0) {
					String dirPath = dataset.getInputPath() + "/" + list.getSelectedValue() + "/Trajectory";
					String[] cmd = {"map/MapApp2.exe", "-gps", dirPath};
					ProcessBuilder pb = new ProcessBuilder(cmd);
					try {
						pb.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (listType == 1) {
					if(dataset.getType().equals("GPS")) {
						/*String selectedPara = (String)comboBox.getSelectedItem();*/
						String filePath = dataset.getOutputPath() + "/StayPoints/"  + list_1.getSelectedValue() + "/" + list.getSelectedValue() + ".txt";
						String[] cmd = {"map/MapApp2.exe", "-staypoint", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String filePath = dataset.getInputPath() + "/" + list_1.getSelectedValue() + ".txt";
						String[] cmd = {"map/MapApp2.exe", "-staypoint", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					if(dataset.getType().equals("GPS")) {
						/*String selectedPara = (String)comboBox.getSelectedItem();*/
						String filePath = dataset.getOutputPath() + "/RoIs/" + list_1.getSelectedValue() + "/" + list_2.getSelectedValue() + ".txt";
						String[] cmd = {"map/MapApp2.exe", "-roi", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String filePath = dataset.getOutputPath() + "/RoIs/" + list_2.getSelectedValue() + ".txt";
						String[] cmd = {"map/MapApp2.exe", "-roi", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}			
		});
		
		/*comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedPara = (String)comboBox.getSelectedItem();
				if((selectedPara != null) && (!selectedPara.equals("none"))) {
					String gpsUser = list.getSelectedValue();
					if (gpsUser != null) {
						list_1.setListData(new String[]{gpsUser});
						
						ArrayList<String> usableRoIFiles = new ArrayList<String>();
						File selectedParaRoIDir = new File(dataset.getOutputPath() + "/RoIs/" + selectedPara);
						if(selectedParaRoIDir.exists()) {
							String[] RoIFiles = selectedParaRoIDir.list();
							for (String anRoIFile : RoIFiles) {
								String[] fields = anRoIFile.split("-");
								String[] users = fields[0].split("_");
								HashSet<String> usersAnRoI = new HashSet<String>();
								for(String aUser : users) {
									usersAnRoI.add(aUser);
								}
								
								if (usersAnRoI.contains(gpsUser)) {
									usableRoIFiles.add(anRoIFile.substring(0, anRoIFile.length() - 4));
								}
							}
							list_2.setListData(usableRoIFiles.toArray(new String[0]));
						}
					} else {
						String[] SPFiles = new File(dataset.getOutputPath() + "/StayPoints/" + selectedPara).list();
						for(int i = 0; i < SPFiles.length; i++) {
							SPFiles[i] = SPFiles[i].substring(0, 3);
						}
						list_1.setListData(SPFiles);
						
						File selectedParaRoIDir = new File(dataset.getOutputPath() + "/RoIs/" + selectedPara);
						if(selectedParaRoIDir.exists()) {
							String[] RoIFiles = selectedParaRoIDir.list();
							for(int i = 0; i < RoIFiles.length; i++) {
								RoIFiles[i] = RoIFiles[i].substring(0, RoIFiles[i].length() - 4);
							}
							list_2.setListData(RoIFiles);
						} else {
							list_2.setModel(new DefaultListModel<String>());
						}
					}					
				} else {
					list_1.setModel(new DefaultListModel<String>());
					list_2.setModel(new DefaultListModel<String>());
				}
			}
		});*/
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dataset.getType().equals("GPS")) {
					// String gps = list.getSelectedValue();
					String s = lblStayPointUser.getText();
					String[] fields = s.split(" ");
					String gps = fields[fields.length - 1];
					String sp = list_1.getSelectedValue();
					String roi = list_2.getSelectedValue();
					
					if (gps == null && sp == null && roi == null) {
						JOptionPane.showMessageDialog(VisualizePanel.this, "You haven't selected anything to be visualized.", "Information", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					ArrayList<String> cmdBuilder = new ArrayList<String>();
					cmdBuilder.add("map/MapApp2.exe");
					/*if (chckbxOneUser.isSelected()) {
						cmdBuilder.add("-oneuser");
						cmdBuilder.add(dataset.getOutputPath() + "/PatternSets/" + sp + "/" + roi + ".txt/0.1_7200.0_0.02_AllDays/" + gps + "MiSTA.output");
					} else {
						cmdBuilder.add("-all");
					}*/
					
					if (gps != null) {
						cmdBuilder.add("-gps");
						cmdBuilder.add(dataset.getInputPath() + "/" + gps + "/Trajectory");
					}
					if (sp != null) {
						cmdBuilder.add("-staypoint");
						/*String selectedPara = (String)comboBox.getSelectedItem();*/
						cmdBuilder.add(dataset.getOutputPath() + "/StayPoints/" /*selectedPara*/ + sp + "/" + gps + ".txt");
					}
					if (roi != null) {
						cmdBuilder.add("-roi");
						/*String selectedPara = (String)comboBox.getSelectedItem();*/
						cmdBuilder.add(dataset.getOutputPath() + "/RoIs/" + sp + "/" + roi + ".txt");
					}
					
					String[] cmd = cmdBuilder.toArray(new String[0]);
					ProcessBuilder pb = new ProcessBuilder(cmd);
					try {
						pb.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					String sp = list_1.getSelectedValue();
					String roi = list_2.getSelectedValue();
					
					if (sp == null && roi == null) {
						JOptionPane.showMessageDialog(VisualizePanel.this, "You haven't selected anything to be visualized.", "Information", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					ArrayList<String> cmdBuilder = new ArrayList<String>();
					cmdBuilder.add("map/MapApp2.exe");
					/*if (chckbxOneUser.isSelected()) {
						cmdBuilder.add("-oneuser");
						cmdBuilder.add(dataset.getOutputPath() + "/PatternSets/" + roi + ".txt/0.1_7200.0_0.02_AllDays/" + sp + "MiSTA.output");
					} else {
						cmdBuilder.add("-all");
					}*/
					
					if (sp != null) {
						cmdBuilder.add("-staypoint");
						cmdBuilder.add(dataset.getInputPath() + "/" + sp + ".txt");
					}
					if (roi != null) {
						cmdBuilder.add("-roi");
						cmdBuilder.add(dataset.getOutputPath() + "/RoIs/" + roi + ".txt");
					}
					
					String[] cmd = cmdBuilder.toArray(new String[0]);
					ProcessBuilder pb = new ProcessBuilder(cmd);
					try {
						pb.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(VisualizePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		/*btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.getSelectionModel().clearSelection();
				comboBox.setSelectedIndex(0);
			}
		});*/
	}
	
	private class ListMouseListener extends MouseAdapter {
		private JList<String> list;
		private byte type;
		private JMenuItem mntmViewFilePopup;
		private JPopupMenu popup;
		
		public ListMouseListener(JList<String> aList, byte aType, JMenuItem aMenuItem, JPopupMenu aPopupMenu) {
			list = aList;
			type = aType;
			mntmViewFilePopup = aMenuItem;
			popup = aPopupMenu;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {				
			if(SwingUtilities.isRightMouseButton(e)) {
				int index = list.locationToIndex(e.getPoint());
				Rectangle bound = list.getCellBounds(index, index);
				if(bound.contains(e.getPoint())) {
					list.setSelectedIndex(index);
					if(type == 0) {
						mntmViewFilePopup.setVisible(false);
					} else {
						mntmViewFilePopup.setVisible(true);
					}
					popup.show(list, e.getX(), e.getY());
					listType = type;
				}
			}				
		}
	}
}
