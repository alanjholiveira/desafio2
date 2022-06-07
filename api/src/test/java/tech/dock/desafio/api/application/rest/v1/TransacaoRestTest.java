package tech.dock.desafio.api.application.rest.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import tech.dock.desafio.api.builder.entity.TransacaoBuilder;
import tech.dock.desafio.api.domain.entity.Transacao;
import tech.dock.desafio.api.domain.service.TransacaoService;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;

import java.text.ParseException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransacaoRestTest extends AbstractIntegrationTest {

    private static final String URL = "/v1/transacoes";

    private WebTestClient webTestClient;

    @Mock
    private TransacaoService service;

    @Autowired
    private TransacaoBuilder builder;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient
                .bindToController(new TransacaoRest(service))
                .configureClient()
                .baseUrl(URL)
                .build();

    }

    @Test
    void quandoBuscarExtratoRetornaSucesso() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();

        when(service.extratoTransacaoConta(any())).thenReturn(Flux.just(transacaoBuilder));


        webTestClient.get()
                .uri("/extrato/".concat(transacaoBuilder.getIdConta().toString()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void quandoBuscarExtratoPorPeriodoRetornaSucesso() throws ParseException {
        Transacao transacaoBuilder = builder.construirEntidade();

        when(service.extratoTransacaoPorPeriodo(any(), any(), any()))
                .thenReturn(Flux.just(transacaoBuilder));


        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/extratoPeriodo")
                        .queryParam("conta", transacaoBuilder.getIdConta())
                        .queryParam("dataInicial", LocalDate.now())
                        .queryParam("dataFinal", LocalDate.now().plusDays(1))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
