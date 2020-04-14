package Busnies_Servic.Service_Layer;

import Busnies_Servic.Enum.ActionStatus;
import Busnies_Servic.Business_Layer.UserManagement.Complaint;
import Busnies_Servic.Business_Layer.UserManagement.Fan;
import DB_Layer.logger;

import java.util.ArrayList;

public class ComplaintController{

    // TODO permissions?

    private static ArrayList<Complaint> complaints;

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

    public void addComplaint(String complaint_description){
        DataManagement.setComplaint(complaint_description);
    }


}
