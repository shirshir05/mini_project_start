package BusniesServic.Service_Layer;
// all Subscription in system

import BusniesServic.Business_Layer.Game.Game;
import BusniesServic.Business_Layer.Game.League;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Complaint;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Business_Layer.UserManagement.SystemAdministrator;
import BusniesServic.Business_Layer.UserManagement.UnionRepresentative;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Enum.Role;
import DB_Layer.logger;
import DB_Layer.stateTaxSystem;
import DB_Layer.unionFinanceSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

public final class DataManagement {

    private static final DataManagement instance = new DataManagement();
    // A list that keeps all the subscriptions that are currently subscribed to the system
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
     * @param arg_user_name
     * @return Subscription
     */
    public static Subscription containSubscription(String arg_user_name){
        for (Subscription  subscription : Subscription) {
            if (subscription.getUserName().equals(arg_user_name)){
                return subscription;
            }
        }
        return null;
    }

    protected static boolean isInEnum(String value) {
        return Arrays.stream(Role.values()).anyMatch(e -> e.name().equals(value));
    }


    /**
     * The function accepts a string with the role name and returns Enum.
     * @param argRole
     * @return Role or null if the tole not found
     */
    public static Role returnEnum(String argRole){
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
            default:
                return null;
        }
    }

    /**
     * @param teamName
     * @return
     */
    public static Team findTeam(String teamName) {
        for (Team t : list_team){
            if (teamName==null || (t.getName().equals(teamName)))
                return t;
        }
        return null;
    }

    /**
     * add Game
     * @param g
     */
    public static void addGame(Game g){
        list_game.add(g);
    }

    /**
     * This function get game_id and return Game
     * @param game_id
     * @return
     */
    public static Game getGame(int game_id){
        for ( Game g: list_game ){
            if (g.getGameId()==game_id)
                return g;
        }
        return null;
    }

    /**
     * This function gets a leaugeName and return Leauge
     * @param leaugeName
     * @return
     */
    public static League findLeague(String leaugeName) {
        for (League l : list_league) {
            if (l.getName().equals(leaugeName)) {
                return l;
            }
        }
        return null;
    }


    public static ArrayList<UnionRepresentative> getUnionRepresentatives(){
        ArrayList<UnionRepresentative> unionReps = new ArrayList<>();
        for(Subscription s: Subscription){
            if(s instanceof UnionRepresentative)
                unionReps.add((UnionRepresentative)s);
        }
        return unionReps;
    }



    public static void setSubscription(Subscription sub){
        Subscription.add(sub);
        logger.log("DataManagement :new Subscription , name: " + sub.getUserName());

    }

    public static void removeSubscription(String user_name){
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
        HashSet<SystemAdministrator> list = getSystemAdministratorsList();
        for (SystemAdministrator s : list) {
            team.addObserver(s);
        }
        logger.log("DataManagement :new team was added, team name: " + team.getName());
    }

    public  static HashSet<Team>  getListTeam(){
        return list_team;

    }

    public static void addToListLeague(League league){
        list_league.add(league);
        logger.log("DataManagement :new league was added, team name: " + league.getName());
    }

    public static  HashSet<League> getListLeague(){
        return list_league;
    }

    /**
     * This function returns a list of all the System Administrators in the system
     * @return
     */
    public static HashSet<SystemAdministrator> getSystemAdministratorsList(){
        HashSet<SystemAdministrator> list = new HashSet<>();
        for (Subscription s : Subscription){
            if (s instanceof SystemAdministrator){
                list.add((SystemAdministrator)s);
            }
        }
        return list;
    }
/*
    public static void setComplaint(String complaint){
        Complaint new_complaint = new Complaint(complaint);
        HashSet<SystemAdministrator> list = getSystemAdministratorsList();
        for (SystemAdministrator s : list){
            new_complaint.addObserver(s);
        }
        new_complaint.notify_all();
        list_Complaints.add(new_complaint);
    }

 */

    /**
     * This function check if email is legal
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
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
     * @param arg_user_name
     * @param arg_password
     * @return comment print to user
     * if return  null the input correct
     */
    public static String InputTest(String arg_user_name, String arg_password){
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

    public static HashSet<Complaint> getAllComplaints() {
        return list_Complaints;
    }

    public static HashSet<Complaint> getUnansweredComplaints() {
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

    public static Subscription getSubscription(String userName){
        if (userName!=null) {
            for (Subscription s : Subscription) {
                if (s.getUserName().equals(userName)) {
                    return s;
                }
            }
        }
        return null;
    }


}
