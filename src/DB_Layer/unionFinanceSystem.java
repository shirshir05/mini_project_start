package DB_Layer;

public class unionFinanceSystem implements unionFinanceSystemInterface {

    public unionFinanceSystem(String URL){
        // connect URL
    }

    public double getTaxRate (double revenueAmount){
        return 0;
    }

    @Override
    public boolean checkConnection() {
        return true;
    }

}
