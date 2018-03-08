public class Main {
    public static void main(String[] args) {
        ArgParser parser = ArgParser.getInstance();
        int question = Integer.parseInt(parser.getArgument(args, "-question"));
        int reps;
        switch (question) {
            case 1:
                String[] probStrings = parser.getArgument(args, "-prob").split(" ");
                double[] probs = new double[probStrings.length];
                for (int i = 0; i < probStrings.length; i++) {
                    probs[i] = Double.parseDouble(probStrings[i]);
                }

                reps = Integer.parseInt(parser.getArgument(args, "-repetitions"));
                for (int i = 0; i < reps; i++) {
                    System.out.println(ProbDistribution.randInt(probs));
                }
                break;
            case 2:
                String strategyString = parser.getArgument(args, "-strategy");
                int crowded = Integer.parseInt(parser.getArgument(args, "-crowded"));
                int state = Integer.parseInt(parser.getArgument(args, "-state"));
                reps = Integer.parseInt(parser.getArgument(args, "-repetitions"));

                Strategy strategy = parseStrategy(strategyString);
                for (int i = 0; i < reps; i++) {
                    int nextState = strategy.getNextState(state, crowded);
                    int decision = strategy.getDecision(nextState);
                    System.out.println(String.format("%d\t%d", decision, nextState));
                }
                break;
            case 3:
                reps = Integer.parseInt(parser.getArgument(args, "-repetitions"));
                int lambda = Integer.parseInt(parser.getArgument(args, "-lambda"));
                int h = Integer.parseInt(parser.getArgument(args, "-h"));
                int weeks = Integer.parseInt(parser.getArgument(args, "-weeks"));
                int maxT = Integer.parseInt(parser.getArgument(args, "-max_t"));
                for (int i = 0; i < reps; i++) {
                    ElFarolBar bar = new ElFarolBar(lambda, h, weeks, maxT, 0.3, 0.5, false, 0);
                    bar.runGa();
                }
                break;
        }
    }

    public static Strategy parseStrategy(String strategyString) {
        String[] strategyStrings = strategyString.split(" ");
        int h = Integer.parseInt(strategyStrings[0]);
        Strategy s = new Strategy(h);
        for (int i = 0; i < h; i++) {
            int startIdx = i * (2 * h + 1) + 1;
            s.getP()[i] = Double.parseDouble(strategyStrings[startIdx]);
            for (int j = 0; j < h; j++) {
                s.getA()[i][j] = Double.parseDouble(strategyStrings[startIdx + j + 1]);
            }
            for (int j = 0; j < h; j++) {
                s.getB()[i][j] = Double.parseDouble(strategyStrings[startIdx + h + j + 1]);
            }
        }
        return s;
    }
}
