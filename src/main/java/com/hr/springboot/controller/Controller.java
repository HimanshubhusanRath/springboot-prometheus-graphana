package com.hr.springboot.controller;

import com.hr.springboot.metrics.exporter.CustomMetricsExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private CustomMetricsExporter customMetricsExporter;

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String type) {
        customMetricsExporter.recordOrderCount(type);
        return String.format("%s order is processed", type);
    }

    @PostMapping("/active-users")
    public String recordActiveUsers(@RequestParam int count) {
        customMetricsExporter.recordActiveUserCount(count);
        return "Active users count is updated";
    }
}
