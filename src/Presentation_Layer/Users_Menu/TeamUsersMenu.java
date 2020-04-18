package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

public class TeamUsersMenu implements UserMenu {

    private String teamMenu = "choose action: \n1:(6.1) update asset";

    @Override
    public ActionStatus presentUserMenu() {

        return null;
    }

    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += teamMenu + " " + args[0] + "\n";
        int input = Integer.parseInt(args[0]);
        if(input == 1) {
            //edit personal info - get edit items choice by the type of subscription.
            output += "\nchoose action: \n1:(6.1) update player \n2:(6.1) update coach \nn3:(6.1) update filed \n4:(6.1) update team owner\n" +
                    "5:(6.1) update team manager \nuser input- " + args[1] +"\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit == 1){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert user name player: \nuser input- " + args[3] +"\n";
                String namePlayer  =  args[3];
                output += "insert 1 to add or 0 to remove: \nuser input- " + args[4] +"\n";
                int addOrRemove  =  Integer.parseInt(args[4]);
                ActionStatus ac = StartSystem.getTc().AddOrRemovePlayer(nameTeam,namePlayer,addOrRemove);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }

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
