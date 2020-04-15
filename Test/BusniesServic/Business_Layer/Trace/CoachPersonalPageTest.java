package BusniesServic.Business_Layer.Trace;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class CoachPersonalPageTest {

    //region Date

    @RunWith(Parameterized.class)
    public static class DateOfBirthTest {

        Date correct;
        CoachPersonalPage coachPersonalPage;
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
            coachPersonalPage = new CoachPersonalPage("Coach");
            this.correct = correct;
            this.dateOfBirth = dateOfBirth;
        }

        @Test
        public void DateOfBirthTest1() {
            coachPersonalPage.setDateOfBirth(dateOfBirth);
            assertEquals(coachPersonalPage.getDateOfBirth(),correct);
        }
    }

    //endregion

    //region Country

    @RunWith(Parameterized.class)
    public static class CountryOfBirthTest {

        String correct;
        CoachPersonalPage coachPersonalPage;
        String country;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Israel", "Israel"}, {null, null}, {"", null}
            });
        }

        public CountryOfBirthTest(String country, String correct) {
            coachPersonalPage = new CoachPersonalPage("Coach");
            this.correct = correct;
            this.country = country;
        }

        @Test
        public void CountryOfBirthTest1() {
            coachPersonalPage.setCountryOfBirth(country);
            assertEquals(coachPersonalPage.getCountryOfBirth(),correct);
        }
    }

    //endregion

    //region Years of experience

    @RunWith(Parameterized.class)
    public static class YearsOfExperienceTest {

        double correct;
        CoachPersonalPage coachPersonalPage;
        double years;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0,0}, {3.5,3.5}, {4,4}, {-1,0}
            });
        }

        public YearsOfExperienceTest(double years, double correct) {
            coachPersonalPage = new CoachPersonalPage("Coach");
            this.correct = correct;
            this.years = years;
        }

        @Test
        public void YearsOfExperienceTest1() {
            coachPersonalPage.setYearOfExperience(years);
            assertTrue(coachPersonalPage.getYearOfExperience()==correct);
        }
    }

    //endregion

    //region Num of titles

    @RunWith(Parameterized.class)
    public static class NumOfTitlesTest {

        int correct;
        CoachPersonalPage coachPersonalPage;
        int titles;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0,0}, {1,1}, {-1,0}
            });
        }

        public NumOfTitlesTest(int titles, int correct) {
            coachPersonalPage = new CoachPersonalPage("Coach");
            this.correct = correct;
            this.titles = titles;
        }

        @Test
        public void NumOfTitlesTest1() {
            coachPersonalPage.setNumOfTitles(titles);
            assertTrue(coachPersonalPage.getNumOfTitles()==correct);
        }
    }

    //endregion
}