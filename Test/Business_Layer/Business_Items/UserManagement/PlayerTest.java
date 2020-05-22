package Business_Layer.Business_Items.UserManagement;
import Business_Layer.Business_Items.Game.Game;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.Trace.PlayerPersonalPage;
import Business_Layer.Enum.PermissionAction;
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
public class PlayerTest {

    private static UnifiedSubscription createPlayer(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name, password, email);
        us.setNewRole(new Player(us.userName));
        return us;
    }

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
            UnifiedSubscription player = createPlayer(userName,password,email);
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
            UnifiedSubscription player = createPlayer(userName,password,email);
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
            UnifiedSubscription player = createPlayer(userName,password,email);
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

    }//getBirthday


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
            UnifiedSubscription player = createPlayer(userName,password,email);
            player.setPlayerPersonalPage(null);
            assertNull(player.getPlayerPersonalPage());
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
            UnifiedSubscription player = createPlayer(userName,password,email);
            player.setPlayerPersonalPage(new PlayerPersonalPage(userName));
            assertNotNull(player.getPlayerPersonalPage());
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
            UnifiedSubscription player = createPlayer(userName,password,email);
            assertEquals(player.alerts.size(),0);
            player.update(new Game("s",LocalDate.of(1995,8,18),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(player.alerts.size(),1);
            player.update(new Game("s",LocalDate.of(1995,8,18),new Team("1","r"),new Team("2","r")),"The ");
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
                    {"P1","123456","shir0@post.bgu.ac.il","position", LocalDate.of(1995,2,3),
                            "name: null\n" +
                                    "email: shir0@post.bgu.ac.il\n" +
                                    "Player: \n" +
                            "position: position\n" +
                            "birthday: 1995-02-03"+"\n"},

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
            UnifiedSubscription player = createPlayer(userName,password,email);
            player.setPosition(position);
            assertEquals(player.toString(),toString);
        }
    }//toString





}