package Business_Layer.Business_Control;
// all Subscription in system

import Business_Layer.Business_Items.Game.*;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.*;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Enum.Role;
import DB_Layer.databaseController;
import DB_Layer.logger;
import DB_Layer.stateTaxSystem;
import DB_Layer.unionFinanceSystem;

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
        //TODO- decide if to delete list or to save and delete at user logout ???
        list_game.add(g);

        //save game to database
        for(Event e :g.getEventList()){
            sql.insert("EventInGame",new String[]{""+g.getGameId(),""+e.getEventTime().toLocalTime(),e.getPlayer(),""+e.getEventType()});
        }
        sql.insert("Game",new String[]{""+g.getGameId(),g.getField(),""+g.getStartTime().toLocalTime(),""+g.getEndTime().toLocalTime(),g.getHost().getName()
            ,g.getGuest().getName(),g.getLeague(),g.getSeason(),g.getHeadReferee(),g.getLinesman1Referee(),g.getLinesman2Referee()});
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
        sql.insert("Users",new String[]{sub.getUserName(),sub.getPassword(),sub.getRole(),sub.getEmail()});
        sql.insertBlob(sub.getUserName()+"Permissions",sub.getPermissions());
        if(sub instanceof UnifiedSubscription){
            if(((UnifiedSubscription) sub).isACoach()){
                sql.insert("UsersData",new String[]{sub.getUserName(),"qualification",((UnifiedSubscription) sub).getQualification()});
                sql.insert("UsersData",new String[]{sub.getUserName(),"roleInTeam",((UnifiedSubscription) sub).getRoleInTeam()});
                sql.insertBlob(sub.getUserName()+"CoachPersonalPage",((UnifiedSubscription) sub).getCoachPersonalPage());
            }if(((UnifiedSubscription) sub).isAPlayer()){
                sql.insert("UsersData",new String[]{sub.getUserName(),"position",((UnifiedSubscription)sub).getPosition()});
                sql.insertBlob(sub.getUserName()+"PlayerPersonalPage",((UnifiedSubscription) sub).getPlayerPersonalPage());
            }if(((UnifiedSubscription) sub).isATeamManager()){
                sql.insert("UsersData",new String[]{sub.getUserName(),"managerAppointedByTeamOwner",((UnifiedSubscription)sub).teamManager_getAppointedByTeamOwner()});
            }if(((UnifiedSubscription) sub).isATeamOwner()){
                sql.insert("UsersData",new String[]{sub.getUserName(),"ownerAppointedByTeamOwner",((UnifiedSubscription)sub).teamOwner_getAppointedByTeamOwner()});
            }
        }else if(sub instanceof Referee){
            sql.insert("UsersData",new String[]{sub.getUserName(),"qualification",((Referee) sub).getQualification()});
            sql.insert("UsersData",new String[]{sub.getUserName(),"refereeGames",((Referee) sub).gamesListToString()});
        }
        logger.log("DataManagement :new Subscription , name: " + sub.getUserName());

    }

    static void removeSubscription(String user_name){
        //Subscription.remove(containSubscription(user_name));
        sql.delete("Users",new String[]{user_name});
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

    public static void updateTeam(Team team){
        sql.delete("Blobs",new String[]{});
        sql.insertBlob(team.getName(),team);
    }

    public static void addToListTeam(Team team){
        list_team.add(team);
        HashSet<Subscription> list = getSystemAdministratorsList();
        for (Subscription s : list) {
            team.addObserver((SystemAdministrator)s);
        }
        sql.insertBlob(team.getName(),team);
        /*
        String asset = (String)team.getTeamAssets().iterator().next();
        sql.insert("Team", new Object[]{team.getName(), asset, team.getListTeamOwner().iterator().next(), team.getStatus(),
                team.getTeamScore().getPoints(), team.getTeamScore().getNumberOfGames(), team.getTeamScore().getWins(), team.getTeamScore().getDrawn(),
                team.getTeamScore().getLoses(), String.valueOf(team.getTeamScore().getGoalsScores()), team.getTeamScore().getGoalsGet()});
        HashSet<UnifiedSubscription> people = team.getListTeamOwner();
        while(people.iterator().hasNext()){
            UnifiedSubscription t = people.iterator().next();
            sql.insert("AssetsInTeam",new Object[]{team.getName(),t.getUserName(),"TeamOwner"});
        }
        people = team.getListTeamManager();
        while(people.iterator().hasNext()){
            UnifiedSubscription t = people.iterator().next();
            sql.insert("AssetsInTeam",new Object[]{team.getName(),t.getUserName(),"TeamManager"});
        }
        people = team.getTeamPlayers();
        while(people.iterator().hasNext()){
            UnifiedSubscription t = people.iterator().next();
            sql.insert("AssetsInTeam",new Object[]{team.getName(),t.getUserName(),"Player"});
        }
        people = team.getTeamCoaches();
        while(people.iterator().hasNext()){
            UnifiedSubscription t = people.iterator().next();
            sql.insert("AssetsInTeam",new Object[]{team.getName(),t.getUserName(),"Coach"});
        }
        HashSet<Object> filds = team.getTeamAssets();
        while(filds.iterator().hasNext()){
            Object t = filds.iterator().next();
            sql.insert("AssetsInTeam",new Object[]{team.getName(),t.toString(),"Filed"});
        }
        */
        logger.log("DataManagement :new team was added, team name: " + team.getName());
    }

    public static void addToListLeague(League league){
        list_league.add(league);
        sql.insert("League", new Object[]{league.getName()});
        for(Season s:league.getAllSeasons()){
            sql.insertBlob("Season"+league.getName()+s.getYear(),s);
        }
        logger.log("DataManagement :new league was added, team name: " + league.getName());
    }

    public static void addNewSeason(String league,Season season,PointsPolicy point){
        sql.insertBlob("Season"+league+season.getYear(),season);
        sql.insert("Season", new Object[]{league, Integer.parseInt(season.getYear())});
    }

    public static void updateSeason(String league,Season season) {
        sql.delete("Blobs",new String[]{"Season"+league+season});
        sql.insertBlob("Season"+league+season.getYear(),season);
    }

    /**
     * This function returns a list of all the System Administrators in the system
     * @return -
     */
    static HashSet<Subscription> getSystemAdministratorsList(){
        return sql.loadUsersByRole("SystemAdministrator");
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
        return sql.loadComplaintInfo(false);
    }

    static HashSet<Complaint> getUnansweredComplaints() {
        return sql.loadComplaintInfo(true);
    }

    /**
     * for the use of complaint controller - adds a compliant after verifying the permissions
     * @param complaint the complaint to add
     */
    public static void addComplaint(Complaint complaint) {
        if(complaint!=null){
            HashSet<Complaint> c = sql.loadComplaintInfo(false);
            if(c!=null) {
                list_Complaints = c;
            }
            list_Complaints.add(complaint);
            sql.delete("Blobs",new String[]{"Complaint"});
            sql.insertBlob("Complaint",list_Complaints);
        }
    }
}
