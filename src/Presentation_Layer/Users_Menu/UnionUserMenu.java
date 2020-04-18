package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

public class UnionUserMenu implements UserMenu {

    private String unionMenu = "choose action: \n1: add league\n2: add season \n"+
            "3: manage referee \n4: manage score policy \n5: manage games \n6: finance\n7: Exit";
    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(unionMenu);
            if(input == 1) {
                //add league

            }
            else if(input == 2) {
                //add season to league

            }
            else if(input == 3) {
                //manage referee - add, remove, set to league and season

            }
            else if(input == 4) {
                // manage score policy and location in league

            }
            else if(input == 5) {
                //manage games - game setting policy and start outomatic games setting;

            }
            else if(input == 6) {
                //finance - edit budget regulation and edit union finance

            }
            else if(input == 7) {
                //exit

            }
            else{
                cli.presentOnly("invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");

    }

    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += unionMenu + " " + args[0] + "\n";
        int input = Integer.parseInt(args[0]);
        if(input == 1) {
            //add league

        }
        else if(input == 2) {
            //add season to league

        }
        else if(input == 3) {
            //manage referee - add, remove, set to league and season

        }
        else if(input == 4) {
            // manage score policy and location in league

        }
        else if(input == 5) {
            //manage games - game setting policy and start outomatic games setting;

        }
        else if(input == 6) {
            //finance - edit budget regulation and edit union finance

        }else if (input == 7) {
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
