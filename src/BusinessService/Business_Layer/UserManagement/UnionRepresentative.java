package BusinessService.Business_Layer.UserManagement;

import java.util.Observable;
import java.util.Observer;

public class UnionRepresentative extends Subscription implements Observer {

    public UnionRepresentative(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password,email);
        permissions.add_default_union_permission();
    }

    @Override
    public String getRole() {
        return "UnionRepresentative";
    }

    //**********************************************function************************************************************//
    @Override
    public void update(Observable o, Object arg) {
        this.alerts.add((String)arg);
    }

    //**********************************************to string************************************************************//
    @Override
    public String toString() {

        return "UnionRepresentative: " + "\n" +
                "name: " + name + "\n" +
                "email: " + email;
    }
}
