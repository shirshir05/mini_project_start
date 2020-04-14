package Busnies_Servic.Business_Layer.UserManagement;
import Busnies_Servic.Business_Layer.Game.Game;
import Busnies_Servic.Business_Layer.TeamManagement.Team;
import Busnies_Servic.Business_Layer.Trace.PlayerPersonalPage;
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
public class PlayerTest {

    /**
     * Test - P1
     */
    @RunWith(Parameterized.class)
    public static class Player1{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il"},
                    {"P2","123456","shir0@post.bgu.ac.il"},
                    {"P3","123456","shir0@post.bgu.ac.il"},
                    {"P4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public Player1(String userName, String password, String email) {
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
        public void Player1Test() {
            Player player = new Player(userName,password,email);
            assertEquals(player.getUserName(),(userName));
            assertEquals(player.getPassword(),getHash(password));
            assertEquals(player.getEmail(),(email));
            assertEquals(player.getPermissions().check_permissions(PermissionAction.personal_page),true);
        }
    }//Player1


    /**
     * Test - P2
     */
    @RunWith(Parameterized.class)
    public static class getPosition{

        public String userName;
        public String password;
        public String email;
        public String position;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il","1"},

            });
        }
        public getPosition(String userName, String password, String email,String position) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.position = position;
        }

        @Test
        public void getPositionTest() {
            Player player = new Player(userName,password,email);
            player.setPosition(position);
            assertEquals(player.getPosition(),position);
        }
    }//getPosition


    /**
     * Test - P3
     */
    @RunWith(Parameterized.class)
    public static class setPosition{

        public String userName;
        public String password;
        public String email;
        public String position;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il","1"},

            });
        }
        public setPosition(String userName, String password, String email,String position) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.position = position;
        }

        @Test
        public void setPositionTest() {
            Player player = new Player(userName,password,email);
            player.setPosition(position);
            assertEquals(player.getPosition(),position);
        }
    }//setPosition



    /**
     * Test - P4
     */
    @RunWith(Parameterized.class)
    public static class getBirthday{

        public String userName;
        public String password;
        public String email;
        public LocalDate date;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il",LocalDate.of(1995,1,1)},

            });
        }
        public getBirthday(String userName, String password, String email,LocalDate d) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.date = d;
        }

        @Test
        public void getBirthdayTest() {
            Player player = new Player(userName,password,email);
            player.setBirthday(date);
            assertNotNull(player.getBirthday());
        }
    }//getBirthday

    /**
     * Test - P5
     */
    @RunWith(Parameterized.class)
    public static class setBirthday{

        public String userName;
        public String password;
        public String email;
        public LocalDate date;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il",LocalDate.of(1995,2,3)},

            });
        }
        public setBirthday(String userName, String password, String email,LocalDate d) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.date = d;
        }

        @Test
        public void setBirthdayTest() {
            Player player = new Player(userName,password,email);
            player.setBirthday(date);
            assertEquals(player.getBirthday(),LocalDate.of(1995,2,3));
        }
    }//setBirthday


    /**
     * Test - P6
     */
    @RunWith(Parameterized.class)
    public static class setPersonalPage{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il"},

            });
        }
        public setPersonalPage(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;

        }

        @Test
        public void setPersonalPageTest() {
            Player player = new Player(userName,password,email);
            player.setPersonalPage(null);
            assertNull(player.getPersonalPage());
        }
    }//setPersonalPage



    /**
     * Test - P7
     */
    @RunWith(Parameterized.class)
    public static class getPersonalPage{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il"},

            });
        }
        public getPersonalPage(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;

        }

        @Test
        public void getPersonalPageTest() {
            Player player = new Player(userName,password,email);
            player.setPersonalPage(new PlayerPersonalPage(userName));
            assertNotNull(player.getPersonalPage());
        }
    }//getPersonalPage



    /**
     * Test - P9
     */
    @RunWith(Parameterized.class)
    public static class update{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il"}


            });
        }
        public update(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void updateTest() {
            Player player = new Player(userName,password,email);
            assertEquals(player.alerts.size(),0);
            player.update(new Game("s",LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(player.alerts.size(),1);
            player.update(new Game("s",LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"The ");
            assertEquals(player.alerts.size(),2);
        }
    }//update




    /**
     * Test - P10
     */
    @RunWith(Parameterized.class)
    public static class toString{

        public String userName;
        public String password;
        public String email;
        public String position;
        public LocalDate birthday;
        public String toString;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"P1","123456","shir0@post.bgu.ac.il","position", LocalDate.of(1995,2,3),"Player: \n" +
                            "name: null\n" +
                            "email: shir0@post.bgu.ac.il\n" +
                            "position: position\n" +
                            "birthday: 1995-02-03"},

            });
        }
        public toString(String userName, String password, String email,String position,LocalDate birthday,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.position = position;
            this.birthday = birthday;
            this.toString = ans;
        }

        @Test
        public void toStringTest() {
            Player player = new Player(userName,password,email);
            player.setPosition(position);
            player.setBirthday(birthday);
            assertEquals(player.toString(),toString);
        }
    }//toString





}