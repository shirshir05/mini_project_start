package acceptensTest;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class GuestMenuTest {

    /**
     * Test - BR1
     */
    @RunWith(Parameterized.class)
    public static class SalaryBudgetTest {


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public SalaryBudgetTest() {

        }

        @Test
        public void SalaryBudgetTest1() {
        }
    }

}
