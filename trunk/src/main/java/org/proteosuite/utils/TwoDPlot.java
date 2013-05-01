/*
 * --------------------------------------------------------------------------
 * TwoDPlot.java
 * --------------------------------------------------------------------------
 * Description:       Displays mzml raw data as 2D plot
 * Developer:         FG
 * Created:           08 February 2011
 * Notes:             
 * Read our documentation under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */

package org.proteosuite.utils;

/**
 * @author FG
 */
import java.awt.Color;
import java.awt.Cursor;
import java.net.URL;
import java.net.MalformedURLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import java.awt.event.MouseMotionListener;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import javax.swing.JButton;
import org.jfree.data.RangeType;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateAxis;
import java.text.SimpleDateFormat;
import org.jfree.ui.RectangleEdge;
import java.awt.geom.Point2D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.geom.Line2D;

/**
 * This class corresponds to 2D Visualisation of MS raw data. This is under construction ...//
 * @author faviel
 * @param title - Window title ...//
 * @param mz - m/z values
 * @param intensity - intensity values
 * @param art - RT values 
 */
public class TwoDPlot extends JInternalFrame implements MouseMotionListener {

    private float[][] data = new float[2][1000000];
    private float[] mz;
    private float[] intens;
    private float[] art;

    //... Generate chart from raw data ...//
    public TwoDPlot(final String title, float[] mz, float[] intens, float[] art) {            
        //... Windows default settings ...//
        super("2D View <" + title + "> - MS1");
        Icon icon = new ImageIcon(getClass().getResource("/images/icon.gif"));
        setFrameIcon(icon);
        setResizable(true);
        setMaximizable(true);
        setClosable(true);
        setIconifiable(true);
        this.mz = mz;
        this.intens = intens;
        this.art = art;
        
        //CheckMemory chm = new CheckMemory("Before filling arrays");
        
        //... Filling data ...//
        populateData(mz, intens, art);

        //... Seeting axis ...//
        final NumberAxis xAxis = new NumberAxis("Retention Time (secs)");
        xAxis.setAutoRangeIncludesZero(true);
        final NumberAxis yAxis = new NumberAxis("m/z");
        yAxis.setAutoRangeIncludesZero(true);

        //... Graph and values ...//
        final FastScatterPlot plot = new FastScatterPlot(this.data, yAxis, xAxis);

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.white);
        plot.setPaint(Color.red);

        //... Container ...//
        final JFreeChart chart = new JFreeChart("", plot);
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 400));
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        panel.setBackground(Color.red);
        panel.setZoomFillPaint(new Color(216,240,223,100));
        panel.setZoomOutlinePaint(new Color(216,240,223));
        panel.setMouseWheelEnabled(true);
        panel.setDisplayToolTips(true);
        add(panel);
    }
    /**---------------------------------
     * Populate array
     * @param mz - m/z values 
     * @param intens - intensity values 
     * @param art - RT values 
     ----------------------------------*/
    private void populateData(float[] mz, float[] intens, float[] art) {
        int iCounter = 0;
        for (int iI = 0; iI < mz.length; iI++) {
            this.data[1][iCounter] = (float) art[iI];
            this.data[0][iCounter] = (float) mz[iI];
            iCounter++;
        }
    }

    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " RT: " + e.getX() + "\n m/z: " + e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        eventOutput("Moved", e);
    }
    
    public void mouseDragged(MouseEvent e) {
        eventOutput("Dragged", e);
    }

}
