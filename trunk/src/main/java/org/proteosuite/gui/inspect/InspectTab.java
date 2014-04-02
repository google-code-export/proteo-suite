package org.proteosuite.gui.inspect;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.proteosuite.gui.listener.InspectComboListener;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.QuantDataFile;
import org.proteosuite.model.RawDataFile;

/**
 * 
 * @author SPerkins
 */
public class InspectTab extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final byte PANEL_BLANK = 0;
	public static final byte PANEL_RAW = 1;
	public static final byte PANEL_IDENT = 2;
	public static final byte PANEL_QUANT = 3;

	private static final int CONTENT_PANEL_INDEX = 1;

	private BorderLayout layout = new BorderLayout();
	private final JComboBox<String> dataFileComboBox = new JComboBox<String>();
	private final InspectModel inspectModel = AnalyseData.getInstance()
			.getInspectModel();
	private static InspectTab instance = null;

	private InspectTab() {
		setLayout(layout);

		dataFileComboBox.addItemListener(new InspectComboListener());

		add(getHeaderPanel(), BorderLayout.PAGE_START);
		setInspectType(PANEL_BLANK);
	}

	public void setInspectType(byte panelType) {
		Component content = null;
		switch (panelType) {
		case PANEL_BLANK:
		case PANEL_IDENT:
			content = new JPanel();
			break;
		case PANEL_RAW:
			content = new InspectRaw();
			break;
		case PANEL_QUANT:
			content = new InspectQuant();
			break;
		default:
			throw new IllegalArgumentException("Unknown panel type");
		}

		if (getContentPanel() != null)
			remove(CONTENT_PANEL_INDEX);

		addImpl(content, BorderLayout.CENTER, CONTENT_PANEL_INDEX);
		repaint();
		revalidate();
	}

	public Component getContentPanel() {
		try {
			return getComponent(CONTENT_PANEL_INDEX);
		} catch (ArrayIndexOutOfBoundsException exception) {
			return null;
		}
	}

	public Component getHeaderPanel() {
		JPanel inspectHeader = new JPanel();
		inspectHeader.setLayout(new FlowLayout());
		inspectHeader.add(new JLabel("Select data file: "));
		inspectHeader.add(dataFileComboBox);

		return inspectHeader;
	}

	public static InspectTab getInstance() {
		if (instance == null)
			instance = new InspectTab();

		return instance;
	}

	public String getSelectedFile() {
		return (String) dataFileComboBox.getSelectedItem();
	}

	public void refreshComboBox() {
		dataFileComboBox.removeAllItems();
		for (RawDataFile dataFile : inspectModel.getRawData()) {
			dataFileComboBox.addItem(dataFile.getFileName());
		}

		for (IdentDataFile identFile : inspectModel.getIdentData()) {
			dataFileComboBox.addItem(identFile.getFileName());
		}

		for (QuantDataFile quantFile : inspectModel.getQuantData()) {
			dataFileComboBox.addItem(quantFile.getFileName());
		}
	}
}
