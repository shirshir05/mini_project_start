package Service_Layer;

import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Enum.ActionStatus;

public class Server {

    private StartSystem st;
    public Server(){
        st = new StartSystem();
    }

    public ActionStatus postMethod(String controllerMethod, String[] params) {
        ActionStatus as;
        try {
            switch (controllerMethod) {
                case "registration":
                    as = StartSystem.getLEc().Registration(params[0],params[1], params[2], params[3]);
                    break;
                case "login":
                    DataManagement.setCurrent(null);
                    as = st.getLEc().Login(params[0],params[1]);
                    break;
                case "logout":
                    as = st.getLEc().Exit(params[0]);
                    break;
                case "answercomplaints":
                    as = st.getAc().answerCompliant(Integer.parseInt(params[0]),params[1]);
                    break;
                    case "changestatusforteam":
                    as = st.getTc().ChangeStatusTeam(params[0],Integer.parseInt(params[1]));
                    break;
                case "onschedulingpolicy":
                    as = st.getGSc().schedulingGame(params[0],params[1],Integer.parseInt(params[2]));
                    break;
                case "defineleague":
                    as = st.getGSc().defineLeague(params[0]);
                    break;
                case "defineseason":
                    as = st.getGSc().defineSeasonToLeague(params[0],params[1],
                            Integer.parseInt(params[2]),Integer.parseInt(params[3]),Integer.parseInt(params[4]));
                    break;
                case "updatepointpolicy":
                    as = st.getGSc().updatePointsPolicy(params[0],params[1],
                            Integer.parseInt(params[2]),Integer.parseInt(params[3]),
                            Integer.parseInt(params[4]));
                    break;
                case "addteamtoleague":
                    as = st.getGSc().addTeamToSeasonInLeague(params[0],params[1], params[2]);
                    break;
                case "addremovereferee":
                    as = st.getGSc().addOrDeleteRefereeToSystem(params[0],params[1],
                            params[2],Integer.parseInt(params[3]));
                    break;
                case "setrefereeonleague":
                    as = st.getGSc().defineRefereeInLeague(params[0],params[1], params[2]);
                    break;
                case "addrole":
                    as = st.getLEc().addRoleToUser(params[0],params[1]);
                    break;
                case "addteam":
                    as = st.getTc().RequestCreateTeam(params[0],params[1]);
                    break;
                case "addremoveplayer":
                    as = st.getTc().AddOrRemovePlayer(params[0],params[1],Integer.parseInt(params[2]));
                    break;
                case "addremovecoach":
                    as = st.getTc().AddOrRemoveCoach(params[0],params[1],Integer.parseInt(params[2]));
                    break;
                case "addremoveteamowner":
                    as = st.getTc().AddOrRemoveTeamOwner(params[0],params[1],Integer.parseInt(params[2]));
                    break;
                case "addremoveteammanager":
                    as = st.getTc().AddOrRemoveTeamManager(params[0],params[1],Integer.parseInt(params[2]));
                    break;
                case "addremoveteamfield":
                    as = st.getTc().AddOrRemoveTeamsAssets(params[0],params[1],Integer.parseInt(params[2]));
                    break;
                case "addpermissiontoteammanger":
                    as = st.getESUDc().addPermissionToTeamManager(params[0],params[1]);
                    break;
                case "registertogamealert":
                    as = st.getAc().fanRegisterToGameAlerts(Integer.parseInt(params[2]));
                    break;
                case "registertopagealert":
                    as = st.getAc().fanRegisterToPage(params[0]);
                    break;
                case "sendcomplaint":
                    as = st.getAc().addComplaint(params[0]);

                    break;
//            case "search":
//                //complete after pull from master
//                break;

                case "savegame":
                    as = st.getGSc().endGame(Integer.parseInt(params[0]),
                            Integer.parseInt(params[1]),Integer.parseInt(params[2]),
                            params[3],params[4]);
                    break;
                case "createnewevent":
                    as = st.getGSc().refereeCreateNewEvent(Integer.parseInt(params[0]),
                            params[1],params[2],params[3]);
                    break;
                case "editgameevent":
                    as = st.getGSc().refereeEditGameEvent(Integer.parseInt(params[0]),
                            params[1],params[2],params[3],params[4]);
                    break;
                default:
                    as = new ActionStatus(false, "check correct post function name");
            }
        } catch (Exception e) {
            e.printStackTrace();
            as = new ActionStatus(false, e.getMessage());
        }
        return as;
    }
}
