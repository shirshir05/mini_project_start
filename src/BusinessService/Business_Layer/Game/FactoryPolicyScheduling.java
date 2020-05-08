package BusinessService.Business_Layer.Game;

public class FactoryPolicyScheduling {


    /**
     * The function returns the object that displays the corresponding policy for the resulting argument
     * @param numberPolicy  -
     * @return -
     */
    public SchedulingGame definePolicy(int numberPolicy){
        if(numberPolicy == 1){
            return new AllForAll();

        }else if(numberPolicy == 2){
            return new AllForAllTwo();
        }
        return null;
    }



}
