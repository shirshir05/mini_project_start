package acceptensTest;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.TeamUsersMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class UnionUserMenuTest {

    /**
     * Test - UU1
     */
    @RunWith(Parameterized.class)
    public static class TeamTest {
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
                    {"1", "A", null , null, null, null, true},  // => true - leauge created
                    {"1", null, null , null, null, null, false},  // => false - name is null
                    // Add Season
                    {"2", "A", "2012", "3", "2", "1" }
                    // Manage Referee
                    // Update Score Policy
                    // Manage Game
                    // Finance
                    // Exit


            });
        }

        public TeamTest(String arg0, String arg1, String arg2, String arg3, String arg4 , String arg5, String arg6, boolean flag){
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
        public void TeamTest1() {
        }
    }




}
