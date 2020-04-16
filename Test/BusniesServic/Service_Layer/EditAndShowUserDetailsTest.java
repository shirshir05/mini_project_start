package BusniesServic.Service_Layer;

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
        LogAndExitController lg = new LogAndExitController();
        lg.Registration("Michal","12345","Fan","michal@gmail.com");
        lg.Registration("Raz","12345","Fan","raz@gmail.com");
        lg.Registration("Ortal","12345","Fan","ortal@gmail.com");
    }


    /**
     * Test - ESD1
     */
    @RunWith(Parameterized.class)
    public static class watchPersonalDetils{
        String userName;
        boolean result;
        static EditAndShowUserDetails controller = new EditAndShowUserDetails();

        /**
         * 0. user doesn't exist
         * 1. user exists, no the correct current
         * 2. correct current
         */
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Shir",false}, {"Raz",false}, {"Ortal",true}
            });
        }
        public watchPersonalDetils(String userName, boolean result) {
            this.userName = userName;
            this.result = result;
        }

        @Test
        public void watchPersonalDetilsTest() {
            setSubscriptions();
            assertEquals(controller.watchPersonalDetils(userName).isActionSuccessful(),result);
        }

    }//watchPersonalDetils


    /**
     * Test - ESD2
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionName{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editSubscriptionName() {
            //parameter
        }
        @Test
        public void editSubscriptionNameTest() {

        }

    }//editSubscriptionName

    /**
     * Test - ESD3
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionEmail{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editSubscriptionEmail() {
            //parameter
        }
        @Test
        public void editSubscriptionEmailTest() {

        }

    }//editSubscriptionEmail

    /**
     * Test - ESD4
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionUserName{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editSubscriptionUserName() {
            //parameter
        }
        @Test
        public void editSubscriptionUserNameTest() {

        }

    }//editSubscriptionUserName

    /**
     * Test - ESD5
     */
    @RunWith(Parameterized.class)
    public static class editSubscriptionPassword{
        //parameter


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{


            });
        }
        public editSubscriptionPassword() {
            //parameter
        }
        @Test
        public void editSubscriptionPasswordTest() {

        }

    }//editSubscriptionPassword


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