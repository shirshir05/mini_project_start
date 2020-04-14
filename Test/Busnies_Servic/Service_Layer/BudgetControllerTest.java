package Busnies_Servic.Service_Layer;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Enclosed.class)
public class BudgetControllerTest {
    /**
     * Test - BC1
     */
    @RunWith(Parameterized.class)
    public static class BudgetSet{

        double max;
        double min;
        boolean correct;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public BudgetSet() {
            //parameter
        }
        @Test
        public void BudgetControllerTest1() {
            BudgetController.setMaxAdvertisementExpense(max);

        }

    }


}
