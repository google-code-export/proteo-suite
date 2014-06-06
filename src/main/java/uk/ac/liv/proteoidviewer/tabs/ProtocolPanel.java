package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ProtocolPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	final JTextPane protocolText = new JTextPane();

	public ProtocolPanel() {
		setLayout(new BorderLayout());
		protocolText.setContentType("text/html");
		protocolText.setEditable(false);

		setBorder(BorderFactory
				.createTitledBorder("Summary"));
		add(new JScrollPane(protocolText),
				BorderLayout.CENTER);
	}

	public void setProtocolText(String string) {
		protocolText.setText(string);		
	}
}
