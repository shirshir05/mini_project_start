package BusniesServic.Service_Layer;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class EditAndShowUserDetailsTest {

    static boolean moreThanOneTime = false;

    private static void setSubscriptions(){
        if(moreThanOneTime)
            return;
        DataManagement.cleanAllData();
        LogAndExitController lg = new LogAndExitController();
        lg.Registration("Michal","12345","Fan","michal@gmail.com");
        lg.Registration("Raz","12345","Fan","raz@gmail.com");
        lg.Registration("Ortal","12345","Fan","ortal@gmail.com");
    }

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
            moreThanOneTime = false;
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
            setSubscriptions();
            assertEquals(controller.editSubscriptionPassword(userName,currPassword,newPassword).isActionSuccessful(),result);
        }

    }//editSubscriptionPassword

    //endregion


    /**
     * Test - ESD6
     */
    @RunWith(Parameterized.class)
    public static class editCoachQualification{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editCoachQualification() {
            //parameter
        }
        @Test
        public void editCoachQualificationTest() {

        }

    }//editCoachQualification


    /**
     * Test - ESD7
     */
    @RunWith(Parameterized.class)
    public static class editCoachRoleInTeam{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editCoachRoleInTeam() {
            //parameter
        }
        @Test
        public void editCoachRoleInTeamTest() {

        }

    }//editCoachRoleInTeam


    /**
     * Test - ESD8
     */
    @RunWith(Parameterized.class)
    public static class editRefereeQualification{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editRefereeQualification() {
            //parameter
        }
        @Test
        public void editRefereeQualificationTest() {

        }

    }//editRefereeQualification

    /**
     * Test - ESD9
     */
    @RunWith(Parameterized.class)
    public static class editPlayerPosition{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editPlayerPosition() {
            //parameter
        }
        @Test
        public void editPlayerPositionTest() {

        }

    }//editPlayerPosition



    /**
     * Test - ESD10
     */
    @RunWith(Parameterized.class)
    public static class editPlayerDate{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editPlayerDate() {
            //parameter
        }
        @Test
        public void editPlayerDateTest() {

        }

    }//editPlayerDate



    /**
     * Test - ESD11
     */
    @RunWith(Parameterized.class)
    public static class editPlayerPersonalPage{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editPlayerPersonalPage() {
            //parameter
        }
        @Test
        public void editPlayerPersonalPageest() {

        }

    }//editPlayerPersonalPage










}