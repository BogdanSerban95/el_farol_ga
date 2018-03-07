public class Tests {
    public static void main(String[] args) {
//        Strategy strategy = new Strategy(10);
//        strategy.randomInit();

        ElFarolBar bar = new ElFarolBar(5, 3, 5, 5);
        bar.generateInitialPopulation();
    }
}
