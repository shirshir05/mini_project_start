package Business_Layer.Business_Items.BudgetManagement;

/**
 *
 * A Class that manages the budget in the system
 */
public class BudgetRegulations implements java.io.Serializable{

    private static final int DEFAULT_MAX_SALARY = 10000;
    private static final int MIN_SALARY = 5000;
    private static final int DEFAULT_MAX_EXPENSE = 1000;

    //region Team related members

    private static double maxPlayerSalary = DEFAULT_MAX_SALARY;
    private static double minPlayerSalary = MIN_SALARY;

    private static double maxCoachSalary = DEFAULT_MAX_SALARY;
    private static double minCoachSalary = MIN_SALARY;

    private static double maxMaintenanceExpense = DEFAULT_MAX_EXPENSE;

    private static double maxAdvertisementExpense = DEFAULT_MAX_EXPENSE;

    private static double maxUniformExpense = DEFAULT_MAX_EXPENSE;

    private static double maxOtherExpense = DEFAULT_MAX_EXPENSE;

    //endregion

    //region Union related members

    private static double maxRefereeSalary = DEFAULT_MAX_SALARY;
    private static double minRefereeSalary = MIN_SALARY;

    private static double maxUnionMemberSalary = DEFAULT_MAX_SALARY;
    private static double minUnionMemberSalary = MIN_SALARY;

    //endregion

    /**
     * returns the lowest salary than can be given to a any employee -> minimum wage
     */
    public static int getMinPossibleSalary(){
        return MIN_SALARY;
    }

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

    //region Getters for team members

    public static double getMaxPlayerSalary() {
        return maxPlayerSalary;
    }

    public static double getMinPlayerSalary() {
        return minPlayerSalary;
    }

    public static double getMaxCoachSalary() {
        return maxCoachSalary;
    }

    public static double getMinCoachSalary() {
        return minCoachSalary;
    }

    public static double getMaxMaintenanceExpense() {
        return maxMaintenanceExpense;
    }

    public static double getMaxAdvertisementExpense() {
        return maxAdvertisementExpense;
    }

    public static double getMaxUniformExpense() {
        return maxUniformExpense;
    }

    public static double getMaxOtherExpense() {
        return maxOtherExpense;
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

    //region Getters for union members

    public static double getMaxRefereeSalary() {
        return maxRefereeSalary;
    }

    public static double getMinRefereeSalary() {
        return minRefereeSalary;
    }

    public static double getMaxUnionMemberSalary() {
        return maxUnionMemberSalary;
    }

    public static double getMinUnionMemberSalary() {
        return minUnionMemberSalary;
    }

    public static void resetRegulationsToDefault(){
        maxPlayerSalary = DEFAULT_MAX_SALARY;
        minPlayerSalary = MIN_SALARY;
        maxCoachSalary = DEFAULT_MAX_SALARY;
        minCoachSalary = MIN_SALARY;
        maxMaintenanceExpense = DEFAULT_MAX_EXPENSE;
        maxAdvertisementExpense = DEFAULT_MAX_EXPENSE;
        maxUniformExpense = DEFAULT_MAX_EXPENSE;
        maxOtherExpense = DEFAULT_MAX_EXPENSE;
        maxRefereeSalary = DEFAULT_MAX_SALARY;
        minRefereeSalary = MIN_SALARY;
        maxUnionMemberSalary = DEFAULT_MAX_SALARY;
        minUnionMemberSalary = MIN_SALARY;
    }

    //endregion
}
