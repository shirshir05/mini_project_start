package Presentation_Layer.Users_Menu;

import BusniesServic.Enum.ActionStatus;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

public class UnionUserMenu implements UserMenu {

    private String unionMenu = "choose action: \n1: add league\n2: add season \n"+
            "3: manage referee \n4: update score policy \n5: manage games \n6: finance\n7: Exit";
    @Override
    public ActionStatus presentUserMenu() {
        boolean ExitOrChangeUser = false;
        StartSystem system = new StartSystem();
        UserCLI cli = system.getCli();
        while(!ExitOrChangeUser){
            int input =  cli.presentAndGetInt(unionMenu);
            if(input == 1) {
                //add league

            }
            else if(input == 2) {
                //add season to league

            }
            else if(input == 3) {
                //manage referee - add, remove, set to league and season

            }
            else if(input == 4) {
                // manage score policy and location in league

            }
            else if(input == 5) {
                //manage games - game setting policy and start outomatic games setting;

            }
            else if(input == 6) {
                //finance - edit budget regulation and edit union finance

            }
            else if(input == 7) {
                //exit

            }
            else{
                cli.presentOnly("invalid choice");
            }
        }
        return new ActionStatus(true,"log out and wait for next user");
    }

    public ActionStatus presentUserMenu(String[] args){
        String output = "";
        output += unionMenu + " " + args[0] + "\n";
        int input = Integer.parseInt(args[0]);
        if(input == 1) {
            //add league
            output += "insert new league name:\nuser input- " + args[1] + "\n";
            String name = args[1];
            boolean b = StartSystem.GSc.defineLeague(name);
            return new ActionStatus( b ,output +"new league created:" + b );
        }
        else if(input == 2) {
            //add season to league
            output += "insert league name:\nuser input- " + args[1] + "\n";
            String league_name = args[1];
            output += "insert season year:\nuser input- " + args[2] + "\n";
            String year = args[2];
            output += "insert win score policy:\nuser input- " + args[3] + "\n";
            int win = Integer.parseInt(args[3]);
            output += "insert lose score policy:\nuser input- " + args[4] + "\n";
            int lose = Integer.parseInt(args[4]);
            output += "insert equal score policy:\nuser input- " + args[5] + "\n";
            int equal = Integer.parseInt(args[5]);
            boolean b =StartSystem.GSc.defineSeasonToLeague(league_name, year,win,lose,equal);
            return new ActionStatus( b ,output +"new season created:" + b );
        }
        else if(input == 3) {
            //manage referee - add, remove, set to league and season
            output += "choose action: \n1:add referee \n2:remove referee\n3:set referee in league \nuser input- " + args[1] + "\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit == 2){
                output += "insert referee user name: \nuser input- " + args[2] +"\n";
                String referee_user_name =  args[2];
                output += "insert referee password: \nuser input- " + args[3] +"\n";
                String referee_password =  args[3];
                output += "insert referee mail: \nuser input- " + args[4] +"\n";
                String mail =  args[4];
                ActionStatus ac =StartSystem.GSc.addOrDeleteRefereeToSystem(referee_user_name, referee_password, mail, 0);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 2){
                output += "insert referee user name: \nuser input- " + args[2] +"\n";
                String referee_user_name =  args[2];
                ActionStatus ac = StartSystem.GSc.addOrDeleteRefereeToSystem(referee_user_name,"123456","r@r.r",1);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 3){
                output += "insert referee user name: \nuser input- " + args[2] +"\n";
                String referee_user_name =  args[2];
                output += "insert referee password: \nuser input- " + args[3] +"\n";
                String league_name =  args[3];
                output += "insert season year: \nuser input- " + args[4] +"\n";
                String season_year =  args[4];
                ActionStatus ac = StartSystem.GSc.defineRefereeInLeague(league_name, referee_user_name, season_year);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
        }
        else if(input == 4) {
            // update score policy (and location in league???)
            output += "insert league name: \nuser input- " + args[2] +"\n";
            String league_name =  args[2];
            output += "insert season year: \nuser input- " + args[3] +"\n";
            String year =  args[3];
            output += "insert new win score: \nuser input- " + args[4] +"\n";
            int win =  Integer.parseInt(args[4]);
            output += "insert new lose score: \nuser input- " + args[5] +"\n";
            int lose =  Integer.parseInt(args[5]);
            output += "insert new equal score: \nuser input- " + args[6] +"\n";
            int equal =  Integer.parseInt(args[6]);
            ActionStatus ac = StartSystem.GSc.updatePointsPolicy(league_name, year, win,  lose,  equal);
            return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
        }
        else if(input == 5) {
            //manage games - game setting policy and start outomatic games setting;
            //TODO - Start ortal's new function
        }
        else if(input == 6) {
            //finance - edit budget regulation and edit union finance
            
        }else if (input == 7) {
            //Exit
            output += "insert User name: " + args[1] +"\n";
            String username =   args[1];
            output += "insert password: " + args[2] +"\n";
            String password =  args[2];
            ActionStatus ac = StartSystem.LEc.Exit(username,password);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());

        } else {
            return new ActionStatus(false, output + "invalid choice\n");
        }
        return new ActionStatus(false, output);
    }

}
