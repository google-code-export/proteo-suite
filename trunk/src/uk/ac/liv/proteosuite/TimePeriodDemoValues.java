/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;

import java.text.SimpleDateFormat;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * An example of....
 *
 */
public class TimePeriodDemoValues extends ApplicationFrame {

    /**
     * A demonstration application showing how to....
     *
     * @param title  the frame title.
     */
    public TimePeriodDemoValues(final String title) {

        super(title);

        final XYDataset data1 = createDataset1();
        final XYItemRenderer renderer1 = new XYBarRenderer();

        final DateAxis domainAxis = new DateAxis("Retention Time");
        domainAxis.setVerticalTickLabels(true);
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.HOUR, 1));
        domainAxis.setDateFormatOverride(new SimpleDateFormat("hh:mm"));
        domainAxis.setLowerMargin(0.01);
        domainAxis.setUpperMargin(0.01);
        final ValueAxis rangeAxis = new NumberAxis("Value");

        final XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);

        final XYDataset data2 = createDataset2();
        final StandardXYItemRenderer renderer2
            = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES);
        renderer2.setShapesFilled(true);

        plot.setDataset(1, data2);
        plot.setRenderer(1, renderer2);

        final JFreeChart chart = new JFreeChart("", plot);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);

    }
    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    public XYDataset createDataset1() {

        final TimePeriodValues s1 = new TimePeriodValues("Cat1");
        final TimePeriodValues s2 = new TimePeriodValues("Cat2");
        final Day today = new Day();
        for (int i = 0; i < 24; i++) {
            final Minute m0 = new Minute(0, new Hour(i, today));
            final Minute m1 = new Minute(15, new Hour(i, today));
            final Minute m2 = new Minute(30, new Hour(i, today));
            final Minute m3 = new Minute(45, new Hour(i, today));
            final Minute m4 = new Minute(0, new Hour(i + 1, today));
            s1.add(new SimpleTimePeriod(m0.getStart(), m1.getStart()), Math.random());
            s2.add(new SimpleTimePeriod(m1.getStart(), m2.getStart()), Math.random());
            s1.add(new SimpleTimePeriod(m2.getStart(), m3.getStart()), Math.random());
            s2.add(new SimpleTimePeriod(m3.getStart(), m4.getStart()), Math.random());
        }

        final TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        return dataset;

    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return the dataset.
     */
    public XYDataset createDataset2() {

        final TimePeriodValues s1 = new TimePeriodValues("Cat3");
        final Day today = new Day();
        for (int i = 0; i < 24; i++) {
            final Minute m0 = new Minute(0, new Hour(i, today));
            final Minute m1 = new Minute(30, new Hour(i, today));
            final Minute m2 = new Minute(0, new Hour(i + 1, today));
            s1.add(new SimpleTimePeriod(m0.getStart(), m1.getStart()), Math.random() * 2.0);
            s1.add(new SimpleTimePeriod(m1.getStart(), m2.getStart()), Math.random() * 2.0);
        }

        final TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
        dataset.addSeries(s1);

        return dataset;

    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final TimePeriodDemoValues demo = new TimePeriodDemoValues("Test");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}