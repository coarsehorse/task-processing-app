package com.home.server1queue.controllers;

import com.home.server1queue.domain.Result;
import com.home.server1queue.domain.Task;
import com.home.server1queue.domain.TaskResultContainer;
import com.home.server1queue.repositories.ResultRepository;
import com.home.server1queue.repositories.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.EmptyStackException;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository<Task> taskRepository;

    @Autowired
    private ResultRepository resultRepository;

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/execute")
    public Result execute(@RequestBody Task task) {
        logger.info("Received /execute request, task " + task);

        TaskResultContainer trc = new TaskResultContainer(task, null);
        resultRepository.put(task.hashCode(), trc); // share locked object
        taskRepository.push(task); // add task to the queue

        // Wait for result in shared task result container
        while (true) {
            trc.getLock().lock();
            try {
                if (trc.getResult() != null)
                    break;
            } finally {
                trc.getLock().unlock();
            }
        }
        resultRepository.remove(task.hashCode());

        return trc.getResult();
    }

    @GetMapping("/getATask")
    public ResponseEntity<Task> getATask() {
        //logger.info("Received /getATask request");

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
        TaskResultContainer trc = resultRepository.get(task.hashCode());
        if (trc == null) {
            logger.error("No TaskResultContainer found for task " + task);
            logger.error("resultRepository: " + resultRepository);
            logger.error("task.hashCode(): " + task.hashCode());
            return;
        }

        // Put a result and notify waiting thread about result(by result availability)
        trc.getLock().lock(); // acquire lock
        try {
            trc.setResult(result);
        } finally {
            trc.getLock().unlock();
        }
        logger.info("Successfully putted result " + result + " to container " + trc);
    }
}
