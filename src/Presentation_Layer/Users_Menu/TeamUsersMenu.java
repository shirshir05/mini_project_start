package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

public class TeamUsersMenu implements UserMenu {

    private String teamMenu = "choose action: \n1: update personal information \n2: upload information to personal page \n"+
            "3: update team assets \n4: edit team status \n5: manage finance\n6: Exit";

    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(teamMenu);
            if(input == 1) {
                //edit personal info - get edit items choice by the type of subscription.

            }
            else if(input == 2) {
                //upload info to page

            }
            else if(input == 3) {
                //chose type of asset in the team, set or remove(player,coach,manager,owner,files) or update info.

            }
            else if(input == 4) {
                //close or open team

            }
            else if(input == 5) {
                //manage finance

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

    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += teamMenu + " " + args[0] + "\n";
        int input = Integer.parseInt(args[0]);
        if(input == 1) {
            //edit personal info - get edit items choice by the type of subscription.

        }
        else if(input == 2) {
            //upload info to page

        }
        else if(input == 3) {
            //chose type of asset in the team, set or remove(player,coach,manager,owner,files) or update info.

        }
        else if(input == 4) {
            //close or open team

        }
        else if(input == 5) {
            //manage finance

        } else if (input == 6) {
            //Exit
            output += "insert User name: " + args[1] +"\n";
            String username =   args[1];
            output += "insert password: " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());

        } else {
            return new ActionStatus(false, output + "invalid choice\n");
        }
        return new ActionStatus(false, output);
    }

}
