import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Experiments {
    public static void main(String[] args) {
        ArgParser parser = ArgParser.getInstance();
        int experiment = Integer.parseInt(parser.getArgument(args, "-experiment"));

        ArrayList<ArrayList<String>> results = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        switch (experiment) {
            case 1:
//                Population size experiment
                for (int i = 50; i <= 1000; i += 50) {
                    System.out.println("Pop size: " + i);
                    ElFarolBar bar = new ElFarolBar(i, 15, 15, 100, 0.5, 1.5, false, 3);
                    bar.setExperimentMode(true);
                    ArrayList<String> expResults = new ArrayList<>();
                    labels.add(String.valueOf(i));
                    for (int j = 0; j < 100; j++) {
                        if ((j + 1) % 10 == 0) {
                            System.out.println("Step: " + j);
                        }
                        expResults.add(String.valueOf(bar.runGa()));
                    }
                    results.add(expResults);
                }
                writeResultsToCsv("..\\results\\population_size_fps_1.csv", results, labels);
                break;
            case 2:
//                Mutation rate experiment
                for (double i = 0.4; i <= 2.8; i += 0.4) {
                    System.out.println("Mutation rate: " + i);
                    ElFarolBar bar = new ElFarolBar(200, 15, 15, 100, 0.5, i, false, 3);
                    bar.setExperimentMode(true);
                    ArrayList<String> expResults = new ArrayList<>();
                    labels.add(String.valueOf(i));
                    for (int j = 0; j < 100; j++) {
                        if ((j + 1) % 10 == 0) {
                            System.out.println("Step: " + j);
                        }
                        expResults.add(String.valueOf(bar.runGa()));
                    }
                    results.add(expResults);
                }
                writeResultsToCsv("..\\results\\mutation_rate_fps_1.csv", results, labels);
                break;
            case 3:
//                Crossover rate experiment
                for (double i = 0.1; i < 1; i += 0.1) {
                    System.out.println("Crossover rate: " + i);
                    ArrayList<String> expResults = new ArrayList<>();
                    ElFarolBar bar = new ElFarolBar(200, 15, 15, 100, i, 1.5, false, 3);
                    bar.setExperimentMode(true);
                    labels.add(String.valueOf(i));
                    for (int j = 0; j < 100; j++) {
                        if ((j + 1) % 10 == 0) {
                            System.out.println("Step: " + j);
                        }
                        expResults.add(String.valueOf(bar.runGa()));
                    }
                    results.add(expResults);
                }
                writeResultsToCsv("..\\results\\crossover_rate_fps_1.csv", results, labels);
                break;
            case 4:
//                Tournament size experiment
                for (int i = 2; i <= 5; i++) {
                    System.out.println("Tournament size: " + i);
                    ElFarolBar bar = new ElFarolBar(200, 15, 15, 100, 0.5, 1.5, true, i);
                    bar.setExperimentMode(true);
                    ArrayList<String> expResults = new ArrayList<>();
                    labels.add(String.valueOf(i));
                    for (int j = 0; j < 100; j++) {
                        if ((j + 1) % 10 == 0) {
                            System.out.println("Step: " + j);
                        }
                        expResults.add(String.valueOf(bar.runGa()));
                    }
                    results.add(expResults);
                }
                writeResultsToCsv("..\\results\\tournament_size.csv", results, labels);
                break;
            case 5:
//                Tournament selection vs FPS experiment
                System.out.println("Tournament selection: ");
                ArrayList<String> runResults = new ArrayList<>();
                labels.add("Tournament selection");
                ElFarolBar bar = new ElFarolBar(200, 15, 15, 100, 0.5, 1.5, true, 3);
                bar.setExperimentMode(true);
                for (int j = 0; j < 100; j++) {
                    if ((j + 1) % 10 == 0) {
                        System.out.println("Step: " + j);
                    }
                    runResults.add(String.valueOf(bar.runGa()));
                }
                results.add(runResults);

                System.out.println("Fitness proportionate selection: ");
                runResults = new ArrayList<>();
                labels.add("Fitness proportionate selection");
                bar = new ElFarolBar(200, 15, 15, 100, 0.5, 1.5, false, 3);
                bar.setExperimentMode(true);
                for (int j = 0; j < 100; j++) {
                    if ((j + 1) % 10 == 0) {
                        System.out.println("Step: " + j);
                    }
                    runResults.add(String.valueOf(bar.runGa()));
                }
                results.add(runResults);

                writeResultsToCsv("..\\results\\selection_scheme.csv", results, labels);
                break;
        }
    }

    private static void writeResultsToCsv(String fileName, ArrayList<ArrayList<String>> results, ArrayList<String> labels) {
        try (FileWriter fr = new FileWriter(fileName)) {
            try (BufferedWriter br = new BufferedWriter(fr)) {
                for (int i = 0; i < results.size(); i++) {
                    br.write(labels.get(i) + "," + String.join(",", results.get(i)));
                    br.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
