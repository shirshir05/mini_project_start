package Service_Layer;

import Business_Layer.Business_Items.BudgetManagement.BudgetRegulations;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Enum.Configurations;
import Business_Layer.Business_Control.*;
import DB_Layer.databaseController;
import DB_Layer.stateTaxSystem;
import DB_Layer.unionFinanceSystem;


import java.io.File;
import java.io.FileWriter;
import java.security.acl.LastOwnerException;
import java.time.Duration;
import java.time.LocalDateTime;

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
            File f = new File("./lib/spellingDict.txt");
            if (f.exists()) {
                f.delete();
            }
            File dir = new File("./logs");
            for(File file: dir.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            FileWriter config = new FileWriter(new File("./Resources/config"),false);
            config.write("NumberOfGames=1\nNumberOfComplaint=1");
            config.flush();
            config.close();


        }catch (Exception e){
            System.err.println("ERROR: function cleanSystem while cleaning files");
        }
    }

    public static ActionStatus ResetToFactory(String tax,String finance){
        cleanSystem();

        //reset db and check connection
        //db.resetDateBaseDel();
        //LocalDateTime index = LocalDateTime.now();
        //long number = 0;
        // todo 5 minutes !!!  - MUST
        //while(number < 60*5){
        //while(number < 60*2){
        //    number= Duration.between(index,LocalDateTime.now()).getSeconds();
        // }
        //System.out.println("hiiiiiiiiiiiiiiiiii ");

        db.resetDateBaseStart();

        //whit to create DB
        LocalDateTime index = LocalDateTime.now();
        long number = 0;
        while(number < 60*2){
            number= Duration.between(index,LocalDateTime.now()).getSeconds();
         }

        Boolean dbStart = db.checkConn();

        LocalDateTime index1 = LocalDateTime.now();
        long number1 = 0;
        while(number1 < 60*2){
            number1= Duration.between(index1,LocalDateTime.now()).getSeconds();
        }
        //create first SystemAdministrator user
        ActionStatus userStart = LEc.Registration("admin", "admin", "SystemAdministrator", "admin@admin.com");

        //start connection to external systems.
        stateTaxSystem stT = new stateTaxSystem(tax);
        unionFinanceSystem unF = new unionFinanceSystem(finance);
        Boolean external = unF.checkConnection() && stT.checkConnection();

        String resultS = "Database upload successfully: "+ dbStart+" ,admin default user created: "+ userStart.isActionSuccessful() +" ,external connections made successfully: "+ external;
        Boolean resultB = dbStart && userStart.isActionSuccessful() && external;
        return new ActionStatus(resultB ,resultS);
    }

    public static ActionStatus startFromDB(){
        DataManagement.cleanAllData();
        //Configurations.setPropValues("NumberOfGames",1);
        DataManagement.setCurrent(null);
        Boolean dbStart = db.sqlConn.checkExistingDB();
        String ans = "";
        if(dbStart){
            ans = "The system is up and running";
        }else {
            ans = "Connectivity problem, Please try again in a few minutes or contact your system administrator";
        }
        return new ActionStatus(dbStart,ans);
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
