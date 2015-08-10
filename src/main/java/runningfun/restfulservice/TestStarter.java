package runningfun.restfulservice;

/**
 * Created by Herschbach.Stefan on 10.08.2015.
 */
public class TestStarter {

    public static void main(String args[]) {
        ReadValuesFromMongoDB readValuesFromMongoDB = new ReadValuesFromMongoDB();
        readValuesFromMongoDB.getValues();
    }

}
