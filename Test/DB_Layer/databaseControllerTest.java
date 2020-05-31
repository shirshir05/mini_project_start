package DB_Layer;
import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Items.Game.Game;
import Business_Layer.Business_Items.Game.League;
import Business_Layer.Business_Items.Game.PointsPolicy;
import Business_Layer.Business_Items.Game.Season;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.Fan;
import Business_Layer.Business_Items.UserManagement.Referee;
import Business_Layer.Business_Items.UserManagement.Subscription;
import Business_Layer.Enum.EventType;
import DB_Layer.JDBC.sqlConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class databaseControllerTest {
    public static sqlConnection sql = new sqlConnection();
    public static databaseController data = new databaseController();

    /**
     * Test - UC1+2- Register and Login
     */
    @RunWith(Parameterized.class)
    public static class registerAndLoginUCTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public registerAndLoginUCTest() {
        }

        @Test
        /*
         TEST INSERTION AND OF NEW USERS, AND GETTING USER BY NAME
         */
        public void insertUserTest() {
            Fan newFan = new Fan("raz","123","123@post.bgu.ac.il");
            DataManagement.setSubscription(newFan);
            assertEquals("raz",DataManagement.containSubscription("raz").getUserName());
        }

        @Test
        /*
         TEST DELETE OF NEW USERS, AND GETTING USER BY NAME
         */
        public void deleteUserTest() {
            DataManagement.removeSubscription("raz");
            assertNull(DataManagement.containSubscription("raz"));
        }

        @Test
        /*
         TEST GETTING USER BY ROLE
         */
        public void insertUserUnionTest() {
            Fan newFan = new Fan("raz","123","123@post.bgu.ac.il");
            DataManagement.setSubscription(newFan);
            HashSet<Subscription> list = data.loadUsersByRole("Fan");
            int i=0;
            String name="";
            for (Subscription s : list) {
                if (i==0){
                    name=s.getUserName();
                }
                else{
                    break;
                }
                i++;
            }
            assertEquals(name,"raz");
            DataManagement.removeSubscription("raz");
        }
    }

    /**
     * Test - UC3- create team test
     */
    @RunWith(Parameterized.class)
    public static class CreateTeamTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public CreateTeamTest() {
        }

        @Test
        /*
         TEST INSERTION AND DELETE OF NEW TEAM
         */
        public void insertTeamTest() {
            Team t = new Team("Maccabi","Teddi");
            DataManagement.addToListTeam(t);
            assertEquals(DataManagement.findTeam("Maccabi").getName(),"Maccabi");
        }


        @Test
        public void deleteTeamTest() {
            Team t = new Team("Maccabi","Teddi");
            sql.delete("Team", new String[]{"Maccabi"});
            assertNull(DataManagement.findTeam("Maccabi"));
        }
    }

    /**
     * Test - UC4- alerts test
     */
    @RunWith(Parameterized.class)
    public static class alertsTest {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public alertsTest() {
        }
        @Test
        public void testAlertToUser() {
            String checkEqualsTo = "newAlert";
            DataManagement.removeSubscription("raz");
            HashSet<String> alerts = new HashSet<>();
            alerts.add("newAlert");
            Fan newFan = new Fan("raz","123","123@post.bgu.ac.il");
            checkEqualsTo="";
            newFan.setAllAlerts(alerts);
            DataManagement.setSubscription(newFan);
            assertEquals(checkEqualsTo,DataManagement.containSubscription("raz").returnAlerts());
            DataManagement.removeSubscription("raz");
        }
    }

    /**
     * Test - UC5+6+7+8- Game tests
     */
    @RunWith(Parameterized.class)
    public static class gameTests {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public gameTests() {
        }
        @Test
        public void testCreateGameWithPoliciesAndEvents() {
            Team t1 = new Team("Maccabi1","Teddi");
            DataManagement.addToListTeam(t1);
            Team t2 = new Team("Maccabi2","Teddi");
            DataManagement.addToListTeam(t2);
            Referee ref1 = new Referee("ref1","123","123@post.bgu.ac.il");
            Referee ref2 = new Referee("ref2","123","123@post.bgu.ac.il");
            Referee ref3 = new Referee("ref3","123","123@post.bgu.ac.il");
            DataManagement.setSubscription(ref1);
            DataManagement.setSubscription(ref2);
            DataManagement.setSubscription(ref3);
            League leg1 = new League("A");
            Season ses1 = new Season("2019");
            DataManagement.addToListLeague(leg1);
            PointsPolicy policy = new PointsPolicy(2,1,0);
            DataManagement.addNewSeason("A",ses1,policy);
            Game g = new Game("Teddi",LocalDate.now(),t1,t2,1);
            g.setLinesman1Referee(ref1);
            g.setHeadReferee(ref2);
            g.setLinesman2Referee(ref3);
            g.setLeague("A");
            g.setSeason("2019");
            g.updateNewEvent("Maccabi1","raz", EventType.goal);
            g.setStartAndEndTime(LocalDateTime.of(2017, 2, 13, 15, 56),LocalDateTime.of(2017, 2, 13, 15, 57));
            DataManagement.addGame(g);
            assertEquals(DataManagement.getGame(1).getField(),"Teddi");
            assertEquals(DataManagement.getGame(1).getEventList().size(),0);
        }
    }

}