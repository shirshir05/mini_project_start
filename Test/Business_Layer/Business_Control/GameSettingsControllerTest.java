package Business_Layer.Business_Control;

import Business_Layer.Business_Items.Game.Game;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.Player;
import Business_Layer.Business_Items.UserManagement.Referee;
import Business_Layer.Business_Items.UserManagement.UnifiedSubscription;
import Business_Layer.Enum.EventType;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class GameSettingsControllerTest {


       /**
     * Test - GSC1
     */
    @RunWith(Parameterized.class)
    public static class defineLeague{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public defineLeague() {
            //parameter
        }
        @Test
        public void defineLeagueTest() {

        }


    }//defineLeague


    /**
     * Test - GSC2
     */
    @RunWith(Parameterized.class)
    public static class defineSeasonToLeague{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public defineSeasonToLeague() {
            //parameter
        }
        @Test
        public void defineSeasonToLeagueTest() {

        }


    }//defineSeasonToLeague



    /**
     * Test - GSC3
     */
    @RunWith(Parameterized.class)
    public static class addOrDeleteRefereeToSystem{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public addOrDeleteRefereeToSystem() {
            //parameter
        }
        @Test
        public void addOrDeleteRefereeToSystemTest() {

        }


    }//addOrDeleteRefereeToSystem

    /**
     * Test - GSC4
     */
    @RunWith(Parameterized.class)
    public static class defineRefereeInLeague{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public defineRefereeInLeague() {
            //parameter
        }
        @Test
        public void defineRefereeInLeagueTest() {

        }


    }//defineRefereeInLeague


    /**
     * Test - GSC5
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
     * Test - GSC6
     */
    @RunWith(Parameterized.class)
    public static class refereeWatchGames{
        //parameter
        String name;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null}


            });
        }
        public refereeWatchGames(String name) {
            this.name=name;
            //parameter
        }
        @Test
        public void refereeWatchGamesTest() {
            DataManagement.cleanAllData();
            GameSettingsController gm = new GameSettingsController();
            LogAndExitController lg = new LogAndExitController();
            lg.Registration("s","12345", "Coach","shir0@post.bgu.ac.il");
            lg.Login("s","12345");
            assertEquals(gm.refereeWatchGames().getDescription(),"You are not a referee!");
            lg.Exit("s");
            lg.Registration("ss","12345", "Referee","shir0@post.bgu.ac.il");
            lg.Login("ss","12345");
            assertEquals(gm.refereeWatchGames().getDescription(),"You are participates in the next games");
        }
    }//refereeWatchGames


    /**
     * Test - GSC7
     */
    @RunWith(Parameterized.class)
    public static class refereeCreateNewEvent{

        int game_id;
        String team_name;
        String player_name;
        String event;
        String ans;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {1,"shir1","mor",EventType.red_ticket,"You may not take this action."},
                    {0,"0","0","red_ticket","The game id does not exist."},
                    {1,"0","0","red_ticket","The team id does not exist."},
                    {1,"shir1","0","red_ticket","The player does not exist in the team."},
                    {1,"shir1","shir","red_ticket","Event successfully updated."},
                    {1,"shir1","din","red_ticket","You may not take this action."},
                    {1,"shir1","dan","red_ticket","You are not a judge of the current game."}

            });
        }
        public refereeCreateNewEvent(int game_id, String team_name, String player_name,String event,String ans) {
            this.game_id = game_id;
            this.team_name = team_name;
            this.player_name = player_name;
            this.event = event;
            this.ans = ans;
        }
        @Test
        public void refereeCreateNewEventTest() {
            DataManagement.cleanAllData();
            GameSettingsController gm = new GameSettingsController();
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
            if(player_name.equals("din") ){
                lg.Registration("1","123456", "Coach","shir0@post.bgu.ac.il");
                lg.Login("1","123456");
            }
            if(player_name.equals("dan")){
                lg.Registration("dd","123456", "Referee","shir0@post.bgu.ac.il");
                lg.Login("dd","123456");

            }
            DataManagement.addToListTeam(t);
            DataManagement.addGame(g);
            DataManagement.findTeam("shir1").addOrRemovePlayer(createPlayer("mor","123456","shir0@post.bgu.ac.il"),1);
            DataManagement.findTeam("shir1").addOrRemovePlayer(createPlayer("din","123456","shir0@post.bgu.ac.il"),1);
            DataManagement.findTeam("shir1").addOrRemovePlayer(createPlayer("shir","123456","shir0@post.bgu.ac.il"),1);
            DataManagement.findTeam("shir1").addOrRemovePlayer(createPlayer("dan","123456","shir0@post.bgu.ac.il"),1);

            assertEquals(gm.refereeCreateNewEvent(game_id,team_name,player_name,event.toString()).getDescription(),ans);


        }


        private static UnifiedSubscription createPlayer(String name, String password, String email){
            UnifiedSubscription us = new UnifiedSubscription(name,password,email);
            us.setNewRole(new Player(us.getUserName()));
            return us;
        }


    }//refereeCreateNewEvent



}