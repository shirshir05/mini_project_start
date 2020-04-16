package BusniesServic.Business_Layer.Game;


//single tone

import BusniesServic.Business_Layer.TeamManagement.Team;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreTable {

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
