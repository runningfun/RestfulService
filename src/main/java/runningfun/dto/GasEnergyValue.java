package runningfun.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Herschbach.Stefan on 02.05.2015.
 */
@XmlRootElement
public class GasEnergyValue {

    private String meterReadingDate;
    private int meterReadingValue;

    public GasEnergyValue() {
    }

    public GasEnergyValue(String meterReadingDate, int meterReadingValue) {
        this.meterReadingDate = meterReadingDate;
        this.meterReadingValue = meterReadingValue;
    }

    public String getMeterReadingDate() {
        return meterReadingDate;
    }

    public void setMeterReadingDate(String meterReadingDate) {
        this.meterReadingDate = meterReadingDate;
    }

    public int getMeterReadingValue() {
        return meterReadingValue;
    }

    public void setMeterReadingValue(int meterReadingValue) {
        this.meterReadingValue = meterReadingValue;
    }
}
