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
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class XYBlockChartDemo1 extends ApplicationFrame {

    public XYBlockChartDemo1(String title) {

        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 400));
        
        setContentPane(chartPanel);

    }

    private static JFreeChart createChart(XYZDataset dataset) {

        NumberAxis xAxis = new NumberAxis("X");
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        NumberAxis yAxis = new NumberAxis("Y");
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setLowerMargin(0.0);
        yAxis.setUpperMargin(0.0);

        XYBlockRendererbk renderer = new XYBlockRendererbk();
        //PaintScale scale = new GrayPaintScale(-2.0, 1.0);
        PaintScalebk scale = new GrayPaintScale(-2.0, 1.0);
        renderer.setPaintScale(scale);
        
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        JFreeChart chart = new JFreeChart("", plot);
        chart.removeLegend();
        chart.setBackgroundPaint(Color.white);
        return chart;
    }

     /**
     * Creates a sample dataset.
     */
    private static XYZDataset createDataset() {

        return new XYZDataset()
        {
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                return 1000;
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return item;
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return item;
            }
            public Number getZ(int series, int item) {
                return new Double(getZValue(series, item));
            }
            public double getZValue(int series, int item) {
                double x = getXValue(series, item);
                double y = getYValue(series, item);
                //return Math.sin(Math.sqrt(x * x + y * y) / 5.0);
                return (x / 1000000);
            }
            public void addChangeListener(DatasetChangeListener listener) {
               // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
              // ignore
            }
            public DatasetGroup getGroup() {
               return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "sin(sqrt(x + y))";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }
    public static JPanel createDemoPanel() {
        return new ChartPanel(createChart(createDataset()));
    }

    public static void main(String[] args) {
        XYBlockChartDemo1 demo = new XYBlockChartDemo1("2D View");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}