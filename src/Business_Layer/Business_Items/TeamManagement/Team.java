package Business_Layer.Business_Items.TeamManagement;

import Business_Layer.Enum.ActionStatus;
import Business_Layer.Business_Items.BudgetManagement.Expense;
import Business_Layer.Business_Items.BudgetManagement.Income;
import Business_Layer.Business_Items.BudgetManagement.TeamBudget;
import Business_Layer.Business_Items.Trace.TeamPersonalPage;
import Business_Layer.Business_Items.UserManagement.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;

/**
 * A class which represents a team
 */
public class Team extends Observable implements Comparable, Serializable {

    private String Name;
    private HashSet<UnifiedSubscription> list_Player;
    private HashSet<UnifiedSubscription> list_Coach;
    private HashSet<UnifiedSubscription> list_TeamManager;
    public HashSet<UnifiedSubscription> list_TeamOwner;
    private HashSet<Object> list_assets;
    private TeamPersonalPage PersonalPage;
    private int status; // 0 - off 1 - on -1 - always close
    private TeamBudget budget;
    private TeamScore teamScore;

    /**
     * constructor
     * @param arg_name
     * @param arg_main_field
     */
    public Team(String arg_name, String arg_main_field ){
        this.Name =arg_name;
        list_Player = new HashSet<>();
        budget =  new TeamBudget(arg_name);
        list_Coach = new HashSet<>() ;
        list_TeamManager = new HashSet<>();
        list_TeamOwner = new HashSet<>();
        list_assets = new HashSet<>();
        PersonalPage = new TeamPersonalPage(Name);
        list_assets.add(arg_main_field);
        status = 1;
    }
    //**********************************************get & set************************************************************//

    public String getName() {
        return Name;
    }

    public void setBudget(TeamBudget t){this.budget=t;}

    public TeamBudget getBudget() {
        return budget;
    }

    public int getStatus() {
        return status;
    }

    public TeamPersonalPage getPersonalPage() {
        return PersonalPage;
    }

    public String setPersonalPage(TeamPersonalPage personalPage) {
        if(status == -1 || status == 0 || status==2){
            return "The team is inactive so no activity can be performed on it";
        }
        PersonalPage = personalPage;
        return null;
    }

    public HashSet<Object> getTeamAssets(){
        return list_assets;
    }

    public HashSet<UnifiedSubscription> getTeamPlayers(){
        return list_Player;
    }

    public HashSet<UnifiedSubscription> getTeamCoaches(){
        return list_Coach;
    }

    public TeamScore getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(TeamScore teamScore) {
        if(teamScore != null){
            this.teamScore = teamScore;
        }
    }

    //**********************************************Team subscriptions*****************************************************//



    /**
     * The function checks whether a particular role is part of the group. If so, he can take action on it.
     * @param object
     * @return
     */
    public boolean checkIfObjectInTeam(Subscription object){
        boolean ans = false;
        if(list_TeamManager != null ){
            //ans = list_TeamManager.contains(object);
            ans = contains(list_TeamManager,object);
        }if(list_TeamOwner != null){
            ans = ans || contains(list_TeamOwner,object) ;
        }
        return ans;
    }

    private boolean contains(HashSet <UnifiedSubscription> unifiedSubscriptions, Subscription subscription){
        for (UnifiedSubscription us : unifiedSubscriptions)
        {
            if(us.equals(subscription)){
                return true;
            }
        }
        return false;
    }

    /**
     * function that return player from team' or null if player not found
     * @param player_name
     * @return
     */
    public UnifiedSubscription getPlayer(String player_name){
        if ( player_name!=null) {
            for (UnifiedSubscription p : list_Player) {
                if (p.getUserName().equals(player_name)) {
                    return p;
                }
            }
        }
        return null;
    }


    //**********************************************Edit asset************************************************************//

    /**
     * A function that allows to add assets like a field for a team
     */
    public Object setAsset(String asset) {
        if(status == -1 || status == 0 || status==2){
            return "The team is inactive so no activity can be performed on it";
        }
        list_assets.add(asset);
        return null;
    }

    /**
     * A function that allows remove assets like a plot for a group
     * @param asset
     */
    public void removeTeamAssets(Object asset){
        list_assets.remove(asset);
    }

    /**
     * The function allows a player to be added to a team or to remove a player from the team.
     */
    public ActionStatus addOrRemovePlayer(UnifiedSubscription player, int add_or_remove ){
        if(status == -1 || status == 0 || status==2){
            return new ActionStatus(false,"The team is inactive so no activity can be performed on it");
        }
        //remove the players
        if(add_or_remove == 0){
            if(list_Player.contains(player)){
                list_Player.remove(player);
                return new ActionStatus(true, "The player was successfully removed from the team.");
            }
            return new ActionStatus(false, "The player is not in the team.");
        }
        //add the players
        else if(add_or_remove == 1){
            if(!list_Player.contains(player)){
                list_Player.add(player);
                return new ActionStatus(true, "The player was successfully added to the team.");
            }
            return new ActionStatus(false,  "The player is already in the team.");
        }
        return new ActionStatus(false,  "The action is invalid.");
    }


    /**
     * The function allows to add and remove coaches from the team.
     * @param coach_add
     * @param add_or_remove
     * @return
     */
    public ActionStatus AddOrRemoveCoach(UnifiedSubscription coach_add, int add_or_remove ){
        if(status == -1 || status == 0 || status==2){
            return new ActionStatus(false,  "The team is inactive so no activity can be performed on it");
        }
        //remove the Coach
        if(add_or_remove == 0){
            if(list_Coach.contains(coach_add)){
                list_Coach.remove(coach_add);
                return new ActionStatus(true, "The Coach was successfully removed from the team.");
            }
            return new ActionStatus(false, "The Coach is not in the team.");
        }
        //add the Coach
        else if(add_or_remove == 1){
            if(!list_Coach.contains(coach_add)){
                list_Coach.add(coach_add);
                return new ActionStatus(true, "The Coach was successfully added to the team.");
            }
            return new ActionStatus(false, "The Coach is already in the team.");
        }
        return new ActionStatus(false, "The action is invalid.");
    }
    /**
     * The function allows to add and remove Team Owner from the team.
     * @param TeamOwner
     * @param add_or_remove
     * @return
     */
    public ActionStatus EditTeamOwner(UnifiedSubscription TeamOwner, int add_or_remove){
        if(status == -1 || status == 0 || status==2){
            //if the status is 2 then the team was just created
            return new ActionStatus(false, "The team is inactive so no activity can be performed on it");
        }
        //remove the TeamOwner
        if(add_or_remove == 0){
            if(list_TeamOwner.contains(TeamOwner)){
                list_TeamOwner.remove(TeamOwner);
                return new ActionStatus(true, "The Team Owner was successfully removed from the team.");
            }
            return new ActionStatus(false, "The Team Owner is not in the team.");
        }
        //add the TeamOwner
        else if(add_or_remove == 1){
            if(!list_TeamOwner.contains(TeamOwner)){
                list_TeamOwner.add(TeamOwner);
                return new ActionStatus(true, "The Team Owner was successfully added to the team.");
            }
            return new ActionStatus(false, "The Team Owner is already in the team.");
        }
        return new ActionStatus(false, "The action is invalid.");
    }

    /**
     * The function allows to add and remove Team Manager from the team.
     * @param teamManager -
     * @param add_or_remove -
     * @return -
     */
    public ActionStatus EditTeamManager(UnifiedSubscription teamManager, int add_or_remove){
        if(status == -1 || status == 0 || status==2){
            return new ActionStatus(false,  "The team is inactive so no activity can be performed on it");
        }
        //remove the teamManager
        if(add_or_remove == 0){
            if(list_TeamManager.contains(teamManager)){
                list_TeamManager.remove(teamManager);
                return new ActionStatus(true,  "The Team Manager was successfully removed from the team.");
            }
            return new ActionStatus(false,  "The Team Manager is not in the team.");
        }
        //add the teamManager
        else if(add_or_remove == 1){
            if(!list_TeamManager.contains(teamManager)){
                list_TeamManager.add(teamManager);
                return new ActionStatus(true,  "The Team Manager was successfully added to the team.");
            }
            return new ActionStatus(false,  "The Team Manager is already in the team.");
        }
        return new ActionStatus(false,  "The action is invalid.");
    }


    //**********************************************change status************************************************************//

    /**
     *change status of team, 0 - close, 1 - open ; -1 - permanently close; 2 - waiting for approval
     * @param status
     * @return
     */
    public ActionStatus changeStatus(int status){
        String notify="";
        if(status == this.status){
            return new ActionStatus(false,  "The team is already set " + this.status);
        }
        this.status = status;
        if (status==0){notify="The Team "+this.Name+" is closed";}
        else if(status==1){notify="The Team "+this.Name+" is open";}
        else if(status==-1){notify="The Team "+this.Name+" is permanently closed";}
        else if(status==2){notify="The Team "+this.Name+" is waiting for union approval";}
        setChanged();
        notifyObservers(notify);

        return new ActionStatus(true,  "The status of the team has changed successfully.");
    }

    //**********************************************region Budget************************************************************//


    public void startNewQuarterForBudget(){
        budget.startNewQuarter();
    }

    public ActionStatus addExpense(double expense, Expense description){
        return budget.addExpense(expense,description);
    }

    public ActionStatus addIncome(double income, Income description){
        return budget.addIncome(income,description);
    }

    public double getCurrentAmountInBudget(){
        return budget.getCurrentAmount();
    }

    public HashSet<UnifiedSubscription> getListTeamManager() {
        return list_TeamManager;
    }

    public HashSet<UnifiedSubscription> getListTeamOwner() {
        return list_TeamOwner;
    }


    //**********************************************equal************************************************************//

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Team)) {
            return false;
        }
        Team team = (Team) obj;
        if (this.Name.equals(team.Name)){
            return true;
        }
        return false;
    }

    //**********************************************Compare************************************************************//

    @Override
    public int compareTo(Object o) {

        Team team = (Team) o;
        if (this.getTeamScore().getPoints() >= team.getTeamScore().getPoints()) {

            return -1;
        }
        return 1;
    }
}