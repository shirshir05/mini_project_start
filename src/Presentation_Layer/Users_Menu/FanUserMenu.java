package Presentation_Layer.Users_Menu;

import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import BusniesServic.Service_Layer.LogAndExitController;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

import java.io.Console;
import java.util.Scanner;

public class FanUserMenu implements UserMenu  {

    private String fanMenu = "choose action: \n1:Logout \n2:Registration for tracking personal pages \n3:Games alert notification" +
            "\n4:Writing a complaint \n5:Viewing Search History \n6:Edit personal information \n7:Search \n8:Exit" ;


    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        LogAndExitController LEC = system.getLEc();
        Fan fan = new Fan("shir","12345","shir0@post.bgu.ac.il");
        DataManagement.setCurrent(fan);
        DataManagement.setSubscription(fan);
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(fanMenu);
            if(input==1) {
                String username =  cli.presentAndGetString("Please enter a username:");
                String password =  cli.presentAndGetString("Please enter a password:");
                cli.presentOnly(system.getLEc().Exit(username,password).getDescription());
            }
            else if(input==2) {
                if (input == 2) {
                    String username =  cli.presentAndGetString("Please enter a username of the person you want to follow:");
                    if(system.getAc().fanRegisterToPage(username).isActionSuccessful()){
                        cli.presentOnly("The transaction completed successfully.");
                    }else{
                        cli.presentOnly("The field you entered was incorrect.");
                    }
                }
            }
            else if(input ==3 ) {
                int game =  cli.presentAndGetInt("Enter a game id:");
                if(system.getAc().fanRegisterToGameAlerts(game).isActionSuccessful()){
                    cli.presentOnly("The transaction completed successfully.");
                }else{
                    cli.presentOnly("The number you entered was incorrect.");
                }
            }
            else if(input ==4 ) {
                String complaint =  cli.presentAndGetString("Write the complaint:");
                cli.presentOnly(system.getAc().addComplaint(complaint).getDescription());
            }
            else if(input ==5 ) {
                cli.presentOnly(system.getSc().showSearchHistory());
            }else if(input ==6 ) {
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
            else if(input==7){
                String word =  cli.presentAndGetString("Write Search word:");
                cli.presentOnly(system.getSc().findData(word));
            }else if(input==8){
                LEC.Exit("Guset","123456");
            }
            else{
                cli.presentOnly("invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");
    }

    public ActionStatus presentUserMenu(String[] args) {

        String output = "";
        output += fanMenu + " " + args[0] +"\n";
        int input =  Integer.parseInt(args[0]);
        if(input==1) {
            output += "insert User name: " + args[1] +"\n";
            String username =   args[1];
            output += "insert password: " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input==2) {
            if (input == 2) {
                output += "insert username of the person you want to follow: " + args[1] +"\n";
                String username =args[1];
                if(StartSystem.getAc().fanRegisterToPage(username).isActionSuccessful()){
                    output+="The transaction completed successfully";
                    return new ActionStatus(true,output);
                }else{
                    output += "The field you entered was incorrect.";
                    return new ActionStatus(false,output);
                }
            }
        }
        else if(input ==3 ) {
            output += "Enter a game id: " + args[1] +"\n";
            int game =  Integer.parseInt(args[1]);
            if(StartSystem.getAc().fanRegisterToGameAlerts(game).isActionSuccessful()){
                return new ActionStatus(true,output + "The transaction completed successfully.");
            }else{
                return new ActionStatus(false,output+"The number you entered was incorrect.");
            }
        }
        else if(input ==4 ) {
            String complaint =  args[1] +"\n";
            return StartSystem.getAc().addComplaint(complaint);
        }
        else if(input ==5 ) {
            return new ActionStatus(true ,output + StartSystem.getSc().showSearchHistory());
        }else if(input ==6 ) {
            output += "choose action: \n1:Edit name \n2:Edit email" + args[1] + "\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit ==1 ){
                output += "Write name:" + args[2] +"\n";
                String name =  args[2];
                return new ActionStatus(true ,output + StartSystem.getESUDc().editSubscriptionName(DataManagement.getCurrent().getUserName(),name).getDescription());
            }else if(edit==2){
                output += "Write emil:" + args[2] +"\n";
                String name =  args[2];
                return new ActionStatus(true ,output + StartSystem.getESUDc().editSubscriptionEmail(DataManagement.getCurrent().getUserName(),name).getDescription());
            }else{
                return new ActionStatus(false ,output + "The digit is invalid.");
            }
        }
        else if(input==7) {
            output += "Write Search word: " + args[1] + "\n";
            String word = args[1];
            return new ActionStatus(true, output + StartSystem.getSc().findData(word));
        }
        else if(input ==8){
            output += "insert User name: " + args[1] +"\n";
            String username =   args[1];
            output += "insert password: " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else{
            return new ActionStatus(false,output + "invalid choice");
        }
        return new ActionStatus(false,output);
    }
}
