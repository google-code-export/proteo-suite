package org.proteosuite.gui.tables;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.model.MzQuantMLFile;

import uk.ac.liv.jmzqml.model.mzqml.Feature;
import uk.ac.liv.jmzqml.model.mzqml.FeatureList;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

public class JTableFeatureQuant extends JTableDefault {
	private static final long serialVersionUID = 1L;

        @Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Feature");
		setModel(model);
	}

	public void showData(MzQuantMLFile dataFile) {
		DefaultTableModel featureModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0)
					return String.class;
				else if (columnIndex == 1)
					return Double.class;
				else if (columnIndex == 2)
					return Double.class;
				else if (columnIndex == 3)
					return Byte.class;

				return String.class;
			}
		};
		setModel(featureModel);
		featureModel.addColumn("FeatureList");

		// ARJ Update 21/11/2013 - we need a full file path,
		// this is only delivering a local file name at the
		// moment.
		// When importing mzQuantML files, full path is needed
		// MzQuantMLUnmarshaller unmarshaller = new
		// MzQuantMLUnmarshaller(jtQuantFiles.getValueAt(0,
		// iIndexRef).toString());
		MzQuantMLUnmarshaller unmarshaller = dataFile.getUnmarshaller();
		MzQuantML mzq = unmarshaller.unmarshal(MzQuantML.class);

		// Feature Quantitation
		// Based on the the assay list and study
		// variables we will include the different columns

		/*
		 * ARJ 22/11/2013 - removed this code, since this isn't correct parsing
		 * of mzq files, even itraq, for simplicity just display mz, rt, charge
		 * for (Assay assay : listAssay) { model3.addColumn(assay.getName()); }
		 */

		featureModel.addColumn("m/z");
		featureModel.addColumn("rt (m)");
		featureModel.addColumn("charge");

		// Fill rows

		// Getting DataMatrix from AssayQuantLayer
		// FeatureList featureList =
		// unmarshaller.unmarshal(MzQuantMLElement.FeatureList);
		for (FeatureList featureList : mzq.getFeatureList()) {
			// String rawFile =
			// featureList.getRawFilesGroupRef();
			String featureListID = featureList.getId();

			for (Feature feature : featureList.getFeature()) {
				double mz = feature.getMz();
				byte charge = Byte.parseByte(feature.getCharge());
				// Needs to be updated in jmzq to correct type
				String strRT = feature.getRt();
				double rt = Double.NaN;
				if (!(strRT == null || strRT.equals("null")))
					rt = Double.parseDouble(strRT);

				Object[] aObj = { featureListID, mz, rt, charge };
				featureModel.insertRow(featureModel.getRowCount(), aObj);
			}
		}

		// Tooltip for headers
		getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			final TableCellRenderer defaultRenderer = getTableHeader()
					.getDefaultRenderer();

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JComponent component = (JComponent) defaultRenderer
						.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
				component.setToolTipText("" + getColumnName(column));
				return component;
			}
		});
		setAutoCreateRowSorter(true);
	}
}
