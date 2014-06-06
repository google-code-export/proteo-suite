/**
 * ProgressBarDialog.java
 *
 * Created on 15-Dec-2010, 12:53:27
 */

package uk.ac.liv.proteoidviewer.util;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

/**
 *
 * @author fghali
 */
public class ProgressBarDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	/** Creates new form ProgressBarDialog */
	public ProgressBarDialog(Frame parent, boolean modal) {
		super(parent, modal);

		JProgressBar progressBar = new JProgressBar();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		progressBar.setIndeterminate(true);

		add(progressBar);

		pack();
		setLocationRelativeTo(parent);
	}
}