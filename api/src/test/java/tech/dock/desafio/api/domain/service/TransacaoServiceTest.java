package tech.dock.desafio.api.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.dock.desafio.api.application.rest.v1.mapper.TransacaoMapper;
import tech.dock.desafio.api.builder.entity.TransacaoBuilder;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.domain.entity.Transacao;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;
import tech.dock.desafio.api.infrastructure.enums.TipoTransacao;
import tech.dock.desafio.api.infrastructure.exception.ErroTransacaoException;
import tech.dock.desafio.api.infrastructure.repository.ContaRepository;
import tech.dock.desafio.api.infrastructure.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TransacaoServiceTest extends AbstractIntegrationTest {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    @InjectMocks
    private TransacaoService service;

    @Mock
    private TransacaoRepository repository;

    @Mock
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoBuilder builder;


    @Test
    void quandoSolicitarExtratoRetornaSucesso() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();
        when(contaRepository.findById(transacaoBuilder.getIdConta())).thenReturn(Mono.just(transacaoBuilder.getConta()));
        when(repository.findByIdConta(transacaoBuilder.getIdConta())).thenReturn(Flux.just(transacaoBuilder));

        Flux<Transacao> response = service.extratoTransacaoConta(transacaoBuilder.getIdConta());

        StepVerifier.create(response)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void quandoRealizarTransacaoDepositoRetornaSucesso() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();
        transacaoBuilder.setTipoTransacao(TipoTransacao.DEPOSITO.getIdTransacao());
        transacaoBuilder.setValor(BigDecimal.valueOf(100));

        when(repository.save(any(Transacao.class))).thenReturn(Mono.just(transacaoBuilder));

        Mono<Conta> result = service.realizarTransacao(TipoTransacao.DEPOSITO, transacaoBuilder.getConta(),
                BigDecimal.valueOf(100));

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void quandoRealizarTransacaoSaqueRetornaSucesso() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();
        transacaoBuilder.setValor(BigDecimal.valueOf(100));

        when(repository.save(any(Transacao.class))).thenReturn(Mono.just(transacaoBuilder));
        when(repository.getValorTransacoesPorDia(transacaoBuilder.getIdConta(), TipoTransacao.SAQUE.getIdTransacao(), LocalDate.now()))
                .thenReturn(Mono.just(BigDecimal.valueOf(100)));

        Mono<Conta> result = service.realizarTransacao(TipoTransacao.SAQUE, transacaoBuilder.getConta(),
                BigDecimal.valueOf(100));

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void quandoRealizarTransacaoDepositoRetornaError() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();
        when(contaRepository.findById(transacaoBuilder.getIdConta())).thenReturn(Mono.just(transacaoBuilder.getConta()));
        when(repository.findByIdConta(transacaoBuilder.getIdConta())).thenReturn(Flux.just(transacaoBuilder));
        when(repository.save(transacaoBuilder)).thenReturn(Mono.just(transacaoBuilder));


        Mono<Conta> response = service.realizarTransacao(TipoTransacao.DEPOSITO, transacaoBuilder.getConta(),
                BigDecimal.valueOf(100));

        StepVerifier.create(response)
                .expectError(ErroTransacaoException.class)
                .verify();
    }

    @Test
    void quandoSolicitarExtratoPorPeriodoRetornaSucesso() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();

        when(repository.getTransacoesPorPeriodo(transacaoBuilder.getIdConta(), LocalDate.now(), LocalDate.now().plusDays(2)))
                .thenReturn(Flux.just(transacaoBuilder));

        Flux<Transacao> result = service.extratoTransacaoPorPeriodo(transacaoBuilder.getIdConta(), LocalDate.now(), LocalDate.now().plusDays(2));

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

}
