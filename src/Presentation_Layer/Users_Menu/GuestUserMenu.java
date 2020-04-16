package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.LogAndExitController;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;
import Presentation_Layer.UserInterface;

public class GuestUserMenu implements UserMenu {

    private String guestMenu = "choose action: \n1:action \n2:action \n3:action";

    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        LogAndExitController LEC = system.getLEc();
        while(!ExitOrChangeUser){
            cli.presentOnly("hello guest");
            int input =  cli.presentAndGetInt(guestMenu);
            switch(input){
                case 1:
                    if(input==1){}
                case2:
                    if(input==2){}
                case 3:
                    LEC.Exit("Guset","123456");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");
    }
}
