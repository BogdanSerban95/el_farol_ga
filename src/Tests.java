public class Tests {
    public static void main(String[] args) {
//        Strategy strategy = new Strategy(10);
//        strategy.randomInit();

//        ElFarolBar bar = new ElFarolBar(5, 3, 5, 5);
//        bar.generateInitialPopulation();

        for (int i = 0; i<5; i++) {
            Strategy s = new Strategy(5);
            s.randomInit();
            s.mutate(2/5.0);
            System.out.println("");
        }
    }
}
