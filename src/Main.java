import Presentation_Layer.Spelling;
import Presentation_Layer.StartSystem;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        //StartSystem sys = new StartSystem();
        //sys.startFromDB();
        //sys.ResetToFactory();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year  = localDate.getYear();
        System.out.println(year);
    }
}
