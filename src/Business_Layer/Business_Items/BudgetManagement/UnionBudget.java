package Business_Layer.Business_Items.BudgetManagement;

import Business_Layer.Enum.ActionStatus;

/**
 * Describes a budget of the union.
 * The budget is initialized to be zero until updated to be otherwise
 */

public class UnionBudget implements IBudget, java.io.Serializable {

    //region Members
    /**
     * keeps the amount left for the union
     */
    private double amount;

    //endregion

    //region IBudget override

    @Override
    public ActionStatus addExpense(double expense, Expense description) {
        if(expense < 0)
            return new ActionStatus(false,"An expense should be a positive amount");
        switch(description){
            case RefereeSalary:
                return statusToReturn(BudgetRegulations.checkRefereeSalary(expense),expense,"Salary is not within limits");
            case UnionMemberSalary:
                return statusToReturn(BudgetRegulations.checkUnionMemberSalary(expense),expense,"Salary is not within limits");
        }
        return new ActionStatus(false,"Not a valid expense");
    }

    @Override
    public ActionStatus addIncome(double income, Income description) {
        if(income < 0)
            return new ActionStatus(false,"An income should be a positive amount");
        updateAmount(income);
        return new ActionStatus(true,"Income updated");
    }

    @Override
    public double getCurrentAmount() {
        return amount;
    }

    //endregion

    //region Private methods

    private ActionStatus statusToReturn(boolean success, double expense, String messageForFail){
        if(success){
            updateAmount((-1)*expense);
            return new ActionStatus(true,"Operation succeeded");
        }
        else {
            return new ActionStatus(false,messageForFail);
        }
    }

    private void updateAmount(double toAddOrReduce){
        amount+=toAddOrReduce;
    }

    //endregion

}
