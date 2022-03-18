package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.distribution.NormalDistribution;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class ManageDistFilesPanel extends JPanel {
	private JPanel panel;
	private Dataset dataset;
	private HashMap<String, String> distStats;
	private JTextField textField;
	private JTextField textField_1;
	//private 
	/**
	 * Create the panel.
	 */
	public ManageDistFilesPanel(Dataset aDataset) {
		dataset = aDataset;
		distStats = new HashMap<String, String>();
		DistFileReader reader = new DistFileReader();
		reader.execute();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		
		final JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblTheNumberOf = new JLabel("The number of semantic tags: ");
		GridBagConstraints gbc_lblTheNumberOf = new GridBagConstraints();
		gbc_lblTheNumberOf.insets = new Insets(10, 10, 5, 5);
		gbc_lblTheNumberOf.gridx = 1;
		gbc_lblTheNumberOf.gridy = 3;
		panel_3.add(lblTheNumberOf, gbc_lblTheNumberOf);
		
		final JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblTheNumberOf_1 = new JLabel("The number of RoIs: ");
		GridBagConstraints gbc_lblTheNumberOf_1 = new GridBagConstraints();
		gbc_lblTheNumberOf_1.anchor = GridBagConstraints.EAST;
		gbc_lblTheNumberOf_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTheNumberOf_1.gridx = 1;
		gbc_lblTheNumberOf_1.gridy = 4;
		panel_3.add(lblTheNumberOf_1, gbc_lblTheNumberOf_1);
		
		final JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 4;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Two ways of adding a new semantic file", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_7.add(panel_2);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add an existing one", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnAdd = new JButton("Add");		
		panel_2.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");		
		panel_2.add(btnDelete);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Generate one using an RoI file", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		
		JPanel panel_9 = new JPanel();
		panel_9.setVisible(false);
		panel_8.add(panel_9);
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_9.rowHeights = new int[] {25, 0};
		gbl_panel_9.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_9.rowWeights = new double[]{0.0, 1.0};
		panel_9.setLayout(gbl_panel_9);
		
		JLabel lblRoiFile = new JLabel("RoI file:");
		GridBagConstraints gbc_lblRoiFile = new GridBagConstraints();
		gbc_lblRoiFile.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblRoiFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoiFile.gridx = 0;
		gbc_lblRoiFile.gridy = 0;
		panel_9.add(lblRoiFile, gbc_lblRoiFile);
		
		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.SOUTH;
		gbc_textField.weightx = 1.0;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_9.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnSelect = new JButton("Select");		
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.anchor = GridBagConstraints.SOUTH;
		gbc_btnSelect.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelect.gridx = 2;
		gbc_btnSelect.gridy = 0;
		panel_9.add(btnSelect, gbc_btnSelect);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new TitledBorder(null, "Select an RoI file of the dataset " + dataset.getName(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.add(panel_14);
		GridBagLayout gbl_panel_14 = new GridBagLayout();
		gbl_panel_14.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_14.rowHeights = new int[] {25, 0};
		gbl_panel_14.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_14.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_14.setLayout(gbl_panel_14);
		
		JLabel lblStayPointPara = new JLabel("Stay point para:");
		GridBagConstraints gbc_lblStayPointPara = new GridBagConstraints();
		gbc_lblStayPointPara.anchor = GridBagConstraints.EAST;
		gbc_lblStayPointPara.insets = new Insets(0, 0, 0, 5);
		gbc_lblStayPointPara.gridx = 0;
		gbc_lblStayPointPara.gridy = 0;
		panel_14.add(lblStayPointPara, gbc_lblStayPointPara);
		
		final WideJComboBox<String> comboBox_3 = new WideJComboBox<String>();		
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 1;
		gbc_comboBox_3.gridy = 0;
		panel_14.add(comboBox_3, gbc_comboBox_3);
		
		JLabel lblRoiFiles = new JLabel("RoI files:");
		GridBagConstraints gbc_lblRoiFiles = new GridBagConstraints();
		gbc_lblRoiFiles.insets = new Insets(0, 0, 0, 5);
		gbc_lblRoiFiles.anchor = GridBagConstraints.EAST;
		gbc_lblRoiFiles.gridx = 2;
		gbc_lblRoiFiles.gridy = 0;
		panel_14.add(lblRoiFiles, gbc_lblRoiFiles);
		
		final WideJComboBox<String> comboBox_2 = new WideJComboBox<String>();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 3;
		gbc_comboBox_2.gridy = 0;
		panel_14.add(comboBox_2, gbc_comboBox_2);
		
		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[] {123, 55, 0};
		gbl_panel_11.rowHeights = new int[] {25, 0};
		gbl_panel_11.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_11.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_11.setLayout(gbl_panel_11);
		
		JLabel lblNumberOfSemantic = new JLabel("Number of semantic tags:");
		GridBagConstraints gbc_lblNumberOfSemantic = new GridBagConstraints();
		gbc_lblNumberOfSemantic.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfSemantic.insets = new Insets(0, 5, 0, 5);
		gbc_lblNumberOfSemantic.gridx = 0;
		gbc_lblNumberOfSemantic.gridy = 0;
		panel_11.add(lblNumberOfSemantic, gbc_lblNumberOfSemantic);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_11.add(textField_1, gbc_textField_1);
		textField_1.setColumns(8);
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);
		
		JButton btnGenerate = new JButton("Generate");		
		panel_10.add(btnGenerate);
		
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem mntmViewFilePopup = new JMenuItem("View file");
		popup.add(mntmViewFilePopup);						
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);		
		
		final JList<String> list = new JList<String>();		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setVgap(0);
		panel_4.add(panel_6);
		
		JLabel lblDataset = new JLabel("Dataset " + dataset.getName());
		panel_6.add(lblDataset);
		
		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_12.getLayout();
		flowLayout_3.setVgap(0);
		panel_4.add(panel_12);
		
		JLabel lblStayPointParameter = new JLabel("Stay point parameter:");
		panel_12.add(lblStayPointParameter);
		
		final WideJComboBox<String> comboBox = new WideJComboBox<String>();		
		panel_4.add(comboBox);
		
		JPanel panel_13 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_13.getLayout();
		flowLayout_4.setVgap(0);
		panel_4.add(panel_13);
		
		JLabel lblRoiFile_1 = new JLabel("RoI file:");
		panel_13.add(lblRoiFile_1);
		
		final WideJComboBox<String> comboBox_1 = new WideJComboBox<String>();		
		panel_4.add(comboBox_1);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setVgap(0);
		panel_4.add(panel_5);
		
		JLabel lblDistributionFileList = new JLabel("Semantic file list:");
		panel_5.add(lblDistributionFileList);
		lblDistributionFileList.setHorizontalAlignment(SwingConstants.CENTER);
		
		splitPane.setLeftComponent(panel);
		splitPane.setRightComponent(panel_1);
		splitPane.setDividerLocation(150);
		
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String RoIFilename = (String) comboBox_1.getSelectedItem();
				if (dataset.getType().equals("GPS")) {
					String SPPara = (String) comboBox.getSelectedItem();					
					if (RoIFilename != null) {
						String[] DistFiles = new File(dataset.getOutputPath() + "/Dist/" + SPPara + "/" + RoIFilename).list();
						list.setListData(DistFiles);
					}
				} else {
					if (RoIFilename != null) {
						String[] DistFiles = new File(dataset.getOutputPath() + "/Dist/" + RoIFilename).list();
						list.setListData(DistFiles);
					}
				}
			}
		});		
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String SPPara = (String)comboBox.getSelectedItem();
				if (SPPara != null) {
					String[] RoIFiles = new File(dataset.getOutputPath() + "/Dist/" + SPPara).list();
					comboBox_1.removeAllItems();
					for (String anRoIFile : RoIFiles) {												
						comboBox_1.addItem(anRoIFile);						
					}
					comboBox_1.setWide(true);					
				}
			}
		});
		
		comboBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String SPPara = (String)comboBox_3.getSelectedItem();
				if (SPPara != null) {
					String[] RoIFiles = new File(dataset.getOutputPath() + "/RoIs/" + SPPara).list();
					comboBox_2.removeAllItems();
					for (String anRoIFile : RoIFiles) {						
						comboBox_2.addItem(anRoIFile);
					}
					comboBox_2.setWide(true);
				}
			}
		});	
		
		if (dataset.getType().equals("GPS")) {
			fillComboBox(comboBox);
		} else {
			panel_12.setVisible(false);
			comboBox.setVisible(false);
			fillComboBox(comboBox_1);
		}
		
		String[] FilesInRoIDir = new File(dataset.getOutputPath() + "/RoIs").list();
		if (dataset.getType().equals("GPS")) {
			for (String anSPParaInRoIDir : FilesInRoIDir) {
				comboBox_3.addItem(anSPParaInRoIDir);
			}
			comboBox_3.setWide(true);
		} else {
			lblStayPointPara.setVisible(false);
			comboBox_3.setVisible(false);
			for (String anRoIFile : FilesInRoIDir) {						
				comboBox_2.addItem(anRoIFile);
			}
			comboBox_2.setWide(true);
		}
		
		comboBox.setPrototypeDisplayValue("MMMMMMMMMMMM");
		comboBox_1.setPrototypeDisplayValue("MMMMMMMMMMMM");
		comboBox_2.setPrototypeDisplayValue("MMMMMMMMMMMM");	
		comboBox_3.setPrototypeDisplayValue("MMMMMMMMMMMM");
		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selectedItem = list.getSelectedValue();
				if(selectedItem != null) {
					String stat = null;
					String RoIFilename = (String) comboBox_1.getSelectedItem();
					if (dataset.getType().equals("GPS")) {
						String SPPara = (String) comboBox.getSelectedItem();					
						stat = distStats.get(dataset.getOutputPath() + "\\Dist\\" + SPPara + "\\" + RoIFilename + "\\" + selectedItem);
					} else {
						stat = distStats.get(dataset.getOutputPath() + "\\Dist\\" + RoIFilename + "\\" + selectedItem);
					}
					String[] fields = stat.split(" ");
					lblNewLabel.setText(fields[0]);
					lblNewLabel_1.setText(fields[1]);
					panel_3.setVisible(true);
				}
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
						popup.show(list, e.getX(), e.getY());
					}
				}
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fc = new JFileChooser();
					int ans = fc.showOpenDialog(ManageDistFilesPanel.this);
					if(ans == JFileChooser.APPROVE_OPTION) {
						textField.setText(fc.getSelectedFile().getAbsolutePath());
					} 
				} catch(HeadlessException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*String pathname = textField.getText();
				if(pathname.isEmpty()) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Please first select an RoI file.", "Select an RoI file", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				File file = new File(pathname);
				if(!file.exists()) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "The RoI file you chose does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}*/
				String RoIFile = (String) comboBox_2.getSelectedItem();
				if (RoIFile == null) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Please first select an RoI file.", "Select an RoI file", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				int tags;
				try {
					tags = Integer.parseInt(textField_1.getText().trim());
					if(tags <= 0 || tags > 50) {
						JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "The number of semantic tags you entered is not between 1 and 50.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "The number of semantic tags you entered is not an integer.", "Number format error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				DistFileGenerator worker = new DistFileGenerator((String) comboBox_3.getSelectedItem(), RoIFile, tags, comboBox, comboBox_1);
				worker.execute();
			}
		});
		
		mntmViewFilePopup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String RoIFilename = (String) comboBox_1.getSelectedItem();
				String filePath = null;
				if (dataset.getType().equals("GPS")) {
					String SPPara = (String) comboBox.getSelectedItem();				
					filePath = dataset.getOutputPath() + "/Dist/" + SPPara + "/" + RoIFilename + "/" + list.getSelectedValue();
				} else {
					filePath = dataset.getOutputPath() + "/Dist/" + RoIFilename + "/" + list.getSelectedValue();
				}
				String[] cmd = {"write.exe", filePath};
				ProcessBuilder pb = new ProcessBuilder(cmd);
				try {
					pb.start();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred while opening the distribution file.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
		});
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String SPPara = null;
				if (dataset.getType().equals("GPS")) {
					SPPara = (String) comboBox.getSelectedItem();
					if (SPPara == null) {
						JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Please first select a stay point parameter in the combo box.", "Info", JOptionPane.INFORMATION_MESSAGE);	
						return;
					}
				}
				String RoIFilename = (String) comboBox_1.getSelectedItem();
				if (RoIFilename == null) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Please first select an RoI file in the combo box.", "Info", JOptionPane.INFORMATION_MESSAGE);	
					return;
				}
				
				JFileChooser parseDir = new JFileChooser();
				File selectedFile = null;
				try {
					int returnValue = parseDir.showOpenDialog(ManageDistFilesPanel.this);		
					if(returnValue == JFileChooser.APPROVE_OPTION) {
						selectedFile = parseDir.getSelectedFile();
					} 
					else {
						return;
					}		
				}
				catch(HeadlessException ex) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred when opening the file choosing dialog.", "Error", JOptionPane.ERROR_MESSAGE);	
					return;
				}
				
				try {
					if (dataset.getType().equals("GPS")) {
						File dir = new File(dataset.getOutputPath() + "/Dist/" + SPPara + "/" + RoIFilename);
						if (FileUtils.directoryContains(dir, selectedFile)) {
							JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "The distribution file you chose has already been added.", "Info", JOptionPane.INFORMATION_MESSAGE);	
							return;
						}

						int ans = JOptionPane.showConfirmDialog(ManageDistFilesPanel.this, "Do you want to delete the original file?", "Confirmation", JOptionPane.YES_NO_OPTION);					
						DistFileAdder worker = new DistFileAdder(selectedFile, SPPara + "/" + RoIFilename, ans, comboBox_1);
						worker.execute();
					} else {
						File dir = new File(dataset.getOutputPath() + "/Dist/" + RoIFilename);
						if (FileUtils.directoryContains(dir, selectedFile)) {
							JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "The distribution file you chose has already been added.", "Info", JOptionPane.INFORMATION_MESSAGE);	
							return;
						}

						int ans = JOptionPane.showConfirmDialog(ManageDistFilesPanel.this, "Do you want to delete the original file?", "Confirmation", JOptionPane.YES_NO_OPTION);					
						DistFileAdder worker = new DistFileAdder(selectedFile, RoIFilename, ans, comboBox_1);
						worker.execute();
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred when checking if the distribution file is already in the dataset's folder.", "Error", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = list.getSelectedValue();
				if(selectedItem != null) {
					String RoIFilename = (String) comboBox_1.getSelectedItem();
					String distFilePathname = null;
					if (dataset.getType().equals("GPS")) {
						String SPPara = (String) comboBox.getSelectedItem();					
						distFilePathname = dataset.getOutputPath() + "/Dist/" + SPPara + "/" + RoIFilename + "/" + selectedItem;
					} else {
						distFilePathname = dataset.getOutputPath() + "/Dist/" + RoIFilename + "/" + selectedItem;
					}
					distStats.remove(distFilePathname);								
					
					int ans = JOptionPane.showConfirmDialog(ManageDistFilesPanel.this, "Are you sure that you want to delete the distribution file?", "Confirm", JOptionPane.YES_NO_OPTION);
					if(ans == JOptionPane.YES_OPTION) {
						DistFileWriter writer = new DistFileWriter(distFilePathname, comboBox_1, RoIFilename);
						writer.execute();
					}					
				}
			}
		});						
	}
	
	private void fillComboBox(WideJComboBox<String> comboBox) {
		File distDir = new File(dataset.getOutputPath() + "/Dist");
		if (distDir.exists()) {
			comboBox.removeAllItems();
			String[] SPParas = new File(dataset.getOutputPath() + "/Dist").list();
			for (String anSPPara : SPParas) {
				if (!anSPPara.equals("DistributionFilesStats.txt")) {
					comboBox.addItem(anSPPara);
				}
			}
			comboBox.setWide(true);
		}
	}
	
	private class Message {
		public Byte type;
		public String message;
	}
	
	private class DistFileGenerator extends SwingWorker<Message, Void> {
		private String SPPara;
		private String RoIFilename;
		private int tags;
		private WideJComboBox<String> comboBox;
		private WideJComboBox<String> comboBox_1;
		
		public DistFileGenerator(String anSPPara, String aFilename, int anInt, WideJComboBox<String> aComboBox, WideJComboBox<String> aComboBox2) {
			SPPara = anSPPara;
			RoIFilename = aFilename;
			tags = anInt;
			comboBox = aComboBox;
			comboBox_1 = aComboBox2;
		}

		@Override
		protected Message doInBackground() throws Exception {
			Message result = new Message();
			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				if (dataset.getType().equals("GPS")) {
					br = new BufferedReader(new FileReader(dataset.getOutputPath() + "/RoIs/" + SPPara + "/" + RoIFilename));
				} else {
					br = new BufferedReader(new FileReader(dataset.getOutputPath() + "/RoIs/" + RoIFilename));
				}
				int lastRoIID = -1;
				String strLine = null;
				StringBuilder builder = new StringBuilder();
				while ((strLine = br.readLine()) != null) {
					String region = strLine.split(" ")[0];
					int currentRoIID = Integer.parseInt(region);
					/*if(currentRoIID - lastRoIID != 1) {						
						result.type = 3;
						return result;
					}*/
					builder.append(region);
					double[] distribution = generateDistribution(tags);
					for (int i = 0; i < tags; i++)
						builder.append("\t" + distribution[i]);
					builder.append("\n");
					lastRoIID = currentRoIID;
				}				
								
				File distDir = null;
				if (dataset.getType().equals("GPS")) {
					distDir = new File(dataset.getOutputPath() + "/Dist/" + SPPara + "/" + RoIFilename);
				} else {
					distDir = new File(dataset.getOutputPath() + "/Dist/" + RoIFilename);
				}
				if(!distDir.exists()) {
					distDir.mkdirs();
				}				
				result.message = "Distribution_" + distDir.list().length + ".txt";
				bw = new BufferedWriter(new FileWriter(distDir + "/" + result.message));
				bw.write(builder.toString());
				bw.close();
				
				bw = new BufferedWriter(new FileWriter(dataset.getOutputPath() + "/Dist/DistributionFilesStats.txt", true));
				bw.write(distDir + "\\" + result.message + ";" + tags + " " + (lastRoIID + 1) + "\n");
				distStats.put(distDir + "\\" + result.message, tags + " " + (lastRoIID + 1));
			} catch(FileNotFoundException e) {
				result.type = 1;
				return result;
			} catch(NumberFormatException e) {
				result.type = 2;
				return result;
			} catch(IOException e) {
				result.type = 4;
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				br.close();
				bw.close();
			}

			result.type = 0;
			return result;
		}
		
		public void done() {
			try {
				Message result = get();
				if(result.type == 0) {
					if (dataset.getType().equals("GPS")) {
						fillComboBox(comboBox);
						comboBox.setSelectedItem(SPPara);
						comboBox_1.setSelectedItem(RoIFilename);
					} else {
						fillComboBox(comboBox_1);
						comboBox_1.setSelectedItem(RoIFilename);
					}
					panel.setVisible(true);
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Succeeded in generating and adding the distribution file " + result.message + ".", "Successful operation", JOptionPane.INFORMATION_MESSAGE);
				} else if(result.type == 1) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Cannot find the specified RoI file.", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(result.type == 2 || result.type == 3) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "The specified RoI file is malformatted.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred while performing file input or output.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Unknown error: cannot know the result of the operation.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		public double[] generateDistribution(int n)
	    {
			NormalDistribution nd = new NormalDistribution();
			double[] a = new double[n];
			double[] x = new double[n];
			double[] densities = new double[n];
			Random r = new Random();
			for(int i = 0; i < n; i++)
			{
				x[i] = r.nextDouble() * 5.0;
			}
			for(int i = 0; i < n; i++)
			{
				densities[i] = nd.density(x[i]);
			}
			double sum = 0.0;
			for(double v : densities)
			{
				sum += v;
			}
			for(int i = 0; i < n; i++)
			{
				densities[i] /= sum;
			}
			/*System.out.println(Arrays.toString(x));*/
			ArrayList<Integer> al = new ArrayList<Integer>();
			for(int i = 0; i < n; i++)
			{
				al.add(i);
			}
			int i = 0;
			while(al.size() != 0)
			{
				int alIndex = r.nextInt(al.size());
				int tagIndex = al.remove(alIndex);
				a[tagIndex] = densities[i++];
			}
			/*System.out.println(Arrays.toString(a));*/
			return a;
	    }
	}
	
	private class DistFileWriter extends SwingWorker<Boolean, Void> {		
		private String pathName;
		private WideJComboBox<String> comboBox_1;
		private String RoIFilename;
		
		public DistFileWriter(String aPathName, WideJComboBox<String> aComboBox, String anRoIFilename) {
			pathName = aPathName;
			comboBox_1 = aComboBox;
			RoIFilename = anRoIFilename;
		}
		
		@Override
		protected Boolean doInBackground() throws Exception {
			new File(pathName).delete();				
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(dataset.getOutputPath() + "/Dist/DistributionFilesStats.txt"));
				for(Map.Entry<String, String> anEntry : distStats.entrySet()) {
					bw.write(anEntry.getKey() + ";" + anEntry.getValue() + "\n");
				}
				bw.close();
			} catch(IOException e) {
				return false;
			}
			
			return true;
		}
		
		public void done() {
			try {
				boolean result = get();
				if(result == false) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred when deleting the distribution file.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					comboBox_1.setSelectedItem(RoIFilename);
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Succeeded in deleting the distribution file.", "Successful operation", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Unknown error: cannot know the result of the deleting process.", "Error", JOptionPane.ERROR_MESSAGE);
			}			
		}
	}
	
	private class DistFileReader extends SwingWorker<Boolean, Void> {
		@Override
		protected Boolean doInBackground() throws Exception {
			File DistFolder = new File(dataset.getOutputPath() + "/Dist");
			if(!DistFolder.exists()) {
				return true;
			}
			try {
				BufferedReader bf = new BufferedReader(new FileReader(dataset.getOutputPath() + "/Dist/DistributionFilesStats.txt"));
				String aLine;
				while((aLine = bf.readLine()) != null) {
					String[] fields = aLine.split(";");
					distStats.put(fields[0], fields[1]);
				}
				bf.close();
			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			}
			return true;
		}
		
		public void done() {
			try {
				boolean result = get();
				if(result == false) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred when reading the distribution file.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Unknown error: cannot know the result of the reading process.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class DistFileAdder extends SwingWorker<Byte, Void> {
		private File selectedFile;
		private String middleDir;
		private int ans;
		private JComboBox<String> comboBox_1;
		
		public DistFileAdder(File aFile, String aDir, int anInt, JComboBox<String> aComboBox) {
			selectedFile = aFile;
			middleDir = aDir;
			ans = anInt;
			comboBox_1 = aComboBox;
		}
		
		@Override
		protected Byte doInBackground() throws Exception {
			/*File dir = new File(dataset.getOutputPath() + "/Dist");
			if(!ifDirExists) {
				dir.mkdirs();
			}*/
			
			File destFile = new File(dataset.getOutputPath() + "/Dist/" + middleDir + "/" + selectedFile.getName());			
				
			try {
				if(ans == JOptionPane.YES_OPTION) {
					FileUtils.moveFile(selectedFile, destFile);
				} else {
					FileUtils.copyFile(selectedFile, destFile);
				}
			} catch(IOException e) {
				return 1;
			}
			
			try {
				String lastLine;
				RandomAccessFile rf = new RandomAccessFile(destFile.getAbsolutePath(), "r");
				long pos = rf.length();
				while(true) {
					if(pos <= 0) {
						rf.seek(0);
						lastLine = rf.readLine();
						if(lastLine != null && !lastLine.trim().isEmpty()) {
							break;
						}
						else {
							destFile.delete();
							rf.close();
							return 2;
						}
					}
					rf.seek(pos-1);
					char theChar = (char)rf.readByte();
					String aLine = rf.readLine();
					if(theChar == '\n' && !(aLine == null || aLine.trim().isEmpty())) {
						rf.seek(pos);
						lastLine = rf.readLine();						
						break;
					}
					pos--;
				}
				rf.close();
				String[] fields = lastLine.split("\t");
				BufferedWriter bw = new BufferedWriter(new FileWriter(dataset.getOutputPath() + "/Dist/DistributionFilesStats.txt", true));
				bw.write(destFile.getAbsolutePath() + ";" + (fields.length - 1) + " " + (Integer.parseInt(fields[0]) + 1) + "\n");
				bw.close();
				distStats.put(destFile.getAbsolutePath(), (fields.length - 1) + " " + (Integer.parseInt(fields[0]) + 1));
			} catch (FileNotFoundException e) {
				return 2;
			} catch (IOException e) {
				return 2;
			} catch (NumberFormatException e) {
				return 2;
			}			
			
			DistFileReader reader = new DistFileReader();
			reader.execute();
			
			return -1;
		}
		
		public void done() {
			try {
				byte result = get();
				if(result == -1) {
					comboBox_1.setSelectedItem(comboBox_1.getSelectedItem());
					panel.setVisible(true);
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Succeeded in adding the new distribution file.", "Successful operation", JOptionPane.INFORMATION_MESSAGE);
				} else if(result == 1) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred when moving or copying the distribution file.", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(result == 2) {
					JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "An error occurred when processing the stats of the distribution file.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(ManageDistFilesPanel.this, "Unknown error: cannot know the result of the adding process.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
