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


    import org.jfree.chart.*;
    import org.jfree.chart.labels.StandardXYItemLabelGenerator;
    import org.jfree.chart.plot.*;
    import org.jfree.chart.renderer.LookupPaintScale;
    import org.jfree.chart.renderer.xy.*;
    import org.jfree.data.xy.*;
    import org.jfree.chart.axis.*;
    import org.jfree.chart.title.*;
    import org.jfree.ui.*;


    public class XYZChart {
       public static int count;

       public XYZChart(String title) {
          XYZChart.count = 0;
          JFrame frame = new JFrame(title);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          JFreeChart chart = createXYZChart();
          int width = 600;
          int height = 600;
          ChartPanel chartPanel = new ChartPanel(chart, width, height, 16, 16,
                width * 10, height * 10, true, true, true, true, true, true);
          frame.setContentPane(chartPanel);
          frame.pack();
          frame.setVisible(true);
       }

       private JFreeChart createXYZChart() {

          String achse1[] = {"0-1000","1000-2000","2000-3000","3000-4000","4000-5000"};
          String achse2[] = {"<1","1-2","2-3","3-5","5-20",">20"};

          myAxis xAxis = new myAxis("x Axis",achse1);
          myAxis yAxis = new myAxis("y Axis",achse2);

          yAxis.setAutoRange(true);

          double[][] data = new double[][] {
                { 226157.0, 6941.0, 3127.0, 3150.0, 6906.0, 2423.0 },
                { 46972.0, 1510.0, 665.0, 698.0, 1330.0, 1018.0 },
                { 12582.0, 651.0, 343.0, 304.0, 403.0, 119.0 },
                { 2754.0, 103.0, 43.0, 48.0, 34.0, 0.0 },
                { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 } };

          XYZDataset xyzset = new XYZArrayDataset(data);

          XYPlot plot = new XYPlot(xyzset, xAxis, yAxis, null);
          XYBlockRenderer r = new XYBlockRenderer();

          StandardXYItemLabelGenerator generator = new StandardXYItemLabelGenerator();

          r.setBaseItemLabelGenerator(generator);
          r.setBaseItemLabelsVisible(true);

          LookupPaintScale ps = new LookupPaintScale(0.0,250000.0, Color.GRAY);

          // create the color spectrum
          int rot = 0;
          int blau = 0;
          int gruen = 0;
          int lv = 0;

          ps.add(0, new Color(0, 0, 0));
          // ps.add(50000, new Color(0, 0, 0));

          // schwarz bis blau - spektrum
          rot = 0;
          blau = 0;
          gruen = 0;
          for (blau = 0; blau < 255; blau++) {
             Color c = new Color(rot, gruen, blau);
             ps.add(lv = lv + 177, c);
          }

          // blau bis türkis - spektrum
          rot = 0;
          blau = 255;
          gruen = 0;
          for (gruen = 0; gruen < 255; gruen++) {
             Color c = new Color(rot, gruen, blau);
             // ps.add(lv, c);
             ps.add(lv = lv + 177, c);
          }

          // türkis bis grün - spektrum
          rot = 0;
          blau = 255;
          gruen = 255;
          for (blau = 255; blau > 0; blau--) {
             Color c = new Color(rot, gruen, blau);
             // ps.add(lv, c);
             ps.add(lv = lv + 177, c);
          }

          // grün bis gelb - spektrum
          rot = 0;
          gruen = 255;
          blau = 0;
          for (rot = 0; rot < 255; rot++) {
             Color c = new Color(rot, gruen, blau);
             // ps.add(lv, c);
             ps.add(lv = lv + 177, c);
          }

          // gelb bis rot - spektrum
          rot = 255;
          gruen = 255;
          blau = 0;
          for (gruen = 255; gruen > 0; gruen--) {
             Color c = new Color(rot, gruen, blau);
             // ps.add(lv, c);
             ps.add(lv = lv + 177, c);
          }

          r.setPaintScale(ps);
          r.setBlockHeight(1.0f);
          r.setBlockWidth(1.0f);
          r.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());

          plot.setRenderer(r);
          plot.setForegroundAlpha(0.90f);

          JFreeChart chart = new JFreeChart("Mehrdimensionales Diagramm",
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);

          NumberAxis scaleAxis = new NumberAxis("Anzahl");
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

       public static void main(String[] args) {
          XYZChart mychart = new XYZChart("Mehrdimensional");
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
          private static final long serialVersionUID = 1L;

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

          //Zuständig für die x-Achse
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

          //Zuständig für die y-Achse
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
