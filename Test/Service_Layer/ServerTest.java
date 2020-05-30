package Service_Layer;

import Business_Layer.Business_Control.LogAndExitController;
import Business_Layer.Enum.ActionStatus;
import org.junit.Test;

import static org.junit.Assert.*;
public class ServerTest {




    @Test
    public void testPostRegistration() {
        Server server = new Server();
        LogAndExitController logAndExitController = new LogAndExitController();
        String[] strings = {"matanshu", "123456","Player","noy@gmail.com"};
        ActionStatus AC = new ActionStatus(true, "Subscription successfully added!");
        //assertEquals(AC ,server.postMethod("registration",strings));
    }
}