package algorithms.weiszfeld;

import java.util.Arrays;

/**
 * N-dimensional Point
 */
public class Point implements Comparable<Point>, Cloneable {
    private double values[];

    public double[] getValues() {
        return values;
    }
    
    public Point(double []values) {
        this.values = values;
    }

    public Point(int dimensions) {
        values = new double[dimensions];
        Arrays.fill(values, 0d);
    }

    public Point add(Point other) {
        for (int i = 0; i < values.length; i++) {
            values[i] += other.values[i];
        }
        return this;
    }

    public Point deduce(Point other) {
        for (int i = 0; i < values.length; i++) {
            values[i] -= other.values[i];
        }
        return this;
    }

    public static Point substraction(Point a, Point b) {
        Point res = new Point(a.values.length);
        for (int i = 0; i < a.values.length; i++) {
            res.values[i] = a.values[i] - b.values[i];
        }
        return res;
    }

    public Point multiply(double k) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= k;
        }
        return this;
    }

    public double getNormToThe2() {
        double res = 0;
        for (int i = 0; i < values.length; i++) {
            res += values[i]*values[i];
        }
        return res;
    }

    // euclidean distance as norm
    public double getNorm() {
        return Math.sqrt(getNormToThe2());
    }

    @Override
    public int compareTo(Point point) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != point.values[i]) {
                double dif = values[i] - point.values[i];
                if (dif > 0d) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return this.compareTo(other) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(this.values);
        return hash;
    }

    public static Point multiply(Point a, double k) {
        Point res = new Point(a.values.length);

        res.add(a);
        res.multiply(k);

        return res;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Point cloned = (Point)super.clone();
        cloned.values = cloned.values.clone();
        return cloned;
    }
    
    
    
    public String showValues() {
        String s = "";
        for (int i = 0; i < values.length; i++) {
            if (i != 0) {
                s += ", ";
            }
            s += String.valueOf(values[i]);
        }
        return s;
    }
}
