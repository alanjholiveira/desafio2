package tech.dock.desafio.api.domain.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.dock.desafio.api.infrastructure.config.testcontainers.AbstractIntegrationTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UtilsTest extends AbstractIntegrationTest {

    @Test
    void quandoVerificarValorRetornaSucesso() {

        boolean result = Utils.isBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(10),
                BigDecimal.valueOf(1000));

        assertThat(result)
                .isTrue();
    }

    @Test
    void quandoVerificarValorInferiorRetornaSucesso() {

        boolean result = Utils.isLower(BigDecimal.valueOf(100), BigDecimal.valueOf(1000));

        assertThat(result)
                .isTrue();
    }
}
