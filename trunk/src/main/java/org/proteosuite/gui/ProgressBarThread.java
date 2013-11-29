package org.proteosuite.gui;

import java.awt.Frame;

import org.proteosuite.utils.ProgressBarDialog;

/**
 * 
 * @author Andrew Collins
 */
public class ProgressBarThread extends Thread implements Runnable {

	final ProgressBarDialog progressBarDialog;

	public ProgressBarThread(String threadName, Frame parent, boolean modal,
			String sThread) {
		super(threadName);
		progressBarDialog = new ProgressBarDialog(parent, modal, sThread);
	}

	@Override
	public void run() {
		// Progress Bar
		progressBarDialog.setTitle("Reading MGF files");
		progressBarDialog.setVisible(true);
	}

	public void setTitle(String title) {
		progressBarDialog.setTitle(title);

	}

	public void setVisible(boolean state) {
		progressBarDialog.setVisible(state);
	}

	public void dispose() {
		progressBarDialog.dispose();

	}
}
