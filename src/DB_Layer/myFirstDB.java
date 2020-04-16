package DB_Layer;

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
            SubscriptionFactory fuc = new SubscriptionFactory();
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
            }
            ac = new ActionStatus(true,"User uploaded successfully");
        }catch (IOException e){
            ac = new ActionStatus(false, "could not open UserDB file");
        }
        return ac;
    }

    @Override
    public ActionStatus loadGameInfo() {
        return null;
    }

    @Override
    public ActionStatus loadLeagueInfo() {
        return null;
    }


    //SAVE TO NEW DATA-BASE
    @Override
    public ActionStatus SaveUsersInfo() {
        return null;
    }

    @Override
    public ActionStatus SaveTeamInfo() {
        return null;
    }

    @Override
    public ActionStatus SaveGameInfo() {
        return null;
    }

    @Override
    public ActionStatus SaveLeagueInfo() {
        return null;
    }
}
