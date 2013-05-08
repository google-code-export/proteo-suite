/*
 * --------------------------------------------------------------------------
 * ProgressBarDialog.java
 * --------------------------------------------------------------------------
 * Description:       Progress Bar Dialog
 * Developer:         fgonzalez
 * Created:           09 February 2012
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

import java.util.Set;
import javax.swing.JDialog;

/**
 * Progress bar used for long procedures (tasks).
 * @author fgonzalez
 * @param parent JFrame which will be used to plot the progress bar.
 * @param modal Access to super class.
 */
public class ProgressBarDialog extends javax.swing.JDialog {

    private String sThread = "";
    private SystemUtils SysUtils = new SystemUtils();
    
    //... Creates new form ProgressBarDialog ...//
    public ProgressBarDialog(java.awt.Frame parent, boolean modal, String sThread) {
        super(parent, modal);        
        initComponents();
        setLocationRelativeTo(parent);
        this.sThread = sThread;
        this.jlExecuting.setText("");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    public void setTaskName(String sExecutingTask){
        this.jlExecuting.setText(sExecutingTask);
    }

    /** 
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpbStatusBar = new javax.swing.JProgressBar();
        jbCancel = new javax.swing.JButton();
        jlExecuting = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jpbStatusBar.setIndeterminate(true);

        jbCancel.setText("Cancel");
        jbCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jbCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlExecuting, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlExecuting, javax.swing.GroupLayout.DEFAULT_SIZE, 11, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbCancel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelActionPerformed
        //... Stop thread ...//        
        
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet){
            if(thread.getName().equals(sThread)){
                 thread.interrupt();
                 System.out.println(SysUtils.getTime()+" - Thread "+thread.getName()+" stopped");
            }
        }
        this.dispose();
    }//GEN-LAST:event_jbCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbCancel;
    private javax.swing.JLabel jlExecuting;
    private javax.swing.JProgressBar jpbStatusBar;
    // End of variables declaration//GEN-END:variables

}
