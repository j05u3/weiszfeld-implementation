package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import algorithms.weiszfeld.Input;
import algorithms.weiszfeld.Output;
import algorithms.weiszfeld.Point;
import algorithms.weiszfeld.WeightedPoint;
import algorithms.weiszfeld.WeiszfeldAlgorithm;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josue
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        FileInputStream fis = new FileInputStream("tests.txt");
        Scanner sc = new Scanner(fis);
        
        /** Reading input **/
        
        // number of test cases
        int tc = sc.nextInt();
        for (int itc = 0; itc < tc; itc++) {
            // dimension
            int n = sc.nextInt();
            // number of points
            int m = sc.nextInt();
            List<WeightedPoint> wPoints = new ArrayList<>(m);
            // points with respective weights
            for (int i = 0; i < m; i++) {
                
                double weight = sc.nextDouble();
                
                double values[] = new double[n];
                for (int j = 0; j < n; j++) {
                    double val = sc.nextDouble();
                    values[j] = val;
                }
                
                WeightedPoint wPoint = new WeightedPoint();
                wPoint.setPoint(new Point(values));
                wPoint.setWeight(weight);
                
                wPoints.add(wPoint);
            }


            Input input = new Input();

            input.setDimension(n);
            input.setPoints(wPoints);
            input.setPermissibleError(0.00001);

            WeiszfeldAlgorithm weiszfeld = new WeiszfeldAlgorithm();
            Output output = weiszfeld.process(input);

            Point result = output.getPoint();

            System.out.println(result.showValues());
        }
        
    }
    
}
