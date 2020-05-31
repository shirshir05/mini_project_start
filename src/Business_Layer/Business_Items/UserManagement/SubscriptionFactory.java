package Business_Layer.Business_Items.UserManagement;
import Business_Layer.Enum.Role;
import Service_Layer.Spelling;


public class SubscriptionFactory {


    /**
     * This function create Subscription by Role
     * @param userName
     * @param password
     * @param role
     * @return
     */
    public Subscription Create(String userName, String password, Role role, String email, boolean add){
        if(userName==null || password==null || role==null || userName.equals("") || password.equals("")) {
            return null;
        }
        /*if(role == Role.Coach ){
            Spelling.updateDictionary("coach: " + userName);
            return new Coach(userName,password, email);
        }*/
        else if (role == Role.Coach || role == Role.Player || role == Role.TeamManager || role == Role.TeamOwner || role==Role.UnifiedSubscription){
            //create a unified subscription with this role
            UnifiedSubscription unified = new UnifiedSubscription(userName,password,email);
            addRoleToUnifiedSubscription(unified, role);
            return unified;
        }
        else if (role == Role.Fan){
            if (add) {Spelling.updateDictionary("fan: " + userName);}
            return new Fan(userName,password, email);
        }
        else if (role == Role.Guest){
            return new Guest(userName,password, email);
        }
        /*else if (role == Role.Player){
            Spelling.updateDictionary("player: " + userName);
            return new Player(userName,password, email);
        }*/
        else if (role == Role.Referee){
            if (add) {Spelling.updateDictionary("referee: " + userName);}
            return new Referee(userName,password, email);
        }
        else if (role == Role.SystemAdministrator){
            if (add) {Spelling.updateDictionary("systemAdministrator: " + userName);}
            return new SystemAdministrator(userName,password, email);
        }
        /*else if (role == Role.TeamManager){
            Spelling.updateDictionary("teamManager: " + userName);
            return new TeamManager(userName,password, email);
        }
        else if (role == Role.TeamOwner){
            Spelling.updateDictionary("teamOwner: " + userName);
            return new TeamOwner(userName,password, email);
        }*/
       /* else if(role == Role.UnifiedSubscription){
            Spelling.updateDictionary("unifiedSubscription: " + userName);
            return new UnifiedSubscription(userName,password, email);
        }*/
        //else if (role == Role.UnionRepresentative){
       // }
        if (add) {Spelling.updateDictionary("unionRepresentative: " + userName);}
        return new UnionRepresentative(userName,password, email);
    }

    public void addRoleToUnifiedSubscription(UnifiedSubscription sub, Role role) {
        if(role == Role.Coach ){
            Spelling.updateDictionary("coach: " + sub.userName);
            sub.setNewRole(new Coach(sub.userName));
        }
        else if (role == Role.TeamManager){
            Spelling.updateDictionary("teamManager: " + sub.userName);
            sub.setNewRole(new TeamManager());
        }
        else if (role == Role.TeamOwner){
            Spelling.updateDictionary("teamOwner: " + sub.userName);
            sub.setNewRole(new TeamOwner());
        }
        else if (role == Role.Player){
            Spelling.updateDictionary("player: " + sub.userName);
            sub.setNewRole(new Player(sub.userName));
        }
    }
}
