package BusniesServic.Business_Layer.UserManagement;

import java.util.Observable;
import java.util.Observer;

public class SystemAdministrator extends Subscription implements Observer{

    /**
     * constructor
     * @param arg_user_name
     * @param arg_password
     * @param email
     */
    public SystemAdministrator(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password,email);
        permissions.add_default_admin_permission();
    }

    /**
     * add alert
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        this.alerts.add((String)arg);
    }

    //**********************************************to string ************************************************************//

    @Override
    public String toString() {

        return "SystemAdministrator: " + "\n" +
                "name: " + name + "\n" +
                "email: " + email;
    }

}
