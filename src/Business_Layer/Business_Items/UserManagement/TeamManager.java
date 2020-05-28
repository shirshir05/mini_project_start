package Business_Layer.Business_Items.UserManagement;

import java.util.Observable;
import java.util.Observer;

public class TeamManager /*extends Subscription */ implements Observer, java.io.Serializable{

    String managerAppointedByTeamOwner;

    public TeamManager(/*String arg_user_name, String arg_password,String email*/) {
        //super(arg_user_name, arg_password,email);
        managerAppointedByTeamOwner = null;
    }
    //**********************************************get & set************************************************************//

    public String getAppointedByTeamOwner() {
        return managerAppointedByTeamOwner;
    }

    /**
     * The function allows you to save who has appointed the group owner
     * @param appointedByTeamOwner
     */
    public void setAppointedByTeamOwner(String appointedByTeamOwner) {
        this.managerAppointedByTeamOwner = appointedByTeamOwner;
    }

    //**********************************************function ************************************************************//

    /**
     * Add alert
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        /*this.alerts.add((String)arg);*/
    }

    //**********************************************to string ************************************************************//

    @Override
    public String toString() {
        String res = "TeamManager: " + "\n";
        if (managerAppointedByTeamOwner != null) {
            res = res + "Appointed by: " + managerAppointedByTeamOwner;
        } else {
            res = res + "Appointed by: null";
        }
        return res;
    }

}


