package runningfun.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Herschbach.Stefan on 02.05.2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GasEnergyValue {

    @XmlElement
    private Date meterReadingDate;
    @XmlElement
    private long counterReading;

    public GasEnergyValue() {
    }

    public GasEnergyValue(Date meterReadingDate, long counterReading) {
        this.meterReadingDate = meterReadingDate;
        this.counterReading = counterReading;
    }

    public Date getMeterReadingDate() {
        return meterReadingDate;
    }

    public void setMeterReadingDate(Date meterReadingDate) {
        this.meterReadingDate = meterReadingDate;
    }

    public long getCounterReading() {
        return counterReading;
    }

    public void setCounterReading(long counterReading) {
        this.counterReading = counterReading;
    }
}
