package ru.practicum.common.exception;

public class DateNotValidException extends RuntimeException {
    public DateNotValidException(String message) {
        super(message);
    }
}
