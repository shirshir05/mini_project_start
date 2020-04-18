package BusniesServic.Business_Layer.Game;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.TeamManagement.TeamScore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
@RunWith(Enclosed.class)
public class ScoreTableTest {
    private ScoreTable scoreTable;

    /**
     * Test - ST1
     */
    @Test
    public void testScoreTableConstructor() {
        int win = 4;
        int lose = -1;
        int drawn = 1;
        PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
        scoreTable = new ScoreTable(pointsPolicy);
        assertEquals(pointsPolicy, scoreTable.getPointsPolicy());
    }

    /**
     * Test - ST2
     */
    @Test
    public void testSetPointsPolicy() {
        int win = 4;
        int lose = -1;
        int drawn = 1;
        PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
        scoreTable = new ScoreTable(pointsPolicy);
        assertEquals(pointsPolicy, scoreTable.getPointsPolicy());

        win = 3;
        lose = 0;
        drawn = 1;
        pointsPolicy = new PointsPolicy(win, lose, drawn);

        scoreTable.setPointsPolicy(pointsPolicy);
        assertEquals(pointsPolicy, scoreTable.getPointsPolicy());
    }

    /**
     * Test - ST3
     */
    @Test
    public void testSetTeams() {
        int win = 4;
        int lose = -1;
        int drawn = 1;
        PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
        scoreTable = new ScoreTable(pointsPolicy);
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(new Team("Barcelona", "Kampnou"));
        scoreTable.setTeams(teams);
        assertEquals(teams, scoreTable.getTeams());
    }

    /**
     * Test - ST4
     */
    @Test
    public void testUpdateScoreTable() {
        int win = 4;
        int lose = -1;
        int drawn = 1;
        PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
        scoreTable = new ScoreTable(pointsPolicy);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        team1.setTeamScore(new TeamScore(team1.getName()));
        team2.setTeamScore(new TeamScore(team2.getName()));
        team1.getTeamScore().setPoints(3);
        team2.getTeamScore().setPoints(5);
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(team1);
        teams.add(team2);
        scoreTable.setTeams(teams);
        scoreTable.updateScoreTable();
        assertEquals(team2, scoreTable.getTeams().get(0));
        assertEquals(team1, scoreTable.getTeams().get(1));

    }

    /**
     * Test - ST5
     */
    @Test
    public void testAddTeam() {
        int win = 4;
        int lose = -1;
        int drawn = 1;
        PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
        scoreTable = new ScoreTable(pointsPolicy);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        team1.setTeamScore(new TeamScore(team1.getName()));
        team2.setTeamScore(new TeamScore(team2.getName()));
        team1.getTeamScore().setPoints(3);
        team2.getTeamScore().setPoints(5);
        scoreTable.addTeam(team1);
        assertEquals(team1, scoreTable.getTeams().get(0));
    }

    /**
     * Test - ST6
     */
    @Test
    public void testToString() {
        int win = 4;
        int lose = -1;
        int drawn = 1;
        PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
        scoreTable = new ScoreTable(pointsPolicy);
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.setTeamScore(new TeamScore(team1.getName()));
        team1.getTeamScore().setPoints(3);
        team1.getTeamScore().setNumberOfGames(5);
        team1.getTeamScore().setWins(2);
        team1.getTeamScore().setLoses(1);
        team1.getTeamScore().setDrawn(0);
        scoreTable.addTeam(team1);
        assertEquals(team1.getTeamScore().toString() + "\n", scoreTable.toString());
    }
}

