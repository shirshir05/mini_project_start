package Busnies_Servic.Service_Layer;

import Busnies_Servic.Business_Layer.Game.Game;
import Busnies_Servic.Business_Layer.TeamManagement.Team;
import Busnies_Servic.Business_Layer.UserManagement.Player;
import Busnies_Servic.Business_Layer.UserManagement.Referee;
import Busnies_Servic.EventType;
import Busnies_Servic.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class GameControllerTest {



    /**
     * Test - GC xxxxx
     */
    @RunWith(Parameterized.class)
    public static class createGame{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public createGame() {
            //parameter
        }
        @Test
        public void createGameTest() {

        }


    }//createGame


    /**
     * Test - GC1
     */
    @RunWith(Parameterized.class)
    public static class refereeCreateNewEvent{

        int game_id;
        String team_name;
        String player_name;
        EventType event;
        String ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {1,"shir1","mor",EventType.red_ticket,"You may not take this action."},
                    {0,"0","0",EventType.red_ticket,"The game id does not exist."},
                    {1,"0","0",EventType.red_ticket,"The team id does not exist."},
                    {1,"shir1","0",EventType.red_ticket,"The player does not exist in the team."},
                    {5,"shir1","shir",EventType.red_ticket,"Event successfully updated."},

            });
        }
        public refereeCreateNewEvent(int game_id, String team_name, String player_name,EventType event,String ans) {
            this.game_id = game_id;
            this.team_name = team_name;
            this.player_name = player_name;
            this.event = event;
            this.ans = ans;
        }
        @Test
        public void refereeCreateNewEventTest() {
            GameController gm = new GameController();
            LogAndExitController lg = new LogAndExitController();
            Team t = new Team("shir1","s");
            Game g = new Game("f", LocalDate.of(1995,8,18),t,new Team("shir2","f"));
            if(!player_name.equals("mor")){
                lg.Registration("REF","123456", "Referee","shir0@post.bgu.ac.il");
                lg.Login("REF","123456");
                g.setHeadReferee(new Referee("REF","123456","shir0@post.bgu.ac.il"));
                g.setLinesman1Referee(new Referee("REF","123456","shir0@post.bgu.ac.il"));
                g.setLinesman2Referee(new Referee("REF","123456","shir0@post.bgu.ac.il"));
            }
            DataManagement.addToListTeam(t);
            DataManagement.addGame(g);
            DataManagement.findTeam("shir1").addOrRemovePlayer(new Player("mor","123456","shir0@post.bgu.ac.il"),1);
            DataManagement.findTeam("shir1").addOrRemovePlayer(new Player("shir","123456","shir0@post.bgu.ac.il"),1);
            assertEquals(gm.refereeCreateNewEvent(game_id,team_name,player_name,event),ans);


        }


    }//refereeCreateNewEvent

    /**
     * Test - GC2
     */
    @RunWith(Parameterized.class)
    public static class refereeWatchGames{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public refereeWatchGames() {
            //parameter
        }
        @Test
        public void refereeWatchGamesTest() {

        }


    }//refereeWatchGames


}