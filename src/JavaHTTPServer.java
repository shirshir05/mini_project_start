import BusinessService.Enum.ActionStatus;
import Presentation_Layer.StartSystem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

// The tutorial can be found just here on the SSaurel's Blog : 
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// Each Client Connection will be managed in a dedicated Thread
public class JavaHTTPServer implements Runnable{

    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";
    static final String FILE_NOT_FOUND = "404.html";
    static final String METHOD_NOT_SUPPORTED = "not_supported.html";
    // port to listen connection
    static final int PORT = 8008;

    // verbose mode
    static final boolean verbose = true;

    // Client Connection via Socket Class
    private Socket connect;
    private ActionStatus actionStatus;
    private JSONObject jsonObject;

    public JavaHTTPServer(Socket c) {
        connect = c;
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

        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    @Override
    public void run() {
        //POST /api/register HTTP/1.1
        //OPTIONS /api/login HTTP/1.1
        // we manage our particular client connection
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedOutputStream dataOut = null;
        String fileRequested = null;
        try {
            // we read characters from the client via input stream on the socket
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());
            String headerLine ;
            StringTokenizer parse;
            String controllerMethod;
            String METHOD;
            dataOut = new BufferedOutputStream(connect.getOutputStream());
            // get first line of the request from the client

            //code to read and print headers
            headerLine = in.readLine();
            System.out.println(headerLine);
            parse = new StringTokenizer(headerLine);
            METHOD = parse.nextToken();
            controllerMethod = headerLine.substring(headerLine.indexOf("api") + 4, headerLine.lastIndexOf(" "));
            System.out.println("controllerMethod is: " + controllerMethod);
            System.out.println("METHOD is: " + METHOD);
            while ((headerLine = in.readLine()).length() != 0) {
                System.out.println(headerLine);
            }
            StringBuilder payload = new StringBuilder();
            while (in.ready()) {
                payload.append((char) in.read());
            }
            System.out.println("payload is: " + payload);
            jsonObject = new JSONObject(payload.toString());
            if(METHOD.equals("POST")){
                actionStatus = handlePostMethod(controllerMethod);
            }
            else if(METHOD.equals("GET")){
                //actionStatus = handleGetMethod(controllerMethod);
            }

            else if(METHOD.equals("REMOVE")){
                //actionStatus = handleDeleteMethod(controllerMethod);
            }

            System.out.println("Payload data is: " + payload.toString());

            if(actionStatus.isActionSuccessful()){
                out.println("HTTP/1.1 200 OK");
                out.println("Server: Java HTTP Server from SSaurel : 1.0");
                out.println("Date: " + new Date());
                //out.println("Content-type: " );
                out.println("Access-Control-Allow-Origin: *");
                out.println("Content-length: " + actionStatus.getDescription().length());
                out.println(""); // blank line between headers and content, very important !
                out.println(actionStatus.getDescription());
                out.flush(); // flush character output stream buffer
                connect.close();
            }


            //JSONObject shir = new JSONObject(actionStatus.toString());
//            JSONObject shir = new JSONObject("{message: check}");
//            out.println("HTTP/1.1 200 OK");
//            out.println("Server: Java HTTP Server from SSaurel : 1.0");
//            out.println("Date: " + new Date());
//            out.println("Content-Type: application/json");
//            out.println("Access-Control-Allow-Origin: *");
//            out.println("Content-length: " + shir.toString().length());
//            out.println(""); // blank line between headers and content, very important !
//            out.println(shir);
//            out.flush(); // flush character output stream buffer
//            connect.close();

        } catch (Exception e) {

        }
    }

    private ActionStatus handleDeleteMethod(String controllerMethod) {
        ActionStatus as = null;
        try {
            switch (controllerMethod) {
                case "removesubscription":
                    as = StartSystem.getLEc().RemoveSubscription
                            (jsonObject.getString("username"));
                    break;
                default:
                    as = new ActionStatus(false, "check correct delete function name");
            }
        }
        catch (Exception e){
            as = new ActionStatus(false, e.getMessage());
        }
        return as;
    }

    private ActionStatus handleGetMethod(String controllerMethod) {
        ActionStatus as = null;
//        try {
//            switch (controllerMethod) {
//                case "watchgameevent":
//                    as = StartSystem.getGSc().printGameEvents
//                            (Integer.parseInt(jsonObject.getString("gameid")));
//                    break;
//                default:
//                    as = new ActionStatus(false, "check correct get function name");
//            }
//        }
//        catch (Exception e){
//            as = new ActionStatus(false, e.getMessage());
//        }
        return as;
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
//            case "answercomplaints":
//                as = StartSystem.getAc().answerCompliant(
//                (jsonObject.getString("username"),
//                        jsonObject.getString("password"));
//                break;
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
                                    Integer.parseInt(jsonObject.getString("onlyweb")));
                    break;

                case "setrefereeonleague":
                    as = StartSystem.getGSc().defineRefereeInLeague
                            (jsonObject.getString("nameleague"),
                                    jsonObject.getString("usernamereferee"),
                                    jsonObject.getString("year"));
                    break;
                case "addrole":
                    as = StartSystem.getLEc().haveRole
                            (jsonObject.getString("role"));
                    break;
                case "createteam":
                    as = StartSystem.getTc().RequestCreateTeam
                            (jsonObject.getString("name"),
                                    jsonObject.getString("field"));
                    break;
                case "addremoveplayer":
                    as = StartSystem.getTc().AddOrRemovePlayer
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameuserplayer"),
                                    Integer.parseInt(jsonObject.getString("onlyweb")));
                    break;
                case "addremovecoach":
                    as = StartSystem.getTc().AddOrRemoveCoach
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameusercoach"),
                                    Integer.parseInt(jsonObject.getString("onlyweb")));
                    break;
                case "addremoveteamowner":
                    as = StartSystem.getTc().AddOrRemoveTeamOwner
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameuserteamowner"),
                                    Integer.parseInt(jsonObject.getString("onlyweb")));
                    break;
                case "addremoveteammanager":
                    as = StartSystem.getTc().AddOrRemoveTeamManager
                            (jsonObject.getString("nameteam"), jsonObject.getString("nameuserteammanager"),
                                    Integer.parseInt(jsonObject.getString("onlyweb")));
                    break;
                case "addremoveteamfield":
                    as = StartSystem.getTc().AddOrRemoveTeamsAssets
                            (jsonObject.getString("nameteam"), jsonObject.getString("namefiled"),
                                    Integer.parseInt(jsonObject.getString("onlyweb")));
                    break;
                case "addpermissiontoteammanger":
                    as = StartSystem.getESUDc().addPermissionToTeamManager
                            (jsonObject.getString("nameteammanager"), jsonObject.getString("permission"));
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
//            case "search":
//                //complete after pull from master
//                break;

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
//            case "editgameevent":
//                as = StartSystem.getGSc().refereeEditGameEvent
//                        (Integer.parseInt(jsonObject.getString("gameid")),
//                                jsonObject.getString("nameteam"),
//                                jsonObject.getString("eventtype"),
//                                jsonObject.getString("usernameplayer    "),
//                                jsonObject.getString("localdatetime"));
//                break;
                default:
                    as = new ActionStatus(false, "check correct post function name");
            }
        }
        catch (Exception e){
            as = new ActionStatus(false, e.getMessage());
        }
        return as;
    }

    private boolean isLegalWinLossEqual(String win, String loss, String equal) {
        if(isNumeric(win) && isNumeric(loss) && isNumeric(equal)){
            return true;
        }
        return false;
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isPolicyValidDigit(String policy) {
        return policy.equals("1") || policy.equals("2");
    }

    private boolean isStatusValidDigit(String status) {
        return status.equals("-1") || status.equals("0") || status.equals("1");
    }

    public static JSONArray parse(String responseBody){
        JSONArray albums = new JSONArray(responseBody);
        JSONObject album = null;
        for (int i = 0; i < albums.length(); i++){
            album = albums.getJSONObject(i);
        }
        return albums;
    }


//            // we read characters from the client via input stream on the socket
//            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
//            // we get character output stream to client (for headers)
//            out = new PrintWriter(connect.getOutputStream());
//            // get binary output stream to client (for requested data)
//            dataOut = new BufferedOutputStream(connect.getOutputStream());
//
//            // get first line of the request from the client
//            String input = in.readLine();
//            System.out.println(in.readLine());
//            // we parse the request with a string tokenizer
//            StringTokenizer parse = new StringTokenizer(input);
//            String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
//            // we get file requested
//            fileRequested = parse.nextToken().toLowerCase();
//
//            // we support only GET and HEAD methods, we check
//            if (!method.equals("GET")  &&  !method.equals("HEAD") &&  !method.equals("OPTIONS")) {
//                if (verbose) {
//                    System.out.println("501 Not Implemented : " + method + " method.");
//                }
//
//                // we return the not supported file to the client
//                File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
//                int fileLength = (int) file.length();
//                String contentMimeType = "text/html";
//                //read content to return to client
//                byte[] fileData = readFileData(file, fileLength);
//
//                // we send HTTP Headers with data to client
//                out.println("HTTP/1.1 501 Not Implemented");
//                out.println("Server: Java HTTP Server from SSaurel : 1.0");
//                out.println("Date: " + new Date());
//                out.println("Content-type: " + contentMimeType);
//                out.println("Content-length: " + fileLength);
//                out.println(); // blank line between headers and content, very important !
//                out.flush(); // flush character output stream buffer
//                // file
//                dataOut.write(fileData, 0, fileLength);
//                dataOut.flush();
//
//            } else {
//                // GET or HEAD method
//                if (fileRequested.endsWith("/")) {
//                    fileRequested += "/"+ DEFAULT_FILE;
//                }
//
//                File file = new File(WEB_ROOT, fileRequested);
//                int fileLength = (int) file.length();
//                String content = getContentType(fileRequested);
//
//                if (method.equals("GET") || method.equals("OPTIONS")) { // GET method so we return content
//                    byte[] fileData = readFileData(file, fileLength);
//
//                    // send HTTP Headers
//                    out.println("HTTP/1.1 200 OK");
//                    out.println("Server: Java HTTP Server from SSaurel : 1.0");
//                    out.println("Date: " + new Date());
//                    out.println("Content-type: " + content);
//                    out.println("Content-length: " + fileLength);
//                    out.println(); // blank line between headers and content, very important !
//                    out.flush(); // flush character output stream buffer
//
//                    dataOut.write(fileData, 0, fileLength);
//                    dataOut.flush();
//                }
//
//                if (verbose) {
//                    System.out.println("File " + fileRequested + " of type " + content + " returned");
//                }
//
//            }
//
//        } catch (FileNotFoundException fnfe) {
//            try {
//                fileNotFound(out, dataOut, fileRequested);
//            } catch (IOException ioe) {
//                System.err.println("Error with file not found exception : " + ioe.getMessage());
//            }
//
//        } catch (IOException ioe) {
//            System.err.println("Server error : " + ioe);
//        } finally {
//            try {
//                in.close();
//                out.close();
//                dataOut.close();
//                connect.close(); // we close socket connection
//            } catch (Exception e) {
//                System.err.println("Error closing stream : " + e.getMessage());
//            }
//
//            if (verbose) {
//                System.out.println("Connection closed.\n");
//            }
//        try(BufferedReader br = new BufferedReader(
//                new InputStreamReader(connect.getInputStream(), "utf-8"))) {
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//        }
//        catch (Exception e){
//
//        }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    // return supported MIME Types
    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
            return "text/html";
        else
            return "text/plain";
    }

    private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
        File file = new File(WEB_ROOT, FILE_NOT_FOUND);
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        out.println("HTTP/1.1 404 File Not Found");
        out.println("Server: Java HTTP Server from SSaurel : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + content);
        out.println("Content-length: " + fileLength);
        out.println(); // blank line between headers and content, very important !
        out.flush(); // flush character output stream buffer




        if (verbose) {
            System.out.println("File " + fileRequested + " not found");
        }
    }
}