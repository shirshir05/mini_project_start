package Business_Layer.Business_Items.Trace;

import java.util.Date;

public class PlayerPersonalPage extends PersonalPage implements java.io.Serializable{

    private Date dateOfBirth;
    private String countryOfBirth;
    private String cityOfBirth;
    private double height; //in cm
    private double weight; //in kg
    private String position;
    private int jerseyNumber;
    private FootballPlayerStatistic statistic;

    public PlayerPersonalPage(String name){
        super(name);

        statistic = new FootballPlayerStatistic();

    }
    //**********************************************get & set ************************************************************//

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getPosition() {
        return position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public FootballPlayerStatistic getStatistic() {
        return statistic;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if(dateOfBirth != null){
            this.dateOfBirth = dateOfBirth;
        }
    }

    public void setCountryOfBirth(String countryOfBirth) {

        if(countryOfBirth != null && countryOfBirth.length() != 0){

            this.countryOfBirth = countryOfBirth;
        }
    }

    public void setCityOfBirth(String cityOfBirth) {

        if(cityOfBirth != null && cityOfBirth.length() != 0){

            this.cityOfBirth = cityOfBirth;
        }
    }

    public void setHeight(double height) {

        if(height >= 0){

            this.height = height;
        }
    }

    public void setWeight(double weight) {

        if(weight >= 0){

            this.weight = weight;
        }

    }

    public void setPosition(String position) {

        if(position != null && position.length() != 0){

            this.position = position;
        }
    }

    public void setJerseyNumber(int jerseyNumber) {

        if(jerseyNumber >= 0){

            this.jerseyNumber = jerseyNumber;
        }
    }

    public void setStatistic(FootballPlayerStatistic statistic) {

        if(statistic != null){

            this.statistic = statistic;
        }
    }

    //**********************************************to string ************************************************************//

    @Override
    public String toString() {

        return "The Player: " + name +"\n" +
                "date of Birth: " + dateOfBirth + "\n" +
                "country of Birth:" + countryOfBirth + "\n"+
                "city of birth:" + cityOfBirth +"\n" +
                "height:" + height + "\n" +
                "weight:" + weight + "\n" +
                "position:" + position + "\n" +
                "jerseyNumber:" + jerseyNumber + "\n" +
                "statistic: " + "\n" +
                statistic.toString();
    }
  
}
