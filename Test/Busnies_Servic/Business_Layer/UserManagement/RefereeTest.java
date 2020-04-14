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
public class RefereeTest {


    /**
     * Test - R1
     */
    @RunWith(Parameterized.class)
    public static class Referee1{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"R1","123456","shir0@post.bgu.ac.il"},
                    {"R2","123456","shir0@post.bgu.ac.il"},
                    {"R3","123456","shir0@post.bgu.ac.il"},
                    {"R4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public Referee1(String userName, String password, String email) {
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
        public void Referee1Test() {
            Referee referee = new Referee(userName,password,email);
            assertEquals(referee.getUserName(),(userName));
            assertEquals(referee.getPassword(),getHash(password));
            assertEquals(referee.getEmail(),(email));
            assertEquals(referee.getPermissions().check_permissions(PermissionAction.update_event),true);
            assertEquals(referee.referee_games.size(),0);
        }
    }//Referee1



    /**
     * Test - R2
     */
    @RunWith(Parameterized.class)
    public static class setQualification{

        public String userName;
        public String password;
        public String email;
        public String qualification;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"R1","123456","shir0@post.bgu.ac.il","qualification"}

            });
        }
        public setQualification(String userName, String password, String email,String qualification) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.qualification = qualification;
        }
        @Test
        public void setQualificationTest() {
            Referee referee = new Referee(userName,password,email);
            referee.setQualification(qualification);
            assertEquals(referee.getQualification(),qualification);
        }
    }//setQualification

    /**
     * Test - R3
     */
    @RunWith(Parameterized.class)
    public static class getQualification{

        public String userName;
        public String password;
        public String email;
        public String qualification;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"R1","123456","shir0@post.bgu.ac.il","qualifications"},
                    {"R2","123456","shir0@post.bgu.ac.il",null}
            });
        }
        public getQualification(String userName, String password, String email,String qualification) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.qualification = qualification;
        }
        @Test
        public void getQualificationTest() {
            Referee referee = new Referee(userName,password,email);
            referee.setQualification(qualification);
            assertEquals(referee.getQualification(),qualification);
        }
    }//getQualification


    /**
     * Test - R4
     */
    @RunWith(Parameterized.class)
    public static class addGame{

        public String userName;
        public String password;
        public String email;
        public String qualification;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"R1","123456","shir0@post.bgu.ac.il","qualifications"},
                    {"R2","123456","shir0@post.bgu.ac.il",null}
            });
        }
        public addGame(String userName, String password, String email,String qualification) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.qualification = qualification;
        }
        @Test
        public void addGameTest() {
            Referee referee = new Referee(userName,password,email);
            assertEquals(referee.referee_games.size(),0);
            referee.addGame(new Game("f",null, null, null));
            assertEquals(referee.referee_games.size(),1);

        }
    }//addGame


    /**
     * Test - R5
     */
    @RunWith(Parameterized.class)
    public static class gamesListToString{

        public String userName;
        public String password;
        public String email;
        public String ans;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"R1","123456","shir0@post.bgu.ac.il","You are participates in the next games: 3, 4"}
            });
        }
        public gamesListToString(String userName, String password, String email,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.ans = ans;
        }
        @Test
        public void gamesListToStringTest() {
            Referee referee = new Referee(userName,password,email);
            referee.addGame(new Game("f",null, null, null));
            referee.addGame(new Game("f",null, null, null));
            assertEquals(referee.gamesListToString(),this.ans);
        }
    }//gamesListToString


    /**
     * Test - R6
     */
    @RunWith(Parameterized.class)
    public static class update{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"R1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public update(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void updateTest() {
            Referee referee = new Referee(userName,password,email);
            assertEquals(referee.alerts.size(),0);
            referee.update(new Game("s",LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(referee.alerts.size(),0);
            referee.update(new Game("s", LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"The ");
            assertEquals(referee.alerts.size(),1);
        }
    }//update



    /**
     * Test - R7
     */
    @RunWith(Parameterized.class)
    public static class toString{

        public String userName;
        public String password;
        public String email;
        public String qualification;
        public String toString;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"T1","123456","shir0@post.bgu.ac.il","qualification","Referee: \n" +
                            "name: null\n" +
                            "email: shir0@post.bgu.ac.il\n" +
                            "qualification: qualification"},

            });
        }
        public toString(String userName, String password, String email,String qualification,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.qualification = qualification;
            this.toString = ans;
        }
        @Test
        public void toStringTest() {
            Referee referee = new Referee(userName,password,email);
            referee.setQualification(qualification);
            assertEquals(referee.toString(),toString);
        }
    }//toString







}