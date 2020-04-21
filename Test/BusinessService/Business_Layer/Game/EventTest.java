package BusinessService.Business_Layer.Game;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Player;
import BusinessService.Enum.EventType;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class EventTest {

    private Event event;

    /**
     * Test - E1
     */
    @Test
    public void testEventConstructor() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(team1, event.getTeam());
        assertEquals(eventType, event.getEventType());
        assertEquals(player, event.getPlayer());

        team1.addOrRemovePlayer(player, 0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime eventTime = LocalDateTime.now();
        event = new Event(team1, eventType, player,eventTime);

        assertEquals(team1, event.getTeam());
        assertEquals(eventType, event.getEventType());

    }

    /**
     * Test - E2
     */
    @Test
    public void testGetPlayer() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(player, event.getPlayer());
    }

    /**
     * Test - E3
     */
    @Test
    public void testGetTeam() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(team1, event.getTeam());
    }

    /**
     * Test - E4
     */
    @Test
    public void testGetEventType() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(eventType, event.getEventType());
    }

    /**
     * Test - E5
     */
    @Test
    public void testSetEventType() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(eventType, event.getEventType());

        EventType eventType1 = EventType.red_ticket;
        event.setEventType(eventType1);

        assertEquals(eventType1, event.getEventType());

        event.setEventType(null);

        assertEquals(eventType1, event.getEventType());
    }

    /**
     * Test - E6
     */
    @Test
    public void testSetTeam() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(team1, event.getTeam());

        Team team2 = new Team("Real Madrid", "fdsfsdfu");
        team2.addOrRemovePlayer(player, 1);
        event.setTeam(team2);

        assertEquals(team2, event.getTeam());

        event.setTeam(null);

        assertNotEquals(team2, event.getTeam());
    }

    /**
     * Test - E7
     */
    @Test
    public void testSetPlayer() {
        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(player, event.getPlayer());

        Player player1 = new Player("Ronaldo", "65464", "31212fsf@gmail.com");
        team1.addOrRemovePlayer(player, 1);

        event.setPlayer(player1);

        assertEquals(player1, event.getPlayer());

        event.setPlayer(null);

        assertNotEquals(player1, event.getPlayer());
    }

    /**
     * Test - E8
     */
    @Test
    public void testEventToString() {

        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
        EventType eventType = EventType.goal;
        Team team1 = new Team("Barcelona", "Camp Nou");
        team1.addOrRemovePlayer(player, 1);
        event = new Event(team1, eventType, player,null);

        assertEquals(eventType +" for player:"+player.getUserName()+" from team:"+ team1.getName() + " time:"+ event.getEventTime().toString(), event.eventToString());
        //eventType +" for player:"+player.getUserName()+" from team:"+ team.getName()+ " time:"+ eventTime.toString()

    }

//    /**
//     * Test - E9
//     */
//    @Test
//    public void testEditEvent() {
//
//        LocalDateTime endOfGameTime = LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30);
//
//        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
//        EventType eventType = EventType.goal;
//        Team team1 = new Team("Barcelona", "Camp Nou");
//        team1.addOrRemovePlayer(player, 1);
//        event = new Event(team1, eventType, player);
//
//        assertEquals(eventType +" for player:"+player.getUserName()+" from team:"+ team1.getName(), event.eventToString());
//    }
//
//    public ActionStatus editEvent(LocalDateTime endOfGameTime, Team arg_team, EventType arg_event_type, Player arg_player, LocalDateTime time){
//        ActionStatus ac = null;
//        if(ChronoUnit.MINUTES.between(endOfGameTime,LocalDateTime.now())<=300){
//            this.team=arg_team;
//            this.eventType=arg_event_type;
//            this.player=player;
//            if (time==null){
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//                eventTime = LocalDateTime.now();
//            }
//            else{
//                eventTime=time;
//            }
//            ac = new ActionStatus(true,"the change was made");
//        }
//        else{
//            ac = new ActionStatus(false,"you can not edit the event 5 hours from the end of the game");
//        }
//        return ac;
//    }

}





