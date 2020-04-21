package BusinessService.Business_Layer.UserManagement;


import BusinessService.Business_Layer.Trace.PlayerPersonalPage;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

public class Player extends Subscription implements Observer {

    protected PlayerPersonalPage PersonalPage;
    protected String position;
    protected LocalDate birthday;

    /**
     * @param arg_user_name
     * @param arg_password
     * @param email
     */
    public Player(String arg_user_name, String arg_password,String email) {
        super(arg_user_name, arg_password,email);
        PersonalPage=new PlayerPersonalPage(arg_user_name);
        permissions.add_default_player_or_coach_permission();
    }

    //**********************************************get & set ************************************************************//

    /**
     * @return
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }


    /**
     * @return
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public PlayerPersonalPage getPersonalPage() {
        return PersonalPage;
    }

    /**
     * @param personalPage
     */
    public void setPersonalPage(PlayerPersonalPage personalPage) {
        PersonalPage = personalPage;

    }

    /**
     * @return
     */


    @Override
    public void update(Observable o, Object arg) {
        this.alerts.add((String)arg);
    }


    @Override
    public String toString() {

        return "Player: " + "\n" +
                "name: " + name + "\n" +
                "email: " + email + "\n" +
                "position: " + position + "\n" +
                "birthday: " + birthday;
    }

}
