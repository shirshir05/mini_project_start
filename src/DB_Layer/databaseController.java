package DB_Layer;

import BusinessService.Business_Layer.Game.*;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.TeamManagement.TeamScore;
import BusinessService.Business_Layer.UserManagement.*;
import BusinessService.Enum.ActionStatus;
import BusinessService.Enum.EventType;
import BusinessService.Service_Layer.DataManagement;
import DB_Layer.JDBC.sqlConnection;
import Presentation_Layer.StartSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            if (rs.next()) {
                String thisUserName = rs.getString("userName");
                Subscription sub = StartSystem.LEc.createUserByType(thisUserName, rs.getString("userPassword"), rs.getString("userRole"), rs.getString("email"));
                if (rs.getString("userRole").equals("UnifiedSubscription")) {
                    ResultSet rs2 = sqlConn.findByKey("UsersData", new String[]{thisUserName});
                    UnifiedSubscription subUn = (UnifiedSubscription) sub;
                    while (rs2.next()) {
                        if (rs2.getString("dataType").equals("position")) {
                            subUn.setNewRole(new Player(thisUserName));
                            subUn.setPosition(rs2.getString("dataValue"));
                        } else if (rs2.getString("dataType").equals("qualification")) {
                            if (!subUn.isACoach()) {
                                subUn.setNewRole(new Coach(thisUserName));
                            }
                            subUn.setQualification(rs2.getString("dataValue"));
                        } else if (rs2.getString("dataType").equals("roleInTeam")) {
                            if (!subUn.isACoach()) {
                                subUn.setNewRole(new Coach(thisUserName));
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
                    if (rs2.next()) {
                        if (rs2.getString("dataType").equals("qualification")) {
                            subRef.setQualification(rs2.getString("dataValue"));
                        }
                    }
                }
                return sub;
            }
        } catch (SQLException e) {
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
                set.add(sub);
            }
        } catch (SQLException e) {
        }
        return set;
    }

    //Get team data by team name from DB
    public Team loadTeamInfo(String team_name) {
        try {
            ResultSet rs = sqlConn.findByKey("Team", new String[]{team_name});
            if (rs.next()) {
                String teamName = rs.getString("teamName");
                Team team = new Team(teamName, rs.getString("mainFiled"));
                //  ?? DataManagement.addToListTeam(team)
                // TODO - ?? team budget ??
                team.changeStatus(rs.getInt("teamStatus"));
                team.EditTeamOwner((UnifiedSubscription) loadUserByName(rs.getString("mainFiled")), 1);
                TeamScore score = new TeamScore(teamName);
                score.setWins(rs.getInt("wins"));
                score.setDrawn(rs.getInt("drawns"));
                score.setLoses(rs.getInt("loses"));
                score.setPoints(rs.getInt("totalScore"));
                score.setNumberOfGames(rs.getInt("numOfGames"));
                score.setGoalsGet(rs.getInt("goalesGoten")); //todo- fix name in table
                score.setGoalsScores(rs.getInt("goalsScored")); //todo- fix name in table
                team.setTeamScore(score);
                //get all users connected to team
                ResultSet rs2 = sqlConn.findByKey("AssetsInTeam", new String[]{teamName});
                while (rs2.next()) {
                    if (rs2.getString("assetRole").equals("TeamOwner")) {
                        team.EditTeamOwner((UnifiedSubscription) loadUserByName(rs2.getString("assetName")), 1);
                    } else if (rs2.getString("assetRole").equals("Coach")) {
                        team.AddOrRemoveCoach((UnifiedSubscription) loadUserByName(rs2.getString("assetName")), 1);
                    } else if (rs2.getString("assetRole").equals("TeamManager")) {
                        team.EditTeamManager((UnifiedSubscription) loadUserByName(rs2.getString("assetName")), 1);
                    } else if (rs2.getString("assetRole").equals("Player")) {
                        team.addOrRemovePlayer((UnifiedSubscription) loadUserByName(rs2.getString("assetName")), 1);
                    } else if (rs2.getString("assetRole").equals("Filed")) {
                        team.setAsset(rs2.getString("assetName"));
                    }
                }
                return team;
            }
        } catch (SQLException e) {
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
                DataManagement.addToListLeague(league); // ???

                //load season objects into this league
                ResultSet rs2 = sqlConn.findByKey("Season", new String[]{leagueName});
                while (rs2.next()) {
                    Season season = new Season(rs2.getString("seasonYear"));
                    league.addSeason(season);
                    PointsPolicy pointsPolicy = new PointsPolicy(rs2.getInt("win"), rs2.getInt("lose"), rs2.getInt("equal"));
                    ScoreTable scoreTable = new ScoreTable(pointsPolicy);
                    season.setScoreTable(scoreTable);
                }

                //load Referee objects into this league for each season

                ResultSet rs3 = sqlConn.findByKey("RefereeInSeason", new String[]{leagueName});
                while (rs3.next()) {
                    Season s = league.getSeason(rs3.getString("seasonYear"));
                    s.addReferee((Referee) loadUserByName(rs3.getString("refereeName")));
                }

            }
        } catch (SQLException e) {
        }
        return league;
    }

    /*
    pro
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Pair<Integer,Integer> score; // Integer[0] = host , Integer[1] = guest
    private HashSet<Event> eventList;
    private String league;
    private String season;
    */

    //init all Games data from DB
    public Game loadGameInfo(int game_id) {
        ActionStatus ac = null;
        try {
            //load league objects
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
            }
            ac = new ActionStatus(true, "loaded successfully");
        } catch (SQLException e) {
            ac = new ActionStatus(false, "Sql SQLException");
        }
        return null;
    }

    public int insert(String table, String[] values) {
        return sqlConn.insert(table, values);
    }

    public int update(String table, String[] key, String column, String value) {
        return sqlConn.update(table, key, column, value);
    }

    public int delete(String table, String[] key) {
        return sqlConn.delete(table, key);
    }

}

