package Busnies_Servic.Business_Layer.Game;

import Busnies_Servic.Business_Layer.TeamManagement.Team;
import Busnies_Servic.Business_Layer.UserManagement.Referee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class LeagueTest {

    private League league;

    /**
     * Test - L1
     */
    @Test
    public void testLeagueConstructor() {

        String name = "La Liga";
        league = new League(name);
        assertEquals(name, league.getName());
    }

    /**
     * Test - L2
     */
    @Test
    public void testGetName() {

        String name = "La Liga";
        league = new League(name);
        assertEquals(name, league.getName());
    }

    /**
     * Test - L3
     */
    @Test
    public void testAddSeason() {

        String name = "La Liga";
        league = new League(name);
        String year = "2020";
        Season season = new Season(year);

        league.addSeason(season);
        assertEquals(season, league.getSeason(year));

        league.addSeason(null);
        assertEquals(1, league.seasons.size());

    }

    /**
     * Test - L4
     */
    @Test
    public void testGetSeason() {

        String name = "La Liga";
        league = new League(name);
        String year = "2020";
        Season season = new Season(year);
        league.addSeason(season);

        assertEquals(season, league.getSeason(year));

        Season season1 = new Season("1999");
        assertEquals(null, league.getSeason("1999"));
    }

    /**
     * Test - L5
     */
    @Test
    public void testSetName() {

        String name = "La Liga";
        league = new League(name);

        assertEquals(name, league.getName());

        String name1 = "change";
        league.setName(name1);

        assertEquals(name1, league.getName());

        league.setName(null);

        assertEquals(name1, league.getName());

        league.setName("");

        assertEquals(name1, league.getName());

    }
}