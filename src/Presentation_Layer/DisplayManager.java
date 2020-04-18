package Presentation_Layer;

import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.Users_Menu.*;

public class DisplayManager {

    public static void main(String[] args) {
        StartSystem sys = new StartSystem();
        UserCLI cli = sys.getCli();

        int input = cli.presentAndGetInt("choose options \n1: reset to factory\n2: init from DB\n3: nothing");
        if(input == 1){
            sys.ResetToFactory();
        }
        else if(input == 2){
            sys.startFromDB();
        }else if(input==3){

        }
        GuestUserMenu GM = new GuestUserMenu();
        ActionStatus ac = GM.presentUserMenu();
        Subscription sub = DataManagement.getCurrent();

        if(sub!=null){
            if(sub instanceof Fan){
                //create new fan menu and show its menu
                FanUserMenu FM = new FanUserMenu();

            }
            else if(sub instanceof Player || sub instanceof Coach || sub instanceof TeamManager || sub instanceof TeamOwner){
                //create new ..... menu and show its menu
                //TeamUsersMenu TM = new TeamUsersMenu();
                //TM.presentUserMenu();
            }
            else if(sub instanceof SystemAdministrator){
                //create new .... menu and show its menu
               // SystemAdminUserMenu SM = new SystemAdminUserMenu();
                //SM.presentUserMenu();
            }
            else if(sub instanceof UnionRepresentative){
                //create new .... menu and show its menu
                //UnionUserMenu UM = new UnionUserMenu();
                //UM.presentUserMenu();
            }
            else if(sub instanceof Referee){
                //create new .... menu and show its menu
               // RefereeUserMenu RM = new RefereeUserMenu();
                //RM.presentUserMenu();
            }
        }
    }
}
