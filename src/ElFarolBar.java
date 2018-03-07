import java.util.ArrayList;

public class ElFarolBar {
    private int lambda;
    private int h;
    private int weeks;
    private int maxT;
    private ArrayList<Individual> population;
    private double crossoverRate;
    private double mutationRate;
    private ArrayList<WeeklyAttendance> currentAttendances;


    public ElFarolBar(int lambda, int h, int weeks, int maxT, double crossoverRate, double mutationRate) {
        this.lambda = lambda;
        this.h = h;
        this.weeks = weeks;
        this.maxT = maxT;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }

    public void generateInitialPopulation() {
        this.population = new ArrayList<>();
        for (int i = 0; i < this.lambda; i++) {
            Individual ind = new Individual(new Strategy(this.h));
            ind.initStrategy();
            population.add(ind);
        }

        this.evaluatePopulation();
    }

    //    Evaluate - count the sum of the payoffs for each week for each individual(Strategy)
    private void evaluatePopulation() {
        this.currentAttendances = this.simulateAttendance();
        this.computePayoffs(currentAttendances);
    }

    //    Simulate - run each strategy over a number of weeks
    private ArrayList<WeeklyAttendance> simulateAttendance() {
        ArrayList<WeeklyAttendance> attendances = new ArrayList<>();
        for (int i = 0; i < weeks; i++) {
            WeeklyAttendance attendance = new WeeklyAttendance(this.lambda);
            for (int j = 0; j < this.lambda; j++) {
                Individual individual = this.population.get(j);
                if (i == 0) {
                    attendance.addAttendance(j, individual.getDecisionFromWeek(i, 0));
                } else {
                    int crowded = attendances.get(i - 1).isCrowded();
                    int nextState = individual.getStateFromWeek(i, crowded);
                    attendance.addAttendance(j, individual.getDecisionFromWeek(i, nextState));
                }
            }
            attendances.add(attendance);
        }
        return attendances;
    }

    private void computePayoffs(ArrayList<WeeklyAttendance> attendances) {
        for (int i = 0; i < this.lambda; i++) {
            int payoff = 0;
            Individual currentIndividual = this.population.get(i);
            for (int j = 0; j < this.weeks; j++) {
                int crowded = attendances.get(j).isCrowded();
                if (currentIndividual.getDecisions().get(j) == 1 && crowded == 0 || currentIndividual.getDecisions().get(j) == 0 && crowded == 1) {
                    payoff += 1;
                }
            }
            currentIndividual.setPayoff(payoff);
        }
    }

    //    Selection - Fitness Proportionate Selection
    public Individual fitnessProportionateSelection() {
        double[] normFit = new double[this.lambda];
        double fitSum = 0.0;
        for (int i = 0; i < this.lambda; i++) {
            fitSum += this.population.get(i).getPayoff();
            normFit[i] = this.population.get(i).getPayoff();
        }
        for (int i = 0; i < this.lambda; i++) {
            normFit[i] = normFit[i] / fitSum;
        }
        int idx = ProbDistribution.randInt(normFit);
        return this.population.get(idx);
    }

    //    Crossover - no idea how
    public Strategy crossover(double crossoverRate, Strategy parentA, Strategy parentB) {
        Strategy offspring = new Strategy(this.h);
        for (int i = 0; i < this.h; i++) {
            offspring.getP()[i] = parentA.getP()[i] * crossoverRate +
                    (1 - crossoverRate) * parentB.getP()[i];
            for (int j = 0; j < this.h; j++) {
                offspring.getA()[i][j] = parentA.getA()[i][j] * crossoverRate +
                        (1 - crossoverRate) * parentB.getA()[i][j];
                offspring.getB()[i][j] = parentA.getB()[i][j] * crossoverRate +
                        (1 - crossoverRate) * parentB.getB()[i][j];
            }
        }
        return offspring;
    }

    public Individual bestIndividual() {
        Individual best = this.population.get(0);
        for (Individual ind : this.population) {
            if (best.getPayoff() < ind.getPayoff()) {
                best = ind;
            }
        }
        return best;
    }

    public ArrayList<Object> runGa() {
        Individual bestIndividual = null;
        int generationCount = 0;

        this.generateInitialPopulation();
        for (int i = 0; i < this.maxT; i++) {
            bestIndividual = bestIndividual();
            ArrayList<Individual> newPopulation = new ArrayList<>();
            for (int j = 0; j < this.lambda; j++) {
                Individual parentA = this.fitnessProportionateSelection();
                Individual parentB = this.fitnessProportionateSelection();

                Strategy offspringStrategy = crossover(this.crossoverRate, parentA.getStrategy(), parentB.getStrategy());
                offspringStrategy.mutate(this.mutationRate);
                newPopulation.add(new Individual(offspringStrategy));
            }
            this.population = newPopulation;
            this.evaluatePopulation();
            printResults(i);
//            System.out.println(String.format("Generation: %d - best fit: %d", i, bestIndividual.getPayoff()));
        }
        ArrayList<WeeklyAttendance> weeklyAttendances = this.simulateAttendance();
        ArrayList<Object> results = new ArrayList<>();
        results.add(weeklyAttendances);
        results.add(maxT);
        return results;
    }

    public void printResults(int generation) {
        for (int i = 0; i < this.currentAttendances.size(); i++) {
            System.out.println(i + "\t" + generation + "\t" + currentAttendances.get(i).toString());
        }
    }
}
