import java.util.ArrayList;

public class Individual {
    private Strategy strategy;
    private ArrayList<Integer> states;
    private ArrayList<Integer> decisions;
    private int payoff;

    public Individual(Strategy strategy) {
        this.strategy = strategy;
        this.states = new ArrayList<>();
        this.decisions = new ArrayList<>();
        this.states.add(0);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public ArrayList<Integer> getStates() {
        return states;
    }

    public ArrayList<Integer> getDecisions() {
        return decisions;
    }

    public void initStrategy() {
        this.strategy.randomInit();
//        this.states.add(0);
    }

    public int getDecisionFromWeek(int week, int state) {
        int decision;
        try {
            decision = this.decisions.get(week);
        } catch (Exception ex) {
            decision = this.strategy.getDecision(state);
            this.decisions.add(decision);
        }
        return decision;
    }

    public int getStateFromWeek(int week, int crowded) {
        int state;
        try {
            state = this.states.get(week);
        } catch (Exception ex) {
            state = this.strategy.getNextState(this.states.get(this.states.size() - 1), crowded);
            this.states.add(state);
        }
        return state;
    }

    public void setPayoff(int payoff) {
        this.payoff = payoff;
    }

    public int getPayoff() {
        return payoff;
    }
}
