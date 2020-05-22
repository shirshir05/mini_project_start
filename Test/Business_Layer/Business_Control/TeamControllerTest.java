package Business_Layer.Business_Control;

import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static Business_Layer.Enum.PermissionAction.*;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class TeamControllerTest {
    public static TeamController n = new TeamController();

    private static UnifiedSubscription createPlayer(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name,password,email);
        us.setNewRole(new Player(us.getUserName()));
        return us;
    }

    private static UnifiedSubscription createCoach(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name,password,email);
        us.setNewRole(new Coach(us.getUserName()));
        return us;
    }

    private static UnifiedSubscription createTeamOwner(String oUser, String s, String s1) {
        UnifiedSubscription us = new UnifiedSubscription(oUser,s,s1);
        us.setNewRole(new TeamOwner());
        return us;
    }

    private static UnifiedSubscription createTeamManager(String oUser, String s, String s1) {
        UnifiedSubscription us = new UnifiedSubscription(oUser,s,s1);
        us.setNewRole(new TeamManager());
        return us;
    }


    /**
     * Test -TC0
     */
    @RunWith(Parameterized.class)
    public static class RequestCreateTeam{
        String teamName;
        String fieldName;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Ortal","Blumfield"}
            });
        }
        public RequestCreateTeam(String name, String field) {

            teamName=name;
            fieldName=field;
        }
        @Test
        public void RequestCreateTeamTest() {
            UnionRepresentative u = new UnionRepresentative("ffe","fefe","fef");
            DataManagement.setSubscription(u);
            UnifiedSubscription t = createTeamOwner("ad","ad","ad");
            DataManagement.setCurrent(t);
            assertTrue(n.RequestCreateTeam(this.teamName,this.fieldName).isActionSuccessful());
            UnifiedSubscription p = createPlayer("ad","ad","ad");
            DataManagement.setCurrent(p);
            assertFalse(n.RequestCreateTeam(this.teamName,this.fieldName).isActionSuccessful());
            DataManagement.cleanAllData();
        }

    }//RequestCreateTeam
    /**
     ** Test - TC1
     */
    @RunWith(Parameterized.class)
    public static class CreateTeam{
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
            });
        }
        public CreateTeam(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }
        @Test
        public void CreateTeamTest() {
            UnifiedSubscription owner = createTeamOwner("ab","fs","fe");
            UnifiedSubscription player = createPlayer("zab","zfs","zfe");
            Team team = new Team(name+"t",field+"t");
            DataManagement.setCurrent(player);
            assertEquals(n.CreateTeam(name+"t",field+"t").getDescription(),"You are not allowed to perform actions on the team.");
            DataManagement.setCurrent(owner);
            DataManagement.addToListTeam(team);
            assertEquals(n.CreateTeam(name+"t",field+"t").getDescription(),"The Team already exists in the system.");
            UnionRepresentative newRep = new UnionRepresentative(name+"x",name+"x","re@gmail.com");
            DataManagement.setSubscription(newRep);
            assertEquals(n.CreateTeam(null,null).getDescription(),"One of the parameters is null");
            n.CreateTeam(name,field);
            assertTrue(DataManagement.findTeam(name)!=null);
            DataManagement.cleanAllData();
        }


    }//CreateTeam

    /**
     ** Test - TC2
     */
    @RunWith(Parameterized.class)
    public static class DeleteCreateTeamRequest{
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz"}
            });
        }
        public DeleteCreateTeamRequest(String teamName) {
            this.name=teamName;
        }
        @Test
        public void DeleteCreateTeamRequestTest() {
            UnifiedSubscription owner = createTeamOwner("c","d","d");
            UnionRepresentative u1 = new UnionRepresentative("a","a","a");
            UnionRepresentative u2 = new UnionRepresentative("b","b","b");
            UnionRepresentative u3 = new UnionRepresentative("e","e","e");
            DataManagement.setSubscription(u1);
            DataManagement.setSubscription(u2);
            DataManagement.setSubscription(u3);
            assertTrue(u1.getAlerts().size()==0 && u2.getAlerts().size()==0 && u3.getAlerts().size()==0);
            DataManagement.setCurrent(owner);
            n.RequestCreateTeam(name,"Blumfield");
            if (name==null){
                assertTrue(u1.getAlerts().size() == 0 && u2.getAlerts().size() == 0 && u3.getAlerts().size() == 0);
            }
            else {
                assertTrue(u1.getAlerts().size() == 1 && u2.getAlerts().size() == 1 && u3.getAlerts().size() == 1);
            }
            n.DeleteCreateTeamRequest(name);
            assertTrue(u1.getAlerts().size()==0 && u2.getAlerts().size()==0 && u3.getAlerts().size()==0);
            DataManagement.cleanAllData();
        }
    }//DeleteCreateTeamRequest

    /**
     ** Test - TC3
     */
    @RunWith(Parameterized.class)
    public static class ApproveCreateTeamAlert{
        public String name;
        public String repName;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","U1"}
            });
        }
        public ApproveCreateTeamAlert(String teamName, String rep) {
            this.name=teamName;
            this.repName=rep;
        }
        @Test
        public void ApproveCreateTeamAlertTest() {
            UnifiedSubscription owner = createTeamOwner("c","d","d");
            UnionRepresentative u1 = new UnionRepresentative(repName,"a","a");
            UnionRepresentative u2 = new UnionRepresentative(repName+"b","b","b");
            UnionRepresentative u3 = new UnionRepresentative(repName+"e","e","e");
            DataManagement.setSubscription(u1);
            DataManagement.setSubscription(u2);
            DataManagement.setSubscription(u3);
            DataManagement.setCurrent(owner);
            n.RequestCreateTeam(name,"Blumfield");
            n.ApproveCreateTeamAlert("teamOwner:" + DataManagement.getCurrent() + "| Team;" + name);
            Team t = DataManagement.findTeam(name);
            assertEquals(t.getStatus(),2);
            DataManagement.cleanAllData();
        }
    }//DeleteCreateTeamRequest


    /**
     ** Test - TC3.5
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
            });
        }
        public AddOrRemovePlayer(String tname, String pname, String ppass, String pmail) {
            this.teamName=tname;
            this.name=pname;
            this.password=ppass;
            this.email=pmail;
        }
        @Test
        public void AddOrRemovePlayerTest() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UnifiedSubscription player = createPlayer(name,password,email);
            UnifiedSubscription owner = createTeamOwner("zdab", "zfcs", "zdfe");
            DataManagement.setCurrent(player);
            assertEquals(n.AddOrRemovePlayer(teamName,name,1).getDescription(),"You are not allowed to perform actions on the team.");
            owner.getPermissions().add_permissions(Edit_team);
            DataManagement.setCurrent(owner);
            Team team = new Team(teamName, name);
            team.EditTeamOwner(owner,1);
            DataManagement.setSubscription(player);
            DataManagement.addToListTeam(team);
            DataManagement.setSubscription(owner);
            assertEquals(n.AddOrRemovePlayer(teamName,name,1).getDescription(),"The player was successfully added to the team.");
            assertEquals(n.AddOrRemovePlayer(teamName,"zdab",1).getDescription(),"The username is not defined as a player on the system.");
            DataManagement.cleanAllData();
        }

    }//AddOrRemovePlayer

    /**
     ** Test - TC4
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveCoach{
        public String teamName;
        public String name;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Maccabi","P1","123456","a@post.bgu.ac.il"},
            });
        }
        public AddOrRemoveCoach(String tname, String pname, String ppass, String pmail) {
            this.teamName=tname;
            this.name=pname;
            this.password=ppass;
            this.email=pmail;
        }
        @Test
        public void AddOrRemoveCoachTest() {
            try {
                Thread.sleep(1222);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UnifiedSubscription player = createCoach(name,password,email);
            UnifiedSubscription owner = createTeamOwner("zdab", "zfcs", "zdfe");
            DataManagement.setCurrent(player);
            assertEquals(n.AddOrRemoveCoach(teamName,name,1).getDescription(),"You are not allowed to perform actions on the team.");
            owner.getPermissions().add_permissions(Edit_team);
            DataManagement.setCurrent(owner);
            Team team = new Team(teamName, name);
            team.EditTeamOwner(owner,1);
            DataManagement.setSubscription(player);
            DataManagement.addToListTeam(team);
            DataManagement.setSubscription(owner);
            assertEquals(n.AddOrRemoveCoach(teamName,name,1).getDescription(),"The Coach was successfully added to the team.");
            assertEquals(n.AddOrRemoveCoach(teamName,"zdab",1).getDescription(),"The username is not defined as a Coach on the system.");
            DataManagement.cleanAllData();
        }

    }//AddOrRemoveCoach

    /**
     ** Test - TC5
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveTeamOwner{
        public String teamName;
        public String name;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Maccabi","P1","123456","a@post.bgu.ac.il"},
            });
        }
        public AddOrRemoveTeamOwner(String tname, String pname, String ppass, String pmail) {
            this.teamName=tname;
            this.name=pname;
            this.password=ppass;
            this.email=pmail;
        }
        @Test
        public void AddOrRemoveTeamOwnerTest() {
            DataManagement.cleanAllData();
            UnifiedSubscription play = createPlayer("bgdd","cs","dw");
            UnifiedSubscription player = createTeamOwner(name,password,email);
            UnifiedSubscription owner = createTeamOwner("zdab", "zfcs", "zdfe");
            DataManagement.setCurrent(player);
            assertEquals(n.AddOrRemoveTeamOwner(teamName,name,1).getDescription(),"You are not allowed to perform actions on the team.");
            owner.getPermissions().add_permissions(Edit_team);
            DataManagement.setCurrent(owner);
            Team team = new Team(teamName, name);
            team.EditTeamOwner(owner,1);
            DataManagement.setSubscription(player);
            DataManagement.addToListTeam(team);
            DataManagement.setSubscription(owner);
            assertEquals(n.AddOrRemoveTeamOwner(teamName,name,1).getDescription(),"The Team Owner was successfully added to the team.");
            assertEquals(n.AddOrRemoveTeamOwner(teamName+"x",name,1).getDescription(),"The Team does not exist in the system.");
            DataManagement.setSubscription(play);
            assertEquals(n.AddOrRemoveTeamOwner(teamName,"bgdd",1).getDescription(),"The username is not defined as a Team Owner on the system.");
            assertEquals(n.AddOrRemoveTeamOwner(teamName,name,1).getDescription(),"You are already set as a team owner.");
            // DELETE
            UnifiedSubscription owner2 = createTeamOwner("owner2", "zfcs", "zdfe");
            DataManagement.setSubscription(owner2);
            n.AddOrRemoveTeamOwner(teamName,"owner2",1);
            //assertEquals(n.AddOrRemoveTeamOwner(teamName,"owner2",0).getDescription(),"The Team Owner was successfully removed from the team.");
            DataManagement.cleanAllData();
            //

        }

    }//AddOrRemoveTeamOwner
    /**
     *Test - TC6
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveTeamManager{
        //parameter
        public String teamName;
        public String name;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Maccabi","P1","123456","a@post.bgu.ac.il"},


            });
        }
        public AddOrRemoveTeamManager(String tname, String pname, String ppass, String pmail) {
            this.teamName=tname;
            this.name=pname;
            this.password=ppass;
            this.email=pmail;
        }
        @Test
        public void AddOrRemoveTeamManagerTest() {
            UnifiedSubscription manager = createTeamManager(name,password,email);
            DataManagement.setSubscription(manager);
            UnifiedSubscription owner = createTeamOwner(name+"x",password="x",email+"x");
            DataManagement.setSubscription(owner);
            DataManagement.setCurrent(owner);
            assertEquals(n.AddOrRemoveTeamManager(teamName,name,1).getDescription(),"You are not allowed to perform actions on the team.");
            owner.getPermissions().add_permissions(Edit_team);
            Team t = new Team(teamName,"fr");
            t.EditTeamOwner(owner,1);
            DataManagement.addToListTeam(t);
            assertEquals(n.AddOrRemoveTeamManager(teamName,name,1).getDescription(),"The Team Manager was successfully added to the team.");
            assertEquals(n.AddOrRemoveTeamManager(teamName,name,1).getDescription(),"You are already set as a team Manager.");
            //assertEquals(n.AddOrRemoveTeamManager(teamName,name,0).getDescription(),"The Team Manager was successfully removed from the team.");
            assertEquals(n.AddOrRemoveTeamManager(teamName,name,1).getDescription(),"You are already set as a team Manager.");
            // new owner as curent
            DataManagement.removeSubscription(owner.getUserName());
            t.EditTeamOwner(owner,0);
            UnifiedSubscription owner2 = createTeamOwner(name+"x"+"2",password="x",email+"x");
            DataManagement.setSubscription(owner2);
            DataManagement.setCurrent(owner2);
            owner2.getPermissions().add_permissions(Edit_team);
            t.EditTeamOwner(owner2,1);
            assertEquals(n.AddOrRemoveTeamManager(teamName,name,0).getDescription(),"The Team Manager was successfully removed from the team.");
            DataManagement.cleanAllData();
        }

    }//AddOrRemoveTeamManager


    /**
     *Test - TC7
     */
    @RunWith(Parameterized.class)
    public static class CheckInputEditTeam {
        String teamname;
        String username;
        Subscription current;
        String ans;
        Team team;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            Team team = new Team("Maccabi","Beersheva");

            UnifiedSubscription teamOwner = new UnifiedSubscription("owner","123456","");
            teamOwner.setNewRole(new TeamOwner());
            teamOwner.teamOwner_setAppointedByTeamOwner(teamOwner.getUserName());
            team.EditTeamOwner(teamOwner,1);

            UnifiedSubscription anotherTeamOwner = new UnifiedSubscription("manager","123456","");
            anotherTeamOwner.setNewRole(new TeamOwner());
            anotherTeamOwner.teamOwner_setAppointedByTeamOwner(anotherTeamOwner.getUserName());

            Fan player = new Fan("player","123456","");

            SystemAdministrator sysAdmin = new SystemAdministrator("sys_admin","123456","");

            return Arrays.asList(new Object[][]{
                    {team,"","",null,"One of the parameters is empty"},
                    {team,null,null,null,"One of the parameters is empty"},
                    {team,team.getName(),player.getUserName(),player,"You are not allowed to perform actions on the team."},
                    {team,"---",teamOwner.getUserName(),teamOwner,"The Team does not exist in the system."},
                    {team,team.getName(),anotherTeamOwner.getUserName(),anotherTeamOwner,"You are not allowed to perform actions on this team."},
                    {team,team.getName(),teamOwner.getUserName(),teamOwner,null},
                    {team,team.getName(),sysAdmin.getUserName(),sysAdmin,null},
                    {team,team.getName(),"doesnt_exist",teamOwner,"The username does not exist on the system."}
            });
        }

        public CheckInputEditTeam(Team team, String name1, String name2, Subscription sub, String ans) {
            this.team = team;
            teamname = name1;
            username = name2;
            current = sub;
            this.ans = ans;
        }

        @Test
        public void CheckInputEditTeamTest() {
            if (current != null) {
                DataManagement.setSubscription(current);
                DataManagement.setCurrent(current);
                DataManagement.addToListTeam(team);
            }
            assertEquals(n.CheckInputEditTeam(teamname, username), ans);
            if(username != null && username.equals("doesnt_exist")){
                //last test
                DataManagement.cleanAllData();
            }
        }

    }//CheckInputEditTeam

    /**
     *Test - TC8
     */
    @RunWith(Parameterized.class)
    public static class ChangeStatusTeam{
        String teamName;
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz"}
            });
        }
        public ChangeStatusTeam(String name) {
            teamName=name;
        }
        @Test
        public void ChangeStatusTeamTest() {
            assertEquals(n.ChangeStatusTeam(teamName,4).getDescription(),"The action is invalid.");
            UnifiedSubscription owner = createTeamOwner("owner","cd","cd");
            owner.getPermissions().add_permissions(Edit_team);
            DataManagement.setSubscription(owner);
            DataManagement.setCurrent(owner);
            assertEquals(n.ChangeStatusTeam(teamName+"x",0).getDescription(),"The Team does not exist in the system.");
            Team team = new Team(teamName,"de");
            team.EditTeamOwner(owner,1);
            team.changeStatus(-1);
            DataManagement.addToListTeam(team);
            assertEquals(n.ChangeStatusTeam(teamName,1).getDescription(),"The team is permanently closed.");
            team.changeStatus(1);
            assertEquals(n.ChangeStatusTeam(teamName,0).getDescription(),"You are not allowed to close a team.");
            assertEquals(n.ChangeStatusTeam(teamName,-1).getDescription(),"You are not authorized to perform this action.");
            owner.getPermissions().add_permissions(Close_team);
            assertEquals(n.ChangeStatusTeam(teamName,0).getDescription(),"The status of the team has changed successfully.");
            assertEquals(n.ChangeStatusTeam(teamName,1).getDescription(),"The status of the team has changed successfully.");
            SystemAdministrator sys = new SystemAdministrator("sys","de","de");
            DataManagement.setSubscription(sys);
            DataManagement.setCurrent(sys);
            team.changeStatus(1);
            assertEquals(n.ChangeStatusTeam(teamName,-1).getDescription(),"The status of the team has changed successfully.");
            DataManagement.cleanAllData();
        }

    }//ChangeStatusTeam

    /**
     *Test - TC9
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveTeamsAssets{
        String teamName;
        String teamAasset;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","field3"}
            });
        }
        public AddOrRemoveTeamsAssets(String name, String asset) {
            teamName=name;
            teamAasset=asset;
        }
        @Test
        public void AddOrRemoveTeamsAssetsTest() {
            UnifiedSubscription owner = createTeamOwner("owner","cd","cd");
            owner.getPermissions().add_permissions(Edit_team);
            DataManagement.setSubscription(owner);
            DataManagement.setCurrent(owner);
            Team team = new Team(teamName,"de");
            team.EditTeamOwner(owner,1);
            DataManagement.addToListTeam(team);
            assertEquals(n.AddOrRemoveTeamsAssets(teamName,teamAasset,1).getDescription(),"The asset was added to the team");
            assertEquals(n.AddOrRemoveTeamsAssets(teamName,teamAasset,0).getDescription(),"The asset was deleted from the team");
            assertEquals(n.AddOrRemoveTeamsAssets(teamName,teamAasset+"x",0).getDescription(),"The team does not contain this asset");
            assertEquals(n.AddOrRemoveTeamsAssets(teamName+"y",teamAasset+"x",0).getDescription(),"There is no such team in the system");
            assertEquals(n.AddOrRemoveTeamsAssets(teamName,null,0).getDescription(),"This asset is null");
            assertEquals(n.AddOrRemoveTeamsAssets(teamName,teamAasset,3).getDescription(),"Choose 1 or 0 only");
            UnifiedSubscription p = createPlayer("123","123","123");
            DataManagement.setSubscription(p);
            DataManagement.setCurrent(p);
            assertEquals(n.AddOrRemoveTeamsAssets(teamName,teamAasset,0).getDescription(),"You are not a team owner");
            DataManagement.cleanAllData();
        }

    }//AddOrRemoveTeamsAssets













}