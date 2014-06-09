package org.proteosuite.gui.tables;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.model.MzMLFile;

import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew collins
 */
public class JTableMzML extends JTableDefault {
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel model;

	public JTableMzML() {
		reset();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void showData(MzMLFile mzml, byte msLevelThreshold, double lowIntensityThreshold) {			
		MzMLUnmarshaller unmarshaller = mzml.getUnmarshaller();

		// Reading spectrum data
		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);

		while (spectrumIterator.hasNext()) {
			byte msLevel = 0;
			float rt = 0;
			String unitRT = "";
			float basePeakMZ = 0;
			float basePeakInt = 0;
			// Reading MS data
			Spectrum spectrum = spectrumIterator.next();

			for (CVParam lCVParam : spectrum.getCvParam()) {
				if (lCVParam.getAccession().equals("MS:1000511"))
					msLevel = Byte.parseByte(lCVParam.getValue().trim());

				if (lCVParam.getAccession().equals("MS:1000504"))
					basePeakMZ = Float.parseFloat(lCVParam.getValue().trim());

				if (lCVParam.getAccession().equals("MS:1000505"))
					basePeakInt = Float.parseFloat(lCVParam.getValue().trim());
			}
			
			if (msLevel != msLevelThreshold)
				continue;
			
			if (basePeakInt < lowIntensityThreshold)
				continue;

			for (CVParam lCVParam : spectrum.getScanList().getScan().get(0)
					.getCvParam()) {
				if (!lCVParam.getAccession().equals("MS:1000016"))
					continue;

				// Extracting RT
				unitRT = lCVParam.getUnitAccession().trim();
				rt = Float.parseFloat(lCVParam.getValue().trim());

				// Convert RT into seconds
				if (unitRT.equals("UO:0000031"))
					rt *= 60;
			}
			PrecursorList plist = spectrum.getPrecursorList();
			// Get precursor ion
			float precursMz = 0.0f;
			if (plist != null && plist.getCount().intValue() == 1) {
				// Detect parent ion m/z and charge
				for (CVParam lCVParam : plist.getPrecursor().get(0)
						.getSelectedIonList().getSelectedIon().get(0)
						.getCvParam()) {
					if (lCVParam.getAccession().equals("MS:1000744")) {
						precursMz = Float
								.parseFloat(lCVParam.getValue().trim());
					}
				}
			}

			if (precursMz > 0.0) {
				model.insertRow(
						model.getRowCount(),
						new Object[] {
								spectrum.getIndex(),
								spectrum.getId(),
								msLevel,
								basePeakMZ,
								basePeakInt,
								rt,
								precursMz
						});
			} else {
				model.insertRow(
						model.getRowCount(),
						new Object[] {
								spectrum.getIndex().toString(),
								spectrum.getId(),
								msLevel,
								basePeakMZ,
								basePeakInt,
								rt,
								""
						});
			}
			// jtMzML.setDefaultRenderer(Color.class, new
			// MSLevelRender(true, msLevel));
		}

		getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			final TableCellRenderer defaultRenderer = JTableMzML.this
					.getTableHeader().getDefaultRenderer();

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JComponent component = (JComponent) defaultRenderer
						.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
				component.setToolTipText(""
						+ JTableMzML.this.getColumnName(column));
				return component;
			}
		});

		setAutoCreateRowSorter(true);
	}

	@Override
	public void reset() {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			Class<?>[] types = new Class[] { Integer.class, String.class,
					Byte.class, Float.class, Float.class, Float.class,
					String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		model.addColumn("Index");
		model.addColumn("ID");
		model.addColumn("MS");
		model.addColumn("Base peak m/z");
		model.addColumn("Base peak int");
		model.addColumn("RT (sec)");
		model.addColumn("Precurs m/z");
		setModel(model);
	}
}