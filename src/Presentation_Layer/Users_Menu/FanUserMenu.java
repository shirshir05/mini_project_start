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

    private String fanMenu = "choose action: \n1:(3.1) Logout \n2:(3.2) Registration for tracking personal pages \n3:(3.3) Games alert notification" +
            "\n4:(3.4) Insert a complaint \n5:(3.5) Viewing Search History \n6:(3.6) Edit personal information \n7:(3.5) Search \n8:Exit" ;




    @Override
    public ActionStatus presentUserMenu() {
        return null;
    }

    @Override
    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += fanMenu + "\nuser input- " + args[0] +"\n";
        int input =  Integer.parseInt(args[0]);
        if(input==1) {
            output += "insert User name: \nuser input- " + args[1] +"\n";
            String username =   args[1];
            output += "insert password: \nuser input- " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input==2) {
            if (input == 2) {
                output += "insert username of the person you want to follow:  \nuser input- " + args[1] +"\n";
                String username =args[1];
                ActionStatus ac = StartSystem.getAc().fanRegisterToPage(username);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }
        }
        else if(input ==3 ) {
            output += "insert a game id: \nuser input-" + args[1] +"\n";
            int game =  Integer.parseInt(args[1]);
            ActionStatus ac = StartSystem.getAc().fanRegisterToGameAlerts(game);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input ==4 ) {
            output +=  "insert complaint: \nuser input- " + args[1] +"\n";
            String complaint =  args[1];
            ActionStatus ac = StartSystem.getAc().addComplaint(complaint);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else if(input ==5 ) {
            return new ActionStatus(true ,output + StartSystem.getSc().showSearchHistory());
        }else if(input ==6 ) {
            output += "choose action: \n1:Edit name \n2:Edit email \nuser input- " + args[1] + "\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit ==1 ){
                output += "insert name: \nuser input- " + args[2] +"\n";
                String name =  args[2];
                ActionStatus ac = StartSystem.getESUDc().editSubscriptionName(DataManagement.getCurrent().getUserName(),name);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit==2){
                output += "insert emil: \nuser input- " + args[2] +"\n";
                String name =  args[2];
                ActionStatus ac = StartSystem.getESUDc().editSubscriptionEmail(DataManagement.getCurrent().getUserName(),name);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else{
                return new ActionStatus(false ,output + "The digit is invalid.");
            }
        }
        else if(input==7) {
            output += "insert Search word: \nuser input- " + args[1] + "\n";
            String word = args[1];
            return new ActionStatus(true, output + StartSystem.getSc().findData(word));
        }
        else if(input ==8){
            output += "insert User name: \nuser input- " + args[1] +"\n";
            String username =   args[1];
            output += "insert password: \nuser input- " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }
        else{
            return new ActionStatus(false,output + "Invalid choice");
        }
        return new ActionStatus(false,output);
    }
}
