package DB_Layer;

import BusniesServic.Business_Layer.Game.League;
import BusniesServic.Business_Layer.Game.Season;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import BusniesServic.Service_Layer.LogAndExitController;
import Presentation_Layer.StartSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
                String[] splited = line.split(",");
                ac = login.Registration(splited[1], splited[2], splited[0], splited[3]);
                done = done && ac.isActionSuccessful();
                line = in.readLine();
            }
            ac = new ActionStatus(done,"User insertion status");
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
                team.EditTeamOwner((TeamOwner)DataManagement.containSubscription(splited[2]),1);
                team.EditTeamManager((TeamManager) DataManagement.containSubscription(splited[3]),1);
                team.AddOrRemoveCoach((Coach) DataManagement.containSubscription(splited[4]),1);
                String[] player = splited[5].split(";");
                for(String s : player){
                    team.addOrRemovePlayer((Player) DataManagement.containSubscription(s),1);
                }
                line = in.readLine();
            }
            ac = new ActionStatus(true,"User uploaded successfully");
        }catch (IOException e){
            ac = new ActionStatus(false, "could not open UserDB file");
        }
        return ac;
    }

    @Override
    public ActionStatus loadGameInfo() {
        return new ActionStatus(false,"function SaveUsersInfo not implemented");
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
                    if(!splited[5].equals("")) {
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
            ac = new ActionStatus(true,"league uploaded successfully");
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
