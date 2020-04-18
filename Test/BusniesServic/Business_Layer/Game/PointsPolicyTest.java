package BusniesServic.Business_Layer.Game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointsPolicyTest {

    private PointsPolicy pointsPolicy;

    /**
     * Test - pp1
     */
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

    /**
     * Test - pp2
     */
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

    /**
     * Test - pp3
     */
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