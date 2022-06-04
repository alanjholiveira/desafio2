package tech.dock.desafio.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    private static final String PACKAGEREST = "tech.dock.desafio.api.application.rest";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGEREST))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Desafio Dock - Software Engineer")
                .description("API Referente ao desafio da empresa Dock para vaga de Software Engineer")
                .version("1.0.0")
                .contact(contact())
                .build();
    }

    private Contact contact() {
        return new Contact("Alan Oliveira", "https://github.com/alanjholiveira", "alanjhone@gmail.com");
    }

}
