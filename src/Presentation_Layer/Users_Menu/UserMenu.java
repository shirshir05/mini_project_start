package Presentation_Layer.Users_Menu;

import BusinessService.Enum.ActionStatus;

public interface UserMenu {

    public ActionStatus presentUserMenu();

    public ActionStatus presentUserMenu(String[] args);


}
