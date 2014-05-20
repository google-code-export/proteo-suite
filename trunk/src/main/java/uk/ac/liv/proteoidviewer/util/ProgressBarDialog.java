/**
 * ProgressBarDialog.java
 *
 * Created on 15-Dec-2010, 12:53:27
 */

package uk.ac.liv.proteoidviewer.util;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

/**
 *
 * @author fghali
 */
public class ProgressBarDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form ProgressBarDialog */
	public ProgressBarDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(parent);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jProgressBar1 = new JProgressBar();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		jProgressBar1.setIndeterminate(true);

		javax.swing.GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jProgressBar1,
								GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(18, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE,
								22, Short.MAX_VALUE).addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ProgressBarDialog dialog = new ProgressBarDialog(new JFrame(),
						true);
				dialog.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JProgressBar jProgressBar1;
	// End of variables declaration//GEN-END:variables

}