package com.qualys.apps.helloword.controllers;

import com.qualys.apps.helloword.exceptions.BackendServiceException;
import com.qualys.common.monitoring.CustomMetricRegistry;
import com.qualys.common.monitoring.Metric;
import com.qualys.common.monitoring.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloWorldServiceController {
    @Autowired
    CustomMetricRegistry customMetricRegistry;

    @Monitored(eventClassName = "HelloWorldController", displayName="HELLO_WORD_METHOD")
    @RequestMapping(value = "/helloWorld")
    public String method1()
            throws BackendServiceException {
        return "hello";
    }

    @Monitored(eventClassName = "HelloWorldController",displayName="HELLO_WORD_METHOD_2")
    @RequestMapping(value = "/exceptionDemo")
    public String method2()
            throws BackendServiceException{
        throw new BackendServiceException(1235, "Generated Exception");
    }


    @Monitored(eventClassName = "HelloWorldController",displayName="METHOD3_INTERNAL_RETRY_COUNT")
    @RequestMapping(value = "/retryDemo")
    public String method3()
            throws BackendServiceException, InterruptedException {
        int retryCount = 3;

        for (int i = 0; i < retryCount; i++) {
            Thread.sleep(2000);
        }
        customMetricRegistry.markMeter(new Metric(this.getClass().getName(), "RETRY_DEMO"), retryCount);
        return "successfullyRetried";
    }
}
