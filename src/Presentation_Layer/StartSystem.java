package Presentation_Layer;

import BusniesServic.Business_Layer.BudgetManagement.BudgetRegulations;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.*;
import DB_Layer.myFirstDB;

import java.io.File;

public class StartSystem {

    public static AlertController Ac = new AlertController();
    public static BudgetController Bc = new BudgetController();
    public static EditAndShowUserDetails ESUDc = new EditAndShowUserDetails();
    public static GameSettingsController GSc = new GameSettingsController();
    public static LogAndExitController LEc = new LogAndExitController();
    public static SearchLogger Sc = new SearchLogger();
    public static TeamController Tc = new TeamController();
    public static UserCLI cli = new UserCLI();

    public static void cleanSystem(){
        //clean old data in system
        DataManagement.cleanAllData();
        BudgetRegulations.resetRegulationsToDefault();
        try {
            File f = new File("lib/spellingDict.txt");
            if (f.exists()) {
                f.delete();
            }
        }catch (Exception e){
            System.err.println("ERROR: function cleanSystem while creating new spellingDict File");
        }
    }

    public static void ResetToFactory(){
        cleanSystem();

        //create general Guest user
        ActionStatus str1 = LEc.Registration("Guest", "123456", "Guest","Guestmail@mail.com");
        cli.presentOnly(str1.getDescription());

        //create first SystemAdministrator user
        //TODO - change this should be in userDisplay class.
        ActionStatus str2 = new ActionStatus(false,"");
        String name = "";
        String password = "";
        while(!str2.isActionSuccessful()) {
            name = cli.presentAndGetString("insert admin's User name");
            password = cli.presentAndGetString("insert admin's password");
            String email = cli.presentAndGetString("insert admin's email");
            str2 = LEc.Registration(name, password, "SystemAdministrator", email);
            cli.presentOnly(str2.getDescription());
        }

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
                    break;
                case 1:
                    //guest menu;
                    LEc.Login("Guest", "123456");
                    chosen = true;
                    break;
                default:
                    cli.presentOnly("invalid choice.");
                    break;
            }
        }
        cli.presentOnly("thank you and goodbye");
        //todo - send to correct user presentation to show user options menu;
    }

    public void startFromDB(){
        DataManagement.cleanAllData();
        myFirstDB db = new myFirstDB();
        String ans = "";
        ans += db.loadUsersInfo().getDescription() +"\n";
        ans += db.loadTeamInfo().getDescription() +"\n";
        ans += db.loadGameInfo().getDescription() +"\n";
        ans += db.loadLeagueInfo().getDescription();
        cli.presentOnly(ans);
        DataManagement.setCurrent(null);
        cli.presentOnly(LEc.Login("Guest", "123456").getDescription());
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

    public static SearchLogger getSc() {
        return Sc;
    }

    public static TeamController getTc() {
        return Tc;
    }

    public static UserCLI getCli() {
        return cli;
    }
}
