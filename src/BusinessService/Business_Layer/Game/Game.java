package BusinessService.Business_Layer.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Observable;

import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Player;
import BusinessService.Business_Layer.UserManagement.Referee;
import BusinessService.Enum.ActionStatus;
import BusinessService.Enum.EventType;
import DB_Layer.logger;
import javafx.util.Pair;


public class Game extends Observable{
    public static int game_id=0;
    protected  int id;
    private String field;
    protected LocalDate date;
    private Team host;
    protected Team guest;
    protected Referee head;
    private Referee linesman1;
    private Referee linesman2;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Pair<Integer,Integer> score; // Integer[0] = host , Integer[1] = guest
    private HashSet<Event> eventList;

    public Game(String f, LocalDate d, Team h, Team g){
        game_id++;
        id = game_id;
        this.field=f;
        this.date=d;
        this.host=h;
        this.guest=g;
        eventList = new HashSet<>();
    }

    public void setGameStartTime(){
        startTime = LocalDateTime.now();
        endTime = startTime.plusMinutes(140);
    }

    public void endGame(ScoreTable scoreTable, int hostGoals, int guestGoals){
        setScore(hostGoals, guestGoals);
        updateTeamsInfo();
        host.getTeamScore().updatePoints(scoreTable.getPointsPolicy());
        guest.getTeamScore().updatePoints(scoreTable.getPointsPolicy());
        scoreTable.updateScoreTable(); //option to use observer
    }

    /**
     *
     */
    private void updateTeamsInfo(){

        host.getTeamScore().incrementNumberOfGames();
        guest.getTeamScore().incrementNumberOfGames();
        host.getTeamScore().incrementNumberOfGoalsScores(score.getKey());
        guest.getTeamScore().incrementNumberOfGoalsScores(score.getValue());
        host.getTeamScore().incrementNumberOfGoalsGet(score.getValue());
        guest.getTeamScore().incrementNumberOfGoalsGet(score.getKey());

        if(score.getKey() > score.getValue()){ //host wins
            host.getTeamScore().incrementWin();
            guest.getTeamScore().incrementLose();
        }

        else if(score.getKey() < score.getValue()) { //guest wins
            guest.getTeamScore().incrementWin();
            host.getTeamScore().incrementLose();
        }

        else{
            guest.getTeamScore().incrementDrwans();
            host.getTeamScore().incrementDrwans();
        }
    }

    public void updateEndTime(long additionalTimeInMinutes){
        endTime = endTime.plusMinutes(additionalTimeInMinutes);
    }

    public int getGameId(){
        return id;
    }

    /**
     * This function let the user add an event to the game
     * @return true - if the event was update, false otherwise.
     */
    public ActionStatus updateNewEvent(String team_name, String player_name, EventType event){
        ActionStatus ac = null;
        Event new_event = null;
        Player p = null;
        if (host.getName().equals(team_name)) {
           p = host.getPlayer(player_name);
           if(p != null){
               new_event = new Event(host, event, p, null);
               eventList.add(new_event);
           }else{
               ac = new ActionStatus(false,"Parameter wrong!");
           }
         }else if(guest.getName().equals(team_name)) {
           p= guest.getPlayer(player_name);
            if(p != null) {
                new_event = new Event(guest, event, p, null);
                eventList.add(new_event);
            }else{
                ac = new ActionStatus(false,"Parameter wrong!");
            }
         } else{
            ac = new ActionStatus(false,"Parameter wrong!");
        }
        if(ac==null) {
            setChanged();
            notifyObservers(new_event.eventToString());
            ac = new ActionStatus(true,"event added successfully");
        }
        logger.log("Game: update_new_event, team: " + team_name + " ,player " + player_name + " ,event " + event+ " " + ac.getDescription());
        return ac;
    }

    /**
     * host referee setter
     * @param r -
     */
    public void setHeadReferee(Referee r){
        if (r!=null) {
            r.addGame(this);
            this.addObserver(r);
            head = r;
        }
    }

    /**
     * linesman1 referee setter
     * @param r -
     */
    public void setLinesman1Referee(Referee r){
        if (r!=null) {
            r.addGame(this);
            this.addObserver(r);
            linesman1 = r;
        }
    }
    /**
     * linesman2 referee setter
     * @param r
     */
    public void setLinesman2Referee(Referee r){
        if (r!=null) {
            r.addGame(this);
            this.addObserver(r);
            linesman2 = r;
        }
    }
    /**
     * head referee getter
     * @return
     */
    public Referee getHeadReferee(){ return head;}
    /**
     * linesman1 referee getter
     * @return
     */
    public Referee getLinesman1Referee(){ return linesman1;}
    /**
     * linesman2 referee getter
     * @return
     */
    public Referee getLinesman2Referee(){ return linesman2;}

    public void change_field(String new_field){

        if(new_field != null && new_field.length() != 0){
            this.field = new_field;
            setChanged();
            notifyObservers("The field has changed! The new field is: "+new_field);
        }
    }

    public void changeDate(LocalDate new_date){
        if(date != null){
            date=new_date;
            setChanged();
            notifyObservers("The date has changed! The new date is: "+new_date.toString());
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public Team getHost() {
        return host;
    }

    public Team getGuest() {
        return guest;
    }

    public Pair<Integer, Integer> getScore() {
        return score;
    }

    public HashSet<Event> getEventList() {
        return eventList;
    }

    public void setHost(Team host) {
        if(host != null && !this.guest.equals(host)){
            this.host = host;
        }
    }

    public void setGuest(Team guest) {
        if(guest != null && !this.host.equals(guest)){
            this.guest = guest;
        }
    }

    public String getField() {
        return field;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    private void setScore(int host, int guest) {
        this.score = new Pair<>(host, guest);
    }
}
