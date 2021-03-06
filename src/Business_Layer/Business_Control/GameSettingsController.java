package Business_Layer.Business_Control;

import Business_Layer.Business_Items.Game.*;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.*;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Enum.EventType;
import Business_Layer.Enum.PermissionAction;
import DB_Layer.JDBC.DatabaseManager;
import DB_Layer.logger;
import Service_Layer.Spelling;
import Service_Layer.StartSystem;
import org.omg.PortableInterceptor.SUCCESSFUL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class GameSettingsController {


/*----------------------------------------------------------create Game------------------------------------------------------------------------*/


    /**
     * policy of league
     * @param seasonName -
     * @param policy - 1 = AllForAll, 2 = AllForAllTwo
     */
    public ActionStatus schedulingGame(String leagueParameter, String seasonName, int policy){
        ActionStatus AC = new ActionStatus(false,"The operation was not performed on the system.");
        if(DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.setting_games)) {
            boolean flag = false;
            League league = DataManagement.findLeague(leagueParameter);
            Season season = league.getSeason(seasonName);
            // TODO - TIME
            if (season != null) {
                flag = true;
                HashSet<Team> teamsInSeason = season.getListOfTeams();
                if(teamsInSeason.size() < 2){
                    AC = new ActionStatus(false, "Error - The number of teams in league less than 2.");
                }else if(!getRefereesFromSeason(season)){
                    AC = new ActionStatus(false, "Error - The number of referees in league less than 3.");
                }else{
                    FactoryPolicyScheduling factoryPolicyScheduling = new FactoryPolicyScheduling();
                    SchedulingGame policyCreateGame = factoryPolicyScheduling.definePolicy(policy);
                    if(policyCreateGame == null){
                        AC = new ActionStatus(false, "Error - The number of policies entered is invalid.");
                    }else{
                        policyCreateGame.algorithm(teamsInSeason, season);
                        AC = new ActionStatus(true, "The games were created according to the policy set.");
                        DataManagement.updateSeason(leagueParameter,season);
                    }
                }
            }


            if(!flag){
                AC = new ActionStatus(false, "This season was not in the system.");
            }
        }else{
            AC = new ActionStatus(false, "You are not authorized to perform this action.");
        }
        return AC;
    }

    /**
     *Check if there are three referees in the league
     * @param season  -
     * @return -
     */
    private boolean getRefereesFromSeason(Season season) {
        HashSet<Referee> referees = season.getListOfReferees();
        return referees.size() >= 3;
    }



/*---------------------------------------------------------update point policy----------------------------------------------------------------*/

    /**
     * update point policy of Season -
     * The league table is updated according to a number of wins, losses and equal
     * @param league_name -
     * @param year -
     * @param win -
     * @param lose -
     * @param equal -
     * @return ActionStatus
     */
    public ActionStatus updatePointsPolicy(String league_name, String year,int win, int lose, int equal){
        ActionStatus AC;
        if(win < 0 || lose < 0 || equal < 0){
            AC = new ActionStatus(false,"Policy details below 0 are therefore invalid.");
        }else{
            League league = DataManagement.findLeague(league_name);
            if (year!=null && league_name!=null && DataManagement.getCurrent() instanceof UnionRepresentative && league!=null) {
                int intFormatYear= Integer.parseInt(year);
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int yearNow  = localDate.getYear();
                if(yearNow >=intFormatYear ){
                    AC = new ActionStatus(false,"The season has already begun You are not allowed to change policies.");
                }
                else if(league.getSeason(year)!=null){
                    // TODO - STOP - SHOW Anatoly step 1 - PointsPolicy before
                    Season season = league.getSeason(year);
                    season.setScoreTable(new ScoreTable(new PointsPolicy(win,lose,equal)));
                    // TODO - STOP - SHOW Anatoly step 2 - PointsPolicy after
                    DataManagement.updateSeason(league_name,season);
                    AC = new ActionStatus(true,"The policy has been changed successfully.");
                }else{
                    AC = new ActionStatus(false,"No season exists in the league.");
                }
            }else{
                AC = new ActionStatus(false,"One of the fields is incorrect..");
            }
        }
        return AC;

    }

    /*------------------------------------------------------define League-----------------------------------------------------------------*/

    /**
     * this function let the union rep to define a league
     * @param name is the name of the league
     * @return true if it was defined
     */
    public ActionStatus defineLeague(String name){
        ActionStatus AC;
        if (name!=null && DataManagement.getCurrent() instanceof UnionRepresentative && DataManagement.findLeague(name)==null) {
            DataManagement.addToListLeague(new League(name));
            Spelling.updateDictionary("league: " + name);
            AC = new ActionStatus(true, "The league was successfully added to the system.");
        }else{
            AC = new ActionStatus(false, "One of the parameters is incorrect, you are not allowed to perform the action or the league name is invalid.");

        }
        return AC;
    }
    /*------------------------------------------define Season To League--------------------------------------------------*/

    /**
     * this function let the union rep to define a season to a leauge
     * @param league_name is the name of the leauge
     * @param year is the season
     * @return true if the season was updated
     */
    public ActionStatus defineSeasonToLeague(String league_name, String year,int win, int lose, int equal){
        ActionStatus AC;
        boolean ans = false;
        if(win < 0 || lose < 0 || equal < 0){
            AC = new ActionStatus(false, "The parameters cannot be less than 0.");
        }
        League league =  DataManagement.findLeague(league_name);
        if (year!=null && league_name!=null && DataManagement.getCurrent() instanceof UnionRepresentative && league!=null) {
            int intFormatYear= Integer.parseInt(year);
            if(league.getSeason(year)!= null){
                AC = new ActionStatus(false, "The season define in system.");
            }else{
                if (intFormatYear>1900 && intFormatYear<2022){
                    Season addSeason =new Season(year);
                    league.addSeason(addSeason);
                    PointsPolicy pointsPolicy = new PointsPolicy(win,lose,equal);
                    ScoreTable scoreTable = new ScoreTable(pointsPolicy);
                    addSeason.setScoreTable(scoreTable);
                    DataManagement.addNewSeason(league_name,addSeason,pointsPolicy);
                    Spelling.updateDictionary("season: " + league_name);
                    AC = new ActionStatus(true, "The season has been set successfully in the league.");
                }else{
                    AC = new ActionStatus(false, "The year must be less than 2022 and greater than 1900.");

                }

            }

        }else{
            AC = new ActionStatus(false, "One of the data is incorrect.");

        }
        logger.log("Settings controller: defineSeasonToLeague, league name: "+ league_name+" ,year: "+year +" ,successful: "+ ans);
        return AC;
    }

    /**
     * Allows to add a team to a season in league. Only the union representative can perform this (with the permissions received by default)
     */
    public ActionStatus addTeamToSeasonInLeague(String teamName, String leagueName, String seasonName){
        ActionStatus AC;
        Team team = DataManagement.findTeam(teamName);
        League league = DataManagement.findLeague(leagueName);
        Subscription cur = DataManagement.getCurrent();
        if (team == null || league == null || cur == null){
            AC = new ActionStatus(false,"Cannot find team or league (try again)");
        }
        else {
            Season season = league.getSeason(seasonName);
            if (season == null) {
                AC = new ActionStatus(false, "Season does not exist in this league");
            } else {
                //check permissions
                if (cur.getPermissions().check_permissions(PermissionAction.add_team_to_season)) {
                    season.addTeam(team);
                    DataManagement.updateSeason(leagueName,season);
                    AC = new ActionStatus(true,"Team added successfully");
                } else {
                    AC = new ActionStatus(false, "You do not have permissions to perform this action");
                }
            }
        }
        return AC;
    }

    /*------------------------------------------referee function--------------------------------------------------*/

    /**
     * The main referee and the game. The referee updates the score ans win
     * @param gameId -
     * @param hostGoals -
     * @param guestGoals -
     * @param seasonYear -
     * @param league -
     * @return ActionStatus
     */
    public ActionStatus endGame(int gameId, int hostGoals, int guestGoals, String seasonYear, String league) {
        ActionStatus AC;
        League league1 =  DataManagement.findLeague(league);
        Game game = DataManagement.getGame(gameId);
        if( game== null){
            AC = new ActionStatus(false,"The game does not exist in the system.");
        }
        else if(game.getDate().isAfter(LocalDate.now())){
            AC = new ActionStatus(false,"The time of the game has not arrived.");
        }
        else if(league1 == null){
            AC = new ActionStatus(false,"The league does not exist in the system.");
        }
        else if(league1.getSeason(seasonYear) == null){
            AC = new ActionStatus(false,"The League doesn't exist season in the year Of " + seasonYear);
        }
        else if(!game.getHeadReferee().equals(DataManagement.getCurrent())){
            AC = new ActionStatus(false,"You are not set as the main referee in the game and therefore you are not allowed to close a game.");
        }
        else if(league1.getSeason(seasonYear).getScoreTable() == null){
            AC = new ActionStatus(false,"The score table doesn't exist yet.");
        }else{
            game.endGame(league1.getSeason(seasonYear).getScoreTable(), hostGoals, guestGoals);
            AC = new ActionStatus(false,"successfully end game.");
            DataManagement.updateGame(game);
        }
        return AC;
    }


    /**
     * This function let the union rep to add a referee to the system
     * int add_or_remove
     * @param referee_user_name is the username of the referee
     * @param referee_password is the password of the referee
     * @param mail is the password of the referee
     * @param add_or_remove is 0 to add and 1 to remove
     * @return true if the operation succeeded
     */
    public ActionStatus addOrDeleteRefereeToSystem(String referee_user_name, String referee_password, String mail, int add_or_remove){
        ActionStatus ac;
        if (DataManagement.getCurrent() instanceof UnionRepresentative) {
            if (referee_user_name != null && referee_password != null) {
                Subscription current_referee = DataManagement.containSubscription(referee_user_name);
                if (add_or_remove == 1 && current_referee == null) { //add
                    ac = StartSystem.LEc.Registration(referee_user_name, referee_password,"Referee", mail);
                    String mail_content= "Hello! you were invited to our system! your username: "+referee_user_name+" and you password: "+referee_password;
                    //DataManagement.containSubscription(referee_user_name).sendEMail(mail,mail_content);
                    Subscription ref = DataManagement.containSubscription(referee_user_name);
                    Spelling.updateDictionary("referee: " + referee_user_name);
                    if(ref!= null){
                        ref.addAlert(mail_content);
                        DataManagement.updateGeneralsOfSubscription(ref);
                    }else{
                        ac = new ActionStatus(false,"illegal parameter");
                    }
                } else if (add_or_remove == 0) { //remove
                    if (current_referee != null) {
                        ac = StartSystem.LEc.RemoveSubscription(referee_user_name);
                    }else{
                        ac = new ActionStatus(false,"No subscription exists in the system");
                    }
                }else{
                    ac = new ActionStatus(false,"number illegal");
                }
            }else{
                ac = new ActionStatus(false,"Username or password cannot be empty");
            }
        }
        else{
            ac = new ActionStatus(false,"You are not authorized to perform this action.");
        }
        logger.log("Settings controller: addOrDeleteRefereeToSystem, referee name: "+ referee_user_name +" ,add or remove: "+add_or_remove +" ,successful: "+ ac.isActionSuccessful() +", "+ ac.getDescription());
        return ac;
    }


    /**
     * This function let the union rep to add a referee to the system
     * @param league_name -
     * @param referee_user_name -
     * @param season_year -
     * @return ActionStatus
     */
    public ActionStatus defineRefereeInLeague(String league_name, String referee_user_name, String season_year) {
        ActionStatus ac;
        League league = DataManagement.findLeague(league_name);
        Subscription referee = DataManagement.containSubscription(referee_user_name);
        if (league != null && referee instanceof Referee) {
            Season season = league.getSeason(season_year);
            if (season!=null && season.getReferee(referee_user_name)== null){
                season.addReferee((Referee)referee);
                DataManagement.updateSeason(league_name,season);
                ac = new ActionStatus(true, "set successfully");
            }
            else{
                ac = new ActionStatus(false, "season not exists or referee already in this season");
            }
        }
        else{
            ac = new ActionStatus(false, "league or referee user not exists");
        }
        logger.log("Settings controller: defineRefereeInLeague, league name: "+ league_name +" ,referee name: "+referee_user_name+" ,season: "+season_year +" ,successful: "+ ac.isActionSuccessful() +" , "+ac.getDescription());
        return ac;
    }


    private static LocalDateTime parserTime(String date){
        //time = Sat May 16 2020 11:50:26 GMT+0300 (Israel Daylight Time)
        try {
            String[] parser = date.split(" ");
            String[]  time= parser[4].split(":");
            int month = 1;
            if(parser[1].equals("Jan")){
                month = 1;
            }else if(parser[1].equals("Feb")){
                month = 2;
            }else if(parser[1].equals("Mar")){
                month = 3;
            }else if(parser[1].equals("Apr")){
                month = 4;
            }else if(parser[1].equals("May")){
                month = 5;
            }else if(parser[1].equals("Jun")){
                month = 6;
            }else if(parser[1].equals("Jul")){
                month = 7;
            }else if(parser[1].equals("Aug")){
                month = 8;
            }else if(parser[1].equals("Sep")){
                month = 9;
            }else if(parser[1].equals("Oct")){
                month = 10;
            }else if(parser[1].equals("Nov")){
                month = 11;
            }else if(parser[1].equals("Dec")){
                month = 12;
            }
            return LocalDateTime.of(Integer.parseInt(parser[3]),month, Integer.parseInt(parser[2]),
                    Integer.parseInt(time[0]),Integer.parseInt(time[1]),Integer.parseInt(time[2]));
        }catch (Exception e){
            return LocalDateTime.now();
        }
    }


    /**
     * A referee can add and edit events that happened in the game
     * @param game_id  -
     * @param arg_team -
     * @param arg_event_type -
     * @param arg_player -
     * @return ActionStatus
     */
    public ActionStatus refereeEditGameEvent(int game_id, String arg_team, String arg_event_type, String arg_player,
                                            String time){
        ActionStatus AC = new ActionStatus(false, "one of details incorrect."); ;
        Game game = DataManagement.getGame(game_id);
        Team team = DataManagement.findTeam(arg_team);
        boolean flag = false;
        boolean flag_player = false;
        EventType eventType = getEventFromString(arg_event_type) ;
        Subscription player = DataManagement.containSubscription(arg_player);
        if(player instanceof UnifiedSubscription){
            if(((UnifiedSubscription) player).isAPlayer()){
                flag_player = true;
            }
        }
        if ( flag_player  &&  eventType != null && game != null && game.getHeadReferee() != null && game.getHeadReferee().equals(DataManagement.getCurrent().getUserName())){
            for (Event currentEvent : game.getEventList()){
                if (currentEvent.getEventTime().equals(parserTime(time))){
                    if (game.getHost().equals(team)){
                        if (arg_player!=null && game.getHost().getPlayer(player.getUserName()).equals(player)){
                            currentEvent.setPlayer((UnifiedSubscription)player);
                            currentEvent.setTeam(team);
                            currentEvent.setEventType(eventType);
                            int a = DataManagement.updateSingleEvent(currentEvent,game);
                            if(a==1) {
                                AC = new ActionStatus(true, "The event was edited");
                            }else{
                                AC = new ActionStatus(false, "change not made");
                            }
                        }
                        else{
                            AC = new ActionStatus(false, "the player does not play in that team");
                        }
                    }
                    else if (game.getGuest().equals(team)){
                        if (arg_player!=null && game.getGuest().getPlayer(player.getUserName()).equals(player)){
                            currentEvent.setTeam(team);
                            currentEvent.setPlayer((UnifiedSubscription)player);
                            currentEvent.setEventType(eventType);
                            int a = DataManagement.updateSingleEvent(currentEvent,game);
                            if(a==1) {
                                AC = new ActionStatus(true, "The event was edited");
                            }else{
                                AC = new ActionStatus(false, "change not made");
                            }
                        }
                        else{
                            AC = new ActionStatus(false, "the player does not play in that team");
                        }
                    }
                    else{
                        AC = new ActionStatus(false, "the team is not a part in the game");
                    }
                    flag = true;
                }
            }
            if(!flag){
                    AC = new ActionStatus(false, "there was not event in this time");
            }
        }
        else{
            AC = new ActionStatus(false,"The game data is incorrect.");
        }
        return AC;
    }

    /**
     * get all event in game
     * @param game_id -
     * @return String
     */
    public ActionStatus printGameEvents(int game_id){
        ActionStatus AC;
        StringBuilder eventList= new StringBuilder("");
        Game game = DataManagement.getGame(game_id);
        if(game != null){
            HashSet<Event> events = game.getEventList();
            for (Event e : events ){
                String eventString = e.toString();
                eventList.append(eventString).append("!!!!");
            }
            AC = new ActionStatus(true,eventList.toString());
        }else{
            eventList.append("No game with this id.");
            AC = new ActionStatus(false,eventList.toString());
        }
        return AC;
    }


    /**
     * get event type from string
     * if event type not exists return null
     * @param str  -
     * @return EventType
     */
    private EventType getEventFromString(String str){
        if(Arrays.stream(EventType.values()).noneMatch(e -> e.name().equals(str))){
            return null;
        }else{
            return EventType.valueOf(str);
        }
    }

    /**
     * This function lets a referee in a game to update a new event
     * @param game_id -
     * @param team_name -
     * @param player_name -
     * @param event -
     * @return true if operation succeeded
     */
    public ActionStatus refereeCreateNewEvent(int game_id, String team_name, String player_name, String event ){
        ActionStatus ac;
        Game game = DataManagement.getGame(game_id);
        Team team = DataManagement.findTeam(team_name);
        EventType eventType = getEventFromString(event);
        if(eventType == null){
            ac = new ActionStatus(false,"The Event Type does not exist.") ;
        }
        else if(game == null ){
            ac = new ActionStatus(false,"The game id does not exist.") ;
        }
        else if(team== null){
            ac = new ActionStatus(false,"The team id does not exist.");
        }
        else if(team.getPlayer(player_name) == null){
            ac = new ActionStatus(false,"The player does not exist in the team.");
        }
        else if(event==null){
            ac = new ActionStatus(false,"The event type does not exist.");
        }
        else if ((DataManagement.getCurrent() instanceof Referee) && DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.update_event)){
            // check if the referee is a referee of the team
            if ((game.getHeadReferee()!=null && game.getHeadReferee().equals(DataManagement.getCurrent().getUserName()) )||
                    game.getLinesman1Referee().equals(DataManagement.getCurrent().getUserName()) ||
                    game.getLinesman2Referee().equals(DataManagement.getCurrent().getUserName()) ){

                ac = game.updateNewEvent(team_name,player_name,eventType);
            }else{
                ac = new ActionStatus(false,"You are not a judge of the current game.");
            }
        }else{
            ac = new ActionStatus(false,"You may not take this action.");
        }
        logger.log("Game controller: refereeCreateNewEvent, team: "+team_name +" , player: "+ player_name +" ,event : " +event +" ,created:  "+ ac.getDescription());
        return ac;
    }

    /**
     * This function shows a referee which games he participates in
     * @return - ActionStatus
     */
    public ActionStatus refereeWatchGames(){
        Subscription sub =DataManagement.getCurrent();
        if ( sub instanceof Referee){
            Referee current = (Referee)sub;
            return new ActionStatus(true,current.gamesListToString());
        }
        return new ActionStatus(false,"You are not a referee!");
    }

    /*------------------------------------------display ScoreTable-------------------------------------------------*/


    /**
     *The function returns the scoring table for the season
     */
    public ActionStatus displayScoreTable(String league, String seasonYear){
        ActionStatus AC;
        League leagueObject = DataManagement.findLeague(league);
        if(leagueObject == null){
            AC = new ActionStatus(false,"The system doesn't exist league with the name: " + league) ;
        }
        else if(leagueObject.getSeason(seasonYear) == null){
            AC = new ActionStatus(false,"The League doesn't exist season in the year of: " + seasonYear) ;
        }
        else if(leagueObject.getSeason(seasonYear).getScoreTable() == null){
            AC = new ActionStatus(false,"The score table doesn't exist yet") ;
        }else{
            AC = new ActionStatus(true,leagueObject.getSeason(seasonYear).getScoreTable().toString()) ;

        }
        return AC;
    }


}
