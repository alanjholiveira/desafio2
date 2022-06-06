package tech.dock.desafio.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SwaggerControllerConfig {

    @Bean
    RouterFunction<ServerResponse> routingFunction() {
        return route(GET("/swagger"),
                req -> ServerResponse.temporaryRedirect(URI.create("swagger-ui/")).build());
    }

}
