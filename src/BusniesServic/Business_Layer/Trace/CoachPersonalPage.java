package BusniesServic.Business_Layer.Trace;

import java.util.Date;

public class CoachPersonalPage extends PersonalPage {

    private Date dateOfBirth;
    private String countryOfBirth;
    private double yearOfExperience;
    private int numOfTitles;


    /**
     * constructor
     * @param name
     */
    public CoachPersonalPage(String name){
        super(name);
    }


    //**********************************************get & set ************************************************************//

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if(dateOfBirth != null){
            this.dateOfBirth = dateOfBirth;
        }
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        if(countryOfBirth != null && countryOfBirth.length() != 0){
            this.countryOfBirth = countryOfBirth;
        }
    }

    public void setYearOfExperience(double yearOfExperience) {
        if(yearOfExperience >= 0)
            this.yearOfExperience = yearOfExperience;
    }

    public double getYearOfExperience() {
        return yearOfExperience;
    }

    public void setNumOfTitles(int numOfTitles) {
        if(numOfTitles >= 0)
            this.numOfTitles = numOfTitles;
    }

    public int getNumOfTitles() {
        return numOfTitles;
    }

    //**********************************************to string ************************************************************//

    @Override
    public String toString() {

        return "The Coach: " + name +"\n" +
                "date of Birth: " + dateOfBirth + "\n" +
                "country of Birth: " + countryOfBirth + "\n"+
                "year of experience: " + yearOfExperience + "\n"+
                "num of titles: " + numOfTitles;
    }
}

