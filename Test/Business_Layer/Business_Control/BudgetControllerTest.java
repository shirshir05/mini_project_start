package Business_Layer.Business_Control;

import Business_Layer.Business_Items.BudgetManagement.BudgetRegulations;
import Business_Layer.Business_Items.BudgetManagement.Expense;
import Business_Layer.Business_Items.BudgetManagement.Income;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.*;
import Business_Layer.Enum.PermissionAction;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@RunWith(Enclosed.class)
public class BudgetControllerTest {

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
     * Test - BC1
     */
    @RunWith(Parameterized.class)
    public static class BudgetSet{

        double max;
        double min;
        boolean correct;
        Subscription subU = new UnionRepresentative("uUser","123456","useru@gmail.com");
        Subscription subF = new Fan("fUser","123456","userf@gmail.com");


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {4000,-300,false},{-5,-300,false},{-1000,0,false},{0,0,false},
                    {5500,10000,true},{6000,7000,true},{7000,100000,true},{8000,9000,true}


            });
        }
        public BudgetSet(double min, double max, boolean correct) {
            //parameter
            this.max = max;
            this.min = min;
            this.correct = correct;

        }
        @Test
        public void BudgetControllerTest1() {
            DataManagement.setCurrent(subU);
            assertEquals(BudgetController.setMinPlayerSalary(min).isActionSuccessful(),correct);
            assertEquals(BudgetController.setMaxPlayerSalary(max).isActionSuccessful(),correct);
        }

        @Test
        public void BudgetControllerTest2() {
            DataManagement.setCurrent(subU);
            assertEquals(BudgetController.setMinCoachSalary(min).isActionSuccessful(),correct);
            assertEquals(BudgetController.setMaxCoachSalary(max).isActionSuccessful(),correct);
        }

        @Test
        public void BudgetControllerTest3() {
            DataManagement.setCurrent(subU);
            assertEquals(BudgetController.setMinRefereeSalary(min).isActionSuccessful(),correct);
            assertEquals(BudgetController.setMaxRefereeSalary(max).isActionSuccessful(),correct);
        }

        @Test
        public void BudgetControllerTest4() {
            DataManagement.setCurrent(subU);
            assertEquals(BudgetController.setMinUnionMemberSalary(min).isActionSuccessful(),correct);
            assertEquals(BudgetController.setMaxUnionMemberSalary(max).isActionSuccessful(),correct);
        }

        @Test
        public void BudgetControllerTest5() {
            DataManagement.setCurrent(subF);
            assertFalse(BudgetController.setMinPlayerSalary(min).isActionSuccessful());
            assertFalse(BudgetController.setMaxPlayerSalary(max).isActionSuccessful());
            assertFalse(BudgetController.setMinCoachSalary(min).isActionSuccessful());
            assertFalse(BudgetController.setMaxCoachSalary(max).isActionSuccessful());
            assertFalse(BudgetController.setMinRefereeSalary(min).isActionSuccessful());
            assertFalse(BudgetController.setMaxRefereeSalary(max).isActionSuccessful());
            assertFalse(BudgetController.setMinUnionMemberSalary(min).isActionSuccessful());
            assertFalse(BudgetController.setMaxUnionMemberSalary(max).isActionSuccessful());

            assertFalse(BudgetController.setMaxMaintenanceExpense(max).isActionSuccessful());
            assertFalse(BudgetController.setMaxUniformExpense(max).isActionSuccessful());
            assertFalse(BudgetController.setMaxAdvertisementExpense(max).isActionSuccessful());
            assertFalse(BudgetController.setMaxOtherExpense(max).isActionSuccessful());

        }

        @Test
        public void BudgetControllerTest6() {
            if(max!=0) {
                DataManagement.setCurrent(subU);
                assertEquals(BudgetController.setMaxMaintenanceExpense(max).isActionSuccessful(), correct);
                assertEquals(BudgetController.setMaxUniformExpense(max).isActionSuccessful(), correct);
                assertEquals(BudgetController.setMaxAdvertisementExpense(max).isActionSuccessful(), correct);
                assertEquals(BudgetController.setMaxOtherExpense(max).isActionSuccessful(), correct);
            }
        }

        @Test
        public void BudgetControllerTest7() {
            if(max!=0) {
                DataManagement.setCurrent(subU);
                assertTrue(BudgetRegulations.getMaxMaintenanceExpense()!=0);
                assertTrue(BudgetRegulations.getMaxAdvertisementExpense()!=0);
                assertTrue(BudgetRegulations.getMaxUniformExpense()!=0);
                assertTrue(BudgetRegulations.getMaxOtherExpense()!=0);
            }
        }

    }//class

    /**
     * Test - BC2
     */
    @RunWith(Parameterized.class)
    public static class teamBudgetSet {

        String name;
        double expanse;
        boolean correct;
        Team team = new Team("tester","homeFiled");
        UnifiedSubscription subO = createTeamOwner("oUser", "123456", "usero@gmail.com");
        UnifiedSubscription subM = createTeamManager("mUser", "123456", "userm@gmail.com");
        Subscription subF = new Fan("fUser", "123456", "userf@gmail.com");


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"tester", -300, false}, {null, 300, false}, {"", 50, false},
            });
        }

        public teamBudgetSet(String teamName, double expanse, boolean correct) {
            //parameter
            this.name = teamName;
            this.expanse = expanse;
            this.correct = correct;
            subO.permissions.add_default_owner_permission();
            team.EditTeamOwner(subO,1);
            team.EditTeamManager(subM,1);
            subM.permissions.add_permissions(PermissionAction.Team_financial);
            DataManagement.addToListTeam(team);
            //remove if default is fixed
            BudgetRegulations.setMaxPlayerSalary(10000);
            BudgetRegulations.setMinPlayerSalary(5000);
            BudgetRegulations.setMaxCoachSalary(10000);
            BudgetRegulations.setMinCoachSalary(5000);
            BudgetRegulations.setMaxAdvertisementExpense(50000);
            BudgetRegulations.setMaxMaintenanceExpense(50000);
            BudgetRegulations.setMaxUniformExpense(50000);

        }

        @Test
        public void BudgetTest1() {
            DataManagement.setCurrent(subO);
            assertTrue(BudgetController.startNewQuarter().isActionSuccessful());
            DataManagement.setCurrent(subM);
            assertTrue(BudgetController.startNewQuarter().isActionSuccessful());
            DataManagement.setCurrent(subF);
            assertFalse(BudgetController.startNewQuarter().isActionSuccessful());
        }


        @Test
        public void BudgetTest2() {
            DataManagement.setCurrent(subO);
            assertEquals(correct,BudgetController.addExpenseToTeam(name,expanse, Expense.Uniform).isActionSuccessful());
            if(correct && ( 10000 >= expanse && expanse >= 5000)) {
                assertEquals(correct, BudgetController.addExpenseToTeam(name, expanse, Expense.PlayerSalary).isActionSuccessful());
                assertEquals(correct, BudgetController.addExpenseToTeam(name, expanse, Expense.CoachSalary).isActionSuccessful());
            }
            else if(!correct){
                assertEquals(correct, BudgetController.addExpenseToTeam(name, expanse, Expense.PlayerSalary).isActionSuccessful());
                assertEquals(correct, BudgetController.addExpenseToTeam(name, expanse, Expense.CoachSalary).isActionSuccessful());
            }
            DataManagement.setCurrent(subM);
            assertEquals(correct,BudgetController.addExpenseToTeam(name,expanse, Expense.Maintenance).isActionSuccessful());
            assertEquals(correct,BudgetController.addExpenseToTeam(name,expanse, Expense.Advertisement).isActionSuccessful());
            DataManagement.setCurrent(subF);
            assertFalse(BudgetController.addExpenseToTeam(name,expanse, Expense.Other).isActionSuccessful());
        }

        @Test
        public void BudgetTest3() {
            boolean ans = correct;
            if(expanse==700000){
                ans = true;
            }
            DataManagement.setCurrent(subO);
            assertEquals(ans,BudgetController.addIncomeToTeam(name,expanse, Income.Donation).isActionSuccessful());
            assertEquals(ans,BudgetController.addIncomeToTeam(name,expanse, Income.Gambling).isActionSuccessful());
            DataManagement.setCurrent(subM);
            assertEquals(ans,BudgetController.addIncomeToTeam(name,expanse, Income.GameTickets).isActionSuccessful());
            assertEquals(ans,BudgetController.addIncomeToTeam(name,expanse, Income.Merchandise).isActionSuccessful());
            DataManagement.setCurrent(subF);
            assertFalse(BudgetController.addIncomeToTeam(name,expanse, Income.Merchandise).isActionSuccessful());
        }

        @Test
        public void BudgetTest4() {
            double i=-1;
            if(name==null || !name.equals("tester")) {
                DataManagement.setCurrent(subO);
                assertTrue(i == BudgetController.getTeamBalanceForQuarter(name));
                DataManagement.setCurrent(subM);
                assertTrue(i == BudgetController.getTeamBalanceForQuarter(name));
            }
            else{
                DataManagement.setCurrent(subO);
                if (expanse!=-300) {
                    assertTrue(i != BudgetController.getTeamBalanceForQuarter(name));
                    DataManagement.setCurrent(subM);
                    assertTrue(i != BudgetController.getTeamBalanceForQuarter(name));
                }
            }
            DataManagement.setCurrent(subF);
            assertTrue(i == BudgetController.getTeamBalanceForQuarter(name));
        }
    }

    /**
     * Test - BC3
     */
    @RunWith(Parameterized.class)
    public static class UnionBudgetTest {

        double expanse;
        boolean correct;
        Subscription subU = new UnionRepresentative("uUser", "123456", "useru@gmail.com");
        Subscription subF = new Fan("fUser", "123456", "userf@gmail.com");


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {-300, false}, {200000, false},{0,false},
                    {5500, true},{9000, true}

            });
        }

        public UnionBudgetTest(double expanse, boolean correct) {
            //parameter
            this.expanse = expanse;
            this.correct = correct;

            //remove if default is fixed
            BudgetRegulations.setMaxRefereeSalary(10000);
            BudgetRegulations.setMinRefereeSalary(5000);
            BudgetRegulations.setMaxUnionMemberSalary(10000);
            BudgetRegulations.setMinUnionMemberSalary(5000);
        }

        @Test
        public void BudgetControllerTest1() {
            DataManagement.setCurrent(subU);
            assertEquals(correct,BudgetController.addExpenseToUnion(expanse,Expense.RefereeSalary).isActionSuccessful());
            assertEquals(correct,BudgetController.addExpenseToUnion(expanse,Expense.UnionMemberSalary).isActionSuccessful());

            DataManagement.setCurrent(subF);
            assertFalse(BudgetController.addExpenseToUnion(expanse,Expense.UnionMemberSalary).isActionSuccessful());

        }

        @Test
        public void BudgetControllerTest2() {
            DataManagement.setCurrent(subU);
            if(expanse>=0) {
                assertTrue(BudgetController.addIncomeToUnion(expanse, Income.Donation).isActionSuccessful());
            }
            else{
                assertFalse(BudgetController.addIncomeToUnion(expanse, Income.Donation).isActionSuccessful());
            }

            DataManagement.setCurrent(subF);
            assertFalse(BudgetController.addIncomeToUnion(expanse,Income.Donation).isActionSuccessful());
        }

        @Test
        public void BudgetControllerTest3() {
            DataManagement.setCurrent(subU);
            assertTrue(BudgetController.getUnionBalance() != -1);

            DataManagement.setCurrent(subF);
            assertTrue(BudgetController.getUnionBalance() == -1);


        }
    }


}
