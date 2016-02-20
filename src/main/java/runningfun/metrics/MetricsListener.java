package runningfun.metrics;

import com.codahale.metrics.*;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Herschbach.Stefan on 20.02.2016.
 */
public class MetricsListener implements ServletContextListener {
    public static MetricRegistry metricsRegistry = new MetricRegistry();
    //    public static Meter temperatureMeter = metricsRegistry.meter("temperature");
//    public static Meter humidityMeter = metricsRegistry.meter("humidity");
    static double lastTemperaturValue;
    static double lastHumidityValue;
    private final Logger logger = LoggerFactory.getLogger(MetricsListener.class);
    public Graphite graphite = new Graphite(new InetSocketAddress("192.168.188.45", 2003));
    //public   Graphite graphite = new Graphite(new InetSocketAddress("192.168.188.51", 2003));
    //   public ConsoleReporter consoleReporter;
    public GraphiteReporter graphiteReporter;
    Gauge tempGauge = MetricsListener.metricsRegistry.register("temperature", new Gauge() {
        @Override
        public Object getValue() {
            return lastTemperaturValue;
        }
    });
    Gauge humidityGauge = MetricsListener.metricsRegistry.register("humidity", new Gauge() {
        @Override
        public Object getValue() {
            return lastHumidityValue;
        }
    });
    private String metricsPrefix = "runningfun.sensors";

    public static void setLastTemperaturValue(double lastTemperaturValue) {
        MetricsListener.lastTemperaturValue = lastTemperaturValue;
    }

    public static void setLastHumidityValue(double lastHumidityValue) {
        MetricsListener.lastHumidityValue = lastHumidityValue;
    }

    void init() {
//        startConsoleReport();
        startGraphiteReporter();
        registerAll(".jvm.tomcat.threads", new ThreadStatesGaugeSet(), metricsRegistry);
        registerAll(".jvm.tomcat.memory", new MemoryUsageGaugeSet(), metricsRegistry);
    }

    void startGraphiteReporter() {
        graphiteReporter = GraphiteReporter.forRegistry(metricsRegistry)
                .prefixedWith(metricsPrefix)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(graphite);
        graphiteReporter.start(1, TimeUnit.MINUTES);
    }

    void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
        for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
            if (entry.getValue() instanceof MetricSet) {
                registerAll(prefix + "." + entry.getKey(), (MetricSet) entry.getValue(), registry);
            } else {
                registry.register(prefix + "." + entry.getKey(), entry.getValue());
            }
        }
    }

    void destroy() {
        synchronized (metricsRegistry) {
            Map<String, Metric> allMetrics = metricsRegistry.getMetrics();
            Set<String> metricNames = allMetrics.keySet();
            for (String metricName : metricNames) {
                metricsRegistry.remove(metricName);
            }
            logger.info("metrics removed from metrics registry");
        }
//        consoleReporter.stop();
        graphiteReporter.stop();
        logger.info("metrics reporter stopped");
        try {
            graphite.close();
        } catch (IOException e) {
            logger.error("Error closing graphite interface {}", e);
        }
//        consoleReporter=null;
        graphiteReporter = null;
        graphite = null;
//        temperatureMeter = null;
//        humidityMeter = null;
        metricsRegistry = null;


    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("contextInitialized");
        init();
        logger.info("metrics handling started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        destroy();
        logger.info("contextDestroyed");
    }
}
