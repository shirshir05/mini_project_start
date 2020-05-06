package BusinessService.Business_Layer.UserManagement;

import BusinessService.Enum.PermissionAction;

import java.util.Observable;
import java.util.Observer;

public class TeamOwner /*extends Subscription*/  implements Observer{


    //teamOwner or SystemAdministrator
    private Subscription appointedByTeamOwner;


    /**
     * constructor
     */
    public TeamOwner(/*String arg_user_name, String arg_password,String email*/) {
       // super(arg_user_name, arg_password,email);
        appointedByTeamOwner = null;
    }

    //**********************************************get & set************************************************************//

    public Subscription getAppointedByTeamOwner() {
        return appointedByTeamOwner;
    }

    /**
     * The function allows you to save who has appointed the group owner
     * @param appointedByTeamOwner
     */
    public void setAppointedByTeamOwner(Subscription appointedByTeamOwner) {
        this.appointedByTeamOwner = appointedByTeamOwner;

        // removing the appointee
        /*if(appointedByTeamOwner == null){
            this.appointedByTeamOwner = null;
            permissions.remove_permissions(PermissionAction.Edit_team);
            permissions.remove_permissions(PermissionAction.Appointment_of_team_owner);
            permissions.remove_permissions(PermissionAction.Remove_Appointment_of_team_owner);
            permissions.remove_permissions(PermissionAction.Appointment_of_team_manager);
            permissions.remove_permissions(PermissionAction.Remove_Appointment_of_team_manager);
            permissions.remove_permissions(PermissionAction.Appointment_of_player);
            permissions.remove_permissions(PermissionAction.Close_team);
            permissions.remove_permissions(PermissionAction.Team_financial);
            permissions.remove_permissions(PermissionAction.Remove_Appointment_of_player);

        }else {
            permissions.add_default_owner_permission();
            this.appointedByTeamOwner = appointedByTeamOwner;
        }*/

    }

    //**********************************************function************************************************************//

    @Override
    public void update(Observable o, Object arg) {
        /*this.alerts.add((String)arg);*/
    }

    //**********************************************to string************************************************************//

    @Override
    public String toString() {

        String res = "TeamOwner: " + "\n";
        if(appointedByTeamOwner != null){
            res = res + "Appointed by: "+appointedByTeamOwner.userName;
        }
        else {
            res = res + "Appointed by: null";
        }
        return  res;
    }

}

