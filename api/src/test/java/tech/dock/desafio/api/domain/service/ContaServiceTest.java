package tech.dock.desafio.api.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.dock.desafio.api.builder.entity.ContaBuilder;
import tech.dock.desafio.api.builder.entity.PessoaBuilder;
import tech.dock.desafio.api.builder.entity.TransacaoBuilder;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;
import tech.dock.desafio.api.infrastructure.enums.TipoTransacao;
import tech.dock.desafio.api.infrastructure.exception.ErroTransacaoException;
import tech.dock.desafio.api.infrastructure.repository.ContaRepository;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ContaServiceTest extends AbstractIntegrationTest {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    @InjectMocks
    private ContaService service;

    @Mock
    private ContaRepository repository;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private TransacaoService transacaoService;

    @Autowired
    private ContaBuilder builder;


    @Test
    void quandoCriarContaRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        when(repository.save(contaBuilder)).thenReturn(Mono.just(contaBuilder));

        Mono<Conta> response = service.criarConta(contaBuilder);

        StepVerifier.create(response)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void quandoConsultarSaldoRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        when(pessoaService.findByIdPessoa(contaBuilder.getIdPessoa())).thenReturn(Mono.just(contaBuilder.getPessoa()));
        when(repository.findById(contaBuilder.getIdConta())).thenReturn(Mono.just(contaBuilder));

        Mono<Conta> result = service.consultarSaldo(contaBuilder.getIdConta());

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void quandoAlterarFlagRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        contaBuilder.setFlagAtivo(Boolean.FALSE);
        when(pessoaService.findByIdPessoa(contaBuilder.getIdPessoa())).thenReturn(Mono.just(contaBuilder.getPessoa()));
        when(repository.findById(contaBuilder.getIdConta())).thenReturn(Mono.just(contaBuilder));
        when(repository.save(contaBuilder)).thenReturn(Mono.just(contaBuilder));

        service.alterarFlag(contaBuilder.getIdConta(), Boolean.FALSE).block();

        verify(repository).save(contaBuilder);
        verify(repository).findById(contaBuilder.getIdConta());

    }

    @Test
    void quandoDepositarValorRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        when(pessoaService.findByIdPessoa(contaBuilder.getIdPessoa())).thenReturn(Mono.just(contaBuilder.getPessoa()));
        when(transacaoService.realizarTransacao(TipoTransacao.DEPOSITO, contaBuilder, BigDecimal.valueOf(100)))
                .thenReturn(Mono.just(contaBuilder));
        when(repository.findById(contaBuilder.getIdConta())).thenReturn(Mono.just(contaBuilder));
        when(repository.save(contaBuilder)).thenReturn(Mono.just(contaBuilder));


        Mono<Conta> result = service.depositoConta(contaBuilder.getIdConta(), BigDecimal.valueOf(100));

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void quandoSacarValorRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        when(pessoaService.findByIdPessoa(contaBuilder.getIdPessoa())).thenReturn(Mono.just(contaBuilder.getPessoa()));
        when(transacaoService.realizarTransacao(TipoTransacao.SAQUE, contaBuilder, BigDecimal.valueOf(10)))
                .thenReturn(Mono.just(contaBuilder));
        when(repository.findById(contaBuilder.getIdConta())).thenReturn(Mono.just(contaBuilder));
        when(repository.save(contaBuilder)).thenReturn(Mono.just(contaBuilder));


        Mono<Conta> result = service.saqueConta(contaBuilder.getIdConta(), BigDecimal.valueOf(10));

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void quandSacarValorRetornaErrorTransacao() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        contaBuilder.setFlagAtivo(Boolean.FALSE);
        when(transacaoService.realizarTransacao(TipoTransacao.SAQUE, contaBuilder, BigDecimal.valueOf(10)))
                .thenReturn(Mono.just(contaBuilder));
        when(repository.findById(contaBuilder.getIdConta())).thenReturn(Mono.just(contaBuilder));
        when(repository.save(contaBuilder)).thenReturn(Mono.just(contaBuilder));

        Publisher<Conta> result = service.saqueConta(contaBuilder.getIdConta(), BigDecimal.valueOf(10));

        StepVerifier.create(result)
                .expectError(ErroTransacaoException.class)
                .verify();
    }


}
