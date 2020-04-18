package Presentation_Layer.Users_Menu;

import BusniesServic.Business_Layer.BudgetManagement.Expense;
import BusniesServic.Business_Layer.BudgetManagement.Income;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;
import Presentation_Layer.StartSystem;
import Presentation_Layer.UserCLI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TeamUsersMenu implements UserMenu {

    private String teamMenu = "choose action: \n1:(6.1,6.2,6.3,6.4,6.5) update asset \n2:(6.6) change status \n3:(6.7) Team budget" +
            "\n4: edit coach personal page\n\n";

    @Override
    public ActionStatus presentUserMenu() {

        return null;
    }

    public ActionStatus presentUserMenu(String[] args) {
        String output = "";
        output += teamMenu + " " + args[0] + "\n";
        int input = Integer.parseInt(args[0]);
        if(input == 1) {
            output += "\nchoose action: \n1:(6.1) update player \n2:(6.1) update coach \nn3:(6.1) update filed \n4:(6.1,6.2,6.3) update team owner" +
                    "5:(6.1,6.4,6.5) update team manager \nuser input- " + args[1] +"\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit == 1){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert user name player: \nuser input- " + args[3] +"\n";
                String namePlayer  =  args[3];
                output += "insert 1 to add or 0 to remove: \nuser input- " + args[4] +"\n";
                int addOrRemove  =  Integer.parseInt(args[4]);
                ActionStatus ac = StartSystem.getTc().AddOrRemovePlayer(nameTeam,namePlayer,addOrRemove);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit == 2){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert user name coach: \nuser input- " + args[3] +"\n";
                String nameCoach =  args[3];
                output += "insert 1 to add or 0 to remove: \nuser input- " + args[4] +"\n";
                int addOrRemove  =  Integer.parseInt(args[4]);
                ActionStatus ac = StartSystem.getTc().AddOrRemoveCoach(nameTeam,nameCoach,addOrRemove);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            } else if(edit == 3){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert user name filed: \nuser input- " + args[3] +"\n";
                String nameFiled =  args[3];
                output += "insert 1 to add or 0 to remove: \nuser input- " + args[4] +"\n";
                int addOrRemove  =  Integer.parseInt(args[4]);
                ActionStatus ac = StartSystem.getTc().AddOrRemoveTeamsAssets(nameTeam,nameFiled,addOrRemove);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit == 4){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert user name team owner: \nuser input- " + args[3] +"\n";
                String nameTeamOwner =  args[3];
                output += "insert 1 to add or 0 to remove: \nuser input- " + args[4] +"\n";
                int addOrRemove  =  Integer.parseInt(args[4]);
                ActionStatus ac = StartSystem.getTc().AddOrRemoveTeamOwner(nameTeam,nameTeamOwner,addOrRemove);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit == 5){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert user name team owner: \nuser input- " + args[3] +"\n";
                String nameTeamManager =  args[3];
                output += "insert 1 to add or 0 to remove: \nuser input- " + args[4] +"\n";
                int addOrRemove  =  Integer.parseInt(args[4]);
                ActionStatus ac = StartSystem.getTc().AddOrRemoveTeamManager(nameTeam,nameTeamManager,addOrRemove);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }
        }
        if(input == 2) {
            output += "insert status: 1- open, 0 -close:  \nuser input- " + args[1] +"\n";
            int status  =  Integer.parseInt(args[1]);
            output += "insert team name: \nuser input- " + args[2] +"\n";
            String teamName  =  args[2];
            ActionStatus ac = StartSystem.getTc().ChangeStatusTeam(teamName,status);
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }  if(input == 3) {
            output += "\nchoose action: \n1:(6.7) start new quarter \n2:(6.7) add expense to team \nn3:(6.7) add income to team \n4:(6.7) get team balance for quarter" +
                    "\nuser input- " + args[1] +"\n";
            int edit =  Integer.parseInt(args[1]);
            if(edit == 1){
                ActionStatus ac = StartSystem.getBc().startNewQuarter();
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            } else if(edit == 2){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert income: \nuser input- " + args[3] +"\n";
                double income =  Double.parseDouble(args[3]);
                ActionStatus ac = StartSystem.getBc().addIncomeToTeam(nameTeam,income, Income.Gambling);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            } else if(edit == 4){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                ActionStatus ac;
                if(StartSystem.getBc().getTeamBalanceForQuarter(nameTeam) == -1){
                     ac = new ActionStatus(false,  "The team not found");
                }else{
                    ac = new ActionStatus(true,StartSystem.getBc().getTeamBalanceForQuarter(nameTeam) + "");
                }
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }else if(edit == 3){
                output += "insert name team: \nuser input- " + args[2] +"\n";
                String nameTeam  =  args[2];
                output += "insert expense: \nuser input- " + args[3] +"\n";
                double expense =  Double.parseDouble(args[3]);
                ActionStatus ac = StartSystem.getBc().addExpenseToTeam(nameTeam,expense,Expense.PlayerSalary);
                return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
            }
        } else if(input == 4){
            output += "insert name team: \nuser input- " + args[1] +"\n";
            String nameCoach  =  args[1];
            ActionStatus ac = StartSystem.getESUDc().editCoachPersonalPage(nameCoach,(new Object[]{new Date(),args[3],args[4],args[5],args[6]}));
            return new ActionStatus(ac.isActionSuccessful(),output + ac.getDescription());
        }else {
            return new ActionStatus(false, output + "invalid choice\n");
        }
        return null;
    }

}
