package com.home.server2executor.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * The result of task execution.
 */
public class Result implements Serializable {

    @Valid
    private Task task;

    @NotNull
    private Object resultData;

    @NotNull
    private Boolean isSuccessful;

    @NotNull
    private Boolean isError;

    public Result() {
        this.isError = true;
        this.isSuccessful = false;
    }

    public Result(Task task, Object resultData, boolean isSuccessful, boolean isError) {
        this.task = task;
        this.resultData = resultData;
        this.isSuccessful = isSuccessful;
        this.isError = isError;
    }

    public Result(Result that) {
        this(new Task(that.getTask()), that.getResultData(), that.isSuccessful(), that.isError());
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return isSuccessful == result.isSuccessful &&
                isError == result.isError &&
                Objects.equals(task, result.task) &&
                Objects.equals(resultData, result.resultData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, resultData, isSuccessful, isError);
    }

    @Override
    public String toString() {
        return "Result{" +
                "task=" + task +
                ", resultData=" + resultData +
                ", isSuccessful=" + isSuccessful +
                ", isError=" + isError +
                '}';
    }
}
