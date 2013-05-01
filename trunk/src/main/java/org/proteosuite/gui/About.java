/*
 * --------------------------------------------------------------------------
 * About.java
 * --------------------------------------------------------------------------
 * Description:       About ProtoeSuite Form
 * Developer:         FG
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite.gui;

import java.awt.Panel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.SystemUtils;

/**
 * This form contains ProteoSuite license and distribution agreements. 
 * @author FG
 * @param void
 * @return void
 */
public class About extends javax.swing.JPanel {
    
    private SystemUtils sysutils = new SystemUtils();
    
    public About() {
        initComponents();
        this.jlLogo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/logo.png")));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jlLogo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlURL = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jlCopyright = new javax.swing.JLabel();
        jlVersion = new javax.swing.JLabel();
        jbSystemInfo = new javax.swing.JButton();
        jbLicense = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(400, 300));
        setMinimumSize(new java.awt.Dimension(400, 300));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel2.setText("Software for the Analysis of Quantitative Proteomics Data");

        jLabel4.setText("ProteoSuite is an open-source java application released under the Apache");

        jLabel5.setText("License 2.0. For more information please visit our website:");

        jlURL.setForeground(new java.awt.Color(51, 102, 255));
        jlURL.setText("http://www.proteosuite.org");
        jlURL.setToolTipText("Go to ProteoSuite Website");
        jlURL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlURL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlURLMouseClicked(evt);
            }
        });

        jlCopyright.setText("Copyright © ProteoSuite 2011-2013 ");

        jlVersion.setText("Version 0.3.0 April 2013");

        jbSystemInfo.setText("System Info");
        jbSystemInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSystemInfoActionPerformed(evt);
            }
        });

        jbLicense.setText("License");
        jbLicense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLicenseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
                        .addComponent(jlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jlURL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(131, 131, 131)
                            .addComponent(jlVersion)
                            .addGap(18, 18, 18))
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jlCopyright)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbSystemInfo)
                        .addGap(18, 18, 18)
                        .addComponent(jbLicense, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(jlCopyright)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlURL, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSystemInfo)
                    .addComponent(jbLicense))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jlURLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlURLMouseClicked
	//... Call proteosuite website ...//
        OpenURL url = new OpenURL("http://www.proteosuite.org");
    }//GEN-LAST:event_jlURLMouseClicked

    private void jbSystemInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSystemInfoActionPerformed
        Panel panel = new Panel();
        JTextArea systemInfo = new JTextArea();
        systemInfo.append("Used Memory:" + Long.toString(sysutils.getUsedMemory()));
        systemInfo.append("Free Memory:" + Long.toString(sysutils.getFreeMemory()));
        systemInfo.append("Total Memory:" + Long.toString(sysutils.getTotalMemory()));
        systemInfo.append("Max Memory:" + Long.toString(sysutils.getMaxMemory()));
        panel.add(systemInfo);
        JOptionPane.showMessageDialog(this, "", "Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jbSystemInfoActionPerformed

    private void jbLicenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLicenseActionPerformed
        
    }//GEN-LAST:event_jbLicenseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbLicense;
    private javax.swing.JButton jbSystemInfo;
    private javax.swing.JLabel jlCopyright;
    private javax.swing.JLabel jlLogo;
    private javax.swing.JLabel jlURL;
    private javax.swing.JLabel jlVersion;
    // End of variables declaration//GEN-END:variables
}
