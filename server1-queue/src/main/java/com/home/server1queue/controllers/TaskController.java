package com.home.server1queue.controllers;

import com.home.server1queue.domain.Result;
import com.home.server1queue.domain.Task;
import com.home.server1queue.repositories.ResultRepository;
import com.home.server1queue.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.AbstractMap;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository<Task> taskRepository;

    @Autowired
    private ResultRepository resultRepository;

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/execute")
    public ResponseEntity<Result> execute(@RequestBody Task task) {
        logger.info("Received /execute request, task " + task);

        // Share task/result pair
        resultRepository.put(task.hashCode(),
                new AbstractMap.SimpleEntry<>(task, null));

        // Add task to the tasks queue
        taskRepository.push(task);

        // Wait until executor server returns the result
        task.waitForResult();

        Result result = resultRepository.get(task.hashCode()).getValue();
        if (result == null) {
            String errorMessage = "Task " + task + " notified about result, but no result found";
            logger.error(errorMessage);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Result(task, errorMessage, false, true));
        }

        logger.info("Successfully executed task " + task);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getATask")
    public ResponseEntity<Task> getATask() {
        logger.debug("Received /getATask request");

        Optional<Task> taskToProcess = Optional.empty();
        try {
            taskToProcess = Optional.of(taskRepository.pop());
        } catch (EmptyStackException ignored) {
        }

        return ResponseEntity.of(taskToProcess);
    }

    @PostMapping("/putAResult")
    public void putAResult(@RequestBody Result result) {
        logger.info("Received /putAResult request, result " + result);

        Task task = result.getTask();
        Map.Entry<Task, Result> taskResPair = resultRepository.get(task.hashCode());
        if (taskResPair == null) {
            logger.error("No TaskResult pair found for task " + task);
            return;
        }
        // Set the result and notify task thread about it
        taskResPair.setValue(result);
        taskResPair.getKey().notifyAboutResult();
    }
}
