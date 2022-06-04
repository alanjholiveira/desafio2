package tech.dock.desafio.api.infrastructure.exception.commons;

import java.util.Map;

public abstract class ApiException extends RuntimeException {

    protected SimpleError error;
    protected Throwable cause;

    private static final String DEFAULT_MESSAGE = "Problema inesperado.";

    private ApiException(String message) {
        super(message);
    }

    public ApiException() {
        this(SimpleError.builder().message(DEFAULT_MESSAGE).build());
    }

    public ApiException(Throwable cause) {
        this(SimpleError.builder().message(DEFAULT_MESSAGE).build());
        this.cause = cause;
    }

    public ApiException(SimpleError error) {
        this(error.getMessage());
        this.error = error;
    }

    public ApiException(Throwable cause, SimpleError error) {
        this(error);
        this.cause = cause;
    }

    @SafeVarargs
    public ApiException(String message, Map<String, String>... args) {
        this(SimpleError.builder().message(message).build());
    }

    public ApiException(String code, String message, Map<String, String> args) {
        this(SimpleError.builder().message(message).code(code).messageArgs(args).build());
    }

    public ApiException(Throwable cause, String message, Map<String, String> args) {
        this(cause, SimpleError.builder().message(message).messageArgs(args).build());
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    public SimpleError getError() {
        return error;
    }

}
