package Users.Users_Menu;

import Business_Layer.Enum.ActionStatus;

public interface UserMenu {

    public ActionStatus presentUserMenu();

    public ActionStatus presentUserMenu(String[] args);


}
