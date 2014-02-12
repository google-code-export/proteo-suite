package org.proteosuite.gui.tables;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


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
            Class<?>[] types = new Class[]{Integer.class, Float.class,
                Float.class};

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

            // Reading mz Values
            List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList()
                    .getBinaryDataArray();

            // Reading mz and intensity values
            for (BinaryDataArray bda : bdal) {
                if (bda.getBinary() == null) {
                    continue;
                }

                List<CVParam> cvpList = bda.getCvParam();
                for (CVParam cvp : cvpList) {
                    if (cvp.getAccession().equals("MS:1000514")) {
                        mzNumbers = bda.getBinaryDataAsNumberArray();
                    } else if (cvp.getAccession().equals("MS:1000515")) {
                        intenNumbers = bda.getBinaryDataAsNumberArray();
                    }
                }
            }

            if (mzNumbers != null && intenNumbers != null) {
                for (int i = 0; i < mzNumbers.length; i++) {
                    model.insertRow(model.getRowCount(),
                            new Object[]{i, mzNumbers[i].doubleValue(),
                                intenNumbers[i].doubleValue()});
                }
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
