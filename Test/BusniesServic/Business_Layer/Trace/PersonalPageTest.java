package BusniesServic.Business_Layer.Trace;

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
public class PersonalPageTest {
    /**
     * Test -PPT1
     */
    @RunWith(Parameterized.class)
    public static class permissions {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public permissions() {
        }
        @Test
        public void permissionsTest() {
            PersonalPage page = new PersonalPage("Shir");
            page.addPermissionToEdit("Raz");
            assertEquals(page.permissionToEdit.size(),2);
            assertTrue(page.chackperrmissiontoedit("Raz"));
            page.removePermissionToEdit("Yael");
            assertEquals(page.permissionToEdit.size(),2);
            page.removePermissionToEdit("Raz");
            assertEquals(page.permissionToEdit.size(),1);

        }
    }

    /**
     * Test -PPT2
     */
    @RunWith(Parameterized.class)
    public static class gettersSetters {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public gettersSetters() {
        }
        @Test
        public void gettersSettersTest() {
            Calendar calendar = Calendar.getInstance();
            PersonalPage page = new PersonalPage("Shir");
            assertTrue(page.addToPageData("Time",calendar.getTime()));
            assertFalse(page.addToPageData("Time",calendar.getTime()));
            assertEquals(calendar.getTime(),page.getPageData("Time"));
            assertNull(page.getPageData("fefe"));
            assertFalse(page.editPageData("fdsc",calendar.getTime()));
            assertTrue(page.editPageData("Time",calendar.getTime()));

            page.setName("Me");
            assertEquals("Me",page.getName());




        }
    }


}
