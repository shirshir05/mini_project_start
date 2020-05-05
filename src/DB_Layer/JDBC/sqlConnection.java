package DB_Layer.JDBC;

import java.sql.ResultSet;

public class sqlConnection {

    public static void main(String[] args) {
        String databaseName = "FootBallDB";
        DatabaseManager databaseManager = new DatabaseManagerMSSQLServer(databaseName);
        databaseManager.startConnection();
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        ResultSetPrinter.printResultSet(resultSet);
        databaseManager.closeConnection();
    }
}
