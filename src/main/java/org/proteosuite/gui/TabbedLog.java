package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.listener.KeyListenerSearch;

/**
 * 
 * @author Andrew Collins
 */
public class TabbedLog extends JTabbedPane {
	private JTextArea jtaLog = new JTextArea();

	public TabbedLog(JTable jtRawData) {
		jtaLog.setColumns(20);
		jtaLog.setFont(new Font("Tahoma", 0, 11)); // NOI18N
		jtaLog.setRows(5);

		JScrollPane jspLog = new JScrollPane();
		jspLog.setViewportView(jtaLog);

		addTab("Log", jspLog);
		addTab("Raw Data", getRawDataValuesPanel(jtRawData));
		addTab("Template 1", getTemplate1());
		addTab("Template 2", getTemplate2());
		addTab("Synth Array", getSynthArrayPanel());
		addTab("Resamp Array", getResampleArray());

		Icon logIcon = new ImageIcon(getClass().getClassLoader().getResource(
				"images/log.gif"));
		JLabel jlLogIcon = new JLabel("Log  ", logIcon, SwingConstants.RIGHT);
		jlLogIcon.setIconTextGap(5);
		setTabComponentAt(0, jlLogIcon);
		
		Icon rawDataIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/raw_data.gif"));
		JLabel jlRawDataIcon = new JLabel("Raw data", rawDataIcon,
				SwingConstants.RIGHT);
		jlRawDataIcon.setIconTextGap(5);
		setTabComponentAt(1, jlRawDataIcon);

		// Setting default selection (Viewers)
		setSelectedIndex(0);
	}

	public void reset() {
		resetLog();
	}

	public void appendLog(String entry) {
		jtaLog.append(entry);
	}

	public void resetLog() {
		jtaLog.setText("");
	}

	public void setLog(String entry) {
		jtaLog.setText(entry);
	}

	private JPanel getRawDataValuesPanel(JTable jtRawData) {
		final JTextField txtMSIndex = new JTextField(10);
		txtMSIndex.setToolTipText("Enter the index");
		txtMSIndex.addKeyListener(new KeyListenerSearch(0, jtRawData, true));

		final JTextField txtMSMz = new JTextField(10);
		txtMSMz.setToolTipText("Enter the m/z value ");
		txtMSMz.addKeyListener(new KeyListenerSearch(1, jtRawData, true));

		final JLabel lblSearch = new JLabel("Search:");
		lblSearch.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		JLabel lblIndex = new JLabel("Index:");
		JLabel lblMassCharge = new JLabel("m/z:");

		JPanel jpRawDataValuesMenu = new JPanel();
		jpRawDataValuesMenu.setMinimumSize(new Dimension(0, 50));
		jpRawDataValuesMenu.setPreferredSize(new Dimension(462, 50));
		jpRawDataValuesMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpRawDataValuesMenu.add(lblSearch);
		jpRawDataValuesMenu.add(lblIndex);
		jpRawDataValuesMenu.add(txtMSIndex);
		jpRawDataValuesMenu.add(lblMassCharge);
		jpRawDataValuesMenu.add(txtMSMz);
		
		JSplitPane jspRawDataValues = new JSplitPane();
		jspRawDataValues.setDividerLocation(40);
		jspRawDataValues.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspRawDataValues.setLeftComponent(jpRawDataValuesMenu);

		JScrollPane jspRawData = new JScrollPane();
		jspRawData.setViewportView(jtRawData);

		jspRawDataValues.setBottomComponent(jspRawData);

		JPanel jpRawDataValues = new JPanel();
		jpRawDataValues.setLayout(new BorderLayout());
		jpRawDataValues.add(jspRawDataValues);

		return jpRawDataValues;
	}

	private JPanel getTemplate1() {
		final JTable jtTemplate1 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		jtTemplate1.setModel(new DefaultTableModel(new String[][] {

		},
				new String[] { "m/z Index", "Scan Index", "Quant",
						"Template2 Index" }));
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setViewportView(jtTemplate1);

		JPanel jPanel1 = new JPanel();

		jPanel1.setLayout(new BorderLayout());
		jPanel1.add(jScrollPane1);

		return jPanel1;
	}

	private JPanel getTemplate2() {
		final JTable jtTemplate2 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		jtTemplate2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Template Index", "x", "y", "i" }));
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jtTemplate2);

		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout(new BorderLayout());
		jPanel2.add(jScrollPane2);

		return jPanel2;
	}

	private JPanel getSynthArrayPanel() {
		JPanel jPanel3 = new JPanel();

		final JTable jTable1 = new JTable();
		jTable1.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane3 = new JScrollPane();
		jScrollPane3.setViewportView(jTable1);

		jPanel3.setLayout(new BorderLayout());
		jPanel3.add(jScrollPane3);

		return jPanel3;
	}

	private JPanel getResampleArray() {

		final JTable jTable2 = new JTable();
		jTable2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane7 = new JScrollPane();
		jScrollPane7.setViewportView(jTable2);

		JPanel jpPanel4 = new JPanel();
		jpPanel4.setLayout(new BorderLayout());
		jpPanel4.add(jScrollPane7);
		

		return jpPanel4;
	}
}
