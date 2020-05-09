package BusinessService.Service_Layer;

// all Subscription in system

import BusinessService.Business_Layer.UserManagement.*;
import BusinessService.Enum.ActionStatus;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Enum.PermissionAction;
import BusinessService.Enum.Role;
import DB_Layer.logger;
import java.util.HashSet;

// to function that remove all Subscription


/**
 * This Class is responsible for connecting to the system exit system
 */
public class LogAndExitController{

    private static SubscriptionFactory factory = new SubscriptionFactory();

    /**
     * The purpose of this function is to register the user to the system.
     * The function checks that the user information is correct
     * @param arg_user_name -
     * @param arg_password -
     * @param arg_role -
     * @return comment print to user
     */
    public ActionStatus Registration(String arg_user_name, String arg_password, String arg_role, String email){
        ActionStatus AC;
        Role role_enum = DataManagement.returnEnum(arg_role);
        //The role does not exist in the system
        if(role_enum == null){
            AC = new ActionStatus(false, "The role does not exist in the system.");
        }
        else {
            String check_input = DataManagement.InputTest(arg_user_name,arg_password);
            //Invalid email, please enter a valid email
            if(!DataManagement.checkEmail(email)){
                AC =  new ActionStatus(false,  "Invalid email, please enter a valid email.");
            }
            // check all input
            else if( check_input!= null){
                AC = new ActionStatus(false, check_input);
            }
            // Subscription successfully added!
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
     * @param arg_user_name -
     * @param arg_password -
     * @return comment print to user
     */
    public ActionStatus Login(String arg_user_name, String arg_password) {
        ActionStatus AC;
        Subscription toLogin = DataManagement.containSubscription(arg_user_name);
        //Another subscription is connected to the system
        if (DataManagement.getCurrent() != null) {
            AC = new ActionStatus(false, "Another subscription is connected to the system.");
        }
        //does not exist in the system
        else if (toLogin == null) {
            AC = new ActionStatus(false, "The user " + arg_user_name + " does not exist in the system.");
        }
        //The password is incorrect
        else if (!toLogin.getPassword().equals(Subscription.getHash(arg_password))) {
            AC = new ActionStatus(false, "The password is incorrect.");
        }
        //Login successful
        else {
            DataManagement.setCurrent(toLogin);
            AC = new ActionStatus(true, roleInString(toLogin));
        }
        logger.log("Login attempt of user : " + arg_user_name + " " + AC.getDescription());
        return AC;
    }


    /**
     * function taht return the role
     * @param toLogin -
     * @return -
     */
    private String roleInString(Subscription toLogin) {
        if (toLogin instanceof Fan) {
            return "fan";
        } else if (toLogin instanceof Referee) {
            return "referee";
        } else if (toLogin instanceof UnionRepresentative) {
            return "unionrepresentative";
        } else if (toLogin instanceof UnifiedSubscription) {
            if (((UnifiedSubscription) toLogin).isAPlayer()) {
                return "player";
            } else if (((UnifiedSubscription) toLogin).isACoach()) {
                return "coach";
            } else if (((UnifiedSubscription) toLogin).isATeamManager()) {
                return "teammanager";
            } else if (((UnifiedSubscription) toLogin).isATeamOwner()) {
                return "teamowner";
            }
        }
        return "";
    }


    /**
     * check if tole exist in current
     * @param role -
     * @return -
     */
    public ActionStatus haveRole(String role){
        ActionStatus AC;
        Subscription sub = DataManagement.getCurrent();
        if( sub!= null){
            if (sub instanceof UnifiedSubscription){
                if(role.equals("player")){
                    AC = new ActionStatus(((UnifiedSubscription) sub).isAPlayer(), "");
                }else if(role.equals("coach")){
                    AC = new ActionStatus(((UnifiedSubscription) sub).isACoach(), "");
                }else if(role.equals("teammanager")){
                    AC = new ActionStatus(((UnifiedSubscription) sub).isATeamManager(), "");
                }else if(role.equals("teamowner")){
                    AC = new ActionStatus(((UnifiedSubscription) sub).isATeamOwner(), "");
                }else{
                    AC = new ActionStatus(false, "One of the details you entered is incorrect.");
                }
            }else{
                AC = new ActionStatus(false, "One of the details you entered is incorrect.");
            }
        }
        //One of the details you entered is incorrect
        else{
            AC = new ActionStatus(false, "One of the details you entered is incorrect.");
        }
        return AC;
    }

    /**
     * The function allows logging off of a user connected to the system
     * @param arg_user_name -
     * @return comment print to user
     */
    public ActionStatus Exit(String arg_user_name){
        ActionStatus AC;
        if(DataManagement.getCurrent() != null){
            //Successfully disconnected from the system
            if(DataManagement.getCurrent().getUserName().equals(arg_user_name)){
                DataManagement.setCurrent(null);
                AC = new ActionStatus(true,  "Successfully disconnected from the system.");
            }
            //One of the details you entered is incorrect
            else{
                AC = new ActionStatus(false, "One of the details you entered is incorrect.");
            }
        }
        //One of the details you entered is incorrect
        else{
            AC = new ActionStatus(false, "One of the details you entered is incorrect.");
        }
        logger.log("Exit attempt of user : "+ arg_user_name+" "+ AC.getDescription());
        return AC;
    }


    /**
     * Only the administrator can delete  users
     * @return  ActionStatus
     */
    public ActionStatus RemoveSubscription(String userName){
        ActionStatus AC;
        if(!ConstraintsCorrectness(userName)){
            AC =  new ActionStatus(false,  "The system constraints do not allow this subscription to be deleted.");
        }
        else if(DataManagement.containSubscription(userName) == null){
            AC =  new ActionStatus(false,  "There is no subscription with this username in the system.");
        }
        else if(!(DataManagement.getCurrent().getPermissions().check_permissions((PermissionAction.Removing_Subscriptions)) || DataManagement.getCurrent().getPermissions().check_permissions((PermissionAction.remove_referee)))){
            AC =  new ActionStatus(false,  "You are not authorized to perform this action.");
        }
        else {
            if(DataManagement.getCurrent().getUserName().equals(userName)){
                DataManagement.setCurrent(null);
            }
            DataManagement.removeSubscription(userName);
            AC = new ActionStatus(true, "the transaction completed successfully.");
        }
        logger.log("Remove Subscription attempt of user : "+userName+" "+AC.getDescription());
        return AC;
    }

    /**
     * Main function that checks system constraints
     * @param userName -
     * @return boolean
     */
    private boolean ConstraintsCorrectness(String userName){
        return ( numberSystemAdministrator(userName) && TeamOwnerForTeam(userName));
    }

    /**
     * Check if there is more than one administrator
     * @param userName -
     * @return false - error the action illegal
     */
    private boolean numberSystemAdministrator(String userName){
        /*
        HashSet<SystemAdministrator> list =  DataManagement.getSystemAdministratorsList();
        if(list.size() == 1){
            for (SystemAdministrator user: list) {
                if(user.getUserName().equals(userName)){
                    // the action illegal
                    return false;
                }
            }

        }

         */
        return true;
    }

    /**
     * Check that there is another team owner for a team
     * @param userName -
     * @return boolean
     */
    private boolean TeamOwnerForTeam(String userName){
        Subscription teamOwner = DataManagement.containSubscription(userName);
        if(teamOwner instanceof UnifiedSubscription && ((UnifiedSubscription)teamOwner).isAnAppointedTeamOwner())
            return searchTeamOwner((UnifiedSubscription)teamOwner);
        return true;

    }

    /**
     * find all team owners in team and check if the team owner
     * that wants to be removed is the only one in the team
     * @param teamOwner  -
     * @return boolean
     */
    private boolean searchTeamOwner(UnifiedSubscription teamOwner) {
        //TODO - FIX IN db; CHECK IN TEAM ASSET IF USER EXISTS AS TEAM OWNER.
        HashSet<Team> list =  null; //DataManagement.getListTeam();
        for (Team team: list) {
            HashSet<UnifiedSubscription> teamOwnerHash = team.getListTeamOwner();
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
    public ActionStatus addRoleToUser(String role, String password) {
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
                if (sub instanceof UnifiedSubscription) {
                    factory.addRoleToUnifiedSubscription((UnifiedSubscription) sub, desiredRole);
                    AC = new ActionStatus(true, "The role " + role + " was added successfully to your account");
                } else { // NOT one of the four roles who are allowed to be parallel users
                    AC = new ActionStatus(false, "You are not authorized to perform this action.");
                }

            }
        }
        logger.log("Add Subscription attempt of user : " + DataManagement.getCurrent().getUserName() + role + " " + AC.getDescription());
        return AC;
    }

    public Subscription createUserByType(String arg_user_name,String arg_password,String role_enum,String email){
        return factory.Create(arg_user_name, arg_password, DataManagement.returnEnum(role_enum), email);
    }
}
