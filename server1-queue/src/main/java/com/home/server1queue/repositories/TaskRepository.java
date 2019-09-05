package com.home.server1queue.repositories;

import com.home.server1queue.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.Stack;

@Repository
public class TaskRepository<T> extends Stack<T> {
}
