package org.proteosuite;

import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;

public class SampleXYDataset2 extends AbstractXYDataset
        implements XYDataset, DomainInfo, RangeInfo {

    private static final int DEFAULT_SERIES_COUNT = 5;
    private static final int DEFAULT_ITEM_COUNT = 40;
    private static final double DEFAULT_RANGE = 200;

    private Double[][] xValues;
    private Double[][] yValues;

    private int seriesCount;
    private int itemCount;

    private Number domainMin;
    private Number domainMax;
    private Number rangeMin;
    private Number rangeMax;
    
    private Range domainRange;
    private Range range;

    public SampleXYDataset2() {
        this(DEFAULT_SERIES_COUNT, DEFAULT_ITEM_COUNT);
    }
    public SampleXYDataset2(int seriesCount, int itemCount) {

        this.xValues = new Double[seriesCount][itemCount];
        this.yValues = new Double[seriesCount][itemCount];
        this.seriesCount = seriesCount;
        this.itemCount = itemCount;

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (int series = 0; series < seriesCount; series++) {
            for (int item = 0; item < itemCount; item++) {

                double x = (Math.random() - 0.5) * DEFAULT_RANGE;
                this.xValues[series][item] = new Double(x);
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }

                double y = (Math.random() + 0.5) * 6 * x + x;
                this.yValues[series][item] = new Double(y);
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }
        }

        this.domainMin = new Double(minX);
        this.domainMax = new Double(maxX);
        this.domainRange = new Range(minX, maxX);

        this.rangeMin = new Double(minY);
        this.rangeMax = new Double(maxY);
        this.range = new Range(minY, maxY);

    }
    public Number getX(int series, int item) {
        return this.xValues[series][item];
    }
    public Number getY(int series, int item) {
        return this.yValues[series][item];
    }
    public int getSeriesCount() {
        return this.seriesCount;
    }
    public Comparable getSeriesKey(int series) {
        return "Sample " + series;
    }
    public int getItemCount(int series) {
        return this.itemCount;
    }
    public double getDomainLowerBound() {
        return this.domainMin.doubleValue();
    }
    public double getDomainLowerBound(boolean includeInterval) {
        return this.domainMin.doubleValue();
    }
    public double getDomainUpperBound() {
        return this.domainMax.doubleValue();
    }
    public double getDomainUpperBound(boolean includeInterval) {
        return this.domainMax.doubleValue();
    }
    public Range getDomainBounds() {
        return this.domainRange;
    }
    public Range getDomainBounds(boolean includeInterval) {
        return this.domainRange;
    }
    public Range getDomainRange() {
        return this.domainRange;
    }
    public double getRangeLowerBound() {
        return this.rangeMin.doubleValue();
    }
    public double getRangeLowerBound(boolean includeInterval) {
        return this.rangeMin.doubleValue();
    }
    public double getRangeUpperBound() {
        return this.rangeMax.doubleValue();
    }
    public double getRangeUpperBound(boolean includeInterval) {
        return this.rangeMax.doubleValue();
    }
    public Range getRangeBounds(boolean includeInterval) {
        return this.range;
    }
    public Range getValueRange() {
        return this.range;
    }
    public Number getMinimumDomainValue() {
        return this.domainMin;
    }
    public Number getMaximumDomainValue() {
        return this.domainMax;
    }
    public Number getMinimumRangeValue() {
        return this.domainMin;
    }
    public Number getMaximumRangeValue() {
        return this.domainMax;
    }
}

