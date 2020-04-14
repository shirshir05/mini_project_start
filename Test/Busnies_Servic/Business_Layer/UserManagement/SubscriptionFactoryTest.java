package Busnies_Servic.Business_Layer.UserManagement;

import Busnies_Servic.Role;
import Presentation_Layer.Spelling;
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
            return Arrays.asList(new Object[][]{
                    {"","null", Role.Coach,"shir0@post.bgu.ac.il",null},
                    {"coach1","123456", Role.Coach,"shir0@post.bgu.ac.il",new Coach("coach1","123456","shir0@post.bgu.ac.il")},
                    {"fan","123456", Role.Fan,"shir0@post.bgu.ac.il",new Fan("fan","123456","shir0@post.bgu.ac.il")},
                    {"guest","123456", Role.Guest,"shir0@post.bgu.ac.il",new Guest("guest","123456","shir0@post.bgu.ac.il")},
                    {"players","123456", Role.Players,"shir0@post.bgu.ac.il",new Player("players","123456","shir0@post.bgu.ac.il")},
                    {"referee","123456", Role.Referee,"shir0@post.bgu.ac.il",new Referee("referee","123456","shir0@post.bgu.ac.il")},
                    {"systemadministrator","123456", Role.SystemAdministrator,"shir0@post.bgu.ac.il",new SystemAdministrator("systemadministrator","123456","shir0@post.bgu.ac.il")},
                    {"teammanager","123456", Role.TeamManager,"shir0@post.bgu.ac.il",new TeamManager("teammanager","123456","shir0@post.bgu.ac.il")},
                    {"teamowner","123456", Role.TeamOwner,"shir0@post.bgu.ac.il",new TeamOwner("teamowner","123456","shir0@post.bgu.ac.il")},
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