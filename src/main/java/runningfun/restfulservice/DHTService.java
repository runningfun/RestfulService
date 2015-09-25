package runningfun.restfulservice;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by Herschbach.Stefan on 15.09.2015.
 */
@Path("dhtservice")
public class DHTService {


    @POST
    @Path("temperature")
    public Response createTemperatureValue(@QueryParam("value") double temperatureValue) {
        System.out.println("temperatureValue received " + temperatureValue);
        new MongoDBHandler().createTemperatureValue(temperatureValue);
        return Response.status(201).build();
    }

    @POST
    @Path("humidity")
    public Response createHumidityValue(@QueryParam("value") double humidityValue) {
        System.out.println("humidityValue received " + humidityValue);
        new MongoDBHandler().createHumidityValue(humidityValue);
        return Response.status(201).build();
    }

    @GET
    public String test() {
        return "Ok";
    }


}
