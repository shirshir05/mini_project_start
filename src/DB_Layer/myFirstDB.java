package DB_Layer;

import BusinessService.Business_Layer.Game.Game;
import BusinessService.Business_Layer.Game.League;
import BusinessService.Business_Layer.Game.Season;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.*;
import BusinessService.Enum.ActionStatus;
import BusinessService.Service_Layer.DataManagement;
import BusinessService.Service_Layer.LogAndExitController;
import Presentation_Layer.StartSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class myFirstDB implements InitFromDB,saveToDB {

    LogAndExitController login = StartSystem.getLEc();;

    @Override
    public ActionStatus startDBConnection() {
        return new ActionStatus(true,"good");
    }

    @Override
    public ActionStatus checkDBConnection() {
        return  new ActionStatus(true,"good");
    }

    @Override
    public ActionStatus loadUsersInfo() {
        ActionStatus ac = null;
        boolean done = true;
        try {

            BufferedReader in = new BufferedReader(new FileReader(new File("DataBase/usersDB.txt")));
            String line = in.readLine();
            while(line!=null){
                String[] split = line.split(",");
                ac = login.Registration(split[1], split[2], split[0], split[3]);
                done = done && ac.isActionSuccessful();
                line = in.readLine();
            }
            ac = new ActionStatus(done,"users file uploaded status");
        }catch (IOException e){
            ac = new ActionStatus(false, "could not open UserDB file");
        }
        return ac;
    }

    @Override
    public ActionStatus loadTeamInfo() {
        ActionStatus ac = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("DataBase/teamDB.txt")));
            String line = in.readLine();
            while(line!=null){
                String[] splited = line.split(",");
                Team team = new Team(splited[0],splited[1]);
                DataManagement.addToListTeam(team);
                ((UnifiedSubscription)DataManagement.containSubscription(splited[2])).teamOwner_setAppointedByTeamOwner(DataManagement.containSubscription(splited[2]));
                team.EditTeamOwner((UnifiedSubscription)DataManagement.containSubscription(splited[2]),1);
                team.EditTeamManager((UnifiedSubscription) DataManagement.containSubscription(splited[3]),1);
                team.AddOrRemoveCoach((UnifiedSubscription) DataManagement.containSubscription(splited[4]),1);
                String[] player = splited[5].split(";");
                for(String s : player){
                    team.addOrRemovePlayer((UnifiedSubscription) DataManagement.containSubscription(s),1);
                }
                line = in.readLine();
            }
            ac = new ActionStatus(true,"teams file uploaded successfully");
        }catch (IOException e){
            ac = new ActionStatus(false, "could not open UserDB file");
        }
        return ac;
    }

    @Override
    public ActionStatus loadGameInfo() {

        //LocalDate date = LocalDate.of(1992, 11, 14);
        ActionStatus ac = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("DataBase/gameDB.txt")));
            String line = in.readLine();
            while(line!=null){
                String[] splited = line.split(",");
                String[] date = splited[2].split(";");
                LocalDate d = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                Game game = new Game(splited[1],d,DataManagement.findTeam(splited[3]),DataManagement.findTeam(splited[4]));
                DataManagement.addGame(game);
                line = in.readLine();
            }
            ac = new ActionStatus(true,"games file uploaded successfully");
        }catch (IOException e){
            ac = new ActionStatus(false, "error in func loadGameInfo");
        }
        return ac;
    }

    @Override
    public ActionStatus loadLeagueInfo() {
        ActionStatus ac = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("DataBase/leagueDB.txt")));
            String line = in.readLine();
            League league = null;
            while(line!=null){
                String[] splited = line.split(",");
                if(splited[0].contains("League")) {
                    league = new League(splited[1]);
                    DataManagement.addToListLeague(league);
                }
                else if(splited[0].contains("Season")){
                    Season season = new Season(splited[1]);
                    season.addReferee((Referee)DataManagement.containSubscription(splited[2]));
                    season.addReferee((Referee)DataManagement.containSubscription(splited[3]));
                    season.addReferee((Referee)DataManagement.containSubscription(splited[4]));
                    if(splited.length==6) {
                        String[] games = splited[5].split(";");
                        for(String s:games) {
                            int i = Integer.parseInt(s);
                            season.addGame(DataManagement.getGame(i));
                        }
                    }
                    league.addSeason(season);
                }
                line = in.readLine();
            }
            ac = new ActionStatus(true,"leagues file uploaded successfully");
        }catch (IOException e){
            ac = new ActionStatus(false, "could not load league file");
        }
        return ac;
    }


    //SAVE TO NEW DATA-BASE
    @Override
    public ActionStatus SaveUsersInfo() {
        return new ActionStatus(false,"function SaveUsersInfo not implemented");

    }

    @Override
    public ActionStatus SaveTeamInfo() {
        return new ActionStatus(false,"function SaveTeamInfo not implemented");

    }

    @Override
    public ActionStatus SaveGameInfo() {
        return new ActionStatus(false,"function SaveGameInfo not implemented");

    }

    @Override
    public ActionStatus SaveLeagueInfo() {
        return new ActionStatus(false,"function SaveLeagueInfo not implemented");
    }
}
