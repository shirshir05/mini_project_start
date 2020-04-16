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

    public static void main(String[] args){
        //BudgetManagement
        JUnitCore.runClasses(BudgetRegulationsTest.class);
        JUnitCore.runClasses(TeamBudgetTest.class);
        JUnitCore.runClasses(UnionBudgetTest.class);

        //Game
        JUnitCore.runClasses(EventTest.class);
        JUnitCore.runClasses(GameTest.class);
        JUnitCore.runClasses(LeagueTest.class);
        JUnitCore.runClasses(SeasonTest.class);

        //Team Management
        JUnitCore.runClasses(TeamTest.class);

        //Trace
        JUnitCore.runClasses(CoachPersonalPageTest.class);

        //User Management
        JUnitCore.runClasses(CoachTest.class);
        JUnitCore.runClasses(ComplaintTest.class);
        JUnitCore.runClasses(FanTest.class);
        JUnitCore.runClasses(GuestTest.class);
        JUnitCore.runClasses(PlayerTest.class);
        JUnitCore.runClasses(RefereeTest.class);
        JUnitCore.runClasses(SubscriptionFactoryTest.class);
        JUnitCore.runClasses(SubscriptionTest.class);
        JUnitCore.runClasses(SystemAdministratorTest.class);
        JUnitCore.runClasses(TeamManagerTest.class);
        JUnitCore.runClasses(TeamOwnerTest.class);
        JUnitCore.runClasses(UnionRepresentativeTest.class);

        //Service Layer
        JUnitCore.runClasses(BudgetControllerTest.class);
        JUnitCore.runClasses(DataManagementTest.class);
        JUnitCore.runClasses(EditAndShowUserDetailsTest.class);
        JUnitCore.runClasses(GameSettingsControllerTest.class);
        JUnitCore.runClasses(LogAndExitControllerTest.class);
        JUnitCore.runClasses(MainTestClass.class);
        JUnitCore.runClasses(SearchLoggerTest.class);
        JUnitCore.runClasses(TeamControllerTest.class);

    }

    private static void register(){
        System.out.println("----------------------TEST FOR LOGANDEXITCONTROLER----------------------------");
        Result result = JUnitCore.runClasses(CoachTest.class);
        for(Failure fail : result.getFailures()){
            System.out.println(fail.toString());
        }
        System.out.println("The number of test = " + result.getRunCount());
       System.out.println("The number of test fail = " + result.getFailureCount());
    }
    private static void SystemAdministratorTest(){
        System.out.println("----------------------TEST FOR SYSTEMADMINSTRATOR----------------------------");
        Result result = JUnitCore.runClasses(SystemAdministratorTest.class);
        for(Failure fail : result.getFailures()){
            System.out.println(fail.toString());
        }
        System.out.println("The number of test = " + result.getRunCount());
        System.out.println("The number of test fail = " + result.getFailureCount());
    }
}
