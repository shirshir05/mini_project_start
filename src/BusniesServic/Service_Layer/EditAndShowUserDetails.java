package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Trace.CoachPersonalPage;
import BusniesServic.Business_Layer.Trace.PersonalPage;
import BusniesServic.Business_Layer.Trace.PersonalPage;
import BusniesServic.Business_Layer.Trace.PlayerPersonalPage;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.Game.ScoreTable;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.Date;

public class EditAndShowUserDetails {

    private static final String DEFAULT_PASSWORD = "11111";

    public PersonalPage getPersonalPageOfCoachOrPlayer(String user_name){
        Subscription subscription = DataManagement.getSubscription(user_name);
        if(subscription != null){
            if(subscription instanceof Coach)
                return ((Coach)subscription).getPersonalPage();
            if (subscription instanceof Player)
                return ((Player)subscription).getPersonalPage();
            if (subscription instanceof UnifiedSubscription){
                PersonalPage personalPage = ((UnifiedSubscription)subscription).getPlayerPersonalPage();
                if(personalPage == null)
                    personalPage = ((UnifiedSubscription)subscription).getCoachPersonalPage();
                return personalPage;
            }
        }
        return null;
    }

    public PersonalPage getPersonalPageOfTeam(String team_name){
        Team team = DataManagement.findTeam(team_name);
        if(team != null){
            return team.getPersonalPage();
        }
        return null;
    }

    public ActionStatus watchPersonalDetails(String user_name) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        subscription.toString();
        return new ActionStatus(true, "The user details was successfully shown!");
    }

    public ActionStatus editSubscriptionName(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new name is not legal");
        }
        subscription.setName(newValue);
        return new ActionStatus(true, "The name of the Subscription was successfully changed!");
    }

    public ActionStatus editSubscriptionEmail(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if(!DataManagement.checkEmail(newValue)){
            return new ActionStatus(false, "The new email is not legal");
        }
        subscription.setEmail(newValue);
        return new ActionStatus(true, "The Email of the Subscription was successfully changed!");
    }

    public ActionStatus editSubscriptionUserName(String user_name, String newUserName) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if(DataManagement.InputTest(newUserName, DEFAULT_PASSWORD) != null) {
            return new ActionStatus(false, "The new User Name is not legal");
        }
        subscription.setUserName(newUserName);
        return new ActionStatus(true, "The User Name of the Subscription was successfully changed!");
    }

    public ActionStatus editSubscriptionPassword(String user_name, String currentPassword, String newPassword) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        String msg = verifyPassword(newPassword);
        if(msg != null) {
            return new ActionStatus(false, msg);
        }
        if(!subscription.getPassword().equals(Subscription.getHash(currentPassword))) {
            return new ActionStatus(false, "Incorrect password");
        }
        subscription.setPassword(newPassword);
        return new ActionStatus(true, "The password of the Subscription was successfully changed!");
    }

    public ActionStatus editCoachQualification(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!isACoach(subscription)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new qualification is not legal");
        }
        if(subscription instanceof Coach) {
            ((Coach) subscription).setQualification(newValue);
        }
        else if (subscription instanceof UnifiedSubscription){
            ((UnifiedSubscription)subscription).setQualification(newValue);
        }
        return new ActionStatus(true, "The Qualification of coach was successfully changed!");
    }

    public ActionStatus editCoachRoleInTeam(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!isACoach(subscription)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new role is not legal");
        }
        if(subscription instanceof Coach) {
            ((Coach) subscription).setRoleInTeam(newValue);
        }
        else if (subscription instanceof UnifiedSubscription){
            ((UnifiedSubscription)subscription).setRoleInTeam(newValue);
        }
        return new ActionStatus(true, "The role of the coach was successfully changed!");
    }

    public ActionStatus editRefereeQualification(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!(subscription instanceof Referee)) {
            return new ActionStatus(false, "The username is not defined as a Referee on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new qualification is not legal");
        }
        Referee referee = (Referee) subscription;
        referee.setQualification(newValue);
        return new ActionStatus(true, "The Qualification of Referee was successfully changed!");
    }

    public ActionStatus editPlayerPosition(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!isAPlayer(subscription)) {
            return new ActionStatus(false, "The username is not defined as a player on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new position is not legal");
        }
        if(subscription instanceof Player) {
            ((Player) subscription).setPosition(newValue);
        }
        else if (subscription instanceof UnifiedSubscription){
            ((UnifiedSubscription)subscription).setPosition(newValue);
        }
        return new ActionStatus(true, "The Position of player was successfully changed!");
    }

/*    public ActionStatus editPlayerBirthDate(String user_name, LocalDate newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!(subscription instanceof Player)) {
            return new ActionStatus(false, "The username is not defined as a player on the system.");
        }
        //TODO need to check more on legal date (year, day ...)
        //consider change Date object
        if(newValue == null){
            return new ActionStatus(false, "The new birthday is not legal");
        }
        Player player = (Player)subscription;
        player.setBirthday(newValue);
        return new ActionStatus(true, "The birthday of player was successfully changed!");
    }*/

    public ActionStatus editPlayerPersonalPage(String user_name, Object[] values) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if (subscription == null) {
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if (!isAPlayer(subscription)) {
            return new ActionStatus(false, "The user is not defined as a player on the system.");
        }

        Subscription theEditor = DataManagement.getCurrent();
        PlayerPersonalPage personalPage = null;
        if(subscription instanceof Player) {
            personalPage = ((Player) subscription).getPersonalPage();
        }
        else if (subscription instanceof UnifiedSubscription){
            personalPage = ((UnifiedSubscription)subscription).getPlayerPersonalPage();
        }
        if(personalPage == null) {
            return new ActionStatus(false, "Cannot find personal page");
        }
        if(!personalPage.chackperrmissiontoedit(theEditor.getUserName())){
            return new ActionStatus(false, "You don't have permissions to edit this player's personal page");
        }

        if(values == null || values.length != 8)
            return new ActionStatus(false,"Illegal parameters");

        if(values[0] instanceof Date)
            personalPage.setDateOfBirth((Date) values[0]);

        if(verifyString(1, values))
            personalPage.setCountryOfBirth((String) values[1]);

        if(verifyString(2, values))
            personalPage.setCityOfBirth((String) values[2]);

        if(verifyStringToDouble(3, values))
            personalPage.setHeight(Double.parseDouble((String) values[3]));

        if(verifyStringToDouble(4, values))
            personalPage.setWeight(Double.parseDouble((String) values[4]));

        if(verifyString(5, values))
            personalPage.setPosition((String) values[5]);

        if(verifyStringToInteger(6, values))
            personalPage.setJerseyNumber(Integer.parseInt((String) values[6]));

        if(verifyString(7, values))
            personalPage.setName((String) values[7]);

        return new ActionStatus(true, "The personal page of player was successfully updated!");
    }

    public ActionStatus editCoachPersonalPage(String user_name ,Object[] values) {

        Subscription subscription = DataManagement.containSubscription(user_name);

        if (subscription == null) {
            return new ActionStatus(false, "There is no subscription in the system by this username.");
        }

        if (!isACoach(subscription)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }

        Subscription theEditor = DataManagement.getCurrent();

        CoachPersonalPage personalPage = null;
        if(subscription instanceof Coach) {
            personalPage = ((Coach) subscription).getPersonalPage();
        }
        else if (subscription instanceof UnifiedSubscription){
            personalPage = ((UnifiedSubscription)subscription).getCoachPersonalPage();
        }

        if(personalPage == null){
            return new ActionStatus(false, "Cannot find personal page");
        }
        if(!personalPage.chackperrmissiontoedit(theEditor.getUserName())){

            return new ActionStatus(false, "You do not have permissions to edit this coach personal page");
        }

        if(values == null || values.length != 5)
            return new ActionStatus(false,"Illegal parameters");

        if(values[0] instanceof Date)
            personalPage.setDateOfBirth((Date) values[0]);

        if(verifyString(1, values))
            personalPage.setCountryOfBirth((String)values[1]);

        if(verifyStringToDouble(2, values))
            personalPage.setYearOfExperience(Double.parseDouble((String) values[2]));

        if(verifyStringToInteger(3,values))
            personalPage.setNumOfTitles(Integer.parseInt((String) values[3]));

        if(verifyString(4, values))
            personalPage.setName((String) values[4]);

        return new ActionStatus(true, "The personal page of coach was successfully update!");
    }

    public ActionStatus editTeamPersonalPage(String team, Object[] values) {

        Team teamObject = DataManagement.findTeam(team);

        if(teamObject == null){

            return new ActionStatus(false, "There is no such a team in the system");
        }

        Subscription theEditor = DataManagement.getCurrent();

        if(!teamObject.getPersonalPage().chackperrmissiontoedit(theEditor.getUserName())){

            return new ActionStatus(false, "You don't have permissions to edit this team's personal page");
        }

        if(values == null || values.length != 5)
            return new ActionStatus(false,"Illegal parameters");

        if(values[0] instanceof Date)
            teamObject.getPersonalPage().setYearOfFoundation((Date) values[0]);

        if(verifyString(1,values))
            teamObject.getPersonalPage().setPresidentName((String) values[1]);

        if(verifyString(2,values))
            teamObject.getPersonalPage().setStadiumName((String) values[2]);

        if(values[3] instanceof ScoreTable)
            teamObject.getPersonalPage().setScoreTable((ScoreTable) values[3]);

        if(verifyString(4,values))
            teamObject.getPersonalPage().setName((String) values[4]);

        return new ActionStatus(true, "The team personal page was successfully updated!");
    }

    /**
     * A coach or player can add a user to their personal page management
     */
    public ActionStatus addPermissionsToCurrentUserPersonalPage(String addPermissionsToThisUser) {
        ActionStatus AC;
        if(addPermissionsToThisUser == null || DataManagement.containSubscription(addPermissionsToThisUser) == null){
            AC = new ActionStatus(false,"Invalid username.");
        }
        else if (DataManagement.getCurrent().permissions.check_permissions(PermissionAction.personal_page)) {

            if (DataManagement.getCurrent() instanceof Coach) {
                Coach coach = (Coach) DataManagement.getCurrent();
                coach.getPersonalPage().addPermissionToEdit(addPermissionsToThisUser);
                AC = new ActionStatus(true, "Permissions successfully added.");
            } else if (DataManagement.getCurrent() instanceof Player) {
                Player player = (Player) DataManagement.getCurrent();
                player.getPersonalPage().addPermissionToEdit(addPermissionsToThisUser);
                AC = new ActionStatus(true, "Permissions successfully added.");
            } else if (DataManagement.getCurrent() instanceof UnifiedSubscription){
                UnifiedSubscription unifiedSubscription = (UnifiedSubscription) DataManagement.getCurrent();
                PersonalPage personalPage = unifiedSubscription.getPlayerPersonalPage();
                //allow the user to edit both personal pages of the current user:
                if(personalPage != null)
                    personalPage.addPermissionToEdit(addPermissionsToThisUser);
                personalPage = unifiedSubscription.getCoachPersonalPage();
                if(personalPage != null)
                    personalPage.addPermissionToEdit(addPermissionsToThisUser);
                AC = new ActionStatus(true, "Permissions successfully added.");
            }
            else {
                AC = new ActionStatus(false, "You do not have a personal page.");
            }
        }
        else{
            AC = new ActionStatus(false,"You are not allowed to edit this personal page");
        }
        logger.log("added permissions to personal page of: "+ DataManagement.getCurrent().getUserName()+" to "+addPermissionsToThisUser+" "+ AC.getDescription());
        return AC;
    }

    public ActionStatus addPermissionsToTeamPersonalPage(String addPermissionsToThisUser, String teamName) {
        ActionStatus AC;
        Team team = DataManagement.findTeam(teamName);
        if(team == null){
            AC = new ActionStatus(false, "Cannot find team");
        }
        else if(addPermissionsToThisUser == null || DataManagement.containSubscription(addPermissionsToThisUser) == null){
            AC = new ActionStatus(false,"Invalid username.");
        }
        else if (DataManagement.getCurrent().permissions.check_permissions(PermissionAction.personal_page)
                && team.getPersonalPage().chackperrmissiontoedit(DataManagement.getCurrent().getUserName())) {
            team.getPersonalPage().addPermissionToEdit(addPermissionsToThisUser);
            AC = new ActionStatus(true, "Permissions successfully added.");
        }
        else{
            AC = new ActionStatus(false,"You are not allowed to edit this personal page");
        }
        logger.log("added permissions to personal page of: "+ DataManagement.getCurrent().getUserName()+" to "+addPermissionsToThisUser+" "+ AC.getDescription());
        return AC;
    }

    //region Private methods

    private boolean verifyStringToInteger(int i, Object[] values) {
        if(values[i] instanceof String) {
            try {
                Integer.parseInt((String) values[i]);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    private boolean verifyStringToDouble(int i, Object[] values) {
        if(values[i] instanceof String) {
            try {
                Double.parseDouble((String) values[i]);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    private boolean verifyString(int i, Object[] values) {
        return values[i] instanceof String && !((String) values[i]).isEmpty();
    }

    private ActionStatus getActionStatus(Subscription subscription) {
        if(subscription == null){
            return new ActionStatus(false, "There is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        return null;
    }

    private String verifyPassword(String password){
        if(password == null || password.length() < 5){
            return "The password must contain at least 5 digits.";
        }
        return null;
    }

    private boolean isACoach(Subscription subscription) {
        return subscription instanceof Coach || (subscription instanceof UnifiedSubscription && ((UnifiedSubscription)subscription).isACoach());
    }

    private boolean isAPlayer(Subscription subscription) {
        return subscription instanceof Player || (subscription instanceof UnifiedSubscription && ((UnifiedSubscription)subscription).isAPlayer());
    }

    //endregion


}
