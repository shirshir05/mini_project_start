package BusinessService.Business_Layer.Trace;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class PlayerPersonalPageTest {

    //region Date

    /**
     * Test -PPP1
     */
    @RunWith(Parameterized.class)
    public static class DateOfBirthTest {

        Date correct;
        PlayerPersonalPage playerPersonalPage;
        Date dateOfBirth;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 9);
            calendar.set(Calendar.MONTH, 12);
            calendar.set(Calendar.YEAR, 1993);

            return Arrays.asList(new Object[][]{
                    {calendar.getTime(), calendar.getTime()}, {null, null}
            });
        }

        public DateOfBirthTest(Date dateOfBirth, Date correct) {
            playerPersonalPage = new PlayerPersonalPage("Player");
            this.correct = correct;
            this.dateOfBirth = dateOfBirth;
        }

        @Test
        public void DateOfBirthTest1() {
            playerPersonalPage.setDateOfBirth(dateOfBirth);
            assertEquals(playerPersonalPage.getDateOfBirth(),correct);
        }
    }

    //endregion

    //region Country City and Position

    /**
     * Test -PPP2
     */

    @RunWith(Parameterized.class)
    public static class PositionAndCountryAndCityOfBirth {

        String correct;
        PlayerPersonalPage playerPersonalPage;
        String country;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Israel", "Israel"}, {null, null}, {"", null}
            });
        }

        public PositionAndCountryAndCityOfBirth(String country, String correct) {
            playerPersonalPage = new PlayerPersonalPage("Player");
            this.correct = correct;
            this.country = country;
        }

        @Test
        public void CountryOfBirthTest() {
            playerPersonalPage.setCountryOfBirth(country);
            assertEquals(playerPersonalPage.getCountryOfBirth(),correct);
        }

        @Test
        public void CityOfBirthTest() {
            playerPersonalPage.setCityOfBirth(country);
            assertEquals(playerPersonalPage.getCityOfBirth(),correct);
        }

        @Test
        public void PositionTest() {
            playerPersonalPage.setPosition(country);
            assertEquals(playerPersonalPage.getPosition(),correct);
        }
    }

    //endregion

    //region Height weight and jersey

    /**
     * Test -PPP3
     */

    @RunWith(Parameterized.class)
    public static class HeightWeightJersey{

        double correct;
        PlayerPersonalPage playerPersonalPage;
        double data;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0,0}, {3.5,3.5}, {4,4}, {-1,0}
            });
        }

        public HeightWeightJersey(double data, double correct) {
            playerPersonalPage = new PlayerPersonalPage("Player");
            this.correct = correct;
            this.data = data;
        }

        @Test
        public void HeightTest() {
            playerPersonalPage.setHeight(data);
            assertTrue(playerPersonalPage.getHeight()==correct);
        }

        @Test
        public void WeightTest() {
            playerPersonalPage.setWeight(data);
            assertTrue(playerPersonalPage.getWeight()==correct);
        }

        @Test
        public void JerseyTest() {
            playerPersonalPage.setJerseyNumber((int)data);
            assertTrue(playerPersonalPage.getJerseyNumber()==(int)correct);
        }
    }

    //endregion

    //region statistics

    /**
     * Test -PPP4
     */
    @RunWith(Parameterized.class)
    public static class Statistics {

        PlayerPersonalPage playerPersonalPage;
        FootballPlayerStatistic data;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {new FootballPlayerStatistic()}, {null}
            });
        }

        public Statistics(FootballPlayerStatistic fps) {
            playerPersonalPage = new PlayerPersonalPage("Player");
            this.data = fps;
        }

        @Test
        public void StatisticsTest() {
            playerPersonalPage.setStatistic(data);
            if (data != null)
                assertTrue(playerPersonalPage.getStatistic() == data);
            else
                assertTrue(playerPersonalPage.getStatistic() != null);
        }
    }

    //endregion

    /**
     * Test -PPP5
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
            PlayerPersonalPage page = new PlayerPersonalPage("player");
            page.setCountryOfBirth("Israel");
            Calendar calendar = Calendar.getInstance();
            page.setDateOfBirth(calendar.getTime());
            page.setCityOfBirth("Rishon");
            page.setHeight(1.80);
            page.setJerseyNumber(1);
            page.setPosition("Goal Keeper");
            page.setWeight(80);
            assertEquals(page.toString(),"The Player: " + "player" +"\n" +
                    "date of Birth: " + calendar.getTime() + "\n" +
                    "country of Birth:" + "Israel" + "\n"+
                    "city of birth:" + "Rishon" +"\n" +
                    "height:" + 1.80 + "\n" +
                    "weight:" + 80.0 + "\n" +
                    "position:" + "Goal Keeper" + "\n" +
                    "jerseyNumber:" + 1 + "\n" +
                    "statistic: " + "\n"+ "FootBallStatistic{goals=0shots=0gameMinutes=0substitutions=0penalties=0redCards=0assists=0starts=0fouls=0yellowCards=0doubleYellowCards=0");
        }
    }


}