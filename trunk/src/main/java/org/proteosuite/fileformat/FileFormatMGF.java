package org.proteosuite.fileformat;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.utils.ProgressBarDialog;

public class FileFormatMGF {

	private JTable jtMGF;
	private JLabel jlFileNameMGFText;
	private String sFileNameRef;
	private JTabbedPane jtpProperties;
	private String sFilePathRef;
	private ProgressBarDialog progressBarDialog;

	public FileFormatMGF(JTable jtMGF, JLabel jlFileNameMGFText, String sFileNameRef, JTabbedPane jtpProperties, String sFilePathRef,
			ProgressBarDialog progressBarDialog) {		
		this.jtMGF = jtMGF;
		this.jlFileNameMGFText = jlFileNameMGFText;
		this.sFileNameRef = sFileNameRef;
		this.jtpProperties = jtpProperties;
		this.sFilePathRef = sFilePathRef;
		this.progressBarDialog = progressBarDialog;
	}
	
	public void run() {
		DefaultTableModel model = new DefaultTableModel() {
			Class<?>[] types = new Class[] { Integer.class,
					String.class, Double.class, String.class,
					Long.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};
		jtMGF.setModel(model);
		model.addColumn("Index");
		model.addColumn("Scan Title");
		model.addColumn("Peptide Mass");
		model.addColumn("Charge");
		model.addColumn("Reference Line");

		jlFileNameMGFText.setText(sFileNameRef);
		jtpProperties.setSelectedIndex(1);

		// ... Reading file ...//
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					sFilePathRef));
			String title = "", charge = "", sPepmass = "";
			double pepmass = 0.0;
			long refLine = 0;
			String string = null;

			long lineNum = 0;
			int iCount = 0;
			while ((string = in.readLine()) != null) {
				lineNum++;
				string = string.trim();
				if (string.startsWith("BEGIN IONS")) {
					break;
				}
			}
			while ((string = in.readLine()) != null) {
				lineNum++;
				string = string.trim();
				if (string.equals("")) {
					continue;
				} else if (string.startsWith("BEGIN IONS")) {
					continue;
				} else if (string.startsWith("TITLE")) {
					title = string.substring(6);
					continue;
				} else if (string.startsWith("RTINSECONDS")) {
					continue;
				} else if (string.startsWith("PEPMASS")) {
					// ... Check for double values ...//
					sPepmass = string.substring(8);
					if (sPepmass.contains(" ")) {
						sPepmass = sPepmass.substring(0,
								sPepmass.indexOf(" "));
					}
					pepmass = Double.parseDouble(sPepmass);
					continue;
				} else if (string.startsWith("CHARGE")) {
					charge = string.substring(7);
					refLine = lineNum;
					continue;
				} else if (string.charAt(0) >= '1'
						&& string.charAt(0) <= '9') {
					continue;
				} else if (string.contains("END IONS")) {
					iCount++;
					// ... Insert rows ...//
					model.insertRow(model.getRowCount(),
							new Object[] { iCount, title, pepmass,
									charge, refLine });
					continue;
				} else {
					continue;
				}
			}
			in.close();
		} catch (Exception e) {
			System.exit(1);
		}
		jtMGF.getTableHeader().setDefaultRenderer(
				new TableCellRenderer() {
					final TableCellRenderer defaultRenderer = jtMGF
							.getTableHeader().getDefaultRenderer();

					public Component getTableCellRendererComponent(
							JTable table, Object value,
							boolean isSelected, boolean hasFocus,
							int row, int column) {
						JComponent component = (JComponent) defaultRenderer
								.getTableCellRendererComponent(
										table, value, isSelected,
										hasFocus, row, column);
						component.setToolTipText(""
								+ jtMGF.getColumnName(column));
						return component;
					}
				});

		jtMGF.setAutoCreateRowSorter(true);
		progressBarDialog.setVisible(false);
		progressBarDialog.dispose();
	}
}
