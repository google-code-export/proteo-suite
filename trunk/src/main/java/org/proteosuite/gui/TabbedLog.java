package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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

		addTab("Log", new ImageIcon(getClass().getClassLoader().getResource(
				"images/log.gif")), jspLog);
		addTab("Raw Data", new ImageIcon(getClass().getClassLoader()
				.getResource("images/raw_data.gif")), getRawDataValuesPanel(jtRawData));
		addTab("Template 1", getTemplate1());
		addTab("Template 2", getTemplate2());
		addTab("Synth Array", getSynthArrayPanel());
		addTab("Resamp Array", getResampleArray());
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

		JPanel jpRawDataValuesMenu = new JPanel();
		jpRawDataValuesMenu.setMinimumSize(new Dimension(0, 50));
		jpRawDataValuesMenu.setPreferredSize(new Dimension(462, 50));
		jpRawDataValuesMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpRawDataValuesMenu.add(lblSearch);
		jpRawDataValuesMenu.add(new JLabel("Index:"));
		jpRawDataValuesMenu.add(txtMSIndex);
		jpRawDataValuesMenu.add(new JLabel("m/z:"));
		jpRawDataValuesMenu.add(txtMSMz);

		JScrollPane jspRawData = new JScrollPane();
		jspRawData.setViewportView(jtRawData);

		JPanel jpRawDataValues = new JPanel();
		jpRawDataValues.setLayout(new BorderLayout());
		jpRawDataValues.add(jpRawDataValuesMenu, BorderLayout.PAGE_START);
		jpRawDataValues.add(jspRawData, BorderLayout.CENTER);

		return jpRawDataValues;
	}

	private JScrollPane getTemplate1() {
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

		return jScrollPane1;
	}

	private JScrollPane getTemplate2() {
		final JTable jtTemplate2 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		jtTemplate2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Template Index", "x", "y", "i" }));
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jtTemplate2);

		return jScrollPane2;
	}

	private JScrollPane getSynthArrayPanel() {
		final JTable jTable1 = new JTable();
		jTable1.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane3 = new JScrollPane();
		jScrollPane3.setViewportView(jTable1);

		return jScrollPane3;
	}

	private JScrollPane getResampleArray() {
		final JTable jTable2 = new JTable();
		jTable2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane7 = new JScrollPane();
		jScrollPane7.setViewportView(jTable2);

		return jScrollPane7;
	}
}
