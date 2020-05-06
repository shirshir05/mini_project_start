package DB_Layer;

import BusinessService.Business_Layer.Game.League;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Coach;
import BusinessService.Business_Layer.UserManagement.Player;
import BusinessService.Business_Layer.UserManagement.TeamManager;
import BusinessService.Business_Layer.UserManagement.TeamOwner;
import BusinessService.Enum.ActionStatus;
import BusinessService.Service_Layer.DataManagement;
import DB_Layer.JDBC.sqlConnection;
import Presentation_Layer.StartSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class databaseController {

    sqlConnection sqlConn = new sqlConnection();

    public ActionStatus startDBConnection() {
        return sqlConn.connect();
    }

    public ActionStatus loadUsersInfo() {
        ActionStatus ac = null;
        try {
            ResultSet rs = sqlConn.find("Users", null);
            while (rs.next()){
                ac = StartSystem.LEc.Registration(rs.getString("userName"),rs.getString("userPassword"),rs.getString("userRole"),rs.getString("email"));
            }
        }
        catch (SQLException e){
            ac = new ActionStatus(false,"Sql SQLException");
        }
        return ac;
    }

    public ActionStatus loadTeamInfo() {
        ActionStatus ac = null;
        try {
            ResultSet rs = sqlConn.find("Team", null);
            while (rs.next()){
                String teamName = rs.getString("teamName");
                Team team = new Team(teamName,rs.getString("mainFiled"));
                DataManagement.addToListTeam(team);

                ResultSet rs2 = sqlConn.find("AssetsInTeam",new String[]{teamName});

                while(rs2.next()){
                    if(rs2.getString("assetRole").equals("TeamOwner")){
                        StartSystem.Tc.AddOrRemoveTeamOwner(teamName,rs2.getString("assetName"),1);
                    }else if(rs2.getString("assetRole").equals("Coach")){
                        StartSystem.Tc.AddOrRemoveCoach(teamName,rs2.getString("assetName"),1);
                    }else if(rs2.getString("assetRole").equals("TeamManager")){
                        StartSystem.Tc.AddOrRemoveTeamManager(teamName,rs2.getString("assetName"),1);
                    }else if(rs2.getString("assetRole").equals("Player")){
                        StartSystem.Tc.AddOrRemovePlayer(teamName,rs2.getString("assetName"),1);
                    }
                }
            }
            ac = new ActionStatus(true,"loaded successfully");
        }
        catch (SQLException e){
            ac = new ActionStatus(false,"Sql SQLException");
        }
        return ac;
    }


    public ActionStatus loadLeagueInfo() {
        ActionStatus ac = null;
        try {
            //load league objects
            ResultSet rs = sqlConn.find("League", null);
            while (rs.next()){
                String leagueName =rs.getString("leagueName");
                DataManagement.addToListLeague(new League(leagueName));

                //load season objects into this league
                ResultSet rs2 = sqlConn.find("Season",new String[]{leagueName});
                while(rs2.next()){
                    StartSystem.GSc.defineSeasonToLeague(leagueName,""+rs2.getString("seasonYear"),rs2.getInt("win"),rs2.getInt("lose"),rs2.getInt("equal"));
                }

                //load Referee objects into this league for each season
                ResultSet rs3 = sqlConn.find("RefereeInSeason",new String[]{leagueName});
                while(rs3.next()){
                    StartSystem.GSc.defineRefereeInLeague(leagueName,rs3.getString("refereeName"),""+rs3.getString("seasonYear"));
                }
            }
            ac = new ActionStatus(true,"loaded successfully");
        }
        catch (SQLException e){
            ac = new ActionStatus(false,"Sql SQLException");
        }
        return ac;
    }


     public ActionStatus loadGameInfo() {
         ActionStatus ac = null;
         try {
             //load league objects
             ResultSet rs = sqlConn.find("Game", null);
             while (rs.next()){
                 String leagueName =rs.getString("leagueName");
                 LocalDate ld = rs.getDate("gameDate").toLocalDate();
                 StartSystem.GSc.createGame(ld, rs.getString("field"), rs.getString("homeTeam"), rs.getString("guestTeam"),
                         rs.getString("headReferee"),  rs.getString("linesmanOneReferee"), rs.getString("linesmanTwoReferee"));

                 //todo- add game to season...
                 //add here
                
                 //load event objects into game
                 ResultSet rs2 = sqlConn.find("EventInGame",new String[]{""+rs.getInt("gameID")});
                 while(rs2.next()){
                     //todo- add events to game...
                 }
             }
             ac = new ActionStatus(true,"loaded successfully");
         }
         catch (SQLException e){
             ac = new ActionStatus(false,"Sql SQLException");
         }
         return ac;
    }

    /*
    public ActionStatus SaveUsersInfo() {
    }
    public ActionStatus SaveTeamInfo() {
    }
    public ActionStatus SaveGameInfo() {
    }
    public ActionStatus SaveLeagueInfo() {
    }
     */
}
