package BusniesServic.Service_Layer;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;
import Presentation_Layer.Spelling;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.Observable;

public class TeamController {

    public TeamController(){}

    /**
     * @param arg_name
     * @return
     */
    public boolean RequestCreateTeam(String arg_name, String arg_field){
        boolean flag=false;
        if (DataManagement.getCurrent() instanceof TeamOwner && arg_name!=null) {
            ArrayList<UnionRepresentative> union = DataManagement.getUnionRepresentatives();
            for (UnionRepresentative rep : union) {
                rep.addAlert("teamOwner:" + DataManagement.getCurrent() + "| Team;" + arg_name);
                flag = true;
            }
        }
        if (flag==true){
            this.CreateTeam(arg_name,arg_field);
        }
        return flag;
    }

    public boolean ApproveCreateTeamAlert(String theAlert,String repName){
        UnionRepresentative rep = (UnionRepresentative)DataManagement.getSubscription(repName);
        boolean flag = false;
        if (rep!=null) {
            for (String alert : rep.getAlerts()) {
                if (alert.equals(theAlert)) {
                    String teamName = theAlert.substring(theAlert.indexOf(';')+1);
                    DataManagement.findTeam(teamName).changeStatus(1);
                    flag = true;
                }
            }
        }
        if (flag){
            DeleteCreateTeamRequest(theAlert);
        }
        return flag;
    }

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
     * Once the association representative is approved, a Team is created in the system.
     * only TeamOwner can create team
     *
     * @param arg_name
     * @param arg_field
     * @return
     */
    public ActionStatus CreateTeam(String arg_name, String arg_field) {
        ActionStatus AC = null;
        if (arg_name==null || arg_field==null){
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
     * The function allows a player to be added to a team or to remove a player from the team
     *
     * @param name_team
     * @param user_name
     * @param add_or_remove
     * @return
     */
    public ActionStatus AddOrRemovePlayer(String name_team, String user_name, int add_or_remove) {
        ActionStatus AC = null;
        String ans = CheckInputEditTeam(name_team, user_name);
        if (ans != null) {
            AC =  new ActionStatus(false,ans);
        }
        else if(!(DataManagement.containSubscription(user_name) instanceof Player)) {
            AC = new ActionStatus(false, "The username is not defined as a player on the system.");
        }
        else if(DataManagement.findTeam(name_team) != null){
            AC =  DataManagement.findTeam(name_team).addOrRemovePlayer((Player) DataManagement.containSubscription(user_name), add_or_remove);
        }
        logger.log("Add Or Remove Player to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }

    /**
     * The function allows a player to be added to a team or to remove a player from the team.
     *
     * @param name_team
     * @param coach_add
     * @param add_or_remove
     * @return
     */
    public ActionStatus AddOrRemoveCoach(String name_team, String coach_add, int add_or_remove) {
        ActionStatus AC = null;
        String ans = CheckInputEditTeam(name_team, coach_add);
        if (ans != null) {
            AC = new ActionStatus(false,ans);
        }
        else if(!( DataManagement.containSubscription(coach_add) instanceof Coach)){
            AC =  new ActionStatus(false, "The username is not defined as a Coach on the system.");
        }
        else if(DataManagement.findTeam(name_team) != null){
            AC = DataManagement.findTeam(name_team).AddOrRemoveCoach((Coach) DataManagement.containSubscription(coach_add), add_or_remove);
        }

        logger.log("Add Or Remove Coach to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }


    /**
     * If the add_or_remove is 1 the function allows a new group owner to be set to the system
     * If the add_or_remove is 0 the function allows a team owner to explain a group owner from the system eight by him
     *
     * @param name_team
     * @param TeamOwner
     * @param add_or_remove
     * @return
     */
    public ActionStatus AddOrRemoveTeamOwner(String name_team, String TeamOwner, int add_or_remove) {
        ActionStatus AC = null;
        String ans = CheckInputEditTeam(name_team, TeamOwner);
        if (ans != null) {
            AC =  new ActionStatus(false, ans);
        }
        else if (!(DataManagement.containSubscription(TeamOwner) instanceof BusniesServic.Business_Layer.UserManagement.TeamOwner)) {
            AC =  new ActionStatus(false, "The username is not defined as a Team Owner on the system.");
        }
        // add teamOwner to team
        else if (add_or_remove == 1) {
            Subscription teamOwner = DataManagement.containSubscription(TeamOwner);
            Team team = DataManagement.findTeam(name_team);
            Subscription appointed = ((BusniesServic.Business_Layer.UserManagement.TeamOwner) teamOwner).getAppointedByTeamOwner();
            if (appointed != null) {
                AC =  new ActionStatus(false, "You are already set as a team owner.");
            }else{
                ((BusniesServic.Business_Layer.UserManagement.TeamOwner) teamOwner).setAppointedByTeamOwner(DataManagement.getCurrent());
                AC = team.EditTeamOwner((BusniesServic.Business_Layer.UserManagement.TeamOwner) teamOwner, add_or_remove);
            }

        }
        // remove teamOwner to team
        else if (add_or_remove == 0) {
            Subscription teamOwner = DataManagement.containSubscription(TeamOwner);
            Team team = DataManagement.findTeam(name_team);
            Subscription appointed = ((BusniesServic.Business_Layer.UserManagement.TeamOwner) teamOwner).getAppointedByTeamOwner();
            ((BusniesServic.Business_Layer.UserManagement.TeamOwner) teamOwner).setAppointedByTeamOwner(null);
            AC = team.EditTeamOwner((BusniesServic.Business_Layer.UserManagement.TeamOwner) teamOwner, add_or_remove);
        }

        logger.log("Add Or Remove Team Owner to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }


    /**
     * If the add_or_remove is 1 the function allows a new group owner to be set to the system
     * If the add_or_remove is 0 the function allows a team owner to explain a group owner from the system eight by him
     *
     * @param name_team
     * @param TeamManager
     * @param add_or_remove
     * @return
     */
    public ActionStatus AddOrRemoveTeamManager(String name_team, String TeamManager, int add_or_remove) {
        ActionStatus AC = null;
        String ans = CheckInputEditTeam(name_team, TeamManager);
        if (ans != null) {
            AC =  new ActionStatus(false, ans);
        }
        // add teamOwner to team
        else if (add_or_remove == 1) {
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
        // remove teamOwner to team
        else if (add_or_remove == 0) {
            Subscription teamManager =DataManagement.containSubscription(TeamManager);
            Team team =DataManagement.findTeam(name_team);
            Subscription appointed = ((TeamManager) teamManager).getAppointedByTeamOwner();
            ((TeamManager) teamManager).setAppointedByTeamOwner(null);
            AC = team.EditTeamManager((TeamManager) teamManager, add_or_remove);
        }
        logger.log("Add Or Remove Team Manager to Team: "+name_team+"-"+AC.getDescription());
        return AC;

    }

    /**
     * The function checks the permissions to edit a set and the parameters proper
     *
     * @param name_team
     * @param user_name
     * @return
     */
    public String CheckInputEditTeam(String name_team, String user_name) {
        String value=null;
        if (name_team==null || user_name==null){
            value= "One of the parameters is null";
        } else if ((DataManagement.getCurrent().getPermissions().check_permissions((PermissionAction.Edit_team)) == false)) {
            value= "You are not allowed to perform actions on the group.";
        } else if (DataManagement.findTeam(name_team) == null) {
            value= "The Team does not exist in the system.";
        } else if (!DataManagement.findTeam(name_team).checkIfObjectInTeam(DataManagement.getCurrent()) && !(DataManagement.getCurrent() instanceof SystemAdministrator)) {
            value= "You are not allowed to perform actions in this group.";
        } else if (DataManagement.containSubscription(user_name) == null) {
            value= "The username does not exist on the system.";
        } return value;
    }


    /**
     * A function that allows the system administrator and the group owner to change the status of the group
     * 0 - close
     * 1- open
     * -1  -  permanently close
     *
     * @param name_team
     * @param status
     * @return
     */
    public ActionStatus ChangeStatusTeam(String name_team, int status) {
        ActionStatus AC = null;
        if (status != 0 && status != 1 && status != -1 && status != 2) {
            AC =  new ActionStatus(false,  "The action is invalid.");
        }
        else if( CheckInputEditTeam(name_team, DataManagement.getCurrent().getUserName()) != null){
            AC = new ActionStatus(false,  CheckInputEditTeam(name_team, DataManagement.getCurrent().getUserName()));
        }
        else if (DataManagement.findTeam(name_team).getStatus() == -1) {
            AC = new ActionStatus(false,  "The team is permanently closed.");
        }
        else if (status==0){
            if (!(DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Close_team))) {
                AC = new ActionStatus(false, "You are not allowed to close a team.");
            }
            else {
                AC = DataManagement.findTeam(name_team).changeStatus(status);
            }
        }
        else if(status==-1) {
            if (!(DataManagement.getCurrent() instanceof SystemAdministrator)) {
                AC = new ActionStatus(false, "You are not authorized to perform this action.");
            } else {
                    AC = DataManagement.findTeam(name_team).changeStatus(status);
            }
        }
        else {
            AC = DataManagement.findTeam(name_team).changeStatus(status);
        }
        logger.log("Change Status to Team: "+name_team+"-"+AC.getDescription());
        return AC;
    }

    public ActionStatus AddOrRemoveTeamsAssets(String name_team, String TeamAsset, int add_or_remove) {
        ActionStatus AC = null;
        Team t = DataManagement.findTeam(name_team);
        if (t!=null && TeamAsset!=null && (add_or_remove==1 || add_or_remove==0) && DataManagement.getCurrent() instanceof TeamOwner){
            if (add_or_remove==1) {
                t.setAsset(TeamAsset);
                AC = new ActionStatus(true, "The asset was added to the team");
            }
            else if (add_or_remove==0){
                if (!t.getTeamAssets().contains(TeamAsset)){
                    AC = new ActionStatus(false, "The team doesnt contains this asset");
                }
                else{
                    t.removeTeamAssets(TeamAsset);
                    AC = new ActionStatus(true, "The asset was deleted from the team");
                }
            }
        }
        else{
            if (t==null){AC = new ActionStatus(false, "There is no such team in the system");}
            else if (TeamAsset==null){AC = new ActionStatus(false, "This asset is null");}
            else if (add_or_remove!=1 && add_or_remove!=0){AC = new ActionStatus(false, "Choose 1 or 0 only");}
            else if (!(DataManagement.getCurrent() instanceof TeamOwner)){AC = new ActionStatus(false, "You are not a team owner");}
        }
        return AC;

    }



}
