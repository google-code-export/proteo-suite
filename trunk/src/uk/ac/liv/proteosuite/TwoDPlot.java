/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;

/**
 *
 * @author faviel
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
/**
 * A demo of the fast scatter plot.
 *
 */
public class TwoDPlot extends JInternalFrame {

    /** A constant for the number of items in the sample dataset. */
    private static final int COUNT = 8000;

    /** The data. */
    private float[][] data = new float[2][COUNT];

    /**
     * Creates a new fast scatter plot demo.
     *
     * @param title  the frame title.
     */
    public TwoDPlot(final String title) {

        super("[" + title + "] - 2D view");
        populateData();

        Icon icon = new ImageIcon(".\\src\\images\\icon.gif");
        this.setFrameIcon(icon);
        this.setResizable(true);
        this.setMaximizable(true);        
        this.setClosable(true);
        this.setIconifiable(true);
        
        
        //final NumberAxis domainAxis = new NumberAxis("Retention Time");
        //domainAxis.setAutoRangeIncludesZero(false);
        final DateAxis domainAxis = new DateAxis("Retention Time");
        domainAxis.setDateFormatOverride(new SimpleDateFormat("hh:mm:ss:SS"));
        

        final NumberAxis rangeAxis = new NumberAxis("m/z");
        rangeAxis.setAutoRangeIncludesZero(false);
        
        final FastScatterPlot plot = new FastScatterPlot(this.data, domainAxis, rangeAxis);
        //final TwoDXYPlot plot = new TwoDXYPlot(this.data, domainAxis, rangeAxis);

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);

       //... Plot style ...//
        plot.setBackgroundPaint(Color.white);
        

        plot.setPaint(Color.gray);        
        //plot.zoom(50.00);

        final JFreeChart chart = new JFreeChart("[" + title + "] - " + "MS1", plot);
        
        
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 400));

        
        //Point2D p = panel.translateScreenToJava2D(MouseEvent.get());
        //Rectangle2D plotArea = panel.getScreenDataArea();
        //XYPlot plot2 = (XYPlot) chart.getPlot(); // your plot
        //double chartX = plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot2.getDomainAxisEdge());
        //double chartY = plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot2.getRangeAxisEdge());

        //panel.add(zoomIn, BorderLayout.EAST);
        //panel.add(zoomOut, BorderLayout.NORTH);
        
  //      panel.setHorizontalZoom(true);
    //    panel.setVerticalZoom(true);
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        panel.setBackground(Color.red);

        setContentPane(panel);

    }



    /**
     * Populates the data array with random values.
     */
    private void populateData() {

        for (int i = 0; i < this.data[0].length; i++) {
            final float x = (float) i;
            this.data[0][i] = x;
            this.data[1][i] = (float) Math.random() * COUNT;
        }
        //for (int iI=1; iI <= 300; iI+=10)
        //{
        //    for (int iJ=0; iJ <= 20; iJ++)
        //    {
        //        this.data[0][(iI-1)*20+iJ] = iI * 1; // x
        //        this.data[1][(iI-1)*20+iJ] = iJ;  // y
        //    }
        //}

    }
    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription
                + " (" + e.getX() + "," + e.getY() + ")"
                + " detected on "
                + e.getComponent().getClass().getName());
    }

    public void mouseMoved(MouseEvent e) {
        eventOutput("Mouse moved", e);
    }

    public void mouseDragged(MouseEvent e) {
        eventOutput("Mouse dragged", e);
    }

}
