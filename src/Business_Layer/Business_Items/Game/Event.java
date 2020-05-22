package Business_Layer.Business_Items.Game;

import Business_Layer.Business_Items.UserManagement.UnifiedSubscription;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Enum.EventType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * A class that represents an event in the game
 */
public class Event {
    private EventType eventType;
    private String player;
    private String team;
    private LocalDateTime eventTime;

    /**
     * event constructor
     * time==null => set the current time
     */
    public Event(Team arg_team, EventType arg_event_type, UnifiedSubscription arg_player, LocalDateTime time){
        team=arg_team.getName();
        eventType =arg_event_type;
        if (arg_team.getPlayer(arg_player.getUserName())!=null){
            player = arg_player.getName();
        }
        else{
            player=null;
            //todo - change to ActionStatus, cant print from business layer
            //System.out.println("The player is not a part of the team!");
        }
        if (time==null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            eventTime = LocalDateTime.now();
        }
        else{
            eventTime=time;
        }
    }

    public Event(String arg_team, EventType arg_event_type, String arg_player, LocalDateTime time) {
        team = arg_team;
        eventType = arg_event_type;
        player = arg_player;
        eventTime = time;
    }

    public ActionStatus editEvent(LocalDateTime endOfGameTime, Team arg_team, EventType arg_event_type, UnifiedSubscription arg_player, LocalDateTime time){
        ActionStatus ac = null;
        if(ChronoUnit.MINUTES.between(endOfGameTime,LocalDateTime.now())<=300){
            this.team = arg_team.getName();
            this.eventType=arg_event_type;
            this.player = arg_player.getName();
            if (time==null){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                eventTime = LocalDateTime.now();
            }
            else{
                eventTime=time;
            }
            ac = new ActionStatus(true,"the change was made");
        }
        else{
            ac = new ActionStatus(false,"you can not edit the event 5 hours from the end of the game");
        }
        return ac;
    }

    public String eventToString(){
        return eventType +" for player:"+player+" from team:"+ team+ " time:"+ eventTime.toString();
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getPlayer() {
        return player;
    }

    public String getTeam() {
        return team;
    }

    public void setEventType(EventType eventType) {
        if(eventType != null)
            this.eventType = eventType;
    }

    public void setPlayer(UnifiedSubscription player) {
        this.player = player.getName();
    }

    public void setTeam(Team team) {
        this.team = team.getName();
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }
}
