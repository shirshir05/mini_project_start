package Busnies_Servic.Business_Layer.BudgetManagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class TeamBudgetTest {

    /**
     * Test - TB1
     */
    @RunWith(Parameterized.class)
    public static class TeamExpanseIncome {
        //parameter
        double max;
        double min;
        double expanse;
        boolean correct;
        TeamBudget TB;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"teamName",5000,2000,-100,false},{"teamName",5000,2000,-1000,false},{"teamName",5000,2000,3000,true}
                    ,{"teamName",5000,0,0,true},{"teamName",5000,2000,5000,true}

            });
        }
        public TeamExpanseIncome(String name, double Max, double Min, double expanses, boolean cor) {
            //parameter
            this.TB = new TeamBudget(name);
            max = Max;
            min = Min;
            expanse = expanses;
            correct = cor;
        }

        @Test
        public void TeamExpenseTest1() {
            BudgetRegulations.setMaxPlayerSalary(max);
            BudgetRegulations.setMinPlayerSalary(min);
            assertEquals(TB.addExpense(expanse,Expense.PlayerSalary).isActionSuccessful(),correct);

            BudgetRegulations.setMaxCoachSalary(max);
            BudgetRegulations.setMinCoachSalary(min);
            assertEquals(TB.addExpense(expanse,Expense.CoachSalary).isActionSuccessful(),correct);

            BudgetRegulations.setMaxUniformExpense(max);
            assertEquals(TB.addExpense(expanse,Expense.Uniform).isActionSuccessful(),correct);

            BudgetRegulations.setMaxAdvertisementExpense(max);
            assertEquals(TB.addExpense(expanse,Expense.Advertisement).isActionSuccessful(),correct);

            BudgetRegulations.setMaxMaintenanceExpense(max);
            assertEquals(TB.addExpense(expanse,Expense.Maintenance).isActionSuccessful(),correct);

            BudgetRegulations.setMaxOtherExpense(max);
            assertEquals(TB.addExpense(expanse,Expense.Other).isActionSuccessful(),correct);
        }

        @Test
        public void TeamIncomeTest1() {
            if(expanse>0){
                assertTrue(TB.addIncome(expanse,Income.Gambling).isActionSuccessful());
                assertTrue(TB.addIncome(expanse,Income.Donation).isActionSuccessful());
                assertTrue(TB.addIncome(expanse,Income.GameTickets).isActionSuccessful());
                assertTrue(TB.addIncome(expanse,Income.Merchandise).isActionSuccessful());
            }
            else{
                assertFalse(TB.addIncome(expanse,Income.Gambling).isActionSuccessful());
                assertFalse(TB.addIncome(expanse,Income.Donation).isActionSuccessful());
                assertFalse(TB.addIncome(expanse,Income.GameTickets).isActionSuccessful());
                assertFalse(TB.addIncome(expanse,Income.Merchandise).isActionSuccessful());
            }
        }
        @Test
        public void CurrentAmountTest1() {
            assertNotEquals(TB.getCurrentAmount(),0);
            TB.startNewQuarter();
            assertTrue(TB.getCurrentAmount() == 0);
            TB.addExpense(-1000,Expense.Other);
            TB.startNewQuarter();
        }

    }

}