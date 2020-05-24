import Business_Layer.Business_Items.UserManagement.*;

import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {
        System.out.println(parserTime("Sat May 16 2020 11:50:26 GMT+0300 (Israel Daylight Time)"));





        /*
        System.out.println(Configurations.getNumberOfGames());//1
        Configurations.setPropValues("NumberOfGames",2);
        System.out.println(Configurations.getNumberOfGames());//1
        System.out.println(Configurations.getNumberOfComplaint());//1
        Configurations.setPropValues("NumberOfComplaint",3);
        System.out.println(Configurations.getNumberOfComplaint());//3

         */

        /**********************************************init**********************************************************
         databaseController databaseController = new databaseController();
         databaseController.startDBConnection();
         UnifiedSubscription unifiedSubscription = new UnifiedSubscription("matanshu", "654321", "matanshu@bgu.ac.il");
         Player player = new Player("matanshu");
         unifiedSubscription.setNewRole(player);
         Referee referee = new Referee("shimon", "345345", "refere@gmail.com");
         Team team = new Team("Real Madrid", "Kamp nou");
         TeamScore teamScore = new TeamScore(team.getName());
         teamScore.setPoints(5);
         teamScore.setGoalsGet(3);
         teamScore.setGoalsScores(10);
         teamScore.setNumberOfGames(12);
         teamScore.setDrawn(2);
         teamScore.setLoses(4);
         teamScore.setWins(11);
         team.setTeamScore(teamScore);
         UnifiedSubscription teamOwner = new UnifiedSubscription("owner", "123456", "owner@gmail.com");
         teamOwner.setNewRole(new TeamOwner());
         team.EditTeamOwner(teamOwner, 1);
         TeamController teamController = new TeamController();
         Team team1 = new Team("Barcelona", "Kamp nou");
         League league = new League("La Liga");
         Season season = new Season("2020");
         league.addSeason(season);
         LocalDate localDate = LocalDate.of(2020,5,23);
         Game game = new Game("Barnabeo",localDate, team, team1);
         game.setHeadReferee(referee);
         game.updateNewEvent(team.getName(),"meTest", EventType.goal);
         */
        /**********************************************Insert***********************************************************
         databaseController.insert("Users", new String[]{referee.getUserName(), referee.getPassword(), referee.getRole(),
         referee.getEmail()});
         databaseController.insert("Users", new String[]{unifiedSubscription.getUserName(), unifiedSubscription.getPassword(),
         unifiedSubscription.getRole(), unifiedSubscription.getEmail()});
         databaseController.insert("Team", new Object[]{team.getName(), "Blumfild", "owner", team.getStatus(),
         team.getTeamScore().getPoints(), team.getTeamScore().getNumberOfGames(), team.getTeamScore().getWins(), team.getTeamScore().getDrawn(),
         team.getTeamScore().getLoses(), String.valueOf(team.getTeamScore().getGoalsScores()), team.getTeamScore().getGoalsGet()});
         databaseController.insert("League", new Object[]{"La Liga"});
         databaseController.insert("League", new Object[]{"UEFA"});
         databaseController.insert("Season", new Object[]{"UEFA", "2020"});
         databaseController.insert("Game",new Object[]{game.getGameId(),game.getField(),game.getDate().toString(),game.getHost().getName(),
         game.getGuest().getName(), "La Liga","2020","shimon","shimon","shimon"});
         game.updateNewEvent(team.getName(),"meTest", EventType.goal);
         Event event = new Event(team.getName(),EventType.offside, "meTest", LocalDateTime.now());
         databaseController.insert("AssetsInTeam",new Object[]{"Real Madrid","meTest","Coach"});
         databaseController.insert("EventInGame",new Object[]{game.getGameId(),event.getEventTime().toLocalTime(),
         event.getPlayer(),event.getEventType().toString()});
         databaseController.insert("RefereeInSeason",new Object[]{league.getName(), season.getYear(), referee.getUserName()});
         databaseController.insert("UsersData",new Object[]{"matanshu", "position", "striker"});
         */
        /**********************************************update ***********************************************************
         databaseController.update("Users", new String[]{"null"},"userName","matanshu");
         databaseController.update("Team", new String[]{"Barcelona"},"Wins","20");
         databaseController.update("League", new String[]{"NBA"},"leagueName","first league");
         databaseController.update("Season", new String[]{"La Liga", "2020"}, "lose", "2");
         databaseController.update("Game", new String[]{"1"},"filed","Boston Garden");
         databaseController.update("AssetsInTeam", new String[]{"Real Madrid", "meTest"},"assetRole","Coach");
         databaseController.update("EventInGame", new String[]{"1", "14:45:50.8930000"},"eventType","goal");
         databaseController.update("RefereeInSeason", new String[]{"La Liga", "2020", "shimon"}, "refereeName", "shalom");
         databaseController.update("UsersData", new String[]{"userName"},"dataType","ownerAppointedByTeamOwner");
         */


        //********************************************** BLOB ************************************************************//
/*
        databaseController databaseController = new databaseController();
        databaseController.startDBConnection();

        Permissions per = new Permissions();
        per.add_default_fan_permission();
        Permissions pi = null;

        try {
            databaseController.sqlConn.insertBlob("perms",per);
            System.out.println("done1");

            Object oi = databaseController.sqlConn.getBlob("perms");
            pi = (Permissions)oi;
            System.out.println("done2");
            System.out.println(pi.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
*/
//        //StartSystem sys = new StartSystem();
//        //sys.startFromDB();
//        //sys.ResetToFactory();
//        Date date = new Date();
//        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        int year  = localDate.getYear();
//        System.out.println(year);
//        StartSystem sys = new StartSystem();
//        sys.startFromDB(); //first option
//        sys.ResetToFactory(); //second option
    }


}