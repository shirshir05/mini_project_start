package Service_Layer;

import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Control.LogAndExitController;
import Business_Layer.Business_Items.Game.League;
import Business_Layer.Business_Items.Game.Season;
import Business_Layer.Enum.ActionStatus;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;
public class JavaHTTPServerTest {
//
//    @Test
//    public void testLeagueConstructor() {
//
//        String name = "La Liga";
//        league = new League(name);
//        assertEquals(name, league.getName());
//    }

    /**
     * Test -
     */
    @Test
    public void testPostRegistration() {
        Server server = new Server();
        String[] strings = {"matanshu", "123456","Player","noy@gmail.com"};
        ActionStatus AC = new ActionStatus(true, "Subscription successfully added!");
        assertEquals(AC ,server.postMethod("registration",strings));
    }

//      case "registration":
//    as = st.getLEc().Registration
//            (jsonObject.getString("username"),
//                                    jsonObject.getString("password"),
//                                            jsonObject.getString("role"),
//                                            jsonObject.getString("email"));
//                    break;
//                case "login":
//                        DataManagement.setCurrent(null);
//    LogAndExitController lc = st.getLEc();
//    String a = jsonObject.getString("username");
//    String b = jsonObject.getString("password");
//    as = lc.Login(a, b);
//                    break;
//                case "logout":
//    as = st.getLEc().Exit(jsonObject.getString("username"));
//                    break;
//                case "answercomplaints":
//    as = st.getAc().answerCompliant(
//                            (Integer.parseInt(jsonObject.getString("id"))),
//            jsonObject.getString("answer"));
//                    break;
//                case "changestatusforteam":
//    as = st.getTc().ChangeStatusTeam
//            (jsonObject.getString("nameteam"),
//                                    Integer.parseInt(jsonObject.getString("status")));
//                    break;
//                case "onschedulingpolicy":
//    as = st.getGSc().schedulingGame
//            (jsonObject.getString("nameleague"),
//                                    jsonObject.getString("seasonname"),
//                                            Integer.parseInt(jsonObject.getString("policy")));
//                    break;
//                case "defineleague":
//    as = st.getGSc().defineLeague(jsonObject.getString("name"));
//                    break;
//                case "defineseason":
//    as = st.getGSc().defineSeasonToLeague
//            (jsonObject.getString("nameleague"),
//                                    jsonObject.getString("year"),
//                                            Integer.parseInt(jsonObject.getString("win")),
//                                            Integer.parseInt(jsonObject.getString("loss")),
//                                            Integer.parseInt(jsonObject.getString("equal")));
//                    break;
//                case "updatepointpolicy":
//    as = st.getGSc().updatePointsPolicy
//            (jsonObject.getString("nameleague"),
//                                    jsonObject.getString("year"),
//                                            Integer.parseInt(jsonObject.getString("win")),
//                                            Integer.parseInt(jsonObject.getString("loss")),
//                                            Integer.parseInt(jsonObject.getString("equal")));
//                    break;
//                case "addteamtoleague":
//    as = st.getGSc().addTeamToSeasonInLeague
//            (jsonObject.getString("nameteam"),
//                                    jsonObject.getString("nameleague"),
//                                            jsonObject.getString("year"));
//                    break;
//                case "addremovereferee":
//    as = st.getGSc().addOrDeleteRefereeToSystem
//            (jsonObject.getString("usernamereferee"),
//                                    jsonObject.getString("password"),
//                                            jsonObject.getString("email"),
//                                            Integer.parseInt(jsonObject.getString("addremove")));
//                    break;
//
//                case "setrefereeonleague":
//    as = st.getGSc().defineRefereeInLeague
//            (jsonObject.getString("nameleague"),
//                                    jsonObject.getString("usernamereferee"),
//                                            jsonObject.getString("year"));
//                    break;
//                case "addrole":
//    as = st.getLEc().addRoleToUser
//            (jsonObject.getString("role"), jsonObject.getString("password"));
//                    break;
//                case "addteam":
//    as = st.getTc().RequestCreateTeam
//            (jsonObject.getString("name"),
//                                    jsonObject.getString("field"));
//                    break;
//                case "addremoveplayer":
//    as = st.getTc().AddOrRemovePlayer
//            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
//                                    Integer.parseInt(jsonObject.getString("addremove")));
//                    break;
//                case "addremovecoach":
//    as = st.getTc().AddOrRemoveCoach
//            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
//                                    Integer.parseInt(jsonObject.getString("addremove")));
//                    break;
//                case "addremoveteamowner":
//    as = st.getTc().AddOrRemoveTeamOwner
//            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
//                                    Integer.parseInt(jsonObject.getString("addremove")));
//                    break;
//                case "addremoveteammanager":
//    as = st.getTc().AddOrRemoveTeamManager
//            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
//                                    Integer.parseInt(jsonObject.getString("addremove")));
//                    break;
//                case "addremoveteamfield":
//    as = st.getTc().AddOrRemoveTeamsAssets
//            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
//                                    Integer.parseInt(jsonObject.getString("addremove")));
//                    break;
//                case "addpermissiontoteammanger":
//    as = st.getESUDc().addPermissionToTeamManager
//            (jsonObject.getString("username"), jsonObject.getString("permissions"));
//                    break;
//                case "registertogamealert":
//    as = st.getAc().fanRegisterToGameAlerts
//            (Integer.parseInt(jsonObject.getString("gamenumber")));
//                    break;
//                case "registertopagealert":
//    as = st.getAc().fanRegisterToPage
//            (jsonObject.getString("usernametofollow"));
//                    break;
//                case "sendcomplaint":
//    as = st.getAc().addComplaint
//            (jsonObject.getString("complaintdescription"));
//
//                    break;
////            case "search":
////                //complete after pull from master
////                break;
//
//                case "savegame":
//    as = st.getGSc().endGame
//            (Integer.parseInt(jsonObject.getString("gameid")),
//            Integer.parseInt(jsonObject.getString("goalhost")),
//            Integer.parseInt(jsonObject.getString("goalguest")),
//            jsonObject.getString("year"),
//            jsonObject.getString("nameleague"));
//                    break;
//                case "createnewevent":
//    as = st.getGSc().refereeCreateNewEvent
//            (Integer.parseInt(jsonObject.getString("gameid")),
//            jsonObject.getString("nameteam"),
//            jsonObject.getString("usernameplayer"),
//            jsonObject.getString("eventtype"));
//                    break;
//                case "editgameevent":
//    as = st.getGSc().refereeEditGameEvent
//            (Integer.parseInt(jsonObject.getString("gameid")),
//            jsonObject.getString("nameteam"),
//            jsonObject.getString("eventtype"),
//            jsonObject.getString("nameuser"),
//            jsonObject.getString("datetime"));
//                    break;
}