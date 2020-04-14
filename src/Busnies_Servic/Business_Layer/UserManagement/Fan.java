package Busnies_Servic.Business_Layer.UserManagement;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class Fan extends Subscription implements Observer{

    protected HashSet<Complaint> list_complaint;

    /**
     * @param arg_user_name
     * @param arg_password
     * @param email
     */
    public Fan(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password, email);
        permissions.add_default_fan_permission();
        list_complaint = new HashSet<>();
    }


    /**
     * Add complaint to fan
     * @param complaint
     * @return
     */
    public boolean addComplaint(Complaint complaint){
        if(!list_complaint.contains(complaint)) {
            complaint.addObserver(this);
            list_complaint.add(complaint);
            return true;
        }
        else{
            return false;
        }
    }


    /**
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        String alert = (String)arg;
        if (!alert.substring(0,3).equals("The")){
            this.alerts.add((String)arg);
        }
    }

    @Override
    public String toString() {

        return "Fan: " + "\n" +
                "name: " + name + "\n" +
                "email: " + email;
    }
}
