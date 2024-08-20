package com.example.mywatch1.controller;

import com.example.mywatch1.service.ApiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class ApiLogController {

    private final ApiLogService apiLogService;

    @Autowired
    public ApiLogController(ApiLogService apiLogService) {
        this.apiLogService = apiLogService;
    }

    @PostMapping
    public ResponseEntity<String> receiveApiLog(@RequestBody ApiLogDto apiLogDto) {
        apiLogService.saveLog(apiLogDto);
        return ResponseEntity.ok("Log received successfully");
    }
}
