package Busnies_Servic.Service_Layer;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;
@RunWith(Enclosed.class)
public class SearchLoggerTest {



    /**
     * Test - SL1
     */
    public static class showSearchHistory{

        @Test
        public void showSearchHistory() {
            LogAndExitController logAndExit = new LogAndExitController();
            SearchLogger sl = new SearchLogger();
            logAndExit.Registration("SHIR","123456","Fan","shir0@post.bgu.ac.il");
            logAndExit.Login("SHIR","123456");
            assertEquals(sl.showSearchHistory(),"");
            assertEquals(sl.findData("SHIR"),"fan: SHIR\n");
            assertEquals(sl.showSearchHistory(),"SHIR\n");
        }


    }//showSearchHistory



}