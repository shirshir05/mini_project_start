package acceptensTest;

import BusniesServic.Business_Layer.UserManagement.Referee;
import BusniesServic.Business_Layer.UserManagement.UnionRepresentative;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.RefereeUserMenu;
import Presentation_Layer.Users_Menu.TeamUsersMenu;
import Presentation_Layer.Users_Menu.UnionUserMenu;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Ref;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class UnionUserMenuTest {

    /**
     * Test - UU1
     */
    @RunWith(Parameterized.class)
    public static class UnionTest {
        //parameter
        String arg0;
        String arg1;
        String arg2;
        String arg3;
        String arg4;
        String arg5;
        String arg6;
        boolean result;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                     // Aadd leauge
                    {"1", "A", null , null, null, null, null, true},  // => true - leauge created
                    // Add Season
                    {"2", "A", "2012", "3", "2", "1" , null,  true}, // => true - Season Added
                    {"2", "B", "2012", "2", "2", "1", null, false}, // => false - Wrong League
                    {"2", "A", "1700", "3", "2", "1" , null,  false}, // => false - Wrong Season
                    {"2", "A", "2012", "-1", "2", "1" , null, false}, // => false - Wrong Policy
                    // Manage Referee
                        // add referee
                        {"3", "1", "Raz", "3124", "Raz@gmail.com", null , null,  true}, // => true - Referee added
                        {"3", "1", "Raz" , "3124", "Razvmail.com", null , null,  false}, // => false - Wrong email
                        // delete
                        {"3", "2", "Raz", null, null, null , null,  true}, // => true - Referee deleted
                        {"3", "2", "Dan", null, null, null , null,  false}, // => false - Wrong referee
                        // set referee in league
                        {"3", "3", "Raz", "A", "2012", null , null,  true}, // => true - Referee deleted
                        {"3", "3", "Dan", "A", "2012", null , null,  false}, // => false - Wrong referee
                        {"3", "3", "Raz", "B", "2012", null , null,  false}, // => false - Wrong league
                        {"3", "3", "Raz", "A", "1700", null , null, false}, // => false - Wrong Season
                    // Update Score Policy
                    {"4", "A", "2012", "3", "2", "1" , null, true}, // => true - Season Added
                    {"4", "B", "2012", "2", "2", "1", null, false}, // => false - Wrong League
                    {"4", "A", "1700", "3", "2", "1" , null, false}, // => false - Wrong Season
                    {"4", "A", "2012", "-1", "2", "1" , null, false}, // => false - Wrong Policy
                    // Manage Game
                    // Finance
                        //Set maximum player salary
                        {"6", "1", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "1", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set minimum player salary
                        {"6", "2", 8000 , null, null, null, null, true},  // => true - setted
                        {"6", "2", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set max coach salary
                        {"6", "3", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "3", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set minimum coach salary
                        {"6", "4", 8000 , null, null, null, null, true},  // => true - setted
                        {"6", "4", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set maximum maintenance expense
                        {"6", "5", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "5", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set maximum asvertisement expense
                        {"6", "6", 9000, null, null, null, null, true},  // => true - setted
                        {"6", "6", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set maximum uniform expense
                        {"6", "7", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "7", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set maximum other expense
                        {"6", "8", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "8", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set maximmum referee salary
                        {"6", "9", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "9", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set minimum referee salary
                        {"6", "10", 8000 , null, null, null, null, true},  // => true - setted
                        {"6", "10", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set maximum union representative salary
                        {"6", "11", 9000 , null, null, null, null, true},  // => true - setted
                        {"6", "11", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Set minimum union representative salary
                        {"6", "12", 8000 , null, null, null, null, true},  // => true - setted
                        {"6", "12", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Add expense to union
                        {"6", "13", 120 , null, null, null, null, true},  // => true - setted
                        {"6", "13", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Add income to union
                        {"6", "14", 120 , null, null, null, null, true},  // => true - setted
                        {"6", "14", -120 , null, null, null, null, false},  // => false - wrong budge
                        //Get union balance
                        {"6", "15", 120 , null, null, null, null, true},  // => true - setted
                        {"6", "15", -120 , null, null, null, null, false},  // => false - wrong budge
                    // Exit
                    {"4" , "Dan" , "1234" , null , null , null , null, true}, // => true - Exit
                    {"4" , "Dan" , "4321" , null , null , null , null, false}, // => false - wrong pasword
                    {"4" , "Razk" , "1234" , null , null , null , null, false}, // => false - wrong username

            });
        }

        public UnionTest(String arg0, String arg1, String arg2, String arg3, String arg4 , String arg5, String arg6, boolean flag){
            this.arg0 = arg0;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.arg5 = arg5;
            this.arg6 = arg6;
            this.result=flag;
        }

        @Test
        public void UnionMenu1() {
            DataManagement.cleanAllData();
            UnionRepresentative union = new UnionRepresentative("Dan", "1234", "raz@gmail.com");
            DataManagement.setSubscription(union);
            DataManagement.setCurrent(union);
            Referee ref = new Referee("Raz","4312","fdfe@gmail.com");
            DataManagement.setSubscription(ref);
            DataManagement.setCurrent(ref);
            UnionUserMenu UM = new UnionUserMenu();
            String[] args = {arg0,arg1,arg2,arg3,arg4,arg5,arg6};
            ActionStatus ac = UM.presentUserMenu(args);
            assertEquals(ac.isActionSuccessful(),result);
            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());
            DataManagement.cleanAllData();



        }
    }




}
