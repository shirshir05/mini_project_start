import BusniesServic.Business_Layer.BudgetManagement.BudgetRegulationsTest;
import BusniesServic.Business_Layer.BudgetManagement.TeamBudgetTest;
import BusniesServic.Business_Layer.BudgetManagement.UnionBudgetTest;
import BusniesServic.Business_Layer.Game.EventTest;
import BusniesServic.Business_Layer.Game.GameTest;
import BusniesServic.Business_Layer.Game.LeagueTest;
import BusniesServic.Business_Layer.Game.SeasonTest;
import BusniesServic.Business_Layer.TeamManagement.TeamTest;
import BusniesServic.Business_Layer.Trace.CoachPersonalPageTest;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Service_Layer.*;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class mainTest {

    static int testCounter = 0;
    static int failedTests = 0;

    public static void main(String[] args){

        //BudgetManagement
        test(BudgetRegulationsTest.class);
        test(TeamBudgetTest.class);
        test(UnionBudgetTest.class);

        //Game
        test(EventTest.class);
        test(GameTest.class);
        test(LeagueTest.class);
        test(SeasonTest.class);

        //Team Management
        test(TeamTest.class);

        //Trace
        test(CoachPersonalPageTest.class);

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
        test(TeamOwnerTest.class);
        test(UnionRepresentativeTest.class);

        //Service Layer
        test(BudgetControllerTest.class);
        test(DataManagementTest.class);
        test(EditAndShowUserDetailsTest.class);
        test(GameSettingsControllerTest.class);
        test(LogAndExitControllerTest.class);
        //test(MainTestClass.class);
        test(SearchLoggerTest.class);
        test(TeamControllerTest.class);

        System.out.println("Total number of tests: "+testCounter);
        System.out.println("Total number of failed tests: "+failedTests);

    }

    private static void test(Class toTest){
        DataManagement.cleanAllData();
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
