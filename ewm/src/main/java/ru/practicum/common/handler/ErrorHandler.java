package ru.practicum.common.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.common.dto.ApiError;
import ru.practicum.common.exception.ConflictException;
import ru.practicum.common.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Not Found")
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(final IllegalArgumentException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Bad Request")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Conflict situation with input parameter")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIllegalStateException(final IllegalStateException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Illegal State")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Method Argument Not Valid")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason(e.getParameterType() + " " + e.getParameterName())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(final ConstraintViolationException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Constraint Violation")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDbConstraintViolationException(final DataIntegrityViolationException e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("DB Constraint Violation")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleServerError(final Throwable e) {
        return ApiError.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
