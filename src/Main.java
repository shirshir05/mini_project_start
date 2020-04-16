import Presentation_Layer.Spelling;

public class Main {

    public static void main(String[] args) {
        System.out.println("shir");

        //initialize the system:
        /**
         LogAndExitController lc = new LogAndExitController();
         //    String str = lc.Registration("mainAdmin", "p@$$w0rd", "SystemAdministrator","email");
         //    System.out.println(str + "\n" );
         String str = lc.Registration("mainAdmin", "p@$$w0rd", "SystemAdministrator","email");
         System.out.println(str + "\n" );
         **/

        //Spelling.updateDictionary("wo hard to speok");
        System.out.println("the correct: "+Spelling.getCorrectWord("hella bon") +"!");

    }
}
