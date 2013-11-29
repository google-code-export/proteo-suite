package org.proteosuite.gui.tables;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.utils.compression.DeltaConversion;
import org.proteosuite.utils.compression.DeltaConversion.DeltaEncodedDataFormatException;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;

public class JTableRawData extends JTableDefault {
	
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");
		setModel(model);
		
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * Displays raw data
	 * 
	 * @iIndex - Index to the aMzMLUnmarshaller list
	 * @sID - scan ID
	 * @return void
	 */
	public void showRawData(MzMLUnmarshaller unmarshaller, String sID,
			JTabbedPane jtpLog) {

		DefaultTableModel model = new DefaultTableModel() {
			Class<?>[] types = new Class[] { Integer.class, Float.class,
					Float.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		};

		setModel(model);
		model.addColumn("Index");
		model.addColumn("m/z");
		model.addColumn("Intensity");
		jtpLog.setSelectedIndex(1);
		try {
			Spectrum spectrum = unmarshaller.getSpectrumById(sID);
			Number[] mzNumbers = null;
			Number[] intenNumbers = null;

			boolean isCompressed = false;
			// Reading mz Values
			List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList()
					.getBinaryDataArray();
			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000000")) {
						isCompressed = true;
					}
				}
			}

			// Reading mz and intensity values
			for (BinaryDataArray bda : bdal) {
				List<CVParam> cvpList = bda.getCvParam();
				for (CVParam cvp : cvpList) {
					if (cvp.getAccession().equals("MS:1000514")) {
						mzNumbers = bda.getBinaryDataAsNumberArray();
						if (isCompressed) {
							try {
								DeltaConversion
										.fromDeltaNumberFormat(mzNumbers);
							} catch (DeltaEncodedDataFormatException dex) {
								System.out
										.println("Problem converting back from delta m/z format: "
												+ dex.getLocalizedMessage());
								return;
							}
						}
					}
					if (cvp.getAccession().equals("MS:1000515")) {
						intenNumbers = bda.getBinaryDataAsNumberArray();
					}
				}
			}
			for (int iI = 0; iI < mzNumbers.length; iI++) {
				model.insertRow(model.getRowCount(),
						new Object[] { iI, mzNumbers[iI].doubleValue(),
								intenNumbers[iI].doubleValue() });
			}
			getTableHeader().setDefaultRenderer(
					new TableCellRenderer() {
						final TableCellRenderer defaultRenderer = getTableHeader().getDefaultRenderer();

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							JComponent component = (JComponent) defaultRenderer
									.getTableCellRendererComponent(table,
											value, isSelected, hasFocus, row,
											column);
							component.setToolTipText(""
									+ getColumnName(column));
							return component;
						}
					});
			setAutoCreateRowSorter(true);
		} catch (MzMLUnmarshallerException ume) {
			System.out.println(ume.getMessage());
		}
	}
}
