package Busnies_Servic.Business_Layer.UserManagement;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class SubscriptionTest {

    /**
     * Test - Sub1
     */
    @RunWith(Parameterized.class)
    public static class getAlerts{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public getAlerts(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void getAlertsTest() {
            Subscription sub = new Coach(userName,password,email);
            assertNotNull(sub.searchHistory);
            sub.addAlert("shir");
            assertEquals(sub.getAlerts(),"shir\n");
        }
    }//getAlerts

    /**
     * Test - Sub2
     */
    @RunWith(Parameterized.class)
    public static class getName{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public getName(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void getNameTest() {
            Subscription sub = new Coach(userName,password,email);
            sub.setName("shir");
            assertEquals(sub.getName(),"shir");
        }
    }//getName

    /**
     * Test - Sub3
     */
    @RunWith(Parameterized.class)
    public static class setEmail{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public setEmail(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void setEmailTest() {
            Subscription sub = new Coach(userName,password,email);
            sub.setEmail("shir0@post.bgu.ac.il");
            assertEquals(sub.getEmail(),"shir0@post.bgu.ac.il");
        }
    }//setEmail

    /**
     * Test - Sub4
     */
    @RunWith(Parameterized.class)
    public static class setPassword{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public setPassword(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        private String getHash(String password){
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

        @Test
        public void setPasswordTest() {
            Subscription sub = new Coach(userName,password,email);
            sub.setPassword("123456");
            assertEquals(sub.getPassword(),getHash("123456"));
        }
    }//setPassword


    /**
     * Test - Sub5
     */
    @RunWith(Parameterized.class)
    public static class setUserName{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public setUserName(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void setUserNameTest() {
            Subscription sub = new Coach(userName,password,email);
            sub.setUserName("hi");
            assertEquals(sub.getUserName(),("hi"));
        }
    }//setUserName

    /**
     * Test - Sub6
     */
    @RunWith(Parameterized.class)
    public static class sendEMail{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public sendEMail(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void sendEMailTest() {
            Subscription sub = new Coach(userName,password,email);
            assertEquals( sub.sendEMail("shir0@post.bgu.ac.il","hi"),("Send to: shir0@post.bgu.ac.il From: shir0@post.bgu.ac.il Mail: hi"));
        }
    }//sendEMail

    /**
     * Test - Sub7
     */
    @RunWith(Parameterized.class)
    public static class addSearch{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public addSearch(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void addSearchTest() {
            Subscription sub = new Coach(userName,password,email);
            assertEquals(sub.getSearch().size(),0);
            sub.addSearch("shir");
            assertEquals(sub.getSearch().size(),1);

        }
    }//addSearch


}