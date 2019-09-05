package com.home.server2executor.domain;

import java.util.Date;
import java.util.Objects;

/**
 * Task domain model.
 */
public class Task implements Cloneable {

    public enum Type {
        GetAll,
        Create,
        Update,
        Remove
    }

    private Type taskType;
    private Product product;
    private Date dateOfReceipt;


    public Task() {
        dateOfReceipt = new Date();
    }

    public Task(Type taskType, Product product, Date dateOfReceipt) {
        this.taskType = taskType;
        this.product = product;
        this.dateOfReceipt = dateOfReceipt;
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
