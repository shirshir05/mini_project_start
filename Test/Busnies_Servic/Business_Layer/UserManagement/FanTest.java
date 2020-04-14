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
public class FanTest {


    /**
     * Test - F1
     */
    @RunWith(Parameterized.class)
    public static class Fan1{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Fan1","123456","shir0@post.bgu.ac.il"},
                    {"Fan2","123456","shir0@post.bgu.ac.il"},
                    {"Fan3","123456","shir0@post.bgu.ac.il"},
                    {"Fan3", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public Fan1(String userName, String password, String email) {
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
        public void Fan1Test() {
            Fan fan = new Fan(userName,password,email);
            assertEquals(fan.getUserName(),(userName));
            assertEquals(fan.getPassword(),getHash(password));
            assertEquals(fan.getEmail(),(email));
            assertEquals(fan.getPermissions().check_permissions(PermissionAction.write_complaint),true);
            assertEquals(fan.getPermissions().check_permissions(PermissionAction.Search_History),true);
            assertNotNull(fan.list_complaint);

        }
    }//Fan1



    /**
     * Test - F2
     */
    @RunWith(Parameterized.class)
    public static class toString{

        public String userName;
        public String password;
        public String email;
        public String ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Fan1","123456","shir0@post.bgu.ac.il","Fan: \n" + "name: null\n" + "email: shir0@post.bgu.ac.il"}

            });
        }
        public toString(String userName, String password, String email,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.ans = ans;
        }

        @Test
        public void toStringTest() {
            Fan fan = new Fan(userName,password,email);
            assertEquals(fan.toString(),ans);


        }
    }//toString



    /**
     * Test - F3
     */
    @RunWith(Parameterized.class)
    public static class addComplaint{

        public String userName;
        public String password;
        public String email;
        public String ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Fan1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public addComplaint(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void addComplaintTest() {
            Fan fan = new Fan(userName,password,email);
            assertEquals(fan.list_complaint.size(),0);
            Complaint c =new Complaint("shir test");
            assertEquals(c.countObservers(),0);
            assertTrue(fan.addComplaint(c));
            assertEquals(c.countObservers(),1);
            assertEquals(fan.list_complaint.size(),1);
            assertFalse(fan.addComplaint(c));
        }
    }//addComplaint



    /**
     * Test - F4
     */
    @RunWith(Parameterized.class)
    public static class update{

        public String userName;
        public String password;
        public String email;
        public String ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Fan1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public update(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void updateTest() {
            Fan fan = new Fan(userName,password,email);
            assertEquals(fan.alerts.size(),0);
            fan.update(new Game("s", LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(fan.alerts.size(),1);
            fan.update(new Game("s",LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"The ");
            assertEquals(fan.alerts.size(),1);



        }
    }//update

}