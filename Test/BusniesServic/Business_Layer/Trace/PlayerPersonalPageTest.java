package BusniesServic.Business_Layer.Trace;

import BusniesServic.Business_Layer.UserManagement.Player;
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

    //region Country and City

    @RunWith(Parameterized.class)
    public static class CountryAndCityOfBirth {

        String correct;
        PlayerPersonalPage playerPersonalPage;
        String country;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Israel", "Israel"}, {null, null}, {"", null}
            });
        }

        public CountryAndCityOfBirth(String country, String correct) {
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
    }

    //endregion

    //region Height and weight

    @RunWith(Parameterized.class)
    public static class HeightWeight{

        double correct;
        PlayerPersonalPage playerPersonalPage;
        double data;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0,0}, {3.5,3.5}, {4,4}, {-1,0}
            });
        }

        public HeightWeight(double data, double correct) {
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
    }

    //endregion
}