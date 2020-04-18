package BusniesServic.Service_Layer;

import static org.junit.Assert.*;

import BusniesServic.Business_Layer.TeamManagement.Team;
import BusniesServic.Business_Layer.UserManagement.TeamOwner;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Business_Layer.UserManagement.SubscriptionFactory;
import BusniesServic.Enum.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

public class LogAndExitControllerTest {

    private LogAndExitController controller;
    private String name;
    private String password;
    private String role;
    private ActionStatus actionStatus;
    private String email;
    private SubscriptionFactory factory;
        @Test
            public void registrationTest() {
            controller = new LogAndExitController();
            actionStatus = new ActionStatus(true, "Subscription successfully added!");
            assertEquals(actionStatus, controller.Registration("shir", "12345","Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false, "Please select another username because this username exists in the system.");
            assertEquals(actionStatus, controller.Registration("shir", "11111", "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false, "The input is empty.");
            assertEquals(actionStatus, controller.Registration("shir", null, "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false, "The input is empty.");
            assertEquals(actionStatus, controller.Registration("", "nul2l", "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false, "The input is empty.");
            assertEquals(actionStatus, controller.Registration("shir", "", "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false,"The password must contain at least 5 digits.");
            assertEquals(actionStatus, controller.Registration("shir","1234", "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus( false,"The password must contain at least 5 digits.");
            assertEquals(actionStatus, controller.Registration("shir", "1", "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false, "The password must contain at least 5 digits.");
            assertEquals(actionStatus, controller.Registration("shir", "1rr", "Coach","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus( false,"The role does not exist in the system.");
            assertEquals(actionStatus, controller.Registration("shir", "12345", "Teacher","shir0@post.bgu.ac.il"));

            actionStatus = new ActionStatus(false, "Invalid email, please enter a valid email.");
            assertEquals(actionStatus, controller.Registration("shir", "12345", "Coach","shirpostbguacil"));
        }

        @Test
        public void loginTest() {

            this.name = "shir";
            this.password = "12345";
            this.role = "Coach";
            email = "shir0@post.bgu.ac.il";
            controller = new LogAndExitController();

            //check Successfully Registration
            actionStatus = new ActionStatus(true, "Subscription successfully added!");
            assertEquals(actionStatus, controller.Registration("shir", password,role,email));

            //check login when another subscription is connected
            DataManagement.setCurrent(DataManagement.containSubscription(name));
            actionStatus = new ActionStatus(false, "Another subscription is connected to the system.");
            assertEquals(actionStatus, controller.Login(name, password));
            DataManagement.setCurrent(null);

            //check login when password does not match
            this.password = "12346";
            actionStatus = new ActionStatus(false, "The password is incorrect.");
            assertEquals(actionStatus, controller.Login(name, password));
            this.password = "12345";

            //check login when there is no such a name."
            this.name = "other name";
            actionStatus = new ActionStatus(false, "The user " + name + " does not exist in the system.");
            assertEquals(actionStatus, controller.Login(name, password));
            this.name = "shir";

            //check login successful
            actionStatus = new ActionStatus(true, "Login successful.");
            assertEquals(actionStatus, controller.Login(name, password));
        }

        @Test
        public void exitTest() {
            factory = new SubscriptionFactory();
            controller = new LogAndExitController();
            this.name = "matan";
            this.password ="12345";
            this.role = "Coach";

            //Register and login user manually
            DataManagement.setCurrent(factory.Create(name,password, Role.Coach,email));
            DataManagement.setSubscription(DataManagement.getCurrent());

            //check exit wrong user name
            this.name = "mata";
            actionStatus = new ActionStatus(false, "One of the details you entered is incorrect.");
            assertEquals(actionStatus, controller.Exit(name, password));
            this.name = "matan";

            //check exit wrong password
            this.password = "1s2345";
            actionStatus = new ActionStatus(false, "One of the details you entered is incorrect.");
            assertEquals(actionStatus, controller.Exit(name, password));
            this.password = "12345";

            //check exit Successfully
            actionStatus = new ActionStatus(true, "Successfully disconnected from the system.");
            assertEquals(actionStatus, controller.Exit(name, password));

            //check exit no current login user
            DataManagement.setCurrent(null);
            actionStatus = new ActionStatus(false, "One of the details you entered is incorrect.");
            assertEquals(actionStatus, controller.Exit(name, password));
        }

        @Test
    public void RemoveSubscriptionTest() {

            factory = new SubscriptionFactory();
            controller = new LogAndExitController();
            this.name = "matan";
            this.password = "12345";
            this.role = "TeamOwner";
            email = "shir0@post.bgu.ac.il";

            //check Successfully Registration
            actionStatus = new ActionStatus(true, "Subscription successfully added!");
            assertEquals(actionStatus, controller.Registration(name, password,role,email));

            Team team2 = new Team("Real Madrid", "Bernabeo");
            DataManagement.addToListTeam(team2);
            team2.EditTeamOwner((TeamOwner) DataManagement.getSubscription(name),1);
            actionStatus = new ActionStatus(false, "The system constraints do not allow this subscription to be deleted.");
            assertEquals(actionStatus, controller.RemoveSubscription(name));
            team2.EditTeamOwner((TeamOwner) DataManagement.getSubscription(name),0);

            name = "omer";
            actionStatus = new ActionStatus(false, "There is no subscription with this username in the system.");
            assertEquals(actionStatus, controller.RemoveSubscription(name));
            name = "matan";

            actionStatus = new ActionStatus(false, "You are not authorized to perform this action.");
            assertEquals(actionStatus, controller.RemoveSubscription(name));

            this.role = "SystemAdministrator";
            this.name = "ran";
            this.password = "12345";
            email = "shir0@post.bgu.ac.il";

            //check Successfully Registration
            actionStatus = new ActionStatus(true, "Subscription successfully added!");
            assertEquals(actionStatus, controller.Registration(name, password,role,email));

//            this.name = "matan";
//            DataManagement.setCurrent(DataManagement.getSubscription("ran"));
//            actionStatus = new ActionStatus(true, "the transaction completed successfully.");
//            assertEquals(actionStatus, controller.RemoveSubscription(name));
        }


    }




