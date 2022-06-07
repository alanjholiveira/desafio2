package tech.dock.desafio.api.application.rest.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.application.rest.v1.mapper.ContaMapper;
import tech.dock.desafio.api.application.rest.v1.request.ContaAlterarStatusRequest;
import tech.dock.desafio.api.application.rest.v1.request.ContaDepositoSaqueRequest;
import tech.dock.desafio.api.application.rest.v1.request.ContaRequest;
import tech.dock.desafio.api.application.rest.v1.response.ContaResponse;
import tech.dock.desafio.api.application.rest.v1.response.ContaSaldoResponse;
import tech.dock.desafio.api.domain.service.ContaService;

import javax.validation.Valid;
import java.math.BigInteger;

@Slf4j
@Api(tags = "Contas", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/v1/contas")
@RequiredArgsConstructor
public class ContaRest {

    private final ContaService service;


    @ApiOperation(value = "Cria uma nova conta para um cliente", response = ContaResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping()
    public Mono<ContaResponse> criarConta(@RequestBody @Valid ContaRequest request) {
        log.info("Iniciando cadastro da conta");
        return service.criarConta(ContaMapper.toEntity(request))
                .map(ContaMapper::toResponse);
    }

    @ApiOperation(value = "Alterar a situação da conta de um cliente", response = Void.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping("/alterarFlag")
    public Mono<Void> alterarFlagConta(@RequestBody @Valid ContaAlterarStatusRequest request) {
        log.info("Iniciando habilitação da conta");
        return service.alterarFlag(request.getIdConta(), request.getFlagAtivo())
                .doOnSuccess(voidResponse -> log.info("Status: {} da conta foi alterado com sucesso",
                        request.getFlagAtivo()));
    }

    @ApiOperation(value = "Consulta saldo da conta de um cliente", response = ContaSaldoResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/{idConta}")
    public Mono<ContaSaldoResponse> consultarSaldo(@PathVariable("idConta") BigInteger idConta) {
        log.info("Iniciando busca por pessoa através do idPessoa");
        return service.consultarSaldo(idConta)
                .map(ContaMapper::toSaldoResponse);
    }

    @ApiOperation(value = "Realiza um deposito na conta de um cliente", response = ContaSaldoResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping("/deposito")
    public Mono<ContaSaldoResponse> depositoConta(@RequestBody @Valid ContaDepositoSaqueRequest request) {
        log.info("Iniciando Deposito da conta: {}, no valor de: {}", request.getIdConta(), request.getValor());
        return service.depositoConta(request.getIdConta(), request.getValor())
                .map(ContaMapper::toSaldoResponse);
    }

    @ApiOperation(value = "Realiza saque da conta de um cliente", response = ContaSaldoResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping("/saque")
    public Mono<ContaSaldoResponse> saqueConta(@RequestBody @Valid ContaDepositoSaqueRequest request) {
        log.info("Iniciando Saque da conta: {}, no valor de: {}", request.getIdConta(), request.getValor());
        return service.saqueConta(request.getIdConta(), request.getValor())
                .map(ContaMapper::toSaldoResponse);
    }


}
