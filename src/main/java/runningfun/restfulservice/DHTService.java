package runningfun.restfulservice;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import runningfun.metrics.MetricsListener;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Herschbach.Stefan on 15.09.2015.
 */
@Path("dhtservice")
public class DHTService {

    @GET
    @Path("temperature")
    @Produces({MediaType.APPLICATION_JSON})
    public FindIterable<Document> getTemperatures() {
        System.out.println("getGasEnergyValuesFromMongo");
        return new MongoDBHandler().getTemperatureValues();
    }

    @GET
    @Path("temperature/{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public FindIterable<Document> getTemperatures(@PathParam("number") int number) {
        System.out.println("get last " + number + " temperature values");
        return new MongoDBHandler().getTemperatureValues(number);
    }

    @POST
    @Path("temperature")
    public Response createTemperatureValue(@QueryParam("value") double temperatureValue) {
        System.out.println("temperatureValue received " + temperatureValue);
        MetricsListener.setLastTemperaturValue(temperatureValue);
        new MongoDBHandler().createTemperatureValue(temperatureValue);
        return Response.status(201).build();
    }

    @POST
    @Path("humidity")
    public Response createHumidityValue(@QueryParam("value") double humidityValue) {
        System.out.println("humidityValue received " + humidityValue);
        MetricsListener.setLastHumidityValue(humidityValue);
        new MongoDBHandler().createHumidityValue(humidityValue);
        return Response.status(201).build();
    }

    @GET
    public String test() {
        return "Ok";
    }


}
