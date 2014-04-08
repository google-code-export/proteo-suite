package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.proteosuite.Launcher;
import org.proteosuite.utils.ExceptionCatcher;

public class MemoryChangeListener implements ActionListener {
	private final short memory;

	public MemoryChangeListener(short memory) {
		this.memory = memory;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (!showWarning())
			return;

		File currentJar = null;
		try {
			currentJar = new File(Launcher.class.getProtectionDomain()
					.getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			ExceptionCatcher.reportException(e);
			e.printStackTrace();
		}

		final String javaBin = System.getProperty("java.home") + File.separator
				+ "bin" + File.separator + "java";

		final List<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-Xmx" + memory + "G");
		command.add("-jar");
		command.add(currentJar.getPath());

		ProcessBuilder pb = new ProcessBuilder(command);
		pb.inheritIO();
		try {
			pb.start();
			System.exit(0);
		} catch (IOException e) {
			ExceptionCatcher.reportException(e);
			e.printStackTrace();
		}
	}

	private boolean showWarning() {
		int returnValue = JOptionPane
				.showConfirmDialog(
						null,
						"Modifying memory will require ProteoSuite to restart and all unsaved data maybe lost. \n"
								+ "Ensure the value selected is not greater than the available system memorry!",
						"Are you sure?", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);

		if (returnValue == JOptionPane.OK_OPTION)
			return true;

		return false;
	}

}
