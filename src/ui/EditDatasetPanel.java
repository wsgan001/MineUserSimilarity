package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.io.FileUtils;

@SuppressWarnings("serial")
public class EditDatasetPanel extends JPanel {
	private JTextField textField;	
	private JTextField textField_1;
	private JTextField textField_2;
	private String oldName;
	private String oldInputPath;
	private String oldOutputPath;
	private JTree tree;
	private Dataset dataset;
	private DefaultMutableTreeNode node;
	private DatasetEditor editor;
	private ProgressMonitor progressDialog;
	private Timer cancelMonitor;

	/**
	 * Create the panel.
	 */
	public EditDatasetPanel(/*Dataset aDataset, */JTree aTree, DefaultMutableTreeNode aNode) {
		node = aNode;
		dataset = (Dataset)node.getUserObject();
		oldName = dataset.getName();
		oldInputPath = dataset.getInputPath();
		oldOutputPath = dataset.getOutputPath();
		tree = aTree;
		/*dataset = aDataset;*/
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Edit the dataset " + dataset.getName(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{46, 0, 0};
		gbl_panel_5.rowHeights = new int[] {25, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblNewLabel_1 = new JLabel("Current output directory:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_5.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(oldInputPath);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 0;
		panel_5.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[] {25, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblName = new JLabel("New name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 68, 0, 5);
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel_1.add(lblName, gbc_lblName);
		
		textField = new JTextField(/*oldName*/);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setVisible(false);
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[] {25, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblInputDirectory = new JLabel("Input directory:");
		GridBagConstraints gbc_lblInputDirectory = new GridBagConstraints();
		gbc_lblInputDirectory.insets = new Insets(0, 8, 0, 5);
		gbc_lblInputDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblInputDirectory.gridx = 0;
		gbc_lblInputDirectory.gridy = 0;
		panel_2.add(lblInputDirectory, gbc_lblInputDirectory);
		
		textField_1 = new JTextField(/*oldInputPath*/);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		panel_2.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnSelect = new JButton("Select");		
		GridBagConstraints gbc_btnSelect = new GridBagConstraints();
		gbc_btnSelect.gridx = 2;
		gbc_btnSelect.gridy = 0;
		panel_2.add(btnSelect, gbc_btnSelect);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[] {25, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblOutputDirectory = new JLabel("New output directory:");
		GridBagConstraints gbc_lblOutputDirectory = new GridBagConstraints();
		gbc_lblOutputDirectory.insets = new Insets(0, 15, 0, 5);
		gbc_lblOutputDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblOutputDirectory.gridx = 0;
		gbc_lblOutputDirectory.gridy = 0;
		panel_3.add(lblOutputDirectory, gbc_lblOutputDirectory);
		
		textField_2 = new JTextField(/*oldOutputPath*/);
		textField_2.setEditable(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel_3.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JButton btnSelect_1 = new JButton("Select");		
		GridBagConstraints gbc_btnSelect_1 = new GridBagConstraints();
		gbc_btnSelect_1.gridx = 2;
		gbc_btnSelect_1.gridy = 0;
		panel_3.add(btnSelect_1, gbc_btnSelect_1);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		final JButton btnConfirm = new JButton("Confirm");		
		panel_4.add(btnConfirm);
		
		JButton btnClear = new JButton("Clear");		
		panel_4.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("<html>\r\nWARNING:\r\n<br/>\r\nPlease make sure that the new input and output directories are empty, or all existing contents in them will be deleted.\r\n</html>");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		add(lblNewLabel, BorderLayout.CENTER);
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_1.setText(openADirectoryDialog());
			}
		});
		
		btnSelect_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2.setText(openADirectoryDialog());
			}
		});
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = textField.getText().trim();
				String newInputPath = textField_1.getText().trim();
				String newOutputPath = textField_2.getText().trim();
				
				if(newName.equalsIgnoreCase(oldName) && newInputPath.equalsIgnoreCase(oldInputPath) && newOutputPath.equalsIgnoreCase(oldOutputPath)) {
					JOptionPane.showMessageDialog(EditDatasetPanel.this, "You did not change anything about the dataset.", "No work to be done", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!newInputPath.equalsIgnoreCase(oldInputPath)) {
					File newInputDir = new File(newInputPath);
					if(newInputDir.list().length != 0) {
						int ans = JOptionPane.showConfirmDialog(EditDatasetPanel.this, "The new input directory is not empty. \nUsing it will delete its contents.\nDo you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.NO_OPTION) {
							return;
						}
					}
				}
				if(!newOutputPath.equalsIgnoreCase(oldOutputPath)) {
					File newOutputDir = new File(newOutputPath);
					if(newOutputDir.list().length != 0) {
						int ans = JOptionPane.showConfirmDialog(EditDatasetPanel.this, "The new output directory is not empty. \nUsing it will delete its contents.\nDo you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);
						if(ans == JOptionPane.NO_OPTION) {
							return;
						}
					}
				}
				
				btnConfirm.setEnabled(false);
				editor = new DatasetEditor(newName, newInputPath, newOutputPath);
				editor.execute();
				progressDialog = new ProgressMonitor(EditDatasetPanel.this, "Modifying the dataset...", null, 0, 100);
				cancelMonitor.start();
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_2.setText("");
			}
		});
		
		cancelMonitor = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(progressDialog.isCanceled()) {
					editor.cancel(true);
					btnConfirm.setEnabled(true);
					cancelMonitor.stop();
				} else if(editor.isDone()) {
					progressDialog.close();
					try {
						boolean result = editor.get();
						if(result == false) {
							JOptionPane.showMessageDialog(EditDatasetPanel.this, "An error occurred when trying modifying the dataset.", "Error", JOptionPane.ERROR_MESSAGE);							
						} else {
							JOptionPane.showMessageDialog(EditDatasetPanel.this, "Succeeded in modifying the dataset.", "Successful operation", JOptionPane.INFORMATION_MESSAGE);							
						}
					} catch (ExecutionException ex) {
						JOptionPane.showMessageDialog(EditDatasetPanel.this, "Unknown error: cannot know the result of the modifying process.", "Error", JOptionPane.ERROR_MESSAGE);						
					} catch (InterruptedException ex) {
						JOptionPane.showMessageDialog(EditDatasetPanel.this, "Unknown error: cannot know the result of the modifying process.", "Error", JOptionPane.ERROR_MESSAGE);
					} finally {
						cancelMonitor.stop();
						btnConfirm.setEnabled(true);
					}
				} else {
					progressDialog.setProgress(editor.getProgress());
				}				
			}			
		});
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
	
	private class DatasetEditor extends SwingWorker<Boolean, Integer> {
		private String newName;
		private String newInputPath;
		private String newOutputPath;
		
		public DatasetEditor(String aName, String anInputPath, String anOutputPath) {
			newName = aName;
			newInputPath = anInputPath;
			newOutputPath = anOutputPath;
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			// TODO Auto-generated method stub
			dataset.setName(newName);
			dataset.setInputPath(newInputPath);
			dataset.setOutputPath(newOutputPath);			
			setProgress(10);
			
			if(!oldInputPath.equalsIgnoreCase(newInputPath)) {
				try {
					File oldInputDir = new File(oldInputPath);
					File newInputDir = new File(newInputPath);
					FileUtils.deleteDirectory(newInputDir);
					FileUtils.moveDirectory(oldInputDir, newInputDir);
				} catch (IOException e) {
					return false;
				}
			}
			setProgress(50);
			if(!oldOutputPath.equalsIgnoreCase(newOutputPath)) {
				try {
					File oldOutputDir = new File(oldOutputPath);
					File newOutputDir = new File(newOutputPath);
					FileUtils.deleteDirectory(newOutputDir);
					FileUtils.moveDirectory(oldOutputDir, newOutputDir);
				} catch (IOException e) {
					return false;
				}
			}
			
			oldName = newName;
			oldInputPath = newInputPath;
			oldOutputPath = newOutputPath;
			setProgress(90);
			
			DefaultTreeModel treeModel = (DefaultTreeModel)tree.getModel();
			treeModel.nodeChanged(node);
			StringBuilder sb = new StringBuilder();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
			Enumeration<DefaultMutableTreeNode> currentDatasets = root.children();
			while(currentDatasets.hasMoreElements()) {
				DefaultMutableTreeNode aCurrentDataset = currentDatasets.nextElement();
				Dataset aDataset = (Dataset)aCurrentDataset.getUserObject();
				sb.append("Name;" + aDataset.getName() + "\nType;" + aDataset.getType() + "\nInputPath;" + aDataset.getInputPath() + "\nOutputPath;" + aDataset.getOutputPath() + "\n");
			}
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(".\\Datasets.txt"));
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e) {
				//JOptionPane.showMessageDialog(null, "An error occurred when writing the dataset file.", "File output error", JOptionPane.ERROR_MESSAGE);	
				return false;
			}
			setProgress(100);
			/*TreePath path = new TreePath(treeModel.getPathToRoot(node));
			treeModel.valueForPathChanged(path, dataset);
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
			Enumeration<DefaultMutableTreeNode> currentDatasets = root.children();
			while(currentDatasets.hasMoreElements()) {
				DefaultMutableTreeNode aCurrentDataset = currentDatasets.nextElement();
				Dataset aDataset = (Dataset)aCurrentDataset.getUserObject();
				if(aDataset == dataset) {
					
				}
			}*/
			return true;
		}
		
	}
}
