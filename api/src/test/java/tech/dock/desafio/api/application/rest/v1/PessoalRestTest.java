package tech.dock.desafio.api.application.rest.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.builder.entity.PessoaBuilder;
import tech.dock.desafio.api.domain.entity.Pessoa;
import tech.dock.desafio.api.domain.service.PessoaService;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;

import java.text.ParseException;

import static org.mockito.Mockito.when;

@SpringBootTest
class PessoalRestTest extends AbstractIntegrationTest {

    private static final String URL = "/v1/pessoas";

    private WebTestClient webTestClient;

    @Mock
    private PessoaService service;

    @Autowired
    private PessoaBuilder builder;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient
                .bindToController(new PessoaRest(service))
                .configureClient()
                .baseUrl(URL)
                .build();

    }

    @Test
    void quandoListarPessoaRetornaSucesso() throws ParseException {
        Pessoa pessoaBuilder = builder.construirEntidade();

        when(service.findAll()).thenReturn(Flux.just(pessoaBuilder));


        webTestClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void quandoBuscarPessoalPeloIdRetornaSucesso() throws ParseException {
        Pessoa pessoaBuilder = builder.construirEntidade();

        when(service.findByIdPessoa(pessoaBuilder.getIdPessoa())).thenReturn(Mono.just(pessoaBuilder));


        webTestClient.get()
                .uri("/".concat(pessoaBuilder.getIdPessoa().toString()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
