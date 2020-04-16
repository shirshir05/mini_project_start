package BusniesServic.Business_Layer.Game;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Player;
import BusniesServic.Enum.EventType;
import org.junit.Test;
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
        event = new Event(team1, eventType, player);

        assertEquals(team1, event.getTeam());
        assertEquals(eventType, event.getEventType());
        assertEquals(player, event.getPlayer());
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
        event = new Event(team1, eventType, player);

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
        event = new Event(team1, eventType, player);

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
        event = new Event(team1, eventType, player);

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
        event = new Event(team1, eventType, player);

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
        event = new Event(team1, eventType, player);

        assertEquals(team1, event.getTeam());

        Team team2 = new Team("Real Madrid", "fdsfsdfu");
        team2.addOrRemovePlayer(player, 1);
        event.setTeam(team2);

        assertEquals(team2, event.getTeam());

        event.setTeam(null);

        assertEquals(team2, event.getTeam());
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
        event = new Event(team1, eventType, player);

        assertEquals(player, event.getPlayer());

        Player player1 = new Player("Ronaldo", "65464", "31212fsf@gmail.com");
        team1.addOrRemovePlayer(player, 1);

        event.setPlayer(player1);

        assertEquals(player1, event.getPlayer());

        event.setPlayer(null);

        assertEquals(player1, event.getPlayer());
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
        event = new Event(team1, eventType, player);

        assertEquals(eventType +" for player:"+player.getUserName()+" from team:"+ team1.getName(), event.eventToString());
    }

//    /**
//     * Test - E9
//     */
//    @Test
//    public void testEditEvent() {
//
//        Player player = new Player("messi", "123456", "31212fsf@gmail.com");
//        EventType eventType = EventType.goal;
//        Team team1 = new Team("Barcelona", "Camp Nou");
//        team1.addOrRemovePlayer(player, 1);
//        event = new Event(team1, eventType, player);
//
//        assertEquals(eventType +" for player:"+player.getUserName()+" from team:"+ team1.getName(), event.eventToString());
//    }

//    public ActionStatus editEvent(LocalDateTime endOfGameTime){
//        //TODO - can we add pointer to the game in the event to check the time?
//        ActionStatus ac = null;
//        if(ChronoUnit.MINUTES.between(endOfGameTime,LocalDateTime.now())<=300){
//            //TODO - add edit options
//
//            ac = new ActionStatus(true,"the change was made");
//        }
//        else{
//            ac = new ActionStatus(false,"you can not edit the event 5 hours from the end of the game");
//        }
//        return ac;
//    }

}





