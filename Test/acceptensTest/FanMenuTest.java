package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Guest;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
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
        String number;
        String arg1;
        String arg2;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //3.1 Logout
                    {"1","shir","1"},// =>password incorrect
                    {"1","shir","12345"} ,// =>Exit
                    // 3.2 egistration for tracking personal pages
                    {"2","noam","1"} //
            });
        }

        public FanTest(String number, String arg1, String arg2){
            this.number = number;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Test
        public void FanMenu1() {
            Fan fan = new Fan("shir","12345","shir0@post.bgu.ac.il");
            StartSystem sys = new StartSystem();
            sys.startFromDB();
            DataManagement.setCurrent(fan);
            DataManagement.setSubscription(fan);

            FanUserMenu FM = new FanUserMenu();
            ActionStatus ac =  FM.presentUserMenu(new String[]{number,arg1,arg2});

            System.out.print(ac.isActionSuccessful() + " " +ac.getDescription());


        }
    }
}
