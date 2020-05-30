package DB_Layer.JDBC;

import Business_Layer.Enum.ActionStatus;
import DB_Layer.interfaceDB;
import DB_Layer.logger;

import java.io.*;
import java.sql.*;
import java.util.HashMap;

public class sqlConnection implements interfaceDB {

    String databaseName;
    DatabaseManager databaseManager;
    HashMap<String,String[]> keys;

    @Override
    public int insert(String table,Object[] values){
        String query = prepQuery(table,values);
        int ans = execute(query);
        return ans;
    }

    public int insertBlob(String table,String key, Object value){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] valueAsBytes = baos.toByteArray();
            PreparedStatement pstmt = databaseManager.conn.prepareStatement("use FootBallDB INSERT INTO "+table+" ([key],[value]) VALUES('"+key +"',?)");
            ByteArrayInputStream bais = new ByteArrayInputStream(valueAsBytes);
            pstmt.setBinaryStream(1, bais, valueAsBytes.length);
            int ans = pstmt.executeUpdate();
            pstmt.close();
            return ans;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public Object getBlob(String table,String key){
        try{
            Object ans = null;
            PreparedStatement stmt = databaseManager.conn.prepareStatement("use FootBallDB SELECT * FROM "+table+" WHERE [key] = '"+key+"'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                byte[] st = (byte[]) rs.getObject("value");
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                ans =  ois.readObject();
            }
            stmt.close();
            rs.close();
            return ans;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateBlob(String table, String key, Object value) {
        delete(table,new String[]{key});
        return insertBlob(table,key,value);
        /*
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            byte[] valueAsBytes = baos.toByteArray();
            PreparedStatement pstmt = databaseManager.conn.prepareStatement("use FootBallDB UPDATE "+table+" WHERE [key] = '"+key+"' SET ([value]) VALUES(?)");
            ByteArrayInputStream bais = new ByteArrayInputStream(valueAsBytes);
            pstmt.setBinaryStream(1, bais, valueAsBytes.length);
            int ans = pstmt.executeUpdate();
            pstmt.close();
            return ans;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
         */
    }

    @Override
    public int update(String table, String[] key, String column, Object value) {
        int i = 1;
        int length = keys.get(table).length-1;
        String query =  "use FootBallDB UPDATE "+ table + " SET " + column + " = '" + value +"' WHERE [" + keys.get(table)[0] + "] = " + "'"+key[0]+"'";
        for (int k=0; k<length; k++){
            query += " AND [" + keys.get(table)[i] + "] = " + "'"+key[i]+"'";
            i++;
        }
        int ans = execute(query);
        return ans;
    }

    @Override
    public ResultSet findByKey(String table, String[] key) {

        String query =  "use FootBallDB SELECT * FROM "+ table;
        if (key != null) {
            query += " WHERE [" + keys.get(table)[0] + "] = " + "'"+key[0]+"'";
            int length = key.length-1;
            for (int k = 0; k < length ; k++) {
                query += " AND [" + keys.get(table)[k + 1] + "] = " + "'" + key[k + 1] + "'";
            }
        }
        System.out.println(query);
        PreparedStatement sqlStatement = null;
        ResultSet result = null;
        try{
            if(this.databaseManager == null){
                connect();
            }
            if (this.databaseManager.conn==null){
                connect();
            }
            if( this.databaseManager.conn.isClosed()){
                connect();
            }
            sqlStatement = databaseManager.conn.prepareStatement(query);
            result = sqlStatement.executeQuery();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResultSet findByValue(String table, String column,String value) {

        String query =  "use FootBallDB SELECT * FROM "+ table + " WHERE [" + column + "] = " + "'"+value+"'";

        PreparedStatement sqlStatement = null;
        ResultSet result = null;
        try{
            if(this.databaseManager == null){
                connect();
            }
            if( this.databaseManager.conn==null){
                connect();
            }
            if( this.databaseManager.conn.isClosed()){
                connect();
            }
            sqlStatement = databaseManager.conn.prepareStatement(query);
            result = sqlStatement.executeQuery();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String table, String[] key) {
        int i = 1;
        int length = keys.get(table).length-1;
        String query =  "use FootBallDB DELETE FROM "+ table + " WHERE [" + keys.get(table)[0] + "] = " + "'"+key[0]+"'";
        for (int k=0; k<length; k++){
            query += " AND [" + keys.get(table)[i] + "] = " + "'"+key[i]+"'";
            i++;
        }
        int ans = execute(query);
        return ans;
    }


    private int execute(String query){
        PreparedStatement sqlStatement = null;
        int rowsEdited = -1;
        try{
            if(this.databaseManager == null){
                connect();
            }
            if(this.databaseManager.conn == null){
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

    private String prepQuery(String table,Object[] values){
        String query = "use FootBallDB INSERT INTO " + "["+table+"]";;

        if(table.equals("Users")){
            query += " ([userName], [userPassword],[userRole],[email],[alerts]) VALUES ('"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+values[4]+"')";
        }
        else if(table.equals("Team")){
            query += " ([teamName], [mainFiled] ,[ownerName] ,[teamStatus], [totalScore], [numOfGames], [wins], [drawns], [loses], [goalsScored], [goalesGoten]) VALUES ('"
                    +values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+values[4]+"','"+values[5]+"','"+values[6]+"','"+
                    values[7]+"','"+values[8]+"','"+values[9]+"','"+values[10]+"')";
        }
        else if(table.equals("AssetsInTeam")){
            query += " ([teamName], [assetName],[assetRole]) VALUES ('"+values[0]+"','"+values[1]+"','"+values[2]+"')";
        }
        else if(table.equals("Game")){
            query += " ([gameID], [filed] ,[gameDate] ,[homeTeam], [guestTeam], [leagueName], [seasonYear], [headReferee], [linesmanOneReferee], [linesmanTwoReferee]) VALUES ('"
                    +values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"','"+values[4]+"','"+values[5]+"','"+values[6]+"','"+
                    values[7]+"','"+values[8]+"','"+values[9]+"')";
        }
        else if(table.equals("EventInGame")){
            query += " ([gameID],  [eventTime], [playerName],  [eventType]) VALUES ('"+
                    values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"')";
        }
        else if(table.equals("League")){
            query += " ([leagueName]) VALUES ('"+
                    values[0]+"')";
        }
        else if(table.equals("Season")){
            query += " ([leagueName], [seasonYear]) VALUES ('"+
                    values[0]+"','"+values[1]+"')";

        }
        else if(table.equals("RefereeInSeason")){
            query += " ( [leagueName], [seasonYear], [refereeName]) VALUES ('"+
                    values[0]+"','"+values[1]+"','"+values[2]+"')";
        }
        else if(table.equals("UsersData")){
            query += " ([userName], [dataType], [dataValue]) VALUES ('"+
                    values[0]+"','"+values[1]+"','"+values[2]+"')";
        }else if(table.equals("logs")){
            query += " ([key]) VALUES ('"+values[0]+"')";
        }else if(table.equals("errors")){
            query += " ([key]) VALUES ('"+values[0]+"')";
        }
        return query;
    }

    public sqlConnection() {
        databaseName = "FootBallDB";
        databaseManager = new DatabaseManagerMSSQLServer(databaseName);

        keys = new HashMap<>();
        keys.put("Users", new String[]{"userName"});
        keys.put("Team",new String[]{"key"});
        keys.put("AssetsInTeam",new String[]{"teamName","assetName"});
        keys.put("Game",new String[]{"gameID"});
        keys.put("EventInGame",new String[]{"gameID","eventTime"});
        keys.put("League",new String[]{"leagueName"});
        keys.put("Season",new String[]{"leagueName","seasonYear"});
        keys.put("RefereeInSeason",new String[]{"leagueName","seasonYear","refereeName"});
        keys.put("UsersData",new String[]{"userName","dataType"});
        keys.put("Blobs",new String[]{"key"});
        connect();


        //ResultSet resultSet = databaseManager.executeQuerySelect("Select * From Users");
        //ResultSetPrinter.printResultSet(resultSet);
    }

    @Override
    public ActionStatus connect(){
        return databaseManager.startConnection();
    }

    public ActionStatus closeConnection(){
        databaseManager.closeConnection();
        return new ActionStatus(true, "DB closed");
    }

    public Object getConn(){
        return this.databaseManager.conn;
    }

    public void resetDB(){
        String s = "";
        String all = "";
        try
        {
            FileReader fr = new FileReader(new File("lib/DBwithObj.sql"));
            BufferedReader br = new BufferedReader(fr);
            while((s = br.readLine()) != null) {
                all += s;
            }
            br.close();
            all = all.trim();
            Statement st = databaseManager.conn.createStatement();
            st.executeUpdate(all);
            System.out.println(all);
            /*
            for(int i = 0; i<inst.length; i++)
            {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!inst[i].trim().equals(""))
                {

                }
            }

             */
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(all);
        }
    }

    public void startLastDB(){
        String s = null;
        StringBuffer sb = new StringBuffer();
        try
        {
            FileReader fr = new FileReader(new File("lib/scriptOB.sql"));
            BufferedReader br = new BufferedReader(fr);
            while((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();

            String[] inst = sb.toString().split("[/]\\*.+\\*[/]");
            Statement st = databaseManager.conn.createStatement();
            for(int i = 0; i<inst.length; i++)
            {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!inst[i].trim().equals(""))
                {
                    st.executeUpdate(inst[i]);
                    System.out.println(">>"+inst[i]);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }
    }
    public boolean checkExistingDB(){
        try {
            if (this.databaseManager == null) {
                connect();
            }
            if (this.databaseManager.conn.isClosed()) {
                connect();
            }
            PreparedStatement sqlStatement = databaseManager.conn.prepareStatement("SELECT name FROM master.sys.databases WHERE name = 'FootBallDB'");
            ResultSet rs = sqlStatement.executeQuery();
            if (rs.next()) {
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }


    public static void main(String[] args) {
        sqlConnection sc = new sqlConnection();
        boolean bol = sc.checkExistingDB();
        logger.getInstance().log("this is me ahahahahahhahaha");
        System.out.println(bol);
    }
}
