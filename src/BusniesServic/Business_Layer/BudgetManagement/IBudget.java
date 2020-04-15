package BusniesServic.Business_Layer.BudgetManagement;
import BusniesServic.Enum.ActionStatus;

public interface IBudget {

    /**
     * adds an expense to the budget according to the regulations set for team budget management.
     * @param expense the amount of the expense
     * @param description reason for expense
     * @return returns an ActionStatus instance telling if the action was successful or not.
     */
    ActionStatus addExpense(double expense, Expense description);

    /**
     * adds an income to the budget according to the regulations set for team budget management.
     * @param income the amount of the income
     * @param description reason for income
     * @return returns an ActionStatus instance telling if the action was successful or not.
     */
    ActionStatus addIncome(double income, Income description);

    /**
     *
     * @return the amount left in the budget
     */
    double getCurrentAmount();

}
