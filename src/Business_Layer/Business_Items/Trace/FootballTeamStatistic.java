package Business_Layer.Business_Items.Trace;

public class FootballTeamStatistic implements java.io.Serializable{

    private int matchesPlayed;
    private int wins;
    private int drawn;
    private int goals;
    private int assists;
    private int cards;
    private int Penalties;
    private int leftToPlay;


    //**********************************************get & set ************************************************************//

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getGoals() {
        return goals;
    }

    public int getAssists() {
        return assists;
    }

    public int getCards() {
        return cards;
    }

    public int getPenalties() {
        return Penalties;
    }

    public int getLeftToPlay() {
        return leftToPlay;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }

    public void setPenalties(int penalties) {
        Penalties = penalties;
    }

    public void setLeftToPlay(int leftToPlay) {
        this.leftToPlay = leftToPlay;
    }

    //**********************************************to string ************************************************************//
    @Override
    public String toString() {
        return "FootballTeamStatistic{" +
                "matchesPlayed=" + matchesPlayed +
                ", wins=" + wins +
                ", drawn=" + drawn +
                ", goals=" + goals +
                ", assists=" + assists +
                ", cards=" + cards +
                ", Penalties=" + Penalties +
                ", leftToPlay=" + leftToPlay +
                '}';
    }
}
