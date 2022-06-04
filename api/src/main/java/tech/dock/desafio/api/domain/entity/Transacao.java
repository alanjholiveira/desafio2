package tech.dock.desafio.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table("tb_transacao")
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    private BigInteger idTransacao;

    private BigInteger idConta;

    private Integer tipoTransacao;

    @Transient
    private Conta conta;

    private BigDecimal valor;

    @CreatedDate
    private LocalDate dataTransacao;

    @CreatedDate
    private LocalTime horaTransacao;

}
