package com.home.server2executor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Task domain model.
 */
public class Task implements Cloneable, Serializable {

    public enum Type {
        GetAll,
        Create,
        Update,
        Remove
    }

    private Type taskType;
    private Product product;
    private Date dateOfReceipt;
    @JsonIgnore
    private boolean done;

    public Task() {
        dateOfReceipt = new Date();
        done = false;
    }

    public Task(Type taskType, Product product, Date dateOfReceipt) {
        this.taskType = taskType;
        this.product = product;
        this.dateOfReceipt = dateOfReceipt;
        done = false;
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
                '}';
    }

    @Override
    public Task clone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Task(this.taskType, this.product, this.dateOfReceipt);
        }
    }
}