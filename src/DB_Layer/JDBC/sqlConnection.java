package DB_Layer.JDBC;

import BusinessService.Enum.ActionStatus;
import DB_Layer.interfaceDB;

import java.sql.ResultSet;

public class sqlConnection implements interfaceDB {

    String databaseName;
    DatabaseManager databaseManager;

    @Override
    public int insert(String table,String[] values){
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        ResultSetPrinter.printResultSet(resultSet);
        return 0;
    }

    @Override
    public int update(String table, String[] key, String[] values) {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        ResultSetPrinter.printResultSet(resultSet);
        return 0;
    }

    @Override
    public ResultSet find(String table, String key) {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        ResultSetPrinter.printResultSet(resultSet);
        return resultSet;
    }

    @Override
    public int delete(String table, String key) {
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        ResultSetPrinter.printResultSet(resultSet);
        return 0;
    }

    public sqlConnection() {
        databaseName = "FootBallDB";
        databaseManager = new DatabaseManagerMSSQLServer(databaseName);
        databaseManager.startConnection();
        ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        ResultSetPrinter.printResultSet(resultSet);
    }

    public ActionStatus closeConnection(){
        databaseManager.closeConnection();
        return new ActionStatus(true, "DB closed");
    }
}
