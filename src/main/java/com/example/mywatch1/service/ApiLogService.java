package com.example.mywatch1.service;

import com.example.mywatch1.repository.ApiLogRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiLogService {

    private final ApiLogRepository apiLogRepository;
    private final Counter apiCallCounter; //io.micrometer.core.instrument.Counter 를 import 해야되는거 주의
    private final Timer responseTimeTimer; //  io.micrometer.core.instrument. 에서 import 하는거 주의

    @Autowired
    public ApiLogService(ApiLogRepository apiLogRepository, MeterRegistry meterRegistry) {
        this.apiLogRepository = apiLogRepository;
        this.apiCallCounter = meterRegistry.counter("api.calls.total");
        this.responseTimeTimer = meterRegistry.timer("api.calls.response.time");
    }

    public void saveLog(ApiLogDto apiLogDto) {
        ApiLog apiLog = new ApiLog();
        apiLog.setEndpoint(apiLogDto.getEndpoint());
        apiLog.setMethod(apiLogDto.getMethod());
        apiLog.setStatus(apiLogDto.getStatus());
        apiLog.setIpAddress(apiLogDto.getIpAddress());
        apiLog.setRequestTime(apiLogDto.getRequestTime());
        apiLog.setResponseTime(apiLogDto.getResponseTime());

        apiLogRepository.save(apiLog);

        // Prometheus 메트릭 업데이트
        apiCallCounter.increment();
        responseTimeTimer.record(apiLog.getResponseTime(), java.util.concurrent.TimeUnit.MILLISECONDS);
    }
}