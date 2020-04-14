package Busnies_Servic.Business_Layer.BudgetManagement;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class BudgetRegulationsTest {

    /**
     * Test - BR1
     */
    @RunWith(Parameterized.class)
    public static class SalaryBudgetTest {

        double max;
        double min;
        double salary;
        boolean correct;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {100, 0, 50, true}, {100, 0, 100, true}, {100, 0, 0, true}, {0, 0, 0, true}, {10.5, 10.0, 10.2, true},
                    {100, 50, 200, false}, {100, 50, 10, false}, {100, 0, 100.1, false}
            });
        }

        public SalaryBudgetTest(double max, double min, double salary, boolean correct){
            this.max = max;
            this.min = min;
            this.salary = salary;
            this.correct = correct;
        }

        @Test
        public void SalaryBudgetTest1() {
            BudgetRegulations.setMaxPlayerSalary(max);
            BudgetRegulations.setMinPlayerSalary(min);
            assertEquals(BudgetRegulations.checkPlayerSalary(salary), correct);

        }

        @Test
        public void SalaryBudgetTest2() {
            BudgetRegulations.setMaxCoachSalary(max);
            BudgetRegulations.setMinCoachSalary(min);
            assertEquals(BudgetRegulations.checkCoachSalary(salary), correct);
        }

        @Test
        public void SalaryBudgetTest3() {
            BudgetRegulations.setMaxRefereeSalary(max);
            BudgetRegulations.setMinRefereeSalary(min);
            assertEquals(BudgetRegulations.checkRefereeSalary(salary), correct);
        }

        @Test
        public void SalaryBudgetTest4() {
            BudgetRegulations.setMaxUnionMemberSalary(max);
            BudgetRegulations.setMinUnionMemberSalary(min);
            assertEquals(BudgetRegulations.checkUnionMemberSalary(salary), correct);
        }

    }

    /**
     * Test - BR2
     */
    @RunWith(Parameterized.class)
    public static class MaxBudgetTest {

        double thisExpanse;
        double prevExpanses;
        double maxBudget;
        boolean correct;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {10, 0, 50, true}, {100, 0, 100, true}, {0, 100, 100, true}, {0, 0, 0, true},
                    {200, 50, 200, false}, {100, 50, 10, false}, {100, 0.2, 100.1, false}
            });
        }

        public MaxBudgetTest(double thisExpanse, double prevExpanses, double maxBudget, boolean correct){
            this.thisExpanse = thisExpanse;
            this.prevExpanses = prevExpanses;
            this.maxBudget = maxBudget;
            this.correct = correct;
        }

        @Test
        public void MaxBudgetTest1() {
            BudgetRegulations.setMaxAdvertisementExpense(maxBudget);
            assertEquals(BudgetRegulations.checkAdvertisementExpense(thisExpanse,prevExpanses), correct);

        }
        @Test
        public void MaxBudgetTest2() {
            BudgetRegulations.setMaxMaintenanceExpense(maxBudget);
            assertEquals(BudgetRegulations.checkMaintenanceExpense(thisExpanse,prevExpanses), correct);

        }
        @Test
        public void MaxBudgetTest3() {
            BudgetRegulations.setMaxUniformExpense(maxBudget);
            assertEquals(BudgetRegulations.checkUniformExpense(thisExpanse,prevExpanses), correct);

        }
        @Test
        public void MaxBudgetTest4() {
            BudgetRegulations.setMaxOtherExpense(maxBudget);
            assertEquals(BudgetRegulations.checkOtherExpense(thisExpanse,prevExpanses), correct);

        }
    }
}