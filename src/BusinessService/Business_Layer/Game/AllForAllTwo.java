package BusinessService.Business_Layer.Game;

import BusinessService.Business_Layer.TeamManagement.Team;
import BusinessService.Business_Layer.UserManagement.Referee;
import java.time.LocalDate;
import java.util.HashSet;

public class AllForAllTwo extends SchedulingGame {


    /**
     * The algorithm slots two games for each team
     * @param teamList -
     * @param season -
     */
    @Override
    public void algorithm(HashSet<Team> teamList, Season season ) {
        int month = 1;
        int day = 1;
        for (Team host : teamList) {
            for (Team guest : teamList) {
                //a team will not play with itself
                if (!host.equals(guest)) {
                    String field = getFieldFromHost(host);
                    HashSet<Referee> threeReferees = season.getListOfReferees();
                    Referee[] array = new Referee[threeReferees.size()];
                    int index = 0;
                    for (Referee r : threeReferees) {
                        array[index] = r;
                        index++;
                    }
                    int sizeReferees = threeReferees.size();
                    int[] refereesToGame = getTreeRandomReferee(sizeReferees);
                    if (field == null) {
                        field = "Field will select later";
                    }
                    LocalDate date = LocalDate.of( Integer.parseInt(season.getYear()),month,day);
                    season.addGame(createGameAfterChecks(date, field, host.getName(), guest.getName(), array[refereesToGame[0]], array[refereesToGame[1]], array[refereesToGame[2]]));
                    day += 7;
                    if(day > 28){
                        day = 1;
                        month ++;
                    }
                } //host != guest
            }

        }
    }
}
