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


public class XYZChart extends JInternalFrame {
   //public static int count;
    private double[] mz;
    private double[] rt;
    private double[] intens;

   public XYZChart(String title, double[] mz, double[] rt) {
      //XYZChart.count = 0;
      //JFrame frame = new JFrame(title);
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
       
      //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      final JFreeChart chart = createXYZChart(mz, rt);
      final ChartPanel chartPanel = new ChartPanel(chart, true);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 400));
      chartPanel.setMouseWheelEnabled(true);
      add(chartPanel);
//      frame.setContentPane(chartPanel);
//      frame.pack();
//      frame.setVisible(true);
   }

   private JFreeChart createXYZChart(double[] mz, double[] rt) {

      //String seriesx[] = {"0:05","0:10","0:15","0:20","0:25"};
      //String seriesy[] = {"200","250","300","350","400", "450", "500", "550", "600", "650", "700"};
      String seriesx[] = new String[1000];
      String seriesy[] = new String[100];

      for (int iI=0; iI<100; iI++)
      {
          seriesx[iI] = Integer.toString(iI);
          seriesy[iI] = Integer.toString(iI);
      }
     

      //myAxis xAxis = new myAxis("retention time", seriesx);
      //myAxis yAxis = new myAxis("m/z", seriesy);

      myAxis xAxis = new myAxis("retention time", seriesx);
      myAxis yAxis = new myAxis("m/z", seriesy);

      yAxis.setAutoRange(true);

//      double[][] data = new double[][]
//      {
//            { 22060157.0, 6009401.0, 3100027.0, 3100050.0, 6900006.0, 2400023.0 },
//            { 40069072.0, 1500010.0, 600605.0, 600908.0, 1300300.0, 1000018.0 },
//            { 10200582.0, 650001.0, 30043.0, 300004.0, 400003.0, 10019.0 },
//            { 270054.0, 10003.0, 40003.0, 400080.0, 3000004.0, 0.0 },
//            { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }
//      };
      double[][] data = new double[100][100];
      for (int iI=0; iI<100; iI++)
      {
           for (int iJ=0; iJ<100; iJ++)
           {
               data[iI][iJ] = iI*iI*100 + 1000000;
           }
      }


      XYZDataset xyzset = new XYZArrayDataset(data);

      XYPlot plot = new XYPlot(xyzset, xAxis, yAxis, null);
      XYBlockRenderer r = new XYBlockRenderer();

      StandardXYItemLabelGenerator generator = new StandardXYItemLabelGenerator();

      r.setBaseItemLabelGenerator(generator);
      r.setBaseItemLabelsVisible(true);
      double maxValue = 15000000.0;

      LookupPaintScale ps = new LookupPaintScale(0.0, maxValue, Color.GRAY);

      // create the color spectrum
      int red = 0;
      int blue = 0;
      int green = 0;
      double gradient = 0;

      ps.add(0, new Color(255, 255, 255));
      red = 255;
      blue = 255;
      green = 255;
      double scale = maxValue / 255;
      for (blue = 255; blue > 0; blue--) {
          red--;
          green--;
         Color c = new Color(red, green, blue);
         ps.add(gradient = gradient + scale, c);
      }

//      ps.add(0, new Color(0, 0, 0));
//      // ps.add(50000, new Color(0, 0, 0));
//
//
//      red = 0;
//      blue = 0;
//      green = 0;
//      for (blue = 0; blue < 255; blue++) {
//         Color c = new Color(red, green, blue);
//         ps.add(gradient = gradient + 177, c);
//      }
//
//
//      red = 0;
//      blue = 255;
//      green = 0;
//      for (green = 0; green < 255; green++) {
//         Color c = new Color(red, green, blue);
//         ps.add(gradient = gradient + 177, c);
//      }
//
//
//      red = 0;
//      blue = 255;
//      green = 255;
//      for (blue = 255; blue > 0; blue--) {
//         Color c = new Color(red, green, blue);
//         ps.add(gradient = gradient + 177, c);
//      }
//
//
//      red = 0;
//      green = 255;
//      blue = 0;
//      for (red = 0; red < 255; red++) {
//         Color c = new Color(red, green, blue);
//         ps.add(gradient = gradient + 177, c);
//      }
//
//
//      red = 255;
//      green = 255;
//      blue = 0;
//      for (green = 255; green > 0; green--) {
//         Color c = new Color(red, green, blue);
//         ps.add(gradient = gradient + 177, c);
//      }

      r.setPaintScale(ps);
      r.setBlockHeight(1.0f);
      r.setBlockWidth(1.0f);
      r.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());

      plot.setRenderer(r);
      plot.setForegroundAlpha(0.90f);

      JFreeChart chart = new JFreeChart("",
            JFreeChart.DEFAULT_TITLE_FONT, plot, false);

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
         return new Double(data[(int) (item / columnCount)][item
               % columnCount]);
      }
   }

//   public static void main(String[] args) {
//      XYZChart mychart = new XYZChart("");
//   }
    public String[] DoubleToStringArray(double[] darray) throws Exception
    {
        if (darray != null)
        {
            String sarray[] = new String[darray.length];
            for (int iI = 0; iI < darray.length; iI++)
            {
                sarray[iI] = Double.toString(darray[iI]);
                System.out.println(sarray[iI]);
            }
            return sarray;
        }
        return null;
    }
   public class myAxis extends NumberAxis
   {
      String werte[];

      public myAxis(String achsenbez,String[] werte) {
         super(achsenbez);
         this.werte = werte;
      }
      /**
       *
       */
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
