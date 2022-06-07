package tech.dock.desafio.api.application.rest.v1.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequest {

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

}
