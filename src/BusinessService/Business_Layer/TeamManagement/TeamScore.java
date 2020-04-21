package BusinessService.Business_Layer.TeamManagement;
import BusinessService.Business_Layer.Game.PointsPolicy;

public class TeamScore {

    String teamName;
    private int numberOfGames;
    private int wins;
    private int drawn;
    private int loses;
    private int goalsScores;
    private int goalsGet;
    private int points;

    public TeamScore(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public int getWins() {
        return wins;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getLoses() {
        return loses;
    }

    public int getGoalsScores() {
        return goalsScores;
    }

    public int getGoalsGet() {
        return goalsGet;
    }

    public int getPoints() {
        return points;
    }

    public void setTeamName(String teamName) {
        if(teamName != null && teamName.length() != 0){
            this.teamName = teamName;
        }
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void incrementNumberOfGames(){
        numberOfGames++;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public void setGoalsScores(int goalsScores) {
        this.goalsScores = goalsScores;
    }

    public void setGoalsGet(int goalsGet) {
        this.goalsGet = goalsGet;
    }

    public void incrementNumberOfGoalsGet(int goalsGet){
        this.goalsGet += goalsGet;
    }

    public void incrementNumberOfGoalsScores(int goalsScores){
        this.goalsScores += goalsScores;
    }

    public void incrementWin(){
        wins++;
    }

    public void incrementLose(){
        loses++;
    }

    public void incrementDrwans(){
        drawn++;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void updatePoints(PointsPolicy pointsPolicy){
        points = wins*pointsPolicy.getWin() + loses*pointsPolicy.getLose() + drawn*pointsPolicy.getDrawn();
    }

    @Override
    public String toString() {
        return  "Team - " + teamName + " | games - " + numberOfGames +  " | wins - " + wins +
                " | drawns - " + drawn + " | loses - " + loses + " | goals scores - " + goalsScores +
                " | goals get - "  + goalsGet + " | points - " + points  + " |";
    }
}
