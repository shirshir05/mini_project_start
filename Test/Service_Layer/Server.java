package Service_Layer;
import Business_Layer.Enum.ActionStatus;
import org.json.JSONObject;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Server {
    private StartSystem st;

    public Server(){
        st = new StartSystem();
    }

    @Test
    public void main(){
        handlePostMethod("createteam","{name: team1, field: fi}");
        handlePostMethod("addremoveplayer", "{ \"teamname\":\"Tel Aviv\"," +
                "\"objectname\":\"Shalom\"," +
                "\"addremove\":\"1\"}");
        handlePostMethod("addremovecoach", "{ \"teamname\":\"Tel Aviv\"," +
                "\"objectname\":\"Shalom\"," +
                "\"addremove\":\"1\"}");
        handlePostMethod("addremoveteamowner", "{ \"teamname\":\"Tel Aviv\"," +
                "\"objectname\":\"Shalom\"," +
                "\"addremove\":\"1\"}");
        handlePostMethod("addremoveteammanager", "{ \"teamname\":\"Tel Aviv\"," +
                "\"objectname\":\"Shalom\"," +
                "\"addremove\":\"1\"}");
        handlePostMethod("changestatusforteam", "{ \"nameteam\":\"Tel Aviv\"," +
                "\"status\":\"1\"}");
        handlePostMethod("addremoveteamfield", "{ \"teamname\":\"Tel Aviv\"," +
                "\"objectname\":\"Barnebeo\"," +
                "\"addremove\":\"1\"}");
        handlePostMethod("registration", "{ \"username\":\"matanshu\"," +
                "\"password\":\"123456\"," +
                "\"role\":\"Coach\"," +
                "\"email\":\"matanshu@gmail.com\"}");
        handlePostMethod("login", "{ \"username\":\"matanshu\"," +
                "\"password\":\"123456\"}");
        handlePostMethod("logout", "{ \"username\":\"matanshu\"}");
        handlePostMethod("addrole", "{ \"role\":\"Referee\"," +
                "\"password\":\"123456\"}");
        handlePostMethod("onschedulingpolicy", "{ \"nameleague\":\"La Liga\"," +
                "\"seasonname\":\"2020\"," +
                "\"policy\":\"1\"}");
        handlePostMethod("defineleague", "{ \"nameleague\":\"Tel Aviv\"}");
        handlePostMethod("defineseason", "{ \"nameleague\":\"La Liga\"," +
                "\"year\":\"2020\"," +
                "\"win\":\"3\"," +
                "\"loss\":\"0\","+
                "\"equal\":\"1\"}");
        handlePostMethod("updatepointpolicy", "{ \"nameleague\":\"La Liga\"," +
                "\"year\":\"2020\"," +
                "\"win\":\"3\"," +
                "\"loss\":\"0\","+
                "\"equal\":\"1\"}");
        handlePostMethod("addteamtoleague", "{ \"nameteam\":\"Real Madrid\"," +
                "\"nameleague\":\"La Liga\"," +
                "\"year\":\"2020\"}");
        handlePostMethod("savegame", "{ \"gameid\":\"2\"," +
                "\"goalhost\":\"3\"," +
                "\"goalguest\":\"2\"," +
                "\"year\":\"2020\","+
                "\"nameleague\":\"La Liga\"}");
        handlePostMethod("addremovereferee", "{ \"usernamereferee\":\"shalom\"," +
                "\"password\":\"123456\"," +
                "\"email\":\"123456\"," +
                "\"addremove\":\"1\"}");
        handlePostMethod("setrefereeonleague", "{ \"nameleague\":\"La Liga\"," +
                "\"usernamereferee\":\"Shalom\"," +
                "\"year\":\"2020\"}");
        handlePostMethod("editgameevent", "{ \"gameid\":\"2\"," +
                "\"nameteam\":\"Barcelona\"," +
                "\"eventtype\":\"goal\"," +
                "\"nameuser\":\"matan\","+
                "\"datetime\":\"14:30:00\"}");
        handlePostMethod("createnewevent", "{ \"gameid\":\"2\"," +
                "\"nameteam\":\"Barcelona\"," +
                "\"usernameplayer\":\"shalom\"," +
                "\"eventtype\":\"goal\"}");
        handlePostMethod("registertogamealert", "{ \"gamenumber\":\"5\"}");
        handlePostMethod("registertopagealert", "{ \"usernametofollow\":\"bar\"}");
        handlePostMethod("sendcomplaint", "{ \"complaintdescription\":\"wins alerts don't work\"}");
        handlePostMethod("answercomplaints", "{ \"answer\":\"bla bla bla\"," +
                "\"id\":\"10\"}");
        handlePostMethod("addpermissiontoteammanger", "{ \"username\":\"bar\"," +
                "\"permissions\":\"Edit_team\"}");
        handleGetMethod("watchcomplaints");
        handleGetMethod("watchlogger");
        handleGetMethod("teamsforapproval");
        handleGetMethod("approveteam");
        handleGetMethod("search");
        handleGetMethod("isa");
        handleGetMethod("watchgameevent");
        handleGetMethod("watchscoretable");
        handleGetMethod("watchgame");
        handleGetMethod("readallalrets");
        handleGetMethod("alertnew");
    }

    public Object[] RequestCreateTeam(String arg_name, String arg_field) {
        return new Object[]{arg_name,arg_field};
    }

    public Object[] AddOrRemovePlayer(String addremoveplayer_pram1
            ,String addremoveplayer_pram2, String addremove) {
        return new Object[]{addremoveplayer_pram1, addremoveplayer_pram2, addremove};
    }

    public Object[] Registration(String arg_user_name, String arg_password, String arg_role, String email){
        return new Object[]{arg_user_name,arg_password,arg_role,email};
    }

    private void handleGetMethod(String controllerMethod) {
        ActionStatus actionStatus;
        String param1;
        String ans;
        try {
            switch (controllerMethod) {
                case "watchcomplaints":
                    param1 = "getAllComplaints";
                    ans = watchcomplaints(param1);
                    assertEquals(ans, param1);
                    break;
                case "watchlogger":
                    param1 = "watchLogger";
                    ans = watchlogger(param1);
                    assertEquals(ans, param1);
                    break;
                case "teamsforapproval":
                    param1 = "AllTeamApprove";
                    ans = teamsforapproval(param1);
                    assertEquals(ans, param1);
                    break;
                case "approveteam":
                    param1 = "ApproveCreateTeamAlert";
                    ans = approveteam(param1);
                    assertEquals(ans, param1);
                    break;
                case "search":
                    param1 = "findData";
                    ans = search(param1);
                    assertEquals(ans, param1);
                    break;
                case "watchsearchhistory":
                    param1 = "showSearchHistory";
                    ans = watchsearchhistory(param1);
                    assertEquals(ans, param1);
                    break;
                case "isa":
                    param1 = "hasRole";
                    ans = isa(param1);
                    assertEquals(ans, param1);
                    break;
                case "watchgameevent":
                    param1 = "printGameEvents";
                    ans = watchgameevent(param1);
                    assertEquals(ans, param1);
                    break;
                case "watchscoretable":
                    param1 = "displayScoreTable";
                    ans = watchscoretable(param1);
                    assertEquals(ans, param1);
                    break;
                case "watchgame":
                    param1 = "refereeWatchGames";
                    ans = watchgame(param1);
                    assertEquals(ans, param1);
                    break;

                case "readallalrets":
                    param1 = "readAllAlerts";
                    ans = readallalrets(param1);
                    assertEquals(ans, param1);
                    break;
                case "alertnew":
                    param1 = "hasAlertNew";
                    ans = alertnew(param1);
                    assertEquals(ans, param1);
                    break;
                default:
                    assertEquals(controllerMethod, false);

            }
        } catch (Exception e) {
            assertEquals(controllerMethod, false);
        }
    }

    private String alertnew(String param1) {
        return "hasAlertNew";
    }

    private String readallalrets(String param1) {
        return "readAllAlerts";
    }

    private String watchgame(String param1) {
        return "refereeWatchGames";
    }

    private String watchscoretable(String param1) {
        return "displayScoreTable";
    }

    private String watchgameevent(String param1) {
        return "printGameEvents";
    }

    private String isa(String param1) {
        return "hasRole";
    }

    private String watchsearchhistory(String param1) {
        return "showSearchHistory";
    }

    private String search(String param1) {
        return "findData";
    }

    private String approveteam(String param1) {
        return "ApproveCreateTeamAlert";
    }

    private String teamsforapproval(String param1) {
        return "AllTeamApprove";
    }

    private String watchlogger(String param1) {
        return "watchLogger";
    }

    private String watchcomplaints(String param1) {
        return "getAllComplaints";
    }


    public void handlePostMethod(String controllerMethod, String json) {
        JSONObject jsonObject;
        String param1;
        String param2;
        String param3;
        String param4;
        String param5;
        ActionStatus as;
        jsonObject = new JSONObject(json);
        Object[] ans;
        try {
            switch (controllerMethod) {
                case "createteam":
                    param1 = jsonObject.getString("name").replaceAll("%20"," ");
                    param2 = jsonObject.getString("field").replaceAll("%20"," ");
                    ans = RequestCreateTeam(param1, param2);
                    assertEquals(ans[0],param1);
                    assertEquals(ans[1],param2);
                    break;
                case "addremoveplayer":
                    param1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    param2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    param3 = jsonObject.getString("addremove");
                    ans = AddOrRemovePlayer(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "addremovecoach":
                    param1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    param2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    param3 = jsonObject.getString("addremove");
                    ans = AddOrRemoveCoach(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "addremoveteamowner":
                    param1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    param2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    param3 = jsonObject.getString("addremove");
                    ans = AddOrRemoveTeamOwner(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "addremoveteammanager":
                    param1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    param2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    param3 = jsonObject.getString("addremove");
                    ans = AddOrRemoveTeamManager(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "changestatusforteam":
                    param1 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    param2 = jsonObject.getString("status").replaceAll("%20"," ");
                    ans = ChangeStatusTeam(param1, param2);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    break;
                case "addremoveteamfield":
                    param1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    param2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    param3 = jsonObject.getString("addremove");
                    ans = AddOrRemoveTeamsAssets(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "registration":
                    param1 = jsonObject.getString("username").replaceAll("%20"," ");
                    param2 =  jsonObject.getString("password").replaceAll("%20"," ");
                    param3 =  jsonObject.getString("role").replaceAll("%20"," ");
                    param4 =  jsonObject.getString("email").replaceAll("%20"," ");
                    ans = Registration(param1, param2, param3, param4);
                    assertEquals(ans[0],param1);
                    assertEquals(ans[1],param2);
                    assertEquals(ans[2],param3);
                    assertEquals(ans[3],param4);
                    break;
                case "login":
                    param1 = jsonObject.getString("username").replaceAll("%20"," ");
                    param2 = jsonObject.getString("password").replaceAll("%20"," ");
                    ans = Login(param1, param2);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    break;
                case "logout":
                    param1 = jsonObject.getString("username").replaceAll("%20"," ");
                    ans = Exit(param1);
                    assertEquals(ans[0], param1);
                    break;
                case "addrole":
                    param1 = jsonObject.getString("role").replaceAll("%20"," ");
                    param2 = jsonObject.getString("password").replaceAll("%20"," ");
                    ans = addRoleToUser(param1, param2);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    break;
                case "onschedulingpolicy":
                    param1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    param2 = jsonObject.getString("seasonname").replaceAll("%20"," ");
                    ans = schedulingGame(param1, param2);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    break;
                case "defineleague":
                    param1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    ans = defineLeague(param1);
                    assertEquals(ans[0], param1);
                    break;
                case "defineseason":
                    param1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    param2 =  jsonObject.getString("year").replaceAll("%20"," ");
                    param3 =  jsonObject.getString("win").replaceAll("%20"," ");
                    param4 =  jsonObject.getString("loss").replaceAll("%20"," ");
                    param5 =  jsonObject.getString("equal").replaceAll("%20"," ");
                    ans = defineSeasonToLeague(param1, param2, param3, param4, param5);
                    assertEquals(ans[0],param1);
                    assertEquals(ans[1],param2);
                    assertEquals(ans[2],param3);
                    assertEquals(ans[3],param4);
                    assertEquals(ans[4],param5);
                    break;
                case "updatepointpolicy":
                    param1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    param2 =  jsonObject.getString("year").replaceAll("%20"," ");
                    param3 =  jsonObject.getString("win").replaceAll("%20"," ");
                    param4 =  jsonObject.getString("loss").replaceAll("%20"," ");
                    param5 =  jsonObject.getString("equal").replaceAll("%20"," ");
                    ans = updatePointsPolicy(param1, param2, param3, param4, param5);
                    assertEquals(ans[0],param1);
                    assertEquals(ans[1],param2);
                    assertEquals(ans[2],param3);
                    assertEquals(ans[3],param4);
                    assertEquals(ans[4],param5);
                    break;
                case "addteamtoleague":
                    param1 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    param2 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    param3 = jsonObject.getString("year");
                    ans = addTeamToSeasonInLeague(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "savegame":
                    param1 = jsonObject.getString("gameid").replaceAll("%20"," ");
                    param2 =  jsonObject.getString("goalhost").replaceAll("%20"," ");
                    param3 =  jsonObject.getString("goalguest").replaceAll("%20"," ");
                    param4 =  jsonObject.getString("year").replaceAll("%20"," ");
                    param5 =  jsonObject.getString("nameleague").replaceAll("%20"," ");
                    ans = endGame(param1, param2, param3, param4, param5);
                    assertEquals(ans[0],param1);
                    assertEquals(ans[1],param2);
                    assertEquals(ans[2],param3);
                    assertEquals(ans[3],param4);
                    assertEquals(ans[4],param5);
                    break;
                case "addremovereferee":
                    param1 = jsonObject.getString("usernamereferee").replaceAll("%20"," ");
                    param2 = jsonObject.getString("password").replaceAll("%20"," ");
                    param3 = jsonObject.getString("email");
                    param4 = jsonObject.getString("addremove");
                    ans = addOrDeleteRefereeToSystem(param1, param2, param3, param4);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    assertEquals(ans[3], param4);
                    break;
                case "setrefereeonleague":
                    param1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    param2 = jsonObject.getString("usernamereferee").replaceAll("%20"," ");
                    param3 = jsonObject.getString("year");
                    ans = defineRefereeInLeague(param1, param2, param3);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    break;
                case "editgameevent":
                    param1 = jsonObject.getString("gameid").replaceAll("%20"," ");
                    param2 =  jsonObject.getString("nameteam").replaceAll("%20"," ");
                    param3 =  jsonObject.getString("eventtype").replaceAll("%20"," ");
                    param4 =  jsonObject.getString("nameuser").replaceAll("%20"," ");
                    param5 =  jsonObject.getString("datetime").replaceAll("%20"," ");
                    ans = refereeEditGameEvent(param1, param2, param3, param4, param5);
                    assertEquals(ans[0],param1);
                    assertEquals(ans[1],param2);
                    assertEquals(ans[2],param3);
                    assertEquals(ans[3],param4);
                    assertEquals(ans[4],param5);
                    break;
                case "createnewevent":
                    param1 = jsonObject.getString("gameid").replaceAll("%20"," ");
                    param2 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    param3 = jsonObject.getString("usernameplayer");
                    param4 = jsonObject.getString("eventtype");
                    ans = refereeCreateNewEvent(param1, param2, param3, param4);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    assertEquals(ans[2], param3);
                    assertEquals(ans[3], param4);
                    break;
                case "registertogamealert":
                    param1 = jsonObject.getString("gamenumber").replaceAll("%20"," ");
                    ans = fanRegisterToGameAlerts(param1);
                    assertEquals(ans[0], param1);
                    break;
                case "registertopagealert":
                    param1 = jsonObject.getString("usernametofollow").replaceAll("%20"," ");
                    ans = fanRegisterToPage(param1);
                    assertEquals(ans[0], param1);
                    break;
                case "sendcomplaint":
                    param1 = jsonObject.getString("complaintdescription").replaceAll("%20"," ");
                    ans = addComplaint(param1);
                    assertEquals(ans[0], param1);
                    break;
                case "answercomplaints":
                    param1 = jsonObject.getString("answer").replaceAll("%20"," ");
                    param2 = jsonObject.getString("id").replaceAll("%20"," ");
                    ans = answerCompliant(param1, param2);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    break;
                case "addpermissiontoteammanger":
                    param1 = jsonObject.getString("username").replaceAll("%20"," ");
                    param2 = jsonObject.getString("permissions").replaceAll("%20"," ");
                    ans = addPermissionToTeamManager(param1, param2);
                    assertEquals(ans[0], param1);
                    assertEquals(ans[1], param2);
                    break;
                default:
                    assertEquals(controllerMethod, false);
            }
        } catch (Exception e) {
            assertEquals(controllerMethod, false);
        }
    }

    private Object[] addPermissionToTeamManager(String param1, String param2) {
        return new Object[]{param1,param2};
    }

    private Object[] answerCompliant(String param1, String param2) {
        return new Object[]{param1,param2};
    }

    private Object[] addComplaint(String param1) {
        return new Object[]{param1};
    }

    private Object[] fanRegisterToPage(String param1) {
        return new Object[]{param1};
    }

    private Object[] fanRegisterToGameAlerts(String param1) {
        return new Object[]{param1};
    }

    private Object[] refereeCreateNewEvent(String param1, String param2, String param3, String param4) {
        return new Object[]{param1, param2, param3, param4};
    }

    private Object[] refereeEditGameEvent(String param1, String param2, String param3, String param4, String param5) {
        return new Object[]{param1, param2, param3, param4, param5};
    }

    private Object[] defineRefereeInLeague(String param1, String param2, String param3) {
        return new Object[]{param1, param2, param3};
    }

    private Object[] addOrDeleteRefereeToSystem(String param1, String param2, String param3, String param4) {
        return new Object[]{param1, param2, param3, param4};
    }

    private Object[] endGame(String param1, String param2, String param3, String param4, String param5) {
        return new Object[]{param1, param2, param3, param4, param5};
    }

    private Object[] addTeamToSeasonInLeague(String param1, String param2, String param3) {
        return new Object[]{param1, param2, param3};
    }

    private Object[] updatePointsPolicy(String param1, String param2, String param3, String param4, String param5) {
        return new Object[]{param1, param2, param3, param4, param5};
    }

    private Object[] defineSeasonToLeague(String param1, String param2, String param3, String param4, String param5) {
        return new Object[]{param1, param2, param3, param4, param5};
    }

    private Object[] defineLeague(String param1) {
        return new Object[]{param1};
    }

    private Object[] schedulingGame(String param1, String param2) {
        return new Object[]{param1, param2};
    }

    private Object[] addRoleToUser(String param1, String param2) {
        return new Object[]{param1, param2};
    }

    private Object[] Exit(String param1) {
        return new Object[]{param1};
    }

    private Object[] Login(String param1, String param2) {
        return new Object[]{param1, param2};
    }

    private Object[] AddOrRemoveTeamsAssets(String param1, String param2, String param3) {
        return new Object[]{param1, param2, param3};
    }

    private Object[] ChangeStatusTeam(String param1, String param2) {
        return new Object[]{param1, param2};
    }

    private Object[] AddOrRemoveTeamManager(String param1, String param2, String param3) {
        return new Object[]{param1, param2, param3};
    }

    private Object[] AddOrRemoveTeamOwner(String param1, String param2, String param3) {
        return new Object[]{param1, param2, param3};
    }

    private Object[] AddOrRemoveCoach(String param1, String param2, String param3) {
        return new Object[]{param1, param2, param3};
    }
}