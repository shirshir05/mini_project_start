package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Game.Game;
import BusniesServic.Business_Layer.Game.League;
import BusniesServic.Business_Layer.Game.Season;
import BusniesServic.Business_Layer.UserManagement.Referee;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Business_Layer.UserManagement.UnionRepresentative;
import BusniesServic.Enum.EventType;
import BusniesServic.Enum.PermissionAction;
import DB_Layer.logger;
import Presentation_Layer.Spelling;

import java.time.LocalDate;

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
    public boolean defineSeasonToLeague(String league_name, String year){
        boolean ans = false;
        if (year!=null && league_name!=null && DataManagement.getCurrent() instanceof UnionRepresentative) {
            int intFormatYear= Integer.parseInt(year);
            if (intFormatYear>1900 && intFormatYear<2021){
                DataManagement.findLeague(league_name).addSeason(new Season(year));
                ans = true;
                Spelling.updateDictionary("season: " + league_name);
            }

        }
        logger.log("Settings controller: defineSeasonToLeague, league name: "+ league_name+" ,year: "+year +" ,successful: "+ ans);
        return ans;
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
    public boolean addOrDeleteRefereeToSystem(String referee_user_name, String referee_password, String mail, int add_or_remove){
        boolean ans = false;
        if (DataManagement.getCurrent() instanceof UnionRepresentative) {
            if (referee_user_name != null && referee_password != null) {
                Subscription current_referee = DataManagement.containSubscription(referee_user_name);
                if (add_or_remove == 0 && current_referee != null) {
                    Referee current = new Referee(referee_user_name, referee_password, mail);
                    String mail_content= "Hello! you were invited to our system! your username: "+referee_user_name+" and you password: "+referee_password;
                    DataManagement.getCurrent().sendEMail(mail,mail_content);
                    DataManagement.setSubscription(current);
                    ans = true;
                    Spelling.updateDictionary("user: " + referee_user_name);
                } else if (add_or_remove == 1) {
                    if (current_referee != null) {
                        Spelling.updateDictionary("referee: " + referee_user_name);
                        ans =  true;
                    }
                }
            }
        }
        logger.log("Settings controller: addOrDeleteRefereeToSystem, referee name: "+ referee_user_name +" ,add or remove: "+add_or_remove +" ,successful: "+ ans);
        return ans;
    }

    /**
     * This function let the union rep to add a referee to the system
     * @param league_name
     * @param referee_user_name
     * @param season_year
     * @return
     */
    public boolean defineRefereeInLeague(String league_name, String referee_user_name, String season_year) {
        boolean ans = false;
        League league = DataManagement.findLeague(league_name);
        Subscription referee = DataManagement.containSubscription(referee_user_name);
        if (league != null && referee!=null && referee instanceof Referee) {
            Season season = league.getSeason(season_year);
            if (season!=null){
                season.addReferee((Referee)referee);
                ans = true;
            }
        }
        logger.log("Settings controller: defineRefereeInLeauge, leauge name: "+ league_name +" ,referee name: "+referee_user_name+" ,season: "+season_year +" ,successful: "+ ans);
        return ans;
    }


    public boolean createGame(LocalDate date, String filed, String host, String guest){
        //MUST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO must create game with Linesman1Referee, Linesman1Referee,HeadReferee

        return true;
    }


    /**
     * This function lets a referee in a game to update a new event
     * @param game_id
     * @param team_name
     * @param player_name
     * @param event
     * @return true if operation succeeded
     */
    public String refereeCreateNewEvent(int game_id, String team_name, String player_name, EventType event ){
        String explanation = null;
        Game game = DataManagement.getGame(game_id);
        if(game == null ){
            explanation = "The game id does not exist.";

        }
        else if(DataManagement.findTeam(team_name)== null){
            explanation = "The team id does not exist.";
        }
        else if(DataManagement.findTeam(team_name).getPlayer(player_name) == null){
            explanation =  "The player does not exist in the team.";
        }
        else if ((DataManagement.getCurrent() instanceof Referee) &&    DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.update_event)){
            // check if the referee is a referee of the team
            if (game.getHeadReferee().getUserName().equals(DataManagement.getCurrent().getUserName()) ||
                    game.getLinesman1Referee().getUserName().equals(DataManagement.getCurrent().getUserName()) ||
                    game.getLinesman2Referee().getUserName().equals(DataManagement.getCurrent().getUserName()) ){
                game.updateNewEvent(team_name,player_name,event);
                explanation =  "Event successfully updated.";
            }else{
                explanation =  "You are not a judge of the current game";
            }
        }else{
            explanation =  "You may not take this action.";
        }
        logger.log("Game controller: refereeCreateNewEvent, team: "+team_name +" , player: "+ player_name +" ,event : " +event +" ,created:  "+ explanation);
        return explanation;
    }

    /**
     * This function shows a referee which games he particiapates in
     * @return
     */
    public String refereeWatchGames(){
        if (DataManagement.getCurrent() instanceof Referee){
            Referee current = (Referee)DataManagement.getCurrent();
            return current.gamesListToString();
        }
        return "You are not a referee!";
    }

}
