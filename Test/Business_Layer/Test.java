package Business_Layer;

import Business_Layer.Business_Control.LogAndExitController;
import Business_Layer.Business_Control.TeamController;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class Test {



    /**
     * Test - UC Login and Create Team
     */
    @RunWith(Parameterized.class)
    public static class LoginCreateTeam{

        public String userName;
        public String password;
        public String email;
        public String role;
        public String ans;
        public int test;
        public String team;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0,"CoachAndTeamOwner","123456","Coach","shir0@post.bgu.ac.il","Subscription successfully added!","team1"},
                    {1, "CoachAndTeamOwner","123456","TeamOwner","shir0@post.bgu.ac.il","The role TeamOwner was added successfully to your account","team1"},
                    {2, "TeamOwner","123456","TeamOwner","shir0@post.bgu.ac.il","Subscription successfully added!","team2"},
            });
        }
        public LoginCreateTeam(int test , String userName, String password, String role, String email, String ans, String team) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.role = role;
            this.ans = ans;
            this.test = test;
            this.team = team;
        }
        @org.junit.Test
        public void LoginCreateTeamTest() {
            LogAndExitController controller = new LogAndExitController();
           controller.Registration("UnionRepresentative","12345","UnionRepresentative","shirshir05@walla.co.il");
            controller.Registration("player","12345","Player","shirshir05@walla.co.il");
            controller.Registration("SystemAdministrator","12345","SystemAdministrator","shirshir05@walla.co.il");


            if(test ==0 || test  ==2){
                assertEquals(controller.Registration(userName,password, role, email).getDescription(),ans);
            }else if(test == 1){
                assertEquals(controller.addRoleToUser(role, password).getDescription(),ans);
               controller.Login(userName, password);
            }
            TeamController teamController = new TeamController();
            if(test ==0){
                assertEquals(teamController.RequestCreateTeam(team,"filed").getDescription(),"You are not allowed to create a Team.");
            }else if(test ==1 || test==2){
                assertEquals(teamController.RequestCreateTeam(team,"filed").getDescription(),"The team wait for approve union representative.");
                assertEquals((teamController.ChangeStatusTeam(team,2).getDescription()),"The action is invalid.");
                assertEquals((teamController.AddOrRemovePlayer(team,"player", 1).getDescription()),"You are not allowed to perform actions on the team.");
                controller.Exit(userName);
                controller.Login("UnionRepresentative","12345");
                assertEquals(teamController.ApproveCreateTeamAlert("teamOwner:" +userName + "| Team;" +team).getDescription(),"Team status successfully changed to 1.");
                assertEquals((teamController.AddOrRemovePlayer(team,"player", 1).getDescription()),"You are not allowed to perform actions on the team.");
                controller.Exit("UnionRepresentative");
                controller.Login("SystemAdministrator","12345");
                assertEquals(teamController.AddOrRemoveTeamOwner(team,userName, 1).getDescription(),"The Team Owner was successfully added to the team.");
                controller.Exit("UnionRepresentative");
                controller.Login(userName,password);
                assertEquals((teamController.AddOrRemovePlayer(team,"player", 1).getDescription()),"The player was successfully added to the team.");
            }
        }
    }


}


