package Presentation_Layer;

import java.util.Scanner;

public class UserCLI implements UserInterface {
    @Override
    public String presentAndGetInput(String presentToUser) {
        String ans = null;
        Scanner in = new Scanner(System.in);

        ans = in.nextLine();
        System.out.println("You entered string "+ans);

        int a = in.nextInt();
        System.out.println("You entered integer "+a);

        float b = in.nextFloat();
        System.out.println("You entered float "+b);

        return null;
    }

    @Override
    public void presentOnly(String presentToUser1, String presentToUser2) {

    }
}
