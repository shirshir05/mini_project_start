package Business_Layer.Business_Items.UserManagement;

import Business_Layer.Enum.Role;
import Service_Layer.Spelling;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;


@RunWith(Enclosed.class)
public class SubscriptionFactoryTest {

    /**
     * Test - SUF1
     */
    @RunWith(Parameterized.class)
    public static class Create{

        public String userName;
        public String password;
        public String email;
        public Role role;
        public Subscription ans;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            UnifiedSubscription coach = new UnifiedSubscription("coach1","123456","shir0@post.bgu.ac.il");
            coach.setNewRole(new Coach(coach.getUserName()));

            UnifiedSubscription player = new UnifiedSubscription("players","123456","shir0@post.bgu.ac.il");
            player.setNewRole(new Player(player.userName));

            UnifiedSubscription teamOwner = new UnifiedSubscription("teamowner","123456","shir0@post.bgu.ac.il");
            teamOwner.setNewRole(new TeamOwner());

            UnifiedSubscription teamManager = new UnifiedSubscription("teammanager","123456","shir0@post.bgu.ac.il");
            teamManager.setNewRole(new TeamManager());

            return Arrays.asList(new Object[][]{
                    {"","null", Role.Coach,"shir0@post.bgu.ac.il",null},
                    {"coach1","123456", Role.Coach,"shir0@post.bgu.ac.il",coach},
                    {"fan","123456", Role.Fan,"shir0@post.bgu.ac.il",new Fan("fan","123456","shir0@post.bgu.ac.il")},
                    {"guest","123456", Role.Guest,"shir0@post.bgu.ac.il",new Guest("guest","123456","shir0@post.bgu.ac.il")},
                    {"players","123456", Role.Player,"shir0@post.bgu.ac.il",player},
                    {"referee","123456", Role.Referee,"shir0@post.bgu.ac.il",new Referee("referee","123456","shir0@post.bgu.ac.il")},
                    {"systemadministrator","123456", Role.SystemAdministrator,"shir0@post.bgu.ac.il",new SystemAdministrator("systemadministrator","123456","shir0@post.bgu.ac.il")},
                    {"teammanager","123456", Role.TeamManager,"shir0@post.bgu.ac.il",teamManager},
                    {"teamowner","123456", Role.TeamOwner,"shir0@post.bgu.ac.il",teamOwner},
                    {"unionrepresentative","123456", Role.UnionRepresentative,"shir0@post.bgu.ac.il",new UnionRepresentative("unionrepresentative","123456","shir0@post.bgu.ac.il")}

            });
        }
        public Create(String userName, String password,Role role, String email,Subscription ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.role = role;
            this.ans = ans;
        }
        @Test
        public void CreateTest() {
            SubscriptionFactory factory = new SubscriptionFactory();
            assertEquals(factory.Create(userName,password,role,email),ans);
            if(!userName.equals("guest") && !userName.equals("")){
                assertEquals(Spelling.getCorrectWord(userName),userName);
            }
        }
    }//Create

}