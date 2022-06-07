package tech.dock.desafio.api.application.rest.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import tech.dock.desafio.api.application.rest.v1.mapper.TransacaoMapper;
import tech.dock.desafio.api.application.rest.v1.response.TransacaoResponse;
import tech.dock.desafio.api.domain.service.TransacaoService;

import java.math.BigInteger;
import java.time.LocalDate;

@Slf4j
@Api(tags = "Transações", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/v1/transacoes")
@RequiredArgsConstructor
public final class TransacaoRest {

    private final TransacaoService service;

    @ApiOperation(value = "Solicita extrato da conta de um cliente", response = TransacaoResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/extrato/{idConta}")
    public Flux<TransacaoResponse> extratoTransacaoConta(@PathVariable("idConta") BigInteger idConta) {
        return service.extratoTransacaoConta(idConta)
                .map(TransacaoMapper::toResponse);
    }

    @ApiOperation(value = "Solicita extrato da conta de um cliente por periodo", response = TransacaoResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/extratoPeriodo")
    public Flux<TransacaoResponse> extratoTransacaoPorPeriodo(
            @RequestParam("conta") BigInteger idConta,
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal
    ) {
        return service.extratoTransacaoPorPeriodo(idConta, dataInicial, dataFinal)
                .map(TransacaoMapper::toResponse);
    }


}
