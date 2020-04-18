package BusniesServic.Service_Layer;

// all Subscription in system

import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Enum.PermissionAction;
import BusniesServic.Enum.Role;
import DB_Layer.logger;
import javafx.scene.chart.XYChart;

import java.security.PublicKey;
import java.util.HashSet;

// to function that remove all Subscription


/**
 * This Class is responsible for connecting to the system exit system
 */
public class LogAndExitController{

    protected static SubscriptionFactory factory = new SubscriptionFactory();

    /**
     * The purpose of this function is to register the user to the system.
     * The function checks that the user information is correct
     * @param arg_user_name
     * @param arg_password
     * @param arg_role
     * @return comment print to user
     */
    public ActionStatus Registration(String arg_user_name, String arg_password, String arg_role, String email){
        //TODO need remove from file ? check?
        ActionStatus AC = null;
        Role role_enum = DataManagement.returnEnum(arg_role);
        if(role_enum == null){
            AC = new ActionStatus(false, "The role does not exist in the system.");
        }
        else {
            String check_input = DataManagement.InputTest(arg_user_name,arg_password);
            if(!DataManagement.checkEmail(email)){
                AC =  new ActionStatus(false,  "Invalid email, please enter a valid email.");
            }
            else if( check_input!= null){
                AC = new ActionStatus(false, check_input);
            }

            else {
                Subscription newSub = factory.Create(arg_user_name, arg_password, role_enum, email);
                DataManagement.setSubscription(newSub);
                DataManagement.setCurrent(newSub);
                AC = new ActionStatus(true, "Subscription successfully added!");
            }
        }
        logger.log("new Registration attempt of user : "+ arg_user_name+" "+arg_role+" "+email + AC.getDescription());
        return AC;
    }


    /**
     * A function that allows the user to log in to the system by username and password
     * @param arg_user_name
     * @param arg_password
     * @return comment print to user
     */
    public ActionStatus Login(String arg_user_name, String arg_password) {
        ActionStatus AC = null;
        Subscription toLogin = DataManagement.containSubscription(arg_user_name);
        if (DataManagement.getCurrent() != null) {
            AC = new ActionStatus(false, "Another subscription is connected to the system.");
        }
        else if (toLogin == null) {
            AC = new ActionStatus(false, "The user " + arg_user_name + " does not exist in the system.");
        }
        else if (!toLogin.getPassword().equals(Subscription.getHash(arg_password))) {
            AC = new ActionStatus(false, "The password is incorrect.");
        }
        else {
            DataManagement.setCurrent(toLogin);
            AC = new ActionStatus(true, "Login successful.");
        }
        logger.log("Login attempt of user : " + arg_user_name + " " + AC.getDescription());
        return AC;
    }


    /**
     * The function allows logging off of a user connected to the system
     * @param arg_user_name
     * @param arg_password
     * @return comment print to user
     */
    public ActionStatus Exit(String arg_user_name, String arg_password){
        ActionStatus AC = null;
        if(DataManagement.getCurrent() != null){
            if(DataManagement.getCurrent().getUserName().equals(arg_user_name) && DataManagement.getCurrent().getPassword().equals(Subscription.getHash(arg_password))){
                DataManagement.setCurrent(null);
                AC = new ActionStatus(true,  "Successfully disconnected from the system.");
            }
            else{
                AC = new ActionStatus(false, "One of the details you entered is incorrect.");
            }
        }
        else{
            AC = new ActionStatus(false, "One of the details you entered is incorrect.");
        }
        logger.log("Exit attempt of user : "+ arg_user_name+" "+ AC.getDescription());
        return AC;
    }


    /**
     * Only the administrator can delete  users
     * @return
     */
    public ActionStatus RemoveSubscription(String userName){
        ActionStatus AC = null;
        if(!ConstraintsCorrectness(userName)){
            AC =  new ActionStatus(false,  "The system constraints do not allow this subscription to be deleted.");
        }
        else if(DataManagement.containSubscription(userName) == null){
            AC =  new ActionStatus(false,  "There is no subscription with this username in the system.");
        }
        else if(!(DataManagement.getCurrent().getPermissions().check_permissions((PermissionAction.Removing_Subscriptions)))){
            AC =  new ActionStatus(false,  "You are not authorized to perform this action.");
        }
        else {
            if(DataManagement.getCurrent().getPermissions().equals(userName)){
                DataManagement.setCurrent(null);
            }
            DataManagement.removeSubscription(userName);
            AC = new ActionStatus(false, "the transaction completed successfully.");
        }
        logger.log("Remove Subscription attempt of user : "+userName+" "+AC.getDescription());
        return AC;
    }

    private boolean ConstraintsCorrectness(String userName){
        return ( numberSystemAdministrator(userName) && TeamOwnerForTeam(userName));

    }

    /**
     * Check if there is more than one administrator
     * @param userName
     * @return false - error the action illegal
     */
    private boolean numberSystemAdministrator(String userName){
        HashSet<SystemAdministrator> list =  DataManagement.getSystemAdministratorsList();
        if(list.size() == 1){
            for (SystemAdministrator user: list) {
                if(user.getUserName().equals(userName)){
                    // the action illegal
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Check that there is another team owner for a team
     * @param userName
     * @return
     */
    private boolean TeamOwnerForTeam(String userName){
        Subscription teamOwner = DataManagement.containSubscription(userName);
        if(teamOwner instanceof TeamOwner){
            return searchTeamOwner((TeamOwner) teamOwner);
        }
        else if(teamOwner instanceof UnifiedSubscription && ((UnifiedSubscription)teamOwner).isATeamOwner())
            return searchTeamOwner(((UnifiedSubscription)teamOwner).getTeamOwner());
        return false;

    }

    private boolean searchTeamOwner(TeamOwner teamOwner) {
        HashSet<Team> list =  DataManagement.getListTeam();
        for (Team team: list) {
            HashSet<TeamOwner> teamOwnerHash = team.getListTeamOwner();
            if(teamOwnerHash.size() == 1){
                if(teamOwnerHash.contains(teamOwner)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Add user
     * The team owner can be a player, coach and manager
     */
    public ActionStatus addRoleToUser(String role,String password) {
        ActionStatus AC;
        if (!DataManagement.getCurrent().getPassword().equals(Subscription.getHash(password))) {
            AC = new ActionStatus(false, "The password does not match the password entered in the system.");
        }
        //continue the rest of the method only if the password is correct:
        else {
            Role desiredRole = DataManagement.returnEnum(role);
            if (desiredRole == null) {
                AC = new ActionStatus(false, "The role does not exist in the system.");
            } else if (desiredRole != Role.Coach && desiredRole != Role.Player && desiredRole != Role.TeamManager && desiredRole != Role.TeamOwner) {
                AC = new ActionStatus(false, "The role you requested cannot be performed in parallel");
            } else { //legal desired role
                Subscription sub = DataManagement.getCurrent();
                //the user is a subscription with allowed parallel roles:
                if (sub instanceof Player || sub instanceof Coach || sub instanceof TeamOwner || sub instanceof TeamManager) {
                    //delete current subscription of the user:
                    DataManagement.removeSubscription(sub.getUserName());
                    //add a subscription with the roles required:
                    Registration(DataManagement.getCurrent().getUserName(), password, "UnifiedSubscription", DataManagement.getCurrent().getEmail());
                    //in registration, the user is logged in automatically, getting the new user:
                    Subscription currentSub = DataManagement.getCurrent();
                    //just to make sure:
                    if (currentSub instanceof UnifiedSubscription) {
                        UnifiedSubscription currentUnified = (UnifiedSubscription) currentSub;
                        //add the current role to subscription
                        currentUnified.setRole(sub);
                        //add the new role to the subscription
                        Subscription newRole = factory.Create(DataManagement.getCurrent().getUserName(), password, desiredRole, DataManagement.getCurrent().getEmail());
                        currentUnified.setRole(newRole);
                        AC = new ActionStatus(true, "Added the role " + role + " to your user");
                    }
                    else {
                        //this means the current user is not a unified subscription
                        //should never happen:
                        AC = new ActionStatus(false, "Something went wrong. Please close the system and try again");
                    }
                } else { //not one of the four roles who are allowed to be parallel users
                    AC = new ActionStatus(false, "You are not authorized to perform this action.");
                }
            }
        }
        logger.log("Add Subscription attempt of user : " + DataManagement.getCurrent().getUserName() + role + " " + AC.getDescription());
        return AC;
    }
}
