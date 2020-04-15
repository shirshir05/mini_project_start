package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Business_Layer.UserManagement.UnionRepresentative;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;


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
        Subscription subU = new UnionRepresentative("uUser","123456","useru@gmail.com");
        Subscription subF = new Fan("fUser","123456","userf@gmail.com");


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {0,1000,true},{-5,300000,false},{-1000,10,false},{0,0,false}


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
            assertTrue(BudgetController.setMaxAdvertisementExpense(max).isActionSuccessful());

        }

    }


}
