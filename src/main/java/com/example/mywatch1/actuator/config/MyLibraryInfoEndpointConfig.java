package com.example.mywatch1.actuator.config;

import com.example.mywatch1.actuator.endpoint.MyLibraryInfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyLibraryInfoEndpointConfig {

    @Bean
    MyLibraryInfoEndpoint myLibraryInfoEndpoint() {
        return new MyLibraryInfoEndpoint();
    }
}