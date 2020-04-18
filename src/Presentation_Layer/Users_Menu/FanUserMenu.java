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
}
