package Business_Layer.Business_Items.Game;
import java.util.HashSet;

public class League implements java.io.Serializable{

    protected String name;
    protected HashSet<Season> seasons;



    /**
     * constructor
     * @param arg_name is the league name
     */
    public League(String arg_name){
        name=arg_name;
        seasons = new HashSet<>();
    }

    public HashSet<Season> getAllSeasons(){
        return seasons;
    }

    /**
     * league name getter
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * this function adds a season to the league
     * @param season
     * @return
     */
    public boolean addSeason(Season season){
        if (season!=null){
            seasons.add(season);
            season.setLeague(this.name);
            return true;
        }
        return false;
    }

    public void setName(String name) {
        if(name != null && name.length() != 0){
            this.name = name;
        }
    }

    /**
     * This function gets a season in  a league
     * @param year
     * @return
     */
    public Season getSeason(String year){
        for (Season s : seasons){
            if (s.getYear().equals(year)){
                return s;
            }
        }
        return null;

    }
}
