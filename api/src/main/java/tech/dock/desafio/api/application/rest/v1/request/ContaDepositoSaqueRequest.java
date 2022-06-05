package tech.dock.desafio.api.application.rest.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContaDepositoSaqueRequest {

    private BigInteger idConta;
    private BigDecimal valor;

}
