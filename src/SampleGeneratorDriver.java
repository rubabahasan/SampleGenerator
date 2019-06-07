import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class SampleGeneratorDriver {

    public static void main(String[] args)
    {
        int N = 300;

//        double[][] tau_raw = {{1,0}};
//        double[][] T_raw = {{-1,1},{0,-1}};

        double[][] tau_raw = {{1,0,0,0,0}};
        double[][] T_raw = {{-1,1,0,0,0}, {0,-1,1,0,0}, {0,0,-1,1,0},  {0,0,0,-3,1.5}, {0,0,0,0,-2}};

        RealMatrix tau = MatrixUtils.createRealMatrix(tau_raw);
        RealMatrix T = MatrixUtils.createRealMatrix(T_raw);

        SampleGenerator sg = new SampleGeneratorPH(tau, T);
        System.out.println(sg);
        ArrayList<Double> samples = sg.generateSample(N);

        double sum1 = 0, sum2 = 0, sum3 = 0;
        for(int i=0; i<N; i++)
        {
            sum1 += samples.get(i);
            sum3 += Math.pow(samples.get(i), 3);
        }

        double mean = sum1/(double)N;
        for(int i=0; i<N; i++)
        {
            sum2 += Math.pow(samples.get(i), 2);
        }
        System.out.println("Mean = " + (sum1/N) + "\n 2nd Moment = " +  (sum2/N) + "\n3rd Moment = " + (sum3/N));
    }
}
