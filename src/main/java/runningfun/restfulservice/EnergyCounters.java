package runningfun.restfulservice;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import runningfun.dto.GasEnergyValue;
import runningfun.dto.GasEnergyValueList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Herschbach.Stefan on 02.05.2015.
 */
@Path("energycounter")
public class EnergyCounters {

    @Path("gasfrommongo")
    @GET
    @Produces({MediaType.APPLICATION_JSON}) //MediaType.APPLICATION_XML,
    public FindIterable<Document> getGasEnergyValuesFromMongo() {
        System.out.println("getGasEnergyValuesFromMongo");
        return new MongoDBHandler().getGasValues();
    }

    @Path("gas")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public GasEnergyValueList getGasValues() {
        FindIterable<Document> documents = new MongoDBHandler().getGasValues();
        GasEnergyValueList gasEnergyValueList = new GasEnergyValueList();
        List<GasEnergyValue> gasEnergyValues = new ArrayList<GasEnergyValue>();
        for (Document document : documents) {
            GasEnergyValue gasEnergyValue = new GasEnergyValue();
            gasEnergyValue.setMeterReadingDate((String) document.getString("date"));
            gasEnergyValue.setMeterReadingValue((Integer) document.getInteger("value"));
            gasEnergyValues.add(gasEnergyValue);
        }
        gasEnergyValueList.setGasEnergyValueList(gasEnergyValues);
        return gasEnergyValueList;
    }

    @Path("gas")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response setNewGasValue(GasEnergyValue gasEnergyValue) {
        System.out.println("setNewGasValue " + gasEnergyValue);
        new MongoDBHandler().setGasValue(gasEnergyValue.getMeterReadingValue(), gasEnergyValue.getMeterReadingDate());
        return Response.status(201).build();
    }

    @Path("gas/{deleteDate}")
    @DELETE
    public Response deleteGasEntry(@PathParam("deleteDate") String deleteDate) {
        System.out.println("delete gas entry for date " + deleteDate);
        DeleteResult deleteResult = new MongoDBHandler().removeGasEntry(deleteDate);
        if (deleteResult.getDeletedCount() == 0) {
            return Response.status(304).build();
        } else {
            return Response.status(202).build();
        }
    }

    @Path("addgastestvalue")
    @GET
    public Response setNewGasTestValue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String sdfDate = simpleDateFormat.format(date);
        GasEnergyValue gasEnergyValue = new GasEnergyValue(sdfDate, 6543);
        new MongoDBHandler().setGasValue(gasEnergyValue.getMeterReadingValue(), gasEnergyValue.getMeterReadingDate());
        return Response.status(201).build();
    }


    @GET
    @Path("test")
    public String test() {
        return "test";
    }

}
