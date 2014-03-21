package org.proteosuite.gui.tables;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.proteosuite.model.MzQuantMLFile;

import uk.ac.liv.jmzqml.MzQuantMLElement;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.Protein;
import uk.ac.liv.jmzqml.model.mzqml.ProteinList;
import uk.ac.liv.jmzqml.model.mzqml.Row;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class JTableProteinQuant extends JTableDefault {

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	@Override
	public void reset() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Protein");
		setModel(model);
	}

	public void showData(MzQuantMLFile dataFile) {
		DefaultTableModel proteinModel = new DefaultTableModel() {
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
		setModel(proteinModel);
		proteinModel.addColumn("Protein");

		MzQuantMLUnmarshaller unmarshaller = dataFile.getUnmarshaller();
		MzQuantML mzq = unmarshaller.unmarshal(MzQuantML.class);

		// Protein Quantitation
		// Based on the the assay list and study variables
		// we will include the different columns

		AssayList assayList = mzq.getAssayList();
		List<Assay> listAssay = assayList.getAssay();
		int iAssays = listAssay.size();
		for (Assay assay : listAssay) {
			proteinModel.addColumn(assay.getName());
		}

		StudyVariableList studyList = unmarshaller
				.unmarshal(MzQuantMLElement.StudyVariableList);

		int iStudyVars = 0;
		if (studyList != null)
		{
			List<StudyVariable> listStudy = studyList.getStudyVariable();
			iStudyVars = listStudy.size();
			for (StudyVariable study : listStudy) {
				proteinModel.addColumn(study.getName());
			}
		}

		// Fill rows

		// Check if mzq file contains protein list
		ProteinList proteinList = unmarshaller
				.unmarshal(MzQuantMLElement.ProteinList);

		if (proteinList == null)
			return;
		
		Map<String, Protein> mapIDToProt = new HashMap<String, Protein>();
		for (Protein protein : proteinList.getProtein()) {
			mapIDToProt.put(protein.getId(), protein);
		}

		Map<String, List<String>> hmProtein = new HashMap<String, List<String>>();
		// Getting DataMatrix from AssayQuantLayer
		if (proteinList.getAssayQuantLayer().size() > 0) {
			List<Row> dataMatrix = proteinList.getAssayQuantLayer().get(0)
					.getDataMatrix().getRow();
			for (Row row : dataMatrix) {
				String protID = row.getObjectRef();
				List<String> al = row.getValue();
				hmProtein.put(mapIDToProt.get(protID).getAccession(), al);
			}
		}
		// Getting DataMatrix from StudyVariableQuantLayer
		if (proteinList.getStudyVariableQuantLayer().size() > 0) {
			List<Row> dataMatrix2 = proteinList.getStudyVariableQuantLayer()
					.get(0).getDataMatrix().getRow();
			for (Row row : dataMatrix2) {
				// Protein prot = (Protein)
				// row.getObjectRef();
				String protID = row.getObjectRef();
				List<String> al2 = hmProtein.get(mapIDToProt.get(protID)
						.getAccession());
				for (String obj : row.getValue()) {
					al2.add(obj);
				}
				hmProtein.put(mapIDToProt.get(protID).getAccession(), al2);
			}
		}
		for (Entry<String, List<String>> entry : hmProtein.entrySet()) {
			Object[] aObj = new Object[iAssays + iStudyVars + 1];
			aObj[0] = entry.getKey();

			int iI = 1;
			for (String s : entry.getValue()) {
				if (iI >= iAssays + iStudyVars + 1) {
					JOptionPane
							.showMessageDialog(
									null,
									"Invalid file. The mzq file contains duplications in the DataMatrix on "
											+ "the AssayQuantLayer or StudyVariableQuantLayer",
									"Information",
									JOptionPane.INFORMATION_MESSAGE);
					break;
				} else {
					aObj[iI] = Double.parseDouble(s);
					iI++;
				}
			}
			proteinModel.insertRow(proteinModel.getRowCount(), aObj);
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
