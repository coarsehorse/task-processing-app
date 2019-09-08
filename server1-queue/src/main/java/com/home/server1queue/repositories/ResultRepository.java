package com.home.server1queue.repositories;

import com.home.server1queue.domain.Result;
import com.home.server1queue.domain.Task;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Results storage.
 * Key - Task hash.
 * Value - Pair of original Task object and corresponding Result object.
 *
 */
@Repository
public class ResultRepository extends ConcurrentHashMap<Integer, Map.Entry<Task, Result>> {
}
