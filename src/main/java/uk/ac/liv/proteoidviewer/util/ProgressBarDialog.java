/**
 * ProgressBarDialog.java
 *
 * Created on 15-Dec-2010, 12:53:27
 */

package uk.ac.liv.proteoidviewer.util;

import java.awt.Frame;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
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

	private void initComponents() {

		JProgressBar jProgressBar1 = new JProgressBar();

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
	}
}