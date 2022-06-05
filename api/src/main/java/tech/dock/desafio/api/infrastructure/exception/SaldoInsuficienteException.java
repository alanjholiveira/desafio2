package tech.dock.desafio.api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;

public class SaldoInsuficienteException extends HttpException {

    private static final String MESSAGE = "Saldo insuficiente para realizar essa operação";

    public SaldoInsuficienteException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
