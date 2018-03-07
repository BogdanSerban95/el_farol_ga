public class WeeklyAttendance {
    private int[] individualAttendance;
    private int totalGoing;

    public WeeklyAttendance(int weeks) {
        this.totalGoing = 0;
        this.individualAttendance = new int[weeks];
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

    public int isCrowded(int populationSize) {
        return (double) totalGoing / populationSize >= 0.6 ? 1 : 0;
    }
}
