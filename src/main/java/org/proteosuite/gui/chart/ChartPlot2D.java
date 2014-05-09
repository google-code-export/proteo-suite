package org.proteosuite.gui.chart;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.proteosuite.model.RawMzMLFile;
import org.proteosuite.utils.TwoDPlot;

import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class ChartPlot2D extends AbstractChart {
	private static final int CHART_INDEX = 1;
	private static double LOW_INTENSITY = 10;
	private static byte MS_LEVEL = 1;

	/**
	 * Displays the MS1 raw data as 2D plots
	 * 
	 * @param unmarshaller
	 * @return
	 */
	public static JPanel get2DPlot(MzMLUnmarshaller unmarshaller) {

		// Check if mzML contains MS1 data
		if (unmarshaller.getChromatogramIDs().isEmpty())
			return null;

		MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller
				.unmarshalCollectionFromXpath("/run/spectrumList/spectrum",
						Spectrum.class);

		Set<Point2D.Float> points = new HashSet<Point2D.Float>();
		while (spectrumIterator.hasNext()) {
			Spectrum spectrum = spectrumIterator.next();

			// Identify MS1 data
			byte msLevel = getMSLevel(spectrum.getCvParam());

			if (msLevel != MS_LEVEL)
				continue;

			float rt = getRetentionTime(spectrum.getScanList().getScan().get(0)
					.getCvParam());

			float[] massCharges = null;
			double[] intensities = null;

			// Reading mz Values
			for (BinaryDataArray binaryData : spectrum.getBinaryDataArrayList()
					.getBinaryDataArray()) {
				if (massCharges == null)
					massCharges = getFloat(getMz(binaryData));

				if (intensities == null)
					intensities = getDouble(getIntensity(binaryData));
			}

			addPoints(points, rt, massCharges, intensities);
		}

		float[][] data = convertPoints(points);

		return TwoDPlot.getTwoDPlot(data);
	}

	private static float[][] convertPoints(Set<Float> points) {
		int i = 0;
		float[][] data = new float[2][points.size()];
		for (Point2D.Float point : points) {
			data[0][i] = point.x;
			data[1][i] = point.y;
			i++;
		}

		return data;
	}

	private static void addPoints(final Set<Float> points, float rt,
			float[] massCharges, double[] intensities) {
		for (int i = 0; i < massCharges.length; i++) {
			// Removing low values
			if (intensities[i] <= LOW_INTENSITY)
				continue;

			points.add(new Point2D.Float(massCharges[i], rt));
		}
	}

	public static JPanel get2DPlot(RawMzMLFile dataFile) {
		JPanel fullPanel = new JPanel(new BorderLayout());
		fullPanel.add(getButtonPanel(fullPanel, dataFile),
				BorderLayout.PAGE_END, 0);
		fullPanel.add(get2DPlot(dataFile.getUnmarshaller()),
				BorderLayout.CENTER, CHART_INDEX);

		return fullPanel;
	}

	// TODO: Make this a bit more sane
	private static JPanel getButtonPanel(final JPanel fullPanel, final RawMzMLFile dataFile) {
		JPanel panel = new JPanel(new FlowLayout());

		panel.add(new JLabel("MS Level:"));
		final JComboBox<Integer> msLevel = new JComboBox<>();
		msLevel.addItem(1);
		msLevel.addItem(2);
		msLevel.setSelectedIndex(MS_LEVEL - 1);

		panel.add(msLevel);

		final JTextField threshold = new JTextField(String.valueOf(LOW_INTENSITY), 5);

		panel.add(new JLabel("Minimum Intensity:"));
		panel.add(threshold);

		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ChartPlot2D.LOW_INTENSITY = Double.parseDouble(threshold
							.getText());
				} catch (NumberFormatException exception) {
					// User entered non-numeric data
					threshold.setText(String.valueOf(ChartPlot2D.LOW_INTENSITY));
				}
				ChartPlot2D.MS_LEVEL = Byte.parseByte(msLevel.getItemAt(
						msLevel.getSelectedIndex()).toString());
				fullPanel.remove(CHART_INDEX);
				fullPanel.add(
						ChartPlot2D.get2DPlot(dataFile.getUnmarshaller()),
						BorderLayout.CENTER, CHART_INDEX);
				fullPanel.revalidate();
			}
		});
                
		panel.add(refresh);

		return panel;
	}
}