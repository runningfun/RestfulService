package runningfun.restfulservice;

import runningfun.dto.GasEnergyValue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Herschbach.Stefan on 02.05.2015.
 */
@Path("energycounter")
public class EnergyCounters {

    @Path("gas")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<GasEnergyValue> getGasEnergyValues() {
        System.out.println("getGasEnergyValues");
        List<GasEnergyValue> gasEnergyValueList = new ArrayList<GasEnergyValue>();
        GasEnergyValue gasEnergyValue1 = new GasEnergyValue(new Date(), 850l);
        GasEnergyValue gasEnergyValue2 = new GasEnergyValue(new Date(), 1000l);
        GasEnergyValue gasEnergyValue3 = new GasEnergyValue(new Date(), 1600l);
        gasEnergyValueList.add(gasEnergyValue1);
        gasEnergyValueList.add(gasEnergyValue2);
        gasEnergyValueList.add(gasEnergyValue3);

        return gasEnergyValueList;
    }

    @GET
    @Path("test")
    public String test() {
        return "test";
    }

}
