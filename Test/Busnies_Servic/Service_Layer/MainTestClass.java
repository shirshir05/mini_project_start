package Busnies_Servic.Service_Layer;

import static org.junit.Assert.*;

import Busnies_Servic.ActionStatus;
import Busnies_Servic.Business_Layer.UserManagement.Subscription;
import Busnies_Servic.Business_Layer.UserManagement.SubscriptionFactory;
import Busnies_Servic.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class MainTestClass {

    @RunWith(Parameterized.class)
    public static class LogAndExitControllerTest {

        SubscriptionFactory factory;
        LogAndExitController controller;
        Subscription Current;
        String name;
        String password;
        String role;
        String expected;
        String email;
        boolean ifRegistationTest;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //Input_test
                    {"shir", "12345","Coach", "Subscription successfully added!"},
                    {"shir", "11111", "Coach","shir0@post.bgu.ac.il", "Please select another username because this username exists in the system."},
                    {null, "12345", "Coach","shir0@post.bgu.ac.il", "The input is empty."},
                    {"shir", null, "Coach","shir0@post.bgu.ac.il", "The input is empty."},
                    {"", "nul2l", "Coach","shir0@post.bgu.ac.il", "The input is empty."},
                    {"shir", "", "Coach","shir0@post.bgu.ac.il", "The input is empty."},
                    {"shir","123456", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", "1", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", "1rr", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", ";", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", "1rr", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", "12345", "Teacher","shir0@post.bgu.ac.il", "The role does not exist in the system."},
                    {"shir", "12345", "Coach","shir0@post.bgu.ac.il", "Another subscription is connected to the system."},
            });
        }

//    public LogAndExitControllerTest(String arg_user_name, String arg_password, String role, String expected) {
//        this.name = arg_user_name;
//        this.password = arg_password;
//        this.role = role;
//        this.expected = expected;
//    }

        public LogAndExitControllerTest(String arg_user_name, String arg_password, String role, String email, String expected) {
            factory = new SubscriptionFactory();
            this.name = arg_user_name;
            this.password = arg_password;
            this.role = role;
            this.expected = expected;
            this.email = email;
        }

        @Before
        public void setUp() throws Exception {

            ifRegistationTest = false;
            controller = new LogAndExitController();
            factory = new SubscriptionFactory();
        }

        @Test
        public void registrationTest() {

            if (expected.equals("The input is empty.") ||
                    expected.equals("The password must contain exactly 5 digits.") ||
                    expected.equals("The password must contain only digits.") ||
                    expected.equals("Subscription successfully added!") ||
                    expected.equals("The role does not exist in the system.")) {

                ifRegistationTest = true;
                assertEquals(expected, controller.Registration(name, password, role,email));

                if (expected.equals("Subscription successfully added!")) {

                    assertEquals("Please select another username because this username exists in the system.", controller.Registration(name, password, role,email));
                }
            }
        }

        @Test
        public void loginTest() {

            this.name = "matan";
            this.password = "12345";
            this.role = "Coach";

            //check Successfully Registration
            assertEquals("Subscription successfully added!", controller.Registration(name, password, role,email)); //maybe need to use stub and change it
            //controller.Subscription.add(factory.Create(name,password, Role.Coach)); //Registare user manualy

            //check login when another subscription is connected
            DataManagement.setCurrent(DataManagement.containSubscription(name));
            assertEquals("Another subscription is connected to the system.", controller.Login(name, password));
            DataManagement.setCurrent(null);

            //check login when password does not match
            this.password = "12346";
            assertEquals("The password does not match the username.", controller.Login(name, password));
            this.password = "12345";

            //check login when there is no such a name."
            this.name = "other name";
            assertEquals("There is no user with such a name.", controller.Login(name, password));
            this.name = "matan";

            //check login successful
            assertEquals("Login successful.", controller.Login(name, password));

        }
        @Test
        public void exitTest() {

            this.name = "matan";
            this.password = "12345";
            this.role = "Coach";

            //Register and login user manually
            DataManagement.setCurrent(factory.Create(name,password, Role.Coach,email));
            DataManagement.setSubscription(DataManagement.getCurrent());

            //check exit wrong user name
            this.name = "mata";
            assertEquals("One of the details you entered is incorrect.", controller.Exit(name, password));
            this.name = "matan";

            //check exit wrong password
            this.password = "1s2345";
            assertEquals("One of the details you entered is incorrect.", controller.Exit(name, password));
            this.password = "12345";

            //check exit Successfully
            assertEquals("Successfully disconnected from the system.", controller.Exit(name, password));

            //check exit no current login user
            DataManagement.setCurrent(null);
            assertEquals("One of the details you entered is incorrect.", controller.Exit(name, password));
        }
    }

    @RunWith(Parameterized.class)
    public static class registrationTest {


        LogAndExitController controller;
        @Before
        public void init(){
            controller = new LogAndExitController();
        }

        String name;
        String password;
        String role;
        String expected;
        String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    //user name = null
                    {null, "12345", "Coach","shir0@post.bgu.ac.il", "The input is empty."},
                    // add user name
                    {"shir", "12345", "Coach","shir0@post.bgu.ac.il", "Subscription successfully added!"},
                    //password null
                    {"shir", null, "Coach","shir0@post.bgu.ac.il", "The input is empty."},

                    {"", "null", "Coach","shir0@post.bgu.ac.il", "The input is empty."},
                    {"shir", "", "Coach","shir0@post.bgu.ac.il", "The input is empty."}, {"shir", "123456", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", "1", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."}, {"shir", "12345", "Coach","shir0@post.bgu.ac.il", "Please select another username because this username exists in the system."},
                    {"shir", "1rr", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."}, {"shir", ";", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."},
                    {"shir", "1rr", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."}, {"shir", "1rr", "Coach","shir0@post.bgu.ac.il", "The password must contain exactly 5 digits."}
            });
        }

        public registrationTest(String name, String password, String role, String email, String expected) {
            this.name = name;
            this.password = password;
            this.role = role;
            this.expected = expected;
            this.email =email;
        }

        @Test
        public void user_name_incorrect() {
            ActionStatus actual = controller.Registration(name, password, role,email);
            assertEquals(expected, actual);
        }
    }





}

