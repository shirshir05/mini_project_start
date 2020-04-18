import BusniesServic.Business_Layer.BudgetManagement.BudgetRegulationsTest;
import BusniesServic.Business_Layer.BudgetManagement.TeamBudgetTest;
import BusniesServic.Business_Layer.BudgetManagement.UnionBudgetTest;
import BusniesServic.Business_Layer.Game.*;
import BusniesServic.Business_Layer.TeamManagement.TeamScore;
import BusniesServic.Business_Layer.TeamManagement.TeamTest;
import BusniesServic.Business_Layer.Trace.*;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Service_Layer.*;
import Presentation_Layer.StartSystem;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class mainTest {

    static int testCounter = 0;
    static int failedTests = 0;

    public static void main(String[] args) throws InterruptedException {
        test(ScoreTable.class);
        test(AlertControllerTest.class);
        test(TeamOwnerTest.class);
        Thread.sleep(1000);
        Game.game_id = 0;


        //BudgetManagement
        test(BudgetRegulationsTest.class);
        test(TeamBudgetTest.class);
        test(UnionBudgetTest.class);

        //Game
        test(EventTest.class);
        Game.game_id = 0;

        test(GameTest.class);
        Game.game_id = 0;

        test(LeagueTest.class);
        test(SeasonTest.class);

        //Team Management
        test(TeamTest.class);

        //Trace
        test(CoachPersonalPageTest.class);
        test(PlayerPersonalPageTest.class);

        //User Management
        test(CoachTest.class);
        test(ComplaintTest.class);
        test(FanTest.class);
        test(GuestTest.class);
        test(PlayerTest.class);
        test(RefereeTest.class);
        test(SubscriptionFactoryTest.class);
        test(SubscriptionTest.class);
        test(SystemAdministratorTest.class);
        test(TeamManagerTest.class);

        test(UnionRepresentativeTest.class);

        //Service Layer
        test(BudgetControllerTest.class);

        test(EditAndShowUserDetailsTest.class);
        test(GameSettingsControllerTest.class);
        test(LogAndExitControllerTest.class);
        //test(MainTestClass.class);
        test(SearchLoggerTest.class);
        test(TeamControllerTest.class);
        StartSystem system = new StartSystem();
        system.cleanSystem();
        test(DataManagementTest.class);


        test(TeamPersonalPageTest.class);
        test(PersonalPageTest.class);
        test(FootballPlayerStatisticTest.class);
        test(FootballTeamStatisticTest.class);
        test(TeamScore.class);
        test(PointsPolicy.class);
        test(ScoreTable.class);
        

        System.out.println("---------------------------------------------------------------");

        System.out.println("Total number of tests: "+testCounter);
        System.out.println("Total number of failed tests: "+failedTests);

    }

    private static void test(Class toTest){
        DataManagement.cleanAllData();
        StartSystem system = new StartSystem();
        system.cleanSystem();
        System.out.println("----------------------"+toTest.getName()+"----------------------------");
        Result result = JUnitCore.runClasses(toTest);
        for(Failure fail : result.getFailures()){
            System.out.println(fail.toString());
        }
        System.out.println("Number of tests = " + result.getRunCount());
        testCounter = testCounter + result.getRunCount();
        System.out.println("Number of test failed = " + result.getFailureCount());
        failedTests = failedTests + result.getFailureCount();
    }

}
