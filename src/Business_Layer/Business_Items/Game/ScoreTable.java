package Business_Layer.Business_Items.Game;


//single tone

import Business_Layer.Business_Items.TeamManagement.Team;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreTable implements java.io.Serializable {

    private ArrayList<Team> teams;
    private PointsPolicy pointsPolicy;

    public ScoreTable(PointsPolicy pointsPolicy) {
        this.pointsPolicy = pointsPolicy;
        teams = new ArrayList<>();
    }

    public void addTeam(Team team){
        if(team != null && !teams.contains(team)){
            teams.add(team);
        }
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void updateScoreTable() {
        Collections.sort(teams);
    }

    public PointsPolicy getPointsPolicy() {
        return pointsPolicy;
    }

    public void setPointsPolicy(PointsPolicy pointsPolicy) {
        this.pointsPolicy = pointsPolicy;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    @Override
    public String toString() {

        String finalAns = "";

        for (Team team : teams)
        {
            finalAns = finalAns + team.getTeamScore().toString() + "\n";
        }

        return finalAns;
    }
}
