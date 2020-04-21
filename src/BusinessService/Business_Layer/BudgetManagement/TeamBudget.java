package BusinessService.Business_Layer.BudgetManagement;

import BusinessService.Enum.ActionStatus;

import java.util.Observable;

/**
 * Describes a budget of a team.
 * The budget is initialized to be zero until updated to be otherwise
 */

public class TeamBudget extends Observable implements IBudget {

    //region Members

    /**
     * the sum of expenses and incomes of a team in the current quarter
     */
    private double amountForCurrentQuarter;

    /**
     * the remaining debt or excess funds from previous quarters.
     * calculated in the end of each quarter in startNewQuarter method
     */
    private double amountFromPreviousQuarters;

    /**
     * the following members keep how much was spent so far.
     */
    private double uniformExpenses;
    private double advertisementExpenses;
    private double maintenanceExpenses;
    private double otherExpenses;

    /**
     * the name of the team this budget belongs to, to notify the union representative when it exceeds the budget
     */
    private String teamName;

    //endregion

    public TeamBudget(String teamName) {
        this.teamName = teamName;
    }


    //region IBudget override

    @Override
    public ActionStatus addExpense(double expense, Expense description) {
        if(expense < 0)
            return new ActionStatus(false,"An expense should be a positive amount");
        ActionStatus tempAS;
        switch(description){
            case PlayerSalary:
                return statusToReturn(BudgetRegulations.checkPlayerSalary(expense),expense,"Salary is not within limits");
            case CoachSalary:
                return statusToReturn(BudgetRegulations.checkCoachSalary(expense),expense,"Salary is not within limits");
            case Uniform:
                tempAS = statusToReturn(BudgetRegulations.checkUniformExpense(expense,uniformExpenses),expense,"Expense exceeds limits");
                if(tempAS.isActionSuccessful())
                    uniformExpenses += expense;
                return tempAS;
            case Advertisement:
                tempAS = statusToReturn(BudgetRegulations.checkAdvertisementExpense(expense,advertisementExpenses),expense,"Expense exceeds limits");
                if(tempAS.isActionSuccessful())
                    advertisementExpenses += expense;
                return tempAS;
            case Maintenance:
                tempAS = statusToReturn(BudgetRegulations.checkMaintenanceExpense(expense,maintenanceExpenses),expense,"Expense exceeds limits");
                if(tempAS.isActionSuccessful())
                    maintenanceExpenses += expense;
                return tempAS;
            case Other:
                tempAS = statusToReturn(BudgetRegulations.checkOtherExpense(expense,otherExpenses),expense,"Expense exceeds limits");
                if(tempAS.isActionSuccessful())
                    otherExpenses += expense;
                return tempAS;
        }
        return new ActionStatus(false,"Not a valid expense description");
    }

    @Override
    public ActionStatus addIncome(double income, Income description) {
        if(income <= 0)
            return new ActionStatus(false,"An income should be a positive amount");
        updateAmount(income);
        return new ActionStatus(true,"Income updated");
    }

    @Override
    public double getCurrentAmount() {
        return amountForCurrentQuarter;
    }

    //endregion

    //region startNewQuarter -> requires permission

    /**
     * resets all expenses to zero for the current quarter and updates the amount
     */
    public void startNewQuarter(){
        //first, verify that the team did not exceed the budget in the previous quarter
        //if it did, notify the union representatives:
        if(amountForCurrentQuarter < 0)
        {
            setChanged();
            notifyObservers("Team "+teamName+" has exceeded the budget for the quarter");
        }
        amountFromPreviousQuarters = amountForCurrentQuarter + amountFromPreviousQuarters;

        //start from zero the balance for the current quarter:
        amountForCurrentQuarter = 0;

        //the track of the different expenses is also set to zero:
        uniformExpenses = 0;
        advertisementExpenses = 0;
        maintenanceExpenses = 0;
        otherExpenses = 0;
    }

    //endregion

    //region Private methods

    private ActionStatus statusToReturn(boolean success, double expense, String messageForFail){
        if(success){
            updateAmount((-1)*expense);
            return new ActionStatus(true,"Operation succeeded");
        }
        else {
            return new ActionStatus(false, messageForFail);
        }
    }

    private void updateAmount(double toAddOrReduce){
        amountForCurrentQuarter +=toAddOrReduce;
    }

    //endregion


}
