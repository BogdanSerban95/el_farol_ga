import java.util.Arrays;

public class WeeklyAttendance {
    private final int popSize;
    private int[] individualAttendance;
    private int totalGoing;

    public WeeklyAttendance(int populationSize) {
        this.totalGoing = 0;
        this.popSize = populationSize;
        this.individualAttendance = new int[populationSize];
    }

    public int[] getIndividualAttendance() {
        return individualAttendance;
    }

    public int getTotalGoing() {
        return totalGoing;
    }

    public void addAttendance(int pos, int val) {
        this.individualAttendance[pos] = val;
        this.totalGoing += val;
    }

    public int isCrowded() {
        return (double) totalGoing / this.popSize >= 0.6 ? 1 : 0;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(this.totalGoing).append("\t");
        res.append(this.isCrowded()).append("\t");
        for (int att : this.individualAttendance) {
            res.append(att).append("\t");
        }
        return res.toString();
    }
}
