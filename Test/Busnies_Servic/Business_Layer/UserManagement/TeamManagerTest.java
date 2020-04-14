package Busnies_Servic.Business_Layer.UserManagement;

import Busnies_Servic.Business_Layer.Game.Game;
import Busnies_Servic.Business_Layer.TeamManagement.Team;
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
public class TeamManagerTest {


    /**
     * Test - TM1
     */
    @RunWith(Parameterized.class)
    public static class TeamManager1{

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
        public TeamManager1(String userName, String password, String email) {
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
        public void TeamManager1Test() {
            TeamManager teamManager = new TeamManager(userName,password,email);
            assertEquals(teamManager.getUserName(),(userName));
            assertEquals(teamManager.getPassword(),getHash(password));
            assertEquals(teamManager.getEmail(),(email));
            assertNull(teamManager.getAppointedByTeamOwner());
        }
    }//TeamManager1



    /**
     * Test - TM2
     */
    @RunWith(Parameterized.class)
    public static class setAppointed_by_teamOwner{

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
        public setAppointed_by_teamOwner(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void setAppointed_by_teamOwnerTest() {
            TeamManager teamManager = new TeamManager(userName,password,email);
            teamManager.setAppointedByTeamOwner(new TeamManager("shir","123456","shir0@post.bgu.ac.il"));
            assertEquals(teamManager.getAppointedByTeamOwner(),new TeamManager("shir","123456","shir0@post.bgu.ac.il"));
        }
    }//setAppointed_by_teamOwner

    /**
     * Test - TM3
     */
    @RunWith(Parameterized.class)
    public static class getAppointed_by_teamOwner{

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
        public getAppointed_by_teamOwner(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void getAppointed_by_teamOwnerTest() {
            TeamManager teamManager = new TeamManager(userName,password,email);
            assertNull(teamManager.getAppointedByTeamOwner());
        }
    }//getAppointed_by_teamOwner

    /**
     * Test - TM4
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
            TeamManager teamManager = new TeamManager(userName,password,email);
            assertEquals(teamManager.alerts.size(),0);
            teamManager.update(new Game("s", LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(teamManager.alerts.size(),1);
            teamManager.update(new Game("s", LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(teamManager.alerts.size(),2);
        }
    }//update




    /**
     * Test - TM5
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
                    {"r1","123456","shir0@post.bgu.ac.il","TeamManager: \n" +
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
            TeamManager teamManager = new TeamManager(userName,password,email);
            assertEquals(teamManager.toString(),toString);
        }
    }//toString




}