package tech.dock.desafio.api.domain.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.dock.desafio.api.infrastructure.exception.ErroTransacaoException;

import java.math.BigDecimal;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

    /**
     * Realiza calculo para verificar ser o valor e inferior
     * @param value BigDecimal
     * @param max BigDecimal
     * @return boolean
     */
    public static Boolean isLower(BigDecimal value, BigDecimal max) {
        return value.compareTo(max) <= 0;
    }

    /**
     * Realiza calculo para verificar ser valo está entre os valores informado.
     * @param value BigDecimal
     * @param min BigDecimal
     * @param max BigDecimal
     * @return boolean
     */
    public static boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    /**
     * Retorna erro em caso de falha na tarnsação
     * @param error Throwable
     * @throws ErroTransacaoException Exception
     */
    public static void getOnError(Throwable error) {
        log.error("[ERROR] Ocorreu erro na tentativa de realizar uma transação, erro: {}", error.getMessage());
        throw new ErroTransacaoException(error);
    }

}
