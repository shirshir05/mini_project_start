package acceptensTest;

import BusniesServic.Business_Layer.Game.Game;
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
                    // 3.2 registration for tracking personal pages
                    {"2","noam",null}, // =>There is no such page
                    {"2","cUser",null},// =>You were successfully registered to the Team page
                    //(3.3) Games alert notification
                    {"3","80",null}, //=>There is no such game in the system
                    {"3","1",null}, //=>You were registered successfully to the game alerts
                    //(3.4) Insert a complaint
                    {"4","I can't change my username on the system",null}, //=>Complaint added successfully
                    {"4","",null}, //=>Complaint cannot be empty
                    //(3.5) Viewing Search History
                    {"5",null,null}, //=>no History
                    //(3.5) Search
                    {"7","fa",null}, //=>
                    //(3.5) Viewing Search History
                    {"5",null,null}, //=>have History
                    //(3.6) Edit personal information
                    {"6","1","shir cohen"}, //=>The name of the Subscription was successfully changed!
                    {"6","2","shir0@"}, //=>The new email is not legal

            });
        }

        public FanTest(String number, String arg1, String arg2){
            this.number = number;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Test
        public void FanMenu1() {
            if(number.equals("1")){
                StartSystem sys = new StartSystem();
                sys.startFromDB();
            }
            DataManagement.setCurrent(DataManagement.containSubscription("shir"));
            DataManagement.setSubscription(DataManagement.containSubscription("shir"));

            FanUserMenu FM = new FanUserMenu();
            ActionStatus ac =  FM.presentUserMenu(new String[]{number,arg1,arg2});

            System.out.print(ac.isActionSuccessful() + " " +ac.getDescription());



        }
    }
}
