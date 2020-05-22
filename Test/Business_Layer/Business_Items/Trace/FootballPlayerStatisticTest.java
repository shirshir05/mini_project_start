package Business_Layer.Business_Items.Trace;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class FootballPlayerStatisticTest {
    /**
     * Test -FPS1
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
            FootballPlayerStatistic stat = new FootballPlayerStatistic();
            stat.setAssists(3);
            assertEquals(3,stat.getAssists());
            stat.setDoubleYellowCards(2);
            assertEquals(2,stat.getDoubleYellowCards());
            stat.setFouls(1);
            assertEquals(1,stat.getFouls());
            stat.setGameMinutes(90);
            assertEquals(90,stat.getGameMinutes());
            stat.setGoals(5);
            assertEquals(5,stat.getGoals());
            stat.setPenalties(6);
            assertEquals(6,stat.getPenalties());
            stat.setRedCards(0);
            assertEquals(0,stat.getRedCards());
            stat.setShots(9);
            assertEquals(9,stat.getShots());
            stat.setStarts(2);
            assertEquals(2,stat.getStarts());
            stat.setSubstitutions(3);
            assertEquals(3,stat.getSubstitutions());
            stat.setYellowCards(4);
            assertEquals(4,stat.getYellowCards());

            assertEquals(stat.toString(),"FootBallStatistic{goals=5shots=9gameMinutes=90substitutions=3penalties=6redCards=0assists=3starts=2fouls=1yellowCards=4doubleYellowCards=2");

        }
    }





}
