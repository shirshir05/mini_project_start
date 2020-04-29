package BusinessService.Business_Layer.Game;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Referee;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class SeasonTest {

    private Season season;

    /**
     * Test - S1
     */
    @Test
    public void testSeasonConstructor() {
        String year = "2000";
        season = new Season(year);
        assertEquals(year, season.getYear());
    }

    /**
     * Test - S2
     */
    @Test
    public void testGetYear() {

        String year = "2000";
        season = new Season(year);
        assertEquals(year, season.getYear());
    }

    /**
     * Test - S3
     */
    @Test
    public void testAddReferee() {
        String year = "2000";
        season = new Season(year);
        Referee referee = new Referee("a","123456", "sdsad@gmail.com");

        season.addReferee(referee);
        assertEquals(referee, season.getReferee("a"));

        season.addReferee(null);

        assertEquals(1, season.list_referee.size());
    }

    /**
     * Test - S4
     */
    @Test
    public void testAddGame() {
        String year = "2000";
        season = new Season(year);
        Game game = new Game("ad", LocalDate.of(2000, 11, 11), new Team("vf", "gdf"), new Team("fsdf", "sdfdsf"));
        int id = game.getGameId();

        season.addGame(game);
        assertEquals(game, season.getGame(id));

        season.addGame(null);

        assertEquals(1, season.list_game.size());
    }

    /**
     * Test - S5
     */
    @Test
    public void testDeleteReferee() {
        String year = "2000";
        season = new Season(year);
        Referee referee = new Referee("a","123456", "sdsad@gmail.com");
        Referee referee1 = new Referee("b","123456", "sdsad@gmail.com");

        season.addReferee(referee);
        assertEquals(referee, season.getReferee("a"));

        season.deleteReferee(referee1);
        assertEquals(referee, season.getReferee("a"));

        season.deleteReferee(referee);
        assertEquals(null, season.getReferee("a"));
    }

    /**
     * Test - S6
     */
    @Test
    public void testDeleteGame() {
        String year = "2000";
        season = new Season(year);
        Game game1 = new Game("ad", LocalDate.of(2000, 11, 11), new Team("vf", "gdf"), new Team("fsdf", "sdfdsf"));
        Game game2 = new Game("adfd", LocalDate.of(2008, 10, 11), new Team("hhf", "gddfgf"), new Team("hh", "sdfdsf"));

        int id1 = game1.getGameId();
        int id2 = game2.getGameId();

        season.addGame(game1);
        assertEquals(game1, season.getGame(id1));

        season.deleteGame(game2);
        assertEquals(game1, season.getGame(id1));

        season.deleteGame(game1);
        assertEquals(null, season.getGame(id1));
    }

    /**
     * Test - S7
     */
    @Test
    public void testGetGame() {

        String year = "2000";
        season = new Season(year);
        Game game = new Game("ad", LocalDate.of(2000, 11, 11), new Team("vf", "gdf"), new Team("fsdf", "sdfdsf"));
        int id = game.getGameId();

        season.addGame(game);
        assertEquals(game, season.getGame(id));

    }

    /**
     * Test - S8
     */
    @Test
    public void testSetYear() {

        String year = "2000";
        season = new Season("1990");

        season.setYear(year);
        assertEquals(year, season.getYear());

        String year2 = "2007";
        season.setYear(year2);
        assertEquals(year2, season.getYear());

        season.setYear(null);
        assertEquals(year2, season.getYear());

        season.setYear("");
        assertEquals(year2, season.getYear());

    }

    /**
     * Test - S9
     */
    @Test
    public void testGetReferee() {

        String year = "2000";
        season = new Season(year);
        Referee referee = new Referee("a","123456", "sdsad@gmail.com");

        season.addReferee(referee);
        assertEquals(referee, season.getReferee("a"));
    }
}