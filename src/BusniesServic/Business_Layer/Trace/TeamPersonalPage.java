package BusniesServic.Business_Layer.Trace;
import BusniesServic.Business_Layer.Game.ScoreTable;

import java.util.Date;

public class TeamPersonalPage extends PersonalPage {

    protected Date yearOfFoundation;
    protected String presidentName;
    protected String stadiumName;
    protected FootballTeamStatistic teamStatistic;
    protected ScoreTable scoreTable;

    public TeamPersonalPage(String name){

        super(name);
        teamStatistic = new FootballTeamStatistic();
    }

    //**********************************************get & set ************************************************************//
    public Date getYearOfFoundation() {
        return yearOfFoundation;
    }

    public String getPresidentName() {
        return presidentName;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public FootballTeamStatistic getTeamStatistic() {
        return teamStatistic;
    }

    public ScoreTable getScoreTable() {
        return scoreTable;
    }

    public void setYearOfFoundation(Date yearOfFoundation) {

        if(yearOfFoundation != null){

            this.yearOfFoundation = yearOfFoundation;
        }
    }

    public void setPresidentName(String presidentName) {

        if (presidentName != null && presidentName.length() != 0) {

            this.presidentName = presidentName;
        }
    }

    public void setStadiumName(String stadiumName) {

        if (stadiumName != null && stadiumName.length() != 0) {

            this.stadiumName = stadiumName;
        }
    }

    public void setTeamStatistic(FootballTeamStatistic teamStatistic) {

        if(teamStatistic != null) {

            this.teamStatistic = teamStatistic;
        }
    }

    public void setScoreTable(ScoreTable scoreTable) {

        if(scoreTable != null) {

            this.scoreTable = scoreTable;
        }
    }
    //**********************************************to string ************************************************************//
    @Override
    public String toString() {

        return "The Team: " + name +"\n" +
                "year of foundation: " + yearOfFoundation + "\n" +
                "president name: " + presidentName + "\n"+
                "stadium name: " + stadiumName +"\n" +
                "team statistic: " + teamStatistic.toString() + "\n" +
                "score table: " + scoreTable.toString();
    }
}
