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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Progress bar used for long procedures (tasks).
 * @author fgonzalez
 * @param parent JFrame which will be used to plot the progress bar.
 * @param modal Access to super class.
 */
public class ProgressBarDialog extends JDialog {

    private JButton jbCancel;
    private JLabel jlExecuting;
    private JProgressBar jpbStatusBar;
    private String sThread = "";
    private static final SystemUtils SYS_UTILS = new SystemUtils();
    
    /**
     * Creates new form ProgressBarDialog
     * @param parent
     * @param modal
     * @param sThread
     */
    public ProgressBarDialog(Frame parent, boolean modal, String sThread) {
        super(parent, modal);        
        initComponents();
        setLocationRelativeTo(parent);
        this.sThread = sThread;
        this.jlExecuting.setText("");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    public void setTaskName(String sExecutingTask){
        jlExecuting.setText(sExecutingTask);
    }

    /** 
     * This method is called from within the constructor to initialise the form.
     */
    private void initComponents() {

        jpbStatusBar = new JProgressBar();
        jbCancel = new JButton();
        jlExecuting = new JLabel();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        jpbStatusBar.setIndeterminate(true);

        jbCancel.setText("Cancel");
        jbCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jbCancelActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jpbStatusBar, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jbCancel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlExecuting, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpbStatusBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(jlExecuting, GroupLayout.DEFAULT_SIZE, 11, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(jbCancel)
                .addContainerGap())
        );

        pack();
    }

    /**
     * 
     * @param actionEvent
     */
    private void jbCancelActionPerformed(ActionEvent actionEvent) {       
        
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet){
            if(thread.getName().equals(sThread)){
                 thread.interrupt();
                 System.out.println(SYS_UTILS.getTime()+" - Thread " + thread.getName() + " stopped");
            }
        }
        this.dispose();
    }
}
