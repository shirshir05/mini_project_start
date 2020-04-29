package BusinessService.Service_Layer;

import BusinessService.Enum.ActionStatus;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.*;
import BusinessService.Enum.PermissionAction;
import DB_Layer.logger;
import Presentation_Layer.Spelling;
import java.util.ArrayList;
import java.util.Observable;

public class TeamController {


    /**
     * The function is managed by the team owner . The team owner sends an alert to all Union Representatives
     *  to request their permission to open a group. The team opens with a status 2
     * @param arg_name - name team
     * @param arg_field - name filed
     * @return - ActionStatus
     */
    public ActionStatus RequestCreateTeam(String arg_name, String arg_field){
        ActionStatus AC;
        Subscription currentUser = DataManagement.getCurrent();
        if (arg_name != null && !arg_name.isEmpty() && isATeamOwner(currentUser)) {
            ArrayList<UnionRepresentative> union = DataManagement.getUnionRepresentatives();
            boolean flag = false;
            for (UnionRepresentative rep : union) {
                rep.addAlert("teamOwner:" + currentUser.getUserName() + "| Team;" + arg_name);
                flag = true;
            }
            if (flag){
                AC = CreateTeam(arg_name,arg_field);
            }
            else{
                AC = new ActionStatus(false, "The operation was not performed because no union representative in system.");
            }
        }else if(arg_name == null || arg_name.isEmpty()){
            AC = new ActionStatus(false, "Team name cannot be empty.");
        }
        else{
            AC = new ActionStatus(false, "You are not allowed to create a Team.");
        }

        return AC;
    }


    /**
     * help function to request team owner to open group
     * When the team ownert  wants to create a team,
     * a team is created in the system with status 2.
     * @param arg_name - name team
     * @param arg_field - name filed
     * @return - ActionStatus
     */
    protected ActionStatus CreateTeam(String arg_name, String arg_field) {
        ActionStatus AC;
        if (arg_name==null || arg_name.isEmpty() ||arg_field==null || arg_field.isEmpty() ){
            AC = new ActionStatus(false, "One of the parameters is null");
        }
        else if (!(DataManagement.getCurrent().getPermissions().check_permissions((PermissionAction.Edit_team))) && !(DataManagement.getCurrent() instanceof TeamOwner)) {
            AC=  new ActionStatus(false,"You are not allowed to perform actions on the group.");
        }
        else if(DataManagement.findTeam(arg_name) != null){
            AC= new ActionStatus(false,"The Team already exists in the system.");
        }
        else{
            Team new_team = new Team(arg_name, arg_field);
            new_team.getPersonalPage().addPermissionToEdit(DataManagement.getCurrent().getUserName());
            new_team.changeStatus(2);
            DataManagement.addToListTeam((new_team));
            //add the union representatives to the observers of the budget of the team:
            ArrayList<UnionRepresentative> unionReps = DataManagement.getUnionRepresentatives();
            Observable budget = new_team.getBudget();
            for(UnionRepresentative s: unionReps){
                budget.addObserver(s);
            }
            AC = new_team.EditTeamOwner((TeamOwner) DataManagement.getCurrent(),1);
            Spelling.updateDictionary("team: " + arg_name);
        }
        logger.log("Create Team: "+arg_name+"-"+AC.getDescription());
        return AC;
    }


    /**
     * TODO - ORTAL
     * @param currentUser
     * @return
     */
    private boolean isATeamOwner(Subscription currentUser) {
        return currentUser instanceof TeamOwner
                || (currentUser instanceof UnifiedSubscription && ((UnifiedSubscription)currentUser).isATeamOwner());
    }

    /**
     *The union representative runs the function. The union representative receives the message of the opening of the
     *  team to which he wants to reply. If the message matches the status the team changes to 1
     * @param theAlert - message open  team
     * @return ActionStatus
     */
    public ActionStatus ApproveCreateTeamAlert(String theAlert){
        Subscription currentUser = DataManagement.getCurrent();
        ActionStatus AC = new ActionStatus(false,"The action was not performed." );
        if (currentUser instanceof UnionRepresentative) {
            boolean flag = false;
            for (String alert : currentUser.getAlerts()) {
                if (alert.equals(theAlert)) {
                    String teamName = theAlert.substring(theAlert.indexOf(';')+1);
                    Team team = DataManagement.findTeam(teamName);
                    if (team == null){
                        AC = new ActionStatus(false,"Error, Team does not exist in system -> message is no longer relevant." );
                    }
                    else{
                        team.changeStatus(1);
                        AC = new ActionStatus(true,"Team status successfully changed to 1." );
                    }
                    flag = true;
                    DeleteCreateTeamRequest(theAlert);
                }
            }
            if(!flag){
                AC = new ActionStatus(false,"There is no appropriate message in the message alert" );
            }
        }
        else{
            AC = new ActionStatus(false,"You are not authorized to perform this action." );
        }
        return AC;
    }

    /**
     * The Union Representatives  answered a group opening alert, so the alert is
     * deleted from all other association representatives
     * @param teamName - name of team
     */
    public void DeleteCreateTeamRequest(String teamName){
        if (teamName!=null) {
            ArrayList<UnionRepresentative> union = DataManagement.getUnionRepresentatives();
            for (UnionRepresentative u : union) {
                for (String alert : u.getAlerts()) {
                    if (alert.contains(teamName)) {
                        u.getAlerts().remove(alert);
                    }
                }
            }
        }

    }




    /**
     * The function allows a player to be added to a team or to remove a player from the team
     * @param name_team - name team
     * @param user_name - name change
     * @param add_or_remove - add = 1 , remove = 0
     * @return ActionStatus
     */
    public ActionStatus AddOrRemovePlayer(String name_team, String user_name, int add_or_remove) {
        ActionStatus AC;
        String ans = CheckInputEditTeam(name_team, user_name);
        Subscription requestedPlayerToAdd = DataManagement.containSubscription(user_name);
        Team team = DataManagement.findTeam(name_team);
        if (ans != null) {
            AC =  new ActionStatus(false,ans);
        }
        else if(!isAPlayer(requestedPlayerToAdd)) {
            AC = new ActionStatus(false, "The username is not defined as a player on the system.");
        }
        else if(team != null){
            AC =  team.addOrRemovePlayer((Player) DataManagement.containSubscription(user_name), add_or_remove);
        }
        else {
            AC = new ActionStatus(false, "Cannot find team");
        }
        logger.log("Add Or Remove Player to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }

    private boolean isAPlayer(Subscription requestedPlayerToAdd) {
        return (requestedPlayerToAdd instanceof Player) || (requestedPlayerToAdd instanceof UnifiedSubscription && ((UnifiedSubscription)requestedPlayerToAdd).isAPlayer());
    }

    /**
     * The function allows a player to be added to a team or to remove a player from the team.
     * @param name_team - name team
     * @param coach_add - name change
     * @param add_or_remove - add = 1 , remove = 0
     * @return ActionStatus
     */
    public ActionStatus AddOrRemoveCoach(String name_team, String coach_add, int add_or_remove) {
        ActionStatus AC;
        String ans = CheckInputEditTeam(name_team, coach_add);
        Subscription requestedCoachToAdd = DataManagement.containSubscription(coach_add);
        Team team = DataManagement.findTeam(name_team);
        if (ans != null) {
            AC = new ActionStatus(false,ans);
        }
        else if(!isACoach(requestedCoachToAdd)){
            AC =  new ActionStatus(false, "The username is not defined as a Coach on the system.");
        }
        else if(team != null){
            AC = team.AddOrRemoveCoach((Coach) DataManagement.containSubscription(coach_add), add_or_remove);
        }
        else {
            AC = new ActionStatus(false, "Cannot find team");
        }
        logger.log("Add Or Remove Coach to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }

    /**
     * Check if the user define a coach in system
     * @param coach - name
     * @return - true or false
     */
    private boolean isACoach(Subscription coach) {
        return (coach instanceof Coach) || (coach instanceof UnifiedSubscription && ((UnifiedSubscription)coach).isACoach());
    }


    /**
     * If the add_or_remove is 1 the function allows a new group owner to be set to the system
     * If the add_or_remove is 0 the function allows a team owner to explain a group owner from the system eight by him
     * @param name_team - name team
     * @param TeamOwner - name change
     * @param add_or_remove - add = 1 , remove = 0
     * @return ActionStatus
     */
    public ActionStatus AddOrRemoveTeamOwner(String name_team, String TeamOwner, int add_or_remove) {
        ActionStatus AC;
        String ans = CheckInputEditTeam(name_team, TeamOwner);
        Subscription requestedTeamOwnerToAdd = DataManagement.containSubscription(TeamOwner);
        if (ans != null) {
            AC =  new ActionStatus(false, ans);
        }
        else if (!isATeamOwner(requestedTeamOwnerToAdd)) {
            AC =  new ActionStatus(false, "The username is not defined as a Team Owner on the system.");
        }
        else if (add_or_remove == 1) {  // add teamOwner to team
            Subscription teamOwner = DataManagement.containSubscription(TeamOwner);
            Team team = DataManagement.findTeam(name_team);
            Subscription appointed = ((TeamOwner)teamOwner).getAppointedByTeamOwner();
            if (appointed != null) {
                AC =  new ActionStatus(false, "You are already set as a team owner.");
            }else{
                ((TeamOwner) teamOwner).setAppointedByTeamOwner(DataManagement.getCurrent());
                AC = team.EditTeamOwner((TeamOwner) teamOwner, add_or_remove);
            }
        }
        else if (add_or_remove == 0) {   // remove teamOwner to team
            Subscription teamOwner = DataManagement.containSubscription(TeamOwner);
            Team team = DataManagement.findTeam(name_team);
            Subscription appointed = ((TeamOwner) teamOwner).getAppointedByTeamOwner();
            if (appointed != null && DataManagement.containSubscription(appointed.getUserName()) != null) {
                // The person responsible for appointing the team is still in the system
                if (appointed != DataManagement.getCurrent()) {
                    AC = new ActionStatus(false, "You do not appoint the team owner and therefore cannot remove them from the team");
                }else{
                    ((TeamOwner) teamOwner).setAppointedByTeamOwner(null);
                    AC = team.EditTeamOwner((TeamOwner) teamOwner, add_or_remove);
                }
            }else{
                ((TeamOwner) teamOwner).setAppointedByTeamOwner(null);
                AC = team.EditTeamOwner((TeamOwner) teamOwner, add_or_remove);
            }
        }
        else{
            AC = new ActionStatus(false, "The number entered is incorrect.");
        }
        logger.log("Add Or Remove Team Owner to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }


    /**
     * If the add_or_remove is 1 the function allows a new group owner to be set to the system
     * If the add_or_remove is 0 the function allows a team owner to explain a group owner from the system eight by him
     *
     * @param name_team - name team
     * @param TeamManager - name change
     * @param add_or_remove - add = 1 , remove = 0
     * @return ActionStatus
     */
    public ActionStatus AddOrRemoveTeamManager(String name_team, String TeamManager, int add_or_remove) {
        ActionStatus AC;
        String ans = CheckInputEditTeam(name_team, TeamManager);
        Subscription requestedTeamManagerToAdd = DataManagement.containSubscription(TeamManager);
        if (ans != null) {
            AC =  new ActionStatus(false, ans);
        }
        else if (!(requestedTeamManagerToAdd instanceof TeamManager)){
            AC =  new ActionStatus(false, "The username is not defined as a Team Manager on the system.");
        }
        else if (add_or_remove == 1) {// add teamOwner to team
            Subscription teamManager =DataManagement.containSubscription(TeamManager);
            Team team =DataManagement.findTeam(name_team);
            Subscription appointed = ((TeamManager) teamManager).getAppointedByTeamOwner();
            if (appointed != null) {
                AC = new ActionStatus(false, "You are already set as a team Manager.");
            }
            else{
                ((TeamManager) teamManager).setAppointedByTeamOwner(DataManagement.getCurrent());
                AC = team.EditTeamManager((TeamManager) teamManager, add_or_remove);
            }

        }
        else if (add_or_remove == 0) {// remove teamOwner to team
            Subscription teamManager =DataManagement.containSubscription(TeamManager);
            Team team =DataManagement.findTeam(name_team);
            Subscription appointed = ((TeamManager) teamManager).getAppointedByTeamOwner();
            if (appointed != null && DataManagement.containSubscription(appointed.getUserName()) != null) {
                // The person responsible for appointing the team is still in the system
                if (appointed != DataManagement.getCurrent()) {
                    AC = new ActionStatus(false, "You do not appoint the team owner and therefore cannot remove them from the team");
                }
                else{
                    ((TeamManager) teamManager).setAppointedByTeamOwner(null);
                    AC = team.EditTeamManager((TeamManager) teamManager, add_or_remove);
                }

            }else{
                ((TeamManager) teamManager).setAppointedByTeamOwner(null);
                AC = team.EditTeamManager((TeamManager) teamManager, add_or_remove);
            }
        }
        else{
            AC = new ActionStatus(false, "The number entered is incorrect.");
        }
        logger.log("Add Or Remove Team Manager to Team: "+name_team+"-"+AC.getDescription());
        return AC;

    }

    /**
     * The function checks the permissions to edit a set and the parameters proper
     *
     * @param name_team - name team
     * @param user_name - user change
     * @return - null if all OK
     */
    public String CheckInputEditTeam(String name_team, String user_name) {
        String value=null;
        if (name_team==null ||name_team.isEmpty() || user_name==null || user_name.isEmpty()){
            value= "One of the parameters is empty";
        } else if ((!DataManagement.getCurrent().getPermissions().check_permissions((PermissionAction.Edit_team)))) {
            value= "You are not allowed to perform actions on the group.";
        } else if (DataManagement.findTeam(name_team) == null) {
            value= "The Team does not exist in the system.";
        } if (DataManagement.findTeam(name_team) != null) {
            Team team = DataManagement.findTeam(name_team);
            if(!(team.checkIfObjectInTeam(DataManagement.getCurrent())) && !(DataManagement.getCurrent() instanceof SystemAdministrator)){
                value= "You are not allowed to perform actions in this group.";
            }
        } else if (DataManagement.containSubscription(user_name) == null) {
            value= "The username does not exist on the system.";
        }
        return value;
    }


    /**
     * A function that allows the system administrator and the team owner to change the status of the team
     * 0 - close
     * 1- open
     * -1  -  permanently close
     * 2 -wait permission union representative
     * @param name_team - name team
     * @param status -
     * @return - ActionStatus
     */
    public ActionStatus ChangeStatusTeam(String name_team, int status) {
        ActionStatus AC;
        if (status != 0 && status != 1 && status != -1) {
            AC =  new ActionStatus(false,  "The action is invalid.");
        }
        else if( CheckInputEditTeam(name_team, DataManagement.getCurrent().getUserName()) != null){
            AC = new ActionStatus(false,  CheckInputEditTeam(name_team, DataManagement.getCurrent().getUserName()));
        }
        else if (DataManagement.findTeam(name_team).getStatus() == -1) {
            AC = new ActionStatus(false,  "The team is permanently closed.");
        }
        else if (status == 0){
            if (!(DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Close_team))) {
                AC = new ActionStatus(false, "You are not allowed to close a team.");
            }
            else {
                AC = DataManagement.findTeam(name_team).changeStatus(status);
            }
        }
        else if(status == -1) {
            if (!(DataManagement.getCurrent() instanceof SystemAdministrator)) {
                AC = new ActionStatus(false, "You are not authorized to perform this action.");
            } else {
                    AC = DataManagement.findTeam(name_team).changeStatus(status);
            }
        }
        else{
            AC = DataManagement.findTeam(name_team).changeStatus(status);
        }

        logger.log("Change Status to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }


    /**
     * The tea, owner can add or remove filed from the team
     * @param name_team - team name
     * @param TeamAsset -
     * @param add_or_remove -
     * @return ActionStatus
     */
    public ActionStatus AddOrRemoveTeamsAssets(String name_team, String TeamAsset, int add_or_remove) {
        ActionStatus AC ;
        Team team = DataManagement.findTeam(name_team);
        String ans = CheckInputEditTeam(name_team, DataManagement.getCurrent().getUserName());
        if( ans!= null){
            AC = new ActionStatus(false, ans);
        }
        if (team!=null && TeamAsset!=null && (add_or_remove == 1 || add_or_remove == 0) && DataManagement.getCurrent() instanceof TeamOwner){
            if (add_or_remove==1) {
                team.setAsset(TeamAsset);
                AC = new ActionStatus(true, "The asset was added to the team");
            }
            else{
                if (!team.getTeamAssets().contains(TeamAsset)){
                    AC = new ActionStatus(false, "The team doesnt contains this asset");
                }
                else{
                    team.removeTeamAssets(TeamAsset);
                    AC = new ActionStatus(true, "The asset was deleted from the team");
                }
            }
        }
        else{
            if (team == null){AC = new ActionStatus(false, "There is no such team in the system");}
            else if (TeamAsset == null){AC = new ActionStatus(false, "This asset is null");}
            else if (add_or_remove !=1 && add_or_remove != 0) {AC = new ActionStatus(false, "Choose 1 or 0 only");}
            else { AC = new ActionStatus(false, "You are not a team owner");}
        }
        return AC;
    }



}
