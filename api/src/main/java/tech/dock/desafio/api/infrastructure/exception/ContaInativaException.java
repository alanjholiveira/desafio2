package tech.dock.desafio.api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;

public class ContaInativaException extends HttpException {

    private static final String MESSAGE = "Conta informada não permite movimentação por estar bloqueada";

    public ContaInativaException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
