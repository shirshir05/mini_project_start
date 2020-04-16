package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Coach;
import BusniesServic.Business_Layer.UserManagement.Complaint;
import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Player;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;

import java.util.HashSet;
import java.util.Observable;

public class AlertController {

    /**
     *  This function register the fan to alerts of a game he choose.
     */
    public boolean fanRegisterToGameAlerts(int game_number){
        Observable chosen_game = DataManagement.getGame(game_number);
        if(DataManagement.getGame(game_number) != null){
            chosen_game.addObserver((Fan) DataManagement.getCurrent());
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This function register the current user to the page he asked to be registered to.
     * @param arg_user_to_register is the name of the page the user wants to register to
     * @return true if the registeration succeeded
     */
    public boolean fanRegisterToPage(String arg_user_to_register){
        boolean ans = false;
        BusniesServic.Business_Layer.UserManagement.Subscription current_user = DataManagement.containSubscription(arg_user_to_register);
        //TODO -add spelling correction here?
        if (current_user instanceof Coach) {
            ((Coach) current_user).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
            ans = true;
        }
        else if (current_user instanceof Player) {
            ((Player) current_user).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
            ans = true;
        }
        else{
            Team t = DataManagement.findTeam(arg_user_to_register);
            if (t!=null){
                t.getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                ans = true;
            }
        }if(!ans){
            return ans;
        }
        logger.log("fan_register_to_page, page mane: "+arg_user_to_register+" ,user mane: "+ current_user.getUserName() +" successful: "+ans);
        return ans;
    }

    /**
     * This method adds a complaint by a user.
     * @param fan the fan who created the complaint
     */
    public static ActionStatus addComplaint(String complaintDescription, Fan fan){
        ActionStatus AC = null;
        if(complaintDescription == null || complaintDescription.isEmpty()) {
            AC = new ActionStatus(false, "Complaint cannot be empty");
        }
        else {
            Complaint c = new Complaint(complaintDescription);
            fan.addComplaint(c);
            DataManagement.addComplaint(c);
            AC = new ActionStatus(true, "Complaint added successfully");
        }
        logger.log("Add Complaint of user : "+ fan.getName() +" "+AC.getDescription());
        return AC;
    }

    /**
     * for the use of the system manager, to read all the complaints in the system
     * @return a hash set of all the complaints
     */
    public static HashSet<Complaint> getAllComplaints() {
        if(DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints))
            return DataManagement.getAllComplaints();
        return null;
    }

    /**
     * for the use of the system manager, to read only the unanswered complaints in the system
     */
    public static HashSet<Complaint> getUnansweredComplaints() {
        if(DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints))
            return DataManagement.getUnansweredComplaints();
        return null;
    }

    public static ActionStatus answerCompliant(Complaint complaint, String answer){
        if(!DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints)) {
            return new ActionStatus(false, "You do not have the required permissions to answer complaints");
        }
        if(complaint.isAnswered()){
            return new ActionStatus(false, "Complaint has been answered already");
        }
        if(answer == null || answer.isEmpty()){
            return new ActionStatus(false, "An answer to a complaint cannot be empty or null");
        }
        //remove the complaint without the answer
        DataManagement.getAllComplaints().remove(complaint);
        //answer the complaint
        complaint.answerComplaint(answer);
        //add it back to the list of complaints
        DataManagement.addComplaint(complaint);
        return new ActionStatus(true, "Complaint has been answered successfully");
    }
}