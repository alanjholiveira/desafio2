package tech.dock.desafio.api.infrastructure.exception.commons;

import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class HttpException extends ApiException {

    public HttpException() {
        super();
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(SimpleError error) {
        super(error);
    }

    public HttpException(Throwable cause, SimpleError error) {
        super(cause, error);
    }

    public HttpException(String message, Map<String, String>... args) {
        super(message, args);
    }

    public HttpException(String code, String message, Map<String, String> args) {
        super(code, message, args);
    }

    public HttpException(Throwable cause, String message, Map<String, String> args) {
        super(cause, message, args);
    }

    public abstract HttpStatus getHttpStatus();
}
