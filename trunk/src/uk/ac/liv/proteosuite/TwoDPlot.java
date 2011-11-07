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

public class TwoDPlot extends JInternalFrame implements MouseMotionListener {

    private float[][] data = new float[2][1000000];
    private double[] mz;
    private double[] intens;
    private double[] art;

    //... Needs to be raw data ...//
    public TwoDPlot(final String title, double[] mz, double[] intens, double[] art) {

        //... Setting Windows defaults ...//
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

        //... Filling data ...//
        populateData(mz, intens, art);

        //... Seeting axis ...//
        //final DateAxis xAxis = new DateAxis("Retention Time");
        //xAxis.setDateFormatOverride(new SimpleDateFormat("ss"));
        final NumberAxis xAxis = new NumberAxis("Retention Time");
        xAxis.setAutoRangeIncludesZero(true);
        final NumberAxis yAxis = new NumberAxis("m/z");
        yAxis.setAutoRangeIncludesZero(true);

        //... Graph and values ...//
        //final FastScatterPlot plot = new FastScatterPlot(this.data, xAxis, yAxis);
        final FastScatterPlot plot = new FastScatterPlot(this.data, yAxis, xAxis);
        //final XYPlot plot = new XYPlot(this.data1, xAxis, yAxis, null);

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.white);
        plot.setPaint(Color.red);

        //... Container ...//
        final JFreeChart chart = new JFreeChart("", plot);
        chart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // OK chart.setBackgroundPaint(Color.white);
        // OK chart.setBorderPaint(Color.red);
        // OK chart.setBorderVisible(true);
        

        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 400));

        
        
        //Point2D p = panel.translateScreenToJava2D(MouseEvent.get());
        //Rectangle2D plotArea = panel.getScreenDataArea();
        //XYPlot plot2 = (XYPlot) chart.getPlot(); // your plot
        //double chartX = plot.getxAxis().java2DToValue(p.getX(), plotArea, plot2.getxAxisEdge());
        //double chartY = plot.getyAxis().java2DToValue(p.getY(), plotArea, plot2.getyAxisEdge());

        //panel.add(zoomIn, BorderLayout.EAST);
        //panel.add(zoomOut, BorderLayout.NORTH);
        
        //panel.setHorizontalZoom(true);
        //panel.setVerticalZoom(true);
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        panel.setBackground(Color.red);
        //panel.setMouseZoomable(false);
        //panel.setFillZoomRectangle(false);
        //panel.setHorizontalAxisTrace(true);
        //panel.setVerticalAxisTrace(true);
        panel.setZoomFillPaint(new Color(216,240,223,100));
        panel.setZoomOutlinePaint(new Color(216,240,223));
        panel.setMouseWheelEnabled(true);
        panel.setDisplayToolTips(true);
                
        //setLayout(new GridLayout(2,1));
        //setContentPane(panel);
        //JButton button1 = new JButton("Click");
        //JButton button2 = new JButton("Here");
        //panel.add(button1);
        //panel.add(button2);
        add(panel);

        //JPanel chartmenu = new JPanel();
        //chartmenu.setSize(400, 100);
        //add(chartmenu);
        //addMouseMotionListener(this);
    }

    /**
     * 
     */
    private void populateData(double[] mz, double[] intens, double[] art) {

        int iCounter = 0;
        for (int iI = 0; iI < mz.length; iI++) {
            if (intens[iI] > 100)
            {
                this.data[1][iCounter] = (float) art[iI];
                this.data[0][iCounter] = (float) mz[iI];
                iCounter++;
            }
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
