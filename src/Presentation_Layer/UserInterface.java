package Presentation_Layer;

public interface UserInterface {

    //present string, get input string;
    public String presentAndGetString(String presentToUser);

    //present string, get input int;
    public int presentAndGetInt(String presentToUser);

    //present string, get input double;
    public double presentAndGetDouble(String presentToUser);

    //present error or alert, don't wait for input
    public void presentOnly(String presentToUser);

}
