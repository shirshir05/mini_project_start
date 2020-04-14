package Busnies_Servic.Service_Layer;

import Busnies_Servic.Business_Layer.TeamManagement.Team;
import Busnies_Servic.Business_Layer.UserManagement.Coach;
import Busnies_Servic.Business_Layer.UserManagement.Fan;
import Busnies_Servic.Business_Layer.UserManagement.Player;
import DB_Layer.logger;

import java.util.Observable;

public class AlertController {

    /**
     *  This function register the fan to alerts of a game he choose.
     */
    public void fanRegisterToGameAlerts(int game_number){
        Observable chosen_game = DataManagement.getGame(game_number);
        chosen_game.addObserver((Fan) DataManagement.getCurrent());
    }

    /**
     * This function register the current user to the page he asked to be registered to.
     * @param arg_user_to_register is the name of the page the user wants to register to
     * @return true if the registeration succeeded
     */
    public boolean fanRegisterToPage(String arg_user_to_register){
        boolean ans = false;
        Busnies_Servic.Business_Layer.UserManagement.Subscription current_user = DataManagement.containSubscription(arg_user_to_register);
        //TODO -add spelling correction here?
        if (current_user instanceof Coach) {
            ((Coach) current_user).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
            ans = true;
        }
        else if (current_user instanceof Player) {
            ((Player) current_user).getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
            ans = true;
        }
        else{
            Team t = DataManagement.findTeam(arg_user_to_register);
            if (t!=null){
                t.getPersonalPage().addObserver((Fan) DataManagement.getCurrent());
                ans = true;
            }
        }
        logger.log("fan_register_to_page, page mane: "+arg_user_to_register+" ,user mane: "+ current_user.getUserName() +" successful: "+ans);
        if (!ans) {
            //TODO - remove print
            System.out.println("Wrong page name");
        }
        return ans;
    }





}
