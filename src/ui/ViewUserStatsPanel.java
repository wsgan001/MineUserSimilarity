package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class ViewUserStatsPanel extends JPanel {
	private Dataset selectedDataset;
	private JTable table;
	private JTable table_1;
	
	/**
	 * Create the panel.
	 */
	public ViewUserStatsPanel(Dataset aDataset) {
		selectedDataset = aDataset;
		
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JButton btnViewStats = new JButton("View stats");		
		panel_2.add(btnViewStats);
		
		JButton btnClear = new JButton("Clear");		
		panel_2.add(btnClear);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		
		final JPanel panel_4 = new JPanel();
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JLabel lblGpsPointStats = new JLabel("GPS point stats");
		lblGpsPointStats.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		lblGpsPointStats.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblGpsPointStats, BorderLayout.NORTH);
		
		final JPanel panel_5 = new JPanel();
		
		panel_5.setLayout(new BorderLayout(0, 0));
		
		panel_3.add(panel_5);
		panel_3.add(panel_4);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_5.add(scrollPane_2, BorderLayout.CENTER);
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.NORTH);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel_7.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel_6.add(panel_7);
		
		JLabel lblStayPointStats = new JLabel("Stay point stats");
		panel_7.add(lblStayPointStats);
		lblStayPointStats.setBorder(null);
		lblStayPointStats.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_8.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panel_6.add(panel_8);
		
		JLabel lblStayPointPara = new JLabel("Stay point para setting: ");
		panel_8.add(lblStayPointPara);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		panel_8.add(comboBox);		
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUserList = new JLabel(" User list:");
		lblUserList.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUserList, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		final JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		
		splitPane.setLeftComponent(panel);
		splitPane.setRightComponent(panel_1);
		splitPane.setDividerLocation(70);

		String[] files = new File(selectedDataset.getInputPath()).list();
		if(selectedDataset.getType().equals("GPS")) {
			list.setListData(files);
			File GPSAndSPStatFolder = new File(selectedDataset.getOutputPath() + "/Stats/SourceDataAndStayPoints");
			String[] statFiles = GPSAndSPStatFolder.list();
			if(statFiles.length > 1) {
				for(String aStatFile : statFiles) {
					if(aStatFile.contains("StayPoints")) {
						comboBox.addItem(aStatFile);
					}
				}
			}
		} else {			
			for(int i = 0; i < files.length; i++) {
				files[i] = files[i].substring(0, 3);
			}
			list.setListData(files);
			/*panel_4.setVisible(false);*/
			panel_3.remove(panel_4);
			panel_8.setVisible(false);			
		}
		
		btnViewStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> selectedList = list.getSelectedValuesList();
				if(selectedList.size() == 0) {
					JOptionPane.showMessageDialog(null, "Please first choose some users.", "Choose users", JOptionPane.INFORMATION_MESSAGE);
					return;
				}			
				
				TableModelConstructor worker = new TableModelConstructor(selectedList, comboBox.getSelectedItem());
				worker.execute();
			}
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_1.setModel(new DefaultTableModel());
				table.setModel(new DefaultTableModel());
			}
		});
	}
	
	private class TableModelConstructor extends SwingWorker<Void, Void> {
		private List<String> selectedList;
		private StatTableModel GPStm;
		private StatTableModel SPtm;
		private Object selectedParaSetting;
		
		public TableModelConstructor(List<String> aList, Object aParaSetting) {
			selectedList = aList;
			GPStm = null;
			SPtm = null;
			selectedParaSetting = aParaSetting;
		}

		@Override
		protected Void doInBackground() throws Exception {
			if(selectedDataset.getType().equals("GPS")) {
				GPStm = new StatTableModel(selectedList, selectedDataset, true, null);
				if(selectedParaSetting != null) {
					SPtm = new StatTableModel(selectedList, selectedDataset, false, (String)selectedParaSetting);
				}
			} else {
				SPtm = new StatTableModel(selectedList, selectedDataset, false, (String)selectedParaSetting);
			}
			return null;
		}		
		
		public void done() {
			if(GPStm != null) {
				table.setModel(GPStm);
			}
			if(SPtm != null && SPtm.getRowCount() != 0) {
				table_1.setModel(SPtm);	
			} else {
				table_1.setModel(new DefaultTableModel());	
			}
		}
	}
}
