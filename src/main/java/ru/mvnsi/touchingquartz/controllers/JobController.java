package ru.mvnsi.touchingquartz.controllers;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mvnsi.touchingquartz.services.SomeService;

@Controller
public class JobController {

    @Autowired
    SomeService someService;

    @GetMapping("stopJob")
    public ResponseEntity<Boolean> stopJob(@RequestParam(value = "jobName") String jobName) throws SchedulerException {
        Boolean jobStopResult = someService.stopJob(jobName);
        return new ResponseEntity<>(jobStopResult, HttpStatus.OK);
    }

}
