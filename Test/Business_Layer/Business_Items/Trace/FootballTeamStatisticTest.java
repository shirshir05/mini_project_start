package Business_Layer.Business_Items.Trace;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)

public class FootballTeamStatisticTest {
    /**
     * Test -FTS1
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
            FootballTeamStatistic s = new FootballTeamStatistic();
            s.setAssists(3);
            assertEquals(3,s.getAssists());
            s.setGoals(5);
            assertEquals(5,s.getGoals());
            s.setPenalties(6);
            assertEquals(6,s.getPenalties());
            s.setCards(2);
            assertEquals(2,s.getCards());
            s.setDrawn(1);
            assertEquals(1,s.getDrawn());
            s.setLeftToPlay(20);
            assertEquals(20,s.getLeftToPlay());
            s.setMatchesPlayed(2);
            assertEquals(2,s.getMatchesPlayed());
            s.setWins(9);
            assertEquals(9,s.getWins());
            assertEquals(s.toString(),"FootballTeamStatistic{matchesPlayed=2, wins=9, drawn=1, goals=5, assists=3, cards=2, Penalties=6, leftToPlay=20}");


        }
    }

}

