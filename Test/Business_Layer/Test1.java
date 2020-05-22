package Business_Layer;

import Business_Layer.Business_Control.GameSettingsController;
import Business_Layer.Business_Control.LogAndExitController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test1 {

    /**
     * Test - UC game management
     */
    @Test
    public void gameManagementTest() {
        LogAndExitController controller = new LogAndExitController();
        controller.Registration("SystemAdministrator","12345","SystemAdministrator","shirshir05@walla.co.il");
        controller.Registration("UnionRepresentative","12345","UnionRepresentative","shirshir05@walla.co.il");
        controller.Login("SystemAdministrator","12345");
        //define league
        GameSettingsController GameController = new GameSettingsController();
        assertEquals(GameController.defineLeague("League A").getDescription(),"The league was successfully added to the system.");
        assertEquals(GameController.defineLeague("League B").getDescription(),"The league was successfully added to the system.");

        //define season to league
    }

}
