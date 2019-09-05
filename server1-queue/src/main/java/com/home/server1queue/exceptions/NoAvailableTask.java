package com.home.server1queue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoAvailableTask extends RuntimeException {

    public NoAvailableTask() { super(); }

    public NoAvailableTask(String message) {
        super(message);
    }

    public NoAvailableTask(String message, Throwable cause) {
        super(message, cause);
    }
}
