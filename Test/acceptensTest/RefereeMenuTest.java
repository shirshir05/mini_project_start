package acceptensTest;

import BusinessService.Business_Layer.Game.Game;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Player;
import BusinessService.Business_Layer.UserManagement.Referee;
import BusinessService.Business_Layer.UserManagement.SystemAdministrator;
import BusinessService.Business_Layer.UserManagement.UnifiedSubscription;
import BusinessService.Enum.ActionStatus;
import BusinessService.Service_Layer.DataManagement;
import BusinessService.Service_Layer.GameSettingsController;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.RefereeUserMenu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class RefereeMenuTest {

    /**
     * Test - FM1
     */
    @RunWith(Parameterized.class)
    public static class RefereeTest {
        String arg0;
        String arg1;
        String arg2;
        String arg3;
        String arg4;
        String arg5;
        boolean result;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //Edit name

                    {"1", "1", "Raz", null, null, null, true},  // => true - name changed   0
                    {"1", "1", null , null, null, null, false},  // => false- name is null   1
                    // Edit mail
                    {"1", "2", "ki@gmail.com", null, null, null, true},  // => true - mail changed  2
                    {"1", "2", "ki.com", null, null, null, false},  // => false- illegal mail   3
                    //Edit qualification
                    {"1", "1", "Raz", null, null, null, true},  // => true - qualification changed   4
                    {"1", "1", null , null, null, null, false},  // => false- qualification is null   5
                     // See games i am referee in
                    {"2" , null , null ,null , null , null , true},  //=> true- print the games      6
                    // Add event in game
                    {"3" , "1", "Maccabi", "Oren", "goal", null, true}, //  => false- event added to the game  7
                    {"3" , "5", "Maccabi", "Raz", "goal", null, false}, //  => false- wrong game id      8
                    {"3" , "5", "Hapoel", "Raz", "goal", null, false}, //  =>  false- wrong team name    9
                    {"3" , "1", null , "Raz", "goal", null, false}, //  =>  false- wrong team name    10
                    {"3" , "1", "Maccabi", "Happy", "goal", null, false}, //  => false - wrong player name   11
                    {"3" , "5", "Maccabi", null , "goal", null, false}, //  => false - wrong player name    12
                    {"3" , "1", "Maccabi", "Oren", "flipflop", null, false}, //  => false- wrong event     13
                    {"3" , "1", "Maccabi", "Raz", null, null, false}, //  => false- wrong event       14
                    // Exit / Logout
                    {"4" , "Raz" , "1234" , null , null , null , true}, // => true - Exit
                    {"4" , "Raz" , "4321" , null , null , null , false}, // => false - wrong pasword
                    {"4" , "Razk" , "1234" , null , null , null , false}, // => false - wrong username
                     // invalid input
                     {"1", "3", null , null, null, null, false},  // => false- invalid input
                     {"4", null , null , null, null, null, false},  // => false- invalid input
            });
        }

        public RefereeTest(String s_arg0, String s_arg1, String s_arg2, String s_arg3, String s_arg4, String s_arg5, boolean flag){
           arg0=s_arg0;
           arg1=s_arg1;
           arg2=s_arg2;
           arg3=s_arg3;
           arg4=s_arg4;
           arg5=s_arg5;
           result=flag;


        }

        @Test
        public void  RefereeMenu1(){
            StartSystem.cleanSystem();
            Game.game_id =0;
            GameSettingsController g = new GameSettingsController();
            Referee ref = new Referee("Raz","1234","raz@post.bgu.ac.il");
            DataManagement.setCurrent(ref);
            DataManagement.setSubscription(ref);
            Team host = new Team("Maccabi","Teddi");
            UnifiedSubscription p = new UnifiedSubscription("Oren","frfr","raz@gmail.com");
            p.setNewRole(new Player(p.getUserName()));
            DataManagement.setSubscription(p);
            host.addOrRemovePlayer(p,1);
            Team guest = new Team("Haifa","Sami");
            DataManagement.addToListTeam(host);
            DataManagement.addToListTeam(guest);
            Referee r1 = new Referee("ben","123","dw@gmail.com");
            Referee r2 = new Referee("ziv", "321", "de@gmail.com");
            DataManagement.setSubscription(r1);
            DataManagement.setSubscription(r2);
            SystemAdministrator sys = new SystemAdministrator("sys","123","sys@gmail.com");
            DataManagement.setSubscription(sys);
            DataManagement.setCurrent(sys);
            //g.createGame(LocalDate.now(),"Teddi","Maccabi","Haifa","Raz", "ben", "ziv");
            DataManagement.setCurrent(ref);
            RefereeUserMenu RM = new RefereeUserMenu();
            String[] args = {arg0,arg1,arg2,arg3,arg4};
            //add strings to args!!!!!!
            ActionStatus ac = RM.presentUserMenu(args);
            assertEquals(ac.isActionSuccessful(),result);
            System.out.println(ac.isActionSuccessful() + " " +ac.getDescription());
            DataManagement.cleanAllData();
        }
    }
}
