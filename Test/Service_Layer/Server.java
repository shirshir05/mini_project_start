package Service_Layer;

import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Control.LogAndExitController;
import Business_Layer.Enum.ActionStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Server {
    private StartSystem st;
    private JSONObject jsonObject;

    public Server(){
        st = new StartSystem();
    }



    @Test
    public void main(){
        handlePostMethod("createteam","{name: team1,field: fi }");
    }


    public String[] RemoveSubscription(String userName){
        return new String[]{userName};
    }

    public String[] RequestCreateTeam(String arg_name, String arg_field) {
        return new String[]{arg_name,arg_field};

    }

        public String[] Registration(String arg_user_name, String arg_password, String arg_role, String email){
        return new String[]{arg_user_name,arg_password,arg_role,email};
    }

    public void handlePostMethod(String controllerMethod, String json) {
        ActionStatus as;
        jsonObject = new JSONObject(json);
        String[] ans;
        try {
            switch (controllerMethod) {
                case "createteam":
                    String addteam_pram1 = jsonObject.getString("name").replaceAll("%20"," ");
                    String addteam_pram2 = jsonObject.getString("field").replaceAll("%20"," ");
                    ans = RequestCreateTeam
                            (addteam_pram1,
                                    addteam_pram2);
                    assertEquals(ans[0],addteam_pram1);
                    assertEquals(ans[1],addteam_pram2);

                    break;
                case "addremoveplayer":
                    String addremoveplayer_pram1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    String addremoveplayer_pram2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    as = st.getTc().AddOrRemovePlayer
                            (addremoveplayer_pram1,addremoveplayer_pram2,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremovecoach":
                    String parm1 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    String parm2 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    as = st.getTc().AddOrRemoveCoach
                            (parm1, parm2,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteamowner":
                    String parm11 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    String parm22 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    as = st.getTc().AddOrRemoveTeamOwner
                            (parm11,parm22,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteammanager":
                    String parm10 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    String parm20 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    as = st.getTc().AddOrRemoveTeamManager
                            (parm10,parm20,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "changestatusforteam":
                    String changestatusforteam_pram1 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    as = st.getTc().ChangeStatusTeam
                            (changestatusforteam_pram1,
                                    Integer.parseInt(jsonObject.getString("status")));
                    break;
                case "addremoveteamfield":
                    String parm111 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    String parm222 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    as = st.getTc().AddOrRemoveTeamsAssets
                            (parm111, parm222,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "registration":
                    String pram1 = jsonObject.getString("username").replaceAll("%20"," ");
                    String pram2 =  jsonObject.getString("password").replaceAll("%20"," ");
                    String pram3 =  jsonObject.getString("role").replaceAll("%20"," ");
                    String pram4 =  jsonObject.getString("email").replaceAll("%20"," ");
                    ans = Registration(pram1,pram2,pram3,pram4);
                    assertEquals(ans[0],"shir");
                    assertEquals(ans[1],"noama ssa");
                    assertEquals(ans[3],"shir");
                    assertEquals(ans[4],"noama ssa");
                    break;
                case "login":
                    DataManagement.setCurrent(null);
                    LogAndExitController lc = st.getLEc();
                    String a = jsonObject.getString("username").replaceAll("%20"," ");
                    String b = jsonObject.getString("password").replaceAll("%20"," ");
                    as = lc.Login(a, b);
                    break;
                case "logout":
                    String logout_pram1 =jsonObject.getString("username").replaceAll("%20"," ");
                    as = st.getLEc().Exit(logout_pram1);
                    break;
                case "addrole":
                    String addrole_pram1 = jsonObject.getString("role").replaceAll("%20"," ");
                    String addrole_pram2 = jsonObject.getString("password").replaceAll("%20"," ");
                    as = st.getLEc().addRoleToUser
                            (addrole_pram1, addrole_pram2);
                    break;
                case "onschedulingpolicy":
                    String onschedulingpolicy_pram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String onschedulingpolicy_pram2 = jsonObject.getString("seasonname").replaceAll("%20"," ");
                    as = st.getGSc().schedulingGame(onschedulingpolicy_pram1,onschedulingpolicy_pram2,
                            Integer.parseInt(jsonObject.getString("policy")));
                    break;
                case "defineleague":
                    String defineleague_pram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    as = st.getGSc().defineLeague(defineleague_pram1);
                    break;
                case "defineseason":
                    String defineseason_pram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String defineseason_pram2 = jsonObject.getString("year").replaceAll("%20"," ");
                    as = st.getGSc().defineSeasonToLeague(defineseason_pram1,defineseason_pram2,
                            Integer.parseInt(jsonObject.getString("win")),
                            Integer.parseInt(jsonObject.getString("loss")),
                            Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "updatepointpolicy":
                    String updatepointpolicypram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String updatepointpolicypram2 = jsonObject.getString("year").replaceAll("%20"," ");
                    as = st.getGSc().updatePointsPolicy
                            (updatepointpolicypram1, updatepointpolicypram2,
                                    Integer.parseInt(jsonObject.getString("win")),
                                    Integer.parseInt(jsonObject.getString("loss")),
                                    Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "addteamtoleague":
                    String addteamtoleaguey_pram1 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    String addteamtoleaguey_pram2 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String addteamtoleaguey_pram3 = jsonObject.getString("year").replaceAll("%20"," ");
                    as = st.getGSc().addTeamToSeasonInLeague
                            (addteamtoleaguey_pram1,
                                    addteamtoleaguey_pram2,
                                    addteamtoleaguey_pram3);
                    break;
                case "savegame":
                    String param4 = jsonObject.getString("gameid").replaceAll("%20"," ");
                    String param5 = jsonObject.getString("goalhost").replaceAll("%20"," ");
                    String param6 = jsonObject.getString("goalguest").replaceAll("%20"," ");
                    String param7 = jsonObject.getString("year").replaceAll("%20"," ");
                    String param8 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    as = st.getGSc().endGame(Integer.parseInt(param4), Integer.parseInt(param5),
                            Integer.parseInt(param6), param7, param8);
                    break;
                case "addremovereferee":
                    String addremovereferee_pram1 = jsonObject.getString("usernamereferee").replaceAll("%20"," ");
                    String addremovereferee_pram2 = jsonObject.getString("password").replaceAll("%20"," ");
                    String addremovereferee_pram3 = jsonObject.getString("email").replaceAll("%20"," ");
                    as = st.getGSc().addOrDeleteRefereeToSystem
                            (addremovereferee_pram1,
                                    addremovereferee_pram2,
                                    addremovereferee_pram3,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "setrefereeonleague":
                    String setrefereeonleague_pram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String setrefereeonleague_pram2 = jsonObject.getString("usernamereferee").replaceAll("%20"," ");
                    String setrefereeonleague_pram3 = jsonObject.getString("year").replaceAll("%20"," ");
                    as = st.getGSc().defineRefereeInLeague
                            (setrefereeonleague_pram1,
                                    setrefereeonleague_pram2,
                                    setrefereeonleague_pram3);
                    break;
                case "editgameevent":
                    String param13 = jsonObject.getString("gameid").replaceAll("%20"," ");
                    String param14 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    String param15 = jsonObject.getString("eventtype").replaceAll("%20"," ");
                    String param16 = jsonObject.getString("nameuser").replaceAll("%20"," ");
                    String param17 = jsonObject.getString("datetime").replaceAll("%20"," ");
                    as = st.getGSc().refereeEditGameEvent
                            (Integer.parseInt(param13), param14, param15, param16, param17);
                    break;
                case "createnewevent":
                    String param9 = jsonObject.getString("gameid").replaceAll("%20"," ");
                    String param10 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    String param11 = jsonObject.getString("usernameplayer").replaceAll("%20"," ");
                    String param12 = jsonObject.getString("eventtype").replaceAll("%20"," ");
                    as = st.getGSc().refereeCreateNewEvent(Integer.parseInt(param9), param10, param11, param12);
                    break;
                case "registertogamealert":
                    String param1 = jsonObject.getString("gamenumber").replaceAll("%20"," ");
                    as = st.getAc().fanRegisterToGameAlerts(Integer.parseInt(param1));
                    break;
                case "registertopagealert":
                    String param2 = jsonObject.getString("usernametofollow").replaceAll("%20"," ");
                    as = st.getAc().fanRegisterToPage(param2);
                    break;
                case "sendcomplaint":
                    String param3 = jsonObject.getString("complaintdescription").replaceAll("%20"," ");
                    as = st.getAc().addComplaint(param3);
                    break;
                case "answercomplaints":
                    String answercomplaints_pram1 = jsonObject.getString("answer").replaceAll("%20"," ");
                    as = st.getAc().answerCompliant(
                            (Integer.parseInt(jsonObject.getString("id"))),answercomplaints_pram1
                    );
                    break;
                case "addpermissiontoteammanger":
                    String addpermissiontoteammanger_1 = jsonObject.getString("username").replaceAll("%20"," ");
                    String addpermissiontoteammanger_2 = jsonObject.getString("permissions").replaceAll("%20"," ");
                    as = st.getESUDc().addPermissionToTeamManager
                            (addpermissiontoteammanger_1, addpermissiontoteammanger_2);
                    break;
                default:
                    as = new ActionStatus(false, "check correct post function name");
            }
        } catch (Exception e) {
            e.printStackTrace();
            as = new ActionStatus(false, e.getMessage());
            DataManagement.saveError("class httpServer post function: "+e.getMessage());
        }
    }
}
