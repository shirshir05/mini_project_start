package BusniesServic.Business_Layer.UserManagement;
import BusniesServic.Business_Layer.Game.Game;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class Referee extends Subscription implements Observer {

    protected String qualification;
    protected HashSet<Game> referee_games;


    public Referee(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password,email);
        permissions.add_default_referee_permission();
        referee_games = new HashSet<>();
    }

    //**********************************************get & set ************************************************************//
    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }


    /**
     * adds a game to the game-list the referee participates in
     * @param g
     */
    public void addGame(Game g){referee_games.add(g);}

    /**
     * a to-string function to the list of games of the referee
     * @return
     */
    public String gamesListToString(){
        String return_value = "You are participates in the next games: ";
        for (Game g:referee_games){
            return_value+=g.getGameId()+", ";
        }
        return return_value.substring(0,return_value.length()-2);
    }


    /**
     * Add alert to referee
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        String alert = (String)arg;
        if (alert.substring(0,3).equals("The")){
            this.alerts.add((String)arg);
        }
    }
    //**********************************************to string ************************************************************//
    @Override
    public String toString() {

        return "Referee: " + "\n" +
                "name: " + name + "\n" +
                "email: " + email + "\n" +
                "qualification: " + qualification;
    }

}
