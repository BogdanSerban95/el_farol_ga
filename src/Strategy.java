import java.util.ArrayList;
import java.util.Random;

public class Strategy {
    private int h;
    private double[] p;
    private double[][] a;
    private double[][] b;

    public Strategy(int h) {
        this.h = h;
        this.p = new double[h];
        this.a = new double[h][h];
        this.b = new double[h][h];
    }

    public double[] getP() {
        return p;
    }

    public double[][] getA() {
        return a;
    }

    public double[][] getB() {
        return b;
    }

    public double[][] getTransitionMatrix(int crowded) {
        return crowded == 1 ? a : b;
    }

    public void randomInit() {
        Random rnd = RandomWrapper.getRandom();
        for (int i = 0; i < h; i++) {
            this.p[i] = rnd.nextDouble();
            double aSum = 1.0;
            double bSum = 1.0;
            for (int j = 0; j < h - 1; j++) {
                double aVal = rnd.nextDouble() * aSum;
                double bVal = rnd.nextDouble() * bSum;
                a[i][j] = aVal;
                b[i][j] = bVal;
                aSum -= aVal;
                bSum -= bVal;
            }
            a[i][h - 1] = aSum;
            b[i][h - 1] = bSum;
        }
    }

    public int getNextState(int currentState, int crowded) {
        double[][] transitionMatrix = this.getTransitionMatrix(crowded);

        return ProbDistribution.randInt(transitionMatrix[currentState]);
    }

    public int getDecision(int state) {
        double prob = this.p[state];
        return prob > RandomWrapper.getRandom().nextDouble() ? 1 : 0;
    }

    public void mutate(double mutationRate) {
        Random rnd = RandomWrapper.getRandom();
//            Mutate attendances
        for (int i = 0; i < h; i++) {
            if (rnd.nextDouble() < mutationRate) {
                double noise = rnd.nextGaussian() * 0.15;
                if (this.p[i] + noise < 0)
                    this.p[i] = 0;
                if (this.p[i] + noise > 1)
                    this.p[i] = 1;
                this.p[i] += noise;
            }
        }
//            Mutate transition matrices
        mutateMatrix(rnd, this.a, mutationRate);
        mutateMatrix(rnd, this.b, mutationRate);
    }

    private void mutateMatrix(Random rnd, double[][] mat, double mutationRate) {
        for (int i = 0; i < h; i++) {
            double sum = 0.0;
            for (int j = 0; j < h; j++) {
                if (rnd.nextDouble() < mutationRate) {
                    double noise = rnd.nextGaussian() * 0.15;
                    if (mat[i][j] + noise < 0)
                        mat[i][j] = 0;
                    if (mat[i][j] + noise > 1)
                        mat[i][j] = 1;
                    mat[i][j] += noise;
                }
                sum += mat[i][j];
            }
            for (int j = 0; j < h; j++) {
                mat[i][j] /= sum;
            }
        }
    }
}
