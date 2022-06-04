package tech.dock.desafio.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("tb_pessoa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    private BigInteger idPessoa;

    @NotNull
    private String nome;

    @Size(max = 13)
    @NotNull
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
