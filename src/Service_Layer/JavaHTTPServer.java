package Service_Layer;

import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Control.LogAndExitController;
import Business_Layer.Enum.ActionStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class JavaHTTPServer implements Runnable {

    // port to listen connection
    static final int PORT = 8008;
    StartSystem st = new StartSystem();

    // verbose mode
    static final boolean verbose = true;

    // Client Connection via Socket Class
    private Socket connect;
    private ActionStatus actionStatus;
    private JSONObject jsonObject;
    private String[] headerSplit;
    private PrintWriter out;

    public JavaHTTPServer(Socket c) {
        connect = c;
        StartSystem startSystem = new StartSystem();
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            // we listen until user halts server execution
            while (true) {
                JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept());
                if (verbose) {
                    System.out.println("Connecton opened. (" + new Date() + ")");
                }
                // create dedicated thread to manage the client connection
                Thread thread = new Thread(myServer);
                thread.start();
            }
//
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    @Override
    public void run() {

        BufferedReader in = null;
        try {
            //DataManagement.setCurrent(DataManagement.containSubscription("union"));
            out = new PrintWriter(connect.getOutputStream());
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String headerLine;
            String controllerMethod;
            String METHOD;
            String username;
            //code to read and print headers
            headerLine = in.readLine();
            headerSplit = headerLine.split("[\\s/]+");
            System.out.println(headerLine);
            METHOD = headerSplit[0];
            controllerMethod = headerSplit[3];
            username = headerSplit[2];
            DataManagement.setCurrent(null);
            if(!METHOD.equals("registration")){
                DataManagement.setCurrent(DataManagement.containSubscription(username));
            }
            System.out.println("controllerMethod is: " + controllerMethod);
            System.out.println("controllerMethod is: " + controllerMethod);
            System.out.println("METHOD is: " + METHOD);
            System.out.println("username is: " + username);
            while ((headerLine = in.readLine()).length() != 0) {
                System.out.println(headerLine);
                if(headerLine.contains("Access-Control-Request-Method")){
                    METHOD = headerLine.split(": ")[1];
                }
            }

            if (METHOD.equals("POST")) {
                StringBuilder payload = new StringBuilder();
                while (in.ready()) {
                    payload.append((char) in.read());
                }
                //System.out.println("payload is: " + payload);
                jsonObject = new JSONObject(payload.toString());
                actionStatus = handlePostMethod(controllerMethod);
                if (actionStatus.isActionSuccessful()) {
                    out.println("HTTP/1.1 200 OK");
                    //default implement send string data

                } else {
                    out.println("HTTP/1.1 202 Accepted");
                }
                sendStringData();
            }

            else if (METHOD.equals("GET")) {
               handleGetMethod(controllerMethod);
            }

            else if (METHOD.equals("DELETE")) {
                handleDeleteMethod(controllerMethod);
                sendStringData();
            }

            out.flush(); // flush character output stream buffer
            connect.close();
        } catch (Exception e) {
        }
    }

    private void handleDeleteMethod(String controllerMethod) {
        try {
            switch (controllerMethod) {
                case "removesubscription":
                    actionStatus = st.getLEc().RemoveSubscription
                            (jsonObject.getString("username"));
                    break;
                default:
                    actionStatus = new ActionStatus(false, "check correct delete function name");
            }
        } catch (Exception e) {
            actionStatus = new ActionStatus(false, e.getMessage());
            DataManagement.saveError("class httpServer delete function: "+e.getMessage());
        }
    }

    private void handleGetMethod(String controllerMethod) {
        try {
            switch (controllerMethod) {
                case "watchcomplaints":
                    actionStatus = st.getAc().getAllComplaints();
                    if (actionStatus.isActionSuccessful()) {
                        String[] arrayComplaints = actionStatus.getDescription().split("~!#%");
                        JSONArray jsonArray = new JSONArray(arrayComplaints);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "watchlogger":
                    actionStatus = StartSystem.getSc().watchLogger(headerSplit[4]);
                    if(actionStatus.isActionSuccessful()){
                        out.println("HTTP/1.1 200 OK");
                    }else{
                        out.println("HTTP/1.1 202 Accepted");
                    }
                    sendStringData();
                    break;
                case "teamsforapproval":
                    actionStatus = StartSystem.getTc().AllTeamApprove();
                    if (actionStatus.isActionSuccessful()) {
                        String[] array = actionStatus.getDescription().split(",");
                        JSONArray jsonArray = new JSONArray(array);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "approveteam":
                    headerSplit[4] = headerSplit[4].replaceAll("%20"," ");
                    actionStatus = st.getTc().ApproveCreateTeamAlert (headerSplit[4]);
                      //(headerSplit[3]);
                    if(actionStatus.isActionSuccessful()){
                        out.println("HTTP/1.1 200 OK");
                    }else{
                        out.println("HTTP/1.1 202 Accepted");
                    }
                    sendStringData();
                    break;
                case "search":
                    actionStatus = st.getSc().findData(headerSplit[4]);
                    if (actionStatus.isActionSuccessful()) {
                        String[] arrayOfSearches = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = new JSONArray(arrayOfSearches);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "watchsearchhistory":
                    actionStatus = st.getSc().showSearchHistory();
                    if (actionStatus.isActionSuccessful()) {
                        String[] arrayOfSearches = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = new JSONArray(arrayOfSearches);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "isa":
                    actionStatus = st.getLEc().hasRole(headerSplit[4]);
                    if(actionStatus.isActionSuccessful()){
                        out.println("HTTP/1.1 200 OK");
                    }else{
                        out.println("HTTP/1.1 202 Accepted");
                    }
                    sendStringData();
                    break;
                case "watchgameevent":
                    actionStatus = st.getGSc().printGameEvents(Integer.parseInt(headerSplit[4]));
                    if (actionStatus.isActionSuccessful()) {
                        String[] linesInGameEvent = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = buildJsonArray(linesInGameEvent);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "watchscoretable":
                    actionStatus = st.getGSc().displayScoreTable(headerSplit[4], headerSplit[5]);
                    if(actionStatus.isActionSuccessful()){
                        String[] linesInScoreTable = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = buildJsonArray(linesInScoreTable);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "watchgame":
                    actionStatus = st.getGSc().refereeWatchGames();
                    if(actionStatus.isActionSuccessful()){
                        out.println("HTTP/1.1 200 OK");
                    }else{
                        out.println("HTTP/1.1 202 Accepted");
                    }
                    sendStringData();
                    break;
                case "readallalrets":
                    actionStatus = st.getAc().readAllAlerts();
                    if(actionStatus.isActionSuccessful()){
                        String[] lines = actionStatus.getDescription().split("!@#");
                        JSONArray jsonArray = buildJsonArrayAlert(lines);
                        sendJsonData(jsonArray);
                    } else {
                        out.println("HTTP/1.1 202 Accepted");
                        sendStringData();
                    }
                    break;
                case "alertnew":
                    actionStatus = st.getAc().hasAlertNew();
                    if(actionStatus.isActionSuccessful()){
                        out.println("HTTP/1.1 200 OK");
                    }else{
                        out.println("HTTP/1.1 202 Accepted");
                    }
                    sendStringData();
                    break;
                default:
                    actionStatus = new ActionStatus(false, "check correct get function name");
                    out.println("HTTP/1.1 202 Accepted");
                    sendStringData();
            }
        } catch (Exception e) {
            actionStatus = new ActionStatus(false, e.getMessage());
            //TODO -  MICHAL!!!!!  THIS LINE IN COMMENT
            DataManagement.saveError("class httpServer get function: "+e.getMessage());
            sendStringData();
        }
    }

    private JSONArray buildJsonArray(String[] linesInScoreTable) {
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for (String line : linesInScoreTable) {
            String[] arrayIndex = line.split("[\\s-|]+");
            //[Team, Barcelona, games, 15, wins, 6, drawns, 4, loses, 2, goalsScores, 3, goalsGet, 4, points, 10]
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(arrayIndex[0], arrayIndex[1]);
            jsonObject.put(arrayIndex[2], arrayIndex[3]);
            jsonObject.put(arrayIndex[4], arrayIndex[5]);
            jsonObject.put(arrayIndex[6], arrayIndex[7]);
            jsonObject.put(arrayIndex[8], arrayIndex[9]);
            jsonObject.put(arrayIndex[10], arrayIndex[11]);
            jsonObject.put(arrayIndex[12], arrayIndex[13]);
            jsonObject.put(arrayIndex[14], arrayIndex[15]);
            jsonArray.put(index, jsonObject);
            index++;
        }
        return jsonArray;
    }
    private JSONArray buildJsonArrayAlert(String[] lineAlert) {
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for (String line : lineAlert) {
            jsonArray.put(index, line);
            index++;
        }
        return jsonArray;
    }

    private void sendJsonData(JSONArray jsonArray) {
        out.println("HTTP/1.1 200 OK");
        out.println("Server: Java HTTP Server from SSaurel : 1.0");
        out.println("Date: " + new Date());
        out.println("Access-Control-Allow-Origin: https://shirshir05.github.io");
        out.println("Access-Control-Allow-Credentials: true");
        out.println("Content-Type: application/json");
        out.println("Content-length: " + jsonArray.toString().getBytes().length);
        out.println(""); // blank line between headers and content, very important !
        out.println(jsonArray);
    }

    private void sendStringData() {
        out.println("Server: Java HTTP Server from SSaurel : 1.0");
        out.println("Date: " + new Date());
        out.println("Access-Control-Allow-Origin: https://shirshir05.github.io");
        out.println("Access-Control-Allow-Credentials: true");
        out.println("Content-length: " + actionStatus.getDescription().length());
        out.println(""); // blank line between headers and content, very important !
        out.println(actionStatus.getDescription());
        out.flush(); // flush character output stream buffer
    }

    private ActionStatus handlePostMethod(String controllerMethod) {
        ActionStatus as;
        try {
            switch (controllerMethod) {
                case "registration":
                    String pram1 = jsonObject.getString("username").replaceAll("%20"," ");
                    String pram2 =  jsonObject.getString("password").replaceAll("%20"," ");
                    String pram3 =  jsonObject.getString("role").replaceAll("%20"," ");
                    String pram4 =  jsonObject.getString("email").replaceAll("%20"," ");
                    as = st.getLEc().Registration
                            (pram1,pram2,pram3,pram4);
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
                case "answercomplaints":
                    String answercomplaints_pram1 = jsonObject.getString("answer").replaceAll("%20"," ");
                    as = st.getAc().answerCompliant(
                            (Integer.parseInt(jsonObject.getString("id"))),answercomplaints_pram1
                            );
                    break;
                case "changestatusforteam":
                    String changestatusforteam_pram1 = jsonObject.getString("nameteam").replaceAll("%20"," ");
                    as = st.getTc().ChangeStatusTeam
                            (changestatusforteam_pram1,
                                    Integer.parseInt(jsonObject.getString("status")));
                    break;
                case "onschedulingpolicy":
                    String onschedulingpolicy_pram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String onschedulingpolicy_pram2 = jsonObject.getString("seasonname").replaceAll("%20"," ");
                    as = st.getGSc().schedulingGame
                            (onschedulingpolicy_pram1,onschedulingpolicy_pram2
                                    ,
                                    Integer.parseInt(jsonObject.getString("policy")));
                    break;
                case "defineleague":
                    String defineleague_pram1 = jsonObject.getString("name").replaceAll("%20"," ");
                    as = st.getGSc().defineLeague(defineleague_pram1);
                    break;
                case "defineseason":
                    String defineseason_pram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String defineseason_pram2 = jsonObject.getString("year").replaceAll("%20"," ");

                    as = st.getGSc().defineSeasonToLeague
                            (defineseason_pram1,defineseason_pram2
                                    ,
                                    Integer.parseInt(jsonObject.getString("win")),
                                    Integer.parseInt(jsonObject.getString("loss")),
                                    Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "updatepointpolicy":
                    String updatepointpolicypram1 = jsonObject.getString("nameleague").replaceAll("%20"," ");
                    String updatepointpolicypram2 = jsonObject.getString("year").replaceAll("%20"," ");
                    as = st.getGSc().updatePointsPolicy
                            (updatepointpolicypram1,
                                    updatepointpolicypram2,
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
                case "addrole":
                    String addrole_pram1 = jsonObject.getString("role").replaceAll("%20"," ");
                    String addrole_pram2 = jsonObject.getString("password").replaceAll("%20"," ");
                    as = st.getLEc().addRoleToUser
                            (addrole_pram1, addrole_pram2);
                    break;
                case "addteam":
                    String addteam_pram1 = jsonObject.getString("name").replaceAll("%20"," ");
                    String addteam_pram2 = jsonObject.getString("field").replaceAll("%20"," ");
                    as = st.getTc().RequestCreateTeam
                            (addteam_pram1,
                                    addteam_pram2);
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
                case "addremoveteamfield":
                    String parm111 = jsonObject.getString("teamname").replaceAll("%20"," ");
                    String parm222 = jsonObject.getString("objectname").replaceAll("%20"," ");
                    as = st.getTc().AddOrRemoveTeamsAssets
                            (parm111, parm222,
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addpermissiontoteammanger":
                    String addpermissiontoteammanger_1 = jsonObject.getString("username").replaceAll("%20"," ");
                    String addpermissiontoteammanger_2 = jsonObject.getString("permissions").replaceAll("%20"," ");
                    as = st.getESUDc().addPermissionToTeamManager
                            (addpermissiontoteammanger_1, addpermissiontoteammanger_2);
                    break;
                case "registertogamealert":
                    as = st.getAc().fanRegisterToGameAlerts
                            (Integer.parseInt(jsonObject.getString("gamenumber")));
                    break;
                case "registertopagealert":
                    as = st.getAc().fanRegisterToPage
                            (jsonObject.getString("usernametofollow"));
                    break;
                case "sendcomplaint":
                    as = st.getAc().addComplaint
                            (jsonObject.getString("complaintdescription"));

                    break;
//            case "search":
//                //complete after pull from master
//                break;

                case "savegame":
                    as = st.getGSc().endGame
                            (Integer.parseInt(jsonObject.getString("gameid")),
                                    Integer.parseInt(jsonObject.getString("goalhost")),
                                    Integer.parseInt(jsonObject.getString("goalguest")),
                                    jsonObject.getString("year"),
                                    jsonObject.getString("nameleague"));
                    break;
                case "createnewevent":
                    as = st.getGSc().refereeCreateNewEvent
                            (Integer.parseInt(jsonObject.getString("gameid")),
                                    jsonObject.getString("nameteam"),
                                    jsonObject.getString("usernameplayer"),
                                    jsonObject.getString("eventtype"));
                    break;
                case "editgameevent":
                    as = st.getGSc().refereeEditGameEvent
                            (Integer.parseInt(jsonObject.getString("gameid")),
                                    jsonObject.getString("nameteam"),
                                    jsonObject.getString("eventtype"),
                                    jsonObject.getString("nameuser"),
                                    jsonObject.getString("datetime"));
                    break;
                default:
                    as = new ActionStatus(false, "check correct post function name");
            }
        } catch (Exception e) {
            e.printStackTrace();
            as = new ActionStatus(false, e.getMessage());
            DataManagement.saveError("class httpServer post function: "+e.getMessage());
        }
        return as;
    }
}