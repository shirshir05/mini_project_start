package BusniesServic.Service_Layer;

import BusniesServic.Business_Layer.Game.ScoreTable;
import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Enum.PermissionAction;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class EditAndShowUserDetailsTest {

    static boolean moreThanOneTimeForSub = false;
    static boolean moreThanOneTimeForPersonalPages = false;
    static boolean moreThanOneTimeForTeam = false;
    static boolean moreThanOneTimeForReferee = false;

    //region private
    private static void setSubscriptions(){
        if(moreThanOneTimeForSub)
            return;
        DataManagement.cleanAllData();
        LogAndExitController lg = new LogAndExitController();
        lg.Registration("Michal","12345","Fan","michal@gmail.com");
        DataManagement.getCurrent().getPermissions().add_permissions(PermissionAction.personal_page);
        lg.Registration("Raz","12345","Fan","raz@gmail.com");
        lg.Registration("Ortal","12345","Fan","ortal@gmail.com");
        moreThanOneTimeForSub = true;
    }

    private static void registerPlayerAndCoach() {
        if(moreThanOneTimeForPersonalPages)
            return;
        LogAndExitController lg = new LogAndExitController();
        lg.Registration("player","12345","Player","player@gmail.com");
        lg.Registration("coach","12345","Coach","coach@gmail.com");
        lg.Exit("coach","12345");
        lg.Login("Ortal","12345");
        moreThanOneTimeForPersonalPages = true;
    }

    private static void registerReferee() {
        if(moreThanOneTimeForReferee)
            return;
        LogAndExitController lg = new LogAndExitController();
        lg.Registration("referee","12345","Referee","referee@gmail.com");
        lg.Exit("referee","12345");
        lg.Login("Ortal","12345");
        moreThanOneTimeForReferee = true;
    }

    private static void registerTeam() {
        if(moreThanOneTimeForTeam)
            return;
        LogAndExitController lg = new LogAndExitController();
        lg.Registration("teamOwner","12345","TeamOwner","teamOwner@gmail.com");
        TeamController tc = new TeamController();
        tc.CreateTeam("team","beersheva");
        Team t = DataManagement.findTeam("team");
        if(t!=null) t.getPersonalPage().addPermissionToEdit("Ortal");
        lg.Exit("teamOwner","12345");
        lg.Login("Ortal","12345");
        moreThanOneTimeForTeam = true;
    }

    private static void loginUser(String userName) {
        LogAndExitController lg = new LogAndExitController();
        if (DataManagement.getCurrent() != null && !DataManagement.getCurrent().getUserName().equals(userName))
            lg.Exit(DataManagement.getCurrent().getUserName(), "12345");
        lg.Login(userName, "12345");
    }

    //endregion

    //region watchPersonalDetails

    /**
     * Test - ESD1
     */
    @RunWith(Parameterized.class)
    public static class watchPersonalDetails{
        String userName;
        boolean result;
        static EditAndShowUserDetails controller = new EditAndShowUserDetails();

        /**
         * 0. user doesn't exist
         * 1. user exists, not the correct current
         * 2. correct current
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir",false}, {"Raz",false}, {"Ortal",true}
            });
        }

        public watchPersonalDetails(String userName, boolean result) {
            this.userName = userName;
            this.result = result;
        }

        @Test
        public void watchPersonalDetailsTest() {
            setSubscriptions();
            assertEquals(controller.watchPersonalDetails(userName).isActionSuccessful(),result);
        }

    }

    //endregion

    //region editSubscriptionName

    /**
     * Test - ESD2
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionNameAndUserName{
        String name;
        String newName;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. user doesn't exist
         * 1. user exists, not the correct current
         * 2. correct current, empty change
         * 3. correct current, null change
         * 4. correct current, correct change
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir","",false}, {"Raz","",false},{"Ortal","",false},{"Ortal",null,false} ,{"Ortal","OrtalP",true}
            });
        }

        public editSubscriptionNameAndUserName(String name, String newName, boolean result) {
            this.name = name;
            this.newName = newName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editSubscriptionNameTest() {
            setSubscriptions();
            assertEquals(controller.editSubscriptionName(name,newName).isActionSuccessful(),result);
        }

        @Test
        public void editSubscriptionUserNameTest() {
            setSubscriptions();
            assertEquals(controller.editSubscriptionUserName(name,newName).isActionSuccessful(),result);
            moreThanOneTimeForSub = false;
        }

    }

    //endregion

    //region Email

    /**
     * Test - ESD3
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionEmail{
        String userName;
        String email;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. user doesn't exist
         * 1. user exists, not the correct current
         * 2. correct current, empty change
         * 3. correct current, null change
         * 4. correct current, correct change
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir","",false}, {"Raz","",false},{"Ortal","",false},{"Ortal",null,false} ,{"Ortal","ortal@gmail.com",true}
            });
        }

        public editSubscriptionEmail(String userName, String email, boolean result) {
            this.userName = userName;
            this.email = email;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editSubscriptionEmailTest() {
            moreThanOneTimeForSub = false;
            setSubscriptions();
            assertEquals(controller.editSubscriptionEmail(userName,email).isActionSuccessful(),result);
        }

    }

    //endregion

    //region Password

    /**
     * Test - ESD5
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionPassword{
        String userName;
        String currPassword;
        String newPassword;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. user doesn't exist
         * 1. user exists, not the correct current
         * 2. correct current, empty change
         * 3. correct current, null change
         * 4. correct current, incorrect password
         * 5. correct current, correct change
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir","12345","12345",false}, {"Raz","12345","12345",false},{"Ortal","12345","",false}, {"Ortal","12345", null ,false} ,{"Ortal","10345","12345",false},{"Ortal","12345","12345",true}
            });
        }

        public editSubscriptionPassword(String userName,String currPassword, String newPassword, boolean result) {
            this.userName = userName;
            this.currPassword = currPassword;
            this.newPassword = newPassword;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editSubscriptionPasswordTest() {
            moreThanOneTimeForSub = false;
            setSubscriptions();
            assertEquals(controller.editSubscriptionPassword(userName,currPassword,newPassword).isActionSuccessful(),result);
        }

    }//editSubscriptionPassword

    //endregion

    //region coach qualification and role

    /**
     * Test - ESD6, ESD7
     */
    @RunWith(Parameterized.class)
    public static class editCoachQualificationAndRole{
        String userName;
        String qualification;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. sub doesn't exist
         * 1. exists but not a coach
         * 2. a coach, empty value
         * 3. a coach, null value
         * 4. a coach, valid value
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir","",false}, {"Ortal","",false}, {"coach","",false},{"coach",null,false}, {"coach","valid",true}
            });
        }

        public editCoachQualificationAndRole(String userName, String qualification, boolean result) {
            this.userName = userName;
            this.qualification = qualification;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editCoachQualificationTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            registerTeam();
            loginUser(userName);
            assertEquals(controller.editCoachQualification(userName,qualification).isActionSuccessful(),result);
        }

        @Test
        public void editCoachRoleTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            registerTeam();
            loginUser(userName);
            assertEquals(controller.editCoachRoleInTeam(userName,qualification).isActionSuccessful(),result);
        }

    }//editCoachQualification

    //endregion

    //region editRefereeQualification

    /**
     * Test - ESD8
     */
    @RunWith(Parameterized.class)
    public static class editRefereeQualification{
        String userName;
        String qualification;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. sub doesn't exist
         * 1. exists but not a referee
         * 2. a coach, empty value
         * 3. a coach, null value
         * 4. a coach, valid value
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir","",false}, {"Ortal","",false}, {"referee","",false},{"referee",null,false}, {"referee","valid",true}
            });
        }

        public editRefereeQualification(String userName, String qualification, boolean result) {
            this.userName = userName;
            this.qualification = qualification;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editRefereeQualificationTest() {
            setSubscriptions();
            registerReferee();
            loginUser(userName);
            assertEquals(controller.editRefereeQualification(userName,qualification).isActionSuccessful(),result);

        }

    }//editRefereeQualification

    //endregion

    //region editPlayerPosition

    /**
     * Test - ESD9
     */
    @RunWith(Parameterized.class)
    public static class editPlayerPosition{
        String userName;
        String qualification;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. sub doesn't exist
         * 1. exists but not a referee
         * 2. a coach, empty value
         * 3. a coach, null value
         * 4. a coach, valid value
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir","",false}, {"Ortal","",false}, {"player","",false},{"player",null,false}, {"player","valid",true}
            });
        }

        public editPlayerPosition(String userName, String qualification, boolean result) {
            this.userName = userName;
            this.qualification = qualification;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editPlayerPositionTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            loginUser(userName);
            assertEquals(controller.editPlayerPosition(userName,qualification).isActionSuccessful(),result);
        }

    }//editPlayerPosition

    //endregion

    //region player birth date - not needed
//    /**
//     * Test - ESD10
//     */
//    @RunWith(Parameterized.class)
//    public static class editPlayerDate{
//        String player;
//        boolean result;
//        Date dateOfBirth;
//        EditAndShowUserDetails controller;
//
//        /**
//         * 0. sub doesn't exist
//         * 1. exists but not a player
//         * 2. a player, null value
//         * 3. a player, valid value
//         */
//        @Parameterized.Parameters
//        public static Collection<Object[]> data() {
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.DATE, 9);
//            calendar.set(Calendar.MONTH, 12);
//            calendar.set(Calendar.YEAR, 1993);
//
//            return Arrays.asList(new Object[][]{
//                    {"Shir",null,false},{"Raz",null, false}, {calendar.getTime(), calendar.getTime()}, {null, null}
//            });
//        }
//
//        public editPlayerDate(String player, Date dateOfBirth, boolean result) {
//            this.player = player;
//            this.result = result;
//            this.dateOfBirth = dateOfBirth;
//            controller = new EditAndShowUserDetails();
//        }
//
//        @Test
//        public void editPlayerDateTest() {
//            setSubscriptions();
//            registerPlayerAndCoach();
//
//        }
//
//    }//editPlayerDate

    //endregion

    //region add permissions

    /**
     * Test - ESD9
     */
    @RunWith(Parameterized.class)
    public static class addPermissions{
        String userToAddPermissionsTo;
        String editingUserName;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. null user name
         * 1. not in the system
         * 2. in the sys, the user is not allowed to edit
         * 3. a coach, the user is allowed to edit
         * 4. a player, the user is allowed to edit
         * 5. not a coach nor a player
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {null,"Ortal",false}, //0
                    {"Shir","Ortal",false}, //1
                    {"Ortal","Ortal",false}, //2
                    {"coach","player",true}, //3
                    {"player","coach",true}, //4
                    {"Ortal","Michal",false} //5
            });
        }

        public addPermissions(String userToAddPermissionsTo, String editingUserName, boolean result) {
            this.userToAddPermissionsTo = userToAddPermissionsTo;
            this.editingUserName = editingUserName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void addPermissionsTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            registerTeam();
            loginUser(editingUserName);
            assertEquals(controller.addPermissionsToCurrentUserPersonalPage(userToAddPermissionsTo).isActionSuccessful(),result);
        }

    }//addPermissions

    //endregion

    //region editCoachPersonalPage

    /**
     * Test - ??
     */
    @RunWith(Parameterized.class)
    public static class editCoachPersonalPage{
        String coachName;
        Object[] values;
        String editingUserName;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. not in the system
         * 1. in the sys, not a coach
         * 2. a coach, the user is not allowed to edit
         * 3. a coach, the user is allowed to edit, values is null
         * 4. a coach, the user is allowed to edit, values.length=1
         * 5. a coach, the user is allowed to edit, values values are null (not instance of...)
         * 6. a coach, the user is allowed to edit, values correct
         * 7. a coach, the user is allowed to edit, values are not parsed into int or double
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir",null,"Ortal",false}, //0
                    {"Ortal",null,"Ortal",false}, //1
                    {"coach",null,"Ortal",false}, //2
                    {"coach",null,"coach",false}, //3
                    {"coach",new Object[]{null},"coach",false}, //4
                    {"coach",new Object[]{null,null,null,null,null},"coach",true}, //5
                    {"coach",new Object[]{new Date(),"null","10","10","null"},"coach",true}, //6
                    {"coach",new Object[]{new Date(),"null","null","null","null"},"coach",true}, //7
            });
        }

        public editCoachPersonalPage(String coachName, Object[] values, String editingUserName, boolean result) {
            this.coachName = coachName;
            this.values = values;
            this.editingUserName = editingUserName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editCoachPersonalPageTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            registerTeam();
            loginUser(editingUserName);
            assertEquals(controller.editCoachPersonalPage(coachName,values).isActionSuccessful(),result);
        }

    }//editCoachPersonalPage

    //endregion

    //region editPlayerPersonalPage

    /**
     * Test - ESD11
     */
    @RunWith(Parameterized.class)
    public static class editPlayerPersonalPage{
        String playerName;
        Object[] values;
        String editingUserName;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. not in the system
         * 1. in the sys, not a player
         * 2. a player, the user is not allowed to edit
         * 3. a player, the user is allowed to edit, values is null
         * 4. a player, the user is allowed to edit, values.length=1
         * 5. a player, the user is allowed to edit, values values are null (not instance of...)
         * 6. a player, the user is allowed to edit, values correct
         * 7. a player, the user is allowed to edit, values are not parsed to int or double
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir",null,"Ortal",false}, //0
                    {"Ortal",null,"Ortal",false}, //1
                    {"player",null,"Ortal",false}, //2
                    {"player",null,"player",false}, //3
                    {"player",new Object[]{null},"player",false}, //4
                    {"player",new Object[]{null,null,null,null,null,null,null,null},"player",true}, //5
                    {"player",new Object[]{new Date(),"null","null","10","10","null","10","null"},"player",true}, //6
                    {"player",new Object[]{new Date(),"null","null","null","null","null","null","null"},"player",true}, //7
            });
        }

        public editPlayerPersonalPage(String playerName, Object[] values, String editingUserName, boolean result) {
            this.playerName = playerName;
            this.values = values;
            this.editingUserName = editingUserName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editPlayerPersonalPageTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            registerTeam();
            loginUser(editingUserName);
            assertEquals(controller.editPlayerPersonalPage(playerName,values).isActionSuccessful(),result);
        }

    }//editPlayerPersonalPage

    //endregion

    //region editTeamPersonalPage

    /**
     * Test - esd12
     */
    @RunWith(Parameterized.class)
    public static class editTeamPersonalPage{
        String teamName;
        Object[] values;
        String editingUserName;
        boolean result;
        EditAndShowUserDetails controller;

        /**
         * 0. not in the system
         * 1. a team, the user is not allowed to edit
         * 2. a team, the user is allowed to edit, values is null
         * 3. a team, the user is allowed to edit, values.length=1
         * 4. a team, the user is allowed to edit, values values are null (not instance of...)
         * 5. a team, the user is allowed to edit, values correct
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir",null,"Raz",false}, //0
                    {"team",null,"Raz",false}, //1
                    {"team",null,"Ortal",false}, //3
                    {"team",new Object[]{null},"Ortal",false}, //4
                    {"team",new Object[]{null,null,null,null,null},"Ortal",true}, //5
                    {"team",new Object[]{new Date(),"null","null",new ScoreTable(null),"null"},"Ortal",true}, //6
            });
        }

        public editTeamPersonalPage(String teamName, Object[] values, String editingUserName, boolean result) {
            this.teamName = teamName;
            this.values = values;
            this.editingUserName = editingUserName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void editTeamPersonalPageTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            registerTeam();
            loginUser(editingUserName);
            assertEquals(controller.editTeamPersonalPage(teamName,values).isActionSuccessful(),result);
        }

    }//editTeamPersonalPage

    //endregion

    //region Get personal page - coach and player

    /**
     * Test - ESD13
     */
    @RunWith(Parameterized.class)
    public static class getPersonalPage{
        String userName;
        EditAndShowUserDetails controller;
        boolean result;

        /**
         * 0. subscription doesn't exist
         * 1. subscription is not coach or player
         * 2. subscription is a player
         * 3. subscription is a coach
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir",false},{"Ortal",false},{"player",true},{"coach",true}
            });
        }

        public getPersonalPage(String userName, boolean result) {
            this.userName = userName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void getPersonalPageTest() {
            setSubscriptions();
            registerPlayerAndCoach();
            assertEquals(controller.getPersonalPageOfCoachOrPlayer(userName) != null, result);
        }

    }

    //endregion

    //region Get personal page - team

    /**
     * Test - ESD14
     */
    @RunWith(Parameterized.class)
    public static class getPersonalPageForTeam{
        String teamName;
        EditAndShowUserDetails controller;
        boolean result;

        /**
         * 0. team doesn't exist
         * 2. team exists
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Ortal",false},{"team",true}
            });
        }

        public getPersonalPageForTeam(String teamName, boolean result) {
            this.teamName = teamName;
            this.result = result;
            controller = new EditAndShowUserDetails();
        }

        @Test
        public void getPersonalPageForTeamTest() {
            setSubscriptions();
            registerTeam();
            assertEquals(controller.getPersonalPageOfTeam(teamName) != null, result);
        }

    }

    //endregion


}