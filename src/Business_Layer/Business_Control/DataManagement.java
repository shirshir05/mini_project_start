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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

public final class DataManagement {

    private static final DataManagement instance = new DataManagement();
    // A list that keeps all the subscriptions that are currently subscribed to the system
    private static databaseController sql = new databaseController();
    private static HashSet<Subscription>  mySubscription = new HashSet<>();

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
            this.createLogicManagement();
            logger.log("DataManagement :the system is initialized");
        }
    }

    /**
     * singleton initialize the parameters
     */
    private void createLogicManagement(){
        // initialize system and connections
        financeSys = new unionFinanceSystem("www.finances.gov");
        boolean checkSystem1 = financeSys.getTaxRate(0) == 0;
        taxSys = new stateTaxSystem("www.taxes.gov");
        boolean checkSystem2 = financeSys.getTaxRate(0) == 0;
    }

    public static ActionStatus getExternalConnStatus(String system){
        ActionStatus ac = null;
        switch (system) {
            case "finance":
                //ac = new ActionStatus(financeSys.checkConnection(),"finance system status");
            case "tax":
                //ac = new ActionStatus(taxSys.checkConnection(),"tax system status");
        }
        return ac;
    }

    /**
     * function data clean all data set in system
     */
    public static void cleanAllData(){
        mySubscription = new HashSet<>();
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
        Subscription sub = null;
        for(Subscription s: mySubscription){
            if(s.getUserName().equals(arg_user_name)){
                sub = s;
            }
        }
        if(sub ==null){
            sub =sql.loadUserByName(arg_user_name);
        }
        return sub;
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
        sql.insertBlob("Game"+g.getGameId(),g);

        //save game to database
        for(Event e :g.getEventList()){
            sql.insert("EventInGame",new Object[]{g.getGameId(),e.getEventTime().toLocalTime(),e.getPlayer(),e.getEventType()});
        }
        sql.insert("Game",new Object[]{g.getGameId(),g.getField(),g.getDate(),g.getStartTime().toLocalTime(),g.getEndTime().toLocalTime(),g.getHost().getName()
            ,g.getGuest().getName(),g.getLeague(),g.getSeason(),g.getHeadReferee(),g.getLinesman1Referee(),g.getLinesman2Referee()});
    }

    public static void updateEventGame(Game g){
        //save game to database
        for(Event e :g.getEventList()){
            if(!e.inDB) {
                sql.insert("EventInGame", new Object[]{g.getGameId(), e.getEventTime().toLocalTime(), e.getPlayer(), e.getEventType()});
                e.inDB = true;
            }
        }
        sql.updateBlob("Blobs","Game"+g.getGameId(),g);
    }

    public static int updateSingleEvent(Event e, Game g){
        //update single event to database
        sql.updateBlob("Blobs","Game"+g.getGameId(),g);

        int a = sql.update("EventInGame",new String[]{g.getGameId()+"",e.getEventTime().toLocalTime()+""},"playerName",e.getPlayer());
        int b = sql.update("EventInGame",new String[]{g.getGameId()+"",e.getEventTime().toLocalTime()+""},"eventType",e.getEventType());
        // ([gameID],  [eventTime], [playerName],  [eventType])
        if(a==1 && b==1){
            return 1;
        }
        return 0;
    }

    /**
     * This function get game_id and return Game
     * @param game_id -
     * @return Game
     */
    public static Game getGame(int game_id){


        return sql.loadGameInfo(game_id);
    }

    public static void updateGame(Game g){
        sql.updateBlob("Blobs","Game"+g.getGameId(),g);
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
        mySubscription.add(sub);
        sql.insert("Users",new Object[]{sub.getUserName(),sub.getPassword(),sub.getRole(),sub.getEmail(),sub.getNumberAlerts()});
        sql.insertBlob(sub.getUserName()+"Permissions",sub.getPermissions());
        sql.insertBlob(sub.getUserName()+"Alerts",sub.getAlerts());
        sql.insertBlob(sub.getUserName()+"searchHistory",sub.getSearch());
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

    public static void removeSubscription(String user_name){
        //Subscription.remove(containSubscription(user_name));
        sql.delete("Users",new String[]{user_name});
        logger.log("DataManagement :remove Subscription , name: " + user_name);
    }

    public  static void updateGeneralsOfSubscription(Subscription sub) {
        sql.update("Users",new String[]{sub.getUserName()},"alerts",sub.getNumberAlerts());
        sql.updateBlob("Blobs",sub.getUserName()+"Permissions",sub.getPermissions());
        sql.updateBlob("Blobs",sub.getUserName()+"Alerts",sub.getAlerts());
        sql.updateBlob("Blobs",sub.getUserName()+"searchHistory",sub.getSearch());
        if(sub instanceof Referee){
            sql.update("UsersData",new String[]{sub.getUserName(),"refereeGames"},"dataValue",((Referee) sub).gamesListToString());
        }
    }

    public static void addInfo(Subscription sub,String role){
        if(role.equals("Coach") || role.equals("coach") ){
            sql.insert("UsersData",new String[]{sub.getUserName(),"qualification",((UnifiedSubscription) sub).getQualification()});
            sql.insert("UsersData",new String[]{sub.getUserName(),"roleInTeam",((UnifiedSubscription) sub).getRoleInTeam()});
            sql.insertBlob(sub.getUserName()+"CoachPersonalPage",((UnifiedSubscription) sub).getCoachPersonalPage());
        }if(role.equals("Player") || role.equals("player") ){
            sql.insert("UsersData",new String[]{sub.getUserName(),"position",((UnifiedSubscription)sub).getPosition()});
            sql.insertBlob(sub.getUserName()+"PlayerPersonalPage",((UnifiedSubscription) sub).getPlayerPersonalPage());
        }if(role.equals("TeamManager") || role.equals("teammanager")){
            sql.insert("UsersData",new String[]{sub.getUserName(),"managerAppointedByTeamOwner",((UnifiedSubscription)sub).teamManager_getAppointedByTeamOwner()});
        }if(role.equals("TeamOwner")|| role.equals("teamowner") ){
            sql.insert("UsersData",new String[]{sub.getUserName(),"ownerAppointedByTeamOwner",((UnifiedSubscription)sub).teamOwner_getAppointedByTeamOwner()});
        }
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
        sql.updateBlob("Team", team.getName(), team);
    }

    public static void addToListTeam(Team team){
        list_team.add(team);
        HashSet<Subscription> list = getSystemAdministratorsList();
        for (Subscription s : list) {
            team.addObserver((SystemAdministrator)s);
        }
        sql.insertBlob(team.getName(),team);
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
        sql.updateBlob("Blobs", "Season"+league+season.getYear(),season);
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

    public static void saveError(String trace) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();
        String savelog = dtf.format(now) +" : "+ trace;
        sql.insert("errors",new Object[]{savelog});
    }

    public static void loadSub(Subscription sub) {
        mySubscription.add(sub);
    }

}
