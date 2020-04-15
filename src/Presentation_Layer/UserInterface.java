package Presentation_Layer;

public interface UserInterface {

    //present string, get input;
    public String presentAndGetInput(String presentToUser);

    //present error or alert, don't wait for input
    public void presentOnly(String presentToUser1,String presentToUser2);

}
