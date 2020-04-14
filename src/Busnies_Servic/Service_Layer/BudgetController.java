package Busnies_Servic.Service_Layer;

import Busnies_Servic.Enum.ActionStatus;
import Busnies_Servic.Business_Layer.BudgetManagement.BudgetRegulations;
import Busnies_Servic.Business_Layer.BudgetManagement.Expense;
import Busnies_Servic.Business_Layer.BudgetManagement.Income;
import Busnies_Servic.Business_Layer.BudgetManagement.UnionBudget;
import Busnies_Servic.Business_Layer.TeamManagement.Team;

import java.util.HashSet;

public class BudgetController {

    //TODO check permissions!

    private static UnionBudget unionBudget = new UnionBudget();


    //region Change regulations
    public static void setMaxPlayerSalary(double maxPlayerSalary) {
        if (verifyMaxSalary(maxPlayerSalary, BudgetRegulations.getMinPlayerSalary())){
            BudgetRegulations.setMaxPlayerSalary(maxPlayerSalary);
        }
    }

    public static void setMinPlayerSalary(double minPlayerSalary) {
        if (verifyMinSalary(minPlayerSalary, BudgetRegulations.getMaxPlayerSalary())) {
            BudgetRegulations.setMinPlayerSalary(minPlayerSalary);
        }
    }

    public static void setMaxCoachSalary(double maxCoachSalary) {
        if (verifyMaxSalary(maxCoachSalary, BudgetRegulations.getMinCoachSalary())){
            BudgetRegulations.setMaxCoachSalary(maxCoachSalary);
        }
    }

    public static void setMinCoachSalary(double minCoachSalary) {
        if (verifyMinSalary(minCoachSalary, BudgetRegulations.getMaxCoachSalary())) {
            BudgetRegulations.setMinCoachSalary(minCoachSalary);
        }
    }

    public static void setMaxMaintenanceExpense(double maxMaintenanceExpense) {
        if (verifyMaxExpense(maxMaintenanceExpense)) {
            BudgetRegulations.setMaxMaintenanceExpense(maxMaintenanceExpense);
        }
    }

    public static void setMaxAdvertisementExpense(double maxAdvertisementExpense) {
        if (verifyMaxExpense(maxAdvertisementExpense)) {
            BudgetRegulations.setMaxAdvertisementExpense(maxAdvertisementExpense);
        }
    }

    public static void setMaxUniformExpense(double maxUniformExpense) {
        if (verifyMaxExpense(maxUniformExpense)) {
            BudgetRegulations.setMaxUniformExpense(maxUniformExpense);
        }
    }

    public static void setMaxOtherExpense(double maxOtherExpense) {
        if (verifyMaxExpense(maxOtherExpense)) {
            BudgetRegulations.setMaxOtherExpense(maxOtherExpense);
        }
    }

    public static void setMaxRefereeSalary(double maxRefereeSalary) {
        if (verifyMaxSalary(maxRefereeSalary, BudgetRegulations.getMinRefereeSalary())) {
            BudgetRegulations.setMaxRefereeSalary(maxRefereeSalary);
        }
    }

    public static void setMinRefereeSalary(double minRefereeSalary) {
        if (verifyMinSalary(minRefereeSalary, BudgetRegulations.getMaxRefereeSalary())) {
            BudgetRegulations.setMinRefereeSalary(minRefereeSalary);
        }
    }

    public static void setMaxUnionMemberSalary(double maxUnionMemberSalary) {
        if (verifyMaxSalary(maxUnionMemberSalary, BudgetRegulations.getMinUnionMemberSalary())) {
            BudgetRegulations.setMaxUnionMemberSalary(maxUnionMemberSalary);
        }
    }

    public static void setMinUnionMemberSalary(double minUnionMemberSalary) {
        if (verifyMinSalary(minUnionMemberSalary, BudgetRegulations.getMaxUnionMemberSalary())) {
            BudgetRegulations.setMinUnionMemberSalary(minUnionMemberSalary);
        }
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
    public static void startNewQuarter(){
        HashSet<Team> teams = DataManagement.getListTeam();
        for (Team t:teams){
            t.startNewQuarterForBudget();
        }
    }

    public static ActionStatus addExpenseToTeam(String teamName, double expense, Expense description){
        if (expense < 0)
            return new ActionStatus(false, "An expense has to be a positive number");
        Team team = DataManagement.findTeam(teamName);
        if(team == null)
            return new ActionStatus(false, "Team not found");
        return team.addExpense(expense, description);
    }

    public static ActionStatus addIncomeToTeam(String teamName, double income, Income description){
        if (income < 0)
            return new ActionStatus(false, "An income has to be a positive number");
        Team team = DataManagement.findTeam(teamName);
        if(team == null)
            return new ActionStatus(false, "Team not found");
        return team.addIncome(income, description);
    }

    public static double getTeamBalanceForQuarter(String teamName){
        Team team = DataManagement.findTeam(teamName);
        if(team != null)
            return team.getCurrentAmountInBudget();
        return -1;
    }

    //endregion

    //region Union budget

    public static ActionStatus addExpenseToUnion(double expense, Expense description){
        if(expense < 0)
            return new ActionStatus(false, "An expense has to be a positive number");
        return unionBudget.addExpense(expense,description);
    }

    public static ActionStatus addIncomeToUnion(double income, Income description){
        if(income < 0)
            return new ActionStatus(false, "An income has to be a positive number");
        return unionBudget.addIncome(income,description);
    }

    public static double getUnionBalance(){
        return unionBudget.getCurrentAmount();
    }

    //endregion
}
