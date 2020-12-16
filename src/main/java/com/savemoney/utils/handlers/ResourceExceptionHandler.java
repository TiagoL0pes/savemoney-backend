package com.savemoney.utils.handlers;

import com.savemoney.utils.exceptions.BadRequestException;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import com.savemoney.utils.exceptions.TransactionNotAllowedException;
import com.savemoney.utils.helpers.DateHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultError> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        DefaultError error = generateDefaultError(exception, req, status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultError> badRequest(BadRequestException exception, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        DefaultError error = generateDefaultError(exception, req, status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(TransactionNotAllowedException.class)
    public ResponseEntity<DefaultError> unprocessableEntity(TransactionNotAllowedException exception, HttpServletRequest req) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        DefaultError error = generateDefaultError(exception, req, status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DefaultError> dataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletRequest req) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        DefaultError error = generateDefaultError(exception, req, status);
        return ResponseEntity.status(status).body(error);
    }

    private DefaultError generateDefaultError(RuntimeException exception, HttpServletRequest req, HttpStatus status) {
        String dateTime = DateHelper.formatDate(LocalDateTime.now(), "dd/MM/yyyy hh:MM:ss");
        return DefaultError.builder()
                .dateTime(dateTime)
                .status(status)
                .code(status.value())
                .message(exception.getMessage())
                .path(req.getRequestURI())
                .build();
    }
}
