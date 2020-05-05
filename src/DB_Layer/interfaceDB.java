package DB_Layer;

import BusinessService.Enum.ActionStatus;

import java.sql.ResultSet;


public interface interfaceDB {

    public ResultSet insert(String table,String key,String[] values);

    public ResultSet update(String table,String key,String values);

    public ResultSet find(String table,String key);
}
