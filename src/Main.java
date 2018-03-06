public class Main {
    public static void main(String[] args) {
        ArgParser parser = ArgParser.getInstance();
        int question = Integer.parseInt(parser.getArgument(args, "-question"));
        switch (question) {
            case 1:
                String[] probStrings = parser.getArgument(args, "-prob").split(" ");
                double[] probs = new double[probStrings.length];
                for (int i = 0; i < probStrings.length; i++) {
                    probs[i] = Double.parseDouble(probStrings[i]);
                }
                int reps = Integer.parseInt(parser.getArgument(args, "-repetitions"));
                for (int i = 0; i < reps; i++) {
                    System.out.println(ProbDistribution.randInt(probs));
                }
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
