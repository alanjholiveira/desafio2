package tech.dock.desafio.api.application.rest.v1.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.dock.desafio.api.infrastructure.enums.TipoConta;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaResponse {

    private BigInteger idConta;
    private String nomePessoa;
    private BigDecimal limiteSaqueDiario;
    private TipoConta tipoConta;

}
