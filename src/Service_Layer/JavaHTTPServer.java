package Service_Layer;
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
            } else if (METHOD.equals("GET")) {
                handleGetMethod(controllerMethod);
            } else if (METHOD.equals("DELETE")) {
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
                    actionStatus = StartSystem.getLEc().RemoveSubscription
                            (jsonObject.getString("username"));
                    break;
                default:
                    actionStatus = new ActionStatus(false, "check correct delete function name");
            }
        } catch (Exception e) {
            actionStatus = new ActionStatus(false, e.getMessage());
        }
    }

    private void handleGetMethod(String controllerMethod) {
        try {
            switch (controllerMethod) {
                case "watchcomplaints":
                    actionStatus = StartSystem.getAc().getAllComplaints();
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
                    actionStatus = StartSystem.getTc().ApproveCreateTeamAlert
                            (jsonObject.getString(headerSplit[3]));
                    sendStringData();
                    break;
                case "search":
                    actionStatus = StartSystem.getSc().findData(headerSplit[3]);
                    if (actionStatus.isActionSuccessful()) {
                        String[] arrayOfSearches = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = new JSONArray(arrayOfSearches);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "watchsearchhistory":
                    actionStatus = StartSystem.getSc().showSearchHistory();
                    if (actionStatus.isActionSuccessful()) {
                        String[] arrayOfSearches = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = new JSONArray(arrayOfSearches);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "isa":
                    actionStatus = StartSystem.getLEc().hasRole(headerSplit[3]);
                    sendStringData();
                    break;
                case "watchgameevent":
                    actionStatus = StartSystem.getGSc().printGameEvents(Integer.parseInt(headerSplit[3]));
                    if (actionStatus.isActionSuccessful()) {
                        String[] linesInGameEvent = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = buildJsonArray(linesInGameEvent);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "watchscoretable":
                    actionStatus = StartSystem.getGSc().displayScoreTable(headerSplit[3], headerSplit[4]);
                    if (actionStatus.isActionSuccessful()) {
                        String[] linesInScoreTable = actionStatus.getDescription().split("\n");
                        JSONArray jsonArray = buildJsonArray(linesInScoreTable);
                        sendJsonData(jsonArray);
                    } else {
                        sendStringData();
                    }
                    break;
                case "watchgame":
                    actionStatus = StartSystem.getGSc().refereeWatchGames();
                    sendStringData();
                    break;

                default:
                    actionStatus = new ActionStatus(false, "check correct get function name");
                    sendStringData();
            }
        } catch (Exception e) {
            actionStatus = new ActionStatus(false, e.getMessage());
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
        out.println("Content-Type: application/json");
        out.println("Access-Control-Allow-Origin: *");
        out.println("Content-length: " + jsonArray.toString().getBytes().length);
        out.println(""); // blank line between headers and content, very important !
        out.println(jsonArray);
    }

    private void sendStringData() {
        out.println("Server: Java HTTP Server from SSaurel : 1.0");
        out.println("Date: " + new Date());
        //out.println("Content-type: " );
        out.println("Cookie: " + "matan");
        out.println("Access-Control-Allow-Origin: *");
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
                    as = StartSystem.getLEc().Registration
                            (jsonObject.getString("username"),
                                    jsonObject.getString("password"),
                                    jsonObject.getString("role"),
                                    jsonObject.getString("email"));
                    break;
                case "login":
                    as = StartSystem.getLEc().Login
                            (jsonObject.getString("username"),
                                    jsonObject.getString("password"));
                    break;
                case "logout":
                    as = StartSystem.getLEc().Exit
                            (jsonObject.getString("username"));
                    break;
                case "answercomplaints":
                    as = StartSystem.getAc().answerCompliant(
                            (Integer.parseInt(jsonObject.getString("id"))),
                            jsonObject.getString("answer"));
                    break;
                case "changestatusforteam":
                    as = StartSystem.getTc().ChangeStatusTeam
                            (jsonObject.getString("nameteam"),
                                    Integer.parseInt(jsonObject.getString("status")));
                    break;
                case "onschedulingpolicy":
                    as = StartSystem.getGSc().schedulingGame
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("seasonname"),
                                    Integer.parseInt(jsonObject.getString("policy")));
                    break;
                case "defineleague":
                    as = StartSystem.getGSc().defineLeague
                            (jsonObject.getString("nameleague"));
                    break;
                case "defineseason":
                    as = StartSystem.getGSc().defineSeasonToLeague
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("year"),
                                    Integer.parseInt(jsonObject.getString("win")),
                                    Integer.parseInt(jsonObject.getString("loss")),
                                    Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "updatepointpolicy":
                    as = StartSystem.getGSc().updatePointsPolicy
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("year"),
                                    Integer.parseInt(jsonObject.getString("win")),
                                    Integer.parseInt(jsonObject.getString("loss")),
                                    Integer.parseInt(jsonObject.getString("equal")));
                    break;
                case "addteamtoleague":
                    as = StartSystem.getGSc().addTeamToSeasonInLeague
                            (jsonObject.getString("nameteam"),
                                    jsonObject.getString("nameleague"),
                                    jsonObject.getString("year"));
                    break;
                case "addremovereferee":
                    as = StartSystem.getGSc().addOrDeleteRefereeToSystem
                            (jsonObject.getString("usernamereferee"),
                                    jsonObject.getString("password"),
                                    jsonObject.getString("email"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;

                case "setrefereeonleague":
                    as = StartSystem.getGSc().defineRefereeInLeague
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("usernamereferee"),
                                    jsonObject.getString("year"));
                    break;
                case "addrole":
                    as = StartSystem.getLEc().addRoleToUser
                            (jsonObject.getString("role"), jsonObject.getString("password"));
                    break;
                case "createteam":
                    as = StartSystem.getTc().RequestCreateTeam
                            (jsonObject.getString("name"),
                                    jsonObject.getString("field"));
                    break;
                case "addremoveplayer":
                    as = StartSystem.getTc().AddOrRemovePlayer
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameuserplayer"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremovecoach":
                    as = StartSystem.getTc().AddOrRemoveCoach
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameusercoach"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteamowner":
                    as = StartSystem.getTc().AddOrRemoveTeamOwner
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameuserteamowner"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteammanager":
                    as = StartSystem.getTc().AddOrRemoveTeamManager
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameuserteammanager"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addremoveteamfield":
                    as = StartSystem.getTc().AddOrRemoveTeamsAssets
                            (jsonObject.getString("nameteam"), jsonObject.getString("namefiled"),
                                    Integer.parseInt(jsonObject.getString("addremove")));
                    break;
                case "addpermissiontoteammanger":
                    as = StartSystem.getESUDc().addPermissionToTeamManager
                            (jsonObject.getString("username"), jsonObject.getString("permissions"));
                    break;
                case "registertogamealert":
                    as = StartSystem.getAc().fanRegisterToGameAlerts
                            (Integer.parseInt(jsonObject.getString("gamenumber")));
                    break;
                case "registertopagealert":
                    as = StartSystem.getAc().fanRegisterToPage
                            (jsonObject.getString("usernametofollow"));
                    break;
                case "sendcomplaint":
                    as = StartSystem.getAc().addComplaint
                            (jsonObject.getString("complaintdescription"));

                    break;
                case "savegame":
                    as = StartSystem.getGSc().endGame
                            (Integer.parseInt(jsonObject.getString("gameid")),
                                    Integer.parseInt(jsonObject.getString("goalhost")),
                                    Integer.parseInt(jsonObject.getString("goalguest")),
                                    jsonObject.getString("year"),
                                    jsonObject.getString("nameleague"));
                    break;
                case "createnewevent":
                    as = StartSystem.getGSc().refereeCreateNewEvent
                            (Integer.parseInt(jsonObject.getString("gameid")),
                                    jsonObject.getString("nameteam"),
                                    jsonObject.getString("usernameplayer"),
                                    jsonObject.getString("eventtype"));
                    break;
                case "editgameevent":
                    as = StartSystem.getGSc().refereeEditGameEvent
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
            as = new ActionStatus(false, e.getMessage());
        }
        return as;
    }
}