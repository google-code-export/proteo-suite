/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ModPanel.java
 *
 * Created on Sep 1, 2009, 1:03:40 PM
 */
package org.proteosuite.external;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author D3Y241
 */
public class ModPanel extends javax.swing.JPanel {

    private GridBagConstraints gbc;
    List<ActionListener> listeners = new LinkedList<ActionListener>();
    static AA[] AAs = new AA[20];
    public static enum ModTypes {Peptide_Mod, Residue_Mod};

    public ModPanel(GridBagConstraints gbc) {
        AAs[0] = new AA("Ala", 'A');
        AAs[1] = new AA("Arg", 'R');
        AAs[2] = new AA("Asn", 'N');
        AAs[3] = new AA("Asp", 'D');
        AAs[4] = new AA("Cys", 'C');
        AAs[5] = new AA("Glu", 'E');
        AAs[6] = new AA("Gln", 'Q');
        AAs[7] = new AA("Gly", 'G');
        AAs[8] = new AA("His", 'H');
        AAs[9] = new AA("Ile", 'I');
        AAs[10] = new AA("Leu", 'L');
        AAs[11] = new AA("Lys", 'K');
        AAs[12] = new AA("Met", 'M');
        AAs[13] = new AA("Phe", 'F');
        AAs[14] = new AA("Pro", 'P');
        AAs[15] = new AA("Ser", 'S');
        AAs[16] = new AA("Thr", 'T');
        AAs[17] = new AA("Trp", 'W');
        AAs[18] = new AA("Tyr", 'Y');
        AAs[19] = new AA("Val", 'V');

        this.gbc = gbc;
        initComponents();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        removeBut = new JButton();
        modTypeCB = new JComboBox<ModTypes>();
        aaCB = new JComboBox<AA>();
        modTF = new JTextField();

        setLayout(new java.awt.GridBagLayout());

        removeBut.setText("Remove");
        removeBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeButActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        add(removeBut, gridBagConstraints);

        modTypeCB.setModel(new DefaultComboBoxModel<ModTypes>(ModTypes.values()));
        modTypeCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modTypeCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(modTypeCB, gridBagConstraints);

        aaCB.setModel(new DefaultComboBoxModel<AA>(AAs));
        aaCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aaCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(aaCB, gridBagConstraints);
        aaCB.setVisible(false);

        modTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                modTFFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(modTF, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void removeButActionPerformed(ActionEvent evt) {//GEN-FIRST:event_removeButActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_removeButActionPerformed

    private void modTypeCBActionPerformed(ActionEvent evt) {//GEN-FIRST:event_modTypeCBActionPerformed
        aaCB.setVisible((modTypeCB.getSelectedItem() == ModTypes.Residue_Mod));
        triggerChange(evt);
    }//GEN-LAST:event_modTypeCBActionPerformed

    private void aaCBActionPerformed(ActionEvent evt) {//GEN-FIRST:event_aaCBActionPerformed
        triggerChange(evt);
    }//GEN-LAST:event_aaCBActionPerformed

    private void modTFFocusLost(FocusEvent evt) {//GEN-FIRST:event_modTFFocusLost
triggerChange(null);
    }//GEN-LAST:event_modTFFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox<AA> aaCB;
    private JTextField modTF;
    private JComboBox<ModTypes> modTypeCB;
    protected javax.swing.JButton removeBut;
    // End of variables declaration//GEN-END:variables

    public GridBagConstraints getGbc() {
        return gbc;
    }

    public void setGbc(GridBagConstraints gbc) {
        this.gbc = gbc;
    }

    public int getIndex() {
        return gbc.gridy - 1;
    }

    public void decrementIndex() {
        int newIndex = getIndex() - 1;

        setIndex(newIndex);
    }

    public void setIndex(int index) {
        if (index < 0) {
            throw new RuntimeException("Trying to set modPanel index too small!");
        }
        gbc.gridy = index + 1;
    }

    public void addChangedListener(ActionListener l) {
        listeners.add(l);
    }

    public void removeChangedListener(ActionListener l) {
        listeners.remove(l);
    }

    private void triggerChange(ActionEvent e) {
        
        for (ActionListener l : listeners) {
            l.actionPerformed(e);
        }
    }

    public ModTypes getModType(){
        return (ModTypes)modTypeCB.getSelectedItem();
    }

    public AA getAA(){
        return (AA)aaCB.getSelectedItem();
    }

    public String getMod(){
        return modTF.getText();
    }

    public class AA {

        private String threeLetter;
        private char oneLetter;

        public AA(String threeLetter, char oneLetter) {
            this.threeLetter = threeLetter;
            this.oneLetter = oneLetter;
        }

        public char getOneLetter() {
            return oneLetter;
        }

        public String getThreeLetter() {
            return threeLetter;
        }

        @Override
        public String toString() {
            return getThreeLetter() + "(" + getOneLetter() + ")";
        }
    }
}
