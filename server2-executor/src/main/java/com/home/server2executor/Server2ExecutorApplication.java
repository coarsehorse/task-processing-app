package com.home.server2executor;

import com.home.server2executor.domain.Result;
import com.home.server2executor.domain.Task;
import com.home.server2executor.service.TaskExecutorService;
import com.home.server2executor.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
public class Server2ExecutorApplication {

    private Logger logger =
            LoggerFactory.getLogger(Server2ExecutorApplication.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(Server2ExecutorApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    /**
     * Run fetching new task process in the main thread.
     * When new task received it execution started in separate thread.
     *
     * @param taskService injected task service.
     * @param taskExecutorService injected task executor service.
     * @return Spring mechanism to run something on app start.
     */
    @Bean
    public CommandLineRunner run(TaskService taskService,
                                 TaskExecutorService taskExecutorService,
                                 ExecutorService executorService,
                                 @Value("${refresh.delay}") Integer refreshDelay) {
        return args -> {
            while (true) {
                Optional<Task> task;
                for (task = Optional.empty(); !task.isPresent();) {
                    try {
                        task = taskService.getATask();
                    } catch (ResourceAccessException ignore) {
                        // If queue server is temporarily
                        // unavailable continue to ping it
                    }
                    Thread.sleep(refreshDelay);
                }
                Task taskObj = task.get();
                logger.info("Received new task " + taskObj);
                executorService.execute(() -> {
                    Result result = taskExecutorService.executeTask(taskObj);
                    taskService.putAResult(result);
                    logger.info("Putted new result " + result);
                });
            }
        };
    }


}
