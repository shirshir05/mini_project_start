package DB_Layer.JDBC;

import BusinessService.Enum.ActionStatus;

public class DatabaseManagerMSSQLServer extends DatabaseManager {

    public DatabaseManagerMSSQLServer(String databaseName) {
        super("jdbc:sqlserver://localhost;Instance=ISE-STR10;integratedSecurity=true", databaseName, "", "");
        //super("jdbc:sqlserver://localhost;integratedSecurity=true", databaseName, "", "");
    }

    public DatabaseManagerMSSQLServer(String connectionString, String databaseName) {
        super(connectionString, databaseName, "", "");
    }

    public DatabaseManagerMSSQLServer(String connectionString, String databaseName, String username, String password) {
        super(connectionString, databaseName, username, password);
    }


    @Override
    public ActionStatus startConnection() {
        ActionStatus ac = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ac = super.startConnection();
        } catch (Exception e) {
            //System.out.println(String.format("Error starting connection to database '%s'", databaseName));
            //System.out.println(e.getMessage());
            ac = new ActionStatus(false,String.format("Error starting connection to database '%s'", databaseName));
        }
        return ac;
    }

}
