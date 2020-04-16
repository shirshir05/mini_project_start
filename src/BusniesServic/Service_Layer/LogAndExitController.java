package BusniesServic.Service_Layer;

// all Subscription in system

import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Enum.PermissionAction;
import BusniesServic.Enum.Role;
import DB_Layer.logger;

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
            HashSet<Team> list =  DataManagement.getListTeam();
            for (Team team: list) {
                HashSet<TeamOwner> teamOwnerHash = team.getListTeamOwner();
                if(teamOwnerHash.size() == 1){
                    if(teamOwnerHash.contains(teamOwner)){
                        return false;
                    }
                }
            }
        }
        return true;

    }


    /**
     * Add user
     * The team owner can be a player, coach and manager
     * @param role
     * @return
     */
    public ActionStatus addUser(String role,String password){
        ActionStatus AC ;
        if(!DataManagement.getCurrent().getPassword().equals(Subscription.getHash(password))){
            AC = new ActionStatus(true, "The password does not match the password entered in the system.");
        }
        Role roleEnum = DataManagement.returnEnum(role);
        if(roleEnum == null){
            AC = new ActionStatus(false, "The role does not exist in the system.");
        }
        else{
            Subscription sub = DataManagement.getCurrent();
            if(sub instanceof Player ){
                if(roleEnum.equals(Role.Coach) || roleEnum.equals(Role.TeamManager) || roleEnum.equals(Role.TeamOwner)){
                    Registration(DataManagement.getCurrent().getUserName() + role,password,role,DataManagement.getCurrent().getEmail());
                    AC = new ActionStatus(true, "You have signed up for the system with a role " + role  +"and username" + DataManagement.getCurrent().getUserName() + role);
                }else{
                    AC = new ActionStatus(false, "You are not authorized to perform the action.");
                }

            } else if(sub instanceof Coach ){
                if(roleEnum.equals(Role.Player) || roleEnum.equals(Role.TeamManager) || roleEnum.equals(Role.TeamOwner)){
                    Registration(DataManagement.getCurrent().getUserName() + role,password,role,DataManagement.getCurrent().getEmail());
                    AC = new ActionStatus(true, "You have signed up for the system with a role " + role  +"and username" + DataManagement.getCurrent().getUserName() + role);

                }else{
                    AC = new ActionStatus(false, "You are not authorized to perform the action.");
                }

            } else if(sub instanceof TeamOwner) {
                if(roleEnum.equals(Role.Player) || roleEnum.equals(Role.TeamManager) || roleEnum.equals(Role.Coach)){
                    Registration(DataManagement.getCurrent().getUserName() + role,password,role,DataManagement.getCurrent().getEmail());
                    AC = new ActionStatus(true, "You have signed up for the system with a role " + role  +"and username" + DataManagement.getCurrent().getUserName() + role);

                }else{
                    AC = new ActionStatus(false, "You are not authorized to perform the action.");
                }

            }else if( sub instanceof TeamManager){
                if(roleEnum.equals(Role.Coach) || roleEnum.equals(Role.Player) || roleEnum.equals(Role.TeamOwner)){
                    Registration(DataManagement.getCurrent().getUserName() + role,password,role,DataManagement.getCurrent().getEmail());
                    AC = new ActionStatus(true, "You have signed up for the system with a role " + role  +"and username" + DataManagement.getCurrent().getUserName() + role);
                }else{
                    AC = new ActionStatus(false, "You are not authorized to perform the action.");
                }
            }else{
                AC = new ActionStatus(false, "You are not authorized to perform the action.");
            }

        }
        logger.log("Add Subscription attempt of user : "+DataManagement.getCurrent().getUserName() + role+" "+AC.getDescription());
        return AC;

    }



}
