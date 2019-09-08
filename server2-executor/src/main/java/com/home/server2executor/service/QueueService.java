package com.home.server2executor.service;

import com.home.server2executor.domain.Result;
import com.home.server2executor.domain.Task;
import com.home.server2executor.resttemplate.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Service for interaction with queue server.
 */
@Service
public class QueueService {

    private String queueUrl;
    private RestTemplate restTemplate;

    public QueueService(RestTemplateBuilder restTemplateBuilder,
                        @Value("${queue.url}") String queueUrl) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        this.queueUrl = queueUrl;
    }

    /**
     * Get a task from queue server.
     *
     * @return task inside optional if task is present or empty optional.
     */
    public Optional<Task> getATask() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<Task> responseEntity = restTemplate.exchange(
                queueUrl + "/getATask",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Task.class);

        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Post done result to the queue server.
     *
     * @param result result of task execution.
     */
    public void putAResult(Result result) {
        restTemplate.exchange(
                queueUrl + "/putAResult",
                HttpMethod.POST,
                new HttpEntity<>(result),
                String.class);
    }
}
