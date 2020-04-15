package DB_Layer;

import BusniesServic.Enum.ActionStatus;


public interface InitDB {

    public ActionStatus startDBConnection();

    public ActionStatus checkDBConnection();

    public ActionStatus loadUsersInfo();

    public ActionStatus loadTeamInfo();

    public ActionStatus loadGameInfo();

    public ActionStatus loadLeagueInfo();
}
