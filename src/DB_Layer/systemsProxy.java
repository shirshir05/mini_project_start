package DB_Layer;

public class systemsProxy {
    stateTaxSystem taxSystem;
    unionFinanceSystem unuionFinance;

    public systemsProxy(stateTaxSystem tax, unionFinanceSystem finance){
        taxSystem = tax;
        unuionFinance = finance;
    }

    public void changeTaxSystem(stateTaxSystem tax){
        taxSystem = tax;
    }

    public void changeFinanceSystem(unionFinanceSystem finance){
        unuionFinance = finance;
    }

}
