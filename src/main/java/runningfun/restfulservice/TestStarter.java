package runningfun.restfulservice;

import java.util.Date;

/**
 * Created by Herschbach.Stefan on 10.08.2015.
 */
public class TestStarter {

    public static void main(String args[]) {
        MongoDBHandler mongoDBHandler = new MongoDBHandler();
        mongoDBHandler.setGasValue(4711, new Date().toString());
        mongoDBHandler.getGasValues();
    }

}
