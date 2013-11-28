package org.proteosuite.gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
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
import javax.swing.LayoutStyle;
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
		final JTextField jtMSIndex = new JTextField();
		jtMSIndex.setToolTipText("Enter the index");
		jtMSIndex.addKeyListener(new KeyListenerSearch(0, jtRawData, true));

		final JTextField jtMSMz = new JTextField();
		jtMSMz.setToolTipText("Enter the m/z value ");
		jtMSMz.addKeyListener(new KeyListenerSearch(1, jtRawData, true));

		final JLabel jLabel1 = new JLabel("Search:");
		jLabel1.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		JLabel jLabel2 = new JLabel("Index:");
		JLabel jLabel3 = new JLabel("m/z:");

		JPanel jpRawDataValuesMenu = new JPanel();
		jpRawDataValuesMenu.setMaximumSize(new Dimension(32767, 50));
		jpRawDataValuesMenu.setMinimumSize(new Dimension(0, 50));
		jpRawDataValuesMenu.setPreferredSize(new Dimension(462, 50));
		GroupLayout jpRawDataValuesMenuLayout = new GroupLayout(
				jpRawDataValuesMenu);
		jpRawDataValuesMenu.setLayout(jpRawDataValuesMenuLayout);
		jpRawDataValuesMenuLayout
				.setHorizontalGroup(jpRawDataValuesMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpRawDataValuesMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jLabel1)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel2)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jtMSIndex,
												GroupLayout.PREFERRED_SIZE, 54,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel3)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jtMSMz,
												GroupLayout.PREFERRED_SIZE, 69,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(103, Short.MAX_VALUE)));
		jpRawDataValuesMenuLayout
				.setVerticalGroup(jpRawDataValuesMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpRawDataValuesMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpRawDataValuesMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(jLabel2)
														.addComponent(
																jtMSIndex,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel3)
														.addComponent(
																jtMSMz,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(19, Short.MAX_VALUE)));
		JSplitPane jspRawDataValues = new JSplitPane();
		jspRawDataValues.setDividerLocation(40);
		jspRawDataValues.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspRawDataValues.setLeftComponent(jpRawDataValuesMenu);

		JScrollPane jspRawData = new JScrollPane();
		jspRawData.setViewportView(jtRawData);

		jspRawDataValues.setBottomComponent(jspRawData);

		JPanel jpRawDataValues = new JPanel();
		GroupLayout jpRawDataValuesLayout = new GroupLayout(jpRawDataValues);
		jpRawDataValues.setLayout(jpRawDataValuesLayout);
		jpRawDataValuesLayout.setHorizontalGroup(jpRawDataValuesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspRawDataValues, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jpRawDataValuesLayout.setVerticalGroup(jpRawDataValuesLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspRawDataValues, GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE));

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
		JPanel jPanel5 = new JPanel();

		GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 51, Short.MAX_VALUE));

		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);

		jPanel1Layout.setHorizontalGroup(jPanel1Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel5, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel1Layout
								.createSequentialGroup()
								.addComponent(jPanel5,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1,
										GroupLayout.DEFAULT_SIZE, 175,
										Short.MAX_VALUE)));

		return jPanel1;
	}

	private JPanel getTemplate2() {
		JPanel jPanel6 = new JPanel();
		final JTable jtTemplate2 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		jtTemplate2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Template Index", "x", "y", "i" }));
		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jtTemplate2);

		GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 52, Short.MAX_VALUE));

		JPanel jPanel2 = new JPanel();
		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel6, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING,
						GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel2Layout
								.createSequentialGroup()
								.addComponent(jPanel6,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2,
										GroupLayout.DEFAULT_SIZE, 174,
										Short.MAX_VALUE)));

		return jPanel2;
	}

	private JPanel getSynthArrayPanel() {
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();

		final JTable jTable1 = new JTable();
		jTable1.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane3 = new JScrollPane();
		jScrollPane3.setViewportView(jTable1);

		GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 58, Short.MAX_VALUE));

		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel4, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jPanel3Layout
								.createSequentialGroup()
								.addComponent(jPanel4,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane3,
										GroupLayout.DEFAULT_SIZE, 168,
										Short.MAX_VALUE)));

		return jPanel3;
	}

	private JPanel getResampleArray() {
		JPanel jPanel7 = new JPanel();

		final JTable jTable2 = new JTable();
		jTable2.setModel(new DefaultTableModel(new String[][] {

		}, new String[] { "Scan Number" }));
		JScrollPane jScrollPane7 = new JScrollPane();
		jScrollPane7.setViewportView(jTable2);

		GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 373, Short.MAX_VALUE));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 57, Short.MAX_VALUE));

		JPanel jpPanel4 = new JPanel();
		GroupLayout jpPanel4Layout = new GroupLayout(jpPanel4);
		jpPanel4.setLayout(jpPanel4Layout);
		jpPanel4Layout.setHorizontalGroup(jpPanel4Layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel7, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jScrollPane7, GroupLayout.DEFAULT_SIZE, 373,
						Short.MAX_VALUE));
		jpPanel4Layout.setVerticalGroup(jpPanel4Layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						jpPanel4Layout
								.createSequentialGroup()
								.addComponent(jPanel7,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane7,
										GroupLayout.DEFAULT_SIZE, 169,
										Short.MAX_VALUE)));

		return jpPanel4;
	}
}
