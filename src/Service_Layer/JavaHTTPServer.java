package Service_Layer;

import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Control.LogAndExitController;
import Business_Layer.Enum.ActionStatus;
import org.json.Cookie;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class JavaHTTPServer implements Runnable {
    Cookie cookie = new Cookie();

    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";
    static final String FILE_NOT_FOUND = "404.html";
    static final String METHOD_NOT_SUPPORTED = "not_supported.html";
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
            // TODO - text system connect successfully - system administrator
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
            //System.out.println(DataManagement.findTeam("teamTest").getStatus());
            //DataManagement.setCurrent(DataManagement.containSubscription("ownerTest"));
            DataManagement.setCurrent(DataManagement.containSubscription("teamManagerTest"));
            out = new PrintWriter(connect.getOutputStream());
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String headerLine;
            StringTokenizer parse;
            String controllerMethod;
            String METHOD;
            //code to read and print headers
            headerLine = in.readLine();
            headerSplit = headerLine.split("[\\s/]+");
            System.out.println(headerLine);
            METHOD = headerSplit[0];
            controllerMethod = headerSplit[2];
            System.out.println("controllerMethod is: " + controllerMethod);
            System.out.println("METHOD is: " + METHOD);
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
                System.out.println("payload is: " + payload);
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
                        sendStringData();
                    }
                    break;
                case "watchlogger":
                    actionStatus = StartSystem.getSc().watchLogger(headerSplit[3]);
                    sendStringData();
                    break;
                case "teamsforapproval":
                    actionStatus = StartSystem.getTc().AllTeamApprove();
                    if (actionStatus.isActionSuccessful()) {
                        String[] array = actionStatus.getDescription().split(",");
                        JSONArray jsonArray = new JSONArray(array);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "approveteam":
                    actionStatus = st.getTc().ApproveCreateTeamAlert
                            (headerSplit[3]);
                    sendStringData();
                    break;
                case "search":
                    actionStatus = st.getSc().findData(headerSplit[3]);
                    if (actionStatus.isActionSuccessful()) {
                        String[] arrayOfSearches = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = new JSONArray(arrayOfSearches);
                        sendJsonData(jsonArray);
                    } else {
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
                        sendStringData();
                    }
                    break;
                case "isa":
                    actionStatus = st.getLEc().hasRole(headerSplit[3]);
                    sendStringData();
                    break;
//                case "watchgameevent":
//                    actionStatus = st.getGSc().printGameEvents(Integer.parseInt(headerSplit[3]));
//                    break;
                case "watchgameevent":
                    actionStatus = st.getGSc().printGameEvents(Integer.parseInt(headerSplit[3]));
                    if (actionStatus.isActionSuccessful()) {
                        String[] linesInGameEvent = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = buildJsonArray(linesInGameEvent);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "watchscoretable":
                    actionStatus = st.getGSc().displayScoreTable(headerSplit[3], headerSplit[4]);
                    if(actionStatus.isActionSuccessful()){
                        String[] linesInScoreTable = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = buildJsonArray(linesInScoreTable);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "watchgame":
                    actionStatus = st.getGSc().refereeWatchGames();
                    sendStringData();
                    break;
                case "logout":
                    actionStatus = st.getLEc().Exit(DataManagement.getCurrent().getUserName());
                    sendStringData();
                    break;
                default:
                    actionStatus = new ActionStatus(false, "check correct get function name");
                    sendStringData();
            }
        } catch (Exception e) {
            actionStatus = new ActionStatus(false, e.getMessage());
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
        ActionStatus as = null;
        try {
            switch (controllerMethod) {
                case "registration":
                    as = st.getLEc().Registration
                            (jsonObject.getString("username"),
                                    jsonObject.getString("password"),
                                    jsonObject.getString("role"),
                                    jsonObject.getString("email"));
                    break;
                case "login":
                    DataManagement.setCurrent(null);
                    LogAndExitController lc = st.getLEc();
                    String a = jsonObject.getString("username");
                    String b = jsonObject.getString("password");
                    as = lc.Login(a, b);
                    break;
                case "logout":
                    as = st.getLEc().Exit(jsonObject.getString("username"));
                    break;
                case "answercomplaints":
                    as = st.getAc().answerCompliant(
                            (Integer.parseInt(jsonObject.getString("id"))),
                            jsonObject.getString("answer"));
                    break;
                case "changestatusforteam":
                    as = st.getTc().ChangeStatusTeam
                            (jsonObject.getString("nameteam"),
                                    Integer.parseInt(jsonObject.getString("status")));
                    break;
                case "onschedulingpolicy":
                    as = st.getGSc().schedulingGame
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("seasonname"),
                                    Integer.parseInt(jsonObject.getString("policy")));
                    break;
                case "defineleague":
                    as = st.getGSc().defineLeague(jsonObject.getString("name"));
                    break;
                case "defineseason":
                    as = st.getGSc().defineSeasonToLeague
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("year"),
                                    Integer.parseInt(jsonObject.getString("win")),
                                    Integer.parseInt(jsonObject.getString("loss")),
                                    Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "updatepointpolicy":
                    as = st.getGSc().updatePointsPolicy
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("year"),
                                    Integer.parseInt(jsonObject.getString("win")),
                                    Integer.parseInt(jsonObject.getString("loss")),
                                    Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "addteamtoleague":
                    as = st.getGSc().addTeamToSeasonInLeague
                            (jsonObject.getString("nameteam"),
                                    jsonObject.getString("nameleague"),
                                    jsonObject.getString("year"));
                    break;
                case "addremovereferee":
                    as = st.getGSc().addOrDeleteRefereeToSystem
                            (jsonObject.getString("usernamereferee"),
                                    jsonObject.getString("password"),
                                    jsonObject.getString("email"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;

                case "setrefereeonleague":
                    as = st.getGSc().defineRefereeInLeague
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("usernamereferee"),
                                    jsonObject.getString("year"));
                    break;
                case "addrole":
                    as = st.getLEc().addRoleToUser
                            (jsonObject.getString("role"), jsonObject.getString("password"));
                    break;
                case "addteam":
                    as = st.getTc().RequestCreateTeam
                            (jsonObject.getString("name"),
                                    jsonObject.getString("field"));
                    break;
                case "addremoveplayer":
                    as = st.getTc().AddOrRemovePlayer
                            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremovecoach":
                    as = st.getTc().AddOrRemoveCoach
                            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteamowner":
                    as = st.getTc().AddOrRemoveTeamOwner
                            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteammanager":
                    as = st.getTc().AddOrRemoveTeamManager
                            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteamfield":
                    as = st.getTc().AddOrRemoveTeamsAssets
                            (jsonObject.getString("teamname"), jsonObject.getString("objectname"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addpermissiontoteammanger":
                    as = st.getESUDc().addPermissionToTeamManager
                            (jsonObject.getString("username"), jsonObject.getString("permissions"));
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