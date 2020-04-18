package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Referee;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.Users_Menu.FanUserMenu;
import Presentation_Layer.Users_Menu.RefereeUserMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class RefereeMenuTest {

    /**
     * Test - FM1
     */
    @RunWith(Parameterized.class)
    public static class RefereeTest {
        //parameter

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public RefereeTest(){
            //init parameters
        }

        @Test
        public void  RefereeMenu1(){
            Referee ref = new Referee("shir","12345","shir0@post.bgu.ac.il");
            DataManagement.setCurrent(ref);
            DataManagement.setSubscription(ref);

            RefereeUserMenu RM = new RefereeUserMenu();
            String[] args = new String[20];
            //add strings to args!!!!!!

            ActionStatus ac = RM.presentUserMenu(args);
            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());
        }
    }
}
