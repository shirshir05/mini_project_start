package Business_Layer.Business_Control;

import Business_Layer.Business_Items.Game.Game;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.Trace.PersonalPage;
import Business_Layer.Business_Items.UserManagement.*;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Enum.Configurations;
import Business_Layer.Enum.PermissionAction;
import DB_Layer.logger;


import javax.xml.crypto.Data;
import java.util.HashSet;

public class AlertController {


    public ActionStatus readAllAlerts(){
        ActionStatus AC;
        Subscription sub = DataManagement.getCurrent();
        if(sub == null){
            AC = new ActionStatus(false,"No user in system");
        }else{
            HashSet<String> alertList = sub.getAlerts();
            StringBuilder ans = new StringBuilder("");
            for (String alert :alertList) {
                ans.append(alert).append("!@#");
            }
            if(alertList.size() > sub.getNumberAlerts()){
                AC = new ActionStatus(true,ans.toString());
                sub.setNumberAlerts(alertList.size());
            }else{
                AC = new ActionStatus(false,ans.toString());
            }
        }
        return AC;
    }


    /**
     * if has new alert return true
     * @return -
     */
    public ActionStatus hasAlertNew(){
        ActionStatus AC;
        Subscription sub = DataManagement.getCurrent();
        if(sub == null){
               AC = new ActionStatus(true,"No user in system");
        }else{
            HashSet<String> alertList = sub.getAlerts();
            if(alertList.size() > sub.getNumberAlerts()){
                AC = new ActionStatus(true,"");
            }else{
                AC = new ActionStatus(false,"");
            }
        }
        return AC;
    }


    /**
     *  This function register the fan to alerts of a game he choose.
     */
    public ActionStatus fanRegisterToGameAlerts(int game_number){
        ActionStatus AC;
        Game game = DataManagement.getGame(game_number);
        if (!(DataManagement.getCurrent() instanceof Fan)){
            AC = new ActionStatus(false,"You are not a Fan");
        }
        else if(game == null){
            AC = new ActionStatus(false,"There is no such game in the system");
        }
        else{
            game.addObserver((Fan) DataManagement.getCurrent());
            AC = new ActionStatus(true,"You were registered successfully to the game alerts");
        }
        return AC;
    }

    /**
     * This function register the current user to the page he asked to be registered to.
     * @param arg_user_to_register is the name of the page the user wants to register to
     * @return true if the registration succeeded
     */
    public ActionStatus fanRegisterToPage(String arg_user_to_register) {
        ActionStatus AC ;
        if (DataManagement.getCurrent() != null) {
            Subscription userWithPersonalPage = DataManagement.containSubscription(arg_user_to_register);
            Team team = DataManagement.findTeam(arg_user_to_register);
            if (!(DataManagement.getCurrent() instanceof Fan)) {
                AC = new ActionStatus(false, "You are not a Fan");
            } else if (userWithPersonalPage == null && team == null) {
                AC = new ActionStatus(false, "There is no such page");
            }
             else if (userWithPersonalPage instanceof UnifiedSubscription && (((UnifiedSubscription) userWithPersonalPage).isACoach() || ((UnifiedSubscription) userWithPersonalPage).isAPlayer())) {
                //by default - registering to the player page. if it doesn't exist, then register to the coach page
                UnifiedSubscription us = (UnifiedSubscription) userWithPersonalPage;
                PersonalPage personalPage = us.getPlayerPersonalPage();
                AC = new ActionStatus(true, "You were successfully registered to the Player page");
                if (personalPage == null) {
                    personalPage = us.getCoachPersonalPage();
                    AC = new ActionStatus(true, "You were successfully registered to the Coach page");
                }
                if (personalPage != null) {
                    personalPage.addObserver((Fan) DataManagement.getCurrent());
                }
                else {
                    AC = new ActionStatus(false, "Could not find the personal page you requested");
                }
            } else if (team != null) {
                team.getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                AC = new ActionStatus(true, "You were successfully registered to the Team page");
            } else {
                AC = new ActionStatus(false, "Cannot find the personal page you requested");
            }

            logger.log("fan_register_to_page, page name: " + arg_user_to_register + " ,user name: " + DataManagement.getCurrent().getUserName() + " successful: " + AC.getDescription());
        }
        else {
            AC = new ActionStatus(false,"No user is logged in to the system");
        }
        logger.log("fan_register_to_page, page name: " + arg_user_to_register + " failed: " + AC.getDescription());
        return AC;
    }

    /**
     * This method adds a complaint by a user.
     * @param complaintDescription is the complaint
     */
    public ActionStatus addComplaint(String complaintDescription){
        ActionStatus AC;
        if(complaintDescription == null || complaintDescription.isEmpty()) {
            AC = new ActionStatus(false, "Complaint cannot be empty");
        }
        else if (!(DataManagement.getCurrent() instanceof Fan)){
            AC = new ActionStatus(false, "You are not a Fan");
        }
        else {
            Fan f = (Fan)DataManagement.getCurrent();
            int NumberComplain =  Configurations.getNumberOfComplaint();
            Complaint c = new Complaint("(ID: " +NumberComplain + ")" +complaintDescription);
            Configurations.setPropValues("NumberOfComplaint",++NumberComplain);
            f.addComplaint(c);
            DataManagement.addComplaint(c);
            AC = new ActionStatus(true, "Complaint added successfully");
        }
        logger.log("Add complaint status: "+AC.getDescription());
        return AC;
    }

    /**
     * for the use of the system manager, to read all the complaints in the system
     * @return a hash set of all the complaints
     */
    public ActionStatus getAllComplaints() {
        StringBuilder returnValue = new StringBuilder("");
        if(DataManagement.getCurrent()!= null && DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints)) {
            HashSet<Complaint> set = DataManagement.getAllComplaints();
            for (Complaint comp:set) {
                returnValue.append(comp.getDescription() + " is answer: " + comp.isAnswered()).append("~!#%"); //seperator between complaints
            }
        }
        if(!returnValue.equals("")) {
            return new ActionStatus(true, returnValue.toString());
        }
        else {
            return new ActionStatus(false, "no complaints");
        }
    }

    /**
     * answer compliant
     * @param numberComplaint -
     * @param answer -
     * @return -
     */
    public ActionStatus answerCompliant(int numberComplaint, String answer){
        ActionStatus AC;
        HashSet<Complaint> set = DataManagement.getAllComplaints();
        Complaint complaint = null;
        for (Complaint comp:set) {
            String description = comp.getDescription();
            String numberCom = description.substring(description.indexOf("(ID: ") + 5,description.indexOf(")"));
            int number = Integer.parseInt(numberCom);
            if(number == numberComplaint){
                complaint = comp;
            }
        }
        if(DataManagement.getCurrent()== null || !DataManagement.getCurrent().getPermissions().check_permissions(PermissionAction.Respond_to_complaints)) {
            AC = new ActionStatus(false, "You do not have the required permissions to answer complaints");
        }
        else if(complaint == null){
            AC = new ActionStatus(false, "No id complaint");
        }
        else if(complaint.isAnswered()){
            AC = new ActionStatus(false, "Complaint has been answered already");
        }
        else if(answer == null || answer.isEmpty()){
            AC = new ActionStatus(false, "An answer to a complaint cannot be empty or null");
        }else{
            //remove the complaint without the answer
            DataManagement.getAllComplaints().remove(complaint);
            //answer the complaint
            complaint.answerComplaint(answer);
            //add it back to the list of complaints
            DataManagement.addComplaint(complaint);
            AC= new ActionStatus(true, "Complaint has been answered successfully");
        }
        return AC;
    }
}