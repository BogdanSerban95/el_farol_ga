import java.util.Random;

public class ProbDistribution {
    public static int randInt(double[] probabilities) {
        Random rnd = RandomWrapper.getRandom();
        double prob = rnd.nextDouble();
        int i;
        for (i = 0; prob > 0; i++) {
            prob -= probabilities[i];
        }
        return i - 1;
    }
}
