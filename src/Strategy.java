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
}
