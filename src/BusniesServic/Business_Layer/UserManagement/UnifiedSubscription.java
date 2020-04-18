package BusniesServic.Business_Layer.UserManagement;

import BusniesServic.Business_Layer.Trace.CoachPersonalPage;
import BusniesServic.Business_Layer.Trace.PlayerPersonalPage;
import BusniesServic.Enum.PermissionAction;

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

    public UnifiedSubscription(String argUserName, String argPassword, String email, Player player, TeamOwner teamOwner, TeamManager teamManager, Coach coach) {
        super(argUserName, argPassword, email);
        this.player = player;
        this.teamOwner = teamOwner;
        this.teamManager = teamManager;
        this.coach = coach;
    }

    //region Role Setter

    public void setRole(Object role){
        if(role instanceof Player)
            player = (Player) role;
        if(role instanceof Coach)
            coach = (Coach) role;
        if(role instanceof TeamManager)
            teamManager = (TeamManager) role;
        if(role instanceof TeamOwner)
            teamOwner = (TeamOwner) role;
    }

    //endregion

    //region Boolean is a

    public boolean isAPlayer(){
        return player != null;
    }

    public boolean isACoach(){
        return coach!=null;
    }

    public boolean isATeamOwner(){
        return teamOwner!=null;
    }

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


    public void setPersonalPage(PlayerPersonalPage personalPage) {
        if(isAPlayer())
            player.setPersonalPage(personalPage);
    }

    //endregion

    //region Team Owner functionality

    public Subscription teamOwner_getAppointedByTeamOwner() {
        if(isATeamOwner())
            return teamOwner.getAppointedByTeamOwner();
        return null;
    }

    public void teamOwner_setAppointedByTeamOwner(Subscription appointedByTeamOwner) {
        if(isATeamOwner())
            teamOwner.setAppointedByTeamOwner(appointedByTeamOwner);
    }

    //endregion

    //region Team Manager functionality

    public Subscription teamManager_getAppointedByTeamOwner() {
        if(isATeamManager())
            return teamManager.getAppointedByTeamOwner();
        return null;
    }

    public void teamManager_setAppointedByTeamOwner(Subscription appointedByTeamOwner) {
        if(isATeamManager())
            teamManager.setAppointedByTeamOwner(appointedByTeamOwner);
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

    public void setPersonalPage(CoachPersonalPage personalPage) {
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
        String result = "";
        if(player!=null)
            result = result + player.toString();
        if(coach!=null)
            result = result + "\n" +coach.toString();
        if(teamManager!=null)
            result = result + "\n" +teamManager.toString();
        if(teamOwner!=null)
            result = result + "\n" +teamOwner.toString();
        return result;
    }
}
