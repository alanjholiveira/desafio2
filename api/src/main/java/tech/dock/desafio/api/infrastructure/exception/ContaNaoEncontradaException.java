package tech.dock.desafio.api.infrastructure.exception;


import org.springframework.http.HttpStatus;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;

public final class ContaNaoEncontradaException extends HttpException {

    private static final String MESSAGE = "A conta informada não foi localizado.";

    public ContaNaoEncontradaException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
