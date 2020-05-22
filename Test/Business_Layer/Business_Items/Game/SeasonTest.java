package Business_Layer.Business_Items.Game;
import Business_Layer.Business_Items.UserManagement.Referee;
import org.junit.Test;

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