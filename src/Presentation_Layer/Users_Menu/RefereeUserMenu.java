package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

public class RefereeUserMenu implements UserMenu {

    private String refereeMenu = "choose action: \n1: update personal information\n2: see my games\n"+
            "3: add event in game\n4: Exit";

    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(refereeMenu);
            if(input == 1) {
                //edit personal info

            }
            else if(input == 2) {
                //see my games - see all the games I am referee in them.

            }
            else if(input == 3) {
                //add event in game and get game report

            }
            else if(input == 4) {
                //Exit

            }
            else{
                cli.presentOnly("invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");

    }
}
