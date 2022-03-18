package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.io.FileUtils;

import javax.swing.JPanel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.FlowLayout;

public class Main {

	private JFrame frame;
	private JPanel panel_1;
	private JSplitPane splitPane;
	private AboutDialog aboutDialog;
	private Image generalFlowchart;
	private JLabel lblNewLabel;
	private AllowExit allowExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Main window = new Main();
					window.frame.setVisible(true);
					
					try {
						window.generalFlowchart = ImageIO.read(Main.class.getResource("/images/GeneralFlowchart.png")/*new File(".\\images\\GeneralFlowchart.png")*/);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					window.generalFlowchart = window.generalFlowchart.getScaledInstance(window.lblNewLabel.getWidth(), window.lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
					window.lblNewLabel.setIcon(new ImageIcon(window.generalFlowchart));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frame = new JFrame("MinUS Tool");
		frame.setBounds(100, 100, 813, 510);
		allowExit = new AllowExit();
		allowExit.ifAllowExit = true;
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(allowExit.ifAllowExit == true) {
					int ans = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?","Confirm exit",JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnDatasets = new JMenu("Datasets");
		menuBar.add(mnDatasets);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		
		mnDatasets.add(mntmAdd);
		
		JMenuItem mntmEdit = new JMenuItem("Edit");		
		mnDatasets.add(mntmEdit);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");		
		mnDatasets.add(mntmDelete);
		
		JSeparator separator = new JSeparator();
		mnDatasets.add(separator);
		
		JMenuItem mntmViewDatasetsInfo = new JMenuItem("View dataset's info");		
		mnDatasets.add(mntmViewDatasetsInfo);
		
		JMenuItem mntmViewUsersStats = new JMenuItem("View users' stats");		
		mnDatasets.add(mntmViewUsersStats);
		
		JMenu mnUserProfile = new JMenu("User profile");
		menuBar.add(mnUserProfile);
		
		JMenuItem mntmConstruct = new JMenuItem("Construct");		
		mnUserProfile.add(mntmConstruct);
		
		JSeparator separator_1 = new JSeparator();
		mnUserProfile.add(separator_1);
		
		JMenuItem mntmVisualize = new JMenuItem("Visualize");
		
		mnUserProfile.add(mntmVisualize);
		
		JMenu mnUserSimilarity = new JMenu("User similarity");
		menuBar.add(mnUserSimilarity);
		
		JMenuItem mntmCompareUsers = new JMenuItem("Compare users");		
		mnUserSimilarity.add(mntmCompareUsers);
		
		JSeparator separator_2 = new JSeparator();
		mnUserSimilarity.add(separator_2);
		
		JMenuItem mntmManageDistributionFiles = new JMenuItem("Manage semantic files");
		mnUserSimilarity.add(mntmManageDistributionFiles);		
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpContents = new JMenuItem("Help contents");		
		mnHelp.add(mntmHelpContents);
		
		JMenuItem mntmStartPage = new JMenuItem("Start page");		
		mnHelp.add(mntmStartPage);
		
		JMenuItem mntmAbout = new JMenuItem("About");		
		mnHelp.add(mntmAbout);
		
		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);		
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		ArrayList<Dataset> datasets = readDatasetFile();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("All datasets");
		for(Dataset aDataset : datasets) {
			DefaultMutableTreeNode aNode = new DefaultMutableTreeNode(aDataset, false);
			root.add(aNode);
		}
		
		final JTree tree = new JTree(root, true);		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane.setViewportView(tree);
		
		panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel();	
		panel_1.add(lblNewLabel, BorderLayout.CENTER);
			
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(9);
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel("The general workflow of the tool is:");
		panel_2.add(lblNewLabel_1);
		
		splitPane.setDividerLocation(100);
		
		//create the right-click menu
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem mntmEditPopup = new JMenuItem("Edit");
		Edit edit = new Edit(tree);
		mntmEditPopup.addActionListener(edit);
		popup.add(mntmEditPopup);
		JMenuItem mntmDeletePopup = new JMenuItem("Delete");
		Delete delete = new Delete(tree);
		mntmDeletePopup.addActionListener(delete);
		popup.add(mntmDeletePopup);
		JSeparator separator_3 = new JSeparator();
		popup.add(separator_3);
		JMenuItem mntmViewDatasetsInfoPopup = new JMenuItem("View dataset's info");	
		ViewDatasetsInfo vdi = new ViewDatasetsInfo(tree);
		mntmViewDatasetsInfoPopup.addActionListener(vdi);
		popup.add(mntmViewDatasetsInfoPopup);
		JMenuItem mntmViewUsersStatsPopup = new JMenuItem("View users' stats");
		ViewUsersStats vus = new ViewUsersStats(tree);
		mntmViewUsersStatsPopup.addActionListener(vus);
		popup.add(mntmViewUsersStatsPopup);
		JSeparator separator_4 = new JSeparator();
		popup.add(separator_4);
		JMenuItem mntmConstructPopup = new JMenuItem("Construct user profile");
		ConstructUserProfile cup = new ConstructUserProfile(tree);
		mntmConstructPopup.addActionListener(cup);
		popup.add(mntmConstructPopup);
		JMenuItem mntmVisualizePopup = new JMenuItem("Visualize");
		Visualize v = new Visualize(tree);
		mntmVisualizePopup.addActionListener(v);
		popup.add(mntmVisualizePopup);
		JSeparator separator_5 = new JSeparator();
		popup.add(separator_5);
		JMenuItem mntmCompareUserSimilarity = new JMenuItem("Compare user similarity");
		CompareUserSimilarity cus = new CompareUserSimilarity(tree);
		mntmCompareUserSimilarity.addActionListener(cus);
		popup.add(mntmCompareUserSimilarity);
		JMenuItem mntmManageDistributionFilesPopup = new JMenuItem("Manage distribution files");
		ManageDistributionFiles mdf = new ManageDistributionFiles(tree);
		mntmManageDistributionFilesPopup.addActionListener(mdf);
		popup.add(mntmManageDistributionFilesPopup);
		
		lblNewLabel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				lblNewLabel.setIcon(new ImageIcon(generalFlowchart.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH)));
				splitPane.setDividerLocation(100);
			}
		});
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				TreePath oldPath = e.getOldLeadSelectionPath();
				if (oldPath != null && oldPath.getLastPathComponent() != tree.getModel().getRoot()) {
					TreePath newPath = e.getNewLeadSelectionPath();
					if (newPath != null) {
						if (newPath.getLastPathComponent() == tree.getModel().getRoot()) {
							tree.clearSelection();
							tree.setSelectionPath(oldPath);
						} else {
							if (splitPane.getRightComponent() != panel_1) {
								int ans = JOptionPane.showConfirmDialog(frame, "Are you sure you want to change the selected dataset?", "Change the selected dataset", JOptionPane.YES_NO_OPTION);
								if (ans == JOptionPane.YES_OPTION) {
									splitPane.setRightComponent(panel_1);
									splitPane.setDividerLocation(100);
								} else {
									tree.clearSelection();
									tree.setSelectionPath(oldPath);
								}
							}
						}
					}
				}
			}
		});
		
		//add the right-click menu to the tree
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					 TreePath path = tree.getPathForLocation(e.getX(), e.getY());					 
					 if(path != null) {
						 if(path.getLastPathComponent() == tree.getModel().getRoot()) {
							 return;
						 }						 
						 Rectangle bound = tree.getPathBounds(path);
						 if(bound.contains(e.getX(), e.getY())) {
							 tree.setSelectionPath(path);
							 popup.show(tree, e.getX(), e.getY());
						 }
					 }
				}
			}
		});
		
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddDatasetPanel panel = new AddDatasetPanel(tree);
				splitPane.setRightComponent(panel);
				splitPane.setDividerLocation(100);
			}
		});
		
		mntmConstruct.addActionListener(cup);		
		mntmViewUsersStats.addActionListener(vus);		
		mntmEdit.addActionListener(edit);		
		mntmDelete.addActionListener(delete);		
		mntmViewDatasetsInfo.addActionListener(vdi);		
		mntmManageDistributionFiles.addActionListener(mdf);		
		mntmCompareUsers.addActionListener(cus);		
		mntmVisualize.addActionListener(v);
		
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (aboutDialog == null) {
					aboutDialog = new AboutDialog(frame);
				}	
				aboutDialog.setLocationRelativeTo(frame);			
				aboutDialog.setVisible(true);
			}
		});
		
		mntmStartPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				splitPane.setRightComponent(panel_1);
				splitPane.setDividerLocation(100);
			}
		});
		
		mntmHelpContents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] cmd = {"hh.exe", ".\\MinUSTool.chm"}; 
				ProcessBuilder pb = new ProcessBuilder(cmd);
				try {
					pb.start();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "An error occurred while opening the help file.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private DefaultMutableTreeNode checkIfDatasetSelected(JTree tree) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(selectedNode == null || selectedNode.isRoot()) {
			JOptionPane.showMessageDialog(frame, "Please first choose a dataset by selecting a node.", "Choose a dataset", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		return selectedNode;
	}
	
	//read registered datasets upon startup
	private ArrayList<Dataset> readDatasetFile() {
		ArrayList<Dataset> datasets = new ArrayList<Dataset>();
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(".\\Datasets.txt"));
			String aLine = null;
			while((aLine = bf.readLine()/*.trim()*/) != null/* && !aLine.equals("\n")*/) {
				if(aLine.contains("Name")) {
					String name = aLine.split(";")[1].trim();
					if((aLine = bf.readLine()).contains("Type")) {						
						String type = aLine.split(";")[1].trim();
						if((aLine = bf.readLine()).contains("InputPath")) {
							String inputPath = aLine.split(";")[1].trim();
							if((aLine = bf.readLine()).contains("OutputPath")) {
								String outputPath = aLine.split(";")[1].trim();
								datasets.add(new Dataset(name, type, inputPath, outputPath));
							}
							else {
								bf.close();
								throw new Exception();
							}
						}
						else {
							bf.close();
							throw new Exception();
						}
					}
					else {
						bf.close();
						throw new Exception();
					}
				}
				else {
					bf.close();
					throw new Exception();
				}
			}
			bf.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(frame, "The dataset file is malformatted.\nThe application cannot start up.", "File format error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		return datasets;
	}	
	
	private class Visualize implements ActionListener {
		private JTree tree;
		
		public Visualize(JTree aTree) {
			tree = aTree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			VisualizePanel panel = new VisualizePanel((Dataset)selectedNode.getUserObject());
			splitPane.setRightComponent(panel);
			splitPane.setDividerLocation(100);
		}		
	}
	
	private class ManageDistributionFiles implements ActionListener {
		private JTree tree;
		
		public ManageDistributionFiles(JTree aTree) {
			tree = aTree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			ManageDistFilesPanel panel = new ManageDistFilesPanel((Dataset)selectedNode.getUserObject());
			splitPane.setRightComponent(panel);
			splitPane.setDividerLocation(100);			
		}		
	}
	
	private class ViewUsersStats implements ActionListener {
		private JTree tree;
		
		public ViewUsersStats(JTree aTree) {
			tree = aTree;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			Dataset selectedDataset = (Dataset)selectedNode.getUserObject();				
			ViewUserStatsPanel panel = new ViewUserStatsPanel(selectedDataset);
			splitPane.setRightComponent(panel);
			splitPane.setDividerLocation(100);
		}		
	}
	
	private class ViewDatasetsInfo implements ActionListener {
		private JTree tree;
		
		public ViewDatasetsInfo(JTree aTree) {
			tree = aTree;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			DatasetStatPanel panel = new DatasetStatPanel((Dataset)selectedNode.getUserObject());
			splitPane.setRightComponent(panel);
			splitPane.setDividerLocation(100);
		}		
	}
	
	private class Delete implements ActionListener {
		private JTree tree;
		
		public Delete(JTree aTree) {
			tree = aTree;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			int ans = JOptionPane.showConfirmDialog(frame, "Do you also want to delete contents on disk?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
			if(ans != JOptionPane.CANCEL_OPTION) {
				DefaultTreeModel treeModel = (DefaultTreeModel)tree.getModel();
				treeModel.removeNodeFromParent(selectedNode);
				DatasetDeletor worker = new DatasetDeletor(treeModel, selectedNode, ans);
				worker.execute();
			}			
		}		
	}
	
	private class Edit implements ActionListener {
		private JTree tree;
		
		public Edit(JTree aTree) {
			tree = aTree;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			EditDatasetPanel panel = new EditDatasetPanel(tree, selectedNode);
			splitPane.setRightComponent(panel);
			splitPane.setDividerLocation(100);
		}		
	}
	
	private class CompareUserSimilarity implements ActionListener {
		private JTree tree;
		
		public CompareUserSimilarity(JTree aTree) {
			tree = aTree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			CompareUsersPanel panel = new CompareUsersPanel((Dataset)selectedNode.getUserObject());
			splitPane.setRightComponent(panel);
			splitPane.setDividerLocation(100);
		}		
	}
	
	private class ConstructUserProfile implements ActionListener {
		private JTree tree;
		
		public ConstructUserProfile(JTree aTree) {
			tree = aTree;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = checkIfDatasetSelected(tree);
			if(selectedNode == null) {
				return;
			}
			
			Dataset selectedDataset = (Dataset)selectedNode.getUserObject();
			String outputPath = selectedDataset.getOutputPath();
			String[] files = new File(outputPath).list();
			if(selectedDataset.getType().equals("GPS")) {					
				try {
					Arrays.sort(files, null);
					if(Arrays.binarySearch(files, "StayPoints", null) < 0) {
						ConstructUserProfilePanel panel = new ConstructUserProfilePanel(/*tree, */1, selectedDataset, allowExit);
						splitPane.setRightComponent(panel);
					} else {
						if(Arrays.binarySearch(files, "RoIs", null) < 0) {
							ConstructUserProfilePanel panel = new ConstructUserProfilePanel(/*tree, */2, selectedDataset, allowExit);
							splitPane.setRightComponent(panel);
						} else {
							ConstructUserProfilePanel panel = new ConstructUserProfilePanel(3, selectedDataset, allowExit);
							splitPane.setRightComponent(panel);
						}						
					}
					splitPane.setDividerLocation(100);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			} else {
				try {
					Arrays.sort(files, null);						
					if(Arrays.binarySearch(files, "RoIs", null) < 0) {
						ConstructUserProfilePanel panel = new ConstructUserProfilePanel(/*tree, */2, selectedDataset, allowExit);
						splitPane.setRightComponent(panel);
					} else {
						ConstructUserProfilePanel panel = new ConstructUserProfilePanel(3, selectedDataset, allowExit);
						splitPane.setRightComponent(panel);
					}							
					splitPane.setDividerLocation(100);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}		
	}
	
	private class DatasetDeletor extends SwingWorker<Byte, Integer> {
		private DefaultTreeModel treeModel;
		private DefaultMutableTreeNode selectedNode;
		private int ans = Integer.MAX_VALUE;
		
		public DatasetDeletor(DefaultTreeModel aTreeModel, DefaultMutableTreeNode aSelectedNode, int anInt) {
			treeModel = aTreeModel;
			selectedNode = aSelectedNode;
			ans = anInt;
		}
		
		@Override
		protected Byte doInBackground() throws Exception {
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
				return 1;
			}						
						
			if(ans == JOptionPane.YES_OPTION) {
				try {
					Dataset selectedDataset = (Dataset)selectedNode.getUserObject();
					FileUtils.deleteDirectory(new File(selectedDataset.getInputPath()));
					FileUtils.deleteDirectory(new File(selectedDataset.getOutputPath()));
				} catch (IOException ex) {
					//JOptionPane.showMessageDialog(frame, "An error occurred when trying deleting contents on disk.", "error", JOptionPane.ERROR_MESSAGE);
					return 2;
				}
			}			
			
			return -1;
		}
		
		public void done() {
			try {
				byte result = get();
				if(result == 1) {
					JOptionPane.showMessageDialog(frame, "An error occurred when writing the dataset file.", "File output error", JOptionPane.ERROR_MESSAGE);	
				} else if(result == 2) {
					JOptionPane.showMessageDialog(frame, "An error occurred when trying deleting contents on disk.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Succeeded in deleting the dataset.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(frame, "Unknown error: cannot get the result of the deletion operation.", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				splitPane.setRightComponent(panel_1);
			}
		}
	}
	
	public class AllowExit {
		public boolean ifAllowExit;
	}
}