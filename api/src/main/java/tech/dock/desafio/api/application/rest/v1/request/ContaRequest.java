package tech.dock.desafio.api.application.rest.v1.request;

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
public class ContaRequest {

    @NotNull
    private BigInteger idPessoa;
    @NotNull
    private BigDecimal limiteSaqueDiario;
    @NotNull
    private TipoConta tipoConta;

}
