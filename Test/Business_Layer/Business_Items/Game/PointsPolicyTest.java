package Business_Layer.Business_Items.Game;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class PointsPolicyTest {

    @RunWith(Parameterized.class)
    /**
     * Test - pp1
     */
    public static class PointsPolicy1 {
        private PointsPolicy pointsPolicy;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public PointsPolicy1() {
        }

        @Test
        public void testLeagueConstructor() {
            int win = 4;
            int lose = -1;
            int drawn = 1;
            pointsPolicy = new PointsPolicy(win, lose, drawn);

            assertEquals(win, pointsPolicy.getWin());
            assertEquals(lose, pointsPolicy.getLose());
            assertEquals(drawn, pointsPolicy.getDrawn());

        }
    }

    /**
     * Test - pp2
     */
    public static class PointsPolicy2 {
        private PointsPolicy pointsPolicy;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public PointsPolicy2() {
        }

        @Test
        public void testSetWin() {
            int win = 4;
            int lose = -1;
            int drawn = 1;
            pointsPolicy = new PointsPolicy(win, lose, drawn);
            assertEquals(win, pointsPolicy.getWin());
            win = 2;
            pointsPolicy.setWin(win);
            assertEquals(win, pointsPolicy.getWin());
        }
    }

    /**
     * Test - pp3
     */
    public static class PointsPolicy3 {
        private PointsPolicy pointsPolicy;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public PointsPolicy3() {
        }

        @Test
        public void testSetDrawn() {
            int win = 4;
            int lose = -1;
            int drawn = 1;
            pointsPolicy = new PointsPolicy(win, lose, drawn);
            assertEquals(win, pointsPolicy.getWin());
            drawn = 2;
            pointsPolicy.setEqual(drawn);
            assertEquals(drawn, pointsPolicy.getDrawn());
        }
    }

        public static class PointsPolicy4 {
            private PointsPolicy pointsPolicy;

            @Parameterized.Parameters
            public static Collection<Object[]> data() {
                return Arrays.asList(new Object[][]{
                        {}
                });
            }

            public PointsPolicy4() {
            }

            /**
             * Test - pp4
             */
            @Test
            public void testsetLose() {
                int win = 4;
                int lose = -1;
                int drawn = 1;
                pointsPolicy = new PointsPolicy(win, lose, drawn);
                assertEquals(win, pointsPolicy.getWin());
                lose = 2;
                pointsPolicy.setLose(lose);
                assertEquals(lose, pointsPolicy.getLose());
            }
        }
    }