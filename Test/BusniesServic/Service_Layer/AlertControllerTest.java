package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Game.Game;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class AlertControllerTest {
    public static AlertController n = new AlertController();

    /**
     * Test -AC1
     */
    @RunWith(Parameterized.class)
    public static class fanRegisterToGameAlerts{
        int gameNumber;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {2}
            });
        }
        public fanRegisterToGameAlerts(int game_number) {
            gameNumber=game_number;
        }
        @Test
        public void fanRegisterToGameAlertsTest() {
            Player player = new Player("play","frp","wp");
            DataManagement.setSubscription(player);
            DataManagement.setCurrent(player);
            assertEquals(n.fanRegisterToGameAlerts(0).getDescription(),"You are not a Fan");
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            assertEquals(n.fanRegisterToGameAlerts(-1).getDescription(),"There is no such game in the system");
            Game game = new Game("n",null,null,null);
            DataManagement.addGame(game);
            assertEquals(n.fanRegisterToGameAlerts(1).getDescription(),"You were registered successfully to the game alerts");
            Game game2 = new Game("n",null,null,null);
            DataManagement.addGame(game2);
            assertEquals(n.fanRegisterToGameAlerts(2).getDescription(),"You were registered successfully to the game alerts");
            DataManagement.cleanAllData();
        }

    }//fanRegisterToGameAlerts

    /**
     * Test -AC2
     */
    @RunWith(Parameterized.class)
    public static class fanRegisterToPage{
        String username;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz"}
            });
        }
        public fanRegisterToPage(String name) {
            username=name;
        }
        @Test
        public void fanRegisterToPageTest() {
            Player player = new Player("play","frp","wp");
            DataManagement.setSubscription(player);
            DataManagement.setCurrent(player);
            assertEquals(n.fanRegisterToPage("me").getDescription(),"You are not a Fan");
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            assertEquals(n.fanRegisterToPage("me").getDescription(),"There is no such page");
            assertEquals(n.fanRegisterToPage("play").getDescription(),"You were successfully registered to the Player page");
            Coach coach = new Coach("coach","frdep","wpde");
            DataManagement.setSubscription(coach);
            assertEquals(n.fanRegisterToPage("coach").getDescription(),"You were successfully registered to the Coach page");
            Team team = new Team("team","de");
            DataManagement.addToListTeam(team);
            assertEquals(n.fanRegisterToPage("team").getDescription(),"You were successfully registered to the Team page");
            assertEquals(n.fanRegisterToPage("team1").getDescription(),"There is no such page");
            DataManagement.setCurrent(null);
            assertEquals(n.fanRegisterToPage("team").getDescription(),"You are not a Fan");
            DataManagement.cleanAllData();
        }

    }//fanRegisterToPage

    /**
     * Test -AC3
     */
    @RunWith(Parameterized.class)
    public static class addComplaint{
        String username;
        String description;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Bas service"}
            });
        }
        public addComplaint(String name, String arg_description) {
            username=name;
            description=arg_description;
        }
        @Test
        public void addComplaintTest() {
            assertEquals(n.addComplaint(null).getDescription(),"Complaint cannot be empty");
            assertEquals(n.addComplaint("").getDescription(),"Complaint cannot be empty");
            assertEquals(n.addComplaint(description).getDescription(),"You are not a Fan");
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            assertEquals(n.addComplaint(description).getDescription(),"Complaint added successfully");
            DataManagement.cleanAllData();
        }

    }//addComplaint


    /**
     * Test -AC4
     */
    @RunWith(Parameterized.class)
    public static class getAllComplaints{
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }
        public getAllComplaints() {
        }
        @Test
        public void getAllComplaintsTest() {
            assertNull(n.getAllComplaints());
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            n.addComplaint("abc");
            n.addComplaint("vsvs");
            SystemAdministrator s = new SystemAdministrator("sys","vtrv","vre");
            DataManagement.setSubscription(s);
            DataManagement.setCurrent(s);
            HashSet<Complaint> complaints = n.getAllComplaints();
            Iterator iter = complaints.iterator();
            Complaint first = (Complaint)iter.next();
            Complaint second = (Complaint)iter.next();
            assertEquals(first.getDescription(), "vsvs");
            assertEquals(second.getDescription(), "abc");
            DataManagement.cleanAllData();
        }

    }//getAllComplaints

    /**
     * Test -AC5
     */
    @RunWith(Parameterized.class)
    public static class getUnansweredComplaints{
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }
        public getUnansweredComplaints() {
        }
        @Test
        public void getUnansweredComplaintsTest() {
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            assertEquals(n.getUnansweredComplaints(),null);
            n.addComplaint("abc");
            n.addComplaint("vsvs");
            SystemAdministrator s = new SystemAdministrator("sys","vtrv","vre");
            DataManagement.setSubscription(s);
            DataManagement.setCurrent(s);
            HashSet<Complaint> complaints = n.getUnansweredComplaints();
            Iterator iter = complaints.iterator();
            Complaint first = (Complaint)iter.next();
            Complaint second = (Complaint)iter.next();
            assertEquals(first.getDescription(), "abc");
            assertEquals(second.getDescription(), "vsvs");
            DataManagement.cleanAllData();
        }

    }//getUnansweredComplaints

    /**
     * Test -AC6
     */
    @RunWith(Parameterized.class)
    public static class answerCompliant{
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }
        public answerCompliant() {
        }
        @Test
        public void answerCompliantTest() {
            assertEquals(n.answerCompliant(null,"OK").getDescription(),"You do not have the required permissions to answer complaints");
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            n.addComplaint("abc");
            SystemAdministrator s = new SystemAdministrator("sys","vtrv","vre");
            DataManagement.setSubscription(s);
            DataManagement.setCurrent(s);
            HashSet<Complaint> complaints = n.getUnansweredComplaints();
            Iterator iter = complaints.iterator();
            Complaint first = (Complaint)iter.next();
            assertEquals(n.answerCompliant(first,null).getDescription(),"An answer to a complaint cannot be empty or null");
            assertEquals(n.answerCompliant(first,"OK").getDescription(),"Complaint has been answered successfully");
            assertEquals(n.answerCompliant(first,"OK").getDescription(),"Complaint has been answered already");
            DataManagement.cleanAllData();
        }

    }//getUnansweredComplaints





}
