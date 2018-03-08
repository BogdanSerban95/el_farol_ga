import java.util.ArrayList;
import java.util.HashMap;

public class Tests {
    public static void main(String[] args) {
//        Strategy strategy = new Strategy(10);
//        strategy.randomInit();

//        ElFarolBar bar = new ElFarolBar(5, 3, 5, 5);

//        bar.generateInitialPopulation();
//        for (int i = 0; i<5; i++) {
//            Strategy s = new Strategy(5);
//            s.randomInit();
//            s.mutate(2/5.0);
//            System.out.println("");
//        }

//        ElFarolBar bar = new ElFarolBar(5, 3, 5, 5);
//        Strategy a = new Strategy(3);
//        a.randomInit();
//        Strategy b = new Strategy(3);
//        b.randomInit();
//
//        Strategy c = bar.crossover(0.5, a, b);

//        ElFarolBar bar = new ElFarolBar(5, 3, 5, 5);
//        bar.generateInitialPopulation();
//        for (int i = 0; i < 5; i++) {
//            Individual ind = bar.fitnessProportionateSelection();
//            System.out.println();
//        }

        ElFarolBar bar = new ElFarolBar(100, 10, 20, 100, 0.4, 0.8, true, 4);
//        for (int i = 0; i < 5; i++) {
//            bar.generateInitialPopulation();
//            Individual best = bar.bestIndividual();
//            System.out.println();
//        }
        bar.setExperimentMode(true);
        System.out.println(bar.runGa());

//        double[] probs = new double[]{0.12, 0.23, 0.30, 0.15, 0.10, 0.05, 0.05};
//        HashMap<Integer, Integer> counts = new HashMap<>();
//        for (int i = 0; i < 1000000; i++) {
//            int pos = ProbDistribution.randInt(probs);
//            if (counts.containsKey(pos)) {
//                counts.put(pos, counts.get(pos) + 1);
//            } else {
//                counts.put(pos, 1);
//            }
//        }
//
//        for (Integer key : counts.keySet()) {
//            System.out.println(String.format("%d %d %.2f", key, counts.get(key), (double)counts.get(key) / 1000000));
//        }
    }

}
