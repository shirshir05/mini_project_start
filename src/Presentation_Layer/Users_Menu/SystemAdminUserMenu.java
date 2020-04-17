package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

public class SystemAdminUserMenu implements UserMenu {

    private String adminMenu = "choose action: \n1: close team\n2: remove subscription \n"+
            "3: see complaints \n4: watch log \n5: build recommendation system\n6: Exit";

    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(adminMenu);
            if(input == 1) {
                //close team permanently

            }
            else if(input == 2) {
                //remove subscription permanently

            }
            else if(input == 3) {
                //see and edit complaints

            }
            else if(input == 4) {
                //watch log

            }
            else if(input == 5) {
                //build recommendation system

            }
            else if(input == 6) {
                //Exit

            }
            else{
                cli.presentOnly("invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");
    }
}
