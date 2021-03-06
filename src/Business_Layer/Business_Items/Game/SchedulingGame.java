package Business_Layer.Business_Items.Game;

import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.Referee;
import Business_Layer.Business_Control.DataManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SchedulingGame implements java.io.Serializable{


    /**
     * The above function defines the algorithm according to the games embedded in the league
     */
    public abstract void  algorithm(HashSet<Team> teamList, Season season);


    /**
     *
     * @param host  -
     * @return -
     */
    String getFieldFromHost(Team host) {
        HashSet<Object> fields = host.getTeamAssets();
        for (Object field : fields) {
            if (field instanceof String)
                return (String) field;
        }
        return null;
    }


    /**
     * @param sizeReferees -
     * @return -
     */
    int[] getTreeRandomReferee(int sizeReferees){
        int randomNum1 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        int randomNum2 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        while(randomNum2 == randomNum1){
            randomNum2 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        }
        int randomNum3 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        while(randomNum3 == randomNum2 || randomNum3 == randomNum1){
            randomNum3 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        }
        return new int[]{randomNum1, randomNum2, randomNum3};
    }

    /**
     * Creates a game after checking all the fields are valid
     * Cannot receive null or illegal parameters
     * @param date notnull
     * @param field notnull
     * @param host notnull
     * @param guest notnull
     * @param headReferee notnull
     * @param line1Referee notnull
     * @param line2Referee notnull
     */
     Game createGameAfterChecks(String league, String season, LocalDate date, String field, String host, String guest, Referee headReferee,
                                Referee line1Referee, Referee line2Referee, int year, int month,int day) {
        Game g = new Game(field, date, DataManagement.findTeam(host),DataManagement.findTeam(guest));
        g.setLinesman1Referee(line1Referee);
        g.setLinesman2Referee(line2Referee);
        g.changeDate(date);
        g.setHeadReferee(headReferee);
        g.setLeague(league);
        g.setSeason(season);
        g.setStartAndEndTime(LocalDateTime.of(year,month,day,21,0),LocalDateTime.of(year,month,day,22,30));
        DataManagement.addGame(g);
        return g;
    }



}
