package BusinessService.Service_Layer;
// all Subscription in system

import BusinessService.Business_Layer.Game.Game;
import BusinessService.Business_Layer.Game.League;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Complaint;
import BusinessService.Business_Layer.UserManagement.Subscription;
import BusinessService.Business_Layer.UserManagement.SystemAdministrator;
import BusinessService.Business_Layer.UserManagement.UnionRepresentative;
import BusinessService.Enum.ActionStatus;
import BusinessService.Enum.Role;
import DB_Layer.JDBC.sqlConnection;
import DB_Layer.databaseController;
import DB_Layer.logger;
import DB_Layer.stateTaxSystem;
import DB_Layer.unionFinanceSystem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

public final class DataManagement {

    private static final DataManagement instance = new DataManagement();
    // A list that keeps all the subscriptions that are currently subscribed to the system
    private static databaseController sql = new databaseController();
    private static HashSet<Subscription>  Subscription = new HashSet<>();

    private static HashSet<Team> list_team = new HashSet<>();

    private static HashSet<Game> list_game = new HashSet<>();

    private static HashSet<League> list_league = new HashSet<>();

    private static HashSet<Complaint> list_Complaints = new HashSet<>();

    // Saves the current subscription that is currently being registered to the system
    private static Subscription current;

    private static stateTaxSystem taxSys;
    private static unionFinanceSystem financeSys;

    /**
     * singleton constructor to initialize the parameters
     */
    private DataManagement() {
        if (instance == null) {
            //Prevent Reflection
            //throw new IllegalStateException("Cannot instantiate a new singleton instance of logic management");
            this.createLogicManagement();
            logger.log("DataManagement :the system is initialized");
        }
    }

    /**
     * singleton initialize the parameters
     */
    private void createLogicManagement(){
        //initialize system and connections
        financeSys = new unionFinanceSystem();
        boolean checkSystem1 = financeSys.initConnection();
        taxSys = new stateTaxSystem();
        boolean checkSystem2 = taxSys.initConnection();
    }

    public static ActionStatus getExternalConnStatus(String system){
        ActionStatus ac = null;
        switch (system) {
            case "finance":
                ac = new ActionStatus(financeSys.checkConnection(),"finance system status");
            case "tax":
                ac = new ActionStatus(taxSys.checkConnection(),"tax system status");
        }
        return ac;
    }

    /**
     * function data clean all data set in system
     */
    public static void cleanAllData(){
        Subscription = new HashSet<>();
        list_team = new HashSet<>();
        list_game = new HashSet<>();
        list_league = new HashSet<>();
        list_Complaints = new HashSet<>();
        current = null;
    }

    /**
     * A function is to check if there is a subscription in the system by username.
     * @param arg_user_name -
     * @return Subscription
     */
    public static Subscription containSubscription(String arg_user_name){
        return sql.loadUserByName(arg_user_name);
    }

    /**
     * check if the role is enum in system
     * @param value -
     * @return boolean
     */
    static boolean isInEnum(String value) {
        return Arrays.stream(Role.values()).anyMatch(e -> e.name().equals(value));
    }


    /**
     * The function accepts a string with the role name and returns Enum.
     * @param argRole -
     * @return Role or null if the tole not found
     */
    static Role returnEnum(String argRole){
        if (!isInEnum(argRole)) {
            return null;
        }
        Role enum_role =  Role.valueOf(argRole);
        switch (enum_role) {
            case Coach:
                return Role.Coach;
            case Fan:
                return Role.Fan;
            case Guest:
                return Role.Guest;
            case Player:
                return Role.Player;
            case Referee:
                return Role.Referee;
            case SystemAdministrator:
                return Role.SystemAdministrator;
            case TeamManager:
                return Role.TeamManager;
            case TeamOwner:
                return Role.TeamOwner;
            case UnionRepresentative:
                return Role.UnionRepresentative;
            case UnifiedSubscription:
                return Role.UnifiedSubscription;
            default:
                return null;
        }
    }

    /**
     * @param teamName -  name team
     * @return Team
     */
    public static Team findTeam(String teamName) {
        return sql.loadTeamInfo(teamName);
    }

    /**
     * add Game
     * @param g - game
     */
    public static void addGame(Game g){
        list_game.add(g);
    }

    /**
     * This function get game_id and return Game
     * @param game_id -
     * @return Game
     */
    public static Game getGame(int game_id){
        return sql.loadGameInfo(game_id);
    }

    /**
     * This function gets a leagueName and return League
     * @param leagueName -
     * @return -
     */
    static League findLeague(String leagueName) {
        return sql.loadLeagueInfo(leagueName);
    }


    /**
     * return all Union Representatives in system
     * @return ArrayList
     */
    static HashSet<Subscription> getUnionRepresentatives(){
        return sql.loadUsersByRole("UnionRepresentative");
    }



    public static void setSubscription(Subscription sub){
        Subscription.add(sub);
        logger.log("DataManagement :new Subscription , name: " + sub.getUserName());

    }

    static void removeSubscription(String user_name){
        Subscription.remove(containSubscription(user_name));
        logger.log("DataManagement :remove Subscription , name: " + user_name);
    }

    public static void setCurrent(Subscription sub){
        current = sub;
        if(sub!= null){
            logger.log("DataManagement :new current set, name: " + current.getUserName());
        }
    }

    public static Subscription getCurrent(){
        return current;
    }

    public static void addToListTeam(Team team){
        list_team.add(team);
        HashSet<Subscription> list = getSystemAdministratorsList();
        for (Subscription s : list) {
            team.addObserver((SystemAdministrator)s);
        }
        logger.log("DataManagement :new team was added, team name: " + team.getName());
    }

    public static void addToListLeague(League league){
        list_league.add(league);
        logger.log("DataManagement :new league was added, team name: " + league.getName());
    }

    /**
     * This function returns a list of all the System Administrators in the system
     * @return -
     */
    static HashSet<Subscription> getSystemAdministratorsList(){
        return sql.loadUsersByRole("UnionRepresentative");
    }

    /**
     * This function check if email is legal
     * @param email -
     * @return -
     */
    static boolean checkEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     * The purpose of this function is to check the correctness of the input of the user who
     * wishes to register.
     * Laws:
     * password - only number and length of 5 digits
     * user_name -Unique not empty
     * @param arg_user_name -
     * @param arg_password -
     * @return comment print to user
     * if return  null the input correct
     */
    static String InputTest(String arg_user_name, String arg_password){
        if(arg_user_name == null || arg_password == null || arg_user_name.equals("") || arg_password.equals("")){
            return "The input is empty.";
        }
        if(arg_password.length() < 5){
            return "The password must contain at least 5 digits.";
        }
        if (DataManagement.containSubscription(arg_user_name) != null){
            return "Please select another username because this username exists in the system.";
        }
        return null;
    }

    static HashSet<Complaint> getAllComplaints() {
        return list_Complaints;
    }

    static HashSet<Complaint> getUnansweredComplaints() {
        HashSet<Complaint> unanswered = new HashSet<>();
        for (Complaint c : list_Complaints){
            if (!c.isAnswered()){
                unanswered.add(c);
            }
        }
        return unanswered;
    }

    /**
     * for the use of complaint controller - adds a compliant after verifying the permissions
     * @param complaint the complaint to add
     */
    public static void addComplaint(Complaint complaint) {
        if(complaint!=null){
            list_Complaints.add(complaint);
        }
    }

}
