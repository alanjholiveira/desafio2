package tech.dock.desafio.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table("tb_conta")
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Conta {

    @Id
    private BigInteger idConta;

    private BigInteger idPessoa;

    @Transient
    private Pessoa pessoa;

    private BigDecimal saldo;

    private BigDecimal limiteSaqueDiario;

    private Boolean flagAtivo;

    private Integer tipoConta;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
