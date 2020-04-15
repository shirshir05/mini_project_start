package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.Coach;
import BusniesServic.Business_Layer.UserManagement.Complaint;
import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Player;
import BusniesServic.Enum.ActionStatus;
import DB_Layer.logger;

import java.util.ArrayList;
import java.util.Observable;

public class AlertController {

    private static ArrayList<Complaint> complaints;


    /**
     *  This function register the fan to alerts of a game he choose.
     */
    public void fanRegisterToGameAlerts(int game_number){
        Observable chosen_game = DataManagement.getGame(game_number);
        chosen_game.addObserver((Fan) DataManagement.getCurrent());
    }

    /**
     * This function register the current user to the page he asked to be registered to.
     * @param arg_user_to_register is the name of the page the user wants to register to
     * @return true if the registeration succeeded
     */
    public boolean fanRegisterToPage(String arg_user_to_register){
        boolean ans = false;
        BusniesServic.Business_Layer.UserManagement.Subscription current_user = DataManagement.containSubscription(arg_user_to_register);
        //TODO -add spelling correction here?
        if (current_user instanceof Coach) {
            ((Coach) current_user).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
            ans = true;
        }
        else if (current_user instanceof Player) {
            ((Player) current_user).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
            ans = true;
        }
        else{
            Team t = DataManagement.findTeam(arg_user_to_register);
            if (t!=null){
                t.getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                ans = true;
            }
        }
        logger.log("fan_register_to_page, page mane: "+arg_user_to_register+" ,user mane: "+ current_user.getUserName() +" successful: "+ans);
        if (!ans) {
            //TODO - remove print
            System.out.println("Wrong page name");
        }
        return ans;
    }

    // TODO permissions?


    /**
     * This method adds a complaint by a user.
     * @param fan the fan who created the complaint
     */
    public static ActionStatus addComplaint(String complaintDescription, Fan fan){
        ActionStatus AC = null;
        if(complaintDescription == null || complaintDescription.isEmpty()) {
            AC = new ActionStatus(false, "Complaint cannot be empty");
        }
        else {
            if (complaints == null)
                complaints = new ArrayList<>();
            Complaint c = new Complaint(complaintDescription);
            fan.addComplaint(c);
            complaints.add(c);
            AC = new ActionStatus(true, "Complaint added successfully");
        }
        logger.log("Add Complaint of user : "+ fan.getName() +" "+AC.getDescription());
        return AC;
    }

    /**
     * for the use of the system manager, to read all the complaints in the system
     * @return a list of all the complaints
     */
    public static ArrayList<Complaint> getComplaints() {
        return complaints;
    }

    /*
    public void addComplaint(String complaint_description){
        DataManagement.setComplaint(complaint_description);
    }
     */





}