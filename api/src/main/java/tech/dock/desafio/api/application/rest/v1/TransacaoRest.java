package tech.dock.desafio.api.application.rest.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import tech.dock.desafio.api.application.rest.v1.mapper.TransacaoMapper;
import tech.dock.desafio.api.application.rest.v1.response.TransacaoResponse;
import tech.dock.desafio.api.domain.entity.Transacao;
import tech.dock.desafio.api.domain.service.TransacaoService;

import java.math.BigInteger;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public final class TransacaoRest {

    private final TransacaoService service;

    @GetMapping("/extrato/{idConta}")
    public Flux<TransacaoResponse> extratoTransacaoConta(@PathVariable("idConta") BigInteger idConta) {
        return service.extratoTransacaoConta(idConta)
                .map(TransacaoMapper::toResponse);
    }

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
