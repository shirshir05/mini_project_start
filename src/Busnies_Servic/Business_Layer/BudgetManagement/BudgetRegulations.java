package Busnies_Servic.Business_Layer.BudgetManagement;

/**
 *
 * A Class that manages the budget in the system
 */
public class BudgetRegulations {

    //region Team related members

    private static double maxPlayerSalary;
    private static double minPlayerSalary;

    private static double maxCoachSalary;
    private static double minCoachSalary;

    private static double maxMaintenanceExpense;

    private static double maxAdvertisementExpense;

    private static double maxUniformExpense;

    private static double maxOtherExpense;

    //endregion

    //region Union related members

    private static double maxRefereeSalary;
    private static double minRefereeSalary;

    private static double maxUnionMemberSalary;
    private static double minUnionMemberSalary;

    //endregion


    //region Team budget verification
    public static boolean checkPlayerSalary(double playerSalary){
        return maxPlayerSalary >= playerSalary && playerSalary >= minPlayerSalary;
    }

    public static boolean checkCoachSalary(double coachSalary){
        return maxCoachSalary >= coachSalary && coachSalary >= minCoachSalary;
    }

    public static boolean checkMaintenanceExpense(double maintenanceExpense, double howMuchSpentSoFar){
        return (maintenanceExpense+howMuchSpentSoFar) <= maxMaintenanceExpense;
    }

    public static boolean checkAdvertisementExpense(double advertisementExpense, double howMuchSpentSoFar){
        return (advertisementExpense+howMuchSpentSoFar) <= maxAdvertisementExpense;
    }

    public static boolean checkUniformExpense(double uniformExpense, double howMuchSpentSoFar){
        return (uniformExpense+howMuchSpentSoFar) <= maxUniformExpense;
    }

    public static boolean checkOtherExpense(double otherExpense, double howMuchSpentSoFar){
        return (otherExpense+howMuchSpentSoFar) <= maxOtherExpense;
    }

    //endregion

    //region Union budget verification

    public static boolean checkRefereeSalary(double playerSalary){
        return maxRefereeSalary >= playerSalary && playerSalary >= minRefereeSalary;
    }

    public static boolean checkUnionMemberSalary(double coachSalary){
        return maxUnionMemberSalary >= coachSalary && coachSalary >= minUnionMemberSalary;
    }

    //endregion

    //region Setters for team members -> requires permissions

    public static void setMaxPlayerSalary(double maxPlayerSalary) {
        BudgetRegulations.maxPlayerSalary = maxPlayerSalary;
    }

    public static void setMinPlayerSalary(double minPlayerSalary) {
        BudgetRegulations.minPlayerSalary = minPlayerSalary;
    }

    public static void setMaxCoachSalary(double maxCoachSalary) {
        BudgetRegulations.maxCoachSalary = maxCoachSalary;
    }

    public static void setMinCoachSalary(double minCoachSalary) {
        BudgetRegulations.minCoachSalary = minCoachSalary;
    }

    public static void setMaxMaintenanceExpense(double maxMaintenanceExpense) {
        BudgetRegulations.maxMaintenanceExpense = maxMaintenanceExpense;
    }

    public static void setMaxAdvertisementExpense(double maxAdvertisementExpense) {
        BudgetRegulations.maxAdvertisementExpense = maxAdvertisementExpense;
    }

    public static void setMaxUniformExpense(double maxUniformExpense) {
        BudgetRegulations.maxUniformExpense = maxUniformExpense;
    }

    public static void setMaxOtherExpense(double maxOtherExpense) {
        BudgetRegulations.maxOtherExpense = maxOtherExpense;
    }

    //endregion

    //region Setters for union members -> requires permissions

    public static void setMaxRefereeSalary(double maxRefereeSalary) {
        BudgetRegulations.maxRefereeSalary = maxRefereeSalary;
    }

    public static void setMinRefereeSalary(double minRefereeSalary) {
        BudgetRegulations.minRefereeSalary = minRefereeSalary;
    }

    public static void setMaxUnionMemberSalary(double maxUnionMemberSalary) {
        BudgetRegulations.maxUnionMemberSalary = maxUnionMemberSalary;
    }

    public static void setMinUnionMemberSalary(double minUnionMemberSalary) {
        BudgetRegulations.minUnionMemberSalary = minUnionMemberSalary;
    }

    //endregion ->
}
