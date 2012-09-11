/*
 * --------------------------------------------------------------------------
 * ProgressBarDialog.java
 * --------------------------------------------------------------------------
 * Description:       Progress Bar Dialog
 * Developer:         Faviel Gonzalez
 * Created:           09 February 2012
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

/**
 * Progress bar used for long procedures (tasks).
 * @author faviel
 * @param parent JFrame which will be used to plot the progress bar.
 * @param modal Access to super class.
 */
public class ProgressBarDialog extends javax.swing.JDialog {

    //... Creates new form ProgressBarDialog ...//
    public ProgressBarDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);        
        initComponents();
        setLocationRelativeTo(parent);
    }

    /** 
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpbStatusBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpbStatusBar.setIndeterminate(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jpbStatusBar;
    // End of variables declaration//GEN-END:variables

}
