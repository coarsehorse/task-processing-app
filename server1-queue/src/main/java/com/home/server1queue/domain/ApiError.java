package com.home.server1queue.domain;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.*;

/**
 * Api common error domain model.
 */
public class ApiError implements Serializable {

    private HttpStatus status;
    private String message;
    private Map<String, String> errors;

    public ApiError(HttpStatus status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, Map.Entry<String, String> error) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonMap(error.getKey(), error.getValue());
    }

    public ApiError(ApiError that) {
        this(that.getStatus(),
                that.getMessage(), new HashMap<>(that.getErrors()));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return status == apiError.status &&
                Objects.equals(message, apiError.message) &&
                Objects.equals(errors, apiError.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, errors);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}
