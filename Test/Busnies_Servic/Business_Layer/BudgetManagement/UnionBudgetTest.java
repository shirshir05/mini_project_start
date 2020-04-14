package Busnies_Servic.Business_Layer.BudgetManagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class UnionBudgetTest {

    /**
     * Test - TB1
     */
    @RunWith(Parameterized.class)
    public static class UnionExpanseIncome {
        //parameter
        double max;
        double min;
        double expanse;
        boolean correct;
        UnionBudget TB;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {5000,2000,-100,false},{5000,2000,-1000,false},{5000,2000,3000,true}
                    ,{5000,0,0,true},{5000,2000,5000,true}

            });
        }

        public UnionExpanseIncome(double Max, double Min, double expanses, boolean cor) {
            //parameter
            this.TB = new UnionBudget();
            max = Max;
            min = Min;
            expanse = expanses;
            correct = cor;
        }

        @Test
        public void UnionExpenseTest1() {
            BudgetRegulations.setMaxRefereeSalary(max);
            BudgetRegulations.setMinRefereeSalary(min);
            assertEquals(TB.addExpense(expanse,Expense.RefereeSalary).isActionSuccessful(),correct);

            BudgetRegulations.setMaxUnionMemberSalary(max);
            BudgetRegulations.setMinUnionMemberSalary(min);
            assertEquals(TB.addExpense(expanse,Expense.UnionMemberSalary).isActionSuccessful(),correct);

        }

        @Test
        public void UnionIncomeTest1() {
            if(expanse>=0){
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
        }

    }
}