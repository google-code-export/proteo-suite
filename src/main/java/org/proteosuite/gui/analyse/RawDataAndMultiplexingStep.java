package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.RawDataTableListener;
import org.proteosuite.gui.listener.TableButtonToggleListener;
import org.proteosuite.gui.tables.RawDataAndMultiplexingTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;

/**
 * 
 * @author SPerkins
 */
public class RawDataAndMultiplexingStep extends JPanel {
	private static final long serialVersionUID = 1L;

	private RawDataAndMultiplexingTable rawDataTable = new RawDataAndMultiplexingTable();
	private final JComboBox<String> multiplexingBox = new JComboBox<>(); 
        private JCheckBox genomeAnnotationBox = new JCheckBox();
        private static RawDataAndMultiplexingStep INSTANCE;

	private RawDataAndMultiplexingStep() {
            
		super(new BorderLayout());
		JLabel stepTitle = new JLabel("Select your raw data and multiplexing:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));

		final JButton continueButton = new JButton("Continue");

		continueButton.setEnabled(false);

		continueButton.addActionListener(new ContinueButtonListener(this));
		
		rawDataTable.getSelectionModel().addListSelectionListener(
				new TableButtonToggleListener(rawDataTable));
		
		rawDataTable.getModel().addTableModelListener(
				new RawDataTableListener(rawDataTable, continueButton));
		rawDataTable.addKeyListener(new RawDataTableKeyListener(this));
                
                multiplexingBox.addItem("iTRAQ 4-plex");
                multiplexingBox.addItem("iTRAQ 8-plex");
                multiplexingBox.addItem("TMT 2-plex");
                multiplexingBox.addItem("TMT 6-plex");
                multiplexingBox.addItem("TMT 8-plex");
                multiplexingBox.addItem("TMT 10-plex");
                if (OpenMSLabelFreeWrapper.checkIsInstalled()) {
                    multiplexingBox.addItem("None (label-free)");
                    multiplexingBox.setSelectedIndex(6);                    
                } else {
                    multiplexingBox.setSelectedIndex(0);
                }		

		JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));

                genomeAnnotationBox.setSelected(true);
                genomeAnnotationBox.setEnabled(false);
		buttonsPanel.add(getRow(new JLabel("Genome Annotation (i.e. ProteoAnnotator) Run?"), genomeAnnotationBox));	
                
                
                multiplexingBox.setEnabled(false);
        buttonsPanel.add(Box.createGlue());
        buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(getRow(new JLabel("Select multiplexing:"), multiplexingBox));
        buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(continueButton);

		add(stepTitle, BorderLayout.PAGE_START);
		add(new JScrollPane(rawDataTable), BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	public static RawDataAndMultiplexingStep getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new RawDataAndMultiplexingStep();
            }
            
            return INSTANCE;
        }
        
        public void refreshFromData() {
		AnalyseData data = AnalyseData.getInstance();
		rawDataTable.clear();
		for (int i = 0; i < data.getRawDataCount(); i++) {
			RawDataFile dataFile = data.getRawDataFile(i);
			rawDataTable.addRawFileRow(dataFile);
		}
	}

	public RawDataAndMultiplexingTable getRawDataTable() {
		return rawDataTable;
	}

	public JComboBox<String> getMultiplexingBox() {
		return multiplexingBox;
	}
        
        public JCheckBox getGenomeAnnotationBox() {
            return genomeAnnotationBox;
        }
        
        private JPanel getRow(Component... components) {
        JPanel row = new JPanel();
        row.setBorder(BorderFactory.createEmptyBorder());
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        for (Component c : components) {
            row.add(c);
        }

        return row;
    }
}