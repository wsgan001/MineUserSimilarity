package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.JSplitPane;

import ui.Main.AllowExit;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ConstructUserProfilePanel extends JPanel {
	/*private JTree tree;*/
	private int buttonEnabled;
	private Dataset selectedDataset;
	private byte comboBoxBehaviour = 0; 
	private JPanel funcPanel;

	/**
	 * Create the panel.
	 */
	public ConstructUserProfilePanel(/*JTree aTree, */int anInteger, Dataset aDataset, final AllowExit allowExit) {
		/*tree = aTree;*/
		buttonEnabled = anInteger;
		selectedDataset = aDataset;
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{121, 0};
		gbl_panel.rowHeights = new int[]{23, 23, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton button = new JButton("Detect stay points");		
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 0;
		gbc_button.gridy = 1;
		panel.add(button, gbc_button);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(ConstructUserProfilePanel.class.getResource("/images/downward arrow.png")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 2;
		panel.add(label, gbc_label);
		
		final JButton button_1 = new JButton("Generate RoIs");		
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 0;
		gbc_button_1.gridy = 3;
		panel.add(button_1, gbc_button_1);
		
		JLabel label_1 = new JLabel();
		label_1.setIcon(new ImageIcon(ConstructUserProfilePanel.class.getResource("/images/downward arrow.png")));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 4;
		panel.add(label_1, gbc_label_1);
		
		final JButton button_2 = new JButton("<html><center>Extract frequent<br/> pattern sets</center></html>");		
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.gridx = 0;
		gbc_button_2.gridy = 5;
		panel.add(button_2, gbc_button_2);
		
		final JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		final JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		final JList<String> list = new JList<String>();		
		scrollPane.setViewportView(list);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		final JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panel_3.add(panel_5);
		
		JLabel lblStayPointPara = new JLabel("Stay point paras");
		panel_5.add(lblStayPointPara);
		
		final JComboBox<String> comboBox = new JComboBox<String>();		
		panel_3.add(comboBox);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel_3.add(panel_4);
		
		JLabel lblUserList = new JLabel(" User list:");
		panel_4.add(lblUserList);
		panel_2.add(panel_3, BorderLayout.NORTH);
		splitPane.setLeftComponent(panel_2);
		//splitPane.setDividerLocation(70);
		splitPane.setVisible(false);
		
		final JPopupMenu popup = new JPopupMenu();
		final JMenuItem mntmViewFilePopup = new JMenuItem("View file");
		popup.add(mntmViewFilePopup);
		JMenuItem mntmVisualizePopup = new JMenuItem("Visualize");
		popup.add(mntmVisualizePopup);
		
		mntmVisualizePopup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (funcPanel instanceof DetectStayPointGeolifePanel) {
					String dirPath = selectedDataset.getInputPath() + "/" + list.getSelectedValue() + "/Trajectory";
					String[] cmd = {"map/MapApp2.exe", "-gps", dirPath};
					ProcessBuilder pb = new ProcessBuilder(cmd);
					try {
						pb.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(ConstructUserProfilePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else/* if (funcPanel instanceof GenerateRoIPanel) */{
					if(selectedDataset.getType().equals("GPS")) {
						String selectedPara = (String)comboBox.getSelectedItem();
						String filePath = selectedDataset.getOutputPath() + "/StayPoints/" + selectedPara + "/" + list.getSelectedValue() + ".txt";
						String[] cmd = {"map/MapApp2.exe", "-staypoint", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(ConstructUserProfilePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String filePath = selectedDataset.getInputPath() + "/" + list.getSelectedValue() + ".txt";
						String[] cmd = {"map/MapApp2.exe", "-staypoint", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(ConstructUserProfilePanel.this, "An error occurred while starting the visualization tool.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}			
		});
		
		mntmViewFilePopup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*if(funcPanel instanceof GenerateRoIPanel) {*/
					if(selectedDataset.getType().equals("GPS")) {
						String selectedPara = (String)comboBox.getSelectedItem();
						String filePath = selectedDataset.getOutputPath() + "/StayPoints/" + selectedPara + "/" + list.getSelectedValue() + ".txt";
						String[] cmd = {"write.exe", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(ConstructUserProfilePanel.this, "An error occurred while opening the stay point file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String filePath = selectedDataset.getInputPath() + "/" + list.getSelectedValue() + ".txt";
						String[] cmd = {"write.exe", filePath};
						ProcessBuilder pb = new ProcessBuilder(cmd);
						try {
							pb.start();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(ConstructUserProfilePanel.this, "An error occurred while opening the stay point file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				/*}*/				
			}			
		});
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxBehaviour == 1) {
					String selectedPara = (String)comboBox.getSelectedItem();
					if(selectedPara != null) {
						String[] SPFilesUnderThePara = new File(selectedDataset.getOutputPath() + "/StayPoints/" + selectedPara).list();
						for(int i = 0; i < SPFilesUnderThePara.length; i++) {
							SPFilesUnderThePara[i] = SPFilesUnderThePara[i].substring(0, 3);
						}
						list.setListData(SPFilesUnderThePara);
					}
				}
				if(comboBoxBehaviour == 2) {
					String selectedPara = (String)comboBox.getSelectedItem();
					if(selectedPara != null) {
						String[] RoIFilesUnderThePara = new File(selectedDataset.getOutputPath() + "/RoIs/" + selectedPara).list();
						HashSet<String> usersInRoIFilesUnderThePara = new HashSet<String>();
						for(String anRoIFile : RoIFilesUnderThePara) {
							String[] fields = anRoIFile.split("-");
							String[] usersInTheRoIFile = fields[0].split("_");
							for(String aUser : usersInTheRoIFile) {
								usersInRoIFilesUnderThePara.add(aUser);
							}
						}
						list.setListData(usersInRoIFilesUnderThePara.toArray(new String[0]));
					}
				}
			}
		});
		
		if(selectedDataset.getType().equals("GPS")) {
			if(buttonEnabled == 1) {
				button_1.setEnabled(false);
				button_2.setEnabled(false);	
			}

			if(buttonEnabled == 2) {
				button_2.setEnabled(false);
			}
		} else {
			button.setEnabled(false);
			if(buttonEnabled == 2) {
				button_2.setEnabled(false);
			}
			panel_5.setVisible(false);
			comboBox.setVisible(false);
		}
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] files = new File(selectedDataset.getInputPath()).list();
				list.setListData(files);
				panel_5.setVisible(false);
				comboBox.setVisible(false);
				splitPane.setVisible(true);
				if(selectedDataset.getType().equals("GPS")) {
					funcPanel = new DetectStayPointGeolifePanel(list, selectedDataset, button_1, allowExit);
					splitPane.setRightComponent(funcPanel);
					revalidate();
				}
			}
		});
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedDataset.getType().equals("GPS")) {
					comboBoxBehaviour = 1;
					comboBox.removeAllItems();
					String[] paraSettings = new File(selectedDataset.getOutputPath() + "/StayPoints").list();
					for(String aParaSetting : paraSettings) {
						comboBox.addItem(aParaSetting);
					}
					comboBox.setVisible(true);					
				} else {
					String[] files = new File(selectedDataset.getInputPath()).list();
					for(int i = 0; i < files.length; i++) {
						files[i] = files[i].substring(0, 3);
					}
					list.setListData(files);
				}
				splitPane.setVisible(true);
				funcPanel = new GenerateRoIPanel(list, selectedDataset, button_2, comboBox, allowExit);
				splitPane.setRightComponent(funcPanel);
				revalidate();
			}
		});
		
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedDataset.getType().equals("GPS")) {
					comboBoxBehaviour = 2;
					comboBox.removeAllItems();
					String[] paraSettings = new File(selectedDataset.getOutputPath() + "/RoIs").list();
					for(String aParaSetting : paraSettings) {
						comboBox.addItem(aParaSetting);
					}
					comboBox.setVisible(true);					
				} else {
					String[] RoIFiles = new File(selectedDataset.getOutputPath() + "/RoIs").list();
					HashSet<String> usersInRoIFiles = new HashSet<String>();
					for(String anRoIFile : RoIFiles) {
						String[] fields = anRoIFile.split("-");
						String[] usersInTheRoIFile = fields[0].split("_");
						for(String aUser : usersInTheRoIFile) {
							usersInRoIFiles.add(aUser);
						}
					}
					list.setListData(usersInRoIFiles.toArray(new String[0]));
				}
				
				splitPane.setVisible(true);
				
				funcPanel = new ExtractPSPanel(list, selectedDataset, comboBox, allowExit);
				splitPane.setRightComponent(funcPanel);
				revalidate();
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				if(SwingUtilities.isRightMouseButton(e)) {
					int index = list.locationToIndex(e.getPoint());
					Rectangle bound = list.getCellBounds(index, index);
					if(bound.contains(e.getPoint())) {
						list.setSelectedIndex(index);
						if (!(funcPanel instanceof DetectStayPointGeolifePanel)) {
							mntmViewFilePopup.setVisible(true);
							popup.show(list, e.getX(), e.getY());
						} else {
							mntmViewFilePopup.setVisible(false);
							popup.show(list, e.getX(), e.getY());
						}
					}
				}				
			}
		});
	}	
}
