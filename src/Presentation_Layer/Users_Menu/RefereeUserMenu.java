package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Enum.EventType;
import BusniesServic.Service_Layer.DataManagement;
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
                int edit =  cli.presentAndGetInt( "choose action: \n1:Edit name \n2:Edit email");
                if(edit ==1 ){
                    String name =  cli.presentAndGetString("Write name:");
                    cli.presentOnly(system.getESUDc().editSubscriptionName(DataManagement.getCurrent().getUserName(),name).getDescription());
                }else if(edit==2){
                    String name =  cli.presentAndGetString("Write emil:");
                    cli.presentOnly(system.getESUDc().editSubscriptionEmail(DataManagement.getCurrent().getUserName(),name).getDescription());
                }else{
                    cli.presentOnly("The digit is invalid.");
                }
            }
            else if(input == 2) {
                //see my games - see all the games I am referee in them.
                ActionStatus ac = StartSystem.GSc.refereeWatchGames();
                cli.presentOnly(ac.getDescription());
            }
            else if(input == 3) {
                //add event in game and get game report
                int gameId =  cli.presentAndGetInt("Write game id:");
                String nameTeam =  cli.presentAndGetString("Write name team:");
                String playerName =  cli.presentAndGetString("Write player name that take part in event:");
                String eventType =  cli.presentAndGetString("Write eventType:");
                EventType event = StartSystem.GSc.getEventFromString(eventType);
                ActionStatus ac = system.getGSc().refereeCreateNewEvent(gameId,nameTeam,playerName,event);
                cli.presentOnly(ac.getDescription());
            }
            else if(input == 4) {
                //Exit

            }
            else{
                cli.presentOnly("Invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");

    }

    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += refereeMenu + "\nuser input-  " + args[0] +"\n";
        int input =  Integer.parseInt(args[0]);
        if(input == 1) {
            //edit personal info
            output += "choose action: \n1:Edit name \n2:Edit email \n3:Edit qualification\nuser input - " + args[1] + "\n";
            int edit = Integer.parseInt(args[1]);
            if(edit ==1 ){
                output += "insert name:\nuser input- " + args[2] +"\n";
                String name = args[2];
                ActionStatus ac = StartSystem.getESUDc().editSubscriptionName(DataManagement.getCurrent().getUserName(),name);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit==2){
                output += "insert emil:\nuser input- " + args[2] +"\n";
                String name =  args[2];
                ActionStatus ac = StartSystem.getESUDc().editSubscriptionEmail(DataManagement.getCurrent().getUserName(),name);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit ==3){
                output += "insert qualification:\nuser input- " + args[2] +"\n";
                ActionStatus ac = StartSystem.ESUDc.editRefereeQualification(DataManagement.getCurrent().getName(),args[2]);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }
            else{
                return new ActionStatus(false,output +"Invalid input.");
            }
        }
        else if(input == 2) {
            //see my games - see all the games I am referee in them.
            ActionStatus ac = StartSystem.GSc.refereeWatchGames();
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input == 3) {
            //add event in game and get game report
            output += "insert game id:\nuser input- " + args[1] + "\n";
            int gameId =  Integer.parseInt(args[1]);
            output += "insert name team:\nuser input- " + args[2] + "\n";
            String nameTeam = args[2];
            output += "insert player name that take part in event:\nuser input- " + args[3] + "\n";
            String playerName =  args[3];
            output += "insert eventType:\nuser input- " + args[4] + "\n";
            String eventType =  args[4];
            EventType event = StartSystem.GSc.getEventFromString(eventType);
            ActionStatus ac =  StartSystem.getGSc().refereeCreateNewEvent(gameId,nameTeam,playerName,event);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input == 4) {
            //Exit
            output += "insert User name:\nuser input-  " + args[1] +"\n";
            String username =   args[1];
            output += "insert password:\nuser input-  " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else{
            return new ActionStatus(false,output+"invalid choice");
        }
    }
}
