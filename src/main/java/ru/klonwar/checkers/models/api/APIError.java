package ru.klonwar.checkers.models.api;

public class APIError extends RuntimeException {
    public APIError() {
    }

    public APIError(String message) {
        super(message);
    }

    public APIError(String message, Throwable cause) {
        super(message, cause);
    }

    public APIError(Throwable cause) {
        super(cause);
    }
}
