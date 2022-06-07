package tech.dock.desafio.api.application.rest.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import tech.dock.desafio.api.application.rest.v1.request.ContaAlterarStatusRequest;
import tech.dock.desafio.api.application.rest.v1.request.ContaDepositoSaqueRequest;
import tech.dock.desafio.api.application.rest.v1.request.ContaRequest;
import tech.dock.desafio.api.builder.entity.ContaBuilder;
import tech.dock.desafio.api.domain.entity.Conta;
import tech.dock.desafio.api.domain.service.ContaService;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;
import tech.dock.desafio.api.infrastructure.enums.TipoConta;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContaRestTest extends AbstractIntegrationTest {

    private static final String URL = "/v1/contas";

    private WebTestClient webTestClient;

    @Mock
    private ContaService service;

    @Autowired
    private ContaBuilder builder;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient
                .bindToController(new ContaRest(service))
                .configureClient()
                .baseUrl(URL)
                .build();

    }

    @Test
    void quandoCriarContaRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        ContaRequest request = ContaRequest.builder()
                        .idPessoa(contaBuilder.getIdPessoa())
                        .tipoConta(TipoConta.CONTA_CORRENTE)
                        .limiteSaqueDiario(BigDecimal.valueOf(1000))
                        .build();

        when(service.criarConta(any(Conta.class))).thenReturn(Mono.just(contaBuilder));

        webTestClient.post()
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void quandoConsultaSaldoRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();

        when(service.consultarSaldo(any())).thenReturn(Mono.just(contaBuilder));

        webTestClient.get()
                .uri("/".concat(contaBuilder.getIdPessoa().toString()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void quandoRealizarDepositoRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        ContaDepositoSaqueRequest request = ContaDepositoSaqueRequest.builder()
                        .idConta(contaBuilder.getIdConta())
                        .valor(BigDecimal.valueOf(100))
                        .build();

        when(service.depositoConta(any(), any())).thenReturn(Mono.just(contaBuilder));

        webTestClient.put()
                .uri("/deposito")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void quandoRealizarSaqueRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        ContaDepositoSaqueRequest request = ContaDepositoSaqueRequest.builder()
                .idConta(contaBuilder.getIdConta())
                .valor(BigDecimal.valueOf(100))
                .build();

        when(service.saqueConta(any(), any())).thenReturn(Mono.just(contaBuilder));

        webTestClient.put()
                .uri("/saque")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    void quandoAlterarFlagRetornaSucesso() throws ParseException {
        Conta contaBuilder = builder.construirEntidade();
        ContaAlterarStatusRequest request = ContaAlterarStatusRequest.builder()
                .idConta(contaBuilder.getIdConta())
                .flagAtivo(Boolean.TRUE)
                .build();

        when(service.alterarFlag(any(), any())).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/alterarFlag")
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
