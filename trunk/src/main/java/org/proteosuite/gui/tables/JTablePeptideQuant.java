package org.proteosuite.gui.tables;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.model.MzQuantMLFile;

import uk.ac.liv.jmzqml.MzQuantMLElement;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensusList;
import uk.ac.liv.jmzqml.model.mzqml.Row;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

public class JTablePeptideQuant extends JTableDefault {
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Peptide");
		setModel(model);
	}

	public void showData(MzQuantMLFile dataFile) {
		DefaultTableModel peptideModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0)
					return String.class;
				else
					return Double.class;
			}
		};
		setModel(peptideModel);
		peptideModel.addColumn("Peptide");

		MzQuantMLUnmarshaller unmarshaller = dataFile.getUnmarshaller();
		MzQuantML mzq = unmarshaller.unmarshal(MzQuantML.class);

		// Protein Quantitation
		// Based on the the assay list and study variables
		// we will include the different columns
		AssayList assayList = mzq.getAssayList();
		List<Assay> listAssay = assayList.getAssay();
		final int iAssays = listAssay.size();

		// Peptide Quantitation
		// Based on the the assay list and study
		// variables we will include the different columns
		for (Assay assay : listAssay) {
			peptideModel.addColumn(assay.getName());
		}

		// Getting DataMatrix from AssayQuantLayer
		PeptideConsensusList pepConsList = unmarshaller
				.unmarshal(MzQuantMLElement.PeptideConsensusList);

		if (pepConsList.getAssayQuantLayer().size() > 0) {
			List<Row> dataMatrix3 = pepConsList.getAssayQuantLayer().get(0)
					.getDataMatrix().getRow();
			for (Row row : dataMatrix3) {
				Object[] aObj = new Object[iAssays + 1];

				aObj[0] = row.getObjectRef();

				int iI = 1;
				for (String s : row.getValue()) {
					if (s.equals("null"))
						aObj[iI] = Double.NaN;
					else
						aObj[iI] = Double.parseDouble(s);
					iI++;
				}
				peptideModel.insertRow(peptideModel.getRowCount(), aObj);
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