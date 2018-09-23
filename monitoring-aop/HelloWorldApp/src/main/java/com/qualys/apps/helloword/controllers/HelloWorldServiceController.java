package com.qualys.apps.helloword.controllers;

import com.qualys.apps.helloword.exceptions.BackendServiceException;
import com.qualys.common.monitoring.Monitored;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloWorldServiceController {
    @Monitored(displayName="HELLO_WORD_METHOD")
    @RequestMapping(value = "/helloWorld")
    public String method1()
            throws BackendServiceException {
        return "hello";
    }

    @Monitored(displayName="HELLO_WORD_METHOD_2")
    @RequestMapping(value = "/exceptionDemo")
    public String method2()
            throws BackendServiceException{
        throw new BackendServiceException(1235, "Generated Exception");
    }
}
