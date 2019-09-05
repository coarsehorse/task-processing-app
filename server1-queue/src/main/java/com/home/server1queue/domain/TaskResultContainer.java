package com.home.server1queue.domain;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class TaskResultContainer {

    private Task task;
    private Result result;
    private ReentrantLock lock;

    public TaskResultContainer() {
        this.lock = new ReentrantLock();
    }

    public TaskResultContainer(Task task, Result result) {
        this.task = task;
        this.result = result;
        this.lock = new ReentrantLock();
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskResultContainer that = (TaskResultContainer) o;
        return Objects.equals(task, that.task) &&
                Objects.equals(result, that.result) &&
                Objects.equals(lock, that.lock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, result, lock);
    }

    @Override
    public String toString() {
        return "TaskResultContainer{" +
                ", task=" + task +
                ", result=" + result +
                ", lock=" + lock +
                '}';
    }
}
