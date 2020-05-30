package DB_Layer;
import Business_Layer.Business_Items.UserManagement.Subscription;
import DB_Layer.JDBC.sqlConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class databaseControllerTest {
    public static sqlConnection sql = new sqlConnection();
    public static databaseController data = new databaseController();

    /**
     * Test - FM1
     */
    @RunWith(Parameterized.class)
    public static class databaseTests {
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {}
            });
        }

        public databaseTests() {
        }

        @Test
        public void insertUserTest() {
            cleanDatabase();
            insertDatabase();
            assertEquals(data.loadUserByName("user1").getUserName(), "user1");
        }
        @Test
        public void insertUserUnionTest() {
            cleanDatabase();
            insertDatabase();
            String[] names = new String[2];
            int i=0;
            HashSet<Subscription> list = data.loadUsersByRole("SystemAdministrator");
            for (Subscription s : list){
                names[i]=s.getUserName();
                i++;
            }
            assertEquals(names[0],"user12");
            assertEquals(names[1],"user11");
        }
        @Test
        public void insertTeamTest() {
            cleanDatabase();
            insertDatabase();
            assertEquals(data.loadTeamInfo("MaccabiTLV").getName(), "MaccabiTLV");
        }
        @Test
        public void insertLeagueTest() {
            cleanDatabase();
            insertDatabase();
            assertEquals(data.loadLeagueInfo("A").getName(), "A");
            assertEquals(data.loadLeagueInfo("A").getSeason("2020").getYear(), "2020");
        }


    }

    private static void cleanDatabase() {
        deleteTestLeagues();
        deleteTestTeams();
        deleteTestUsers();
    }
    private static void insertDatabase() {
        testUsers();
        testTeams();
        testLeagues();
    }

    private static void deleteTestUsers() {
        // Delete UsersData
        sql.delete("UsersData",new String[]{"user1","position"});
        sql.delete("UsersData",new String[]{"user2","position"});
        sql.delete("UsersData",new String[]{"user1a","position"});
        sql.delete("UsersData",new String[]{"user2a","position"});
        sql.delete("UsersData",new String[]{"user3","qualification"});
        sql.delete("UsersData",new String[]{"user4","qualification"});
        sql.delete("UsersData",new String[]{"user8","qualification"});
        sql.delete("UsersData",new String[]{"user9","qualification"});
        sql.delete("UsersData",new String[]{"user10","qualification"});
        sql.delete("UsersData",new String[]{"user14","managerAppointedByTeamOwner"});
        sql.delete("UsersData",new String[]{"user15","managerAppointedByTeamOwner"});
        sql.delete("UsersData",new String[]{"user16","ownerAppointedByTeamOwner"});
        sql.delete("UsersData",new String[]{"user17","ownerAppointedByTeamOwner"});

        // Delete Users
        sql.delete("Users",new String[]{"user1"});
        sql.delete("Users",new String[]{"user2"});
        sql.delete("Users",new String[]{"user1a"});
        sql.delete("Users",new String[]{"user2a"});
        sql.delete("Users",new String[]{"user3"});
        sql.delete("Users",new String[]{"user4"});
        sql.delete("Users",new String[]{"user8"});
        sql.delete("Users",new String[]{"user9"});
        sql.delete("Users",new String[]{"user10"});
        sql.delete("Users",new String[]{"user11"});
        sql.delete("Users",new String[]{"user12"});
        sql.delete("Users",new String[]{"user13"});
        sql.delete("Users",new String[]{"user14"});
        sql.delete("Users",new String[]{"user15"});
        sql.delete("Users",new String[]{"user16"});
        sql.delete("Users",new String[]{"user17"});

    }
    private static void testUsers() {
        // Insert Users
        sql.insert("Users",new String[]{"user1","1","UnifiedSubscription","@"}); //Player
        sql.insert("Users",new String[]{"user2","1","UnifiedSubscription","@"}); //Player
        sql.insert("Users",new String[]{"user1a","1","UnifiedSubscription","@"}); //Player
        sql.insert("Users",new String[]{"user2a","1","UnifiedSubscription","@"}); //Player
        sql.insert("Users",new String[]{"user3","1","UnifiedSubscription","@"}); //Coach
        sql.insert("Users",new String[]{"user4","1","UnifiedSubscription","@"}); //Coach
        sql.insert("Users",new String[]{"user8","1","Referee","@"});
        sql.insert("Users",new String[]{"user9","1","Referee","@"});
        sql.insert("Users",new String[]{"user10","1","Referee","@"});
        sql.insert("Users",new String[]{"user11","1","SystemAdministrator","@"});
        sql.insert("Users",new String[]{"user12","1","SystemAdministrator","@"});
        sql.insert("Users",new String[]{"user13","1","UnionRepresentative","@"});
        sql.insert("Users",new String[]{"user14","1","UnifiedSubscription","@"}); //TeamManager
        sql.insert("Users",new String[]{"user15","1","UnifiedSubscription","@"}); //TeamManager
        sql.insert("Users",new String[]{"user16","1","UnifiedSubscription","@"}); //TeamOwner
        sql.insert("Users",new String[]{"user17","1","UnifiedSubscription","@"}); //TeamOwner

        // Insert UsersData
        sql.insert("UsersData",new String[]{"user1","position","p1"});
        sql.insert("UsersData",new String[]{"user2","position","p2"});
        sql.insert("UsersData",new String[]{"user1a","position","p1a"});
        sql.insert("UsersData",new String[]{"user2a","position","p2a"});
        sql.insert("UsersData",new String[]{"user3","qualification","q1"});
        sql.insert("UsersData",new String[]{"user4","qualification","q2"});
        sql.insert("UsersData",new String[]{"user8","qualification","q12"});
        sql.insert("UsersData",new String[]{"user9","qualification","q13"});
        sql.insert("UsersData",new String[]{"user10","qualification","q14"});
        sql.insert("UsersData",new String[]{"user14","managerAppointedByTeamOwner","user16"});
        sql.insert("UsersData",new String[]{"user15","managerAppointedByTeamOwner","user16"});
        sql.insert("UsersData",new String[]{"user16","ownerAppointedByTeamOwner","TeamOwner",""});
        sql.insert("UsersData",new String[]{"user17","ownerAppointedByTeamOwner","TeamOwner","user16"});
    }

    private static void testTeams() {
        sql.insert("Team",new String[]{"MaccabiTLV","Teddi","user16","1","0","0","0","0","0","0","0"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user17","TeamOwner"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user3","Coach"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user14","TeamManager"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user1","Player"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user2","Player"});

        sql.insert("Team",new String[]{"MaccabiHaifa","Sami","user17","1","0","0","0","0","0","0","0"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user4","Coach"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user15","TeamManager"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user1a","Player"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user2a","Player"});
    }

    private static void deleteTestTeams() {
        sql.delete("AssetsInTeam",new String[]{"MaccabiHaifa","user4"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiHaifa","user15"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiHaifa","user1a"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiHaifa","user2a"});

        sql.delete("AssetsInTeam",new String[]{"MaccabiTLV","user17"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiTLV","user3"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiTLV","user14"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiTLV","user1"});
        sql.delete("AssetsInTeam",new String[]{"MaccabiTLV","user2"});

        sql.delete("Team",new String[]{"MaccabiHaifa"});
        sql.delete("Team",new String[]{"MaccabiTLV"});

    }

    private static void testLeagues() {
        sql.insert("League",new String[]{"A"});
        sql.insert("Season",new String[]{"A","2020"});
        sql.insert("RefereeInSeason",new String[]{"A","2020","user8"});
        sql.insert("RefereeInSeason",new String[]{"A","2020","user9"});
        sql.insert("League",new String[]{"B"});
        sql.insert("Season",new String[]{"B","2020"});
        sql.insert("RefereeInSeason",new String[]{"B","2020","user9"});
    }
    private static void deleteTestLeagues() {
        sql.delete("RefereeInSeason",new String[]{"A","2020","user8"});
        sql.delete("RefereeInSeason",new String[]{"A","2020","user9"});
        sql.delete("Season",new String[]{"A","2020"});
        sql.delete("League",new String[]{"A"});
        sql.delete("RefereeInSeason",new String[]{"B","2020","user9"});
        sql.delete("Season",new String[]{"B","2020"});
        sql.delete("League",new String[]{"B"});
    }

    private static void testGames() {
        /*
        sql.insert("Game",new String[]{"MaccabiTLV","Teddi","user16","1","0","0","0","0","0","0","0"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user17","TeamOwner"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user3","Coach"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user14","TeamManager"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user1","Player"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiTLV","user2","Player"});

        sql.insert("Team",new String[]{"MaccabiHaifa","Sami","user17","1","0","0","0","0","0","0","0"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user4","Coach"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user15","TeamManager"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user1a","Player"});
        sql.insert("AssetsInTeam",new String[]{"MaccabiHaifa","user2a","Player"});s
         */
    }



}
