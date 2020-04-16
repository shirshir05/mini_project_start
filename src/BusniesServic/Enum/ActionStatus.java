package BusniesServic.Enum;

/**
 * This class describes if an operation in the system was successful or not
 * Class Members:
 * actionSuccessful - true if action was successfully done
 * description - describes the reason the action was successful or not
 */
public class ActionStatus {

    private boolean actionSuccessful;
    private String description = " ";

    public ActionStatus(boolean actionSucceeded, String description) {
        this.actionSuccessful = actionSucceeded;
        this.description = description;
    }

    public boolean isActionSuccessful() {
        return actionSuccessful;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if(obj == null){
            return false;
        }

        if (!(obj instanceof ActionStatus)) {
            return false;
        }

        ActionStatus actionStatus = (ActionStatus) obj;

        if(actionStatus.description.equals(description) && actionStatus.actionSuccessful == actionSuccessful){

            return true;
        }

        return false;
    }
}
