package BusinessService.Business_Layer.UserManagement;

import BusinessService.Business_Layer.Game.Game;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Enum.PermissionAction;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TeamOwnerTest {

    private static UnifiedSubscription createTeamOwner(String userName, String password, String email) {
        UnifiedSubscription us = new UnifiedSubscription(userName, password, email);
        us.setNewRole(new TeamOwner());
        return us;
    }

    /**
     * Test - TO1
     */
    @RunWith(Parameterized.class)
    public static class TeamOwner1{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"},
                    {"t2","123456","shir0@post.bgu.ac.il"},
                    {"t3","123456","shir0@post.bgu.ac.il"},
                    {"Rt4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public TeamOwner1(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        private String getHash(String password){
            String sha1 = "";
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-1");
                digest.reset();
                digest.update(password.getBytes("utf8"));
                sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
            } catch (Exception e){
                e.printStackTrace();
            }
            return sha1;
        }


        @Test
        public void TeamOwner1Test() {
            UnifiedSubscription teamOwner = createTeamOwner(userName,password,email);
            assertEquals(teamOwner.getUserName(),(userName));
            assertEquals(teamOwner.getPassword(),getHash(password));
            assertEquals(teamOwner.getEmail(),(email));
            assertNull(teamOwner.teamOwner_getAppointedByTeamOwner());
        }

    }//TeamOwner1


    /**
     * Test - TO2
     */
    @RunWith(Parameterized.class)
    public static class getAppointedByTeamOwner{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"},
                    {"t2","123456","shir0@post.bgu.ac.il"},
                    {"t3","123456","shir0@post.bgu.ac.il"},
                    {"Rt4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public getAppointedByTeamOwner(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void getAppointedByTeamOwnerTest() {
            UnifiedSubscription teamOwner = createTeamOwner(userName,password,email);
            assertNull(teamOwner.teamOwner_getAppointedByTeamOwner());
        }
    }//getAppointedByTeamOwner


    /**
     * Test - TO3
     */
    @RunWith(Parameterized.class)
    public static class setAppointedByTeamOwner{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"},
                    {"t2","123456","shir0@post.bgu.ac.il"},
                    {"t3","123456","shir0@post.bgu.ac.il"},
                    {"Rt4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public setAppointedByTeamOwner(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void setAppointedByTeamOwnerTest() {
            UnifiedSubscription teamOwner = new UnifiedSubscription(userName,password,email);
            teamOwner.setNewRole(new TeamOwner());
            UnifiedSubscription appointedBy = new UnifiedSubscription("shir","123456","");
            appointedBy.setNewRole(new TeamOwner());
            teamOwner.teamOwner_setAppointedByTeamOwner(appointedBy.userName);
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Edit_team));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Appointment_of_team_owner));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Remove_Appointment_of_team_owner));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Appointment_of_team_manager));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Remove_Appointment_of_team_manager));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Appointment_of_player));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Remove_Appointment_of_player));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Team_financial));
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Close_team));
            teamOwner.teamOwner_setAppointedByTeamOwner(null);
            assertNull(teamOwner.teamOwner_getAppointedByTeamOwner());
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Edit_team));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Appointment_of_team_owner));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Remove_Appointment_of_team_owner));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Appointment_of_team_manager));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Remove_Appointment_of_team_manager));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Appointment_of_player));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Remove_Appointment_of_player));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Team_financial));
            assertFalse(teamOwner.getPermissions().check_permissions(PermissionAction.Close_team));
            teamOwner.getPermissions().add_permissions(PermissionAction.Close_team);
            assertTrue(teamOwner.getPermissions().check_permissions(PermissionAction.Close_team));




        }
    }//setAppointedByTeamOwner



    /**
     * Test - TO4
     */
    @RunWith(Parameterized.class)
    public static class update{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t","123456","shir0@post.bgu.ac.il"}

            });
        }
        public update(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void updateTest() {
            UnifiedSubscription teamOwner = createTeamOwner(userName,password,email);
            assertEquals(teamOwner.alerts.size(),0);
            teamOwner.update(new Game("s", LocalDate.of(1995,8,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(teamOwner.alerts.size(),1);
            teamOwner.update(new Game("s",LocalDate.of(1995,8,8),new Team("1","r"),new Team("2","r")),"The ");
            assertEquals(teamOwner.alerts.size(),2);
        }
    }//update


    /**
     * Test - TO5
     */
    @RunWith(Parameterized.class)
    public static class toString{

        public String userName;
        public String password;
        public String email;
        public String toString;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"r1","123456","shir0@post.bgu.ac.il",
                            "name: null" + "\n" +
                            "email: shir0@post.bgu.ac.il" + "\n"+
                                    "TeamOwner: \n" + "Appointed by: null"}

            });
        }
        public toString(String userName, String password, String email,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.toString = ans;
        }
        @Test
        public void toStringTest() {
            UnifiedSubscription teamOwner = createTeamOwner(userName,password,email);
            assertEquals(teamOwner.toString(),toString);
        }
    }//toString





}