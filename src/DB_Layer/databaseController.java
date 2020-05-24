package DB_Layer;

import Business_Layer.Business_Items.Game.*;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.TeamManagement.TeamScore;
import Business_Layer.Business_Items.Trace.CoachPersonalPage;
import Business_Layer.Business_Items.Trace.PlayerPersonalPage;
import Business_Layer.Business_Items.Trace.TeamPersonalPage;
import Business_Layer.Business_Items.UserManagement.*;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Enum.EventType;
import Business_Layer.Business_Control.DataManagement;
import DB_Layer.JDBC.sqlConnection;
import Service_Layer.StartSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

public class databaseController {

    public sqlConnection sqlConn = new sqlConnection();

    public ActionStatus startDBConnection() {
        return sqlConn.connect();
    }

    //Get User by user name from DB
    public Subscription loadUserByName(String userID) {
        try {
            ResultSet rs = sqlConn.findByKey("Users", new String[]{userID});
            if (rs.next()) {
                String thisUserName = rs.getString("userName");
                Subscription sub = StartSystem.LEc.createUserByType(thisUserName, rs.getString("userPassword"), rs.getString("userRole"), rs.getString("email"));
                sub.resetPass(rs.getString("userPassword"));
                if (rs.getString("userRole").equals("UnifiedSubscription")) {
                    ResultSet rs2 = sqlConn.findByKey("UsersData", new String[]{thisUserName});
                    UnifiedSubscription subUn = (UnifiedSubscription) sub;
                    while (rs2.next()) {
                        if (rs2.getString("dataType").equals("position")) {
                            subUn.setNewRole(new Player(thisUserName));
                            subUn.setPosition(rs2.getString("dataValue"));
                            subUn.setPlayerPersonalPage((PlayerPersonalPage)sqlConn.getBlob("Blobs",thisUserName+"PlayerPersonalPage"));
                        } else if (rs2.getString("dataType").equals("qualification")) {
                            if (!subUn.isACoach()) {
                                subUn.setNewRole(new Coach(thisUserName));
                                subUn.setCoachPersonalPage((CoachPersonalPage)sqlConn.getBlob("Blobs",thisUserName+"CoachPersonalPage"));
                            }
                            subUn.setQualification(rs2.getString("dataValue"));
                        } else if (rs2.getString("dataType").equals("roleInTeam")) {
                            if (!subUn.isACoach()) {
                                subUn.setNewRole(new Coach(thisUserName));
                                subUn.setCoachPersonalPage((CoachPersonalPage)sqlConn.getBlob("Blobs",thisUserName+"CoachPersonalPage"));
                            }
                            subUn.setRoleInTeam(rs2.getString("dataValue"));
                        } else if (rs2.getString("dataType").equals("ownerAppointedByTeamOwner")) {
                            subUn.setNewRole(new TeamOwner());
                            subUn.teamOwner_setAppointedByTeamOwner(rs2.getString("dataValue"));
                        } else if (rs2.getString("dataType").equals("managerAppointedByTeamOwner")) {
                            subUn.setNewRole(new TeamOwner());
                            subUn.teamManager_setAppointedByTeamOwner(rs2.getString("dataValue"));
                        }
                    }

                } else if (rs.getString("userRole").equals("Referee")) {
                    ResultSet rs2 = sqlConn.findByKey("UsersData", new String[]{thisUserName});
                    Referee subRef = (Referee) sub;
                    while (rs2.next()) {
                        if (rs2.getString("dataType").equals("qualification")) {
                            subRef.setQualification(rs2.getString("dataValue"));
                        }else if(rs2.getString("dataType").equals("refereeGames")){
                            subRef.setGamesList(rs2.getString("dataValue"));
                        }
                    }
                }
                //GET PERMISSIONS FROM BLOB
                sub.permissions = (Permissions)sqlConn.getBlob("Blobs",userID+"Permissions");
                sub.setAllAlerts((HashSet<String>) sqlConn.getBlob("Blobs",sub.getUserName()+"Alerts"));
                sub.setAllHistory((HashSet<String>) sqlConn.getBlob("Blobs",sub.getUserName()+"searchHistory"));
                return sub;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //Get Users list by user role from DB (used for user without personal info)
    public HashSet<Subscription> loadUsersByRole(String role) {
        HashSet<Subscription> set = new HashSet<Subscription>();
        try {
            ResultSet rs = sqlConn.findByValue("Users", "userRole", role);
            while (rs.next()) {
                Subscription sub = StartSystem.LEc.createUserByType(rs.getString("userName"), rs.getString("userPassword"), rs.getString("userRole"), rs.getString("email"));
                sub.resetPass(rs.getString("userPassword"));
                sub.permissions = (Permissions)sqlConn.getBlob("Blobs",rs.getString("userName")+"Permissions");
                sub.setAllAlerts((HashSet<String>) sqlConn.getBlob("Blobs",sub.getUserName()+"Alerts"));
                sub.setAllHistory((HashSet<String>) sqlConn.getBlob("Blobs",sub.getUserName()+"searchHistory"));
                set.add(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return set;
    }

    //Get team data by team name from DB
    public Team loadTeamInfo(String team_name) {
        Team team = (Team)sqlConn.getBlob("Team", team_name);

        if(team!=null) {
            //set team personal page
            Object ob = sqlConn.getBlob("Blobs", team.getName() + "TeamPage");
            team.setPersonalPage((TeamPersonalPage) ob);
            return team;
        }
        return null;
    }

    //init all Leagues & Seasons data from DB
    public League loadLeagueInfo(String league_name) {
        League league = null;
        try {
            //load league objects
            ResultSet rs = sqlConn.findByKey("League", new String[]{league_name});
            while (rs.next()) {
                String leagueName = rs.getString("leagueName");
                league = new League(leagueName);

                //load season objects into this league
                ResultSet rs2 = sqlConn.findByKey("Season", new String[]{leagueName});
                while (rs2.next()) {
                    Season season = (Season)sqlConn.getBlob("Blobs","Season"+league.getName()+rs2.getInt("seasonYear"));
                    league.addSeason(season);
                }

                //load Referee objects into this league for each season

                ResultSet rs3 = sqlConn.findByKey("RefereeInSeason", new String[]{leagueName});
                while (rs3.next()) {
                    Season s = league.getSeason(rs3.getString("seasonYear"));
                    s.addReferee((Referee) loadUserByName(rs3.getString("refereeName")));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return league;
    }


    //init all Games data from DB
    public Game loadGameInfo(int game_id) {
        try {
            //load game objects
            ResultSet rs = sqlConn.findByKey("Game", null);
            if (rs.next()) {
                String leagueName = rs.getString("leagueName");
                String Season = rs.getString("seasonYear");

                LocalDate ld = rs.getDate("gameDate").toLocalDate();
                LocalDateTime ldtStart = LocalDateTime.of(ld,rs.getTime("gameStartTime").toLocalTime());
                LocalDateTime ldtEnd = LocalDateTime.of(ld,rs.getTime("gameEndTime").toLocalTime());
                Team home = loadTeamInfo(rs.getString("homeTeam"));
                Team guest = loadTeamInfo(rs.getString("guestTeam"));

                Game game = new Game(rs.getString("filed"), ld,home,guest,rs.getInt("gameID"));
                game.setHeadReferee((Referee)loadUserByName(rs.getString("headReferee")));
                game.setLinesman1Referee((Referee)loadUserByName(rs.getString("linesmanOneReferee")));
                game.setLinesman2Referee((Referee)loadUserByName(rs.getString("linesmanTwoReferee")));
                game.setLeague(leagueName);
                game.setSeason(Season);
                game.setStartAndEndTime(ldtStart,ldtEnd);

                //load event objects into game
                ResultSet rs2 = sqlConn.findByKey("EventInGame", new String[]{"" + rs.getInt("gameID")});
                while (rs2.next()) {
                    LocalDateTime eventTime = LocalDateTime.of(ld,rs.getTime("eventTime").toLocalTime());
                    Event event = new Event(rs.getString("team"), EventType.valueOf(rs.getString("eventType")),rs.getString("playerName"),eventTime);
                    game.addEvent(event);
                }
                return game;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    //get Complaint data from DB
    public HashSet<Complaint> loadComplaintInfo(boolean onlyUnread) {
        HashSet<Complaint> list_Complaints= (HashSet<Complaint>)sqlConn.getBlob("Blobs","Complaint");
        if(list_Complaints!=null) {
            if (onlyUnread) {
                HashSet<Complaint> unanswered = new HashSet<>();
                for (Complaint c : list_Complaints) {
                    if (!c.isAnswered()) {
                        unanswered.add(c);
                    }
                }
                return unanswered;
            } else {
                return list_Complaints;
            }
        }
        return null;
    }

    public int insert(String table, Object[] values) {
        return sqlConn.insert(table, values);
    }

    public int insertBlob(String key,Object value) {
        if(value instanceof Team) {
            return sqlConn.insertBlob("Team", key, value);
        }
        else{
            return sqlConn.insertBlob("Blobs", key, value);
        }
    }

    public int updateBlob(String table, String key, Object value){
        return sqlConn.updateBlob(table, key, value);
    }

    public int update(String table, String[] key, String column, String value) {
        return sqlConn.update(table, key, column, value);
    }

    public int delete(String table, String[] key) {
        return sqlConn.delete(table, key);
    }


    public void resetDateBase(){
        sqlConn.resetDB();
    }

    public void startLastDateBase(){
        sqlConn.startLastDB();
    }

}

