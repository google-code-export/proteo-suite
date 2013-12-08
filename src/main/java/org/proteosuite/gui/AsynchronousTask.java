package org.proteosuite.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AsynchronousTask extends SwingWorker<Object, Object> {
	private JButton btnCancel;
	private JLabel jlExecuting;
	private JProgressBar jpbStatusBar;
	private static JDialog popup;
	private static Queue<Runnable> runnableTasks = new ConcurrentLinkedQueue<Runnable>();

	/**
	 * Creates new form ProgressBarDialog
	 * 
	 * @param parent
	 * @param modal
	 * @param sThread
	 */
	public AsynchronousTask(Frame parent) {
		jpbStatusBar = new JProgressBar();
		btnCancel = new JButton("Cancel");
		jlExecuting = new JLabel();

		jlExecuting.setBorder(new EmptyBorder(10, 10, 10, 10));
		jpbStatusBar.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnCancel.setBorder(new EmptyBorder(10, 10, 10, 10));

		popup = new JDialog(parent, true);
		initComponents(popup);
		popup.setLocationRelativeTo(parent);
		popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popup.setResizable(false);
	}

	public void setTaskName(String sExecutingTask) {
		jlExecuting.setText(sExecutingTask);
	}

	/**
	 * This method is called from within the constructor to initialise the form.
	 */
	private void initComponents(JDialog popup) {
		popup.setMinimumSize(new Dimension(400, 100));
		popup.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		jpbStatusBar.setIndeterminate(true);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCancel.setEnabled(false);
				cancel(true);
				runnableTasks.clear();
			}
		});

		popup.setLayout(new BoxLayout(popup.getContentPane(), BoxLayout.Y_AXIS));
		popup.add(jlExecuting);
		popup.add(jpbStatusBar);
		popup.add(btnCancel);

		popup.pack();
	}

	@Override
	protected Object doInBackground() throws Exception {
		while (true)
		{
			Runnable runnableTask = runnableTasks.poll();
			if (runnableTask == null)
				break;
			
			runnableTask.run();
		}
		popup.dispose();
		return null;
	}
	
	@Override
	protected void done() {
		popup.dispose();		
	}

	public void addTask(Runnable runnableTask) {
		runnableTasks.add(runnableTask);
	}

	public void showDialog() {
		if (runnableTasks.size() > 0)
			popup.setVisible(true);
	}
}