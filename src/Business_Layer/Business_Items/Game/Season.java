package Business_Layer.Business_Items.Game;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.Referee;

import java.util.HashSet;

public class Season implements java.io.Serializable {
    protected String season;
    protected HashSet<Referee> list_referee;
    protected ScoreTable scoreTable;
    protected HashSet<Team> list_team;
    protected String league;
    /**
     * season constructor
     * @param year is the season's year
     */
    public Season(String year) {
        list_referee = new HashSet<>();
        list_team = new HashSet<>();
        season = year;
    }

    /**
     * This function is adding a referee to the league
     * @param r is the referee
     */
    public void addReferee(Referee r){

        if(r != null){

            list_referee.add(r);
        }
    }

    /**
     * This function is deleting a referee from the league
     * @param r is the referee
     */
    public void deleteReferee(Referee r){
        if(r != null && list_referee.contains(r)){

            list_referee.remove(r);
        }
    }

    public Referee getReferee(String refereeName){
        for (Referee r : list_referee){
            if (r.getUserName().equals(refereeName)){
                return r;
            }
        }
        return null;
    }


    /**
     * This function gets the season's year
     * @return
     */
    public String getYear(){
        return this.season;
    }

    public void setYear(String season) {
        if(season != null && season.length() != 0){

            this.season = season;
        }
    }

    public ScoreTable getScoreTable() {
        return scoreTable;
    }

    public void setScoreTable(ScoreTable scoreTable) {
        this.scoreTable = scoreTable;
    }

    public void addTeam(Team team){
        if(team != null)
            list_team.add(team);
    }

    public HashSet<Team> getListOfTeams() {
        return list_team;
    }

    public HashSet<Referee> getListOfReferees() {
        return list_referee;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
