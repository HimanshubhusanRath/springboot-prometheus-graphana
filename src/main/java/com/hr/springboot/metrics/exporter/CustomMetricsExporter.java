package com.hr.springboot.metrics.exporter;


import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomMetricsExporter {
    @Autowired
    private MeterRegistry meterRegistry;

    public void recordOrderCount(final String orderType) {
        meterRegistry.counter("order-count", "type", orderType).increment();
    }

    public void recordActiveUserCount(final int activeUserCount) {
        meterRegistry.gauge("active-user-count", activeUserCount);
    }

}
