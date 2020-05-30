package Users.Users_Menu;

import java.util.Scanner;

public class UserCLI implements UserInterface {

    @Override
    public String presentAndGetString(String presentToUser) {

        //print:
        System.out.println(presentToUser);
        //get string
        Scanner in = new Scanner(System.in);
        String ans = in.nextLine();

        return ans;
    }

    @Override
    public int presentAndGetInt(String presentToUser) {
        //print:
        System.out.println(presentToUser);
        //get int
        Scanner in = new Scanner(System.in);
        int ans = in.nextInt();

        return ans;
    }

    @Override
    public double presentAndGetDouble(String presentToUser) {

        //print:
        System.out.println(presentToUser);
        //get double
        Scanner in = new Scanner(System.in);
        double ans = in.nextDouble();

        return ans;
    }

    @Override
    public void presentOnly(String presentToUser) {
        //print:
        System.out.println(presentToUser);
    }
}
