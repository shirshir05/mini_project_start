package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Guest;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.Users_Menu.FanUserMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class FanMenuTest {

    /**
     * Test - FM1
     */
    @RunWith(Parameterized.class)
    public static class FanTest {
        //parameter

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public FanTest(){
            //init parameters
        }

        @Test
        public void FanMenu1() {
            Fan fan = new Fan("shir","12345","shir0@post.bgu.ac.il");
            DataManagement.setCurrent(fan);
            DataManagement.setSubscription(fan);

            FanUserMenu FM = new FanUserMenu();
            String[] args = new String[20];
            //add strings to args!!!!!!


            ActionStatus ac = FM.presentUserMenu(args);
            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());


        }
    }
}
