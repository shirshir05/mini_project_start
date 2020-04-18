package BusniesServic.Business_Layer.UserManagement;

import java.util.Observable;

public class Complaint extends Observable {


    private String description;
    private String answer;
    private boolean isAnswered;

    public Complaint(String dsc){
        description = dsc;
        isAnswered = false;
        answer = null;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public String getAnswer() {
        if(answer == null || answer.isEmpty())
            return "Complaint has not been answered";
        return answer;
    }

    public void answerComplaint(String ans){
        answer = ans;
        isAnswered = true;
        setChanged();
        notifyObservers("Your complaint:\n" +
                "\""+description+"\"\n" +
                "Has been answered:\n " +
                "\""+ans+"\"");
    }

    @Override
    public String toString(){
        String ans =  "Complaint Description:\n"+description+"\n";
        if(isAnswered){
            ans += "Complaint has been answered:\n" + answer + "\n";
        }
        else{
            ans += "Complaint has not been answered" + "\n";
        }
        return ans;
    }




}
