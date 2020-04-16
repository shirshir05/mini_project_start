package BusniesServic.Business_Layer.Game;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Player;
import BusniesServic.Enum.EventType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * A class that represents an event in the game
 */
public class Event {
    private EventType eventType;
    private Player player;
    private Team team;
    private LocalDateTime eventTime;

    /**
     * event constructor
     */
    public Event(Team arg_team, EventType arg_event_type, Player arg_player){
        team=arg_team;
        eventType =arg_event_type;
        if (arg_team.getPlayer(arg_player.getUserName())!=null){
            player=arg_player;
        }
        else{
            player=null;
            //todo - change to ActionStatus, cant print from business layer
            System.out.println("The player is not a part of the team!");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        eventTime = LocalDateTime.now();
    }

    public ActionStatus editEvent(LocalDateTime endOfGameTime){
        //TODO - can we add pointer to the game in the event to check the time?
        ActionStatus ac = null;
        if(ChronoUnit.MINUTES.between(endOfGameTime,LocalDateTime.now())<=300){
            //TODO - add edit options

            ac = new ActionStatus(true,"the change was made");
        }
        else{
            ac = new ActionStatus(false,"you can not edit the event 5 hours from the end of the game");
        }
        return ac;
    }

    public String eventToString(){
        return eventType +" for player:"+player.getUserName()+" from team:"+ team.getName();
    }

}
