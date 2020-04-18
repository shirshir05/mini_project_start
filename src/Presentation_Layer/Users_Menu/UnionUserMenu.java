package Presentation_Layer.Users_Menu;

import BusniesServic.Business_Layer.BudgetManagement.Expense;
import BusniesServic.Business_Layer.BudgetManagement.Income;
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
            if(edit == 1){
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
                output += "insert leauge name: \nuser input- " + args[3] +"\n";
                String league_name =  args[3];
                output += "insert season year: \nuser input- " + args[4] +"\n";
                String season_year =  args[4];
                ActionStatus ac = StartSystem.GSc.defineRefereeInLeague(league_name, referee_user_name, season_year);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
        }
        else if(input == 4) {
            // update score policy (and location in league???)
            output += "insert league name: \nuser input- " + args[1] +"\n";
            String league_name =  args[1];
            output += "insert season year: \nuser input- " + args[2] +"\n";
            String year =  args[2];
            output += "insert new win score: \nuser input- " + args[3] +"\n";
            int win =  Integer.parseInt(args[3]);
            output += "insert new lose score: \nuser input- " + args[4] +"\n";
            int lose =  Integer.parseInt(args[4]);
            output += "insert new equal score: \nuser input- " + args[5] +"\n";
            int equal =  Integer.parseInt(args[5]);
            ActionStatus ac = StartSystem.GSc.updatePointsPolicy(league_name, year, win,  lose,  equal);
            return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
        }
        else if(input == 5) {
            //manage games - game setting policy and start outomatic games setting;
            //TODO - Start ortal's new function
        }
        else if(input == 6) {
            //finance - edit budget regulation and edit union finance
            output += "choose action: \n1:setMaxPlayerSalary \n2:setMinPlayerSalary\n3:setMaxCoachSalary " +
                    "\n4:setMinCoachSalary\n5:setMaxMaintenanceExpense \n6:setMaxAdvertisementExpense \n7:setMaxUniformExpense" +
                    "\n8:setMaxOtherExpense \n9:setMaxRefereeSalary \n10:setMinRefereeSalary \n11:setMaxUnionMemberSalary " +
                    "\n12:setMinUnionMemberSalary \n13:addExpenseToUnion \n14:addIncomeToUnion \n15:getUnionBalance\n" +
                    " \nuser input- " + args[1] + "\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit == 1){
                output += "insert maxPlayerSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxPlayerSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 2){
                output += "insert MinPlayerSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMinPlayerSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 3){
                output += "insert MaxCoachSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxCoachSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 4){
                output += "insert MinCoachSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMinCoachSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 5){
                output += "insert MaxMaintenanceExpense: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxMaintenanceExpense(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 6){
                output += "insert MaxAdvertisementExpense: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxAdvertisementExpense(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 7){
                output += "insert MaxUniformExpense: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxUniformExpense(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 8){
                output += "insert MaxOtherExpense: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxOtherExpense(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 9){
                output += "insert MaxRefereeSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxRefereeSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 10){
                output += "insert MinRefereeSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMinRefereeSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 11){
                output += "insert MaxUnionMemberSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMaxUnionMemberSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 12){
                output += "insert MinUnionMemberSalary: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                ActionStatus ac = StartSystem.Bc.setMinUnionMemberSalary(salary);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 13){
                output += "insert amount ExpenseToUnion: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                output += "insert type of Expense: \nuser input- " + args[3] +"\n";
                Expense exp =  StartSystem.Bc.getExpenseFromString(args[3]);
                ActionStatus ac = StartSystem.Bc.addExpenseToUnion(salary,exp);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 14){
                output += "insert amount IncomeToUnion: \nuser input- " + args[2] +"\n";
                double salary =  Double.parseDouble(args[2]);
                output += "insert type of Income: \nuser input- " + args[3] +"\n";
                Income inc =  StartSystem.Bc.getIncomeFromString(args[3]);
                ActionStatus ac = StartSystem.Bc.addIncomeToUnion(salary,inc);
                return new ActionStatus(ac.isActionSuccessful(),output+ac.getDescription());
            }
            else if(edit == 15){
                double d = StartSystem.Bc.getUnionBalance();
                return new ActionStatus(d!=-1,output+"the union balance is:"+d+"\n");
            }

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
