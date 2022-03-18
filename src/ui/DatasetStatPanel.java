package ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import java.awt.Insets;
import java.io.File;

@SuppressWarnings("serial")
public class DatasetStatPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DatasetStatPanel(Dataset dataset) {
		String name = dataset.getName();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dataset \"" + name + "\" information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[] {25, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 52, 0, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		panel_1.add(lblName, gbc_lblName);
		
		JLabel lblNewLabel = new JLabel(name);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[] {25, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblType = new JLabel("Type:");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.insets = new Insets(0, 55, 0, 5);
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 0;
		panel_2.add(lblType, gbc_lblType);
		
		JLabel lblNewLabel_1 = new JLabel(dataset.getType());
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0};
		gbl_panel_3.rowHeights = new int[] {25, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblNumberOfUsers = new JLabel("Number of users:");
		GridBagConstraints gbc_lblNumberOfUsers = new GridBagConstraints();
		gbc_lblNumberOfUsers.insets = new Insets(0, 0, 0, 5);
		gbc_lblNumberOfUsers.gridx = 0;
		gbc_lblNumberOfUsers.gridy = 0;
		panel_3.add(lblNumberOfUsers, gbc_lblNumberOfUsers);
		
		JLabel lblNewLabel_2 = new JLabel("" + (new File(dataset.getInputPath())).list().length);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 0;
		panel_3.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 0};
		gbl_panel_4.rowHeights = new int[] {25, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblInputDirectory = new JLabel("Input directory:");
		GridBagConstraints gbc_lblInputDirectory = new GridBagConstraints();
		gbc_lblInputDirectory.insets = new Insets(0, 7, 0, 5);
		gbc_lblInputDirectory.gridx = 0;
		gbc_lblInputDirectory.gridy = 0;
		panel_4.add(lblInputDirectory, gbc_lblInputDirectory);
		
		JLabel lblNewLabel_3 = new JLabel(dataset.getInputPath());
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 0;
		panel_4.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0};
		gbl_panel_5.rowHeights = new int[] {25, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblOutputDirectory = new JLabel("Output directory:");
		GridBagConstraints gbc_lblOutputDirectory = new GridBagConstraints();
		gbc_lblOutputDirectory.insets = new Insets(0, 0, 0, 5);
		gbc_lblOutputDirectory.gridx = 0;
		gbc_lblOutputDirectory.gridy = 0;
		panel_5.add(lblOutputDirectory, gbc_lblOutputDirectory);
		
		JLabel lblNewLabel_4 = new JLabel(dataset.getOutputPath());
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 0;
		panel_5.add(lblNewLabel_4, gbc_lblNewLabel_4);

	}

}
