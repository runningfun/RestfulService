package runningfun.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


/**
 * Created by Herschbach.Stefan on 09.05.2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GasEnergyValueList {

    @XmlElement
    private List<GasEnergyValue> gasEnergyValueList;


    public GasEnergyValueList() {
    }

    public List<GasEnergyValue> getGasEnergyValueList() {
        return gasEnergyValueList;
    }

    public void setGasEnergyValueList(List<GasEnergyValue> gasEnergyValueList) {
        this.gasEnergyValueList = gasEnergyValueList;
    }


}
