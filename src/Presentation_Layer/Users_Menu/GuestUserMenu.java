package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import BusniesServic.Service_Layer.LogAndExitController;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;
import Presentation_Layer.UserInterface;

public class GuestUserMenu implements UserMenu {

    private String guestMenu = "choose action: \n1:Register \n2:Login \n3:Search";

    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        LogAndExitController LEC = system.getLEc();
        cli.presentOnly("hello guest");
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(guestMenu);
            if(input==1) {
                boolean correct = false;
                while (!correct) {
                    String name = cli.presentAndGetString("insert User name");
                    String password = cli.presentAndGetString("insert 5 character password");
                    String email = cli.presentAndGetString("insert email");
                    String role = cli.presentAndGetString("insert your Role");
                    ActionStatus ac = LEC.Registration(name, password, role, email);
                    cli.presentOnly(ac.getDescription());
                    if(ac.isActionSuccessful()){
                        correct = true;
                    }
                }
            }
            else if(input==2) {
                boolean correct = false;
                while (!correct) {
                    String name = cli.presentAndGetString("insert User name");
                    String password = cli.presentAndGetString("insert password");
                    DataManagement.setCurrent(null);
                    ActionStatus ac = LEC.Login(name, password);
                    cli.presentOnly(ac.getDescription());
                    if(ac.isActionSuccessful()){
                        correct = true;
                    }
                }
                return new ActionStatus(true, "Login with new user");
            }
            else if(input ==3 ) {
                LEC.Exit("Guset", "123456");
                ExitOrChangeUser = true;
                break;
            }
            else{
                cli.presentOnly("invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");
    }


    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += guestMenu + " " + args[0] +"\n";
        int input =  Integer.parseInt(args[0]);
        if(input==1) {
            output += "insert User name" + args[1] +"\n";
            String name = args[1];
            output += "insert password" + args[2] +"\n";
            String password = args[2];
            output += "insert email" + args[3] +"\n";
            String email = args[3];
            output += "insert your Role" + args[4] +"\n";
            String role = args[4];
            ActionStatus ac = StartSystem.LEc.Registration(name, password, role, email);
            output += (ac.getDescription()) + "\n";
            return new ActionStatus(ac.isActionSuccessful(),output);
        }
        else if(input==2) {
            output += "insert User name " + args[1] + "\ninsert password" + args[2] + "\n";
            String name = args[1];
            String password = args[2];
            DataManagement.setCurrent(null);
            ActionStatus ac = StartSystem.LEc.Login(name, password);
            return new ActionStatus(ac.isActionSuccessful(), output + ac.getDescription());
        }
        else if(input ==3 ) {
            StartSystem.LEc.Exit("Guset", "123456");
            return new ActionStatus(true,output + " Exit");
        }
        else{
            output += "invalid choice\n";
            return new ActionStatus(false,output);
        }
    }

}
