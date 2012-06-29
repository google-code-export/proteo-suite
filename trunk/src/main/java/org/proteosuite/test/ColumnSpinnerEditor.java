/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.test;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author mpcusack
 */
public class ColumnSpinnerEditor extends JTextField implements ChangeListener {

    ReadSearchResultsDialog srd;
    JSpinner parent;
    boolean useHeaders;

    /**
     *
     * @param msrd
     * @param parent
     */
    public ColumnSpinnerEditor(ReadSearchResultsDialog srd, JSpinner parent) {
        this(srd, parent, true);
    }

    /**
     *
     * @param msrd
     * @param parent
     * @param useHeaders
     */
    public ColumnSpinnerEditor(ReadSearchResultsDialog srd, JSpinner parent, boolean useHeaders) {
        this.srd = srd;
        this.parent = parent;
        this.useHeaders = useHeaders;
        parent.addChangeListener(this);
        setEditable(false);
        setBackground(Color.white);
        setHorizontalAlignment(RIGHT);
        setFont(getFont().deriveFont(Font.BOLD));
        if (useHeaders) {
            srd.addPropertyChangeListener("headers", new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    stateChanged(null);
                }
            });
        }
        stateChanged(null);
    }

    public void stateChanged(ChangeEvent e) {
        setText(getDisplayString());
    }

    private String getDisplayString() {
        if (useHeaders) {
            if (srd.getHeaders() == null) {
                return parent.getValue().toString();
            }
            int ival = (Integer) parent.getValue();

            if (ival >= 1 && ival <= srd.getHeaders().length) {
                return parent.getValue().toString() + ": " + srd.getHeaders()[ival - 1];
            }
        }
        return parent.getValue().toString();
    }
}