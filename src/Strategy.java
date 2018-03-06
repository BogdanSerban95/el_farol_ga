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

    public double[][] getTranzitionMatrix(int crowded) {
        return crowded == 1 ? a : b;
    }
}
