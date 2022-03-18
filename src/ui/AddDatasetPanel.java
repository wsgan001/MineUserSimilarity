package ui;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import detectStayPoints.UserStatistics;

@SuppressWarnings("serial")
public class AddDatasetPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTree tree;
	private DatasetGeoValidityChecker geoChecker;
	private DatasetYonValidityChecker yonChecker;
	private GeoStatsAcquirer geoStat;
	private YonStatsAcquirer yonStat;
	private ProgressMonitor progressDialog;
	private ProgressMonitor progressDialogStat;
	private Timer cancelMonitor;
	private Timer cancelMonitorStat;
	private String name;
	private String inputPath;
	private String outputPath;
	private String type;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;	

	/**
	 * Create the panel.
	 */
	public AddDatasetPanel(JTree aTree) {
		tree = aTree;
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add a new dataset", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] {31, 150, 0, 0, 0, 0};
		gbl_panel_4.rowHeights = new int[] {23, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 53, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel_4.add(lblName, gbc_lblName);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel_4.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblType = new JLabel("Type:");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.insets = new Insets(0, 0, 0, 5);
		gbc_lblType.anchor = GridBagConstraints.EAST;
		gbc_lblType.gridx = 3;
		gbc_lblType.gridy = 0;
		panel_4.add(lblType, gbc_lblType);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"GPS point", "Stay point"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 0;
		panel_4.add(comboBox, gbc_comboBox);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {55, 200, 61, 0};
		gbl_panel_1.rowHeights = new int[]{23, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblInputDirectory = new JLabel("Input directory:");
		GridBagConstraints gbc_lblInputDirectory = new GridBagConstraints();
		gbc_lblInputDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblInputDirectory.insets = new Insets(0, 8, 0, 5);
		gbc_lblInputDirectory.gridx = 0;
		gbc_lblInputDirectory.gridy = 0;
		panel_1.add(lblInputDirectory, gbc_lblInputDirectory);
		
		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.weightx = 1.0;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(openADirectoryDialog());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {84, 86, 61, 0};
		gbl_panel_2.rowHeights = new int[]{23, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblOutputDirectory = new JLabel("Output directory:");
		GridBagConstraints gbc_lblOutputDirectory = new GridBagConstraints();
		gbc_lblOutputDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblOutputDirectory.insets = new Insets(0, 0, 0, 5);
		gbc_lblOutputDirectory.gridx = 0;
		gbc_lblOutputDirectory.gridy = 0;
		panel_2.add(lblOutputDirectory, gbc_lblOutputDirectory);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.weightx = 1.0;
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_2.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnSelect_1 = new JButton("Select");
		btnSelect_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedDir = openADirectoryDialog();
				if(!selectedDir.isEmpty()) {
					File inputDir = new File(selectedDir);
					if(inputDir.list().length != 0) {
						JOptionPane.showMessageDialog(AddDatasetPanel.this, "The output directory is not empty.", "Invalid output directory", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						textField_1.setText(inputDir.getAbsolutePath());
					}
				}
			}
		});
		GridBagConstraints gbc_btnSelect_1 = new GridBagConstraints();
		gbc_btnSelect_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnSelect_1.gridx = 2;
		gbc_btnSelect_1.gridy = 0;
		panel_2.add(btnSelect_1, gbc_btnSelect_1);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		final JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = (String)comboBox.getSelectedItem();
				if(type == null) {
					JOptionPane.showMessageDialog(null, "You haven't chosen a dataset type.", "Choose a type", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				name = textField_2.getText().trim();
				inputPath = textField.getText().trim();
				outputPath = textField_1.getText().trim();
				
				if(name.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You haven't filled in the dataset's name.", "Fill in a name", JOptionPane.INFORMATION_MESSAGE);
					return;					
				}
				if(inputPath.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You haven't chosen an input directory.", "Choose an input directory", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(outputPath.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You haven't chosen an output directory.", "Choose an output directory", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				treeModel = (DefaultTreeModel)(tree.getModel());								
				
				if(type.equals("GPS point")) {
					btnAdd.setEnabled(false);
					geoChecker = new DatasetGeoValidityChecker(/*inputPath*/);
					geoChecker.execute();
					progressDialog = new ProgressMonitor(AddDatasetPanel.this, "Checking the validity of the input directory...", null, 0, 100);
					cancelMonitor.start();					
				} else {
					btnAdd.setEnabled(false);
					yonChecker = new DatasetYonValidityChecker(/*inputPath*/);
					yonChecker.execute();
					progressDialog = new ProgressMonitor(AddDatasetPanel.this, "Checking the validity of the input directory...", null, 0, 100);
					cancelMonitor.start();
				}
			}
		});
		panel_3.add(btnAdd);
		
		//notify the EDT to check if the worker thread has finished adding a new dataset
		cancelMonitor = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(type.equals("GPS point")) {
					if(progressDialog.isCanceled()) {
						geoChecker.cancel(true);
						btnAdd.setEnabled(true);
						cancelMonitor.stop();
					} else if(geoChecker.isDone()) {
						progressDialog.close();
						try {
							InvalidMessage result = geoChecker.get();
							if(result.type == -1) {
								BufferedWriter bw = new BufferedWriter(new FileWriter("./Datasets.txt", true));
								bw.write("Name;" + name + "\nType;GPS\nInputPath;" + inputPath + "\nOutputPath;" + outputPath + "\n");
								bw.close();

								Dataset newDataset = new Dataset(name, "GPS", inputPath, outputPath);
								DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newDataset, false);				
								treeModel.insertNodeInto(newNode, root, root.getChildCount());

								JOptionPane.showMessageDialog(null, "Succeeded in adding the new dataset.", "Added successfully", JOptionPane.INFORMATION_MESSAGE);

								geoStat = new GeoStatsAcquirer(newDataset);
								geoStat.execute();
								progressDialogStat = new ProgressMonitor(AddDatasetPanel.this, "Gathering stats of GPS points in the dataset...", null, 0, 100);
								cancelMonitorStat.start();								
							} else if(result.type == 1) {
								JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThere are files in it.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							} else if(result.type == 2) {	
								JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThe names of some subfolders do not consist of three digits.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							} else if(result.type == 3) {
								JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThere are folders in the subfolder of the user " + result.message +".", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							} else if(result.type == 4) {
								JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThe user " + result.message + " has a filename whose first eight characters are not a valid date.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							} else if(result.type == 5) {
								JOptionPane.showMessageDialog(null, "A dataset with the same name already exists.", "Duplicate name", JOptionPane.INFORMATION_MESSAGE);
							} else if(result.type == 6) {
								JOptionPane.showMessageDialog(null, "A dataset with the same input directory already exists.", "Duplicate input directory", JOptionPane.INFORMATION_MESSAGE);
							} else if(result.type == 7) {
								JOptionPane.showMessageDialog(null, "A dataset with the same output directory already exists.", "Duplicate output directory", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "An error occurred when writing the dataset file.", "File output error", JOptionPane.ERROR_MESSAGE);						
						} finally {
							cancelMonitor.stop();
							btnAdd.setEnabled(true);
						}						
					} else {
						progressDialog.setProgress(geoChecker.getProgress());
					}				
				} else {
					if(progressDialog.isCanceled()) {
						yonChecker.cancel(true);
						btnAdd.setEnabled(true);
						cancelMonitor.stop();
					} else if(yonChecker.isDone()) {
						progressDialog.close();
						try {
							Integer result = yonChecker.get();
							if(result == -1) {
								BufferedWriter bw = new BufferedWriter(new FileWriter("./Datasets.txt", true));
								bw.write("Name;" + name + "\nType;SP\nInputPath;" + inputPath + "\nOutputPath;" + outputPath + "\n");
								bw.close();

								Dataset newDataset = new Dataset(name, "SP", inputPath, outputPath);
								DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new Dataset(name, "SP", inputPath, outputPath), false);				
								treeModel.insertNodeInto(newNode, root, root.getChildCount());
								
								JOptionPane.showMessageDialog(null, "Succeeded in adding the new dataset.", "Added successfully", JOptionPane.INFORMATION_MESSAGE);
								
								yonStat = new YonStatsAcquirer(newDataset);
								yonStat.execute();
								progressDialogStat = new ProgressMonitor(AddDatasetPanel.this, "Gathering stats of GPS points in the dataset...", null, 0, 100);
								cancelMonitorStat.start();								
							} else if(result == 1) {
								JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThere are folders in it.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							} else if(result == 2) {
								JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nSome filenames do not consist of three digits.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							} else if(result == 5) {
								JOptionPane.showMessageDialog(null, "A dataset with the same name already exists.", "Duplicate name", JOptionPane.INFORMATION_MESSAGE);
							} else if(result == 6) {
								JOptionPane.showMessageDialog(null, "A dataset with the same input directory already exists.", "Duplicate input directory", JOptionPane.INFORMATION_MESSAGE);
							} else if(result == 7) {
								JOptionPane.showMessageDialog(null, "A dataset with the same output directory already exists.", "Duplicate output directory", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "An error occurred when writing the dataset file.", "File output error", JOptionPane.ERROR_MESSAGE);						
						} finally {
							cancelMonitor.stop();
							btnAdd.setEnabled(true);
						}						
					} else {
						progressDialog.setProgress(yonChecker.getProgress());
					}
				}
			}
		});
		
		//notify the EDT to check if the worker thread has finished gathering statistics from a new dataset
		cancelMonitorStat = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(type.equals("GPS point")) {
					if(progressDialogStat.isCanceled()) {
						geoStat.cancel(true);
						/*btnAdd.setEnabled(true);*/
						cancelMonitorStat.stop();
					} else if(geoStat.isDone()) {
						progressDialogStat.close();
						try {
							String result = geoStat.get();
							if(!result.equals("Successful")) {
								JOptionPane.showMessageDialog(AddDatasetPanel.this, "An error occurred when acquiring stats of the user " + result + ".", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							}
						} catch (InterruptedException | ExecutionException e) {
							JOptionPane.showMessageDialog(AddDatasetPanel.this, "Unknown error: cannot know the result of the process of acquiring GPS points' stats.", "Error", JOptionPane.ERROR_MESSAGE);
						} finally {
							cancelMonitorStat.stop();
							/*btnAdd.setEnabled(true);*/
						}
					} else {						
						progressDialogStat.setProgress(geoStat.getProgress());
					}
				} else {
					if(progressDialogStat.isCanceled()) {
						yonStat.cancel(true);
						/*btnAdd.setEnabled(true);*/
						cancelMonitorStat.stop();
					} else if(yonStat.isDone()) {
						progressDialogStat.close();
						try {
							String result = yonStat.get();
							if(!result.equals("Successful")) {
								JOptionPane.showMessageDialog(AddDatasetPanel.this, "An error occurred when acquiring stats of the user " + result + ".", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
							}
						} catch (InterruptedException | ExecutionException e) {
							JOptionPane.showMessageDialog(AddDatasetPanel.this, "Unknown error: cannot know the result of the process of acquiring GPS points' stats.", "Error", JOptionPane.ERROR_MESSAGE);
						} finally {
							cancelMonitorStat.stop();
							/*btnAdd.setEnabled(true);*/
						}
					} else {
						progressDialogStat.setProgress(yonStat.getProgress());
					}
				}
			}				
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
			}
		});
		panel_3.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("<html>\r\nThe dataset to be added should conform to one of the following two types of formats:\r\n<br/>\r\n<br/>\r\nGPS point type: (for example look at Geolife dataset)\r\n<br/>\r\nThe input directory contains subfolders each of which is for one user and whose names are the users' three-digit numbered IDs, like 003. In each user's subfolder there is one folder named \"Trajectory\", in which each file is his GPS point trajectory in one day, or part of it. The first eight characters of each filename are the date of the day, like 20090909.\r\n<br/>\r\n<br/>\r\nStay point type: (for example look at Yonsei dataset)\r\n<br/>\r\nThe input directory contains files each of which is one user's stay point trajectories. Each filename is the user's three-digit numbered ID, like 003.\r\n</html>");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		add(lblNewLabel, BorderLayout.CENTER);
	}

	private String openADirectoryDialog() {
		JFileChooser parseDir = new JFileChooser();
		parseDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		try {
			int returnValue = parseDir.showOpenDialog(this);		
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				return parseDir.getSelectedFile().getAbsolutePath();
			} 
			else {
				return "";
			}		
		}
		catch(HeadlessException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//check if any property of the new dataset you want to add is the same with that of some existing datasets
	private InvalidMessage checkDuplicateProperties() {
		root = (DefaultMutableTreeNode)treeModel.getRoot();
		Enumeration<DefaultMutableTreeNode> currentDatasets = root.children();
		while(currentDatasets.hasMoreElements()) {
			Dataset aCurrentDataset = (Dataset)currentDatasets.nextElement().getUserObject();
			if(name.equalsIgnoreCase(aCurrentDataset.toString())) {
				//JOptionPane.showMessageDialog(null, "A dataset with the same name already exists.", "Duplicate name", JOptionPane.INFORMATION_MESSAGE);
				InvalidMessage error = new InvalidMessage();
				error.type = 5;
				return error;
			}
			if(inputPath.equalsIgnoreCase(aCurrentDataset.getInputPath())) {
				//JOptionPane.showMessageDialog(null, "A dataset with the same input directory already exists.", "Duplicate input directory", JOptionPane.INFORMATION_MESSAGE);
				InvalidMessage error = new InvalidMessage();
				error.type = 6;
				return error;
			}
			if(outputPath.equalsIgnoreCase(aCurrentDataset.getOutputPath())) {
				//JOptionPane.showMessageDialog(null, "A dataset with the same output directory already exists.", "Duplicate output directory", JOptionPane.INFORMATION_MESSAGE);
				InvalidMessage error = new InvalidMessage();
				error.type = 7;
				return error;
			}
		}
		return null;
	}
	
	//inner class of a worker thread to check the validity of the new dataset of type GPS you want to add
	private class DatasetGeoValidityChecker extends SwingWorker<InvalidMessage, Integer> {
		@Override
		protected InvalidMessage doInBackground() throws Exception {
			InvalidMessage duplicateProperties = checkDuplicateProperties();
			if(duplicateProperties != null) {
				return duplicateProperties;
			}
			
			File inputDir = new File(inputPath);
			File[] filesInInputDir = inputDir.listFiles();
			setProgress(2);
			for(File aFile : filesInInputDir) {
				if(!aFile.isDirectory()) {
					//JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThere are files in it.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
					InvalidMessage error = new InvalidMessage();
					error.type = 1;
					return error;
				}						
			}
			setProgress(10);
			for(File aFile : filesInInputDir) {
				String dirName = aFile.getName();
				if(!dirName.matches("\\d{3}")) {
					//JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThe names of some subfolders do not consist of three digits.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
					InvalidMessage error = new InvalidMessage();
					error.type = 2;
					return error;
				}
			}
			setProgress(25);
			for(File aFile : filesInInputDir) {
				File[] files = new File(aFile + File.separator + "Trajectory").listFiles();
				for(File aDayFile : files) {
					if(!aDayFile.isFile()) {
						//JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\nThere are folders in the subfolder of the user " + aFile.getName() +".", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
						InvalidMessage error = new InvalidMessage();
						error.type = 3;
						error.message = aFile.getName();
						return error;
					}
				}
			}
			setProgress(40);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			df.setLenient(false);
			for(File aFile : filesInInputDir) {
				File[] files = new File(aFile + File.separator + "Trajectory").listFiles();
				for(File aDayFile : files) {
					String fileName = aDayFile.getName();
					String firstEight = fileName.substring(0, 8);						
					try {								
						if(!Character.isDigit(firstEight.charAt(7))) {
							throw new ParseException(null,0);
						}
						df.parse(firstEight);								
					} catch(ParseException ex) {
						//JOptionPane.showMessageDialog(null, "The input directory you chose does not meet the required format.\n"The user " + aFile.getName() + " has a filename whose first eight characters are not a valid date.", "Malformatted input directory", JOptionPane.ERROR_MESSAGE);
						InvalidMessage error = new InvalidMessage();
						error.type = 4;
						error.message = aFile.getName();
						return error;
					}
				}
			}
			setProgress(99);
			InvalidMessage error = new InvalidMessage();
			error.type = -1;
			return error;
		}		
	}
	
	//inner class of a worker thread to check the validity of the new dataset of type SP you want to add
	private class DatasetYonValidityChecker extends SwingWorker<Integer, Integer> {
		@Override
		protected Integer doInBackground() throws Exception {
			InvalidMessage duplicateProperties = checkDuplicateProperties();
			if(duplicateProperties != null) {
				return duplicateProperties.type;
			}

			File inputDir = new File(inputPath);
			File[] filesInInputDir = inputDir.listFiles();
			setProgress(10);
			for(File aFile : filesInInputDir) {
				if(!aFile.isFile()) {
					return 1;
				}
			}
			setProgress(50);
			for(File aFile : filesInInputDir) {
				String fileName = aFile.getName().substring(0, 3);
				if(!fileName.matches("\\d{3}")) {
					return 2;
				}
			}
			setProgress(100);
			return -1;
		}		
	}
	
	private class InvalidMessage {
		public int type;
		public String message;
	}
	
	//acquiring statistics of the new dataset of type GPS
	private class GeoStatsAcquirer extends SwingWorker<String, Void> {
		private Dataset dataset;
		
		public GeoStatsAcquirer(Dataset aDataset) {
			dataset = aDataset;
		}
		
		@Override
		protected String doInBackground() throws Exception {
			File[] userSubfolders = new File(dataset.getInputPath()).listFiles();
			StringBuilder sb = new StringBuilder();
			double j = 1.0;
			for(File aUser : userSubfolders) {
				UserStatsSourceGPS stat = new UserStatsSourceGPS();
				stat.setUserID(aUser.getName());
				
				String[] files = new File(aUser + "/Trajectory").list();
				TreeMap<String, LinkedList<String>> map = new TreeMap<String, LinkedList<String>>();
				for (int i = 0; i < files.length; i++) {
					String date = files[i].substring(0, 8);
					if (map.containsKey(date))
						map.get(date).add(files[i]);
					else {
						LinkedList<String> filelist = new LinkedList<String>();
						filelist.add(files[i]);
						map.put(date, filelist);
					}
				}
				stat.setNumberOfDays(map.size());
				
				try {
					for(Map.Entry<String, LinkedList<String>> aDay : map.entrySet()) {	
						int numberGPSInTheDay = 0;
						for(String aFile : aDay.getValue()) {
							BufferedReader br = new BufferedReader(new FileReader(aUser + "/Trajectory/" + aFile));
							for (int k = 0; k < 6; k++)
								br.readLine();
							
							String aLine;
							while((aLine = br.readLine()) != null) {
								numberGPSInTheDay++;
							}
							br.close();
						}
						
						stat.setNumberOfGPSPoints(stat.getNumberOfGPSPoints() + numberGPSInTheDay);
						if(stat.getMaximalNumberOfGPSPointsInADay() < numberGPSInTheDay)
				        	stat.setMaximalNumberOfGPSPointsInADay(numberGPSInTheDay);			        
				        if(stat.getMinimalNumberOfGPSPointsInADay() > numberGPSInTheDay) 
				        	stat.setMinimalNumberOfGPSPointsInADay(numberGPSInTheDay);
					}						        			        			        
				} catch(Exception e) {
					return aUser.getName();
				}				
				sb.append(stat.toString());
				setProgress((int)(j / userSubfolders.length * 100));
				j++;
			}
			
			File dir = new File(dataset.getOutputPath() + "/Stats/SourceDataAndStayPoints");
	        if(!dir.exists()) {
	        	dir.mkdirs();
	        }
			BufferedWriter bw = new BufferedWriter(new FileWriter(dir + "/SourceData.txt"));
	        bw.write(sb.toString());
			bw.close();
			return "Successful";
		}
	}
	
	//acquiring statistics of the new dataset of type SP
	private class YonStatsAcquirer extends SwingWorker<String, Void> {
		private Dataset dataset;
		
		public YonStatsAcquirer(Dataset aDataset) {
			dataset = aDataset;
		}
		
		@Override
		protected String doInBackground() throws Exception {
			File[] users = new File(dataset.getInputPath()).listFiles();
			StringBuilder sb = new StringBuilder();
			int j = 1;
			for(File aUser : users) {
				UserStatistics stat = new UserStatistics();
				stat.setUserID(aUser.getName().substring(0, 3));
				
				int numberOfDays = 0;
				int numberOfStayPoints = 0;
				int maximalNumberOfStayPointsInADay = 0;
				int minimalNumberOfStayPointsInADay = Integer.MAX_VALUE;
				try {
					BufferedReader bf = new BufferedReader(new FileReader(aUser));
					String aLine;
					while((aLine = bf.readLine()) != null) {
						numberOfDays++;
						String[] fields = aLine.split(" ");
						int numberOfStayPointsInADay = fields.length / 3 - 1;
						numberOfStayPoints += numberOfStayPointsInADay;
						if(maximalNumberOfStayPointsInADay < numberOfStayPointsInADay) {
							maximalNumberOfStayPointsInADay = numberOfStayPointsInADay;
						}
						if(minimalNumberOfStayPointsInADay > numberOfStayPointsInADay) {
							minimalNumberOfStayPointsInADay = numberOfStayPointsInADay;
						}
					}
					bf.close();
				} catch(Exception e) {
					return aUser.getName();
				}
				
				stat.setNumberOfDays(numberOfDays);
				stat.setNumberOfStayPoints(numberOfStayPoints);
				stat.setMaximalNumberOfStayPointsInADay(maximalNumberOfStayPointsInADay);
				stat.setMinimalNumberOfStayPointsInADay(minimalNumberOfStayPointsInADay);
				
				sb.append(stat.toString());
				setProgress(j / users.length);
				j++;
			}
			
			File dir = new File(dataset.getOutputPath() + "/Stats");
	        if(!dir.exists()) {
	        	dir.mkdirs();
	        }
			BufferedWriter bw = new BufferedWriter(new FileWriter(dir + "/StayPoints.txt"));
	        bw.write(sb.toString());
			bw.close();
			return "Successful";
		}		
	}	
	
	private class UserStatsSourceGPS {
		private String userID;
		private int numberOfDays, numberOfGPSPoints, maximalNumberOfGPSPointsInADay, minimalNumberOfGPSPointsInADay;
		
		public UserStatsSourceGPS() 
	    {	        
	        minimalNumberOfGPSPointsInADay = Integer.MAX_VALUE;
	    }
		
		/*public String getUserID() 
	    {
	        return userID;
	    }*/

	    public void setUserID(String aUserID) 
	    {
	        userID = aUserID;
	    }

	    /*public int getNumberOfDays() 
	    {
	        return numberOfDays;
	    }*/
	    
	    public void setNumberOfDays(int someNumberOfDays) 
	    {
	        numberOfDays = someNumberOfDays;
	    }
	    
	    public int getNumberOfGPSPoints() 
	    {
	        return numberOfGPSPoints;
	    }

	    public void setNumberOfGPSPoints(int someNumberOfGPSPoints) 
	    {
	        numberOfGPSPoints = someNumberOfGPSPoints;
	    }
	    
	    public int getMaximalNumberOfGPSPointsInADay() {
	    	return maximalNumberOfGPSPointsInADay;
	    }
	    
	    public void setMaximalNumberOfGPSPointsInADay(int someNumberOfGPSPointsInADay) {
	    	maximalNumberOfGPSPointsInADay = someNumberOfGPSPointsInADay;
	    }
	    
	    public int getMinimalNumberOfGPSPointsInADay() {
	    	return minimalNumberOfGPSPointsInADay;
	    }
	    
	    public void setMinimalNumberOfGPSPointsInADay(int someNumberOfGPSPointsInADay) {
	    	minimalNumberOfGPSPointsInADay = someNumberOfGPSPointsInADay;
	    }
	    
	    /*public double calculateAverageNumberOfGPSPointsPerDay() {
	    	return (double)numberOfGPSPoints / numberOfDays;
	    }*/
	    
	    public String toString() {
	    	return userID + " " + numberOfDays + " " + numberOfGPSPoints + " " + minimalNumberOfGPSPointsInADay + " " + maximalNumberOfGPSPointsInADay + " " + ((double)numberOfGPSPoints / numberOfDays) + "\n";
	    }
	}
}
