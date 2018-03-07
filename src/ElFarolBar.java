import java.util.ArrayList;

public class ElFarolBar {
    private int lambda;
    private int h;
    private int weeks;
    private int maxT;
    private ArrayList<Individual> population;


    public ElFarolBar(int lambda, int h, int weeks, int max_t) {
        this.lambda = lambda;
        this.h = h;
        this.weeks = weeks;
        this.maxT = maxT;
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

    private void evaluatePopulation() {
        ArrayList<WeeklyAttendance> attendances = this.simulateAttendance();
        this.computePayoffs(attendances);
    }

    private ArrayList<WeeklyAttendance> simulateAttendance() {
        ArrayList<WeeklyAttendance> attendances = new ArrayList<>();
        for (int i = 0; i < weeks; i++) {
            WeeklyAttendance attendance = new WeeklyAttendance(this.weeks);
            for (int j = 0; j < this.lambda; j++) {
                Individual individual = this.population.get(j);
                if (i == 0) {
                    attendance.addAttendance(j, individual.getDecisionFromWeek(i, 0));
                } else {
                    int crowded = attendances.get(i - 1).isCrowded(this.lambda);
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
                int crowded = attendances.get(j).isCrowded(this.lambda);
                if (currentIndividual.getDecisions().get(j) == 1 && crowded == 0 || currentIndividual.getDecisions().get(j) == 0 && crowded == 1) {
                    payoff += 1;
                }
            }
            currentIndividual.setPayoff(payoff);
        }
    }

//    Selection - Fitness Proportionate Selection

//    Mutation - implemented here or in the individual class (after i decide what will be an individual)

//    Crossover - no idea how

//    Simulate - run each strategy over a number of weeks

//    Evaluate - count the sum of the payoffs for each week for each individual(Strategy)


}
