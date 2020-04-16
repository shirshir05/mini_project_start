package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Game.Game;
import BusniesServic.Business_Layer.Game.League;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Coach;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Business_Layer.UserManagement.SystemAdministrator;
import BusniesServic.Business_Layer.UserManagement.UnionRepresentative;
import BusniesServic.Enum.Role;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class DataManagementTest {



    /**
     * Test - DM3
     */
    @RunWith(Parameterized.class)
    public static class containSubscription{

        public String userName;
        public String password;
        public String email;
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"shir","123456","shir0@post.bgu.ac.il"}
            });
        }

        public  containSubscription(String userName, String password, String email){
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void containSubscriptionTest() {
            Subscription sub = new Coach(userName,password,email);
            DataManagement.setSubscription(sub);
            assertNotNull(DataManagement.containSubscription("shir"));
            assertNull(DataManagement.containSubscription("dana"));

        }

    }//containSubscription


    /**
     * Test - DM4
     */
    @RunWith(Parameterized.class)
    public static class isInEnum{
        //parameter
        String ans;
        String role;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach","true"},{"shir","false"},{null,"false"},{"","false"}


            });
        }
        public isInEnum(String role, String ans ){
            this.ans = ans;
            this.role =role;
        }
        @Test
        public void isInEnumTest() {
            if(ans.equals("true")){
                assertTrue(DataManagement.isInEnum(role));
            }else{
                assertFalse(DataManagement.isInEnum(role));

            }

        }

    }//isInEnum


    /**
     * Test - DM5
     */
    @RunWith(Parameterized.class)
    public static class returnEnum{
        //parameter
        String name;
        Role role;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null,null},
                    {"Referee", Role.Referee},
                    {"SystemAdministrator",Role.SystemAdministrator},
                    {"TeamManager", Role.TeamManager},
                    {"TeamOwner",Role.TeamOwner},
                    {"UnionRepresentative",Role.UnionRepresentative},
                    {"shir",null},
                    {"Coach", Role.Coach},
                    {"Fan",Role.Fan},
                    {"Players", Role.Player},
                    {"Guest",Role.Guest}

            });
        }
        public returnEnum( String name,Role role) {
            //parameter
            this.name = name;
            this.role = role;
        }
        @Test
        public void returnEnumTest() {
           assertEquals(DataManagement.returnEnum(name),role);
        }

    }//returnEnum



    /**
     * Test - DM6
     */
    @RunWith(Parameterized.class)
    public static class findTeam{
        //parameter
        String name;
        String filed;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Barcelona", "Camp Nou"}


            });
        }
        public findTeam(String name, String filed) {
            //parameter
            this.name = name;
            this.filed= filed;
        }
        @Test
        public void findTeamTest() {
            Team team1 = new Team(name,filed);
            Team team2 = new Team("Real Madrid", "Bernabeo");
            DataManagement.addToListTeam(team1);
            assertEquals(DataManagement.findTeam("Barcelona"),team1);
            assertNull(DataManagement.findTeam("Real Madrid"));

        }

    }//findTeam

    /**
     * Test - DM6
     */
    @RunWith(Parameterized.class)
    public static class addGame{
        //parameter
        String name;
        String filed;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Barcelona", "Camp Nou"}


            });
        }
        public addGame(String name, String filed) {
            //parameter
            this.name = name;
            this.filed= filed;
        }
        @Test
        public void addGameTest() {
            LocalDate date = LocalDate.of(1992, 11, 14);
            Team team1 = new Team(name, filed);
            Team team2 = new Team("Real Madrid", "Bernabeo");
            Game game = new Game("Bernabeo11", date, team1, team2);
            DataManagement.addGame(game);
            assertEquals(DataManagement.getGame(2),game);
        }

    }//addGame


    /**
     * Test - DM7
     */
    @RunWith(Parameterized.class)
    public static class getGame{
        //parameter
        String name;
        String filed;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Barcelona", "Camp Nou"}
            });
        }

        public getGame(String name, String filed) {
            this.name = name;
            this.filed= filed;
        }
        @Test
        public void getGameTest() {
            LocalDate date = LocalDate.of(1992, 11, 14);
            Team team1 = new Team(name, filed);
            Team team2 = new Team("Real Madrid", "Bernabeo");
            Game game = new Game("Bernabeo", date, team1, team2);
            DataManagement.addGame(game);
            assertEquals(DataManagement.getGame(1),game);
            assertNull(DataManagement.getGame(5));


        }

    }//getGame


    /**
     * Test - DM8
     */
    @RunWith(Parameterized.class)
    public static class findLeague{
        //parameter
        String name;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"shir11"}
            });
        }
        public findLeague(String name) {
            this.name = name;
            //parameter
        }
        @Test
        public void findLeagueTest() {
            League l = new League(name);
            DataManagement.addToListLeague(new League("roi"));
            DataManagement.addToListLeague(l);
            assertEquals(DataManagement.findLeague("shir11"),l);
            assertNull(DataManagement.findLeague("s"));

        }

    }//findLeague

    /**
     * Test - DM10
     */
    @RunWith(Parameterized.class)
    public static class getUnionRepresentatives{
        //parameter
        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public getUnionRepresentatives(String userName, String password, String email) {
            //parameter
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void getUnionRepresentativesTest() {
            Subscription sub = new UnionRepresentative(userName,password,email);
            DataManagement.setSubscription(sub);
            DataManagement.setSubscription(new Coach("shir",password,email));
            DataManagement.getUnionRepresentatives().contains(sub);

        }

    }//getUnionRepresentatives


    /**
     * Test - DM11
     */
    @RunWith(Parameterized.class)
    public static class setSubscription{
        //parameter
        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t11","123456","shir0@post.bgu.ac.il"}


            });
        }
        public setSubscription(String userName, String password, String email) {
            //parameter
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void setSubscriptionTest() {
            Subscription sub = new UnionRepresentative(userName,password,email);
            DataManagement.setSubscription(sub);
            DataManagement.setSubscription(new Coach("shir",password,email));
            assertEquals(DataManagement.containSubscription(userName),sub);

        }

    }//setSubscription



    /**
     * Test - DM12
     */
    @RunWith(Parameterized.class)
    public static class removeSubscription{
        //parameter
        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t111","123456","shir0@post.bgu.ac.il"}
            });
        }
        public removeSubscription(String userName, String password, String email) {
            //parameter
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void removeSubscriptionTest() {
            Subscription sub = new UnionRepresentative(userName,password,email);
            DataManagement.setSubscription(sub);
            DataManagement.setSubscription(new Coach("shir",password,email));
            assertEquals(DataManagement.containSubscription(userName),sub);
            DataManagement.removeSubscription(userName);
            assertNull(DataManagement.containSubscription(userName));
        }

    }//removeSubscription



    /**
     * Test - DM13
     */
    @RunWith(Parameterized.class)
    public static class setCurrent{
        //parameter
        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public setCurrent(String userName, String password, String email) {
            //parameter
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void setCurrentTest() {
            Subscription sub = new UnionRepresentative(userName,password,email);
            DataManagement.setCurrent(sub);
            assertEquals(DataManagement.getCurrent(),sub);

        }

    }//setCurrent



    /**
     * Test - DM14
     */
    @RunWith(Parameterized.class)
    public static class getCurrent{
        //parameter
        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public getCurrent(String userName, String password, String email) {
            //parameter
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void getCurrentTest() {
            Subscription sub = new UnionRepresentative(userName,password,email);
            assertNull(DataManagement.getCurrent());
            DataManagement.setCurrent(sub);
            assertEquals(DataManagement.getCurrent(),sub);

        }

    }//getCurrent




    /**
     * Test - DM15
     */
    @RunWith(Parameterized.class)
    public static class addToListTeam{
        //parameter
        String name;
        String filed;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Barcelona", "Camp Nou"}


            });
        }
        public addToListTeam(String name, String filed) {
            //parameter
            this.name = name;
            this.filed= filed;
        }
        @Test
        public void addToListTeamTest() {
            Team team1 = new Team(name,filed);
            SystemAdministrator sub = new SystemAdministrator("t1","123456","shir0@post.bgu.ac.il");
            assertEquals(team1.countObservers(),0);
            DataManagement.setSubscription(sub);
            DataManagement.addToListTeam(team1);
            assertEquals(DataManagement.findTeam(name),team1);
            assertEquals(team1.countObservers(),1);


        }

    }//addToListTeam



    /**
     * Test - DM16
     */
    @RunWith(Parameterized.class)
    public static class getListTeam{
        String name;
        String filed;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Barcelona", "Camp Nou"}


            });
        }
        public getListTeam(String name, String filed) {
            //parameter
            this.name = name;
            this.filed= filed;
        }
        @Test
        public void getListTeamTest() {
            Team team1 = new Team(name,filed);
            DataManagement.addToListTeam(team1);
            assertTrue(DataManagement.getListTeam().contains(team1));
        }

    }//getListTeam



    /**
     * Test - DM17
     */
    @RunWith(Parameterized.class)
    public static class addToListLeague{
        String name;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"shir"}
            });
        }
        public addToListLeague(String name) {
            this.name = name;
            //parameter
        }
        @Test
        public void addToListLeagueTest() {
            League l = new League(name);
            DataManagement.addToListLeague(l);
            DataManagement.getListLeague().contains(l);

        }

    }//addToListLeague


    /**
     * Test - DM18
     */
    @RunWith(Parameterized.class)
    public static class getListLeague{
        String name;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"shir"}
            });
        }
        public getListLeague(String name) {
            this.name = name;
            //parameter
        }
        @Test
        public void getListLeagueTest() {
            League l = new League(name);
            DataManagement.addToListLeague(l);
            DataManagement.getListLeague().contains(l);

        }

    }//getListLeague



    /**
     * Test - DM19
     */
    @RunWith(Parameterized.class)
    public static class getSystemAdministratorsList{
        //parameter
        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public getSystemAdministratorsList(String userName, String password, String email) {
            //parameter
            this.userName = userName;
            this.password = password;
            this.email = email;
        }
        @Test
        public void getSystemAdministratorsListTest() {
            Subscription sub = new SystemAdministrator(userName,password,email);
            DataManagement.setSubscription(sub);
            DataManagement.getSystemAdministratorsList().contains(sub);

        }

    }//getSystemAdministratorsList


    /**
     * Test - DM21
     */
    @RunWith(Parameterized.class)
    public static class checkEmail{
        //parameter
        String email;
        String ans;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null,"false"},
                    {"","false"},
                    {"s","false"},
                    {"shir0post.bgu.ac.il","false"},
                    {"shir0@post.bgu.ac.il","true"}
            });
        }
        public checkEmail(String email, String ans) {
            //parameter
            this.email = email;
            this.ans= ans;
        }

        @Test
        public void checkEmailTest() {
            if(ans.equals("false")){
                assertFalse(DataManagement.checkEmail(email));
            }else{
                assertTrue(DataManagement.checkEmail(email));

            }
        }

    }//checkEmail



    /**
     * Test -  DM22
     */
    @RunWith(Parameterized.class)
    public static class InputTest{
        //parameter
        String user;
        String password;
        String ans;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"","","The input is empty."},
                    {"s","","The input is empty."},
                    {null,"","The input is empty."},
                    {"s",null,"The input is empty."},
                    {"s","1","The password must contain at least 5 digits."},
                    {"shir","12345","Please select another username because this username exists in the system."},
                    {"1","12345",null},




            });
        }
        public InputTest(String user,String password,String ans) {
            //parameter
            this.user = user;
            this.password = password;
            this.ans = ans;
        }
        @Test
        public void InputTestTest() {
            DataManagement.setSubscription(new Coach("shir","123456","shir0post.bgu.ac.il"));
            assertEquals(DataManagement.InputTest(user,password),ans);
        }


    }//InputTest






}