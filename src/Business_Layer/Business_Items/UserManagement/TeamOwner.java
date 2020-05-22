package Business_Layer.Business_Items.UserManagement;

import java.util.Observable;
import java.util.Observer;

public class TeamOwner /*extends Subscription*/  implements Observer{


    //teamOwner or SystemAdministrator
    private String ownerAppointedByTeamOwner;


    /**
     * constructor
     */
    public TeamOwner(/*String arg_user_name, String arg_password,String email*/) {
       // super(arg_user_name, arg_password,email);
        ownerAppointedByTeamOwner = null;
    }

    //**********************************************get & set************************************************************//

    public String getAppointedByTeamOwner() {
        return ownerAppointedByTeamOwner;
    }

    /**
     * The function allows you to save who has appointed the group owner
     * @param appointedByTeamOwner
     */
    public void setAppointedByTeamOwner(String appointedByTeamOwner) {
        this.ownerAppointedByTeamOwner = appointedByTeamOwner;

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
        if(ownerAppointedByTeamOwner != null){
            res = res + "Appointed by: "+ownerAppointedByTeamOwner;
        }
        else {
            res = res + "Appointed by: null";
        }
        return  res;
    }

}

