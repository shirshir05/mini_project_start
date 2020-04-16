package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.PermissionAction;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Collection;

import static BusniesServic.Enum.PermissionAction.Edit_team;
import static BusniesServic.Enum.PermissionAction.Team_financial;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TeamControllerTest {
    public static TeamController n = new TeamController();

    /**
     * Test -TC1
     */
    @RunWith(Parameterized.class)
    public static class RequestCreateTeam{
        String teamName;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz"},{""},{null}
            });
        }
        public RequestCreateTeam(String name) {
            teamName=name;
        }
        @Test
        public void RequestCreateTeamTest() {
            assertTrue(n.RequestCreateTeam(this.teamName));
        }


    }//RequestCreateTeam
    /**
     ** Test - TC2
     */
    @RunWith(Parameterized.class)
    public static class CreateTeam{
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {null,"Blumfield"},
                    {"Raz2",""}, {"Raz3",null},
                    {"",""}
            });
        }
        public CreateTeam(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }
        @Test
        public void CreateTeamTest() {
            TeamOwner owner = new TeamOwner("ab","fs","fe");
            Player player = new Player("zab","zfs","zfe");
            Team team = new Team(name+"t",field+"t");
            DataManagement.setCurrent(player);
            assertEquals(n.CreateTeam(name+"t",field+"t").getDescription(),"You are not allowed to perform actions on the group.");
            DataManagement.setCurrent(owner);
            DataManagement.addToListTeam(team);
            assertEquals(n.CreateTeam(name+"t",field+"t").getDescription(),"The Team already exists in the system.");
            UnionRepresentative newRep = new UnionRepresentative(name+"x",name+"x","re@gmail.com");
            DataManagement.setSubscription(newRep);
            assertEquals(n.CreateTeam(name+"xyz",field+"xyz").getDescription(),"The Team Owner was successfully added to the team.");
        }


    }//CreateTeam

    /**
     ** Test - TC3
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemovePlayer{
        public String teamName;
        public String name;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Maccabi","P1","123456","a@post.bgu.ac.il"},
                    {"Maccabi","","123456","s@post.bgu.ac.il"},
                    {"Maccabi","P3","","d@post.bgu.ac.il"},
                    {"Maccabi","P4", "123456",""},
                    {"Maccabi",null,"123456","s@post.bgu.ac.il"},
                    {"Maccabi","P3",null,"d@post.bgu.ac.il"},
                    {"Maccabi","P4", "123456",null}
            });
        }
        public AddOrRemovePlayer(String pname, String ppass, String pmail) {
            this.name=pname;
            this.password=ppass;
            this.email=pmail;
        }
        @Test
        public void AddOrRemovePlayerTest() {
        }

    }//AddOrRemovePlayer

    /**
     ** Test - TC4
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveCoach{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public AddOrRemoveCoach() {
            //parameter
        }
        @Test
        public void AddOrRemoveCoachTest() {

        }

    }//AddOrRemoveCoach

    /**
     ** Test - TC5
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveTeamOwner{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public AddOrRemoveTeamOwner() {
            //parameter
        }
        @Test
        public void AddOrRemoveTeamOwnerTest() {

        }

    }//AddOrRemoveTeamOwner
    /**
     *Test - TC6
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveTeamManager{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public AddOrRemoveTeamManager() {
            //parameter
        }
        @Test
        public void AddOrRemoveTeamManagerTest() {

        }

    }//AddOrRemoveTeamManager


    /**
     *Test - TC7
     */
    @RunWith(Parameterized.class)
    public static class CheckInputEditTeam{
        String teamname;
        String username;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Maccabi","Raz"},{"","Raz1"},{"Maccabi2",""},{null,"Raz3"},{"Maccabi4",null},{null,null}
            });
        }
        public CheckInputEditTeam(String name1, String name2) {
            teamname=name1;
            username=name2;
        }
        @Test
        public void CheckInputEditTeamTest() {
            // first if - one of the parameters is null
            if (teamname==null||username==null){
                assertEquals(n.CheckInputEditTeam(teamname,username),"One of the parameters is null");
                assertEquals(n.CheckInputEditTeam(null,null),"One of the parameters is null");
                assertEquals(n.CheckInputEditTeam("abc",null),"One of the parameters is null");
            }
            else {
                // second if - don't have permissions to edit
                Player player = new Player("zab", "zfs", "zfe");
                DataManagement.setCurrent(player);
                assertEquals(n.CheckInputEditTeam(teamname, username), "You are not allowed to perform actions on the group.");
                // fourth if - the team is not in the system
                player.getPermissions().add_permissions(Edit_team);
                Team team = new Team(teamname, username);
                TeamOwner owner = new TeamOwner("zdab", "zfcs", "zdfe");
                team.EditTeamOwner(owner, 1);
                DataManagement.addToListTeam(team);
                SystemAdministrator sysAdmin = new SystemAdministrator("zabc", "zfsc", "zfec");
                assertEquals(n.CheckInputEditTeam(teamname, username), "You are not allowed to perform actions in this group.");
                // last if - the team is not in the system
                owner.getPermissions().add_permissions(Edit_team);
                DataManagement.setCurrent(owner);
                DataManagement.setSubscription(player);
                assertEquals(n.CheckInputEditTeam(teamname, username), "The username does not exist on the system.");
                assertEquals(n.CheckInputEditTeam(teamname, "zab"), null);
                // fourth if - the team does not exist
                assertEquals(n.CheckInputEditTeam("aac","Cdds"),"The Team does not exist in the system.");
            }
        }

    }//CheckInputEditTeam

    /**
     *Test - TC8
     */
    @RunWith(Parameterized.class)
    public static class ChangeStatusTeam{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public ChangeStatusTeam() {
            //parameter
        }
        @Test
        public void ChangeStatusTeamTest() {

        }

    }//ChangeStatusTeam












}