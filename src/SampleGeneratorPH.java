import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.math3.distribution.ExponentialDistribution;

public class SampleGeneratorPH implements SampleGenerator{

    int n;
    double p, p_x;
    double lambda_x1, lambda_x2, lambda_y;

    ExponentialDistribution expDist_lambda_y, expDist_lambda_x1, expDist_lambda_x2;
    Random rand;

    public SampleGeneratorPH(RealMatrix tau, RealMatrix T) {
        n = T.getColumnDimension();
        p = tau.getEntry(0, 0);
        lambda_y = T.getEntry(0,1);
        lambda_x2 = (-1)*T.getEntry(n-1, n-1);

        double p_x_lambda_x1 = T.getEntry(n-2, n-1);

        lambda_x1 = -(T.getEntry(n-2, n-2));
        p_x = T.getEntry(n-2, n-1) / lambda_x1;

        expDist_lambda_y = new ExponentialDistribution(this.lambda_y);
        expDist_lambda_x1 = new ExponentialDistribution(this.lambda_x1);
        expDist_lambda_x2 = new ExponentialDistribution(this.lambda_x2);

        rand = new Random();

    }

    @Override
    public String toString() {
        return "SampleGeneratorPH{\n" +
                "n=" + n +
                ", \np=" + p +
                ", \np_x=" + p_x +
                ", \nlambda_x1=" + lambda_x1 +
                ", \nlambda_x2=" + lambda_x2 +
                ", \nlambda_y=" + lambda_y +
                '}';
    }

    public double generateSingleSample() {

        double var = 0;

        double r_p = rand.nextDouble();

        if(r_p < this.p)
        {
            for(int i=0; i<(n-2); i++)
            {
                var += expDist_lambda_y.sample();
            }

            var += expDist_lambda_x1.sample();

            double r_p_x = rand.nextDouble();
            if(r_p_x < this.p_x)
            {
                var += expDist_lambda_x2.sample();
            }
        }
        return var;

    }

    @Override
    public ArrayList<Double> generateSample(int N) {
        ArrayList<Double> alist = new ArrayList<Double>();
        for(int i=0; i<N; i++)
        {
            alist.add(generateSingleSample());
        }
        return alist;
    }
}
