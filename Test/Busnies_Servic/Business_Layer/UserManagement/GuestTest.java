package Busnies_Servic.Business_Layer.UserManagement;

import Busnies_Servic.PermissionAction;
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
public class GuestTest {


    /**
     * Test - G1
     */
    @RunWith(Parameterized.class)
    public static class Guest1{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"G1","123456","shir0@post.bgu.ac.il"},
                    {"G2","123456","shir0@post.bgu.ac.il"},
                    {"G3","123456","shir0@post.bgu.ac.il"},
                    {"G4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public Guest1(String userName, String password, String email) {
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
        public void Guest1Test() {
            Guest guest = new Guest(userName,password,email);
            assertEquals(guest.getUserName(),(userName));
            assertEquals(guest.getPassword(),getHash(password));
            assertEquals(guest.getEmail(),(email));
        }
    }//Guest1





}