/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite.test;

/**
 *
 * @author faviel
 */
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
class Testing  
{  
  JDesktopPane dp = new JDesktopPane();  
  public void buildGUI()  
  {  
    final JInternalFrame if1 = getInternalFrame();  
    JTextArea ta = new JTextArea(5,10);  
    for(int x = 0; x < 25; x++)ta.append(x+"\n");  
    JScrollPane sp = new JScrollPane(ta);  
    if1.getContentPane().add(sp);  
    if1.pack();  
    dp.add(if1);  
    JButton btn = new JButton("Clear");  
    JFrame f = new JFrame();  
    f.getContentPane().add(dp,BorderLayout.CENTER);  
    f.getContentPane().add(btn,BorderLayout.SOUTH);  
    f.setSize(400,300);  
    f.setLocationRelativeTo(null);  
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    f.setVisible(true);  
    btn.addActionListener(new ActionListener(){  
      public void actionPerformed(ActionEvent ae){  
        dp.remove(if1);  
        dp.add(getInternalFrame());  
        dp.revalidate();  
        dp.repaint();  
      }  
    });  
  }  
  public JInternalFrame getInternalFrame()  
  {  
    JInternalFrame internalFrame = new JInternalFrame( "I-F1", true, true, true, true );  
    internalFrame.setLocation(50,50);  
    internalFrame.setSize(150,100);  
    internalFrame.setVisible(true);  
    return internalFrame;  
  }  
  public static void main(String[] args)  
  {  
    SwingUtilities.invokeLater(new Runnable(){  
      public void run(){  
        new Testing().buildGUI();  
      }  
    });  
  }  
}  