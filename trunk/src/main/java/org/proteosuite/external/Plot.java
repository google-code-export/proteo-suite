package org.proteosuite.external;

import org.proteosuite.external.IPC;
import java.awt.Color;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.AxisEntity;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.Range;
import org.jfree.data.function.Function2D;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Plot
        implements Serializable {

   final static double DEFAULT_LESS_THAN = .0001;
   ArrayList<Peak> peaks;
   String title;
   long resolvingPower;
   final static int samples = 1000;
   boolean useBars;
   final public static Color color = new Color(0xFF, 0x55, 0x55);
   JFreeChart jFreeChart;
   XYItemRenderer renderer;
   XYDataset dataset;

   public Plot(Collection<Peak> peaks, String title, long resolvingPower, boolean useBar) {
      this.peaks = new ArrayList<Peak>(peaks);
      this.title = title;
      this.resolvingPower = resolvingPower;
      this.useBars = useBar;
   }

   public JFreeChart makeChart() {


      final FixedRange domain;
      final FixedRange range;
      boolean legend = false;

      double lowestX;
      double highestX;

      if (!useBars) {

         dataset = genNormalPlot();
         //FixedRange range = new FixedRange("Relative Intensity", 0, 101, true);
         //FixedRange domain = new FixedRange("m/z", getLowerBound(), getUpperBound());
         //range = new NumberAxis();
         //domain = new NumberAxis();
         renderer = new XYAreaRenderer(XYAreaRenderer.AREA);

         if (dataset.getItemCount(0) == 0) {
            return null;
         }
         lowestX = dataset.getXValue(0, 0);

         int highestSeries = dataset.getSeriesCount() -1;
         highestX = dataset.getXValue(highestSeries, dataset.getItemCount(highestSeries) - 1);

      } else {
         dataset = genBarPlot();
         renderer = new XYBarRenderer();
         ((XYBarRenderer) renderer).setBarPainter(new StandardXYBarPainter());
         ((XYBarRenderer) renderer).setShadowVisible(false);

         lowestX = peaks.get(0).getMass();
         highestX = peaks.get(peaks.size() - 1).getMass();
      }

      resetPeakColors();

      XYToolTipGenerator toolTipGenerator = new XYToolTipGenerator() {

         public String generateToolTip(XYDataset dataset, int series, int item) {
            return peaks.get(series).getTooltip();
         }
      };
      renderer.setBaseToolTipGenerator(toolTipGenerator);

      //renderer.setSeriesStroke(0, new BasicStroke(1));
      range = new FixedRange("Relative Intensity", 0, 1.01, true);
      range.setNumberFormatOverride(NumberFormat.getPercentInstance());

      //System.out.println(lowestX + "\t" + highestX);


      domain = new FixedRange("m/z", getPeaksLowerBound(lowestX), getPeaksUpperBound(highestX));

      XYPlot plot = new XYPlot(dataset, domain, range, renderer);

      jFreeChart = new JFreeChart(title, plot);
      if (!legend) {
         jFreeChart.removeLegend();
      }
      return jFreeChart;
   }

   public void resetPeakColors() {
      for (int seriesIndex = 0; seriesIndex < dataset.getSeriesCount(); seriesIndex++) {
         setPeakColor(seriesIndex, color);
      }
   }

   public void setPeakColor(double mass, Color color) {
      for (int peakIndex = 0; peakIndex < dataset.getSeriesCount(); peakIndex++) {
         if (dataset.getSeriesKey(peakIndex).equals(mass)) {
            setPeakColor(peakIndex, color);
         }
      }
   }

   public void setPeakColor(int seriesIndex, Color color) {
      renderer.setSeriesPaint(seriesIndex, color);
   }

   Plot showPlot() {
      jFreeChart = makeChart();

      //ch.
      final ChartPanel chartPanel = new ChartPanel(jFreeChart);

      chartPanel.setMaximumDrawHeight(99999);
      chartPanel.setMaximumDrawWidth(99999);

      chartPanel.setPreferredSize(new java.awt.Dimension(975, 400));
      FixedRange domain = (FixedRange) ((XYPlot) jFreeChart.getPlot()).getDomainAxis();
      chartPanel.addChartMouseListener(new RangeChangeListener(chartPanel, domain));
      frame = new ApplicationFrame("Calculated Spectra");
      frame.setContentPane(chartPanel);
      //ChartSerializer.writeChart(frame);

      frame.pack();
      RefineryUtilities.centerFrameOnScreen(frame);
      RefineryUtilities.positionFrameOnScreen(frame, .955, .25);

      frame.setVisible(true);
      return this;
   }
   ApplicationFrame frame;

   XYDataset genNormalPlot() {
      removeSmallPeaks(300);

      DefaultXYDataset normalDataset = new DefaultXYDataset();
      for (Peak p : peaks) {
         double width = p.getMass() / resolvingPower / IPC.TWO_SQRT_TWO_LN_TWO;
         double height = p.getRelInt();
         GaussianFunction f = new GaussianFunction(height, p.getMass(), width);

         double[] bounds = f.getBounds();
         // System.err.printf("Made gaussian func from [%f:%f], bounds are %f - %f%n", i.getMass(), i.getP(), bounds[0], bounds[1]);
         double[][] data = new double[2][samples];
         double step = (bounds[1] - bounds[0]) / (samples - 1);
         for (int index = 0; index < samples; index++) {
            double x = bounds[0] + (step * index);
            double y = f.getValue(x);

            data[0][index] = x;
            data[1][index] = y;
         }
         normalDataset.addSeries(p.getMass(), data);

      }

      return normalDataset;
   }

   XYDataset genBarPlot() {
      double halfBarWidth = 0.0005;

      removeSmallPeaks(2000);


      XYIntervalSeriesCollection barDataset = new XYIntervalSeriesCollection();


      for (Peak tempPeak : peaks) {
         //if ((tempIsotope.getP()) * 10000 > maxP) {
         //double[][] data = new double[2][1];

         //data[0][0] = tempPeak.getMass();
         //data[1][0] = tempPeak.getRelInt();
         //XYBarDataset barDataset = new XYBarDataset(new DefaultXYDataset(), 0.001);
         //((DefaultXYDataset) barDataset.getUnderlyingDataset()).addSeries(title, data);
         XYIntervalSeries xyis = new XYIntervalSeries(tempPeak.getMass());
         xyis.add(tempPeak.getMass(), tempPeak.getMass() - halfBarWidth, tempPeak.getMass() + halfBarWidth, tempPeak.getRelInt(), 0, tempPeak.getRelInt());
         barDataset.addSeries(xyis);


      }



      return barDataset;
   }

   public static double getPeaksUpperBound(
           double highestX) {

      double upperBound = Math.ceil(highestX * TWO) / TWO;
      //System.out.println("------");
      //for (IPC.Results.Entry entry : peaks) {
      //    System.out.println(entry.getMass() + "\t" + entry.getRelInt() + "\t" + (entry.getRelInt()>=DEFAULT_LESS_THAN));
      //}
      //System.out.println("=" + upperBound);
      //System.out.println("------");
      return upperBound;

   }
   private final static double TWO = 2.0;

   public static double getPeaksLowerBound(double lowestX) {
      double lowerBound = Math.floor(lowestX * 2.0) / TWO;
      return lowerBound;

   }

   double getMaxP() {
      double maxP = 0.0;
      for (Peak tempPeak : peaks) {
         if (tempPeak.getP() > maxP) {
            maxP = tempPeak.getP();
         }
      }
      return maxP;
   }

   void removeSmallPeaks(int peaksToLeave) {
      int size = peaks.size();
      for (Iterator<Peak> it = peaks.iterator(); it.hasNext();) {
         Peak tempPeak = it.next();
         if (tempPeak.getRelInt() < DEFAULT_LESS_THAN) {
            it.remove();
         }
      }
      while (peaks.size() > peaksToLeave) {
         Peak leastIntense = peaks.get(0);
         for (Iterator<Peak> it = peaks.iterator(); it.hasNext();) {
            Peak tempPeak = it.next();
            if (tempPeak.getP() < leastIntense.getP()) {
               leastIntense = tempPeak;
            }
         }
         peaks.remove(leastIntense);
      }
      //System.err.printf("Removed %d/%d peaks for graphing%n", size - peaks.size(), size);
   }

   public static class FixedRange
           extends NumberAxis
           implements Serializable {

      boolean veryFixed;
      Range initialRange;

      public FixedRange(String label, double lowerBound, double upperBound, boolean veryFixed) {
         super(label);
         initialRange = new Range(lowerBound, upperBound);
         setDefaultAutoRange(initialRange);
         this.veryFixed = veryFixed;
      }

      public FixedRange(String label, double lowerBound, double upperBound) {
         this(label, lowerBound, upperBound, false);
      }

      void resetDefaultAutoRange() {
         setDefaultAutoRange(initialRange);
      }

      @Override
      protected void autoAdjustRange() {

         if (getDefaultAutoRange() == null) {
            super.autoAdjustRange();

         } else {
            setRange(getDefaultAutoRange());

         }
      }

      @Override
      public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
         //System.err.println("\t!" + getLabel() + ":\t" + range + "\t" + turnOffAutoRange + "\t" + notify);
         if (veryFixed) {
            super.setRange(getDefaultAutoRange(), turnOffAutoRange, notify);
         } else {
            super.setRange(range, turnOffAutoRange, notify);
         }
      }
   }

   static class GaussianFunction
           implements Function2D,
           Serializable {

      double height;
      double center;
      double width;

      public GaussianFunction(double height, double center, double width) {
         this.height = height;
         this.center = center;
         this.width = width;
      }

      public double getValue(double x) {
         return height * Math.pow(Math.E, -(Math.pow(x - center, 2) / (2 * width * width)));
      }

      public double[] getBounds() {
         return getBounds(height * DEFAULT_LESS_THAN);
      }

      public double[] getBounds(double lessThan) {
         double delta = Math.sqrt(-2 * width * width * Math.log(lessThan / height));
         return new double[]{center - delta, center + delta};
      }
   }

   public static class RangeChangeListener
           implements ChartMouseListener,
           Serializable {

      ChartPanel chartPanel;
      FixedRange domain;

      public RangeChangeListener(ChartPanel chartPanel, FixedRange domain) {
         this.chartPanel = chartPanel;
         this.domain = domain;
      }

      public void chartMouseClicked(ChartMouseEvent event) {

         if (event.getEntity() instanceof AxisEntity) {
            FixedRange axis = (FixedRange) ((AxisEntity) event.getEntity()).getAxis();
            Range range = axis.getDefaultAutoRange();
            double step = 0.5;
            if (range.getUpperBound() < 2) {
               step = 0.01;
            }
            SpinnerNumberModel lowerBoundModel = new SpinnerNumberModel(range.getLowerBound(), 0, range.getUpperBound() * 10, step);
            SpinnerNumberModel upperBoundModel = new SpinnerNumberModel(range.getUpperBound(), 0, range.getUpperBound() * 10, step);


            JSpinner lowerBoundSpinner = new JSpinner(lowerBoundModel);
            JSpinner upperBoundSpinner = new JSpinner(upperBoundModel);

            if (axis != domain) {
               lowerBoundSpinner.setEditor(new JSpinner.NumberEditor(lowerBoundSpinner, "0%"));
               upperBoundSpinner.setEditor(new JSpinner.NumberEditor(upperBoundSpinner, "0%"));
            }

            String[] options = {"Change", "Reset", "Cancel"};

            Object[] message = {"Lower Bound: ", lowerBoundSpinner,
               "Upper Bound: ", upperBoundSpinner};

            JOptionPane optionPane = new JOptionPane(message,
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.OK_CANCEL_OPTION, null,
                    options);

            JDialog dialog = optionPane.createDialog(chartPanel, "Change " + axis.getLabel() + " Bounds");
            dialog.setVisible(true);

            if (optionPane.getValue() == options[0]) {
               axis.setDefaultAutoRange(new Range(lowerBoundModel.getNumber().doubleValue(), upperBoundModel.getNumber().doubleValue()));
            } else if (optionPane.getValue() == options[1]) {
               axis.resetDefaultAutoRange();
            }
            if (optionPane.getValue() != options[2]) {
               if (axis == domain) {
                  chartPanel.restoreAutoDomainBounds();
               } else {
                  chartPanel.restoreAutoRangeBounds();
               }
            }
         }
      }

      public void chartMouseMoved(ChartMouseEvent event) {
      }
   }
}


