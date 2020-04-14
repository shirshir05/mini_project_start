package Busnies_Servic.Business_Layer.UserManagement;

import Busnies_Servic.Business_Layer.Trace.CoachPersonalPage;
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
public class CoachTest {

    /**
     * Test - C1
     */
    @RunWith(Parameterized.class)
    public static class CoachTestC{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"},
                    {"Coach2","123456","shir0@post.bgu.ac.il"},
                    {"Coach3","123456","shir0@post.bgu.ac.il"},
                    {"Coach3", "123456","shir0@post.bgu.ac.il"}
            });
        }
        public CoachTestC(String userName, String password, String email) {
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
        public void CoachTest() {
            Coach coach = new Coach(userName,password,email);
            assertEquals(coach.getUserName(),(userName));
            assertEquals(coach.getPassword(),getHash(password));
            assertEquals(coach.getEmail(),(email));
            assertEquals(coach.getPermissions().check_permissions(PermissionAction.personal_page),true);
        }
    }//Coach



    /**
     * Test - C2
     */
    @RunWith(Parameterized.class)
    public static class setQualification{

        public String userName;
        public String password;
        public String email;
        public String qualification;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il","qualification"}

            });
        }
        public setQualification(String userName, String password, String email,String qualification) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.qualification = qualification;
        }
        @Test
        public void setQualificationTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setQualification(qualification);
            assertEquals(coach.getQualification(),qualification);
        }
    }//setQualification

    /**
     * Test - C3
     */
    @RunWith(Parameterized.class)
    public static class getQualification{

        public String userName;
        public String password;
        public String email;
        public String qualification;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il","qualifications"},
                    {"Coach1","123456","shir0@post.bgu.ac.il",null}
            });
        }
        public getQualification(String userName, String password, String email,String qualification) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.qualification = qualification;
        }
        @Test
        public void getQualificationTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setQualification(qualification);
            assertEquals(coach.getQualification(),qualification);
        }
    }//getQualification



    /**
     * Test - C4
     */
    @RunWith(Parameterized.class)
    public static class setRoleInTeam{

        public String userName;
        public String password;
        public String email;
        public String Role;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il","qualifications"},
                    {"Coach1","123456","shir0@post.bgu.ac.il",null}
            });
        }
        public setRoleInTeam(String userName, String password, String email,String Role) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.Role = Role;
        }
        @Test
        public void setRoleInTeamTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setRoleInTeam(Role);
            assertEquals(coach.getRoleInTeam(),Role);
        }
    }//setRoleInTeam


    /**
     * Test - C5
     */
    @RunWith(Parameterized.class)
    public static class getRoleInTeam{

        public String userName;
        public String password;
        public String email;
        public String Role;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il","qualifications"},
                    {"Coach1","123456","shir0@post.bgu.ac.il",null}
            });
        }
        public getRoleInTeam(String userName, String password, String email,String Role) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.Role = Role;
        }
        @Test
        public void getRoleInTeamTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setRoleInTeam(Role);
            assertEquals(coach.getRoleInTeam(),Role);
        }
    }//getRoleInTeam


    /**
     * Test - C6
     */
    @RunWith(Parameterized.class)
    public static class toString{

        public String userName;
        public String password;
        public String email;
        public String Role;
        public String qualification;
        public String toString;

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il","qualifications","role","Coach: \n" +
                            "name: null\n" +
                            "email: shir0@post.bgu.ac.il\n" +
                            "qualification: qualifications\n" +
                            "roleInTeam: role"},

            });
        }
        public toString(String userName, String password, String email,String qualification,String Role,String ans) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.Role = Role;
            this.qualification = qualification;
            this.toString = ans;
        }
        @Test
        public void toStringTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setRoleInTeam(Role);
            coach.setQualification(qualification);
            assertEquals(coach.toString(),toString);
        }
    }//toString




    /**
     * Test - C7
     */
    @RunWith(Parameterized.class)
    public static class setPersonalPage{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public setPersonalPage(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;

        }

        @Test
        public void setPersonalPageTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setPersonalPage(null);
            assertNull(coach.getPersonalPage());
        }
    }//setPersonalPage


    /**
     * Test - C8
     */
    @RunWith(Parameterized.class)
    public static class getPersonalPage{

        public String userName;
        public String password;
        public String email;


        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Coach1","123456","shir0@post.bgu.ac.il"}

            });
        }
        public getPersonalPage(String userName, String password, String email) {
            this.userName = userName;
            this.password = password;
            this.email = email;

        }

        @Test
        public void getPersonalPageTest() {
            Coach coach = new Coach(userName,password,email);
            coach.setPersonalPage(new CoachPersonalPage(userName));
            assertNotNull(coach.getPersonalPage());
        }
    }//getPersonalPage

}