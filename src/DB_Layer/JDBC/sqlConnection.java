package DB_Layer.JDBC;

import BusinessService.Enum.ActionStatus;
import DB_Layer.interfaceDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class sqlConnection implements interfaceDB {

    String databaseName;
    DatabaseManager databaseManager;
    HashMap<String,String> keys;

    @Override
    public int insert(String table,String[] values){
        String query = prepQuery(table,values);
        int ans = execute(query);
        return ans;
    }

    @Override
    public int update(String table, String[] key, String[] values) {
        String query =  "use FootBallDB UPDATE "+ table + " set " +values[0] + " = " + values[1] +" where " + keys.get(table) + " = " + key[0];
        if(key.length==2){
            query += " and " + keys.get(table+"2") + " = " + key[1];
        }
        int ans = execute(query);
        return ans;
    }

    @Override
    public ResultSet find(String table, String key) {
        //todo - fix query
        String query =  "use FootBallDB SELECT * from "+ table + " where " + key + " = " + key;
        PreparedStatement sqlStatement = null;
        ResultSet result = null;
        try{
            if(this.databaseManager == null){
                connect();
            }
            if( this.databaseManager.conn.isClosed()){
                connect();
            }
            sqlStatement = databaseManager.conn.prepareStatement(query);
            result = sqlStatement.executeQuery();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(sqlStatement != null){
                    sqlStatement.close();
                }
            }catch (SQLException e3) {
                e3.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int delete(String table, String key) {
        String query =  "use FootBallDB DELETE from "+ table + " where " + keys.get(table) + " = " + key;
        int ans = execute(query);
        return ans;
    }

    public int execute(String query){
        PreparedStatement sqlStatement = null;
        int rowsEdited = -1;
        try{
            if(this.databaseManager == null){
                connect();
            }
            if( this.databaseManager.conn.isClosed()){
                connect();
            }
            sqlStatement = databaseManager.conn.prepareStatement(query);
            rowsEdited = sqlStatement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(sqlStatement != null){
                    sqlStatement.close();
                }
            }catch (SQLException e3) {
                e3.printStackTrace();
            }
        }
        return rowsEdited;
    }

    private String prepQuery(String table,String[] values){
        //TODO - complete!
        String query = "use FootBallDB INSERT INTO " + table;;

        if(table.equals("Users")){
            query = " ([userName], [userPassword],[userRole],[email]) VALUES ('"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"')";
        }
        else if(table.equals("Team")){

        }
        else if(table.equals("AssetsInTeam")){

        }
        else if(table.equals("Game")){

        }
        else if(table.equals("EventInGame")){

        }
        else if(table.equals("League")){

        }
        else if(table.equals("Season")){

        }
        else if(table.equals("RefereeInSeason")){

        }
        return query;
    }

    public sqlConnection() {
        databaseName = "FootBallDB";
        databaseManager = new DatabaseManagerMSSQLServer(databaseName);
        connect();
        keys = new HashMap<>();
        //todo - complete
        keys.put("Users","userName");
        keys.put("Team","");
        keys.put("AssetsInTeam","");
        keys.put("AssetsInTeam2","");
        keys.put("Game","");
        keys.put("EventInGame","");
        keys.put("EventInGame2","");
        keys.put("League","");
        keys.put("Season","");
        keys.put("Season2","");
        keys.put("RefereeInSeason","");
        keys.put("RefereeInSeason2","");

        //ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        //ResultSetPrinter.printResultSet(resultSet);
    }

    public void connect(){
        databaseManager.startConnection();
    }

    public ActionStatus closeConnection(){
        databaseManager.closeConnection();
        return new ActionStatus(true, "DB closed");
    }
}
