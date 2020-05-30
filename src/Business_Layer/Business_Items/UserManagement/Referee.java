package Business_Layer.Business_Items.UserManagement;
import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Items.Game.Game;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class Referee extends Subscription implements Observer, java.io.Serializable {

    protected String qualification;
    protected HashSet<Integer> referee_games;


    public Referee(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password,email);
        permissions.add_default_referee_permission();
        referee_games = new HashSet<>();
    }

    @Override
    public String getRole() {
        return "Referee";
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
    public void addGame(Game g){
        referee_games.add(g.getGameId());
    }

    /**
     * a to-string function to the list of games of the referee
     * @return
     */
    public String gamesListToString(){
        String return_value = "";
        for (Integer g:referee_games){
            return_value += g +", ";
        }
        if(return_value.equals("")){
            return return_value;
        }
        return return_value.substring(0,return_value.length()-2);
    }

    public void setGamesList(String list){
        String[] splitOne = list.split(": ");
        if(splitOne.length>1) {
            String[] splitTwo = splitOne[1].split(", ");
            for (String g : splitTwo) {
                referee_games.add(Integer.parseInt(g));
            }
        }
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
