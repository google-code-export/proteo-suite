/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;

/**
 *
 * @author fgonzalez
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;


import org.jfree.chart.*;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.title.*;
import org.jfree.ui.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.util.Random;


public class XYZChart extends JInternalFrame {
   
    private double[] mz;
    private double[] rt;
    private double[][] intens;
    private double maxintens;

    public XYZChart(String title, double[] mz, double[] rt, double maxintens) {
      
        //... Setting Windows defaults ...//
        super("2D View <" + title + "> - MS1");
        Icon icon = new ImageIcon(getClass().getResource("/images/icon.gif"));

        setFrameIcon(icon);
        setResizable(true);
        setMaximizable(true);
        setClosable(true);
        setIconifiable(true);

        this.mz = mz;
        this.rt = rt;
        this.maxintens = maxintens;

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JFreeChart chart = createXYZChart(mz, rt);

        final ChartPanel chartPanel = new ChartPanel(chart, true);

        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        chartPanel.setMouseWheelEnabled(true);
        add(chartPanel);
        //frame.setContentPane(chartPanel);
        //frame.pack();
        //frame.setVisible(true);
    }

    private JFreeChart createXYZChart(double[] mz, double[] rt) {

        String seriesx[] = new String[rt.length];
        String seriesy[] = new String[mz.length];

        for (int iI=0; iI<mz.length; iI++)
        {
            seriesy[iI] = Integer.toString(iI);
        }
        for (int iI=0; iI<rt.length; iI++)
        {
            seriesx[iI] = Integer.toString(iI);
        }

        populateData(rt, mz);

        myAxis xAxis = new myAxis("retention time", seriesx);
        myAxis yAxis = new myAxis("m/z", seriesy);

        yAxis.setAutoRange(true);
        yAxis.setAutoRangeIncludesZero(false);
        xAxis.setAutoRange(true);
        xAxis.setAutoRangeIncludesZero(false);
        XYZDataset xyzset = new XYZArrayDataset(this.intens);

        XYPlot plot = new XYPlot(xyzset, xAxis, yAxis, null);

        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.white);

        //... Modifying renderer ...//
        XYBlockRenderer render = new XYBlockRenderer();
        StandardXYItemLabelGenerator generator = new StandardXYItemLabelGenerator();
        render.setBaseItemLabelGenerator(generator);
        render.setBaseItemLabelsVisible(true);

        //... Setting PaintScale ...//
        LookupPaintScale ps = new LookupPaintScale(0.0, this.maxintens, Color.GRAY);

        //... Creating Color Spectrum
        int red = 0;
        int blue = 0;
        int green = 0;
        double gradient = 0;

        ps.add(0, new Color(255, 255, 255));
        red = 255;
        blue = 255;
        green = 255;
        double scale = this.maxintens / 255;
        for (blue = 255; blue > 0; blue--)
        {
            red--;
            green--;
            Color c = new Color(red, green, blue);
            ps.add(gradient = gradient + scale, c);
        }

        render.setPaintScale(ps);
        render.setBlockHeight(1.0f);
        render.setBlockWidth(1.0f);
        render.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());

        plot.setRenderer(render);
        plot.setForegroundAlpha(0.90f);

        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);

        NumberAxis scaleAxis = new NumberAxis("Intensity");
        scaleAxis.setUpperBound(100);
        scaleAxis.setAxisLinePaint(Color.white);
        scaleAxis.setTickMarkPaint(Color.white);
        scaleAxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 12));

        PaintScaleLegend ps_legend = new PaintScaleLegend(ps, scaleAxis);
        ps_legend.setAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        ps_legend.setPadding(new RectangleInsets(5, 5, 5, 5));
        ps_legend.setStripWidth(50);
        ps_legend.setPosition(RectangleEdge.RIGHT);
        ps_legend.setBackgroundPaint(Color.white);

        chart.addSubtitle(ps_legend);
        chart.setBackgroundPaint(Color.white);

        return chart;
    }

    private void populateData(double[] rt, double[] mz) {

      double[][] data = new double[rt.length][mz.length];
      for (int iI=0; iI<rt.length; iI++)
      {
           for (int iJ=0; iJ<mz.length; iJ++)
           {
               //data[iI][iJ] = (iI*100)*(iI*100) + 1000000;
               Random generator = new Random();
               data[iI][iJ] = (generator.nextInt(10000)*10000) + 1000;
           }
      }
      this.intens = data;
    }

    private static class XYZArrayDataset extends AbstractXYZDataset {
        double[][] data;
        int rowCount = 0;
        int columnCount = 0;

        XYZArrayDataset(double[][] data) {
         this.data = data;
         rowCount = data.length;
         columnCount = data[0].length;
        }
        public int getSeriesCount() {
            return 1;
        }

        public Comparable getSeriesKey(int series) {
            return "serie";
        }

        public int getItemCount(int series) {
            return rowCount * columnCount;
        }

        public double getXValue(int series, int item) {
            return (int) (item / columnCount);
        }

        public double getYValue(int series, int item) {
            return item % columnCount;
        }

        public double getZValue(int series, int item) {
            return data[(int) (item / columnCount)][item % columnCount];
        }

        public Number getX(int series, int item) {
            return new Double((int) (item / columnCount));
        }

        public Number getY(int series, int item) {
            return new Double(item % columnCount);
        }

        public Number getZ(int series, int item) {
            return new Double(data[(int) (item / columnCount)][item % columnCount]);
        }
    }
    public class myAxis extends NumberAxis
    {
        String werte[];

        public myAxis(String achsenbez,String[] werte) {
         super(achsenbez);
         this.werte = werte;
        }

        private static final long seriagradientersionUID = 1L;

        @Override
        public java.util.List refreshTicks(java.awt.Graphics2D g2,
                AxisState state,
                java.awt.geom.Rectangle2D dataArea,
                org.jfree.ui.RectangleEdge edge)
        {
         java.util.List tickliste = new ArrayList<NumberTick>();

         if (RectangleEdge.isTopOrBottom(edge))
         {
            tickliste = refreshTicksHorizontal(g2, dataArea, edge);
         }
         else if (RectangleEdge.isLeftOrRight(edge))
         {
            tickliste = refreshTicksVertical(g2, dataArea, edge);
         }

         return tickliste;
        }

        @Override
        public java.util.List refreshTicksHorizontal(Graphics2D g2,
             Rectangle2D dataArea, RectangleEdge edge)
        {
         java.util.List tickliste = new ArrayList<NumberTick>();

         for(int i=0;i<this.werte.length;i++)
         {
            tickliste.add(new NumberTick(i,this.werte[i],TextAnchor.TOP_CENTER,TextAnchor.CENTER,0));
         }

         return tickliste;
        }


        @Override
        public java.util.List refreshTicksVertical(Graphics2D g2,
             Rectangle2D dataArea, RectangleEdge edge)
        {
         java.util.List tickliste = new ArrayList<NumberTick>();

         for(int i=0;i<this.werte.length;i++)
         {
            tickliste.add(new NumberTick(i,this.werte[i],TextAnchor.CENTER_RIGHT,TextAnchor.CENTER,0));
         }

         return tickliste;
      }
   }
}
