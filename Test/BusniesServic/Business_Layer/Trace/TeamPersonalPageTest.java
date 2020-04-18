package BusniesServic.Business_Layer.Trace;

import BusniesServic.Business_Layer.Game.ScoreTable;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class TeamPersonalPageTest {

    /**
     * Test -TP1
     */

    @RunWith(Parameterized.class)
    public static class gettersSettersTest {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public gettersSettersTest() {
        }

        @Test
        public void gettersSettersTest1() {
            TeamPersonalPage page = new TeamPersonalPage("Maccabi");
            Calendar calendar = Calendar.getInstance();
            page.setYearOfFoundation(calendar.getTime());
            assertEquals(calendar.getTime(),page.getYearOfFoundation());
            page.setPresidentName("Me");
            assertEquals("Me",page.getPresidentName());
            page.setStadiumName("Teddi");
            assertEquals("Teddi",page.getStadiumName());
            FootballTeamStatistic teamStatistic = new FootballTeamStatistic();
            page.setTeamStatistic(teamStatistic);
            assertEquals(teamStatistic,page.getTeamStatistic());
            ScoreTable scoreTable = new ScoreTable(null);
            page.setScoreTable(scoreTable);
            assertEquals(scoreTable,page.getScoreTable());
        }
    }

    /**
     * Test -TP2
     */

    @RunWith(Parameterized.class)
    public static class equalsTest {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public equalsTest() {
        }

        @Test
        public void equalsTest1() {
            TeamPersonalPage page = new TeamPersonalPage("Maccabi");
            Calendar calendar = Calendar.getInstance();
            page.setYearOfFoundation(calendar.getTime());
            page.setPresidentName("Me");
            FootballTeamStatistic teamStatistic = new FootballTeamStatistic();
            page.setTeamStatistic(teamStatistic);
            ScoreTable scoreTable = new ScoreTable(null);
            page.setScoreTable(scoreTable);

            assertEquals(page.toString(),"The Team: " + "Maccabi" +"\n" +
                    "year of foundation: " + calendar.getTime() + "\n" +
                    "president name: " + "Me" + "\n"+
                    "stadium name: " + null +"\n" +
                    "team statistic: " + teamStatistic.toString() + "\n" +
                    "score table: " + scoreTable.toString());

        }
    }


}
