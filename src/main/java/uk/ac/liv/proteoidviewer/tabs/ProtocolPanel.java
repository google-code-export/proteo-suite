package uk.ac.liv.proteoidviewer.tabs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ProtocolPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	final JTextPane protocolTextPane = new JTextPane();

	public ProtocolPanel() {
		setLayout(new BorderLayout());
		protocolTextPane.setContentType("text/html");
		protocolTextPane.setEditable(false);

		setBorder(BorderFactory
				.createTitledBorder("Summary"));
		add(new JScrollPane(protocolTextPane),
				BorderLayout.CENTER);
	}

	public void setProtocolText(String string) {
		protocolTextPane.setText(string);		
	}
}
