package BusinessService.Service_Layer;

import BusinessService.Enum.ActionStatus;
import BusinessService.Business_Layer.BudgetManagement.BudgetRegulations;
import BusinessService.Business_Layer.BudgetManagement.Expense;
import BusinessService.Business_Layer.BudgetManagement.Income;
import BusinessService.Business_Layer.BudgetManagement.UnionBudget;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Enum.PermissionAction;

import java.util.Arrays;
import java.util.HashSet;

public class BudgetController {

    private static final String NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET = "You are not allowed to perform actions on the budget";
    private static final String UPDATE_SUCCESSFUL = "Updated successfully";
    private static final String ILLEGAL_VALUE = "Illegal value";

    private static UnionBudget unionBudget = new UnionBudget();


    //region Change regulations

    public static ActionStatus setMaxPlayerSalary(double maxPlayerSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxSalary(maxPlayerSalary, BudgetRegulations.getMinPlayerSalary())){
            BudgetRegulations.setMaxPlayerSalary(maxPlayerSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMinPlayerSalary(double minPlayerSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMinSalary(minPlayerSalary, BudgetRegulations.getMaxPlayerSalary())) {
            BudgetRegulations.setMinPlayerSalary(minPlayerSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMaxCoachSalary(double maxCoachSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxSalary(maxCoachSalary, BudgetRegulations.getMinCoachSalary())){
            BudgetRegulations.setMaxCoachSalary(maxCoachSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMinCoachSalary(double minCoachSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMinSalary(minCoachSalary, BudgetRegulations.getMaxCoachSalary())) {
            BudgetRegulations.setMinCoachSalary(minCoachSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMaxMaintenanceExpense(double maxMaintenanceExpense) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxExpense(maxMaintenanceExpense)) {
            BudgetRegulations.setMaxMaintenanceExpense(maxMaintenanceExpense);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, ILLEGAL_VALUE);
    }

    public static ActionStatus setMaxAdvertisementExpense(double maxAdvertisementExpense) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxExpense(maxAdvertisementExpense)) {
            BudgetRegulations.setMaxAdvertisementExpense(maxAdvertisementExpense);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, ILLEGAL_VALUE);
    }

    public static ActionStatus setMaxUniformExpense(double maxUniformExpense) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxExpense(maxUniformExpense)) {
            BudgetRegulations.setMaxUniformExpense(maxUniformExpense);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, ILLEGAL_VALUE);
    }

    public static ActionStatus setMaxOtherExpense(double maxOtherExpense) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxExpense(maxOtherExpense)) {
            BudgetRegulations.setMaxOtherExpense(maxOtherExpense);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, ILLEGAL_VALUE);
    }

    public static ActionStatus setMaxRefereeSalary(double maxRefereeSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxSalary(maxRefereeSalary, BudgetRegulations.getMinRefereeSalary())) {
            BudgetRegulations.setMaxRefereeSalary(maxRefereeSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMinRefereeSalary(double minRefereeSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMinSalary(minRefereeSalary, BudgetRegulations.getMaxRefereeSalary())) {
            BudgetRegulations.setMinRefereeSalary(minRefereeSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMaxUnionMemberSalary(double maxUnionMemberSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMaxSalary(maxUnionMemberSalary, BudgetRegulations.getMinUnionMemberSalary())) {
            BudgetRegulations.setMaxUnionMemberSalary(maxUnionMemberSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    public static ActionStatus setMinUnionMemberSalary(double minUnionMemberSalary) {
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (verifyMinSalary(minUnionMemberSalary, BudgetRegulations.getMaxUnionMemberSalary())) {
            BudgetRegulations.setMinUnionMemberSalary(minUnionMemberSalary);
            return new ActionStatus(true, UPDATE_SUCCESSFUL);
        }
        return new ActionStatus(false, "Illegal salary");
    }

    private static boolean verifyMaxSalary(double maxSalary, double existingMin) {
        return maxSalary > 0 && maxSalary >= existingMin;
    }

    private static boolean verifyMinSalary(double minSalary, double existingMax) {
        return BudgetRegulations.getMinPossibleSalary() <= minSalary && minSalary <= existingMax;
    }

    private static boolean verifyMaxExpense(double expense){
        return expense >= 0;
    }

    //endregion

    // region Team budget

    public static ActionStatus startNewQuarter(){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Team_financial))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        HashSet<Team> teams = DataManagement.getListTeam();
        for (Team t:teams){
            t.startNewQuarterForBudget();
        }
        return new ActionStatus(true, UPDATE_SUCCESSFUL);
    }
    public static ActionStatus addExpenseToTeam(String teamName, double expense, Expense description){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Team_financial))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (expense < 0)
            return new ActionStatus(false, "An expense has to be a positive number");
        Team team = DataManagement.findTeam(teamName);
        if(team == null)
            return new ActionStatus(false, "Team not found");
        if(!team.checkIfObjectInTeam(DataManagement.getCurrent())){
            return new ActionStatus(false, "You do not belong to the team.");
        }
        return team.addExpense(expense, description);
    }

    public static ActionStatus addIncomeToTeam(String teamName, double income, Income description){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Team_financial))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if (income < 0)
            return new ActionStatus(false, "An income has to be a positive number");
        Team team = DataManagement.findTeam(teamName);
        if(team == null)
            return new ActionStatus(false, "Team not found");
        if(!team.checkIfObjectInTeam(DataManagement.getCurrent())){
            return new ActionStatus(false, "You do not belong to the team.");
        }
        return team.addIncome(income, description);
    }

    public static double getTeamBalanceForQuarter(String teamName){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Team_financial))
            return -1;
        Team team = DataManagement.findTeam(teamName);
        if(team != null){
            if(!team.checkIfObjectInTeam(DataManagement.getCurrent())){
                return -1;
            }
            return team.getCurrentAmountInBudget();
        }
        return -1;
    }

    //endregion

    //region Union budget

    public static ActionStatus addExpenseToUnion(double expense, Expense description){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
        return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if(expense < 0)
            return new ActionStatus(false, "An expense has to be a positive number");
        return unionBudget.addExpense(expense,description);
    }

    public static ActionStatus addIncomeToUnion(double income, Income description){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return new ActionStatus(false, NOT_ALLOWED_TO_PERFORM_ACTIONS_ON_BUDGET);
        if(income < 0)
            return new ActionStatus(false, "An income has to be a positive number");
        return unionBudget.addIncome(income,description);
    }

    public static double getUnionBalance(){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.change_budget_regulations))
            return -1;
        return unionBudget.getCurrentAmount();
    }

    public static Expense getExpenseFromString(String str){
        if(! (Arrays.stream(Expense.values()).anyMatch(e -> e.name().equals(str)))){
            return null;
        }else{
            Expense enumExpense =  Expense.valueOf(str);
            return enumExpense;
        }
    }

    public static Income getIncomeFromString(String str){
        if(! (Arrays.stream(Income.values()).anyMatch(e -> e.name().equals(str)))){
            return null;
        }else{
            Income enumIncome =  Income.valueOf(str);
            return enumIncome;
        }
    }

    //endregion
}
