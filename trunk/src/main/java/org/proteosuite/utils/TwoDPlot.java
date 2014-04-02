/*
 * --------------------------------------------------------------------------
 * TwoDPlot.java
 * --------------------------------------------------------------------------
 * Description:       Displays mzml raw data as 2D plot
 * Developer:         fgonzalez
 * Created:           08 February 2011
 * Notes:             
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

/**
 * @author fgonzalez
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JInternalFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;

/**
 * This class corresponds to 2D Visualisation of MS raw data.
 */
public class TwoDPlot extends JInternalFrame implements MouseMotionListener {
    /**
     * Generate chart from raw data
     * @param data
     * @return
     */
    public static ChartPanel getTwoDPlot(float[][] data) {
        //ySeeting axis
        final NumberAxis xAxis = new NumberAxis("Retention Time (secs)");
        xAxis.setAutoRangeIncludesZero(true);
        final NumberAxis yAxis = new NumberAxis("m/z");
        yAxis.setAutoRangeIncludesZero(true);
        
        // Graph and values
        final FastScatterPlot plot = new FastScatterPlot(data, yAxis, xAxis);

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.white);
        plot.setPaint(Color.red);

        // Container
        final JFreeChart chart = new JFreeChart("", plot);
        
        // Does nothing?
        // chart.setAntiAlias(true);
        // chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new Dimension(500, 400));
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        panel.setBackground(Color.red);
        panel.setZoomFillPaint(new Color(216, 240, 223, 100));
        panel.setZoomOutlinePaint(new Color(216, 240, 223));
        panel.setMouseWheelEnabled(true);
        panel.setDisplayToolTips(true);
        
        return panel;
    }

    private void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " RT: " + e.getX() + "\n m/z: " + e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        eventOutput("Moved", e);
    }
    
    public void mouseDragged(MouseEvent e) {
        eventOutput("Dragged", e);
    }
}
