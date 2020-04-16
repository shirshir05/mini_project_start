import Presentation_Layer.Spelling;
import Presentation_Layer.StartSystem;

public class Main {

    public static void main(String[] args) {

        StartSystem sys = new StartSystem();
        sys.startFromDB();
        //sys.ResetToFactory();
    }
}
