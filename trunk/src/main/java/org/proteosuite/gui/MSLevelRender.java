/*
 * --------------------------------------------------------------------------
 * MSLevelRender.java
 * --------------------------------------------------------------------------
 * Description:       Render for the type of MS data (MS1 or MS2)
 * Developer:         FG
 * Created:           11 March 2013
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;


import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;

/**
 * This class allows to render the table for MS1 or MS2 data 
 * @author FG
 * @param void
 * @return void
 */
public class MSLevelRender extends JLabel implements TableCellRenderer {
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;
    String msLevel = "";
 
    public MSLevelRender(boolean isBordered, String msLevel) {
        this.isBordered = isBordered;
        this.msLevel = msLevel;
        setOpaque(true); 
    }
 
    public Component getTableCellRendererComponent(JTable table, Object color,
                            boolean isSelected, boolean hasFocus, int row, int column) {
        Color newColor = (Color)color;
        setBackground(newColor);
        
        if (isBordered) {
            if (isSelected) {
                if (selectedBorder == null) {
                    selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getSelectionBackground());
                }
                setBorder(selectedBorder);
            } else {
                if (unselectedBorder == null) {
                    unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getBackground());
                }
                setBorder(unselectedBorder);
            }
        }
        return this;
    }
}