package acceptensTest;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.FanUserMenu;
import Presentation_Layer.Users_Menu.TeamUsersMenu;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
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
        String arg5;
        String arg6;
        boolean ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //(6.1) update asset
                    //(6.1) update player
                    {"1","1","t","PlayerThree","1",null,null,false},// =>The Team does not exist in the system.
                    {"1","1","teamOne","PlayerThree","1",null,null,true},//=>The player was successfully removed from the team.
                    {"1","1","teamOne","teamCoachTwo","1",null,null,false},//=>The username is not defined as a player on the system.
                    //(6.1) update Coach
                    {"1","2","teamOne","teamCoachTwo","1",null,null,true},//=>The Coach was successfully added to the team.
                    {"1","2","teamOne","teamCoachTwo","0",null,null,true},//=>The Coach was successfully removed from the team.
                    {"1","2","teamOne","teamCoachTwo","0",null,null,false},//=>The Coach is not in the team.
                    //(6.1) update filed
                    {"1","3","teamOne","filed1","1",null,null,true},//=>The asset was added to the team
                    {"1","3","teamOne","fil","0",null,null,false},//=>The team doesnt contains this asset
                    //(6.1) update team owner
                    {"1","4","teamOne","teamCoachTwo","1",null,null,false},//=>The username is not defined as a Team Owner on the system.
                    {"1","4","teamOne","teamOwnerThree","1",null,null,false},//=>You are already set as a team owner.
                    {"1","4","teamOne","teamNew","1",null,null,true},//=>The Team Owner was successfully added to the team.
                    //(6.1) update team manager
                    {"1","5","teamOne","teamManagerTwo","1",null,null,true},//=>The Team Manager was successfully added to the team.
                    {"1","5","teamOne","teamManagerNew","0",null,null,false},//=>The Team Manager is not in the team.
                    //(6.6) change status
                    {"2","1","teamOne",null,null,null,null,false},//=>The group is already set 1
                    {"2","0","teamOne",null,null,null,null,true},//=>The status of the group has changed successfully.
                    //(6.7)Team budget
                    {"3","1",null,null,null,null,null,true},//=>Updated successfully
                    //income
                    {"3","2","teamOne","10000",null,null,null,true},//=>Income updated
                    {"3","2","t","10000",null,null,null,false},//=>Team not found
                    //expense
                    {"3","3","teamOne","150000",null,null,null,false},//=>Salary is not within limits
                    {"3","3","teamOne","6000",null,null,null,true},//=>Operation succeeded
                    //getTeamBalanceForQuarter
                    {"3","4","teamOne",null,null,null,null,true},//=>4000.0
                    {"3","4","t",null,null,null,null,false},//=>The team not found

                    {"4","1",null,"Israel","5","7","noam",false},//=>There is no subscription in the system by this username.
                    {"4","teamCoachOne",null,"Israel","5","7","noam",true},//=>The personal page of coach was successfully update!

            });
        }

        public TeamTest(String number, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6,boolean ans){
            this.number = number;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.ans = ans;
            this.arg5 =arg5;
            this.arg6=arg6;
        }

        @Test
        public void TeamTest1() {
            if(number.equals("1") && arg1.equals("1")){
                StartSystem sys = new StartSystem();
                sys.startFromDB();
            }
            DataManagement.setCurrent(DataManagement.containSubscription("teamOwnerOne"));
            DataManagement.setSubscription(DataManagement.containSubscription("teamOwnerOne"));
            if(number.equals("4") && arg1.equals("teamCoachOne")){
                DataManagement.setCurrent(DataManagement.containSubscription("teamCoachOne"));
                DataManagement.setSubscription(DataManagement.containSubscription("teamCoachOne"));
            }


            TeamUsersMenu FM = new TeamUsersMenu();
            ActionStatus ac =  FM.presentUserMenu(new String[]{number,arg1,arg2,arg3,arg4, this.arg5 , this.arg6});

            assertEquals(ac.isActionSuccessful(),ans);
            System.out.print(ac.isActionSuccessful() + " " +ac.getDescription());

        }
    }
}
