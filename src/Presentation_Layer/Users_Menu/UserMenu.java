package Presentation_Layer.Users_Menu;

import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;

public interface UserMenu {

    public ActionStatus presentUserMenu();

    public ActionStatus presentUserMenu(String[] args);


}
