package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Guest;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.DisplayManager;
import Presentation_Layer.StartSystem;
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
        String number;
        String arg1;
        String arg2;
        String arg3;
        String arg4;
        boolean ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //2.2 Register to system
                    {"1","miki","123","my@mail.com","Fan",false},// Register information invalid
                    {"1","miki","123456789","my@mail.com","Fan",true} ,//Registered
                    // 2.3 login to system
                    {"2","lolo","123456",null,null,false}, // not login user not found
                    {"2","fUser","123456",null,null,true},// good login
                    // 2.4 search personal pages in system
                    {"3","who are you",null,null,null,true}, // wrong search
                    {"3","Coach",null,null,null,true}, // search by category
                    {"3","shir",null,null,null,true}, // search by name
            });
        }

        public GustMenuTest(String number, String arg1, String arg2,String arg3,String arg4,boolean ans){
            this.number = number;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.ans = ans;
        }

        @Test
        public void GustMenu1() {
            if(number.equals("1")){
                StartSystem sys = new StartSystem();
                sys.startFromDB();
            }
            DataManagement.setCurrent(DataManagement.containSubscription("Guest"));
            DataManagement.setSubscription(DataManagement.containSubscription("Guest"));

            GuestUserMenu GM = new GuestUserMenu();
            ActionStatus ac = GM.presentUserMenu(new String[]{number,arg1,arg2,arg3,arg4});

            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());

        }
    }

}
