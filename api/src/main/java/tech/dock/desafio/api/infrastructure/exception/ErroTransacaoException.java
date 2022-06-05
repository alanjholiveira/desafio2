package tech.dock.desafio.api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;

import java.util.HashMap;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ErroTransacaoException extends HttpException {

    private static final String MESSAGE = "Ocorreu um erro ao realizar uma transação";

    public ErroTransacaoException() {
        super(MESSAGE);
    }

    public ErroTransacaoException(Throwable cause) {
        super(cause, cause.getMessage(), new HashMap<>());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
