package Presentation_Layer;

import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.*;
import DB_Layer.myFirstDB;

import javax.naming.directory.SearchControls;

public class StartSystem {

    public static AlertController Ac = new AlertController();
    public static BudgetController Bc = new BudgetController();
    public static EditAndShowUserDetails ESUDc = new EditAndShowUserDetails();
    public static GameSettingsController GSc = new GameSettingsController();
    public static LogAndExitController LEc = new LogAndExitController();
    public static SearchControls Sc = new SearchControls();
    public static TeamController Tc = new TeamController();
    public static UserCLI cli = new UserCLI();

    public static void ResetToFactory(){

        //create general Guest user
        ActionStatus str2 = LEc.Registration("Guest", "123456", "Guest","Guestmail@mail.com");

        //create first SystemAdministrator user
        String name = cli.presentAndGetString("insert admin's User name");
        String password = cli.presentAndGetString("insert admin's password");
        String email = cli.presentAndGetString("insert admin's email");
        ActionStatus str1 = LEc.Registration(name, password, "SystemAdministrator",email);

        //start connection to external systems.
        cli.presentOnly("connection to external systems");
        cli.presentOnly("Finance system connection stable: "+DataManagement.getExternalConnStatus("finance").isActionSuccessful());
        cli.presentOnly("Tax system connection stable: "+DataManagement.getExternalConnStatus("tax").isActionSuccessful());

        //choose user to log in with to the system;
        boolean chosen = false;
        while(!chosen) {
            int i = cli.presentAndGetInt("would you like to poesied as SystemAdministrator or as guest?\npress 0 to SystemAdministrator\npress 1 to Guest");
            switch (i) {
                case 0:
                    //admin menu;
                    LEc.Login(name, password);
                    chosen = true;
                case 1:
                    //guest menu;
                    LEc.Login("Guest", "123456");
                    chosen = true;
                default:
                    cli.presentOnly("invalid choice.");
            }
        }
        //todo - send to correct user presentation to show user options menu;
    }

    public void startFromDB(){
        myFirstDB db = new myFirstDB();
        db.loadUsersInfo();
        db.loadTeamInfo();
        db.loadGameInfo();
        db.loadLeagueInfo();
        LEc.Login("Guest", "123456");
        cli.presentOnly("hello Guest");
        //todo - send to correct user presentation to show user options menu;
    }


    public static AlertController getAc() {
        return Ac;
    }

    public static BudgetController getBc() {
        return Bc;
    }

    public static EditAndShowUserDetails getESUDc() {
        return ESUDc;
    }

    public static GameSettingsController getGSc() {
        return GSc;
    }

    public static LogAndExitController getLEc() {
        return LEc;
    }

    public static SearchControls getSc() {
        return Sc;
    }

    public static TeamController getTc() {
        return Tc;
    }

    public static UserCLI getCli() {
        return cli;
    }
}
