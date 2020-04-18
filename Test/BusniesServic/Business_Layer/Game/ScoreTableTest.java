package BusniesServic.Business_Layer.Game;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.TeamManagement.TeamScore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class ScoreTableTest {


    /**
     * Test - ST1
     */
    public static class ScoreTable1 {
        private ScoreTable scoreTable;
        String ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null}
            });
        }

        public ScoreTable1(String ans) {
            this.ans =ans;
        }

        @Test
        public void testScoreTableConstructor() {
            int win = 4;
            int lose = -1;
            int drawn = 1;
            PointsPolicy pointsPolicy = new PointsPolicy(win, lose, drawn);
            scoreTable = new ScoreTable(pointsPolicy);
            assertEquals(pointsPolicy, scoreTable.getPointsPolicy());
        }
    }

    /**
     * Test - ST2
     */
    public static class ScoreTable2 {
        private ScoreTable scoreTable;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public ScoreTable2() {
        }


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
    }

    /**
     * Test - ST3
     */
    public static class ScoreTable3 {
        private ScoreTable scoreTable;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public ScoreTable3() {
        }


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
    }

    /**
     * Test - ST4
     */
    public static class ScoreTable4 {
        private ScoreTable scoreTable;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public ScoreTable4() {
        }


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
    }

    /**
     * Test - ST5
     */
    public static class ScoreTable5 {
        private ScoreTable scoreTable;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public ScoreTable5() {
        }


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
    }

    /**
     * Test - ST6
     */
    public static class ScoreTable6 {
        private ScoreTable scoreTable;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public ScoreTable6() {
        }


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
}

