package BusinessService.Business_Layer.Game;

import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Referee;
import BusinessService.Service_Layer.DataManagement;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SchedulingGame {


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
        while(randomNum2 != randomNum1){
            randomNum2 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        }
        int randomNum3 = ThreadLocalRandom.current().nextInt(0, sizeReferees );
        while(randomNum3 != randomNum2 ){
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
     Game createGameAfterChecks(LocalDate date, String field, String host, String guest, Referee headReferee, Referee line1Referee, Referee line2Referee) {
        Game g = new Game(field, date, DataManagement.findTeam(host),DataManagement.findTeam(guest));
        g.setLinesman1Referee(line1Referee);
        g.setLinesman2Referee(line2Referee);
        g.setHeadReferee(headReferee);
        DataManagement.addGame(g);
        return g;
    }



}
