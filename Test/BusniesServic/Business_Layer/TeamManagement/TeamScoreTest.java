package BusniesServic.Business_Layer.TeamManagement;

import BusniesServic.Business_Layer.Game.PointsPolicy;
import BusniesServic.Business_Layer.Game.ScoreTable;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamScoreTest {

    private TeamScore teamScore;

    /**
     * Test - TS1
     */
    @Test
    public void testTeamScoreConstructor() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        assertEquals(name, teamScore.getTeamName());
    }

    /**
     * Test - TS2
     */
    @Test
    public void testTeamSetTeamName() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);

        teamScore.setTeamName(null);
        assertEquals(name, teamScore.getTeamName());

        teamScore.setTeamName("");
        assertEquals(name, teamScore.getTeamName());

        name = "Real";
        teamScore.setTeamName(name);
        assertEquals(name, teamScore.getTeamName());
    }

    /**
     * Test - TS3
     */
    @Test
    public void testSetNumberOfGames() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int games = 3;
        teamScore.setNumberOfGames(games);
        assertEquals(games, teamScore.getNumberOfGames());
    }

    /**
     * Test - TS4
     */
    @Test
    public void testSetWins() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int wins = 3;
        teamScore.setWins(wins);
        assertEquals(wins, teamScore.getWins());
    }

    /**
     * Test - TS5
     */
    @Test
    public void testSetDrawn() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setDrawn(val);
        assertEquals(val, teamScore.getDrawn());
    }

    /**
     * Test - TS6
     */
    @Test
    public void testSetGoalsScores() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setGoalsScores(val);
        assertEquals(val, teamScore.getGoalsScores());
    }

    /**
     * Test - TS7
     */
    @Test
    public void testSetGoalsGet() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setGoalsGet(val);
        assertEquals(val, teamScore.getGoalsGet());
    }

    /**
     * Test - TS8
     */
    @Test
    public void testSetLoses() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setLoses(val);
        assertEquals(val, teamScore.getLoses());
    }

    /**
     * Test - TS9
     */
    @Test
    public void testSetPoints() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setPoints(val);
        assertEquals(val, teamScore.getPoints());
    }

    /**
     * Test - TS10
     */
    @Test
    public void testIncrementNumberOfGames() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setNumberOfGames(val);
        teamScore.incrementNumberOfGames();
        assertEquals(val+1, teamScore.getNumberOfGames());
    }

    /**
     * Test - TS11
     */
    @Test
    public void testIncrementNumberOfGoalsGet() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        int temp = 2;
        teamScore.setGoalsGet(val);
        teamScore.incrementNumberOfGoalsGet(temp);
        assertEquals(val+temp, teamScore.getGoalsGet());
    }

    /**
     * Test - TS12
     */
    @Test
    public void testIncrementNumberOfGoalsScores() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        int temp = 2;
        teamScore.setGoalsScores(val);
        teamScore.incrementNumberOfGoalsScores(temp);
        assertEquals(val+temp, teamScore.getGoalsScores());
    }

    /**
     * Test - TS13
     */
    @Test
    public void testIncrementWin() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setWins(val);
        teamScore.incrementWin();
        assertEquals(val+1, teamScore.getWins());
    }

    /**
     * Test - TS14
     */
    @Test
    public void testIncrementLose() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setLoses(val);
        teamScore.incrementLose();
        assertEquals(val+1, teamScore.getLoses());
    }

    /**
     * Test - TS15
     */
    @Test
    public void testIncrementDrwans() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int val = 3;
        teamScore.setDrawn(val);
        teamScore.incrementDrwans();
        assertEquals(val+1, teamScore.getDrawn());
    }

    /**
     * Test - TS16
     */
    @Test
    public void testUpdatePoints() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int win = 3;
        int lose = 2;
        int drawn = 1;
        teamScore.setWins(win);
        teamScore.setLoses(lose);
        teamScore.setDrawn(drawn);
        PointsPolicy pointsPolicy = new PointsPolicy(5,-2,1);
       teamScore.updatePoints(pointsPolicy);
        assertEquals(win*pointsPolicy.getWin() +
                lose*pointsPolicy.getLose() + drawn * pointsPolicy.getDrawn() ,teamScore.getPoints());
    }

    /**
     * Test - TS17
     */
    @Test
    public void testToString() {

        String name = "Barcelona";
        teamScore = new TeamScore(name);
        int win = 3;
        int lose = 2;
        int drawn = 1;
        int games = 4;
        int points = 10;
        int goalsScore = 7;
        int goalsGet = 3;
        teamScore.setGoalsScores(goalsScore);
        teamScore.setGoalsGet(goalsGet);
        teamScore.setNumberOfGames(games);
        teamScore.setPoints(points);
        teamScore.setWins(win);
        teamScore.setLoses(lose);
        teamScore.setDrawn(drawn);

        assertEquals("Team - " + name + " | games - " + games +  " | wins - " + win +
                " | drawns - " + drawn + " | loses - " + lose + " | goals scores - " + goalsScore +
                " | goals get - "  + goalsGet + " | points - " + points  + " |", teamScore.toString());
    }
}


