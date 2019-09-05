package com.home.server2executor.service;

import com.home.server2executor.domain.Product;
import com.home.server2executor.domain.Result;
import com.home.server2executor.domain.Task;
import com.home.server2executor.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutorService {

    private Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private ProductRepository productRepository;



    public TaskExecutorService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Result createTask(Task task) {
        logger.error("1 TaskExecutorService createTask() task product " + task.getProduct());
        // Clone task product object to get rid of save() method side effects
        Product created = productRepository.save(task.getProduct().clone());
        logger.error("2 TaskExecutorService createTask() task product " + task.getProduct());
        return new Result(task, created, true, false);
    }

    public Result executeTask(Task task) {
        switch (task.getTaskType()) {
            case Create:
                return createTask(task);
            /*case GetAll:
                break;
            case Remove:
                break;
            case Update:
                break;*/
            default:
                return new Result(task,
                        "Not recognized task type: " + task.getTaskType(),
                        false,
                        true);
        }
    }
}
