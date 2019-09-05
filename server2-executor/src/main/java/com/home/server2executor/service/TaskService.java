package com.home.server2executor.service;
import com.home.server2executor.domain.Result;
import com.home.server2executor.domain.Task;
import com.home.server2executor.resttemplate.RestTemplateResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private RestTemplate restTemplate;

    @Autowired
    public TaskService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    @Value("${queue.url}")
    private String queueUrl;

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
