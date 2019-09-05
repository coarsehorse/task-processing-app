package com.home.server1queue.repositories;

import com.home.server1queue.domain.TaskResultContainer;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ResultRepository extends ConcurrentHashMap<Integer, TaskResultContainer> {
}
