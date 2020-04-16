package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Trace.PersonalPage;
import BusniesServic.Business_Layer.Trace.PersonalPage;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.Game.ScoreTable;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Coach;
import BusniesServic.Business_Layer.UserManagement.Player;
import BusniesServic.Business_Layer.UserManagement.Referee;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;

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
            return new ActionStatus(false, "The new Email is not legal");
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
        if (!(subscription instanceof Coach)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new qualification is not legal");
        }
        Coach coach = (Coach) subscription;
        coach.setQualification(newValue);
        return new ActionStatus(true, "The Qualification of coach was successfully changed!");
    }

    public ActionStatus editCoachRoleInTeam(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!(subscription instanceof Coach)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new role is not legal");
        }
        Coach coach = (Coach) subscription;
        coach.setRoleInTeam(newValue);
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
        if (!(subscription instanceof Player)) {
            return new ActionStatus(false, "The username is not defined as a player on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new position is not legal");
        }
        Player player = (Player)subscription;
        player.setPosition(newValue);
        return new ActionStatus(true, "The Position of player was successfully changed!");
    }

    public ActionStatus editPlayerBirthDate(String user_name, LocalDate newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        ActionStatus ac = getActionStatus(subscription);
        if (ac != null) return ac;
        if (!(subscription instanceof Player)) {
            return new ActionStatus(false, "The username is not defined as a player on the system.");
        }
        //need to check more on legal date (year, day ...)
        //consider change Date object
        if(newValue == null){
            return new ActionStatus(false, "The new birthday is not legal");
        }
        Player player = (Player)subscription;
        player.setBirthday(newValue);
        return new ActionStatus(true, "The birthday of player was successfully changed!");
    }

    public ActionStatus editPlayerPersonalPage(String user_name, Object[] values) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if (subscription == null) {
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if (!(subscription instanceof Player)) {
            return new ActionStatus(false, "The user is not defined as a player on the system.");
        }

        Subscription theEditor = DataManagement.getCurrent();

        Player player = (Player) subscription;

        if(!player.getPersonalPage().chackperrmissiontoedit(theEditor.getUserName())){
            return new ActionStatus(false, "You don't have permissions to edit this player's personal page");
        }

        if(values.length != 8)
            return new ActionStatus(false,"Illegal parameters");

        if(values[0] instanceof Date)
            player.getPersonalPage().setDateOfBirth((Date) values[0]);

        if(verifyString(1, values))
            player.getPersonalPage().setCountryOfBirth((String) values[1]);

        if(verifyString(2, values))
            player.getPersonalPage().setCityOfBirth((String) values[2]);

        if(verifyStringToDouble(3, values))
            player.getPersonalPage().setHeight(Double.parseDouble((String) values[3]));

        if(verifyStringToDouble(4, values))
            player.getPersonalPage().setWeight(Double.parseDouble((String) values[4]));

        if(verifyString(5, values))
            player.getPersonalPage().setPosition((String) values[5]);

        if(verifyStringToInteger(6, values))
            player.getPersonalPage().setJerseyNumber(Integer.parseInt((String) values[6]));

        if(verifyString(7, values))
            player.getPersonalPage().setName((String) values[7]);

        return new ActionStatus(true, "The personal page of player was successfully updated!");
    }

    public ActionStatus editCoachPersonalPage(String user_name ,Object[] values) {

        Subscription subscription = DataManagement.containSubscription(user_name);

        if (subscription == null) {
            return new ActionStatus(false, "There is no subscription in the system by this username.");
        }

        if (!(subscription instanceof Coach)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }

        Subscription theEditor = DataManagement.getCurrent();

        Coach coach = (Coach) subscription;

        if(!coach.getPersonalPage().chackperrmissiontoedit(theEditor.getUserName())){

            return new ActionStatus(false, "You do not have permissions to edit this coach personal page");
        }

        if(values.length != 4)
            return new ActionStatus(false,"Illegal parameters");

        if(values[0] instanceof Date)
            coach.getPersonalPage().setDateOfBirth((Date) values[0]);

        if(verifyString(1, values))
            coach.getPersonalPage().setCountryOfBirth((String)values[1]);

        if(verifyStringToDouble(3, values))
            coach.getPersonalPage().setYearOfExperience(Double.parseDouble((String) values[2]));

        if(verifyStringToInteger(3,values))
            coach.getPersonalPage().setNumOfTitles(Integer.parseInt((String) values[4]));

        if(verifyString(4, values))
            coach.getPersonalPage().setName((String) values[4]);

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

        if(values.length != 5)
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

    //endregion

    /**
     * A coach or player can add a user to their personal page management
     */
    public ActionStatus addPermissions(String addPermissionsToThisPage) {
        ActionStatus AC;
        if(addPermissionsToThisPage == null || DataManagement.containSubscription(addPermissionsToThisPage) == null){
            AC = new ActionStatus(false,"Invalid username.");
        }
        else if (DataManagement.getCurrent().permissions.check_permissions(PermissionAction.personal_page)) {

            if (DataManagement.getCurrent() instanceof Coach) {
                Coach coach = (Coach) DataManagement.getCurrent();
                coach.getPersonalPage().addPageOwner(addPermissionsToThisPage);
                AC = new ActionStatus(true, "Permissions successfully added.");
            } else if (DataManagement.getCurrent() instanceof Player) {
                Player player = (Player) DataManagement.getCurrent();
                player.getPersonalPage().addPageOwner(addPermissionsToThisPage);
                AC = new ActionStatus(true, "Permissions successfully added.");
            } else {
                AC = new ActionStatus(false, "You do not have a personal page.");
            }

        }
        else{
            AC = new ActionStatus(false,"You do not have a personal page.");
        }
        logger.log("added permissions to personal page of: "+ DataManagement.getCurrent().getUserName()+" to "+addPermissionsToThisPage+" "+ AC.getDescription());
        return AC;
    }



}
