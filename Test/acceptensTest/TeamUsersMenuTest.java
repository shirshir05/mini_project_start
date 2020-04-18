package acceptensTest;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.FanUserMenu;
import Presentation_Layer.Users_Menu.TeamUsersMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

public class TeamUsersMenuTest {
    /**
     * Test - TM1
     */
    @RunWith(Parameterized.class)
    public static class TeamTest {
        //parameter
        String number;
        String arg1;
        String arg2;
        String arg3;
        String arg4;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //(6.1) update asset
                    //(6.1) update player
                    {"1","1","t","PlayerThree","1"},// =>The Team does not exist in the system.

            });
        }

        public TeamTest(String number, String arg1, String arg2, String arg3, String arg4){
            this.number = number;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
        }

        @Test
        public void TeamTest1() {
            if(number.equals("1")){
                StartSystem sys = new StartSystem();
                sys.startFromDB();
            }
            DataManagement.setCurrent(DataManagement.containSubscription("ortal"));
            DataManagement.setSubscription(DataManagement.containSubscription("ortal"));

            TeamUsersMenu FM = new TeamUsersMenu();
            ActionStatus ac =  FM.presentUserMenu(new String[]{number,arg1,arg2,arg3,arg4});

            System.out.print(ac.isActionSuccessful() + " " +ac.getDescription());



        }
    }
}
