package BusinessService.Business_Layer.TeamManagement;
import BusinessService.Business_Layer.BudgetManagement.TeamBudget;
import BusinessService.Business_Layer.Trace.TeamPersonalPage;
import BusinessService.Business_Layer.UserManagement.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import static BusinessService.Business_Layer.BudgetManagement.Expense.PlayerSalary;
import static BusinessService.Business_Layer.BudgetManagement.Income.Gambling;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)

public class TeamTest {

    private static UnifiedSubscription createTeamOwner(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name, password, email);
        us.setNewRole(new TeamOwner());
        return us;
    }

    private static UnifiedSubscription createTeamManager(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name, password, email);
        us.setNewRole(new TeamManager());
        return us;
    }

    private static UnifiedSubscription createPlayer(String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name, password, email);
        us.setNewRole(new Player(us.getUserName()));
        return us;
    }

    private static UnifiedSubscription createCoach (String name, String password, String email){
        UnifiedSubscription us = new UnifiedSubscription(name, password, email);
        us.setNewRole(new Coach(us.getUserName()));
        return us;
    }


    /**
     * Test - T1
     */
    @RunWith(Parameterized.class)
    public static class Team1{
        //parameter
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }
        public Team1(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }
        @Test
        public void TeamTest() {
            Team team = new Team(this.name,this.field);
            HashSet<Object> assetsList = (HashSet<Object>)team.getTeamAssets();
            Iterator iter =assetsList.iterator();
            String asset = (String)iter.next();
            assertEquals(team.getName(),(this.name));
            assertEquals(asset,(this.field));
        }

    }//Team1

    /**
     * Test - T2
     */
    @RunWith(Parameterized.class)
    public static class getName {
        //parameter
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }

        public getName(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }

        @Test
        public void getNametTest() {
            Team team = new Team(this.name,this.field);
            assertEquals(team.getName(),this.name);
        }
    } // getName

    /**
     * Test - T3
     */
    @RunWith(Parameterized.class)
    public static class setBudget {
        //parameter
        public String name;
        public String field;
        TeamBudget budget;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }

        public setBudget(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
            this.budget = new TeamBudget(this.name);
        }

        @Test
        public void setBudgetTest() {
            Team team = new Team(this.name,this.field);
            team.setBudget(this.budget);
            assertEquals(team.getBudget(),this.budget);
        }
    } //setBudget

    /**
     * Test - T4     */
    @RunWith(Parameterized.class)
    public static class getStatus{
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }
        public getStatus(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }
        @Test
        public void getStatusTest() {
            Team team = new Team(this.name,this.field);
            assertEquals(team.getStatus(),1);
        }

    }//getStatus

    /**
     * Test - T5
     */
    @RunWith(Parameterized.class)
    public static class setPersonalPage{
        public String name;
        public String field;
        public TeamPersonalPage page;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }
        public setPersonalPage(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
            page = new TeamPersonalPage(this.name);
        }
        @Test
        public void setPersonalPageTest() {
            Team team = new Team(this.name,this.field);
            assertNull(team.setPersonalPage(page));
            assertEquals(team.getPersonalPage(),this.page);
            team.changeStatus(0);
            assertEquals(team.setPersonalPage(page),"The team is inactive so no activity can be performed on it");
            team.changeStatus(-1);
            assertEquals(team.setPersonalPage(page),"The team is inactive so no activity can be performed on it");
        }

    }//setPersonalPage

    /**
     * Test - T6
     */
    @RunWith(Parameterized.class)
    public static class checkIfObjectInTeam{
        public String name;
        public String field;
        public String subName;
        public String subPassword;
        public String subEmail;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","razklein@gmail.com"},
            });
        }
        public checkIfObjectInTeam(String teamName, String mainField, String name, String pass, String mail) {
            this.name=teamName;
            this.field=mainField;
            this.subName=name;
            this.subPassword=pass;
            this.subEmail=mail;
        }
        @Test
        public void checkIfObjectInTeamTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription manager = createTeamManager(this.subName+"m",this.subPassword+"m","m"+this.subEmail);
            UnifiedSubscription owner = createTeamOwner(this.subName+"m",this.subPassword+"m","m"+this.subEmail);
            team.EditTeamOwner(owner,1);
            team.EditTeamManager(manager,1);
            assertTrue(team.checkIfObjectInTeam(manager));
            assertTrue(team.checkIfObjectInTeam(owner));
            team.EditTeamOwner(owner,0);
            team.EditTeamManager(manager,0);
            assertFalse(team.checkIfObjectInTeam(manager));
            assertFalse(team.checkIfObjectInTeam(owner));
        }

    }//checkIfObjectInTeam

    /**
     * Test - T7
     */
    @RunWith(Parameterized.class)
    public static class getPlayer{
        public String name;
        public String field;
        public String subName;
        public String subPassword;
        public String subEmail;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","razklein@gmail.com"},
            });
        }
        public getPlayer(String teamName, String mainField, String name, String pass, String mail) {
            this.name=teamName;
            this.field=mainField;
            this.subName=name;
            this.subPassword=pass;
            this.subEmail=mail;
        }
        @Test
        public void getPlayerTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription player = createPlayer(this.subName,this.subPassword,this.subEmail);
            UnifiedSubscription player2 = createPlayer("a","b","c");

            team.addOrRemovePlayer(player,1);
            assertEquals(team.getPlayer(this.subName),player);
            team.addOrRemovePlayer(player,0);
            assertNull(team.getPlayer(this.subName));
            team.addOrRemovePlayer(player,1);
            team.addOrRemovePlayer(player,1);
            team.addOrRemovePlayer(player2,1);
            assertNotEquals(team.getPlayer("d"),player2);
        }
    }//getPlayer

    /**
     * Test - T8
     */
    @RunWith(Parameterized.class)
    public static class setAsset{
        public String name;
        public String field;
        public String field2;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","new"}
            });
        }
        public setAsset(String teamName, String mainField, String newField) {
            this.name=teamName;
            this.field=mainField;
            this.field2=newField;
        }
        @Test
        public void setAssetTest() {
            Team team = new Team(this.name,this.field);
            team.setAsset(this.field2);
            HashSet<Object> assets= (HashSet<Object>) team.getTeamAssets();
            Iterator iter = assets.iterator();
            assertEquals(iter.next(),this.field2);
            assertEquals(iter.next(),this.field);
            team.changeStatus(0);
            assertEquals(team.setAsset(this.field2),"The team is inactive so no activity can be performed on it");
            team.changeStatus(-1);
            assertEquals(team.setAsset(this.field2),"The team is inactive so no activity can be performed on it");
        }
    }//setAsset

    /**
     * Test - T9
     */
    @RunWith(Parameterized.class)
    public static class removeTeamAssets{
        public String name;
        public String field;
        public String field2;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","new"}
            });
        }
        public removeTeamAssets(String teamName, String mainField, String newField) {
            this.name=teamName;
            this.field=mainField;
            this.field2=newField;
        }
        @Test
        public void removeTeamAssetsTest() {
            Team team = new Team(this.name,this.field);
            team.setAsset(this.field2);
            HashSet<Object> assets=  (HashSet<Object>) team.getTeamAssets();
            team.removeTeamAssets(this.field);
            Iterator iter = assets.iterator();
            assertEquals(iter.next(),this.field2);
            assertEquals(assets.size(),1);
        }
    }//removeTeamAssets

    /**
     * Test - T10
     */
    @RunWith(Parameterized.class)
    public static class addOrRemovePlayer{
        public String name;
        public String field;
        public String subName;
        public String subPassword;
        public String subEmail;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","razklein@gmail.com"},
            });
        }
        public addOrRemovePlayer(String teamName, String mainField, String name, String pass, String mail) {
            this.name=teamName;
            this.field=mainField;
            this.subName=name;
            this.subPassword=pass;
            this.subEmail=mail;
        }
        @Test
        public void addOrRemovePlayerTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription player = createPlayer(this.subName,this.subPassword,this.subEmail);
            assertEquals(team.addOrRemovePlayer(player,1).getDescription(),"The player was successfully added to the team.");
            assertEquals(team.addOrRemovePlayer(player,1).getDescription(),"The player is already in the team.");
            assertEquals(team.addOrRemovePlayer(player,0).getDescription(),"The player was successfully removed from the team.");
            assertEquals(team.addOrRemovePlayer(player,0).getDescription(),"The player is not in the team.");
            assertEquals(team.addOrRemovePlayer(player,2).getDescription(),"The action is invalid.");
            team.changeStatus(-1);
            assertEquals(team.addOrRemovePlayer(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
            team.changeStatus(0);
            assertEquals(team.addOrRemovePlayer(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
        }
    }//addOrRemovePlayer

    /**
     * Test - T11
     */
    @RunWith(Parameterized.class)
    public static class AddOrRemoveCoach{
        public String name;
        public String field;
        public String subName;
        public String subPassword;
        public String subEmail;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","razklein@gmail.com"},
            });
        }
        public AddOrRemoveCoach(String teamName, String mainField, String name, String pass, String mail) {
            this.name=teamName;
            this.field=mainField;
            this.subName=name;
            this.subPassword=pass;
            this.subEmail=mail;
        }
        @Test
        public void AddOrRemoveCoachTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription player = createCoach(this.subName,this.subPassword,this.subEmail);
            assertEquals(team.AddOrRemoveCoach(player,1).getDescription(),"The Coach was successfully added to the team.");
            assertEquals(team.AddOrRemoveCoach(player,1).getDescription(),"The Coach is already in the team.");
            assertEquals(team.AddOrRemoveCoach(player,0).getDescription(),"The Coach was successfully removed from the team.");
            assertEquals(team.AddOrRemoveCoach(player,0).getDescription(),"The Coach is not in the team.");
            assertEquals(team.AddOrRemoveCoach(player,2).getDescription(),"The action is invalid.");
            team.changeStatus(-1);
            assertEquals(team.AddOrRemoveCoach(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
            team.changeStatus(0);
            assertEquals(team.AddOrRemoveCoach(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
        }
    }//AddOrRemoveCoach

    /**
     * Test - T12
     */
    @RunWith(Parameterized.class)
    public static class EditTeamOwner{
        public String name;
        public String field;
        public String subName;
        public String subPassword;
        public String subEmail;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","razklein@gmail.com"},
            });
        }
        public EditTeamOwner(String teamName, String mainField, String name, String pass, String mail) {
            this.name=teamName;
            this.field=mainField;
            this.subName=name;
            this.subPassword=pass;
            this.subEmail=mail;
        }
        @Test
        public void EditTeamOwnerTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription player = createTeamOwner(this.subName,this.subPassword,this.subEmail);
            assertEquals(team.EditTeamOwner(player,1).getDescription(),"The Team Owner was successfully added to the team.");
            assertEquals(team.EditTeamOwner(player,1).getDescription(),"The Team Owner is already in the team.");
            assertEquals(team.EditTeamOwner(player,0).getDescription(),"The Team Owner was successfully removed from the team.");
            assertEquals(team.EditTeamOwner(player,0).getDescription(),"The Team Owner is not in the team.");
            assertEquals(team.EditTeamOwner(player,2).getDescription(),"The action is invalid.");
            team.changeStatus(-1);
            assertEquals(team.EditTeamOwner(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
            team.changeStatus(0);
            assertEquals(team.EditTeamOwner(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
        }
    }//EditTeamOwner

    /**
     * Test - T13
     */
    @RunWith(Parameterized.class)
    public static class EditTeamManager{
        public String name;
        public String field;
        public String subName;
        public String subPassword;
        public String subEmail;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","razklein@gmail.com"},
            });
        }
        public EditTeamManager(String teamName, String mainField, String name, String pass, String mail) {
            this.name=teamName;
            this.field=mainField;
            this.subName=name;
            this.subPassword=pass;
            this.subEmail=mail;
        }
        @Test
        public void EditTeamManagerTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription player = createTeamManager(this.subName,this.subPassword,this.subEmail);
            assertEquals(team.EditTeamManager(player,1).getDescription(),"The Team Manager was successfully added to the team.");
            assertEquals(team.EditTeamManager(player,1).getDescription(),"The Team Manager is already in the team.");
            assertEquals(team.EditTeamManager(player,0).getDescription(),"The Team Manager was successfully removed from the team.");
            assertEquals(team.EditTeamManager(player,0).getDescription(),"The Team Manager is not in the team.");
            assertEquals(team.EditTeamManager(player,2).getDescription(),"The action is invalid.");
            team.changeStatus(-1);
            assertEquals(team.EditTeamManager(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
            team.changeStatus(0);
            assertEquals(team.EditTeamManager(player,1).getDescription(),"The team is inactive so no activity can be performed on it");
        }
    }//EditTeamOwner

    /**
     * Test - T14
     */
    @RunWith(Parameterized.class)
    public static class changeStatus{
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }
        public changeStatus(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }
        @Test
        public void changeStatusTest() {
            Team team = new Team(this.name,this.field);
            assertEquals(team.changeStatus(1).getDescription(),"The team is already set 1");
            assertEquals(team.changeStatus(-1).getDescription(),"The status of the team has changed successfully.");
            assertEquals(team.getStatus(),-1);
            team.changeStatus(0);
            assertEquals(team.getStatus(),0);
            team.changeStatus(1);
            assertEquals(team.getStatus(),1);
            team.changeStatus(2);
            assertEquals(team.getStatus(),2);
        }

    }//getStatus
    /**
     * Test - T15
     */
    @RunWith(Parameterized.class)
    public static class addIncome {
        //parameter
        public String name;
        public String field;
        TeamBudget budget;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }

        public addIncome(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
            this.budget = new TeamBudget(this.name);
        }

        @Test
        public void addIncomeTest() {
            Team team = new Team(this.name,this.field);
            team.setBudget(this.budget);
            team.addIncome(120,Gambling);
            assertEquals(team.getCurrentAmountInBudget(),120.0,0);
        }
    } //addIncome

    /**
     * Test - T16
     */
    @RunWith(Parameterized.class)
    public static class startNewQuarterForBudget {
        //parameter
        public String name;
        public String field;
        TeamBudget budget;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }

        public startNewQuarterForBudget(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
            this.budget = new TeamBudget(this.name);
        }

        @Test
        public void startNewQuarterForBudgetTest() {
            Team team = new Team(this.name,this.field);
            team.setBudget(this.budget);
            team.addIncome(120,Gambling);
            team.startNewQuarterForBudget();
            assertEquals(team.getCurrentAmountInBudget(),0,0);

        }
    } //startNewQuarterForBudget

    /**
     * Test - T17
     */
    @RunWith(Parameterized.class)
    public static class addExpense {
        //parameter
        public String name;
        public String field;
        TeamBudget budget;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }

        public addExpense(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
            this.budget = new TeamBudget(this.name);
        }

        @Test
        public void addExpenseTest() {
            Team team = new Team(this.name,this.field);
            team.setBudget(this.budget);
            team.addIncome(10000,Gambling);
            team.addExpense(6000, PlayerSalary);
            assertEquals(team.getCurrentAmountInBudget(),4000,0);

        }
    } //addExpense

    /**
     * Test - T18
     */
    @RunWith(Parameterized.class)
    public static class getListTeamOwner {
        //parameter
        public String name;
        public String field;
        public String name1;
        public String pass1;
        public String mail1;
        public String name2;
        public String pass2;
        public String mail2;
        public String name3;
        public String pass3;
        public String mail3;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","or@gmail.com","Dan","1432","mn@gmail.com","Mario","11132","vd@gmail.com"},
            });
        }

        public getListTeamOwner(String teamName, String mainField, String a1, String a2, String a3,String b1, String b2, String b3, String c1, String c2, String c3) {
            this.name=teamName;
            this.field=mainField;
            this.name1=a1;
            this.pass1=a2;
            this.mail1=a3;
            this.name2=b1;
            this.pass2=b2;
            this.mail2=b3;
            this.name3=c1;
            this.pass3=c2;
            this.mail3=c3;
        }

        @Test
        public void getListTeamOwnerTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription owner1 = createTeamOwner(this.name1,this.pass1,this.mail1);
            UnifiedSubscription owner2 = createTeamOwner(this.name2,this.pass2,this.mail2);
            UnifiedSubscription owner3 = createTeamOwner(this.name3,this.pass3,this.mail3);
            team.EditTeamOwner(owner1,1);
            team.EditTeamOwner(owner2,1);
            team.EditTeamOwner(owner3,1);
            HashSet<UnifiedSubscription> list = new HashSet<>();
            list.add(owner1);
            list.add(owner2);
            list.add(owner3);
            assertEquals(list.size(),team.getListTeamOwner().size());
            Iterator iter = list.iterator();
            while(iter.hasNext()){
                assertTrue(team.getListTeamOwner().contains(iter.next()));
            }
        }
    } //getListTeamOwner

    /**
     * Test - T19
     */
    @RunWith(Parameterized.class)
    public static class getListTeamManager {
        //parameter
        public String name;
        public String field;
        public String name1;
        public String pass1;
        public String mail1;
        public String name2;
        public String pass2;
        public String mail2;
        public String name3;
        public String pass3;
        public String mail3;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Oren","1234","or@gmail.com","Dan","1432","mn@gmail.com","Mario","11132","vd@gmail.com"},
            });
        }

        public getListTeamManager(String teamName, String mainField, String a1, String a2, String a3,String b1, String b2, String b3, String c1, String c2, String c3) {
            this.name=teamName;
            this.field=mainField;
            this.name1=a1;
            this.pass1=a2;
            this.mail1=a3;
            this.name2=b1;
            this.pass2=b2;
            this.mail2=b3;
            this.name3=c1;
            this.pass3=c2;
            this.mail3=c3;
        }

        @Test
        public void getListTeamOwnerTest() {
            Team team = new Team(this.name,this.field);
            UnifiedSubscription owner1 = createTeamManager(this.name1,this.pass1,this.mail1);
            UnifiedSubscription owner2 = createTeamManager(this.name2,this.pass2,this.mail2);
            UnifiedSubscription owner3 = createTeamManager(this.name3,this.pass3,this.mail3);
            team.EditTeamManager(owner1,1);
            team.EditTeamManager(owner2,1);
            team.EditTeamManager(owner3,1);
            HashSet<UnifiedSubscription> list = new HashSet<>();
            list.add(owner1);
            list.add(owner2);
            list.add(owner3);
            assertEquals(list.size(),team.getListTeamManager().size());
            Iterator iter = list.iterator();
            while(iter.hasNext()){
                assertTrue(team.getListTeamManager().contains(iter.next()));
            }
        }
    } //getListTeamManager

    /**
     * Test - T20
     */
    @RunWith(Parameterized.class)
    public static class equalsTeam {
        //parameter
        public String name1;
        public String field1;
        public String name2;
        public String field2;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz", "Blumfield", "Oren", "Teddi"}
            });
        }

        public equalsTeam(String teamName, String mainField, String teamName2, String mainField2) {
            this.name1 = teamName;
            this.field1 = mainField;
            this.name2 = teamName2;
            this.field2 = mainField2;
        }

        @Test
        public void equalsTeamTest() {
            Team team1 = new Team(this.name1, this.field1);
            Team team2 = new Team(this.name2, this.field2);
            Team team3 = new Team(this.name1, this.field1);
            UnifiedSubscription player = createPlayer(this.name1, "", "");
            Team nullTeam = null;
            assertFalse(team1.equals(nullTeam));
            assertTrue(team1.equals(team1));
            assertFalse(team1.equals(player));
            assertFalse(team1.equals(team2));
            assertTrue(team1.equals(team3));

        } //equalsTeam
    }


    /**
     * Test - T21
     */
    @RunWith(Parameterized.class)
    public static class setTeamScore {
        //parameter
        public String name;
        public String field;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield"},
                    {"","Blumfield"}, {null,"Blumfield"},
                    {"Raz",""}, {"Raz",null},
                    {"",""},{null,null}
            });
        }

        public setTeamScore(String teamName, String mainField) {
            this.name=teamName;
            this.field=mainField;
        }

        @Test
        public void setTeamScoreTest() {
            Team team = new Team(this.name,this.field);
            TeamScore score = new TeamScore(this.name);
            team.setTeamScore(score);
            assertEquals(team.getTeamScore(),score);
        }
    } //setTeamScore

    /**
     * Test - T22
     */
    @RunWith(Parameterized.class)
    public static class compareTo {
        //parameter
        public String name1;
        public String field1;
        public String name2;
        public String field2;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Raz","Blumfield","Shir","Teddi"},
            });
        }

        public compareTo(String teamName, String mainField, String t2, String f2) {
            this.name1=teamName;
            this.field1=mainField;
            this.name2=t2;
            this.field2=f2;
        }

        @Test
        public void compareToTest() {
            Team team = new Team(this.name1,this.field1);
            TeamScore score = new TeamScore(this.name1);
            Team team2 = new Team(this.name2,this.field2);
            TeamScore score2 = new TeamScore(this.name2);
            score.setPoints(3);
            score2.setPoints(2);
            team.setTeamScore(score);
            team2.setTeamScore(score2);
            assertEquals(team.compareTo(team2),-1);
            assertEquals(team2.compareTo(team),1);
        }
    } //setTeamScore


}