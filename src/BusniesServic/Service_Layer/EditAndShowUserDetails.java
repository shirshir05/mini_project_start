package BusniesServic.Service_Layer;

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


    public ActionStatus watchPersonalDetils(String user_name) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        subscription.toString();
        return new ActionStatus(true, "The user details was successfully shown!");
    }

    public ActionStatus editSubscriptionName(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new name is not legal");
        }
        subscription.setName(newValue);
        return new ActionStatus(true, "The name of the Subscription was successfully changed!");
    }

    public ActionStatus editSubscriptionEmail(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        if(!DataManagement.checkEmail(newValue)){
            return new ActionStatus(false, "The new Email is not legal");
        }
        subscription.setEmail(newValue);
        return new ActionStatus(true, "The Email of the Subscription was successfully changed!");
    }

    public ActionStatus editSubscriptionUserName(String user_name, String newUserName, String currentPassword) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        if(DataManagement.InputTest(newUserName, currentPassword) != null) {
            return new ActionStatus(false, "The new User Name is not legal");
        }
        subscription.setUserName(newUserName);
        return new ActionStatus(true, "The User Name of the Subscription was successfully changed!");
    }

    public ActionStatus editSubscriptionPassword(String user_name, String currentUserName, String newPassword) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        if(DataManagement.InputTest(currentUserName, newPassword) != null) {
            return new ActionStatus(false, "The new password is not legal");
        }
        subscription.setPassword(newPassword);
        return new ActionStatus(true, "The password of the Subscription was successfully changed!");
    }

    public ActionStatus editCoachQualification(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
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
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
        if (!(subscription instanceof Coach)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }
        if(newValue == null || newValue.length() == 0){
            return new ActionStatus(false, "The new role is not legal");
        }
        Coach coach = (Coach) subscription;
        coach.setRoleInTeam(newValue);
        return new ActionStatus(true, "The role of coach was successfully changed!");
    }

    public ActionStatus editRefereeQualification(String user_name, String newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
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
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
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

    public ActionStatus editPlayerDate(String user_name, LocalDate newValue) {
        Subscription subscription = DataManagement.containSubscription(user_name);
        if(subscription == null){
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }
        if(!subscription.getUserName().equals(DataManagement.getCurrent().getUserName())){
            return new ActionStatus(false, "Another subscription is connected to the system.");
        }
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
            return new ActionStatus(false, "The username is not defined as a player on the system.");
        }

        Subscription theEditor = DataManagement.getCurrent();

        Player player = (Player) subscription;

        if(!player.getPersonalPage().chackperrmissiontoedit(theEditor.getUserName())){

            return new ActionStatus(false, "You don't have permissions to edit this player personal page");
        }
        player.getPersonalPage().setDateOfBirth((Date) values[0]);
        player.getPersonalPage().setCountryOfBirth((String) values[1]);
        player.getPersonalPage().setCityOfBirth((String) values[2]);
        player.getPersonalPage().setHeight((double) values[3]);
        player.getPersonalPage().setWeight((double) values[4]);
        player.getPersonalPage().setPosition((String) values[5]);
        player.getPersonalPage().setJerseyNumber((int) values[6]);
        player.getPersonalPage().setName((String) values[7]);
        return new ActionStatus(true, "The personal page of player was successfully update!");
    }

    public ActionStatus editCoachPersonalPage(String user_name ,Object[] values) {

        Subscription subscription = DataManagement.containSubscription(user_name);

        if (subscription == null) {
            return new ActionStatus(false, "there is no subscription in the system by this username.");
        }

        if (!(subscription instanceof Coach)) {
            return new ActionStatus(false, "The username is not defined as a coach on the system.");
        }

        Subscription theEditor = DataManagement.getCurrent();

        Coach coach = (Coach) subscription;

        if(!coach.getPersonalPage().chackperrmissiontoedit(theEditor.getUserName())){

            return new ActionStatus(false, "You don't have permissions to edit this coach personal page");
        }
/*
        coach.getPersonalPage().setDateOfBirth((Date) values[0]);
        coach.getPersonalPage().setCountryOfBirth((String)values[1]);
        coach.getPersonalPage().setYearOfExperience((String)values[2]);
        coach.getPersonalPage().setNumOfTitles((String)values[3]);

 */
        coach.getPersonalPage().setName((String) values[4]);

        return new ActionStatus(true, "The personal page of coach was successfully update!");
    }

    public ActionStatus editTeamPersonalPage(String team, Object[] values) {

        /*
        protected Date yearOfFoundation;
        protected String presidentName;
        protected String stadiumName;
        protected FootballTeamStatistic teamStatistic;
        protected ScoreTable scoreTable;
         */

        Team teamObject = DataManagement.findTeam(team);

        if(teamObject == null){

            return new ActionStatus(false, "there is no such a team in the system");
        }

        Subscription theEditor = DataManagement.getCurrent();

        if(!teamObject.getPersonalPage().chackperrmissiontoedit(theEditor.getUserName())){

            return new ActionStatus(false, "You don't have permissions to edit this team personal page");
        }

        teamObject.getPersonalPage().setYearOfFoundation((Date) values[0]);
        teamObject.getPersonalPage().setPresidentName((String) values[1]);
        teamObject.getPersonalPage().setStadiumName((String) values[2]);
        teamObject.getPersonalPage().setScoreTable((ScoreTable) values[3]);
        teamObject.getPersonalPage().setName((String) values[4]);

        return new ActionStatus(true, "The Team personal page was successfully update!");
    }

    /**
     *A coach or player can add a user to their personal page management
     * @param nameUser
     * @return
     */
    public ActionStatus addPermission(String nameUser) {
        ActionStatus AC;
        if(nameUser == null){
            AC = new ActionStatus(false,"Username is null.");
        }
        else if (DataManagement.getCurrent().permissions.check_permissions(PermissionAction.personal_page)) {
            if(DataManagement.containSubscription(nameUser) == null){
                AC = new ActionStatus(false,"No such user exists in the system.");
            }else{
                Subscription add = DataManagement.containSubscription(nameUser);
                if(DataManagement.getCurrent() instanceof Coach  ){
                    Coach coach = (Coach) DataManagement.getCurrent();
                    coach.getPersonalPage().addPageOwner(nameUser);
                    AC = new ActionStatus(true,"Permissions successfully added.");
                }else if(DataManagement.getCurrent() instanceof Player){
                    Player player = (Player) DataManagement.getCurrent();
                    player.getPersonalPage().addPageOwner(nameUser);
                    AC = new ActionStatus(true,"Permissions successfully added.");
                }
                else {
                    AC = new ActionStatus(false, "You do not have a personal page.");
                }
            }
        }else{
            AC = new ActionStatus(false,"You do not have a personal page.");
        }
        logger.log("add Permission to personal page of: "+ DataManagement.getCurrent().getUserName()+" to "+nameUser+" "+ AC.getDescription());
        return AC;
    }



}
