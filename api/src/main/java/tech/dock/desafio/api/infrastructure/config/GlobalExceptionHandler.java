package tech.dock.desafio.api.infrastructure.config;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;
import tech.dock.desafio.api.infrastructure.exception.commons.HttpException;
import tech.dock.desafio.api.infrastructure.exception.commons.SimpleError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String missingServletRequestParameter = "Falta parâmetro a ser informado.";
    private static final String methodArgumentTypeMismatch = "Parâmetro informado inválido.";
    private static final String jsonProcessing = "JSON de entrada inválido.";

    private final Tracer tracer;

    @ExceptionHandler
    public ResponseEntity<SimpleError> handleException(Throwable throwable) {
        if(throwable instanceof HttpException) {
            HttpException e = (HttpException) throwable;
            SimpleError error = convertError(e.getError(), e.getHttpStatus());
            this.LOGGER.error(error.getMessage(), e.getCause() != null ? e.getCause() : e);
            return ResponseEntity.status(e.getHttpStatus()).body(error);
        }
        this.LOGGER.error(throwable.getMessage(), throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler
    public ResponseEntity<SimpleError> handlerServerInputException(ServerWebInputException e) {
        this.LOGGER.error(e.getMessage(), e);
        SimpleError error = new SimpleError();
        error.setMessage(missingServletRequestParameter);
        return ResponseEntity.badRequest().body(convertError(error, e.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<SimpleError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        this.LOGGER.error(e.getMessage(), e);
        SimpleError error = new SimpleError();
        error.setMessage(methodArgumentTypeMismatch);
        return new ResponseEntity<>(convertError(error, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SimpleError> handleJsonProcessingException(JsonProcessingException e) {
        this.LOGGER.error(e.getMessage(), e);
        SimpleError error = new SimpleError();
        error.setMessage(jsonProcessing);
        return new ResponseEntity<>(convertError(error, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SimpleError> handleConstraintViolationException(ConstraintViolationException e) {
        this.LOGGER.error(e.getMessage(), e);
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .get();
        SimpleError error = SimpleError.builder()
                .message(message)
                .build();
        return new ResponseEntity<>(convertError(error, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    private SimpleError convertError(SimpleError error, HttpStatus httpStatus) {
        error.setCode(String.valueOf(httpStatus.value()));
        error.setTraceId(getTraceIdIfPresent());
        return error;
    }

    private String getTraceIdIfPresent() {
        return Optional.ofNullable(tracer.currentSpan())
                .map(Span::context)
                .map(TraceContext::traceIdString)
                .orElse(null);
    }

}
