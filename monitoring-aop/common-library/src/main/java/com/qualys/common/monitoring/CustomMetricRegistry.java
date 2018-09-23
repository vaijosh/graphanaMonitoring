package com.qualys.common.monitoring;

import com.codahale.metrics.*;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CustomMetricRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMetricRegistry.class);

    private final String prefix;
    private final MetricRegistry metricRegistry;

    public static final String REQUESTS = "requests";
    public static final String ERRORS = "errors";
    public static final String DURATION = "duration";

    public CustomMetricRegistry(String prefix, final MetricRegistry metricRegistry){
        this.prefix = prefix;
        this.metricRegistry = metricRegistry;
    }

    public String getPrefix()
    {
        return this.prefix;
    }

    public MetricRegistry getMetricRegistry(){
        return this.metricRegistry;
    }

    public Counter counter(final String name){
        return metricRegistry.counter(MetricRegistry.name(prefix, name));
    }

    public Histogram histogram(final String name){
        return metricRegistry.histogram(MetricRegistry.name(prefix, name));
    }

    public Meter meter(final String name){
        return metricRegistry.meter(MetricRegistry.name(prefix, name));
    }

    public Timer timer(final String name){
        return metricRegistry.timer(MetricRegistry.name(prefix, name));
    }

    public void recordTime(final String name, long elapsed){
        metricRegistry.timer(MetricRegistry.name(prefix, name)).update(elapsed, TimeUnit.NANOSECONDS);
    }
    public void registerGCMetricSet() {
        registerAll("gc", new GarbageCollectorMetricSet());
    }
    public void registerMemoryUsageGuageSet() {
        registerAll("memory", new MemoryUsageGaugeSet());
    }
    public void registerThreadStatesGuageSet() {
        registerAll("threads", new ThreadStatesGaugeSet());
    }

    private void registerAll(String name, MetricSet metricSet){
        for(Map.Entry<String, Metric> m : metricSet.getMetrics().entrySet()) {
            if(m.getValue() instanceof MetricSet) {
                registerAll(prefix + "." + name + "." + m.getKey(), (MetricSet) m.getValue());
            }else{
                metricRegistry.register(prefix + "." + name + "." + m.getKey(), m.getValue());
            }
        }
    }
}