package DB_Layer;

import BusinessService.Enum.ActionStatus;

import java.sql.ResultSet;


public interface interfaceDB {

    public int insert(String table,String[] values);

    public int update(String table,String[] key,String[] values);

    public ResultSet find(String table,String key);

    public int delete(String table,String key);
}
