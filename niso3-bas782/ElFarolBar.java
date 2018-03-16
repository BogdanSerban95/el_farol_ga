import java.util.ArrayList;

public class ElFarolBar {
    private final boolean tournSelection;
    private final int tournamentSize;
    private int lambda;
    private int h;
    private int weeks;
    private int maxT;
    private ArrayList<Individual> population;
    private double crossoverRate;
    private double mutationRate;
    private ArrayList<WeeklyAttendance> currentAttendances;
    private boolean experimentMode = false;

    public ElFarolBar(int lambda, int h, int weeks, int maxT, double crossoverRate, double mutationRate, boolean tournamentSelection, int tournamentSize) {
        this.lambda = lambda;
        this.h = h;
        this.weeks = weeks;
        this.maxT = maxT;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.tournSelection = tournamentSelection;
        this.tournamentSize = tournamentSize;
    }

    public void setExperimentMode(boolean val) {
        this.experimentMode = val;
    }

    public void generateInitialPopulation() {
        this.population = new ArrayList<>();
        for (int i = 0; i < this.lambda; i++) {
            Individual ind = new Individual(new Strategy(this.h));
            ind.initStrategy();
            population.add(ind);
        }

        this.evaluatePopulation(this.population);
    }

    private void evaluatePopulation(ArrayList<Individual> individuals) {
        this.currentAttendances = this.simulateAttendance(individuals);
        this.computePayoffs(currentAttendances, individuals);
    }

    private ArrayList<WeeklyAttendance> simulateAttendance(ArrayList<Individual> individuals) {
        ArrayList<WeeklyAttendance> attendances = new ArrayList<>();
        for (int i = 0; i < weeks; i++) {
            WeeklyAttendance attendance = new WeeklyAttendance(this.lambda);
            for (int j = 0; j < this.lambda; j++) {
                Individual individual = individuals.get(j);
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

    private void computePayoffs(ArrayList<WeeklyAttendance> attendances, ArrayList<Individual> individuals) {
        for (int i = 0; i < this.lambda; i++) {
            int payoff = 0;
            Individual currentIndividual = individuals.get(i);
            for (int j = 0; j < this.weeks; j++) {
                int crowded = attendances.get(j).isCrowded();
                if (currentIndividual.getDecisions().get(j) == 1 && crowded == 0 || currentIndividual.getDecisions().get(j) == 0 && crowded == 1) {
                    payoff += 1;
                }
            }
            currentIndividual.setPayoff(payoff);
        }
    }

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

    private Individual tournamentSelection() {
        Individual winner = new Individual(null);
        winner.setPayoff(0);
        ArrayList<Individual> winners = new ArrayList<>();
        for (int i = 0; i < this.tournamentSize; i++) {
            int contestantIdx = RandomWrapper.getRandom().nextInt(this.lambda);
            Individual contestant = this.population.get(contestantIdx);
            if (contestant.getPayoff() > winner.getPayoff()) {
                winner = contestant;
                winners.clear();
                winners.add(winner);
            } else if (contestant.getPayoff() == winner.getPayoff()) {
                winners.add(contestant);
            }
        }

        return winners.get(RandomWrapper.getRandom().nextInt(winners.size()));
    }

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

    public void selectNextPopulation(ArrayList<Individual> offsprings) {
        ArrayList<Individual> contestants = new ArrayList<>();
        contestants.addAll(this.population);
        contestants.addAll(offsprings);
        contestants = sortContestants(contestants);
        this.population.clear();
        for (int i = 0; i < this.lambda; i++) {
            this.population.add(contestants.get(i));
        }
    }

    public ArrayList<Individual> sortContestants(ArrayList<Individual> contestants) {
        int k;
        do {
            k = 0;
            for (int i = 0; i < contestants.size() - 1; i++) {
                if (contestants.get(i).getPayoff() < contestants.get(i + 1).getPayoff()) {
                    Individual aux = contestants.get(i);
                    contestants.set(i, contestants.get(i + 1));
                    contestants.set(i + 1, aux);
                    k = 1;
                }
            }
        } while (k == 1);
        return contestants;
    }

    public int runGa() {

        this.generateInitialPopulation();
        for (int i = 0; i < this.maxT; i++) {
            ArrayList<Individual> newPopulation = new ArrayList<>();
            for (int j = 0; j < this.lambda; j++) {
                Individual parentA = null;
                Individual parentB = null;
                if (tournSelection) {
                    parentA = this.tournamentSelection();
                    parentB = this.tournamentSelection();
                } else {
                    parentA = this.fitnessProportionateSelection();
                    parentB = this.fitnessProportionateSelection();
                }

                Strategy offspringStrategy = crossover(this.crossoverRate, parentA.getStrategy(), parentB.getStrategy());
                offspringStrategy.mutate(this.mutationRate);
                newPopulation.add(new Individual(offspringStrategy));
            }
            this.evaluatePopulation(newPopulation);
            this.selectNextPopulation(newPopulation);
            this.evaluatePopulation(this.population);
            if (!experimentMode) {
                printResults(i);
            }
        }

        int averageAttendance = 0;
        if (experimentMode) {
            for (WeeklyAttendance attendance : this.currentAttendances) {
                averageAttendance += attendance.getTotalGoing();
            }
        }
        return averageAttendance / this.weeks;
    }

    public void printResults(int generation) {
        for (int i = 0; i < this.currentAttendances.size(); i++) {
            System.out.println(i + "\t" + generation + "\t" + currentAttendances.get(i).toString());
        }
    }
}
