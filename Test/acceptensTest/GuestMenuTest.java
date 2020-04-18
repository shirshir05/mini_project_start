package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Guest;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.DisplayManager;
import Presentation_Layer.Users_Menu.GuestUserMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class GuestMenuTest {

    /**
     * Test - GM1
     */
    @RunWith(Parameterized.class)
    public static class GustMenuTest {
        //parameter

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public GustMenuTest(){
            //init parameters
        }

        @Test
        public void GustMenu1() {
            Subscription g = new Guest("Guest","123456","g@g.g");
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
