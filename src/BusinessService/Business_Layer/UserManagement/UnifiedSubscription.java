package BusinessService.Business_Layer.UserManagement;

import BusinessService.Business_Layer.Trace.CoachPersonalPage;
import BusinessService.Business_Layer.Trace.PlayerPersonalPage;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

/**
 * This class allows a subscription to be multiple roles in the system
 */
public class UnifiedSubscription extends Subscription implements Observer {

    Player player;
    TeamOwner teamOwner;
    TeamManager teamManager;
    Coach coach;

    public UnifiedSubscription(String argUserName, String argPassword, String email) {
        super(argUserName, argPassword, email);
        this.player = null;
        this.teamOwner = null;
        this.teamManager = null;
        this.coach = null;
    }

    //region Role Setter

    /**
     * sets a new role to the subscription and adds the correct permissions for this role.
     * @param role an object representing the role to be added to the subscription
     */
    public void setNewRole(Object role){
        boolean correctRole = false;
        if(role instanceof Player) {
            player = (Player) role;
            permissions.add_default_player_or_coach_permission();
            //correctRole = true;
        }
        else if(role instanceof Coach) {
            coach = (Coach) role;
            permissions.add_default_player_or_coach_permission();
            //correctRole = true;
        }
        else if(role instanceof TeamManager) {
            teamManager = (TeamManager) role;
            // correctRole = true;
        }
        else if(role instanceof TeamOwner) {
            teamOwner = (TeamOwner) role;
            // correctRole = true;
        }
        /*
        if(correctRole)
            getPermissions().copyPermissions(role.getPermissions());*/
    }

    //endregion

    //region Boolean is a

    public boolean isAPlayer(){
        return player != null;
    }

    public boolean isACoach(){
        return coach!=null;
    }

    /**
     * returns true if the subscription is a team owner in the system and someone appointed them
     */
    public boolean isAnAppointedTeamOwner(){
        return teamOwner!=null && teamOwner.getAppointedByTeamOwner()!=null;
    }

    /**
     * returns true if the subscription is a team owner (not necessarily appointed by another user)
     */
    public boolean isATeamOwner(){
        return teamOwner!=null;
    }

    /**
     * returns true if the subscription is a team manager in the system and someone appointed them
     */
    public boolean isAnAppointedTeamManager(){
        return teamManager!=null && teamManager.getAppointedByTeamOwner()!=null;
    }

    /**
     * returns true if the subscription is a team manager (not necessarily appointed by another user)
     */
    public boolean isATeamManager(){
        return teamManager!=null;
    }

    //endregion

    //region Player functionality

    public String getPosition() {
        if(isAPlayer())
            return player.getPosition();
        return "";
    }

    public void setPosition(String position) {
        if(isAPlayer())
            player.setPosition(position);
    }

    public LocalDate getBirthday() {
        if(isAPlayer())
            return player.getBirthday();
        return null;
    }

    public void setBirthday(LocalDate birthday) {
        if(isAPlayer())
            player.setBirthday(birthday);
    }

    public PlayerPersonalPage getPlayerPersonalPage() {
        if(isAPlayer())
            return player.getPersonalPage();
        return null;
    }


    public void setPlayerPersonalPage(PlayerPersonalPage personalPage) {
        if(isAPlayer())
            player.setPersonalPage(personalPage);
    }

    //endregion

    //region Team Owner functionality

    public Subscription teamOwner_getAppointedByTeamOwner() {
        if(isAnAppointedTeamOwner())
            // will return null is the team owner was not appointed by anyone yet
            return teamOwner.getAppointedByTeamOwner();
        return null;
    }

    public void teamOwner_setAppointedByTeamOwner(Subscription appointedByTeamOwner) {
        if (isATeamOwner()) {
            if (appointedByTeamOwner == null) {
                //removing the subscription from being a team owner
                // first, remove the permissions
                permissions.removePermissionsOfTeamOwner();
                // then, assign this team owner to be null so the subscription is not a team owner anymore
                teamOwner = null;
            } else {
                teamOwner.setAppointedByTeamOwner(appointedByTeamOwner);
                permissions.add_default_owner_permission();
            }
        }
    }

    //endregion

    //region Team Manager functionality

    public Subscription teamManager_getAppointedByTeamOwner() {
        if(isAnAppointedTeamManager())
            // will return null is the team manager was not appointed by anyone yet
            return teamManager.getAppointedByTeamOwner();
        return null;
    }

    public void teamManager_setAppointedByTeamOwner(Subscription appointedByTeamOwner) {
        if(isATeamManager()) {
            if (appointedByTeamOwner == null) {
                //removing the subscription from being a team manager
                // assign this team manager to be null so the subscription is not a team owner anymore
                teamManager = null;
            } else {
                teamManager.setAppointedByTeamOwner(appointedByTeamOwner);
            }
        }
    }


    //endregion

    //region Coach functionality

    public String getQualification() {
        if(isACoach())
            return coach.getQualification();
        return null;
    }

    public void setQualification(String qualification) {
        if(isACoach())
            coach.setQualification(qualification);
    }

    public String getRoleInTeam() {
        if(isACoach())
            return coach.getRoleInTeam();
        return "";
    }

    public void setRoleInTeam(String roleInTeam) {
        if(isACoach())
            coach.setRoleInTeam(roleInTeam);
    }

    public CoachPersonalPage getCoachPersonalPage() {
        if(isACoach())
            return coach.getPersonalPage();
        return null;
    }

    public void setCoachPersonalPage(CoachPersonalPage personalPage) {
        if(isACoach())
            coach.setPersonalPage(personalPage);
    }

    //endregion


    //TODO observable

    @Override
    public void update(Observable o, Object arg) {
        this.alerts.add((String)arg);
    }

    @Override
    public String toString() {
        String result =
                "name: " + name + "\n" +
                 "email: " + email + "\n";
        if(player!=null)
            result = result + player.toString() + "\n";
        if(coach!=null)
            result = result + coach.toString() + "\n";
        if(teamManager!=null)
            result = result + teamManager.toString() + "\n";
        if(teamOwner!=null)
            result = result + teamOwner.toString();
        return result;
    }
}
