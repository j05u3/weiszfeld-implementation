package algorithms.weiszfeld;

import algorithms.Algorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by josue on 12/08/15.
 * To get the geometric median for a set of n-dimensional points.
 * Based on the Modified Weiszfeld Method from
 * @link{http://ie.technion.ac.il/Home/Users/becka/Weiszfeld_review-v3.pdf}
 * described up to the section 6.3
 */
public class WeiszfeldAlgorithm implements Algorithm<Input, Output> {
    
    @Override
    public Output process(Input input) {

        // filtering repeated points
        Map<Point, Double> map = new HashMap<>();
        for (WeightedPoint wPoint : input.getPoints()) {
            Point point = wPoint.getPoint();
            if (map.containsKey(point)) {
                map.put(point, map.get(point) + wPoint.getWeight());
            } else {
                map.put(point, wPoint.getWeight());
            }
        }
        
        // anchor points
        List<WeightedPoint> aPoints = new ArrayList<>(map.size());
        for (Map.Entry<Point, Double> entry : map.entrySet()) {
            WeightedPoint wPoint = new WeightedPoint();
            wPoint.setPoint(entry.getKey());
            wPoint.setWeight(entry.getValue());
            aPoints.add(wPoint);
        }

        int n = input.getDimension();
        int maxIterations = Integer.MAX_VALUE;
        if (input.getMaxIterations() != null) {
            maxIterations = input.getMaxIterations();
        }
        Double permissibleError = input.getPermissibleError();
        if (permissibleError == null) {
            permissibleError = Double.MIN_VALUE;
        }
        
        // choosing starting point
        Point startPoint = null;
        double mini = Double.POSITIVE_INFINITY;
        for (WeightedPoint wPoint : aPoints) {
            double eval = evaluateF(wPoint.getPoint(), aPoints);
            if (eval < mini) {
                mini = eval;
                startPoint = wPoint.getPoint();
            }
        }

        Point x = null;
        try {
            x = (Point)startPoint.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(WeiszfeldAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Point lastX;
        double error;
        int iterationCounter = 0;
        do {
            lastX = x;
            if (map.containsKey(x)) {
                Point rj = R(x, aPoints, n);
                double wj = map.get(x);
                if (rj.getNorm() > wj) {
                    x = operatorS(x, wj, rj, aPoints, n);
                }
            } else {
                x = operatorT(x, aPoints, n);
            }
            error = Point.substraction(x, lastX).getNorm();
            iterationCounter++;
        } while (error > permissibleError && iterationCounter < maxIterations);
        /* Stops whenever the error is less than or equal the permissibleError
        *  or reaches the maximum number of iterations.
        */

        Output output = new Output();
        output.setPoint(x);
        output.setLastError(error);
        output.setNumberOfIterations(iterationCounter);
        
        return output;
    }
    private Point operatorT(Point x, List<WeightedPoint> aPoints, int dimension) {
        Point result = new Point(dimension);

        double weightsSum = 0;
        for (WeightedPoint a: aPoints) {
            double w = a.getWeight();
            double curWeight = w/Point.substraction(x,a.getPoint()).getNorm();
            Point cur = Point.multiply(a.getPoint(), curWeight);

            weightsSum += curWeight;
            result.add(cur);
        }

        return result.multiply(1d/weightsSum);
    }
    
    private Point operatorS(Point aj, double wj, Point rj
            , List<WeightedPoint> aPoints, int dimension) {
        double rjNorm = rj.getNorm();        
        Point dj = new Point(dimension);
        dj.add(rj);
        dj.multiply(-1.0/rjNorm);
        
        // calculating tj (stepsize) taken from Vardi and Zhang
        double lj = operatorL(aj, aPoints);
        double tj = (rjNorm - wj)/lj;
        
        dj.multiply(tj);
        dj.add(aj);
        
        return dj;
    }
    
    private Point R(Point aj, List<WeightedPoint> aPoints, int dimension) {
        Point result = new Point(dimension);
        
        for (WeightedPoint ai: aPoints) {
            if (ai.getPoint().compareTo(aj) != 0) {
                double w = ai.getWeight();
                Point dif = Point.substraction(ai.getPoint(), aj);
                double factor = w/dif.getNorm();
                dif.multiply(factor);

                result.add(dif);
            }
        }
        
        return result;
    }

    private double operatorL(Point aj, List<WeightedPoint> aPoints) {
        double res = 0;
        for (WeightedPoint ai: aPoints) {
            if (aj.compareTo(ai.getPoint()) != 0) {
                Point dif = Point.substraction(aj, ai.getPoint());
                res += ai.getWeight()/dif.getNorm();
            }
        }
        return res;
    }
    
    /**
     * Evaluating the objective function in a given point x
     * @param x Point to evaluate the function.
     * @param aPoints List of weighted points.
     * @return 
     */
    private double evaluateF(Point x, List<WeightedPoint> aPoints) {
        double res = 0;
        for (WeightedPoint ai: aPoints) {
            res += ai.getWeight() * Point.substraction(ai.getPoint(), x).getNorm();
        }
        return res;
    }
    

}
