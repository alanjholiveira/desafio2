package tech.dock.desafio.api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;

public final class LimiteDiarioException extends HttpException {

    private static final String MESSAGE = "Você atingiu seu limite diario de saque. Para aumentar seu limite fale com seu gerente";

    public LimiteDiarioException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
