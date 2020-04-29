package BusinessService.Business_Layer.Game;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.TeamManagement.TeamScore;
import BusinessService.Business_Layer.UserManagement.Player;
import BusinessService.Business_Layer.UserManagement.Referee;
import BusinessService.Enum.EventType;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    /**
     * Test - G1
     */
    @Test
    public void testGameConstructor() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        String field = "Bernabeo";
        game = new Game(field, date, team1, team2);

        assertEquals(field, game.getField());
        assertEquals(date, game.getDate());
        assertEquals(team1, game.getHost());
        assertEquals(team2, game.getGuest());
        //assertEquals(1, game.getGameId());
        assertEquals(null, game.getHeadReferee());
        assertEquals(null, game.getLinesman1Referee());
        assertEquals(null, game.getLinesman2Referee());
        assertEquals(null, game.getLinesman1Referee());
        assertEquals(null, game.getLinesman2Referee());
    }

    /**
     * Test - G3
     */
    @Test
    public void testEndGame() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        team1.setTeamScore(new TeamScore(team1.getName()));
        team2.setTeamScore(new TeamScore(team2.getName()));
        ScoreTable scoreTable = new ScoreTable(new PointsPolicy(3,-1, 1));
        scoreTable.addTeam(team2);
        scoreTable.addTeam(team1);
        game = new Game("Bernabeo", date, team1, team2);
        game.endGame(scoreTable, 4,2);
        assertEquals(1,team1.getTeamScore().getNumberOfGames());
        assertEquals(4,team1.getTeamScore().getGoalsScores());
        assertEquals(2,team1.getTeamScore().getGoalsGet());
        assertEquals(1,team1.getTeamScore().getWins());
        assertEquals(0,team1.getTeamScore().getLoses());
        assertEquals(0,team1.getTeamScore().getDrawn());
        assertEquals(1,team2.getTeamScore().getNumberOfGames());
        assertEquals(2,team2.getTeamScore().getGoalsScores());
        assertEquals(4,team2.getTeamScore().getGoalsGet());
        assertEquals(0,team2.getTeamScore().getWins());
        assertEquals(1,team2.getTeamScore().getLoses());
        assertEquals(0,team2.getTeamScore().getDrawn());

        assertEquals(team1.getTeamScore().getWins()*scoreTable.getPointsPolicy().getWin() +
                team1.getTeamScore().getLoses()*scoreTable.getPointsPolicy().getLose() +
                team1.getTeamScore().getDrawn()*scoreTable.getPointsPolicy().getDrawn(),team1.getTeamScore().getPoints());

        assertEquals(team1, scoreTable.getTeams().get(0));
        assertEquals(team2, scoreTable.getTeams().get(1));

        game = new Game("Bernabeo", date, team2, team1);
        game.endGame(scoreTable, 2,2);
        assertEquals(2,team1.getTeamScore().getNumberOfGames());
        assertEquals(6,team1.getTeamScore().getGoalsScores());
        assertEquals(4,team1.getTeamScore().getGoalsGet());
        assertEquals(1,team1.getTeamScore().getWins());
        assertEquals(0,team1.getTeamScore().getLoses());
        assertEquals(1,team1.getTeamScore().getDrawn());
        assertEquals(2,team2.getTeamScore().getNumberOfGames());
        assertEquals(4,team2.getTeamScore().getGoalsScores());
        assertEquals(6,team2.getTeamScore().getGoalsGet());
        assertEquals(0,team2.getTeamScore().getWins());
        assertEquals(1,team2.getTeamScore().getLoses());
        assertEquals(1,team2.getTeamScore().getDrawn());

        game = new Game("Bernabeo", date, team2, team1);
        game.endGame(scoreTable, 0,1);
        assertEquals(3,team1.getTeamScore().getNumberOfGames());
        assertEquals(7,team1.getTeamScore().getGoalsScores());
        assertEquals(4,team1.getTeamScore().getGoalsGet());
        assertEquals(2,team1.getTeamScore().getWins());
        assertEquals(0,team1.getTeamScore().getLoses());
        assertEquals(1,team1.getTeamScore().getDrawn());
        assertEquals(3,team2.getTeamScore().getNumberOfGames());
        assertEquals(4,team2.getTeamScore().getGoalsScores());
        assertEquals(7,team2.getTeamScore().getGoalsGet());
        assertEquals(0,team2.getTeamScore().getWins());
        assertEquals(2,team2.getTeamScore().getLoses());
        assertEquals(1,team2.getTeamScore().getDrawn());

        assertEquals(team1.getTeamScore().getWins()*scoreTable.getPointsPolicy().getWin() +
                team1.getTeamScore().getLoses()*scoreTable.getPointsPolicy().getLose() +
                team1.getTeamScore().getDrawn()*scoreTable.getPointsPolicy().getDrawn(),team1.getTeamScore().getPoints());

        assertEquals(team2.getTeamScore().getWins()*scoreTable.getPointsPolicy().getWin() +
                team2.getTeamScore().getLoses()*scoreTable.getPointsPolicy().getLose() +
                team2.getTeamScore().getDrawn()*scoreTable.getPointsPolicy().getDrawn(),team2.getTeamScore().getPoints());
    }

//    public void endGame(ScoreTable scoreTable, int hostGoals, int guestGoals){
//        setScore(hostGoals, guestGoals);
//        updateTeamsInfo();
//        host.getTeamScore().updatePoints(scoreTable.getPointsPolicy());
//        guest.getTeamScore().updatePoints(scoreTable.getPointsPolicy());
//        scoreTable.updateScoreTable(); //option to use observer
//    }


    /**
     * Test - G3
     */
    @Test
    public void testGetHeadReferee() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(null, game.getHeadReferee());

        Referee referee = new Referee("", "", "matanshus@gmail.com");
        game.setHeadReferee(referee);
        assertEquals(referee, game.getHeadReferee());
    }

    /**
     * Test - G4
     */
    @Test
    public void testGetLinesman1Referee() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(null, game.getLinesman1Referee());

        Referee referee = new Referee("", "", "matanshus@gmail.com");
        game.setLinesman1Referee(referee);
        assertEquals(referee, game.getLinesman1Referee());
    }

    /**
     * Test - G5
     */
    @Test
    public void testGetLinesman2Referee() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(null, game.getLinesman2Referee());

        Referee referee = new Referee("", "", "matanshus@gmail.com");
        game.setLinesman1Referee(referee);
        assertEquals(referee, game.getLinesman1Referee());
    }

    /**
     * Test - G6
     */
    @Test
    public void testSetHeadReferee() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setHeadReferee(null);
        assertEquals(null, game.getHeadReferee());

        Referee referee = new Referee("", "", "matanshus@gmail.com");
        game.setHeadReferee(referee);
        assertEquals(referee, game.getHeadReferee());
    }

    /**
     * Test - G7
     */
    @Test
    public void testSetLinesman1Referee() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setLinesman1Referee(null);
        assertEquals(null, game.getLinesman1Referee());

        Referee referee = new Referee("", "", "matanshus@gmail.com");
        game.setLinesman1Referee(referee);
        assertEquals(referee, game.getLinesman1Referee());
    }

    /**
     * Test - G8
     */
    @Test
    public void testSetLinesman2Referee() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setLinesman1Referee(null);
        assertEquals(null, game.getLinesman2Referee());

        Referee referee = new Referee("", "", "matanshus@gmail.com");
        game.setLinesman2Referee(referee);
        assertEquals(referee, game.getLinesman2Referee());
    }

    /**
     * Test - G9
     */
    @Test
    public void testGetDate() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(date, game.getDate());
    }

    /**
     * Test - G10
     */
    @Test
    public void testChangeDate() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(date, game.getDate());

        LocalDate date2 = LocalDate.of(1995, 5, 23);

        game.changeDate(date2);
        assertEquals(date2, game.getDate());
    }

    /**
     * Test - G11
     */
    @Test
    public void testGetHost() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(team1, game.getHost());
    }

    /**
     * Test - G12
     */
    @Test
    public void testGetGuest() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(team2, game.getGuest());
    }

    /**
     * Test - G13
     */
    @Test
    public void testSetHost() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(team1, game.getHost());

        game.setHost(team2);
        assertEquals(team1, game.getHost());

        game.setHost(null);
        assertEquals(team1, game.getHost());

        game.setHost(team3);
        assertEquals(team3, game.getHost());

        game.setHost(team1);
        assertEquals(team1, game.getHost());
    }

    /**
     * Test - G14
     */
    @Test
    public void testSetGuest() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals(team2, game.getGuest());

        game.setGuest(team1);
        assertEquals(team2, game.getGuest());

        game.setGuest(null);
        assertEquals(team2, game.getGuest());

        game.setGuest(team3);
        assertEquals(team3, game.getGuest());
    }

    /**
     * Test - G15
     */
    @Test
    public void getScore() {

        //need to complete
    }

    /**
     * Test - G16
     */
    @Test
    public void testUpdate_score() {
        //need to complete
    }

    /**
     * Test - G17
     */
    @Test
    public void testUpdateNewEvent() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        //team name doesn't exist
        assertEquals(false, game.updateNewEvent("team1.getName()", "messi", EventType.foul).isActionSuccessful());

        //player doesn't exist
        assertEquals(false, game.updateNewEvent(team1.getName(), "messi", EventType.foul).isActionSuccessful());
        assertEquals(false, game.updateNewEvent(team2.getName(), "messi", EventType.foul).isActionSuccessful());

        team1.addOrRemovePlayer(new Player("messi", "123456", "31212fsf@gmail.com"), 1);
        team2.addOrRemovePlayer(new Player("Ronaldo", "123456", "31212fsf@gmail.com"), 1);
        assertEquals(true, game.updateNewEvent(team1.getName(), "messi", EventType.foul).isActionSuccessful());
        assertEquals(true, game.updateNewEvent(team2.getName(), "Ronaldo", EventType.foul).isActionSuccessful());

    }

    /**
     * Test - G18
     */
    @Test
    public void testSetGameStartTimeSetGuest() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setGameStartTime();
        LocalDateTime gameEndTime = game.getEndTime();

        assertEquals(gameEndTime, game.getStartTime().plusMinutes(140));

    }


    /**
     * Test - G19
     */
    @Test
    public void testUpdateEndTime() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setGameStartTime();
        assertEquals(game.getEndTime(), game.getStartTime().plusMinutes(140));

        LocalDateTime gameEndTime = game.getEndTime().plusMinutes(20);
        game.updateEndTime(20);

        assertEquals(gameEndTime, game.getEndTime());

    }

//    public void updateEndTime(long additionalTimeInMinutes){
//        endTime = endTime.plusMinutes(additionalTimeInMinutes);
//    }


    /**
     * Test - G20
     */
    @Test
    public void testGetField() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        assertEquals("Bernabeo", game.getField());
    }

    /**
     * Test - G22
     */
    @Test
    public void testChange_field() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        String field = "Bernabeo";
        game = new Game(field, date, team1, team2);

        assertEquals(field, game.getField());

        field = "Yad Aliau";
        game.change_field(field);
        assertEquals(field, game.getField());

        field = null;
        game.change_field(field);
        assertEquals("Yad Aliau", game.getField());

        field = "";
        game.change_field(field);
        assertEquals("Yad Aliau", game.getField());
        assertEquals(team1, game.getHost());
    }

    /**
     * Test - G23
     */
    @Test
    public void testGetStartTime() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setGameStartTime();
        LocalDateTime gameEndTime = game.getEndTime();
        assertEquals(gameEndTime, game.getStartTime().plusMinutes(140));
    }

    /**
     * Test - G24
     */
    @Test
    public void testGetEndTime() {

        LocalDate date = LocalDate.of(1992, 11, 14);
        Team team1 = new Team("Barcelona", "Camp Nou");
        Team team2 = new Team("Real Madrid", "Bernabeo");
        Team team3 = new Team("Roma", "Bernabeo");
        game = new Game("Bernabeo", date, team1, team2);

        game.setGameStartTime();
        LocalDateTime gameStart = game.getStartTime();
        assertEquals(gameStart.plusMinutes(140), game.getEndTime());
    }

    //    /**
//     * Test - G2
//     */
//    @Test
//    public void testGetId() {
//
//        LocalDate date = LocalDate.of(1992, 11, 14);
//        Team team1 = new Team("Barcelona", "Camp Nou");
//        Team team2 = new Team("Real Madrid", "Bernabeo");
//        game = new Game("Bernabeo", date, team1, team2);
//
//        Game game2 = new Game("Bernabeo", date, team1, team2);
//        assertEquals(6, game.getGameId());
//        assertEquals(7, game2.getGameId());
//    }
}
