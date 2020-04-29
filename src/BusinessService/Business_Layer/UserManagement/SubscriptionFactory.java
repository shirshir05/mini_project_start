package BusinessService.Business_Layer.UserManagement;
import BusinessService.Enum.Role;
import Presentation_Layer.Spelling;


public class SubscriptionFactory {


    /**
     * This function create Subscription by Role
     * @param userName
     * @param password
     * @param role
     * @return
     */
    public Subscription Create(String userName, String password, Role role, String email){
        if(userName==null || password==null || role==null || userName.equals("") || password.equals("")) {
            return null;
        }
        if(role == Role.Coach ){
            Spelling.updateDictionary("coach: " + userName);
            return new Coach(userName,password, email);
        }
        else if (role == Role.Fan){
            Spelling.updateDictionary("fan: " + userName);
            return new Fan(userName,password, email);
        }
        else if (role == Role.Guest){
            return new Guest(userName,password, email);
        }
        else if (role == Role.Player){
            Spelling.updateDictionary("player: " + userName);
            return new Player(userName,password, email);
        }
        else if (role == Role.Referee){
            Spelling.updateDictionary("referee: " + userName);
            return new Referee(userName,password, email);
        }
        else if (role == Role.SystemAdministrator){
            Spelling.updateDictionary("systemAdministrator: " + userName);
            return new SystemAdministrator(userName,password, email);
        }
        else if (role == Role.TeamManager){
            Spelling.updateDictionary("teamManager: " + userName);
            return new TeamManager(userName,password, email);
        }
        else if (role == Role.TeamOwner){
            Spelling.updateDictionary("teamOwner: " + userName);
            return new TeamOwner(userName,password, email);
        }
        else if(role == Role.UnifiedSubscription){
            Spelling.updateDictionary("unifiedSubscription: " + userName);
            return new UnifiedSubscription(userName,password, email,null,null,null,null);
        }
        //else if (role == Role.UnionRepresentative){
       // }
        Spelling.updateDictionary("unionRepresentative: " + userName);
        return new UnionRepresentative(userName,password, email);
    }
}
