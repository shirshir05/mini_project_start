package Business_Layer.Business_Items.UserManagement;

public class Guest extends Subscription {

    public Guest(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password,email);

    }

    @Override
    public String getRole() {
        return "Guest";
    }

}
