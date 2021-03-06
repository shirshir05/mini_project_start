package Business_Layer.Business_Control;

import Business_Layer.Business_Items.Game.Game;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class AlertControllerTest {
    public static AlertController n = new AlertController();

    private static UnifiedSubscription createPlayer(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name,password,email);
        us.setNewRole(new Player(us.getUserName()));
        return us;
    }

    private static UnifiedSubscription createCoach(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name,password,email);
        us.setNewRole(new Coach(us.getUserName()));
        return us;
    }

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
            UnifiedSubscription player = createPlayer("play","frp","wp");
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
            UnifiedSubscription player = createPlayer("play","frp","wp");
            DataManagement.setSubscription(player);
            DataManagement.setCurrent(player);
            assertEquals(n.fanRegisterToPage("me").getDescription(),"You are not a Fan");
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            assertEquals(n.fanRegisterToPage("me").getDescription(),"There is no such page");
            assertEquals(n.fanRegisterToPage("play").getDescription(),"You were successfully registered to the Player page");
            UnifiedSubscription coach = createCoach("coach","frdep","wpde");
            DataManagement.setSubscription(coach);
            assertEquals(n.fanRegisterToPage("coach").getDescription(),"You were successfully registered to the Coach page");
            Team team = new Team("team","de");
            DataManagement.addToListTeam(team);
            assertEquals(n.fanRegisterToPage("team1").getDescription(),"There is no such page");
            DataManagement.setCurrent(null);
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
            DataManagement.cleanAllData();
            assertNull(n.getAllComplaints());
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            n.addComplaint("abc");
            n.addComplaint("vsvs");
            SystemAdministrator s = new SystemAdministrator("sys","vtrv","vre");
            DataManagement.setSubscription(s);
            DataManagement.setCurrent(s);
            HashSet<Complaint> complaints = null;
            ArrayList<String> complaintsContent = new ArrayList<>();
            Iterator it = complaints.iterator();
            complaintsContent.add(((Complaint)it.next()).getDescription());
            complaintsContent.add(((Complaint)it.next()).getDescription());
            assertEquals(complaintsContent.contains("abc"), true);
            assertEquals(complaintsContent.contains("vsvs"), true);
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
            //assertEquals(n.getUnansweredComplaints(),null);
            n.addComplaint("abc");
            n.addComplaint("vsvs");
            SystemAdministrator s = new SystemAdministrator("sys","vtrv","vre");
            DataManagement.setSubscription(s);
            DataManagement.setCurrent(s);
            HashSet<Complaint> complaints =null;
            Iterator iter = complaints.iterator();
            Complaint first = (Complaint)iter.next();
            Complaint second = (Complaint)iter.next();
            //assertEquals(first.getDescription(), "abc");
            //assertEquals(second.getDescription(), "vsvs");
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
            assertEquals(n.answerCompliant(1,"OK").getDescription(),"You do not have the required permissions to answer complaints");
            Fan fan = new Fan("fan","fr","w");
            DataManagement.setSubscription(fan);
            DataManagement.setCurrent(fan);
            n.addComplaint("abc");
            SystemAdministrator s = new SystemAdministrator("sys","vtrv","vre");
            DataManagement.setSubscription(s);
            DataManagement.setCurrent(s);
            HashSet<Complaint> complaints = null;
            Iterator iter = complaints.iterator();
            Complaint first = (Complaint)iter.next();
            assertEquals(n.answerCompliant(1,"first").getDescription(),"An answer to a complaint cannot be empty or null");
            assertEquals(n.answerCompliant(2, "first").getDescription(),"Complaint has been answered successfully");
            assertEquals(n.answerCompliant(3, "first").getDescription(),"Complaint has been answered already");
            DataManagement.cleanAllData();
        }

    }//getUnansweredComplaints





}
