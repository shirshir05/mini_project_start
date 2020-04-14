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
    //TODO - check negative numbers and max<min

    private static UnionBudget unionBudget = new UnionBudget();

    //**********************************************endregion************************************************************//
    //region Change regulations
    public static void setMaxPlayerSalary(double maxPlayerSalary) {
        BudgetRegulations.setMaxPlayerSalary(maxPlayerSalary);
    }

    public static void setMinPlayerSalary(double minPlayerSalary) {
        BudgetRegulations.setMinPlayerSalary(minPlayerSalary);
    }

    public static void setMaxCoachSalary(double maxCoachSalary) {
        BudgetRegulations.setMaxCoachSalary(maxCoachSalary);
    }

    public static void setMinCoachSalary(double minCoachSalary) {
        BudgetRegulations.setMinCoachSalary(minCoachSalary);
    }

    public static void setMaxMaintenanceExpense(double maxMaintenanceExpense) {
        BudgetRegulations.setMaxMaintenanceExpense(maxMaintenanceExpense);
    }

    public static void setMaxAdvertisementExpense(double maxAdvertisementExpense) {
        BudgetRegulations.setMaxAdvertisementExpense(maxAdvertisementExpense);
    }

    public static void setMaxUniformExpense(double maxUniformExpense) {
        BudgetRegulations.setMaxUniformExpense(maxUniformExpense);
    }

    public static void setMaxOtherExpense(double maxOtherExpense) {
        BudgetRegulations.setMaxOtherExpense(maxOtherExpense);
    }

    public static void setMaxRefereeSalary(double maxRefereeSalary) {
        BudgetRegulations.setMaxRefereeSalary(maxRefereeSalary);
    }

    public static void setMinRefereeSalary(double minRefereeSalary) {
        BudgetRegulations.setMinRefereeSalary(minRefereeSalary);
    }

    public static void setMaxUnionMemberSalary(double maxUnionMemberSalary) {
        BudgetRegulations.setMaxUnionMemberSalary(maxUnionMemberSalary);
    }

    public static void setMinUnionMemberSalary(double minUnionMemberSalary) {
        BudgetRegulations.setMinUnionMemberSalary(minUnionMemberSalary);
    }



//**********************************************region Team budget************************************************************//
    public static void startNewQuarter(){
        HashSet<Team> teams = DataManagement.getListTeam();
        for (Team t:teams){
            t.startNewQuarterForBudget();
        }
    }

    public static ActionStatus addExpenseToTeam(String teamName, double expense, Expense description){
        Team team = DataManagement.findTeam(teamName);
        if(team == null)
            return new ActionStatus(false, "Team not found");
        return team.addExpense(expense, description);
    }

    public static ActionStatus addIncomeToTeam(String teamName, double income, Income description){
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
        return unionBudget.addExpense(expense,description);
    }

    public static ActionStatus addIncomeToUnion(double income, Income description){
        return unionBudget.addIncome(income,description);
    }

    public static double getUnionBalance(String teamName){
        return unionBudget.getCurrentAmount();
    }

    //endregion
}
