package Presentation_Layer;

import BusniesServic.Business_Layer.UserManagement.*;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.Users_Menu.FanUserMenu;
import Presentation_Layer.Users_Menu.GuestUserMenu;

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
        FanUserMenu fanM = new FanUserMenu();
        if(sub!=null){
            if(sub instanceof Fan){
                //create new fan menu and show its menu
                fanM.presentUserMenu();
            }
            else if(sub instanceof Player || sub instanceof Coach || sub instanceof TeamManager || sub instanceof TeamOwner){
                //create new ..... menu and show its menu
            }
            else if(sub instanceof SystemAdministrator){
                //create new .... menu and show its menu
            }
            else if(sub instanceof UnionRepresentative){
                //create new .... menu and show its menu
            }
            else if(sub instanceof Referee){
                //create new .... menu and show its menu
            }
        }
    }
}
