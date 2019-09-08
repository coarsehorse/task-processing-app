package com.home.server1queue.repositories;

import com.home.server1queue.domain.Task;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Task queue storage.
 */
@Repository
public class TaskRepository extends ConcurrentLinkedQueue<Task> {
}
