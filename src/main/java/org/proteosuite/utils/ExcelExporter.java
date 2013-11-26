/*
 * --------------------------------------------------------------------------
 * ExcelExporter.java
 * --------------------------------------------------------------------------
 * Description:       Convert a jtable to a spreadsheet
 * Developer:         fgonzalez
 * Created:           11 March 2013
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.utils;

import java.io.File;
import javax.swing.*;
import javax.swing.table.*;
import jxl.*;
import jxl.write.*;

/**
 * This class allows to export any table into a spreadsheet *
 * @author fgonzalez
 */
public class ExcelExporter {
    /**
     * Export data into specified excel file.
     * @param table - Table to export
     * @param file - File name in which the data will be exported
     * @param bAll - Export all columns
     * @param iExclude - Exclude column
     */
    public void fillData(final JTable table, File file, final boolean bAll, final int iExclude) {
        try {
            WritableWorkbook wbk = Workbook.createWorkbook(file);
            WritableSheet sheet1 = wbk.createSheet("Results", 0);
            TableModel model = table.getModel();

            for (int iI = 0; iI < model.getColumnCount(); iI++) {
                if (bAll) {
                    Label column = new Label(iI, 0, model.getColumnName(iI));
                    sheet1.addCell(column);                    
                } else if (iI!=iExclude){
                    Label column = new Label(iI, 0, model.getColumnName(iI));
                    sheet1.addCell(column);
                }
            }
            
            for (int iI = 0; iI < model.getRowCount(); iI++) {
                for (int iJ = 0; iJ < model.getColumnCount(); iJ++) {
                    if (bAll) {                    
                        Label row = new Label(iJ, iI + 1, model.getValueAt(iI, iJ).toString());
                        sheet1.addCell(row);
                    } else if (iJ!=iExclude){
                        Label row = new Label(iJ, iI + 1, model.getValueAt(iI, iJ).toString());
                        sheet1.addCell(row);
                    }
                }
            }
            
            wbk.write();
            wbk.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

