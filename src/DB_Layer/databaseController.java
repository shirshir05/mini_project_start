package DB_Layer;

import BusinessService.Business_Layer.Game.League;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.TeamManagement.TeamScore;
import BusinessService.Business_Layer.UserManagement.*;
import BusinessService.Enum.ActionStatus;
import BusinessService.Service_Layer.DataManagement;
import DB_Layer.JDBC.sqlConnection;
import Presentation_Layer.StartSystem;
import Presentation_Layer.Users_Menu.RefereeUserMenu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;

public class databaseController {

    sqlConnection sqlConn = new sqlConnection();

    public ActionStatus startDBConnection() {
        return sqlConn.connect();
    }

    //Get User by user name from DB
    public Subscription loadUserByName(String userID) {
        try {
            ResultSet rs = sqlConn.findByKey("Users", new String[]{userID});
            if (rs.next()){
                String thisUserName = rs.getString("userName");
                Subscription sub =  StartSystem.LEc.createUserByType(thisUserName,rs.getString("userPassword"),rs.getString("userRole"),rs.getString("email"));
                if(rs.getString("userRole").equals("UnifiedSubscription")){
                    ResultSet rs2 = sqlConn.findByKey("UsersData", new String[]{thisUserName});
                    UnifiedSubscription subUn = (UnifiedSubscription) sub;
                    while(rs2.next()) {
                        if (rs2.getString("dataType").equals("position")) {
                            subUn.setNewRole(new Player(thisUserName));
                            subUn.setPosition(rs2.getString("dataValue"));
                        }else if (rs2.getString("dataType").equals("qualification")) {
                            if(!subUn.isACoach()){
                                subUn.setNewRole(new Coach(thisUserName));
                            }
                            subUn.setQualification(rs2.getString("dataValue"));
                        }else if (rs2.getString("dataType").equals("roleInTeam")) {
                            if(!subUn.isACoach()){
                                subUn.setNewRole(new Coach(thisUserName));
                            }
                            subUn.setRoleInTeam(rs2.getString("dataValue"));
                        }else if (rs2.getString("dataType").equals("ownerAppointedByTeamOwner")) {
                            subUn.setNewRole(new TeamOwner());
                            subUn.teamOwner_setAppointedByTeamOwner(rs2.getString("dataValue"));
                        }else if (rs2.getString("dataType").equals("managerAppointedByTeamOwner")) {
                            subUn.setNewRole(new TeamOwner());
                            subUn.teamManager_setAppointedByTeamOwner(rs2.getString("dataValue"));
                        }
                    }
                }else if(rs.getString("userRole").equals("Referee")){
                    ResultSet rs2 = sqlConn.findByKey("UsersData", new String[]{thisUserName});
                    Referee subRef = (Referee) sub;
                    if(rs2.next()) {
                        if (rs2.getString("dataType").equals("qualification")) {
                            subRef.setQualification(rs2.getString("dataValue"));
                        }
                    }
                }
                return sub;
            }
        }
        catch (SQLException e){
        }
        return null;
    }

    //Get Users list by user role from DB (used for user without personal info)
    public HashSet<Subscription> loadUsersByRole(String role) {
        HashSet<Subscription> set = null;
        try {
            ResultSet rs = sqlConn.findByValue("Users","userRole",role);
            while (rs.next()){
                Subscription sub = StartSystem.LEc.createUserByType(rs.getString("userName"),rs.getString("userPassword"),rs.getString("userRole"),rs.getString("email"));
                set.add(sub);
            }
        }
        catch (SQLException e){
        }
        return set;
    }

    //Get team data by team name from DB
    public Team loadTeamInfo(String team_name) {
        try {
            ResultSet rs = sqlConn.findByKey("Team", new String[]{team_name});
            if (rs.next()){
                String teamName = rs.getString("teamName");
                Team team = new Team(teamName,rs.getString("mainFiled"));
                //  ?? DataManagement.addToListTeam(team)
                // TODO - ?? team budget ??
                team.changeStatus(rs.getInt("teamStatus"));
                TeamScore score = new TeamScore(teamName);
                score.setWins(rs.getInt("wins"));
                score.setDrawn(rs.getInt("drawns"));
                score.setLoses(rs.getInt("loses"));
                score.setPoints(rs.getInt("totalScore"));
                score.setNumberOfGames(rs.getInt("numOfGames"));
                score.setGoalsGet(rs.getInt("goalesGoten")); //todo- fix name in table
                score.setGoalsScores(rs.getInt("goalesScored")); //todo- fix name in table
                team.setTeamScore(score);
                //get all users connected to team
                ResultSet rs2 = sqlConn.findByKey("AssetsInTeam",new String[]{teamName});
                while(rs2.next()){
                    if(rs2.getString("assetRole").equals("TeamOwner")){
                        team.EditTeamOwner((UnifiedSubscription) loadUserByName(rs2.getString("assetName")),1);
                    }else if(rs2.getString("assetRole").equals("Coach")){
                        team.AddOrRemoveCoach((UnifiedSubscription)loadUserByName(rs2.getString("assetName")),1);
                    }else if(rs2.getString("assetRole").equals("TeamManager")){
                        team.EditTeamManager((UnifiedSubscription)loadUserByName(rs2.getString("assetName")),1);
                    }else if(rs2.getString("assetRole").equals("Player")){
                        team.addOrRemovePlayer((UnifiedSubscription)loadUserByName(rs2.getString("assetName")),1);
                    }else if(rs2.getString("assetRole").equals("Filed")){
                        team.setAsset(rs2.getString("assetName"));
                    }
                }
            }
        }
        catch (SQLException e){
        }
        return null;
    }

    //init all Leagues & Seasons data from DB
    public ActionStatus loadLeagueInfo() {
        ActionStatus ac = null;
        try {
            //load league objects
            ResultSet rs = sqlConn.findByKey("League", null);
            while (rs.next()){
                String leagueName =rs.getString("leagueName");
                DataManagement.addToListLeague(new League(leagueName));

                //load season objects into this league
                ResultSet rs2 = sqlConn.findByKey("Season",new String[]{leagueName});
                while(rs2.next()){
                    StartSystem.GSc.defineSeasonToLeague(leagueName,""+rs2.getString("seasonYear"),rs2.getInt("win"),rs2.getInt("lose"),rs2.getInt("equal"));
                }

                //load Referee objects into this league for each season
                ResultSet rs3 = sqlConn.findByKey("RefereeInSeason",new String[]{leagueName});
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

    //init all Games data from DB
     public ActionStatus loadGameInfo() {
         ActionStatus ac = null;
         try {
             //load league objects
             ResultSet rs = sqlConn.findByKey("Game", null);
             while (rs.next()){
                 String leagueName =rs.getString("leagueName");
                 LocalDate ld = rs.getDate("gameDate").toLocalDate();
                 //StartSystem.GSc.createGame(ld, rs.getString("field"), rs.getString("homeTeam"), rs.getString("guestTeam"),
                 //        rs.getString("headReferee"),  rs.getString("linesmanOneReferee"), rs.getString("linesmanTwoReferee"));

                 //todo- add game to season...
                 //add here

                 //load event objects into game
                 ResultSet rs2 = sqlConn.findByKey("EventInGame",new String[]{""+rs.getInt("gameID")});
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
