package Busnies_Servic.Service_Layer;

import Busnies_Servic.Business_Layer.Game.Game;
import Busnies_Servic.Business_Layer.UserManagement.Referee;
import Busnies_Servic.EventType;
import Busnies_Servic.PermissionAction;
import DB_Layer.logger;

import java.time.LocalDate;

public class GameController{

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
