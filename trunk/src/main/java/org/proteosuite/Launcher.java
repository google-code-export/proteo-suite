package org.proteosuite;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.UpdateCheck;

public class Launcher {
	/**
	 * Main
	 * 
	 * @param args
	 *            the command line arguments (leave empty)
	 * @return void
	 */
	public static void main(String args[]) {
		// Setting standard look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException exception) {
			exception.printStackTrace();
		}
		
		if (!OpenMSLabelFreeWrapper.checkIsInstalled()) {
			int result = JOptionPane
					.showConfirmDialog(
							null,
							"You do not appear to have openMS installed.\nYou need to install openMS in able to use the label-free quantiation feature.\n"
									+ "OpenMS is available at:\nhttp://open-ms.sourceforge.net/\nTo install now, click \"Yes\" to be directed to the openMS web site.",
							"openMS Not Installed!", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				OpenURL.open("http://open-ms.sourceforge.net/");
				return;
			}
		}

		SwingWorker<String, String> checkVersion = new UpdateWorker();
		checkVersion.execute();

		// Create and display the form
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ProteoSuite().setVisible(true);
			}
		});
	}

	private static class UpdateWorker extends SwingWorker<String, String> {
		@Override
		protected String doInBackground() throws Exception {
			return UpdateCheck.hasUpdate(ProteoSuite.PROTEOSUITE_VERSION);
		}

		@Override
		protected void done() {
			try {
				String newVersion = get();

				if (newVersion == null)
					return;

				int result = JOptionPane
						.showConfirmDialog(
								null,
								"There is a new version of ProteoSuite available\n Click OK to visit the download page.",
								"Information", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.INFORMATION_MESSAGE);

				if (result == JOptionPane.OK_OPTION)
					OpenURL.open(newVersion);
			} catch (Exception ignore) {
				// Most likely no Internet connection, do nothing!
			}
		}
	}
}
