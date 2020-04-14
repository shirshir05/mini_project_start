package DB_Layer;

public class unionFinanceSystem implements apiConnections {
    @Override
    public boolean initConnection() {
        return true;
    }

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public String getUpdatedData() {
        return "";
    }

    @Override
    public boolean setNewData(Object data) {
        return true;
    }
}
