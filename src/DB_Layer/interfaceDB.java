package DB_Layer;

import Business_Layer.Enum.ActionStatus;

import java.sql.ResultSet;


public interface interfaceDB {

    public int insert(String table,Object[] values);

    public int update(String table,String[] key,String fieldName, Object value);

    public ResultSet findByKey(String table,String[] key);

    public ResultSet findByValue(String table, String column,String value);

    public int delete(String table,String[] key);

    public ActionStatus connect();

}
