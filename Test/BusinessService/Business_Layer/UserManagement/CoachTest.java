package BusinessService.Business_Layer.UserManagement;

import BusinessService.Business_Layer.Trace.CoachPersonalPage;
import BusinessService.Enum.PermissionAction;
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

    private static UnifiedSubscription createCoach(String userName, String password, String email) {
        UnifiedSubscription coach = new UnifiedSubscription(userName,password,email);
        coach.setNewRole(new Coach(coach.getUserName()));
        return coach;
    }

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
            UnifiedSubscription coach = createCoach(userName,password,email);
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
            UnifiedSubscription coach = createCoach(userName,password,email);
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
            UnifiedSubscription coach = createCoach(userName,password,email);
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
            UnifiedSubscription coach = createCoach(userName,password,email);
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
            UnifiedSubscription coach = createCoach(userName,password,email);
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
                    {"Coach1","123456","shir0@post.bgu.ac.il","qualifications","role",
                            "name: null\n" +
                            "email: shir0@post.bgu.ac.il\n" +
                                    "Coach: \n" +
                            "qualification: qualifications\n" +
                            "roleInTeam: role"+"\n"},

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
            UnifiedSubscription coach = createCoach(userName,password,email);
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
            UnifiedSubscription coach = createCoach(userName,password,email);
            coach.setCoachPersonalPage(null);
            assertNull(coach.getCoachPersonalPage());
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
            UnifiedSubscription coach = createCoach(userName,password,email);
            coach.setCoachPersonalPage(new CoachPersonalPage(userName));
            assertNotNull(coach.getCoachPersonalPage());
        }
    }//getPersonalPage

}