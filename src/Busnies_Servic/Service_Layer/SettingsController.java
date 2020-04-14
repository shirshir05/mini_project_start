package Busnies_Servic.Service_Layer;

import Busnies_Servic.Business_Layer.Game.League;
import Busnies_Servic.Business_Layer.Game.Season;
import Busnies_Servic.Business_Layer.UserManagement.Referee;
import Busnies_Servic.Business_Layer.UserManagement.Subscription;
import Busnies_Servic.Business_Layer.UserManagement.UnionRepresentative;
import DB_Layer.logger;
import Presentation_Layer.Spelling;

public class SettingsController{


    /**
     * this function let the union rep to define a leauge
     * @param name is the name of the leauge
     * @return true if it was defined
     */
    public boolean defineLeague(String name){
        if (name!=null && DataManagement.getCurrent() instanceof UnionRepresentative) {
            DataManagement.addToListLeague(new League(name));
            Spelling.updateDictionary("league: " + name);
            return true;
        }
        return false;
    }

    /**
     * this function let the union rep to define a season to a leauge
     * @param league_name is the name of the leauge
     * @param year is the season
     * @return true if the season was updated
     */
    public boolean defineSeasonToLeague(String league_name, String year){
        boolean ans = false;
        if (year!=null && league_name!=null && DataManagement.getCurrent() instanceof UnionRepresentative) {
            int intFormatYear= Integer.parseInt(year);
            if (intFormatYear>1900 && intFormatYear<2021){
                DataManagement.findLeague(league_name).addSeason(new Season(year));
                ans = true;
                Spelling.updateDictionary("season: " + league_name);
            }

        }
        logger.log("Settings controller: defineSeasonToLeague, league name: "+ league_name+" ,year: "+year +" ,successful: "+ ans);
        return ans;
    }

    /**
     * This function let the union rep to add a referee to the system
     * int add_or_remove
     * @param referee_user_name is the username of the referee
     * @param referee_password is the password of the referee
     * @param mail is the password of the referee
     * @param add_or_remove is 0 to add and 1 to remove
     * @return true if the operation succeeded
     */
    public boolean addOrDeleteRefereeToSystem(String referee_user_name, String referee_password, String mail, int add_or_remove){
        boolean ans = false;
        if (DataManagement.getCurrent() instanceof UnionRepresentative) {
            if (referee_user_name != null && referee_password != null) {
                Subscription current_referee = DataManagement.containSubscription(referee_user_name);
                if (add_or_remove == 0 && current_referee != null) {
                    Referee current = new Referee(referee_user_name, referee_password, mail);
                    String mail_content= "Hello! you were invited to our system! your username: "+referee_user_name+" and you password: "+referee_password;
                    DataManagement.getCurrent().sendEMail(mail,mail_content);
                    DataManagement.setSubscription(current);
                    ans = true;
                    Spelling.updateDictionary("user: " + referee_user_name);
                } else if (add_or_remove == 1) {
                    if (current_referee != null) {
                        Spelling.updateDictionary("referee: " + referee_user_name);
                        ans =  true;
                    }
                }
            }
        }
        logger.log("Settings controller: addOrDeleteRefereeToSystem, referee name: "+ referee_user_name +" ,add or remove: "+add_or_remove +" ,successful: "+ ans);
        return ans;
    }

    /**
     * This function let the union rep to add a referee to the system
     * @param leauge_name
     * @param referee_user_name
     * @param season_year
     * @return
     */
    public boolean defineRefereeInLeague(String leauge_name, String referee_user_name, String season_year) {
        boolean ans = false;
        League league = DataManagement.findLeague(leauge_name);
        Subscription referee = DataManagement.containSubscription(referee_user_name);
        if (league != null && referee!=null && referee instanceof Referee) {
            Season season = league.getSeason(season_year);
            if (season!=null){
                season.addReferee((Referee)referee);
                ans = true;
            }
        }
        logger.log("Settings controller: defineRefereeInLeauge, leauge name: "+ leauge_name +" ,referee name: "+referee_user_name+" ,season: "+season_year +" ,successful: "+ ans);
        return ans;
    }

}
