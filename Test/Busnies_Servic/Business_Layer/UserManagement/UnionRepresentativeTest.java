package Busnies_Servic.Business_Layer.UserManagement;

import Busnies_Servic.Business_Layer.Game.Game;
import Busnies_Servic.Business_Layer.TeamManagement.Team;
import Busnies_Servic.PermissionAction;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class UnionRepresentativeTest {

    /**
     * Test - UR1
     */
    @RunWith(Parameterized.class)
    public static class UnionRepresentative1{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t1","123456","shir0@post.bgu.ac.il"},
                    {"t2","123456","shir0@post.bgu.ac.il"},
                    {"t3","123456","shir0@post.bgu.ac.il"},
                    {"Rt4", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public UnionRepresentative1(String userName, String password, String email) {
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
        public void UnionRepresentative1Test() {
            UnionRepresentative UnionRepresentative = new UnionRepresentative(userName,password,email);
            assertEquals(UnionRepresentative.getUserName(),(userName));
            assertEquals(UnionRepresentative.getPassword(),getHash(password));
            assertEquals(UnionRepresentative.getEmail(),(email));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.define_league));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.define_season));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.appointment_referee));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.remove_referee));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.setting_referee_in_league));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.Calculation_Policy));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.setting_games));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.setting_games_Policy));
            assertTrue(UnionRepresentative.getPermissions().check_permissions(PermissionAction.uniun_financial));
        }
    }//UnionRepresentative1



    /**
     * Test - UR2
     */
    @RunWith(Parameterized.class)
    public static class update{

        public String userName;
        public String password;
        public String email;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"t","123456","shir0@post.bgu.ac.il"}

            });
        }
        public update(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        @Test
        public void updateTest() {
            UnionRepresentative UnionRepresentative = new UnionRepresentative(userName,password,email);
            assertEquals(UnionRepresentative.alerts.size(),0);
            UnionRepresentative.update(new Game("s", LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"shir");
            assertEquals(UnionRepresentative.alerts.size(),1);
            UnionRepresentative.update(new Game("s",LocalDate.of(1995,18,8),new Team("1","r"),new Team("2","r")),"The ");
            assertEquals(UnionRepresentative.alerts.size(),2);
        }
    }//update

    /**
     * Test - UR3
     */
    @RunWith(Parameterized.class)
    public static class toString{

        public String userName;
        public String password;
        public String email;
        public String toString;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"r1","123456","shir0@post.bgu.ac.il","UnionRepresentative: \n" +
                            "name: null\n" +
                            "email: shir0@post.bgu.ac.il"}

            });
        }
        public toString(String userName, String password, String email,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.toString = ans;
        }
        @Test
        public void toStringTest() {
            UnionRepresentative UnionRepresentative = new UnionRepresentative(userName,password,email);
            assertEquals(UnionRepresentative.toString(),toString);
        }
    }//toString




}