package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author SPerkins
 */
public class AnalyseDynamicTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private static AnalyseDynamicTab instance = null;
	public static final JPanel RAW_DATA_AND_MULTIPLEXING_STEP = new RawDataAndMultiplexingStep();
	public static final JPanel DEFINE_CONDITIONS_STEP = new DefineConditionsStep();
	public static final JPanel CREATE_OR_LOAD_IDENTIFICATIONS_STEP = new CreateOrLoadIdentificationsStep();
        public static final JPanel CLEAN_IDENTIFICATIONS_STEP = new CleanIdentificationsStep();
	public static final JPanel LABEL_FREE_STEP = new LabelFreeStep();
	public static final JPanel ITRAQ_STEP = new ITRAQStep();
        public static final JPanel TMT_STEP = new TMTStep();
	public static final JPanel DONE_STEP = new DoneStep();
	private static final AnalyseStatusPanel analysisStatusPanel = new AnalyseStatusPanel();

	private AnalyseDynamicTab() {
		super(new BorderLayout());
		moveToStep(RAW_DATA_AND_MULTIPLEXING_STEP);
	}

	public static AnalyseDynamicTab getInstance() {
		if (instance == null) {
			instance = new AnalyseDynamicTab();
		}

		return instance;
	}

	public void moveToStep(JPanel panel) {
		removeAll();
		add(analysisStatusPanel, BorderLayout.PAGE_START);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(panel, BorderLayout.CENTER);
                repaint();
	}

	public AnalyseStatusPanel getAnalyseStatusPanel() {
		return analysisStatusPanel;
	}
}