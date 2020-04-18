package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.Trace.PersonalPage;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.Observable;

public class AlertController {

    /**
     *  This function register the fan to alerts of a game he choose.
     */
    public ActionStatus fanRegisterToGameAlerts(int game_number){
        ActionStatus AC = null;
        if (!(DataManagement.getCurrent() instanceof Fan)){
            AC = new ActionStatus(false,"You are not a Fan");
        }
        else if(DataManagement.getGame(game_number) == null){
            AC = new ActionStatus(false,"There is no such game in the system");
        }
        else{
            Observable chosen_game = DataManagement.getGame(game_number);
            chosen_game.addObserver((Fan) DataManagement.getCurrent());
            AC = new ActionStatus(false,"You were registered successfully to the game alerts");
        }
        return AC;
    }

    /**
     * This function register the current user to the page he asked to be registered to.
     * @param arg_user_to_register is the name of the page the user wants to register to
     * @return true if the registeration succeeded
     */
    public ActionStatus fanRegisterToPage(String arg_user_to_register) {
        ActionStatus AC = null;
        if (DataManagement.getCurrent() != null) {
            BusniesServic.Business_Layer.UserManagement.Subscription userWithPersonalPage = DataManagement.containSubscription(arg_user_to_register);
            Team team = DataManagement.findTeam(arg_user_to_register);
            if (!(DataManagement.getCurrent() instanceof Fan)) {
                AC = new ActionStatus(false, "You are not a Fan");
            } else if (userWithPersonalPage == null && team == null) {
                AC = new ActionStatus(false, "There is no such page");
            }
            //TODO -add spelling correction here?
            else if (userWithPersonalPage instanceof Coach) {
                ((Coach) userWithPersonalPage).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                AC = new ActionStatus(true, "You were successfully registered to the Coach page");
            } else if (userWithPersonalPage instanceof Player) {
                ((Player) userWithPersonalPage).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                AC = new ActionStatus(true, "You were successfully registered to the Player page");
            } else if (userWithPersonalPage instanceof UnifiedSubscription && (((UnifiedSubscription) userWithPersonalPage).isACoach() || ((UnifiedSubscription) userWithPersonalPage).isAPlayer())) {
                //by default - registering to the player page. if it doesn't exist, then register to the coach page
                UnifiedSubscription us = (UnifiedSubscription) userWithPersonalPage;
                PersonalPage personalPage = us.getPlayerPersonalPage();
                if (personalPage == null) {
                    personalPage = us.getCoachPersonalPage();
                }
                if (personalPage != null) {
                    personalPage.addObserver((Fan) DataManagement.getCurrent());
                    AC = new ActionStatus(true, "You were successfully registered to the Player page");
                }
                else {
                    AC = new ActionStatus(false, "Could not find the personal page you requested");
                }
            } else if (team != null) {
                team.getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                AC = new ActionStatus(true, "You were successfully registered to the Team page");
            } else {
                AC = new ActionStatus(false, "Cannot find the personal page you requested");
            }

            logger.log("fan_register_to_page, page name: " + arg_user_to_register + " ,user name: " + DataManagement.getCurrent().getUserName() + " successful: " + AC.getDescription());
        }
        else {
            AC = new ActionStatus(false,"No user is logged in to the system");
        }
        logger.log("fan_register_to_page, page name: " + arg_user_to_register + " failed: " + AC.getDescription());

        return AC;
    }

    /**
     * This method adds a complaint by a user.
     * @param complaintDescription is the complaint
     */
    public ActionStatus addComplaint(String complaintDescription){
        ActionStatus AC = null;
        if(complaintDescription == null || complaintDescription.isEmpty()) {
            AC = new ActionStatus(false, "Complaint cannot be empty");
            logger.log("Add complaint status: "+AC.getDescription());
        }
        else if (!(DataManagement.getCurrent() instanceof Fan)){
            AC = new ActionStatus(false, "You are not a Fan");
            logger.log("Add complaint status: "+AC.getDescription());
        }
        else {
            Fan f = (Fan)DataManagement.getCurrent();
            Complaint c = new Complaint(complaintDescription);
            f.addComplaint(c);
            DataManagement.addComplaint(c);
            AC = new ActionStatus(true, "Complaint added successfully");
            logger.log("Add Complaint of user : "+ f.getName() +" "+AC.getDescription());
        }
        return AC;
    }

    /**
     * for the use of the system manager, to read all the complaints in the system
     * @return a hash set of all the complaints
     */
    public HashSet<Complaint> getAllComplaints() {
        if(DataManagement.getCurrent()!= null && DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints))
            return DataManagement.getAllComplaints();
        return null;
    }

    /**
     * for the use of the system manager, to read only the unanswered complaints in the system
     */
    public HashSet<Complaint> getUnansweredComplaints() {
        if(DataManagement.getCurrent()!= null && DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints))
            return DataManagement.getUnansweredComplaints();
        return null;
    }

    public ActionStatus answerCompliant(Complaint complaint, String answer){
        if(DataManagement.getCurrent()== null || !DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints)) {
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