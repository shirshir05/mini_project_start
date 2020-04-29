package acceptensTest;

import BusinessService.Enum.ActionStatus;
import BusinessService.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.SystemAdminUserMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class SystemAdminMenuTest {

    /**
     * Test - AM1
     */
    @RunWith(Parameterized.class)
    public static class AdminMenuTest {
        //parameter
        String number;
        String arg1;
        String arg2;
        boolean ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //
                    {"1","teamteam",null,false},// close non existing team
                    {"1","teamOne",null,true} ,//close team
                    //
                    {"2","doli",null,false}, // user not exists
                    {"2","del",null,true},// remove subscription permanently
                    //
                    {"3","1",null,true}, // see complaint
                    {"3","2","",false}, // invalid response to complaint
                    {"3","2","thank you for letting us know about the problem, we will fix it a.s.a.p :)",true}, // respond to complaint
                    //
                    {"4","1",null,true}, // watch log
            });
        }

        public AdminMenuTest(String number, String arg1, String arg2,boolean ans){
            this.number = number;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.ans = ans;
        }

        @Test
        public void AdminTest1() {
            if(number.equals("1")){
                StartSystem sys = new StartSystem();
                sys.startFromDB();
            }
            DataManagement.setCurrent(DataManagement.containSubscription("SysAdmin"));
            DataManagement.setSubscription(DataManagement.containSubscription("SysAdmin"));

            SystemAdminUserMenu AM = new SystemAdminUserMenu();
            ActionStatus ac = AM.presentUserMenu(new String[]{number,arg1,arg2});

            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());


        }
    }

}
