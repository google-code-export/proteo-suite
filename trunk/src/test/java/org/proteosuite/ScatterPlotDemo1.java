/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteosuite;

/* ---------------------
 * ScatterPlotDemo1.java
 * ---------------------
 * (C) Copyright 2002-2006, by Object Refinery Limited.
 *
 */

import org.proteosuite.SampleXYDataset2;
import java.awt.Color;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ShapeUtilities;

/**
 * A demo scatter plot.
 */
public class ScatterPlotDemo1 extends ApplicationFrame {

    /**
     * A demonstration application showing a scatter plot.
     *
     * @param title  the frame title.
     */
    public ScatterPlotDemo1(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot("2D View",
                "X", "Y", dataset, PlotOrientation.VERTICAL, true, false, false);
 
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setNoDataMessage("NO DATA");
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        
        XYLineAndShapeRenderer renderer 
                = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setUseOutlinePaint(true);
        
        Shape square = new Rectangle2D.Float(100.0f, 100.0f, 100.0f, 100.0f);
        plot = (XYPlot) chart.getPlot();
        renderer.setSeriesShape(0, square);
        renderer.setSeriesShape(1, square);
        renderer.setSeriesShape(2, square);
        renderer.setSeriesShape(3, square);
        renderer.setSeriesShape(4, square);


        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        domainAxis.setTickMarkInsideLength(2.0f);
        domainAxis.setTickMarkOutsideLength(0.0f);
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickMarkInsideLength(2.0f);
        rangeAxis.setTickMarkOutsideLength(0.0f);
        
        return chart;
    }
    
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     * 
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(new SampleXYDataset2());
        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setVerticalAxisTrace(true);
        //chartPanel.setHorizontalAxisTrace(true);
        // popup menu conflicts with axis trace
        chartPanel.setPopupMenu(null);
        
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        return chartPanel;
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        ScatterPlotDemo1 demo = new ScatterPlotDemo1("2D View");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
