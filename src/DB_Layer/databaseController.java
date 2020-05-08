package DB_Layer;

import BusinessService.Business_Layer.Game.League;
import BusinessService.Business_Layer.TeamManagement.Team;
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
                DataManagement.addToListTeam(team);
                ResultSet rs2 = sqlConn.findByKey("AssetsInTeam",new String[]{teamName});
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

    public static void main(String[] args){

        sqlConnection sql = new sqlConnection();
        sql.connect();
        databaseController data = new databaseController();
        Subscription s1 = data.loadUserByName("user1");
        Subscription s2 = data.loadUserByName("user3");
    }

    private static void deleteTestUsers() {
        sqlConnection sql = new sqlConnection();
        sql.connect();
        // Insert Users

        // Insert UsersData
        sql.delete("UsersData",new String[]{"user1","position"});
        sql.delete("UsersData",new String[]{"user2","position"});
        sql.delete("UsersData",new String[]{"user3","qualification"});
        sql.delete("UsersData",new String[]{"user4","qualification"});
        sql.delete("UsersData",new String[]{"user8","qualification"});
        sql.delete("UsersData",new String[]{"user9","qualification"});
        sql.delete("UsersData",new String[]{"user10","qualification"});
        sql.delete("UsersData",new String[]{"user14","managerAppointedByTeamOwner"});
        sql.delete("UsersData",new String[]{"user15","managerAppointedByTeamOwner"});
        sql.delete("UsersData",new String[]{"user16","ownerAppointedByTeamOwner"});
        sql.delete("UsersData",new String[]{"user17","ownerAppointedByTeamOwner"});

        sql.delete("Users",new String[]{"user1"});
        sql.delete("Users",new String[]{"user2"});
        sql.delete("Users",new String[]{"user3"});
        sql.delete("Users",new String[]{"user4"});
        sql.delete("Users",new String[]{"user6"});
        sql.delete("Users",new String[]{"user7"});
        sql.delete("Users",new String[]{"user8"});
        sql.delete("Users",new String[]{"user9"});
        sql.delete("Users",new String[]{"user10"});
        sql.delete("Users",new String[]{"user11"});
        sql.delete("Users",new String[]{"user12"});
        sql.delete("Users",new String[]{"user13"});
        sql.delete("Users",new String[]{"user14"});
        sql.delete("Users",new String[]{"user15"});
        sql.delete("Users",new String[]{"user16"});
        sql.delete("Users",new String[]{"user17"});

    }
    private static void testUsers() {
        sqlConnection sql = new sqlConnection();
        sql.connect();
        // Insert Users
        sql.insert("Users",new String[]{"user1","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user2","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user3","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user4","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user6","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user7","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user8","1","Referee","@"});
        sql.insert("Users",new String[]{"user9","1","Referee","@"});
        sql.insert("Users",new String[]{"user10","1","Referee","@"});
        sql.insert("Users",new String[]{"user11","1","SystemAdministrator","@"});
        sql.insert("Users",new String[]{"user12","1","SystemAdministrator","@"});
        sql.insert("Users",new String[]{"user13","1","UnionRepresentative","@"});
        sql.insert("Users",new String[]{"user14","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user15","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user16","1","UnifiedSubscription","@"});
        sql.insert("Users",new String[]{"user17","1","UnifiedSubscription","@"});

        // Insert UsersData
        sql.insert("UsersData",new String[]{"user1","position","p1"});
        sql.insert("UsersData",new String[]{"user2","position","p2"});
        sql.insert("UsersData",new String[]{"user3","qualification","q1"});
        sql.insert("UsersData",new String[]{"user4","qualification","q2"});
        sql.insert("UsersData",new String[]{"user8","qualification","q12"});
        sql.insert("UsersData",new String[]{"user9","qualification","q13"});
        sql.insert("UsersData",new String[]{"user10","qualification","q14"});
        sql.insert("UsersData",new String[]{"user14","managerAppointedByTeamOwner","user16"});
        sql.insert("UsersData",new String[]{"user15","managerAppointedByTeamOwner","user16"});
        sql.insert("UsersData",new String[]{"user16","ownerAppointedByTeamOwner","TeamOwner",""});
        sql.insert("UsersData",new String[]{"user17","ownerAppointedByTeamOwner","TeamOwner","user16"});
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
