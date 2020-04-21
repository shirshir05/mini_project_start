package DB_Layer;

import BusinessService.Enum.ActionStatus;


public interface InitFromDB {

    public ActionStatus startDBConnection();

    public ActionStatus checkDBConnection();

    public ActionStatus loadUsersInfo();

    public ActionStatus loadTeamInfo();

    public ActionStatus loadGameInfo();

    public ActionStatus loadLeagueInfo();
}
