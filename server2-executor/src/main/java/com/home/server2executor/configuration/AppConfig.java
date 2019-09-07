package com.home.server2executor.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {

    private Integer asyncLevelMin;
    private Integer asyncLevelMax;

    public AppConfig(@Value("${async.level.min}") Integer asyncLevelMin,
                     @Value("${async.level.max}") Integer asyncLevelMax) {
        this.asyncLevelMin = asyncLevelMin;
        this.asyncLevelMax = asyncLevelMax;
    }

    @Bean
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(
                asyncLevelMin,
                asyncLevelMax,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }
}
