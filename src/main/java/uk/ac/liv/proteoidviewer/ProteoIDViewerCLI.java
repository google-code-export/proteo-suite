package uk.ac.liv.proteoidviewer;

import java.awt.EventQueue;

/**
 *
 * @author Fawaz Ghali
 */
public class ProteoIDViewerCLI {
	private ProteoIDViewer proteoIDViewer = null;

	public ProteoIDViewerCLI(final String mzidFile) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				proteoIDViewer = new ProteoIDViewer();
				proteoIDViewer.setVisible(true);
				proteoIDViewer.openCLI(mzidFile);
			}
		});
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		new ProteoIDViewerCLI(args[0]);
	}
}
