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
import java.awt.Paint;
import java.io.Serializable;

import org.jfree.util.PublicCloneable;

/**
 * A paint scale that returns shades of gray.
 *
 * WARNING: THIS CLASS IS NOT PART OF THE STANDARD JFREECHART API AND IS
 * SUBJECT TO ALTERATION OR REMOVAL.  DO NOT RELY ON THIS CLASS FOR
 * PRODUCTION USE.  Please experiment with this code and provide feedback.
 */
public class GrayPaintScale
        implements PaintScale, PublicCloneable, Serializable {

    /** The minimum value. */
    private double min;

    /** The maximum value. */
    private double max;

    /**
     * Creates a new <code>GrayPaintScale</code> instance with default values.
     */
    public GrayPaintScale() {
        this(0.0, 1.0);
    }

    /**
     * Creates a new paint scale for values in the specified range.
     *
     * @param min  the minimum value.
     * @param max
     */
    public GrayPaintScale(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Returns a paint for the specified value.
     *
     * @param value  the value.
     *
     * @return A paint for the specified value.
     */
    public Paint getPaint(double value) {
        double v = Math.max(value, this.min);
        v = Math.min(v, this.max);
        int g = (int) ((value - this.min) / (this.max - this.min) * 255.0);
        return new Color(g, g, g);
    }

    /**
     * Tests this <code>GrayPaintScale</code> instance for equality with an
     * arbitrary object.  This method returns <code>true</code> if and only
     * if:
     * <ul>
     * <li><code>obj</code> is not <code>null</code>;</li>
     * <li><code>obj</code> is an instance of <code>GrayPaintScale</code>;</li>
     * </ul>
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GrayPaintScale)) {
            return false;
        }
        GrayPaintScale that = (GrayPaintScale) obj;
        if (this.min != that.min) {
            return false;
        }
        if (this.max != that.max) {
            return false;
        }
        return true;
    }

    /**
     * Returns a clone of this <code>GrayPaintScale</code> instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning this
     *     instance.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
