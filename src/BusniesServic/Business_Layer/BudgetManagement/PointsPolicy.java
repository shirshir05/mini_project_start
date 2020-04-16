package BusniesServic.Business_Layer.BudgetManagement;

public class PointsPolicy {

    private int win;
    private int lose;
    private int drawn;

    public PointsPolicy(int win, int lose, int equal) {
        this.win = win;
        this.lose = lose;
        this.drawn = equal;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public int getDrawn() {
        return drawn;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public void setEqual(int drawn) {
        this.drawn = drawn;
    }
}
