package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Guest;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Business_Layer.UserManagement.SystemAdministrator;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.Users_Menu.GuestUserMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class SystemAdminMenuTest {

    /**
     * Test - GM1
     */
    @RunWith(Parameterized.class)
    public static class AdminMenuTest {
        //parameter

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public AdminMenuTest(){
            //init parameters
        }

        @Test
        public void AdminTest1() {
            Subscription g = new SystemAdministrator("Admin","123456","a@a.a");
            DataManagement.setSubscription(g);
            DataManagement.setCurrent(g);

            GuestUserMenu GM = new GuestUserMenu();
            String[] args = new String[20];
            //add strings to args!!!!!!


            ActionStatus ac = GM.presentUserMenu(args);
            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());


        }
    }

}
