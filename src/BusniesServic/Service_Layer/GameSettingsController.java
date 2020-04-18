package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Game.*;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Enum.EventType;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;
import Presentation_Layer.Spelling;
import Presentation_Layer.StartSystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class GameSettingsController {


    /**
     * this function let the union rep to define a leauge
     * @param name is the name of the leauge
     * @return true if it was defined
     */
    public boolean defineLeague(String name){
        if (name!=null && DataManagement.getCurrent() instanceof UnionRepresentative) {
            DataManagement.addToListLeague(new League(name));
            Spelling.updateDictionary("league: " + name);
            return true;
        }
        return false;
    }

    /**
     * this function let the union rep to define a season to a leauge
     * @param league_name is the name of the leauge
     * @param year is the season
     * @return true if the season was updated
     */
    public boolean defineSeasonToLeague(String league_name, String year,int win, int lose, int equal){
        boolean ans = false;
        if(win < 0 || lose < 0 || equal < 0){
            return ans;
        }
        if (year!=null && league_name!=null && DataManagement.getCurrent() instanceof UnionRepresentative && DataManagement.findLeague(league_name)!=null) {
            int intFormatYear= Integer.parseInt(year);
            if (intFormatYear>1900 && intFormatYear<2021){
                Season addSeason =new Season(year);
                DataManagement.findLeague(league_name).addSeason(addSeason);
                PointsPolicy pointsPolicy = new PointsPolicy(win,lose,equal);
                ScoreTable scoreTable = new ScoreTable(pointsPolicy);
                addSeason.setScoreTable(scoreTable);
                ans = true;
                Spelling.updateDictionary("season: " + league_name);
            }

        }
        logger.log("Settings controller: defineSeasonToLeague, league name: "+ league_name+" ,year: "+year +" ,successful: "+ ans);
        return ans;
    }

    /**
     * update point policy of Season
     * @param league_name
     * @param year
     * @param win
     * @param lose
     * @param equal
     * @return
     */
    public ActionStatus updatePointsPolicy(String league_name, String year,int win, int lose, int equal){
        ActionStatus AC;
        if(win < 0 || lose < 0 || equal < 0){
            AC = new ActionStatus(false,"Policy details below 0 are therefore invalid.");
        }else{
          if (year!=null && league_name!=null && DataManagement.getCurrent() instanceof UnionRepresentative && DataManagement.findLeague(league_name)!=null) {
              int intFormatYear= Integer.parseInt(year);
              Date date = new Date();
              LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
              int yearNow  = localDate.getYear();
              if(yearNow >=intFormatYear ){
                  AC = new ActionStatus(false,"The season has already begun You are not allowed to change policies.");
              }
              else if(DataManagement.findLeague(league_name).getSeason(year)!=null){
                    DataManagement.findLeague(league_name).getSeason(year).setScoreTable(new ScoreTable(new PointsPolicy(win,lose,equal)));
                  AC = new ActionStatus(true,"The policy has been changed successfully.");
              }else{
                  AC = new ActionStatus(false,"One of the fields is incorrect..");
              }
            }else{
              AC = new ActionStatus(false,"One of the fields is incorrect..");
            }
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
        ActionStatus ac = null;
        if (DataManagement.getCurrent() instanceof UnionRepresentative) {
            if (referee_user_name != null && referee_password != null) {
                Subscription current_referee = DataManagement.containSubscription(referee_user_name);
                if (add_or_remove == 0 && current_referee != null) {
                    ac = StartSystem.LEc.Registration(referee_user_name, referee_password,"Referee", mail);
                    String mail_content= "Hello! you were invited to our system! your username: "+referee_user_name+" and you password: "+referee_password;
                    DataManagement.getSubscription(referee_user_name).sendEMail(mail,mail_content);
                    Spelling.updateDictionary("referee: " + referee_user_name);
                } else if (add_or_remove == 1) {
                    if (current_referee != null) {
                        ac = StartSystem.LEc.RemoveSubscription(referee_user_name);
                    }
                }
            }
        }
        logger.log("Settings controller: addOrDeleteRefereeToSystem, referee name: "+ referee_user_name +" ,add or remove: "+add_or_remove +" ,successful: "+ ac.isActionSuccessful() +", "+ ac.getDescription());
        return ac;
    }

    /**
     * This function let the union rep to add a referee to the system
     * @param league_name
     * @param referee_user_name
     * @param season_year
     * @return
     */
    public ActionStatus defineRefereeInLeague(String league_name, String referee_user_name, String season_year) {
        ActionStatus ac = null;
        League league = DataManagement.findLeague(league_name);
        Subscription referee = DataManagement.containSubscription(referee_user_name);
        if (league != null && referee!=null && referee instanceof Referee) {
            Season season = league.getSeason(season_year);
            if (season!=null){
                season.addReferee((Referee)referee);
                ac = new ActionStatus(true, "set successfully");
            }
            else{
                ac = new ActionStatus(false, "season not exists");
            }
        }
        else{
            ac = new ActionStatus(false, "league or referee user not exists");
        }
        logger.log("Settings controller: defineRefereeInLeauge, leauge name: "+ league_name +" ,referee name: "+referee_user_name+" ,season: "+season_year +" ,successful: "+ ac.isActionSuccessful() +" , "+ac.getDescription());
        return ac;
    }


    public ActionStatus createGame(LocalDate date, String field, String host, String guest,
                              String headReferee, String line1Referee, String line2Referee){
        ActionStatus AC = null;
        if (! (DataManagement.getCurrent() instanceof SystemAdministrator)){
            AC = new ActionStatus(false, "You are not a system administrator");
        }
        else if(date==null || field==null || host==null || guest==null || headReferee==null || line1Referee==null || line2Referee==null){
            AC = new ActionStatus(false, "one of the parameters is null");
        }
        else if(DataManagement.findTeam(host)==null){
            AC = new ActionStatus(false, "The host team doesnt exist in the system");
        }
        else if(DataManagement.findTeam(guest)==null){
            AC = new ActionStatus(false, "The guest team doesnt exist in the system");
        }
        else if(!(DataManagement.getSubscription(headReferee) instanceof Referee) || !(DataManagement.getSubscription(line1Referee) instanceof Referee)
        || !(DataManagement.getSubscription(line2Referee) instanceof Referee)){
            AC = new ActionStatus(false, "One of the referees is not defined in the system");
        }
        else{
            createGameAfterChecks(date, field, host, guest, headReferee, line1Referee, line2Referee);
            AC = new ActionStatus(true, "The game was created successfully");
        }
        return AC;
    }

    /**
     * Creates a game after checking all the fields are valid
     * Cannot receive null or illegal parameters
     * @param date notnull
     * @param field notnull
     * @param host notnull
     * @param guest notnull
     * @param headReferee notnull
     * @param line1Referee notnull
     * @param line2Referee notnull
     */
    private Game createGameAfterChecks(LocalDate date, String field, String host, String guest, String headReferee, String line1Referee, String line2Referee) {
        Game g = new Game(field, date, DataManagement.findTeam(host),DataManagement.findTeam(guest));
        g.setLinesman1Referee((Referee)DataManagement.getSubscription(line1Referee));
        g.setLinesman2Referee((Referee)DataManagement.getSubscription(line2Referee));
        g.setHeadReferee((Referee)DataManagement.getSubscription(headReferee));
        DataManagement.addGame(g);
        return g;
    }


    /**
     * This function lets a referee in a game to update a new event
     * @param game_id
     * @param team_name
     * @param player_name
     * @param event
     * @return true if operation succeeded
     */
    public ActionStatus refereeCreateNewEvent(int game_id, String team_name, String player_name, EventType event ){
        ActionStatus ac = null;
        Game game = DataManagement.getGame(game_id);
        if(game == null ){
            ac = new ActionStatus(false,"The game id does not exist.") ;

        }
        else if(DataManagement.findTeam(team_name)== null){
            ac = new ActionStatus(false,"The team id does not exist.");
        }
        else if(DataManagement.findTeam(team_name).getPlayer(player_name) == null){
            ac = new ActionStatus(false,"The player does not exist in the team.");
        }
        else if(event==null){
            ac = new ActionStatus(false,"The event type does not exist.");
        }
        else if ((DataManagement.getCurrent() instanceof Referee) &&    DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.update_event)){
            // check if the referee is a referee of the team
            if ((game.getHeadReferee()!=null && game.getHeadReferee().getUserName().equals(DataManagement.getCurrent().getUserName()) )||
                    game.getLinesman1Referee().getUserName().equals(DataManagement.getCurrent().getUserName()) ||
                    game.getLinesman2Referee().getUserName().equals(DataManagement.getCurrent().getUserName()) ){
                game.updateNewEvent(team_name,player_name,event);
                ac = new ActionStatus(true, "Event successfully updated.");
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
     * This function shows a referee which games he particiapates in
     * @return
     */
    public ActionStatus refereeWatchGames(){
        if (DataManagement.getCurrent() instanceof Referee){
            Referee current = (Referee)DataManagement.getCurrent();
            return new ActionStatus(true,current.gamesListToString());
        }
        return new ActionStatus(false,"You are not a referee!");
    }

    /**
     *
     */
    public String displayScoreTable(String league, String seasonYear){
        League leagueObject = DataManagement.findLeague(league);
        if(leagueObject == null){
            return "The system doesn't exist league with the name: " + league;
        }
        Season seasonObject = leagueObject.getSeason(seasonYear);
        if(seasonObject == null){
            return "The League doesn't exist season in the year of: " + seasonYear;
        }
        if(seasonObject.getScoreTable() == null){
            return "The score table doesn't exist yet";
        }
        return seasonObject.getScoreTable().toString();
    }

    /**
     * @param gameId
     * @param hostGoals
     * @param guestGoals
     * @param seasonYear
     * @param league
     * @return
     */
    public ActionStatus endGame(int gameId, int hostGoals, int guestGoals, String seasonYear, String league) {
        ActionStatus AC;
        if(DataManagement.getGame(gameId) == null){
            AC = new ActionStatus(false,"The game does not exist in the system.");
        }
        else if( DataManagement.findLeague(league) == null){
        }
        if(DataManagement.findLeague(league).getSeason(seasonYear) == null){
            AC = new ActionStatus(false,"The League doesn't exist season in the year Of " + seasonYear);
        }
        if(!DataManagement.getGame(gameId).getHeadReferee().equals(DataManagement.getCurrent())){
            AC = new ActionStatus(false,"You are not set as the main referee in the game and therefore you are not allowed to close a game.");
        }
        if(DataManagement.findLeague(league).getSeason(seasonYear).getScoreTable() == null){
            AC = new ActionStatus(false,"The score table doesn't exist yet.");
        }else{
            DataManagement.getGame(gameId).endGame(DataManagement.findLeague(league).getSeason(seasonYear).getScoreTable(), hostGoals, guestGoals);
            AC = new ActionStatus(false,"successfully end game.");
        }
        return AC;
    }
    public ActionStatus refereeEditGameEvent(int game_id, Team arg_team, EventType arg_event_type, Player arg_player, LocalDateTime eventTime){
        ActionStatus AC = null;
        Game game = DataManagement.getGame(game_id);
        if (game.getHeadReferee().equals(DataManagement.getCurrent())){
            for (Event currentEvent : game.getEventList()){
                if (currentEvent.getEventTime().equals(eventTime)){
                    if (arg_team!=null && game.getHost().equals(arg_team)){
                        currentEvent.setTeam(arg_team);
                        if (arg_player!=null && game.getHost().getPlayer(arg_player.getUserName()).equals(arg_player)){
                            currentEvent.setPlayer(arg_player);
                        }
                        else{
                            AC = new ActionStatus(false, "the player doesnt play in that team");
                        }
                    }
                    else if (arg_team!=null && game.getGuest().equals(arg_team)){
                        currentEvent.setTeam(arg_team);
                        if (arg_player!=null && game.getGuest().getPlayer(arg_player.getUserName()).equals(arg_player)){
                            currentEvent.setPlayer(arg_player);
                        }
                        else{
                            AC = new ActionStatus(false, "the player doesnt play in that team");
                        }
                    }
                    else{
                        AC = new ActionStatus(false, "the team is not a part in the game");
                    }
                    currentEvent.setEventType(arg_event_type);
                }
                else{
                    AC = new ActionStatus(false, "there was not event in this time");
                }
            }
            if (AC!=null) {
                AC = new ActionStatus(true, "The events were edited");
            }
        }
        else{
            AC = new ActionStatus(false,"you are not the head referee in the game.");
        }
        return AC;
    }

    public String printGameEvents(int game_id){
        String eventList="";
        Game g = DataManagement.getGame(game_id);
        for (Event e : g.getEventList()){
            eventList+=e.toString()+"/n";
        }
        return eventList;
    }


    public EventType getEventFromString(String str){
        if(! (Arrays.stream(EventType.values()).anyMatch(e -> e.name().equals(str)))){
            return null;
        }else{
            EventType enumEvent =  EventType.valueOf(str);
            return enumEvent;
        }
    }

    /**
     * Allows to add a team to a season in league. Only the union representative can perform this (with the permissions received by default)
     */
    public ActionStatus addTeamToSeasonInLeague(String teamName, String leagueName, String seasonName){
        ActionStatus AC = null;
        Team team = DataManagement.findTeam(teamName);
        League league = DataManagement.findLeague(leagueName);
        if (team == null || league == null){
            AC = new ActionStatus(false,"Cannot find team or league");
        }
        else {
            Season season = league.getSeason(seasonName);
            if (season == null) {
                AC = new ActionStatus(false, "Season does not exist in this league");
            } else {
                //check permissions
                if (DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.add_team_to_season)) {
                    season.addTeam(team);
                    AC = new ActionStatus(true,"Team added successfully");
                } else {
                    AC = new ActionStatus(false, "You do not have permissions to perform this action");
                }
            }
        }
        return AC;
    }

    public void assignGamesInSeason(String seasonName){
        if(DataManagement.getCurrent() != null && DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.setting_games)) {
            // ActionStatus AC = null;
            HashSet<League> allLeagues = DataManagement.getListLeague();
            for (League league : allLeagues) {

                Season season = league.getSeason(seasonName);
                if (season != null) {
                    //the season exists in the league
                    HashSet<Team> teamsInSeason = season.getListOfTeams();
                    //assign the games so each team is a host and guest
                    for (Team host : teamsInSeason) {
                        for (Team guest : teamsInSeason) {
                            //a team will not play with itself
                            if (!host.equals(guest)) {

                                String field = getFieldFromHost(host);
                                String[] threeReferees = getRefereesFromSeason(season);
                                if (threeReferees != null && field != null) {
                                    season.addGame(createGameAfterChecks(getDateForGame(), field, host.getName(), guest.getName(), threeReferees[0], threeReferees[1], threeReferees[2]));
                                }
                            } //host != guest
                        }
                    } //for: host teams
                }// season != null
            }//for: all leagues
        }
    }

    private String[] getRefereesFromSeason(Season season) {
        HashSet<Referee> referees = season.getListOfReferees();
        if(referees.size() < 3)
            return null;
        String [] threeReferees = new String[3];
        int index = 0;
        for (Referee r :referees){
            threeReferees[index] = r.getUserName();
            index ++;
            if (index == 2)
                break;
        }
        return threeReferees;
    }

    private String getFieldFromHost(Team host) {
        HashSet<Object> fields = host.getTeamAssets();
        for (Object field : fields) {
            if (field instanceof String)
                return (String) field;
        }
        return null;
    }

    private LocalDate getDateForGame() {
        return LocalDate.now();
    }

}
