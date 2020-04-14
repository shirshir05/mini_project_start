package Busnies_Servic.Business_Layer.UserManagement;

import Busnies_Servic.Business_Layer.Game.Game;
import Busnies_Servic.Business_Layer.TeamManagement.Team;
import Busnies_Servic.PermissionAction;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class SystemAdministratorTest {



    /**
     * Test - SA1
     */
    @RunWith(Parameterized.class)
    public static class SystemAdministrator1{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"SA1","123456","shir0@post.bgu.ac.il"},
                    {"SA2","123456","shir0@post.bgu.ac.il"},
                    {"SA3","123456","shir0@post.bgu.ac.il"},
                    {"SA4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public SystemAdministrator1(String userName, String password, String email) {
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
        public void SystemAdministrator1Test() {
            SystemAdministrator systemAdministrator = new SystemAdministrator(userName,password,email);
            assertEquals(systemAdministrator.getUserName(),(userName));
            assertEquals(systemAdministrator.getPassword(),getHash(password));
            assertEquals(systemAdministrator.getEmail(),(email));
            assertTrue(systemAdministrator.getPermissions().check_permissions(PermissionAction.Close_team_perpetually));
            assertTrue(systemAdministrator.getPermissions().check_permissions(PermissionAction.Respond_to_complaints));
            assertTrue(systemAdministrator.getPermissions().check_permissions(PermissionAction.Removing_Subscriptions));
            assertTrue(systemAdministrator.getPermissions().check_permissions(PermissionAction.watch_log));
            assertTrue(systemAdministrator.getPermissions().check_permissions(PermissionAction.Recommendation_system));

        }
    }//SystemAdministrator1


    /**
     * Test - SA2
     */
    @RunWith(Parameterized.class)
    public static class update{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"SA1","123456","shir0@post.bgu.ac.il"},

            });
        }
        public update(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void updateTest() {
            SystemAdministrator systemAdministrator = new SystemAdministrator(userName,password,email);
            assertEquals(systemAdministrator.alerts.size(),0);
            systemAdministrator.update(new Game("s", LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(systemAdministrator.alerts.size(),1);
            systemAdministrator.update(new Game("s",LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"The ");
            assertEquals(systemAdministrator.alerts.size(),2);
        }
    }//update


    /**
     * Test - SA3
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
                    {"r1","123456","shir0@post.bgu.ac.il","SystemAdministrator: \n" +
                            "name: null\n" +
                            "email: shir0@post.bgu.ac.il"}


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
            SystemAdministrator systemAdministrator = new SystemAdministrator(userName,password,email);
            assertEquals(systemAdministrator.toString(),toString);
        }
    }//toString


}