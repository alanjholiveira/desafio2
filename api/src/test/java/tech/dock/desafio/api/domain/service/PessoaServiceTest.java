package tech.dock.desafio.api.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tech.dock.desafio.api.builder.entity.PessoaBuilder;
import tech.dock.desafio.api.domain.entity.Pessoa;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;
import tech.dock.desafio.api.infrastructure.repository.PessoaRepository;

import java.text.ParseException;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PessoaServiceTest extends AbstractIntegrationTest {

    private static final Sort DEFAULT_SORT = Sort.by(Sort.Order.by("lastModifiedDate"));

    @InjectMocks
    private PessoaService service;

    @Mock
    private PessoaRepository repository;


    @Autowired
    private PessoaBuilder builder;


    @Test
    void when_getAll_returns_success() throws ParseException {
        Pessoa pessoaBuilder = builder.construirEntidade();
        when(repository.findAll(DEFAULT_SORT)).thenReturn(Flux.just(pessoaBuilder));

        Flux<Pessoa> response = service.findAll();

        StepVerifier.create(response)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void when_getPessoaByIdPessoa_return_success() throws ParseException {
        Pessoa pessoaBuilder = builder.construirEntidade();
        when(repository.findById(pessoaBuilder.getIdPessoa())).thenReturn(Mono.just(pessoaBuilder));

        Mono<Pessoa> result = service.findByIdPessoa(pessoaBuilder.getIdPessoa());

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

    }
}
