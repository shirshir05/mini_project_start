package BusinessService.Business_Layer.Game;
import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Referee;

import java.util.HashSet;

public class Season {
    protected String season;
    protected HashSet<Referee> list_referee;
    protected HashSet<Game> list_game;
    protected ScoreTable scoreTable;
    protected HashSet<Team> list_team;
    /**
     * season constructor
     * @param year is the season's year
     */
    public Season(String year) {
        list_referee = new HashSet<>();
        list_game = new HashSet<>();
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

    public Game getGame(int gameId){

        for (Game g : list_game){
            if (g.getGameId() == gameId){
                return g;
            }
        }
        return null;
    }

    /**
     * This function is adding a game to the League
     * @param g is the game
     */
    public void addGame(Game g){
        if(g != null){

            list_game.add(g);
        }
    }

    /**
     * This function is deleting a game from the league
     * @param g is the game
     */
    public void deleteGame(Game g){
        if(g != null && list_game.contains(g)){

            list_game.remove(g);
        }
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
}
