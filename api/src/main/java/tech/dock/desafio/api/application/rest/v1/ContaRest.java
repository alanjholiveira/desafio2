package tech.dock.desafio.api.application.rest.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.application.rest.v1.mapper.ContaMapper;
import tech.dock.desafio.api.application.rest.v1.request.ContaAlterarStatusRequest;
import tech.dock.desafio.api.application.rest.v1.request.ContaRequest;
import tech.dock.desafio.api.application.rest.v1.request.ContaDepositoSaqueRequest;
import tech.dock.desafio.api.application.rest.v1.response.ContaResponse;
import tech.dock.desafio.api.application.rest.v1.response.ContaSaldoResponse;
import tech.dock.desafio.api.domain.service.ContaService;

import javax.validation.Valid;
import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaRest {

    private final ContaService service;


    @GetMapping()
    public Flux<ContaResponse> listarTodaconta() {
        return service.listarTodaConta()
                .map(ContaMapper::toResponse);
    }

    @PostMapping()
    public Mono<ContaResponse> criarConta(@RequestBody @Valid ContaRequest request) {
        log.info("Iniciando cadastro da conta");
        return service.criarConta(ContaMapper.toEntity(request))
                .map(ContaMapper::toResponse);
    }

    @PutMapping("/alterarFlag")
    public Mono<Void> alterarFlagConta(@RequestBody @Valid ContaAlterarStatusRequest request) {
        log.info("Iniciando habilitação da conta");
        return service.alterarFlag(request.getIdConta(), request.getFlagAtivo())
                .doOnSuccess(voidResponse -> log.info("Status: {} da conta foi alterado com sucesso",
                        request.getFlagAtivo()));
    }

    @GetMapping("/{idConta}")
    public Mono<ContaSaldoResponse> consultarSaldo(@PathVariable("idConta") BigInteger idConta) {
        log.info("Iniciando busca por pessoa através do idPessoa");
        return service.consultarSaldo(idConta)
                .map(ContaMapper::toSaldoResponse);
    }

    @PutMapping("/deposito")
    public Mono<ContaSaldoResponse> depositoConta(@RequestBody @Valid ContaDepositoSaqueRequest request) {
        log.info("Iniciando Deposito da conta: {}, no valor de: {}", request.getIdConta(), request.getValor());
        return service.depositoConta(request.getIdConta(), request.getValor())
                .map(ContaMapper::toSaldoResponse);
    }

    @PutMapping("/saque")
    public Mono<ContaSaldoResponse> saqueConta(@RequestBody @Valid ContaDepositoSaqueRequest request) {
        log.info("Iniciando Saque da conta: {}, no valor de: {}", request.getIdConta(), request.getValor());
        return service.saqueConta(request.getIdConta(), request.getValor())
                .map(ContaMapper::toSaldoResponse);
    }


}
