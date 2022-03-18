package ui;

import hausdorff.UserComparisonHausdorff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import ui.ScrollablePanel.IncrementInfo;
import ui.ScrollablePanel.IncrementType;
import ui.ScrollablePanel.ScrollableSizeHint;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import cps.UserComparisonCPS;

import lcs.UserComparisonLCS;
import javax.swing.JSplitPane;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

@SuppressWarnings("serial")
public class CompareUsersPanel extends JPanel {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	private final ButtonGroup buttonGroup_4 = new ButtonGroup();
	private JTextField textField;
	private ScrollablePanel panel_17;
	private final ButtonGroup buttonGroup_5 = new ButtonGroup();
	private JLabel lblCalculatingSimilarityValues;
	private JButton btnStart;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public CompareUsersPanel(final Dataset dataset) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_3.add(panel_1);
		panel_1.setBorder(null);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JLabel lblStayPointParameter = new JLabel(" Stay point parameter:");
		panel_2.add(lblStayPointParameter);
		
		final WideJComboBox<String> comboBox = new WideJComboBox<String>();	
		panel_1.add(comboBox);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);
		
		JLabel lblRoiFile = new JLabel("RoI file:");
		panel_5.add(lblRoiFile);
		
		final WideJComboBox<String> comboBox_1 = new WideJComboBox<String>();		
		panel_4.add(comboBox_1);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		
		JLabel lblPatternSetParameter = new JLabel("Pattern set parameter:");
		panel_7.add(lblPatternSetParameter);
		
		final WideJComboBox<String> comboBox_2 = new WideJComboBox<String>();		
		panel_6.add(comboBox_2);
		
		JPanel panel_8 = new JPanel();
		panel_3.add(panel_8);
		
		JLabel lblUserList = new JLabel("User list:");
		panel_8.add(lblUserList);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		final JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10, BorderLayout.NORTH);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.Y_AXIS));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Methods for comparing users of the dataset " + dataset.getName(), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.Y_AXIS));
		
		JPanel panel_20 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_20.getLayout();
		flowLayout.setVgap(0);
		panel_11.add(panel_20);
		
		final JRadioButton rdbtnLcs = new JRadioButton("MTP based");		
		panel_20.add(rdbtnLcs);
		rdbtnLcs.getModel().setActionCommand("LCS");
		buttonGroup.add(rdbtnLcs);
		
		final JRadioButton rdbtnLcsImproved = new JRadioButton("Improved MTP-based ");	
		panel_20.add(rdbtnLcsImproved);
		rdbtnLcsImproved.getModel().setActionCommand("LCS improved");
		buttonGroup.add(rdbtnLcsImproved);
		
		final JRadioButton rdbtnCps = new JRadioButton("CPS-based");	
		panel_20.add(rdbtnCps);
		rdbtnCps.getModel().setActionCommand("CPS");
		buttonGroup.add(rdbtnCps);
		
		JRadioButton rdbtnHausdorff = new JRadioButton("Hausdorff distance-based");		
		panel_20.add(rdbtnHausdorff);
		rdbtnHausdorff.getModel().setActionCommand("Hausdorff");
		buttonGroup.add(rdbtnHausdorff);
		
		final JPanel panel_12 = new JPanel();
		panel_12.setBorder(new TitledBorder(null, "Semantics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.add(panel_12);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.Y_AXIS));
		
		JPanel panel_18 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_18.getLayout();
		flowLayout_1.setVgap(0);
		panel_12.add(panel_18);
		
		final JRadioButton rdbtnWithSemantics = new JRadioButton("With semantics");		
		rdbtnWithSemantics.getModel().setActionCommand("With semantics");
		panel_18.add(rdbtnWithSemantics);
		buttonGroup_1.add(rdbtnWithSemantics);
		
		final JRadioButton rdbtnWithoutSemantics = new JRadioButton("Without semantics");
		rdbtnWithoutSemantics.getModel().setActionCommand("Without semantics");
		panel_18.add(rdbtnWithoutSemantics);
		buttonGroup_1.add(rdbtnWithoutSemantics);
		
		final JPanel panel_19 = new JPanel();
		panel_19.setVisible(false);
		FlowLayout flowLayout_5 = (FlowLayout) panel_19.getLayout();
		flowLayout_5.setVgap(0);
		panel_12.add(panel_19);
		
		final JLabel lblDistanceThreshold = new JLabel("Distance threshold:");
		panel_19.add(lblDistanceThreshold);
		
		textField = new JTextField();
		panel_19.add(textField);
		textField.setColumns(10);
		
		JLabel lblDistributionFile = new JLabel("Distribution file: ");
		panel_19.add(lblDistributionFile);
		
		final JComboBox<String> comboBox_3 = new JComboBox<String>();		
		panel_19.add(comboBox_3);		
		
		final JPanel panel_13 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_13.getLayout();
		flowLayout_2.setVgap(0);
		panel_13.setBorder(new TitledBorder(null, "Time", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.add(panel_13);
		
		final JRadioButton rdbtnWithTime = new JRadioButton("With time");
		rdbtnWithTime.getModel().setActionCommand("With time");
		buttonGroup_2.add(rdbtnWithTime);
		panel_13.add(rdbtnWithTime);
		
		final JRadioButton rdbtnWithoutTime = new JRadioButton("Without time");
		rdbtnWithoutTime.getModel().setActionCommand("Without time");
		buttonGroup_2.add(rdbtnWithoutTime);
		panel_13.add(rdbtnWithoutTime);
		
		final JPanel panel_14 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_14.getLayout();
		flowLayout_3.setVgap(0);
		panel_14.setVisible(false);
		
		final JPanel panel_21 = new JPanel();
		panel_21.setVisible(false);
		panel_21.setBorder(new TitledBorder(null, "Two ways of CPS based with semantics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.add(panel_21);
		panel_21.setLayout(new BoxLayout(panel_21, BoxLayout.Y_AXIS));
		
		JPanel panel_22 = new JPanel();
		panel_21.add(panel_22);
		
		JRadioButton rdbtnSetAThreshold = new JRadioButton("Set a threshold on distribtuion probabilities");		
		panel_22.add(rdbtnSetAThreshold);
		rdbtnSetAThreshold.getModel().setActionCommand("Set a threshold on distribtuion probabilities");
		buttonGroup_5.add(rdbtnSetAThreshold);
		
		JRadioButton rdbtnSetThresholdsOn = new JRadioButton("Set thresholds on supports of semantic tag patterns");
		panel_22.add(rdbtnSetThresholdsOn);
		rdbtnSetThresholdsOn.getModel().setActionCommand("Set thresholds on supports of semantic tag patterns");
		buttonGroup_5.add(rdbtnSetThresholdsOn);
		
		final JPanel panel_23 = new JPanel();
		panel_21.add(panel_23);
		
		JLabel lblProbabilityThreshold = new JLabel("Probability threshold:");
		panel_23.add(lblProbabilityThreshold);
		
		textField_1 = new JTextField();
		panel_23.add(textField_1);
		textField_1.setColumns(10);
		panel_14.setBorder(new TitledBorder(null, "Two ways of Hausdorff", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.add(panel_14);
		
		JRadioButton rdbtnAdjustPatternsLengths = new JRadioButton("Supplement patterns' lengths");
		rdbtnAdjustPatternsLengths.getModel().setActionCommand("Adjust patterns' lengths");
		buttonGroup_3.add(rdbtnAdjustPatternsLengths);
		panel_14.add(rdbtnAdjustPatternsLengths);
		
		JRadioButton rdbtnClassifyByPatternLength = new JRadioButton("Classify by pattern length");
		rdbtnClassifyByPatternLength.getModel().setActionCommand("Classify by pattern length");
		buttonGroup_3.add(rdbtnClassifyByPatternLength);
		panel_14.add(rdbtnClassifyByPatternLength);
		
		final JPanel panel_15 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_15.getLayout();
		flowLayout_4.setVgap(0);
		panel_15.setVisible(false);
		panel_15.setBorder(new TitledBorder(null, "Distribution distance measurement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_10.add(panel_15);
		
		JRadioButton rdbtnHellinger = new JRadioButton("Hellinger");
		rdbtnHellinger.getModel().setActionCommand("Hellinger");
		buttonGroup_4.add(rdbtnHellinger);
		panel_15.add(rdbtnHellinger);
		
		JRadioButton rdbtnTotalVariance = new JRadioButton("Total variance");
		rdbtnTotalVariance.getModel().setActionCommand("Total variance");
		buttonGroup_4.add(rdbtnTotalVariance);
		panel_15.add(rdbtnTotalVariance);
		
		JPanel panel_16 = new JPanel();
		panel_10.add(panel_16);
		
		btnStart = new JButton("Start");		
		panel_16.add(btnStart);
		
		JButton btnClear = new JButton("Clear");		
		panel_16.add(btnClear);		
		
		lblCalculatingSimilarityValues = new JLabel();
		panel_16.add(lblCalculatingSimilarityValues);
		
		panel_17 = new ScrollablePanel(/*new GridLayout(0, 1)*/);
		panel_17.setLayout(new BoxLayout(panel_17, BoxLayout.Y_AXIS));
		panel_17.setScrollableWidth(ScrollableSizeHint.FIT);		
		IncrementInfo block = new IncrementInfo(IncrementType.PERCENT, 100);
		IncrementInfo unit = new IncrementInfo(IncrementType.PERCENT, 10);

		panel_17.setScrollableBlockIncrement(SwingConstants.HORIZONTAL, block);
		panel_17.setScrollableBlockIncrement(SwingConstants.VERTICAL, block);
		panel_17.setScrollableUnitIncrement(SwingConstants.HORIZONTAL, unit);
		panel_17.setScrollableUnitIncrement(SwingConstants.VERTICAL, unit);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(panel_17);
		panel_9.add(scrollPane_1, BorderLayout.CENTER);	
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		splitPane.setLeftComponent(panel);
		splitPane.setRightComponent(panel_9);
		
		ToolTipManager.sharedInstance().setDismissDelay(15000);
		
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem mntmViewFilePopup = new JMenuItem("View");
		popup.add(mntmViewFilePopup);
		
		comboBox.setPrototypeDisplayValue("MMMMMMMMMMMM");
		comboBox_1.setPrototypeDisplayValue("MMMMMMMMMMMM");
		comboBox_2.setPrototypeDisplayValue("MMMMMMMMMMMM");		
		
		comboBox_3.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				String selectedRoI = (String)comboBox_1.getSelectedItem();
				if (dataset.getType().equals("GPS")) {
					String selectedPara = (String)comboBox.getSelectedItem();				
					if (selectedPara != null && selectedRoI != null) {
						File distFilesAnRoI = new File(dataset.getOutputPath() + "/Dist/" + selectedPara + "/" + selectedRoI);
						if (distFilesAnRoI.exists()) {
							String[] distFilenamesAnRoI = distFilesAnRoI.list();
							comboBox_3.removeAllItems();
							for (String aDistFilename : distFilenamesAnRoI) {
								comboBox_3.addItem(aDistFilename);
							}
						} else {
							comboBox_3.removeAllItems();
						}
					}
				} else {
					if (selectedRoI != null) {
						File distFilesAnRoI = new File(dataset.getOutputPath() + "/Dist/" + selectedRoI);
						if (distFilesAnRoI.exists()) {
							String[] distFilenamesAnRoI = distFilesAnRoI.list();
							comboBox_3.removeAllItems();
							for (String aDistFilename : distFilenamesAnRoI) {
								comboBox_3.addItem(aDistFilename);
							}
						} else {
							comboBox_3.removeAllItems();
						}
					}
				}
			}
		});
		
		mntmViewFilePopup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedPara = (String)comboBox.getSelectedItem();
				String selectedRoI = (String)comboBox_1.getSelectedItem();
				String PSPara = (String)comboBox_2.getSelectedItem();
				
				if(dataset.getType().equals("GPS")) {
					String filePath = dataset.getOutputPath() + "/PatternSets/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + list.getSelectedValue() + "MiSTA.output";
					String[] cmd = {"write.exe", filePath};
					ProcessBuilder pb = new ProcessBuilder(cmd);
					try {
						pb.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(CompareUsersPanel.this, "An error occurred while opening the pattern set file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					String filePath = dataset.getOutputPath() + "/PatternSets/" + selectedRoI + "/" + PSPara + "/" + list.getSelectedValue() + "MiSTA.output";
					String[] cmd = {"write.exe", filePath};
					ProcessBuilder pb = new ProcessBuilder(cmd);
					try {
						pb.start();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(CompareUsersPanel.this, "An error occurred while opening the pattern set file of the user.", "Error", JOptionPane.ERROR_MESSAGE);
					}
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
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedPara = (String)comboBox.getSelectedItem();
				File[] RoIs = new File(dataset.getOutputPath() + "/PatternSets/" + selectedPara).listFiles();
				comboBox_1.removeAllItems();
				for(File anRoI : RoIs) {
					comboBox_1.addItem(anRoI.getName());
				}
				comboBox.setWide(true);				
			}
		});
		
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedRoI = (String)comboBox_1.getSelectedItem();
				if(dataset.getType().equals("GPS")) {
					if(selectedRoI != null) {
						String selectedPara = (String)comboBox.getSelectedItem();
						File[] PSParas = new File(dataset.getOutputPath() + "/PatternSets/" + selectedPara + "/" + selectedRoI).listFiles();
						comboBox_2.removeAllItems();
						for(File aPSPara : PSParas) {
							comboBox_2.addItem(aPSPara.getName());
						}
						comboBox_1.setWide(true);
					}
				} else {
					File[] PSParas = new File(dataset.getOutputPath() + "/PatternSets/" + selectedRoI).listFiles();
					comboBox_2.removeAllItems();
					for(File aPSPara : PSParas) {
						comboBox_2.addItem(aPSPara.getName());
					}
					comboBox_1.setWide(true);
				}
			}
		});
		
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String PSPara = (String)comboBox_2.getSelectedItem();
				if(PSPara != null) {
					String[] PSFilenames;
					if(dataset.getType().equals("GPS")) {
						String selectedPara = (String)comboBox.getSelectedItem();		
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						PSFilenames = new File(dataset.getOutputPath() + "/PatternSets/" + selectedPara + "/" + selectedRoI + "/" + PSPara).list();						
					} else {
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						PSFilenames = new File(dataset.getOutputPath() + "/PatternSets/" + selectedRoI + "/" + PSPara).list();
					}
					for(int i = 0; i < PSFilenames.length; i++) {
						PSFilenames[i] = PSFilenames[i].substring(0, 3);
					}
					list.setListData(PSFilenames);
					comboBox_2.setWide(true);
				}
			}
		});
		
		if(dataset.getType().equals("GPS")) {
			File[] SPParas = new File(dataset.getOutputPath() + "/PatternSets").listFiles();
			for(File aSPPara : SPParas) {
				comboBox.addItem(aSPPara.getName());
			}
			comboBox.setWide(true);
		} else {
			panel_1.setVisible(false);
			File[] SPParas = new File(dataset.getOutputPath() + "/PatternSets").listFiles();
			for(File aSPPara : SPParas) {
				comboBox_1.addItem(aSPPara.getName());
			}
			comboBox_1.setWide(true);
		}
		
		/*File distDir = new File(dataset.getOutputPath() + "/Dist");
		if(distDir.exists()) {
			String[] distFiles = distDir.list();
			for(String aDistFile : distFiles) {
				if(!aDistFile.equals("DistributionFilesStats.txt")) {
					comboBox_3.addItem(aDistFile);
				}
			}
		}*/
		
		rdbtnHausdorff.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					rdbtnWithoutSemantics.setEnabled(false);
					rdbtnWithSemantics.setSelected(true);
					lblDistanceThreshold.setVisible(false);
					rdbtnWithTime.setEnabled(false);
					rdbtnWithoutTime.setSelected(true);
					textField.setVisible(false);
					panel_14.setVisible(true);
					panel_15.setVisible(true);
				} else {
					lblDistanceThreshold.setVisible(true);
					textField.setVisible(true);
					rdbtnWithoutSemantics.setEnabled(true);
					rdbtnWithTime.setEnabled(true);
					buttonGroup_1.clearSelection();
					buttonGroup_2.clearSelection();
					panel_14.setVisible(false);
					panel_15.setVisible(false);
				}
			}
		});
		
		rdbtnCps.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					/*lblDistanceThreshold.setText("Support deletion threshold:");
					textField.setToolTipText("<html>\r\nSemantic patterns whose supports are below this threshold will be deleted before comparing two users.\r\n<br/>\r\nA typical value is 0.04 for the Geolife dataset, and 0.08 for the Yonsei dataset.\r\n<html/>");*/
					lblDistanceThreshold.setVisible(false);
					textField.setVisible(false);
					rdbtnWithTime.setEnabled(false);
					rdbtnWithoutTime.setSelected(true);
				} else {
					rdbtnWithTime.setEnabled(true);
					buttonGroup_1.clearSelection();
					buttonGroup_2.clearSelection();
				}
			}
		});
		
		rdbtnLcsImproved.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					lblDistanceThreshold.setText("Distance threshold:");
					textField.setToolTipText("<html>\r\nIt specifies the minimum distance between two probability distributions for them to \r\n<br/>\r\nbe LS-similar. A typical value is 0.5.\r\n<html/>");						
				} else {
					buttonGroup_1.clearSelection();
					buttonGroup_2.clearSelection();
				}
			}
		});
		
		rdbtnLcs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					/*rdbtnWithoutSemantics.setSelected(true);
					rdbtnWithSemantics.setEnabled(false);*/		
					lblDistanceThreshold.setText("Probability threshold:");
					textField.setToolTipText("<html>\r\nAn RoI has functionalities represented by semantic tags with probabilities greater than the threshold.\r\n<html/>");
				} else {					
					/*rdbtnWithSemantics.setEnabled(true);*/
					buttonGroup_1.clearSelection();
					buttonGroup_2.clearSelection();
				}
			}
		});
		
		rdbtnWithSemantics.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {		
				int state = e.getStateChange();
				if(rdbtnLcsImproved.isSelected()) {					
					if(state == ItemEvent.SELECTED) {
						lblDistanceThreshold.setVisible(true);
						textField.setVisible(true);
						rdbtnWithTime.setEnabled(false);
						rdbtnWithoutTime.setSelected(true);
						panel_19.setVisible(true);
					} else {
						rdbtnWithTime.setEnabled(true);
						panel_19.setVisible(false);
						panel_21.setVisible(false);
						buttonGroup_2.clearSelection();
					}
				} else if(rdbtnCps.isSelected()) {
					if(state == ItemEvent.SELECTED) {						
						panel_19.setVisible(true);
						panel_21.setVisible(true);
					} else {
						panel_19.setVisible(false);
						panel_21.setVisible(false);
					}
				} else if(rdbtnLcs.isSelected()) {
					if (state == ItemEvent.SELECTED) {
						panel_19.setVisible(true);
						rdbtnWithTime.setEnabled(false);
						rdbtnWithoutTime.setSelected(true);
						/*panel_21.setVisible(false);*/
					} else {
						panel_19.setVisible(false);
						rdbtnWithTime.setEnabled(true);
						buttonGroup_2.clearSelection();
					}
				} else {
					if(state == ItemEvent.SELECTED) {						
						panel_19.setVisible(true);
					} else {
						panel_19.setVisible(false);
						panel_21.setVisible(false);
					}
				}
			}
		});
		
		/*rdbtnSetAThreshold.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if(state == ItemEvent.SELECTED) {
					panel_23.setVisible(true);
				} else {
					panel_23.setVisible(false);
				}
			}
		});*/
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_17.removeAll();
				revalidate();
			}
		});

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> selectedList = list.getSelectedValuesList();
				if(selectedList.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please first choose some users.", "Choose users", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				ButtonModel method = buttonGroup.getSelection();
				if(method == null) {
					JOptionPane.showMessageDialog(null, "Please first choose a method.", "Choose a method", JOptionPane.INFORMATION_MESSAGE);
					return;
				}				
				
				if(method.getActionCommand().equals("LCS")) {
					ButtonModel semantics = buttonGroup_1.getSelection();
					if(semantics == null) {
						JOptionPane.showMessageDialog(null, "Please choose if semantics will be taken into account in the improved MTP based method.", "Choose a semantics variation", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					if(semantics.getActionCommand().equals("With semantics")) {
						String distFilename = (String)comboBox_3.getSelectedItem();
						if(distFilename == null) {
							JOptionPane.showMessageDialog(null, "Please choose a distribution file.\nIf there is no file listed, add one first.", "Choose a distribution file", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						double probabilityThreshold;
						try {
							probabilityThreshold = Double.parseDouble(textField.getText().trim());
							if(probabilityThreshold < 0.0) {
								JOptionPane.showMessageDialog(null, "The probability threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "The probability threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						String selectedPara = (String)comboBox.getSelectedItem();
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						String PSPara = (String)comboBox_2.getSelectedItem();

						StringBuilder sb = new StringBuilder();
						int numberUsers = selectedList.size();
						for(int i = 0; i < numberUsers; i++) {
							if(i != numberUsers - 1) {
								sb.append(selectedList.get(i) + "_");
							} else {
								sb.append(selectedList.get(i));
							}
						}
						
						if(dataset.getType().equals("GPS")) {
							File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
							if(supposedDir.exists()) {
								File supposedFile = new File(supposedDir + "/LCSWithSemanticsWithoutTime" + probabilityThreshold + ".csv");
								try {
									if(FileUtils.directoryContains(supposedDir, supposedFile)) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Reading file...");
										ResultReader reader = new ResultReader("Result of the method \"MTP based\" with semantics and without time when the threshold is " + probabilityThreshold, supposedFile, selectedList);
										reader.execute();
									} else {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, probabilityThreshold, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
										worker.execute();
									}
								} catch (IOException e1) {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, probabilityThreshold, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
									worker.execute();
								}
							} else {
								btnStart.setEnabled(false);
								lblCalculatingSimilarityValues.setText("Calculating similarity values...");
								LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, probabilityThreshold, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
								worker.execute();
							}
						} else {
							File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
							if(supposedDir.exists()) {
								File supposedFile = new File(supposedDir + "/LCSWithSemanticsWithoutTime" + probabilityThreshold + ".csv");
								try {
									if(FileUtils.directoryContains(supposedDir, supposedFile)) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Reading file...");
										ResultReader reader = new ResultReader("Result of the method \"MTP based\" with semantics and without time when the threshold is " + probabilityThreshold, supposedFile, selectedList);
										reader.execute();
									} else {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, probabilityThreshold, selectedRoI + "/" + PSPara, false);
										worker.execute();
									}
								} catch (IOException e1) {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, probabilityThreshold, selectedRoI + "/" + PSPara, false);
									worker.execute();
								}
							} else {
								btnStart.setEnabled(false);
								lblCalculatingSimilarityValues.setText("Calculating similarity values...");
								LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, probabilityThreshold, selectedRoI + "/" + PSPara, false);
								worker.execute();
							}
						}
					} else {
						ButtonModel time = buttonGroup_2.getSelection();
						if(time == null) {
							JOptionPane.showMessageDialog(null, "Please choose if time will be taken into account in the MTP based method.", "Choose a time variation", JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						String selectedPara = (String)comboBox.getSelectedItem();
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						String PSPara = (String)comboBox_2.getSelectedItem();

						StringBuilder sb = new StringBuilder();
						int numberUsers = selectedList.size();
						for(int i = 0; i < numberUsers; i++) {
							if(i != numberUsers - 1) {
								sb.append(selectedList.get(i) + "_");
							} else {
								sb.append(selectedList.get(i));
							}
						}

						if(time.getActionCommand().equals("With time")) {
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSWithoutSemanticsWithTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"MTP based\" without semantics and with time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, true, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSWithoutSemanticsWithTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"MTP based\" without semantics and with time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedRoI + "/" + PSPara, false);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedRoI + "/" + PSPara, false);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedRoI + "/" + PSPara, false);
									worker.execute();
								}
							}
						} else {
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSWithoutSemanticsOrTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"MTP based\" without semantics or time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, false);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSWithoutSemanticsOrTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"MTP based\" without semantics or time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedRoI + "/" + PSPara, false);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedRoI + "/" + PSPara, false);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedRoI + "/" + PSPara, false);
									worker.execute();
								}
							}
						}
					}
				}
				
				if(method.getActionCommand().equals("LCS improved")) {
					ButtonModel semantics = buttonGroup_1.getSelection();
					if(semantics == null) {
						JOptionPane.showMessageDialog(null, "Please choose if semantics will be taken into account in the improved MTP based method.", "Choose a semantics variation", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if(semantics.getActionCommand().equals("With semantics")) {
						String distFilename = (String)comboBox_3.getSelectedItem();
						if(distFilename == null) {
							JOptionPane.showMessageDialog(null, "Please choose a distribution file.\nIf there is no file listed, add one first.", "Choose a distribution file", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						double distanceThreshold;
						try {
							distanceThreshold = Double.parseDouble(textField.getText().trim());
							if(distanceThreshold < 0.0) {
								JOptionPane.showMessageDialog(null, "The distance threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "The distance threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						/*ButtonModel time = buttonGroup_2.getSelection();
						if(time == null) {
							JOptionPane.showMessageDialog(null, "Please choose the \"Without time\" option in the improved LCS method.", "Choose a time variation", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if(time.getActionCommand().equals("With time")) {
							JOptionPane.showMessageDialog(null, "The \"With time\" option in the improved LCS method when considering semantics is illegal.\nPlease choose the other option", "Choose a time variation", JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {*/
							String selectedPara = (String)comboBox.getSelectedItem();
							String selectedRoI = (String)comboBox_1.getSelectedItem();
							String PSPara = (String)comboBox_2.getSelectedItem();
							
							StringBuilder sb = new StringBuilder();
							int numberUsers = selectedList.size();
							for(int i = 0; i < numberUsers; i++) {
								if(i != numberUsers - 1) {
									sb.append(selectedList.get(i) + "_");
								} else {
									sb.append(selectedList.get(i));
								}
							}
							
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSImprovedWithSemanticsWithoutTime_" + distanceThreshold + ".csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"improved MTP based\" with semantics and without time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, distanceThreshold, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, distanceThreshold, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, distanceThreshold, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSImprovedWithSemanticsWithoutTime_" + distanceThreshold + ".csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"improved MTP based\" with semantics and without time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, distanceThreshold, selectedRoI + "/" + PSPara, true);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, distanceThreshold, selectedRoI + "/" + PSPara, true);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, distanceThreshold, selectedRoI + "/" + PSPara, true);
									worker.execute();
								}
							}							
						/*}*/ 
					} else {
						ButtonModel time = buttonGroup_2.getSelection();
						if(time == null) {
							JOptionPane.showMessageDialog(null, "Please choose if time will be taken into account in the improved MTP based method.", "Choose a time variation", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						String selectedPara = (String)comboBox.getSelectedItem();
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						String PSPara = (String)comboBox_2.getSelectedItem();
						
						StringBuilder sb = new StringBuilder();
						int numberUsers = selectedList.size();
						for(int i = 0; i < numberUsers; i++) {
							if(i != numberUsers - 1) {
								sb.append(selectedList.get(i) + "_");
							} else {
								sb.append(selectedList.get(i));
							}
						}
						
						if(time.getActionCommand().equals("With time")) {								
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSImprovedWithoutSemanticsWithTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"improved MTP based\" without semantics and with time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSImprovedWithoutSemanticsWithTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"improved MTP based\" without semantics and with time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedRoI + "/" + PSPara, true);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedRoI + "/" + PSPara, true);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, true, false, 0.0, selectedRoI + "/" + PSPara, true);
									worker.execute();
								}
							}
						} else {
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSImprovedWithoutSemanticsOrTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"improved MTP based\" without semantics or time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedPara + "/" + selectedRoI + "/" + PSPara, true);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/LCSImprovedWithoutSemanticsOrTime.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"improved MTP based\" without semantics or time", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedRoI + "/" + PSPara, true);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedRoI + "/" + PSPara, true);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									LCSComparator worker = new LCSComparator(dataset.getOutputPath(), null, selectedList, false, false, 0.0, selectedRoI + "/" + PSPara, true);
									worker.execute();
								}
							}
						}
					}
				}
				
				if(method.getActionCommand().equals("CPS")) {
					ButtonModel semantics = buttonGroup_1.getSelection();
					if(semantics == null) {
						JOptionPane.showMessageDialog(null, "Please choose if semantics will be taken into account in the CPS method.", "Choose a semantics variation", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if(semantics.getActionCommand().equals("With semantics")) {
						String distFilename = (String)comboBox_3.getSelectedItem();
						if(distFilename == null) {
							JOptionPane.showMessageDialog(null, "Please choose a distribution file.\nIf there is no file listed, add one first.", "Choose a distribution file", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						ButtonModel way = buttonGroup_5.getSelection();
						if (way == null) {							
							JOptionPane.showMessageDialog(null, "Please choose a way of setting thresholds.", "Choose a way", JOptionPane.INFORMATION_MESSAGE);
							return;							
						}
						double probThre = 0.0;
						/*if (way.getActionCommand().equals("Set a threshold on distribtuion probabilities")) {*/
							try {
								probThre = Double.parseDouble(textField_1.getText().trim());
								if(probThre < 0.0 || probThre > 1.0) {
									JOptionPane.showMessageDialog(null, "The probability threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							} catch(NumberFormatException ex) {
								JOptionPane.showMessageDialog(null, "The probability threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						/*}*/
						
						/*double supportDeletionThreshold;
						try {
							supportDeletionThreshold = Double.parseDouble(textField.getText().trim());
							if(supportDeletionThreshold < 0.0 || supportDeletionThreshold > 1.0) {
								JOptionPane.showMessageDialog(null, "The support deletion threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "The support deletion threshold should be a number between 0 and 1.", "Illegal threshold", JOptionPane.INFORMATION_MESSAGE);
							return;
						}*/
						
						String selectedPara = (String)comboBox.getSelectedItem();
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						String PSPara = (String)comboBox_2.getSelectedItem();
						String[] threExtractingPatterns = PSPara.split("_");	
						double threSup = Double.parseDouble(threExtractingPatterns[0]);
						
						StringBuilder sb = new StringBuilder();
						int numberUsers = selectedList.size();
						for(int i = 0; i < numberUsers; i++) {
							if(i != numberUsers - 1) {
								sb.append(selectedList.get(i) + "_");
							} else {
								sb.append(selectedList.get(i));
							}
						}
						
						if (way.getActionCommand().equals("Set a threshold on distribtuion probabilities")) {
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/CPSWithSemanticsThresholdOnProbabilities_" + probThre + ".csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"CPS\" with semantics setting a threshold on distribtuion probabilities", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, selectedPara + "/" + selectedRoI + "/" + PSPara, true, probThre, threSup);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, selectedPara + "/" + selectedRoI + "/" + PSPara, true, probThre, threSup);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, selectedPara + "/" + selectedRoI + "/" + PSPara, true, probThre, threSup);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/CPSWithSemanticsThresholdOnProbabilities_" + probThre + ".csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"CPS\" with semantics setting a threshold on distribtuion probabilities", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, selectedRoI + "/" + PSPara, true, probThre, threSup);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, selectedRoI + "/" + PSPara, true, probThre, threSup);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, selectedRoI + "/" + PSPara, true, probThre, threSup);
									worker.execute();
								}
							}
						} else {
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/CPSWithSemanticsThresholdOnSemanticPatterns_" + probThre + ".csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"CPS\" with semantics setting thresholds on supports of semantic tag patterns", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, selectedPara + "/" + selectedRoI + "/" + PSPara, false, probThre, threSup);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, selectedPara + "/" + selectedRoI + "/" + PSPara, false, probThre, threSup);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, selectedPara + "/" + selectedRoI + "/" + PSPara, false, probThre, threSup);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/CPSWithSemanticsThresholdOnSemanticPatterns_" + probThre + ".csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"CPS\" with semantics setting thresholds on supports of semantic tag patterns", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, selectedRoI + "/" + PSPara, false, probThre, threSup);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, selectedRoI + "/" + PSPara, false, probThre, threSup);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									CPSComparator worker = new CPSComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, selectedRoI + "/" + PSPara, false, probThre, threSup);
									worker.execute();
								}
							}
						}
					} else {
						String selectedPara = (String)comboBox.getSelectedItem();
						String selectedRoI = (String)comboBox_1.getSelectedItem();
						String PSPara = (String)comboBox_2.getSelectedItem();
						
						StringBuilder sb = new StringBuilder();
						int numberUsers = selectedList.size();
						for(int i = 0; i < numberUsers; i++) {
							if(i != numberUsers - 1) {
								sb.append(selectedList.get(i) + "_");
							} else {
								sb.append(selectedList.get(i));
							}
						}
						
						if(dataset.getType().equals("GPS")) {
							File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());							
							if(supposedDir.exists()) {
								File supposedFile = new File(supposedDir + "/CPSWithoutSemantics.csv");
								try {
									if(FileUtils.directoryContains(supposedDir, supposedFile)) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Reading file...");
										ResultReader reader = new ResultReader("Result of the method \"CPS\" without semantics", supposedFile, selectedList);
										reader.execute();
									} else {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										CPSComparator worker = new CPSComparator(dataset.getOutputPath(), null, selectedList, false, selectedPara + "/" + selectedRoI + "/" + PSPara, true, 0.0, 0.0);
										worker.execute();
									}
								} catch (IOException e1) {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									CPSComparator worker = new CPSComparator(dataset.getOutputPath(), null, selectedList, false, selectedPara + "/" + selectedRoI + "/" + PSPara, true, 0.0, 0.0);
									worker.execute();
								}
							} else {
								btnStart.setEnabled(false);
								lblCalculatingSimilarityValues.setText("Calculating similarity values...");
								CPSComparator worker = new CPSComparator(dataset.getOutputPath(), null, selectedList, false, selectedPara + "/" + selectedRoI + "/" + PSPara, true, 0.0, 0.0);
								worker.execute();
							}
						} else {
							File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
							if(supposedDir.exists()) {
								File supposedFile = new File(supposedDir + "/CPSWithoutSemantics.csv");
								try {
									if(FileUtils.directoryContains(supposedDir, supposedFile)) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Reading file...");
										ResultReader reader = new ResultReader("Result of the method \"CPS\" without semantics", supposedFile, selectedList);
										reader.execute();
									} else {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										CPSComparator worker = new CPSComparator(dataset.getOutputPath(), null, selectedList, false, selectedRoI + "/" + PSPara, true, 0.0, 0.0);
										worker.execute();
									}
								} catch (IOException e1) {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									CPSComparator worker = new CPSComparator(dataset.getOutputPath(), null, selectedList, false, selectedRoI + "/" + PSPara, true, 0.0, 0.0);
									worker.execute();
								}
							} else {
								btnStart.setEnabled(false);
								lblCalculatingSimilarityValues.setText("Calculating similarity values...");
								CPSComparator worker = new CPSComparator(dataset.getOutputPath(), null, selectedList, false, selectedRoI + "/" + PSPara, true, 0.0, 0.0);
								worker.execute();
							}
						}						
					}
				}
				
				if(method.getActionCommand().equals("Hausdorff")) {
					String distFilename = (String)comboBox_3.getSelectedItem();
					if(distFilename == null) {
						JOptionPane.showMessageDialog(null, "Please choose a distribution file.\nIf there is no file listed, add one first.", "Choose a distribution file", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					ButtonModel way = buttonGroup_3.getSelection();
					if(way == null) {
						JOptionPane.showMessageDialog(null, "Please choose a way in the Hausdorff method.", "Choose a way", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					ButtonModel distDistanceMeasure = buttonGroup_4.getSelection();
					if(distDistanceMeasure == null) {
						JOptionPane.showMessageDialog(null, "Please choose a distribution distance measurement in the Hausdorff method.", "Choose a distribution distance measurement", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					if(way.getActionCommand().equals("Adjust patterns' lengths")) {
						if(distDistanceMeasure.getActionCommand().equals("Hellinger")) {
							String selectedPara = (String)comboBox.getSelectedItem();
							String selectedRoI = (String)comboBox_1.getSelectedItem();
							String PSPara = (String)comboBox_2.getSelectedItem();
							
							StringBuilder sb = new StringBuilder();
							int numberUsers = selectedList.size();
							for(int i = 0; i < numberUsers; i++) {
								if(i != numberUsers - 1) {
									sb.append(selectedList.get(i) + "_");
								} else {
									sb.append(selectedList.get(i));
								}
							}
							
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffAdjustLenHellinger.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" adjusting patterns' lengths and using Hellinger", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, true, selectedPara + "/" + selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, true, selectedPara + "/" + selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, true, selectedPara + "/" + selectedRoI + "/" + PSPara);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffAdjustLenHellinger.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" adjusting patterns' lengths and using Hellinger", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, true, selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, true, selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, true, selectedRoI + "/" + PSPara);
									worker.execute();
								}
							}
						} else {
							String selectedPara = (String)comboBox.getSelectedItem();
							String selectedRoI = (String)comboBox_1.getSelectedItem();
							String PSPara = (String)comboBox_2.getSelectedItem();
							
							StringBuilder sb = new StringBuilder();
							int numberUsers = selectedList.size();
							for(int i = 0; i < numberUsers; i++) {
								if(i != numberUsers - 1) {
									sb.append(selectedList.get(i) + "_");
								} else {
									sb.append(selectedList.get(i));
								}
							}
							
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffAdjustLenTV.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" adjusting patterns' lengths and using total variance", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, false, selectedPara + "/" + selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, false, selectedPara + "/" + selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, true, false, selectedPara + "/" + selectedRoI + "/" + PSPara);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffAdjustLenTV.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" adjusting patterns' lengths and using total variance", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, false, selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, false, selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, true, false, selectedRoI + "/" + PSPara);
									worker.execute();
								}
							}
						}
					} else {
						if(distDistanceMeasure.getActionCommand().equals("Hellinger")) {
							String selectedPara = (String)comboBox.getSelectedItem();
							String selectedRoI = (String)comboBox_1.getSelectedItem();
							String PSPara = (String)comboBox_2.getSelectedItem();
							
							StringBuilder sb = new StringBuilder();
							int numberUsers = selectedList.size();
							for(int i = 0; i < numberUsers; i++) {
								if(i != numberUsers - 1) {
									sb.append(selectedList.get(i) + "_");
								} else {
									sb.append(selectedList.get(i));
								}
							}
							
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffClassifyByLenHellinger.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" classifying by pattern length and using Hellinger", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, selectedPara + "/" + selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, selectedPara + "/" + selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, true, selectedPara + "/" + selectedRoI + "/" + PSPara);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffClassifyByLenHellinger.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" classifying by pattern length and using Hellinger", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, true, selectedRoI + "/" + PSPara);
									worker.execute();
								}
							}
						} else {
							String selectedPara = (String)comboBox.getSelectedItem();
							String selectedRoI = (String)comboBox_1.getSelectedItem();
							String PSPara = (String)comboBox_2.getSelectedItem();
							
							StringBuilder sb = new StringBuilder();
							int numberUsers = selectedList.size();
							for(int i = 0; i < numberUsers; i++) {
								if(i != numberUsers - 1) {
									sb.append(selectedList.get(i) + "_");
								} else {
									sb.append(selectedList.get(i));
								}
							}
							
							if(dataset.getType().equals("GPS")) {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedPara + "/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffClassifyByLenTV.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" classifying by pattern length and using total variance", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, false, selectedPara + "/" + selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, false, selectedPara + "/" + selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedPara + "\\" + selectedRoI + "\\" + distFilename, selectedList, false, false, selectedPara + "/" + selectedRoI + "/" + PSPara);
									worker.execute();
								}
							} else {
								File supposedDir = new File(dataset.getOutputPath() + "/ComparisonResults/" + selectedRoI + "/" + PSPara + "/" + sb.toString());
								if(supposedDir.exists()) {
									File supposedFile = new File(supposedDir + "/HausdorffClassifyByLenTV.csv");
									try {
										if(FileUtils.directoryContains(supposedDir, supposedFile)) {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Reading file...");
											ResultReader reader = new ResultReader("Result of the method \"Hausdorff\" classifying by pattern length and using total variance", supposedFile, selectedList);
											reader.execute();
										} else {
											btnStart.setEnabled(false);
											lblCalculatingSimilarityValues.setText("Calculating similarity values...");
											HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, false, selectedRoI + "/" + PSPara);
											worker.execute();
										}
									} catch (IOException e1) {
										btnStart.setEnabled(false);
										lblCalculatingSimilarityValues.setText("Calculating similarity values...");
										HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, false, selectedRoI + "/" + PSPara);
										worker.execute();
									}
								} else {
									btnStart.setEnabled(false);
									lblCalculatingSimilarityValues.setText("Calculating similarity values...");
									HausdorffComparator worker = new HausdorffComparator(dataset.getOutputPath(), selectedRoI + "\\" + distFilename, selectedList, false, false, selectedRoI + "/" + PSPara);
									worker.execute();
								}
							}
						}
					}
				}
			}
		});
	}
	
	private class ResultReader extends SwingWorker<ComparisonResultTableModel, Void> {
		private String method;
		private File resultFile;
		private List<String> selectedUsers;
		
		public ResultReader(String aMethod, File aFile, List<String> aUserList) {
			method = aMethod;
			resultFile = aFile;
			selectedUsers = aUserList;
		}

		@Override
		protected ComparisonResultTableModel doInBackground() throws Exception {
			int userNumber = selectedUsers.size();
			double[][] simMatrix = new double[userNumber][userNumber];
			try {				
				BufferedReader bf = new BufferedReader(new FileReader(resultFile));
				String aLine;
				int i = 0;
				while((aLine = bf.readLine()) != null) {
					if(i == 0) {
						i++;
						continue;
					} else {
						String[] fields = aLine.split(",");
						for(int j = 0; j < userNumber; j++) {
							simMatrix[i - 1][j] = Double.parseDouble(fields[j + 1]);
						}
						i++;
					}
				}
				bf.close();
			} catch(Exception e) {
				return null;
			}
			
			return new ComparisonResultTableModel(selectedUsers, simMatrix);
		}
		
		public void done() {
			try {
				ComparisonResultTableModel model = get();
				btnStart.setEnabled(true);
				lblCalculatingSimilarityValues.setText("");
				if(model != null) {
					JLabel label = new JLabel(method);
					JPanel panel2 = new JPanel();
					panel2.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
					panel2.add(label);
					
					JPanel panel = new JPanel(new BorderLayout());					
					JTable table = new JTable(model);
					table.setDefaultRenderer(Number.class, new ComparisonResultDefaultTableCellRenderer());
					panel.add(table.getTableHeader(), BorderLayout.NORTH);
					panel.add(table, BorderLayout.CENTER);
					panel_17.add(panel2);
					panel_17.add(panel);
					revalidate();
				} else {
					JOptionPane.showMessageDialog(CompareUsersPanel.this, "An error occurred while reading the file which stores the comparison results.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch(InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(CompareUsersPanel.this, "Unknown error: cannot get the result of the operation of reading the file which stores the comparison results.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	private class LCSComparator extends SwingWorker<ComparisonResultTableModel, Void> {
		private String outputDirOfDataset;
		private String distFilename;
		private List<String> selectedUsers;
		private boolean time;
		private boolean semantics;
		private double threshold;
		private String dirsPath;
		private boolean improved;
		
		public LCSComparator(String aDirPath, String aFilename, List<String> aUserList, boolean ifTime, boolean ifSemantics, double aThreshold, String aPath, boolean ifImproved) {
			outputDirOfDataset = aDirPath;
			distFilename = aFilename;
			selectedUsers = aUserList;
			time = ifTime;
			semantics = ifSemantics;
			threshold = aThreshold;
			dirsPath = aPath;
			improved = ifImproved;
		}
		
		@Override
		protected ComparisonResultTableModel doInBackground() throws Exception {
			try {
				double[][] simMatrix = new UserComparisonLCS().compareUsers(outputDirOfDataset, outputDirOfDataset + "/Dist/" + distFilename, selectedUsers, time, semantics, threshold, dirsPath, improved);
				return new ComparisonResultTableModel(selectedUsers, simMatrix);
			} catch(Exception e) {
				return null;
			}			
		}
		
		public void done() {
			try {
				ComparisonResultTableModel model = get();
				btnStart.setEnabled(true);
				lblCalculatingSimilarityValues.setText("");
				
				if(model != null) {
					String labelText;
					if (improved) {
						if(semantics) {
							if(time) {
								labelText = "Result of the method \"improved MTP\" with semantics and time when the distance threshold is " + threshold;
							} else {
								labelText = "Result of the method \"improved MTP\" with semantics and without time when the distance threshold is " + threshold;
							}
						} else {
							if(time) {
								labelText = "Result of the method \"improved MTP\" without semantics and with time";
							} else {
								labelText = "Result of the method \"improved MTP\" without semantics or time";
							}
						}
					} else {
						if(semantics) {
							if(time) {
								labelText = "Result of the method \"MTP\" with semantics and time when the probability threshold is " + threshold;
							} else {
								labelText = "Result of the method \"MTP\" with semantics and without time when the probability threshold is " + threshold;
							}
						} else {
							if(time) {
								labelText = "Result of the method \"MTP\" without semantics and with time";
							} else {
								labelText = "Result of the method \"MTP\" without semantics or time";
							}
						}
					}
					JLabel label = new JLabel(labelText);
					JPanel panel2 = new JPanel();
					panel2.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
					panel2.add(label);
					
					JPanel panel = new JPanel(new BorderLayout());					
					JTable table = new JTable(model);
					table.setDefaultRenderer(Number.class, new ComparisonResultDefaultTableCellRenderer());
					panel.add(table.getTableHeader(), BorderLayout.NORTH);
					panel.add(table, BorderLayout.CENTER);
					panel_17.add(panel2);
					panel_17.add(panel);
					revalidate();
				} else {
					JOptionPane.showMessageDialog(CompareUsersPanel.this, "The distribution file you provided is illegal.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(CompareUsersPanel.this, "Unknown error: cannot get the result of the operation of comparing users.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class CPSComparator extends SwingWorker<ComparisonResultTableModel, Void> {
		private String outputDirOfDataset;
		private String distFilename;
		private List<String> selectedUsers;
		private boolean semantics;
		private String dirsPath;
		private boolean ifThresholdOnProb;
		private double probThre;
		private double threSup;
		
		public CPSComparator(String aDirPath, String aFilename, List<String> aUserList, boolean ifSemantics, String aPath, boolean aBool, double aThre, double aThreSup) {
			outputDirOfDataset = aDirPath;
			distFilename = aFilename;
			selectedUsers = aUserList;
			semantics = ifSemantics;
			dirsPath = aPath;
			ifThresholdOnProb = aBool;
			probThre = aThre;
			threSup = aThreSup;
		}

		@Override
		protected ComparisonResultTableModel doInBackground() throws Exception {
			try {
				double[][] simMatrix = new UserComparisonCPS().compareUsers(outputDirOfDataset, outputDirOfDataset + "/Dist/" + distFilename, selectedUsers, semantics, dirsPath, ifThresholdOnProb, probThre, threSup);
				return new ComparisonResultTableModel(selectedUsers, simMatrix);
			} catch(Exception e) {
				return null;
			}
		}
		
		public void done() {
			try {
				ComparisonResultTableModel model = get();
				btnStart.setEnabled(true);
				lblCalculatingSimilarityValues.setText("");
				
				if(model != null) {
					String labelText;
					if(semantics) {
						if (ifThresholdOnProb) {
							labelText = "Result of the method \"CPS\" with semantics setting a threshold on distribtuion probabilities";
						} else {
							labelText = "Result of the method \"CPS\" with semantics setting thresholds on supports of semantic tag patterns";
						}
					} else {
						labelText = "Result of the method \"CPS\" without semantics";
					}
					JLabel label = new JLabel(labelText);
					JPanel panel2 = new JPanel();
					panel2.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
					panel2.add(label);
					
					JPanel panel = new JPanel(new BorderLayout());					
					JTable table = new JTable(model);
					table.setDefaultRenderer(Number.class, new ComparisonResultDefaultTableCellRenderer());
					panel.add(table.getTableHeader(), BorderLayout.NORTH);
					panel.add(table, BorderLayout.CENTER);
					panel_17.add(panel2);
					panel_17.add(panel);
					revalidate();
				} else {
					JOptionPane.showMessageDialog(CompareUsersPanel.this, "The distribution file you provided is illegal.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(CompareUsersPanel.this, "Unknown error: cannot get the result of the operation of comparing users.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class HausdorffComparator extends SwingWorker<ComparisonResultTableModel, Void> {
		private String outputDirOfDataset;
		private String distFilename;
		private List<String> selectedUsers;
		private boolean adjustLen;
		private boolean hellinger;
		private String dirsPath;
		
		public HausdorffComparator(String aDirPath, String aFilename, List<String> aUserList, boolean ifAdjustLen, boolean ifHellinger, String aPath) {
			outputDirOfDataset = aDirPath;
			distFilename = aFilename;
			selectedUsers = aUserList;
			adjustLen = ifAdjustLen;
			hellinger = ifHellinger;
			dirsPath = aPath;
		}

		@Override
		protected ComparisonResultTableModel doInBackground() throws Exception {
			try {
				double[][] simMatrix = UserComparisonHausdorff.compareUsers(outputDirOfDataset, outputDirOfDataset + "/Dist/" + distFilename, selectedUsers, adjustLen, hellinger, dirsPath);
				return new ComparisonResultTableModel(selectedUsers, simMatrix);
			} catch(Exception e) {
				return null;
			}
		}
		
		public void done() {
			try {
				ComparisonResultTableModel model = get();
				btnStart.setEnabled(true);
				lblCalculatingSimilarityValues.setText("");
				
				if(model != null) {
					String labelText;
					if(adjustLen) {
						if(hellinger) {
							labelText = "Result of the method \"Hausdorff\" adjusting patterns' lengths and using Hellinger";
						} else {
							labelText = "Result of the method \"Hausdorff\" adjusting patterns' lengths and using total variance";
						}
					} else {
						if(hellinger) {
							labelText = "Result of the method \"Hausdorff\" classifying by pattern length and using Hellinger";
						} else {
							labelText = "Result of the method \"Hausdorff\" classifying by pattern length and using total variance";
						}
					}
					JLabel label = new JLabel(labelText);
					JPanel panel2 = new JPanel();
					panel2.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
					panel2.add(label);
					
					JPanel panel = new JPanel(new BorderLayout());					
					JTable table = new JTable(model);
					table.setDefaultRenderer(Number.class, new ComparisonResultDefaultTableCellRenderer());
					panel.add(table.getTableHeader(), BorderLayout.NORTH);
					panel.add(table, BorderLayout.CENTER);
					panel_17.add(panel2);
					panel_17.add(panel);
					panel.scrollRectToVisible(panel.getBounds());
					revalidate();
				} else {
					JOptionPane.showMessageDialog(CompareUsersPanel.this, "The distribution file you provided is illegal.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException e) {
				JOptionPane.showMessageDialog(CompareUsersPanel.this, "Unknown error: cannot get the result of the operation of comparing users.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
