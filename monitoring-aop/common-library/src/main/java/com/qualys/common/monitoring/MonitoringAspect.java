package com.qualys.common.monitoring;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class MonitoringAspect {

    @Autowired
    private CustomMetricRegistry metricRegistry;

    @Pointcut("@annotation(monitored)")
    public void monitoredMethod(Monitored monitored){}

    @Around("monitoredMethod(monitored)")
    public Object collectMetrics(ProceedingJoinPoint pjp, Monitored monitored) throws Throwable {
        Object targetObject;
        final String methodName = pjp.getSignature().getName();

        //Metric DisplayName
        String displayName = monitored.displayName();

        if("".equals(displayName)) {
            displayName = methodName;
        }
        // start  timer
        final Timer.Context timerContext = metricRegistry.timer(MetricRegistry.name(displayName, CustomMetricRegistry.DURATION)).time();

        //increment total requests meter
        metricRegistry.meter(MetricRegistry.name(displayName, CustomMetricRegistry.REQUESTS)).mark();

        try {
            //TODO Handle void object
            targetObject = pjp.proceed();
        } catch( Throwable e) {
            //Record Exception metric
            metricRegistry.meter(MetricRegistry.name(displayName, metricRegistry.ERRORS)).mark();
            //Exception specific metrics if required.
            throw e;
        }
        finally {
            //Record Timer metric
            final long elapsed = timerContext.stop();
            metricRegistry.recordTime(MetricRegistry.name(displayName, metricRegistry.DURATION), elapsed);
        }
        return targetObject;
    }
}