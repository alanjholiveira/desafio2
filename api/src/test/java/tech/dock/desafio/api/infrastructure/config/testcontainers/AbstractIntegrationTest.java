package tech.dock.desafio.api.infrastructure.config.testcontainers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import tech.dock.desafio.api.DesafioDockApplication;

@SpringBootTest(classes = DesafioDockApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer =  new PostgreSQLContainer<>("postgres:14-alpine")
                .withUsername("test")
                .withPassword("password")
                .withDatabaseName("test")
                .withReuse(true);
        postgreSQLContainer.start();
    }
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.r2dbc.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
                + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);

        registry.add("spring.liquibase.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.liquibase.user", postgreSQLContainer::getUsername);
        registry.add("spring.liquibase.password", postgreSQLContainer::getPassword);
    }

}
