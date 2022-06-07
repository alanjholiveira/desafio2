package tech.dock.desafio.api.application.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import tech.dock.desafio.api.application.controller.HomeController;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;

@SpringBootTest
public class HomeControllerTest extends AbstractIntegrationTest {

    private static final String URL = "/";

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient
                .bindToController(new HomeController())
                .configureClient()
                .baseUrl(URL)
                .build();

    }

    @Test
    void quandoAcessarHomeRetornaSucesso() {

        webTestClient.get()
                .accept(MediaType.valueOf(MediaType.TEXT_HTML_VALUE))
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

}
