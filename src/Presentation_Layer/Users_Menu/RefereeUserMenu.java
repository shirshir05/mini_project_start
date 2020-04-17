package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
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
                int gameId =  cli.presentAndGetInt("Write game id:");
                String nameTeam =  cli.presentAndGetString("Write name team:");
                String playerName =  cli.presentAndGetString("Write player name that take part in event:");
                String eventType =  cli.presentAndGetString("Write eventType:");
                system.getGSc().refereeCreateNewEvent(gameId,nameTeam,playerName,null);
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
