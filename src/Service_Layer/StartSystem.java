package Service_Layer;

import Business_Layer.Business_Items.BudgetManagement.BudgetRegulations;
import Business_Layer.Business_Control.*;
import DB_Layer.databaseController;

import java.io.File;

public class StartSystem {

    public static AlertController Ac = new AlertController();
    public static BudgetController Bc = new BudgetController();
    public static EditAndShowUserDetails ESUDc = new EditAndShowUserDetails();
    public static GameSettingsController GSc = new GameSettingsController();
    public static LogAndExitController LEc = new LogAndExitController();
    public static SearchLogger Sc = new SearchLogger();
    public static TeamController Tc = new TeamController();
    public static databaseController db = new databaseController();

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
        //clean old data in system
        DataManagement.cleanAllData();
        db.resetDateBase();
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

    public void startFromDB(){
        if(!db.dbExist()){
            db.resetDateBase();
        }
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
}
