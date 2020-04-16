package DB_Layer;

import BusniesServic.Business_Layer.UserManagement.Fan;
import BusniesServic.Business_Layer.UserManagement.Subscription;
import BusniesServic.Enum.ActionStatus;
import BusniesServic.Service_Layer.DataManagement;

import java.util.HashSet;

public class myFirstDB implements InitDB{
    @Override
    public ActionStatus startDBConnection() {
        return new ActionStatus(true,"good");
    }

    @Override
    public ActionStatus checkDBConnection() {
        return  new ActionStatus(true,"good");
    }

    @Override
    public ActionStatus loadUsersInfo() {
       return null;
    }

    @Override
    public ActionStatus loadTeamInfo() {
        return null;
    }

    @Override
    public ActionStatus loadGameInfo() {
        return null;
    }

    @Override
    public ActionStatus loadLeagueInfo() {
        return null;
    }
}
