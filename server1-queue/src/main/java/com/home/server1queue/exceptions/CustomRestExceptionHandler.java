package com.home.server1queue.exceptions;

import com.home.server1queue.domain.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom error and exception handler.
 * It wraps error inside <code>ApiError</code> object
 * and send to the client.
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles validation exceptions.
     * Wraps their errors and messages inside ApiError object.
     *
     * @param ex the exception.
     * @param headers the http headers.
     * @param status the http status.
     * @param request the http request.
     * @return response entity of ApiError type.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        for (ObjectError ge : ex.getBindingResult().getGlobalErrors()) {
            errors.put(ge.getObjectName(), ge.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        logger.error("Validation API error has been sent: " + apiError);

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * Handles common exceptions and wraps it to ApiError object.
     *
     * @param ex the exception
     * @param request the http request
     * @return response entity with ApiError object.
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiError> handleAll(Exception ex,
                                              WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getLocalizedMessage(), new AbstractMap.SimpleEntry<>("error", "error occurred"));

        logger.error("Default exception handler has generated error " + apiError);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
