package Business_Layer.Business_Items.Game;

import Business_Layer.Business_Items.TeamManagement.Team;
import Business_Layer.Business_Items.UserManagement.Referee;
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
                    createGameAfterChecks(season.getLeague(), season.getYear(),date, field, host.getName(), guest.getName(), array[refereesToGame[0]], array[refereesToGame[1]], array[refereesToGame[2]]);
                    day += 7;
                    if(day > 28){
                        day = 1;
                        month ++;
                    }if(month == 13){
                        month = 1;
                        day = 2;
                    }
                } //host != guest
            }

        }
    }
}
