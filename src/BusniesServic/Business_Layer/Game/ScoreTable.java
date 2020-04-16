package BusniesServic.Business_Layer.Game;


//single tone

import BusniesServic.Business_Layer.BudgetManagement.PointsPolicy;
import BusniesServic.Business_Layer.TeamManagement.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ScoreTable {

    private ArrayList<Team> teams;
    private PointsPolicy pointsPolicy;

    public ScoreTable(PointsPolicy pointsPolicy) {
        this.pointsPolicy = pointsPolicy;
        teams = new ArrayList<>();
    }

    public void updateScoreTable() {
        Collections.sort(teams);
    }

}
