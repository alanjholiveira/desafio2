package tech.dock.desafio.api.infrastructure.exception;

import org.springframework.http.HttpStatus;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;

import java.util.HashMap;

public class ErroConsultarException extends HttpException {

    private static final String MESSAGE = "Ocorreu um erro ao realizar uma consulta";

    public ErroConsultarException() {
        super(MESSAGE);
    }

    public ErroConsultarException(Throwable cause) {
        super(cause, MESSAGE, new HashMap<>());
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
