package org.proteosuite.gui.tables;

import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.proteosuite.model.RawMzMLFile;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.FileDescription;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 *
 * @author Andrew collins
 */
public class JTableMzML extends JTableDefault {

    private DefaultTableModel model;

    public JTableMzML() {
        reset();
    }

    public void showData(RawMzMLFile mzml) {
        MzMLUnmarshaller unmarshaller = mzml.getUnmarshaller();
        StringBuilder sOutput = new StringBuilder();

        FileDescription fdList = unmarshaller.unmarshalFromXpath(
                "/fileDescription", FileDescription.class);
        List<CVParam> fileContent = fdList.getFileContent()
                .getCvParam();
        for (Iterator<CVParam> lCVParamIterator = fileContent.iterator(); lCVParamIterator
                .hasNext();) {
            CVParam lCVParam = lCVParamIterator.next();
            sOutput.append(" - " + lCVParam.getName().trim()
                    + "<br />");
        }
        // Source File
        sOutput.append("<b>Source File:</b><br />");
        List<CVParam> sourceParam = fdList.getSourceFileList()
                .getSourceFile().get(0).getCvParam();
        for (Iterator<CVParam> lCVParamIterator = sourceParam.iterator(); lCVParamIterator
                .hasNext();) {
            CVParam lCVParam = lCVParamIterator.next();
            sOutput.append(" - " + lCVParam.getName().trim()
                    + "<br />");
        }

        sOutput.append("<b>Software:</b><br />");
        uk.ac.ebi.jmzml.model.mzml.SoftwareList softList = unmarshaller
                .unmarshalFromXpath(
                        "/softwareList",
                        uk.ac.ebi.jmzml.model.mzml.SoftwareList.class);
        List<CVParam> softParam = softList.getSoftware().get(0)
                .getCvParam();
        for (Iterator<CVParam> lCVParamIterator = softParam.iterator(); lCVParamIterator
                .hasNext();) {
            CVParam lCVParam = lCVParamIterator.next();
            sOutput.append(" - " + lCVParam.getName().trim()
                    + "<br />");
        }

        // Reading spectrum data
        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
                .unmarshalCollectionFromXpath(
                        "/run/spectrumList/spectrum",
                        Spectrum.class);
        String msLevel = "";
        float basePeakMZ = 0;
        float basePeakInt = 0;
        String unitRT = "";
        float rt = 0;
        float rtMin = 0;
        int iCount = 1;
        double dScansMS1 = 0;
        double dScansMS2 = 0;

        while (spectrumIterator.hasNext()) {
            // Reading MS data
            Spectrum spectrum = spectrumIterator.next();
            List<CVParam> specParam = spectrum.getCvParam();
            for (Iterator<CVParam> lCVParamIterator = specParam.iterator(); lCVParamIterator
                    .hasNext();) {
                CVParam lCVParam = lCVParamIterator
                        .next();
                if (lCVParam.getAccession().equals("MS:1000511")) {
                    msLevel = lCVParam.getValue().trim();
                    if (msLevel.equals("1")) {
                        // Type of data
                        dScansMS1++;
                    } else {
                        dScansMS2++;
                    }
                }
                if (lCVParam.getAccession().equals("MS:1000504")) {
                    basePeakMZ = Float.parseFloat(lCVParam
                            .getValue().trim());
                }
                if (lCVParam.getAccession().equals("MS:1000505")) {
                    basePeakInt = Float.parseFloat(lCVParam
                            .getValue().trim());
                }
            }
            List<CVParam> scanParam = spectrum.getScanList()
                    .getScan().get(0).getCvParam();
            for (Iterator<CVParam> lCVParamIterator = scanParam.iterator(); lCVParamIterator
                    .hasNext();) {
                CVParam lCVParam = lCVParamIterator
                        .next();
                if (lCVParam.getAccession().equals("MS:1000016")) {
                    // Extracting RT
                    unitRT = lCVParam.getUnitAccession().trim();
                    if (unitRT.equals("UO:0000031")) {
                        // Convert RT into seconds
                        rt = Float.parseFloat(lCVParam.getValue()
                                .trim());
                        rtMin = rt;
                        rt = rt * 60;
                    } else {
                        // Convert RT into minutes
                        rt = Float.parseFloat(lCVParam.getValue()
                                .trim());
                        rtMin = rt / 60;
                    }
                }
            }
            PrecursorList plist = spectrum.getPrecursorList();
            // Get precursor ion
            float precursMZ = 0.0f;
            if (plist != null) {
                if (plist.getCount().intValue() == 1) {
                    List<CVParam> scanPrecParam = plist
                            .getPrecursor().get(0)
                            .getSelectedIonList().getSelectedIon()
                            .get(0).getCvParam();

                    // ... Detect parent ion m/z and charge ...//
                    for (Iterator<CVParam> lCVParamIterator = scanPrecParam
                            .iterator(); lCVParamIterator.hasNext();) {
                        CVParam lCVParam = lCVParamIterator
                                .next();
                        if (lCVParam.getAccession().equals(
                                "MS:1000744")) {
                            precursMZ = Float.parseFloat(lCVParam
                                    .getValue().trim());
                        }
                    }
                }
            }

            Color color = null;
            if (msLevel.equals("1")) {
                color = new Color(204, 192, 218);
            } else {
                color = new Color(252, 213, 218);
            }
            if (precursMZ > 0.0) {
                model.insertRow(
                        model.getRowCount(),
                        new Object[]{
                            Integer.parseInt(spectrum
                                    .getIndex().toString()),
                            spectrum.getId().toString(),
                            msLevel,
                            Float.parseFloat(String.format(
                                            "%.2f", basePeakMZ)),
                            Float.parseFloat(String.format(
                                            "%.2f", basePeakInt)),
                            Float.parseFloat(String.format(
                                            "%.2f", rt)),
                            String.format("%.4f", precursMZ)});
            } else {
                model.insertRow(
                        model.getRowCount(),
                        new Object[]{
                            Integer.parseInt(spectrum
                                    .getIndex().toString()),
                            spectrum.getId().toString(),
                            msLevel,
                            Float.parseFloat(String.format(
                                            "%.2f", basePeakMZ)),
                            Float.parseFloat(String.format(
                                            "%.2f", basePeakInt)),
                            Float.parseFloat(String.format(
                                            "%.2f", rt)), ""});
            }
            iCount++;
            // jtMzML.setDefaultRenderer(Color.class, new
            // MSLevelRender(true, msLevel));
        }
        this.getTableHeader().setDefaultRenderer(
                new TableCellRenderer() {
                    final TableCellRenderer defaultRenderer = JTableMzML.this.getTableHeader().getDefaultRenderer();

                    public Component getTableCellRendererComponent(
                            JTable table, Object value,
                            boolean isSelected, boolean hasFocus,
                            int row, int column) {
                                JComponent component = (JComponent) defaultRenderer
                                .getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column);
                                component.setToolTipText(""
                                        + JTableMzML.this.getColumnName(column));
                                return component;
                            }
                });

        this.setAutoCreateRowSorter(true);
    }

    @Override
    public void reset() {
        model = new DefaultTableModel() {
            Class<?>[] types = new Class[]{Integer.class,
                String.class, String.class, Float.class,
                Float.class, Float.class, String.class};

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

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
