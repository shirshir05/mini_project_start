package Business_Layer.Business_Items.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import Business_Layer.Business_Control.DataManagement;
import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.Referee;
import Business_Layer.Business_Items.UserManagement.Subscription;
import Business_Layer.Business_Items.UserManagement.UnifiedSubscription;
import Business_Layer.Enum.ActionStatus;
import Business_Layer.Enum.Configurations;
import Business_Layer.Enum.EventType;
import DB_Layer.logger;
import Service_Layer.Spelling;
import javafx.util.Pair;


public class Game extends Observable implements java.io.Serializable{
    protected  int id;
    private String field;
    protected LocalDate date;
    private Team host;
    protected Team guest;
    protected String head;
    private String linesman1;
    private String linesman2;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Pair<Integer,Integer> score; // Integer[0] = host , Integer[1] = guest
    private HashSet<Event> eventList;
    private HashSet<String> usersOb;
    private String league;
    private String season;

    public Game(String f, LocalDate d, Team h, Team g){
        id = Configurations.getNumberOfGames();
        Configurations.setPropValues("NumberOfGames",++id);
        this.field=f;
        this.date=d;
        this.host=h;
        this.guest=g;
        eventList = new HashSet<>();
        usersOb = new HashSet<>();
        Spelling.updateDictionary("Game: " +id);
    }

    /**
     * for DB
     * @param f  -
     * @param d-
     * @param h-
     * @param g-
     * @param gameId-
     */
    public Game(String f, LocalDate d, Team h, Team g,int gameId){
        id = gameId;
        this.field=f;
        this.date=d;
        this.host=h;
        this.guest=g;
        eventList = new HashSet<>();
        usersOb = new HashSet<>();
    }

    public void setGameStartTime(){
        startTime = LocalDateTime.now();
        endTime = startTime.plusMinutes(140);
    }

    public void setStartAndEndTime(LocalDateTime start,LocalDateTime end){
        startTime = start;
        endTime = end;
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
        UnifiedSubscription p = null;
        if (host.getName().equals(team_name)) {
           p = host.getPlayer(player_name);
           if(p != null){
               new_event = new Event(host, event, p, LocalDateTime.now());
               eventList.add(new_event);
           }else{
               ac = new ActionStatus(false,"Parameter wrong!");
           }
        }else if(guest.getName().equals(team_name)) {
           p= guest.getPlayer(player_name);
            if(p != null) {
                new_event = new Event(guest, event, p, LocalDateTime.now());
                eventList.add(new_event);
            }else{
                ac = new ActionStatus(false,"Parameter wrong!");
            }
        } else{
            ac = new ActionStatus(false,"Parameter wrong!");
        }
        if(ac==null) {
            HashSet<String> obs = this.usersOb;
            HashSet<Subscription> obsSub = new HashSet<>();
            for(String s : obs){
                obsSub.add(DataManagement.containSubscription(s));
            }
            setChanged();
            notifyObservers(new_event.eventToString());
            ac = new ActionStatus(true,"event added successfully");
            DataManagement.updateEventGame(this);

        }
        logger.log("Game: update_new_event, team: " + team_name + " ,player " + player_name + " ,event " + event+ " " + ac.getDescription());
        return ac;
    }

    /**
     * used to insert existing events from database
     * @param e
     */
    public void addEvent(Event e){
        eventList.add(e);
    }

    /**
     * host referee setter
     * @param r -
     */
    public void setHeadReferee(Referee r){
        if (r!=null) {
            r.addGame(this);
            this.addObserver(r);
            head = r.getUserName();
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
            linesman1 = r.getUserName();
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
            linesman2 = r.getUserName();
        }
    }
    /**
     * head referee getter
     * @return
     */
    public String getHeadReferee(){ return head;}
    /**
     * linesman1 referee getter
     * @return
     */
    public String getLinesman1Referee(){ return linesman1;}
    /**
     * linesman2 referee getter
     * @return
     */
    public String getLinesman2Referee(){ return linesman2;}

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

    public void setLeague(String league) {
        this.league = league;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLeague() {
        return league;
    }

    public String getSeason() {
        return season;
    }

    public HashSet<String> getObs(){return usersOb;}

    public void setObs(HashSet<String> obs){usersOb = obs;}

    public void addToObs(String user){usersOb.add(user);}
}

