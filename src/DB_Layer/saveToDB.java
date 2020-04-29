package DB_Layer;

import BusinessService.Enum.ActionStatus;

public interface saveToDB {

    public ActionStatus startDBConnection();

    public ActionStatus checkDBConnection();

    public ActionStatus SaveUsersInfo();

    public ActionStatus SaveTeamInfo();

    public ActionStatus SaveGameInfo();

    public ActionStatus SaveLeagueInfo();
}
