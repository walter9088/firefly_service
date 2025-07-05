package com.firefly.common.exception;

import com.firefly.common.model.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return HttpResponse.badRequest("参数验证失败", message).toResponseEntity();
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<HttpResponse<Object>> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return HttpResponse.badRequest("参数绑定失败", message).toResponseEntity();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HttpResponse<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return HttpResponse.badRequest("参数验证失败", message).toResponseEntity();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        return HttpResponse.badRequest("参数错误", e.getMessage()).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse<Object>> handleException(Exception e) {
        return HttpResponse.serverError("服务器内部错误", e.getMessage()).toResponseEntity();
    }
} 