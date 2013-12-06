package org.proteosuite.gui;

import java.awt.Frame;

import org.proteosuite.utils.ProgressBarDialog;

/**
 * 
 * @author Andrew Collins
 */
public class ProgressBarThread extends Thread implements Runnable {

	ProgressBarDialog progressBarDialog;
	private Frame parent;
	private boolean modal;
	private String threadName;

	public ProgressBarThread(Frame parent, boolean modal, String threadName) {
		super(threadName);
		this.parent = parent;
		this.modal = modal;
		this.threadName = threadName;
	}

	@Override
	public void run() {
		// Progress Bar
		progressBarDialog = new ProgressBarDialog(parent, modal, threadName);
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
