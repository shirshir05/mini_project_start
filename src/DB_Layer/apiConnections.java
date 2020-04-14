package DB_Layer;

public interface apiConnections {

    public boolean initConnection();
    public boolean checkConnection();
    public String getUpdatedData();
    public boolean setNewData(Object data);
}
