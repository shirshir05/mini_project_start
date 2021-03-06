package Users.Users_Menu;

import Business_Layer.Enum.ActionStatus;
import Service_Layer.StartSystem;

public class SystemAdminUserMenu implements UserMenu {

    private String adminMenu = "choose action: \n1: close team\n2: remove subscription \n"+
            "3: see complaints \n4: watch log \n5: build recommendation system\n6: Exit";

    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = new UserCLI();
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

    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += adminMenu + " " + args[0] + "\n";
        int input = Integer.parseInt(args[0]);
        if(input == 1) {
            //close team permanently
            output += "insert team name:\nuser input- "+ args[1]+ "\n";
            ActionStatus ac = StartSystem.Tc.ChangeStatusTeam(args[1],-1);
            return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
        }
        else if(input == 2) {
            //remove subscription permanently
            output += "insert user name:\nuser input- "+ args[1]+ "\n";
            ActionStatus ac =StartSystem.LEc.RemoveSubscription(args[1]);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input == 3) {
            //see and edit complaints
            output += "choose action: \n1:watch all complaints \n2:watch and answer unanswered complaints\nuser input- " + args[1] + "\n";
            int choice = Integer.parseInt(args[1]);
            if(choice == 1){
                output += StartSystem.Ac.getAllComplaints().getDescription();
                return new ActionStatus(true,output);
            }else if(choice == 2){
              
            }
        }
        else if(input == 4) {
            //watch log
            return new ActionStatus(true,output +"log file is updates and can be found in /logs/systemLog");
        }
        else if(input == 5) {
            //build recommendation system
            return new ActionStatus(true,output +"this functionality is not implemented");

        } else if (input == 6) {
            //Exit
            output += "insert User name:\nuser input-  " + args[1] +"\n";
            String username =   args[1];
            output += "insert password:\nuser input-  " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());

        } else {
            return new ActionStatus(false, output + "invalid choice\n");
        }
        return new ActionStatus(false, output);
    }
}
