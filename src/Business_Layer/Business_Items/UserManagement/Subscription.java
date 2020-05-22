package Business_Layer.Business_Items.UserManagement;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Objects;


public abstract class Subscription {
    protected String userName;
    private String password;
    public Permissions permissions;
    public String email;
    protected String name;
    protected HashSet<String> searchHistory;


    protected HashSet<String> alerts;

    public Subscription(String argUserName, String argPassword,String email){
        userName=argUserName;
        password = getHash(argPassword);
        permissions = new Permissions();
        this.email = email;
        searchHistory =new HashSet<>();
        alerts = new HashSet<>();
    }

    //**********************************************get & set ************************************************************//

    public abstract String getRole();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = getHash(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    /**
     * return hash search
     * @param
     */
    public HashSet<String> getSearch(){
        return searchHistory;
    }


    //**********************************************function ************************************************************//

    public void addAlert(String s){alerts.add(s);}

    public String sendEMail(String mailto, String mail){
        return "Send to: "+mailto+" From: "+this.email+" Mail: "+mail;
    }

    /**
     * Add word to history
     * @param word
     */
    public void addSearch(String word){
        searchHistory.add(word);
    }


    /**
     * This function returns the hash of the password
     */
    public static String getHash(String password){
        String sha1 = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return sha1;
    }

    /**
     * This function returns the subscription alerts
     */
    public String returnAlerts(){
        String ans = "";
        for (String alert : alerts){
            ans = ans + alert +"\n";
        }
        return ans;
    }

    public HashSet<String> getAlerts(){
        return this.alerts;
    }




    //**********************************************equals ************************************************************//

    /**
     * This function equal Subscription by user_name
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(userName, that.userName);
    }


}




