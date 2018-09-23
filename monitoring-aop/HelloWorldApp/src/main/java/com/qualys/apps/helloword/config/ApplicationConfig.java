package com.qualys.apps.helloword.config;

import com.qualys.common.monitoring.CustomMetricRegistry;
import com.qualys.common.monitoring.MetricsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.qualys.common.monitoring")
public class ApplicationConfig {
    private CustomMetricRegistry customMetricRegistry;

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public CustomMetricRegistry customMetricRegistry(final MetricsConfig metricsConfig) {
        customMetricRegistry = new CustomMetricRegistry(appName, metricsConfig.getMetricRegistry());
        customMetricRegistry.registerGCMetricSet();
        customMetricRegistry.registerMemoryUsageGuageSet();
        customMetricRegistry.registerThreadStatesGuageSet();
        return customMetricRegistry;
    }
}
