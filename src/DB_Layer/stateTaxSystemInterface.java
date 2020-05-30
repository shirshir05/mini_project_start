package DB_Layer;

public interface stateTaxSystemInterface {
    public boolean addPayment(String teamName, String date, double amount);

    public boolean checkConnection();
}
