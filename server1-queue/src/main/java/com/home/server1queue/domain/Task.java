package com.home.server1queue.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Task domain model.
 */
public class Task implements Serializable {

    public enum Type {
        GetAll,
        Create,
        Update,
        Remove
    }

    @NotNull
    private Type taskType;

    @Valid
    private Product product;

    @Nullable
    private Date dateOfReceipt;

    @Nullable
    private Integer pageIndex;

    @Nullable
    private Integer pageSize;

    @JsonIgnore
    private boolean done;

    public Task() {
        dateOfReceipt = new Date();
        done = false;
    }

    public Task(Type taskType, Product product,
                Date dateOfReceipt, Integer pageIndex, Integer pageSize) {
        this.taskType = taskType;
        this.product = product;
        this.dateOfReceipt = dateOfReceipt;
        done = false;
    }

    public Task(Task that) {
        this(that.getTaskType(), new Product(that.getProduct()),
                new Date(that.getDateOfReceipt().getTime()), that.getPageIndex(), that.getPageSize());
    }

    public Type getTaskType() {
        return taskType;
    }

    public void setTaskType(Type taskType) {
        this.taskType = taskType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Nullable
    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(@Nullable Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Nullable
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(@Nullable Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Wait for the result of current task
     * using two steps: inner flag <code>done</code> and
     * <code>wait()</code> method.
     */
    public synchronized void waitForResult() {
        while (!this.done) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); // preserve interruption status
            }
        }
    }

    /**
     * Notify waiting thread about task result.
     */
    public synchronized void notifyAboutResult() {
        this.done = true;
        notify();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskType == task.taskType &&
                Objects.equals(product, task.product) &&
                Objects.equals(dateOfReceipt, task.dateOfReceipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskType, product);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskType=" + taskType +
                ", product=" + product +
                ", dateOfReceipt=" + dateOfReceipt +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", done=" + done +
                '}';
    }
}
